package org.devlive.lightcall.processor;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.devlive.lightcall.RequestContext;
import org.devlive.lightcall.RequestException;
import org.devlive.lightcall.handler.ParameterHandler;
import org.devlive.lightcall.handler.ParameterHandlerFactory;
import org.devlive.lightcall.interceptor.Interceptor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;

@Slf4j
public abstract class AbstractMethodProcessor<A extends Annotation>
        implements MethodProcessor<A>
{
    protected final OkHttpClient client;
    protected final ObjectMapper objectMapper;
    protected final List<Interceptor> interceptors;

    protected AbstractMethodProcessor(OkHttpClient client, ObjectMapper objectMapper, List<Interceptor> interceptors)
    {
        this.client = client;
        this.objectMapper = objectMapper;
        this.interceptors = interceptors;
    }

    @Override
    public Object process(Object proxy, Method method, Object[] args, A annotation, RequestContext context)
            throws Throwable
    {
        String path = getPath(annotation);
        HttpUrl url = buildUrl(path, method, args, context);
        Request request = buildRequest(url, context);
        return executeRequest(request, method.getReturnType(), context);
    }

    protected abstract String getPath(A annotation);

    protected abstract Request buildRequest(HttpUrl url, RequestContext context);

    protected HttpUrl buildUrl(String path, Method method, Object[] args, RequestContext context)
    {
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

    protected <T> T executeRequest(Request request, Class<T> returnType, RequestContext context)
            throws Exception
    {
        log.info("Executing request - URL: {}, Method: {}", request.url(), request.method());

        Request interceptedRequest = applyBeforeRequestInterceptors(request);

        long startTime = System.currentTimeMillis();
        try {
            Response response = client.newCall(interceptedRequest).execute();
            long duration = System.currentTimeMillis() - startTime;
            log.debug("Received response in {}ms - Status code: {}", duration, response.code());

            response = applyAfterResponseInterceptors(response);

            if (!response.isSuccessful()) {
                throw new RequestException("Request failed with code: " + response.code());
            }

            if (response.body() == null) {
                log.warn("Response body is null for URL: {}", request.url());
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

    protected Request applyBeforeRequestInterceptors(Request request)
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

    protected Response applyAfterResponseInterceptors(Response response)
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
