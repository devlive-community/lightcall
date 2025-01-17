package org.devlive.lightcall.processor;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.devlive.lightcall.RequestContext;
import org.devlive.lightcall.RequestException;
import org.devlive.lightcall.annotation.Get;
import org.devlive.lightcall.handler.ParameterHandler;
import org.devlive.lightcall.handler.ParameterHandlerFactory;
import org.devlive.lightcall.interceptor.Interceptor;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;

@Slf4j
public class GetProcessor
        implements MethodProcessor<Get>
{
    private final OkHttpClient client;
    private final ObjectMapper objectMapper;
    private final List<Interceptor> interceptors;

    public GetProcessor(OkHttpClient client, ObjectMapper objectMapper, List<Interceptor> interceptors)
    {
        this.client = client;
        this.objectMapper = objectMapper;
        this.interceptors = interceptors;
    }

    @Override
    public Class<Get> getAnnotationType()
    {
        return Get.class;
    }

    @Override
    public Object process(Object proxy, Method method, Object[] args, Get annotation, RequestContext context)
            throws Throwable
    {
        log.debug("Processing GET request for method: {}", method.getName());

        HttpUrl url = buildUrl(annotation.value(), method, args, context);
        return executeGet(url, method.getReturnType(), context);
    }

    private HttpUrl buildUrl(String path, Method method, Object[] args, RequestContext context)
    {
        // 原有的 buildUrl 逻辑
        log.debug("Building URL for path: {}", path);

        List<ParameterHandler> handlers = ParameterHandlerFactory.createHandlers(method, context);
        Parameter[] parameters = method.getParameters();
        String processedPath = path;

        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            Object arg = args[i];

            for (ParameterHandler handler : handlers) {
                if (handler.canHandle(parameter)) {
                    processedPath = handler.handle(parameter, arg, processedPath);
                    break;
                }
            }
        }

        if (!processedPath.startsWith("/")) {
            processedPath = "/" + processedPath;
        }
        context.getUrlBuilder().addPathSegments(processedPath.substring(1));

        HttpUrl url = context.getUrlBuilder().build();
        log.debug("Built URL: {}", url);
        return url;
    }

    private <T> T executeGet(HttpUrl url, Class<T> returnType, RequestContext context)
            throws Exception
    {
        log.info("Executing GET request - URL: {}, Expected return type: {}", url, returnType);

        Request originalRequest = context.getRequestBuilder()
                .url(url)
                .get()
                .build();

        Request request = applyBeforeRequestInterceptors(originalRequest);
        log.info("Executing request - URL: {}, Headers: {}", request.url(), request.headers());

        long startTime = System.currentTimeMillis();
        try {
            Response response = client.newCall(request).execute();
            long duration = System.currentTimeMillis() - startTime;
            log.debug("Received response in {}ms - Status code: {}", duration, response.code());

            response = applyAfterResponseInterceptors(response);

            if (!response.isSuccessful()) {
                throw new RequestException("Request failed with code: " + response.code());
            }

            if (response.body() == null) {
                log.warn("Response body is null for URL: {}", url);
                return null;
            }

            String responseBody = response.body().string();
            response.close();

            return objectMapper.readValue(responseBody, returnType);
        }
        catch (Exception e) {
            log.error("Error executing request: {}", e.getMessage(), e);
            throw e;
        }
    }

    private Request applyBeforeRequestInterceptors(Request request)
    {
        Request interceptedRequest = request;
        for (Interceptor interceptor : interceptors) {
            try {
                interceptedRequest = interceptor.beforeRequest(interceptedRequest);
                log.debug("Applied beforeRequest interceptor: {}", interceptor.getClass().getSimpleName());
            }
            catch (Exception e) {
                log.error("Error in beforeRequest interceptor {}: {}",
                        interceptor.getClass().getSimpleName(), e.getMessage(), e);
            }
        }
        return interceptedRequest;
    }

    private Response applyAfterResponseInterceptors(Response response)
            throws Exception
    {
        Response interceptedResponse = response;
        for (Interceptor interceptor : interceptors) {
            try {
                interceptedResponse = interceptor.afterResponse(interceptedResponse);
                log.debug("Applied afterResponse interceptor: {}", interceptor.getClass().getSimpleName());
            }
            catch (Exception e) {
                log.error("Error in afterResponse interceptor {}: {}",
                        interceptor.getClass().getSimpleName(), e.getMessage(), e);
                throw e;
            }
        }
        return interceptedResponse;
    }
}
