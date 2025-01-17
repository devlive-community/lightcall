package org.devlive.lightcall.proxy;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.devlive.lightcall.RequestException;
import org.devlive.lightcall.annotation.Get;
import org.devlive.lightcall.config.LightCallConfig;
import org.devlive.lightcall.handler.ParameterHandler;
import org.devlive.lightcall.handler.ParameterHandlerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
public class LightCallProxy
        implements InvocationHandler
{
    private final OkHttpClient client;
    private final LightCallConfig config;
    private final ObjectMapper objectMapper;

    public LightCallProxy(LightCallConfig config)
    {
        log.debug("Initializing LightCallProxy with config: {}", config);
        this.config = config;
        this.client = new OkHttpClient.Builder()
                .connectTimeout(config.getConnectTimeout(), TimeUnit.MILLISECONDS)
                .readTimeout(config.getReadTimeout(), TimeUnit.MILLISECONDS)
                .build();
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable
    {
        log.debug("Invoking method: {}.{}({})",
                method.getDeclaringClass().getSimpleName(),
                method.getName(),
                formatMethodArgs(method, args));

        if (method.getDeclaringClass() == Object.class) {
            log.trace("Handling Object method: {}", method.getName());
            return method.invoke(this, args);
        }

        Get getAnnotation = method.getAnnotation(Get.class);
        if (getAnnotation != null) {
            log.debug("Processing @Get annotation with value: {}", getAnnotation.value());
            HttpUrl url = buildUrl(getAnnotation.value(), method, args);
            return executeGet(url, method.getReturnType());
        }

        throw new UnsupportedOperationException(
                String.format("Method %s is not annotated with @Get", method.getName()));
    }

    private HttpUrl buildUrl(String path, Method method, Object[] args)
    {
        log.debug("Building URL for path: {} with args: {}", path, Arrays.toString(args));

        // 创建基础 URL 构建器
        HttpUrl.Builder urlBuilder = HttpUrl.parse(config.getBaseUrl())
                .newBuilder();

        // 获取参数处理器
        List<ParameterHandler> handlers = ParameterHandlerFactory.createHandlers(urlBuilder);

        // 处理参数
        Parameter[] parameters = method.getParameters();
        String processedPath = path;

        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            Object arg = args[i];

            // 找到合适的处理器处理参数
            for (ParameterHandler handler : handlers) {
                if (handler.canHandle(parameter)) {
                    processedPath = handler.handle(parameter, arg, processedPath);
                    break;
                }
            }
        }

        // 添加处理后的路径
        if (!processedPath.startsWith("/")) {
            processedPath = "/" + processedPath;
        }
        urlBuilder.addPathSegments(processedPath.substring(1));

        HttpUrl url = urlBuilder.build();
        log.debug("Built URL: {}", url);
        return url;
    }

    private <T> T executeGet(HttpUrl url, Class<T> returnType)
            throws Exception
    {
        log.info("Executing GET request - URL: {}, Expected return type: {}", url, returnType);

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        long startTime = System.currentTimeMillis();
        try (Response response = client.newCall(request).execute()) {
            long duration = System.currentTimeMillis() - startTime;
            log.debug("Received response in {}ms - Status code: {}", duration, response.code());

            if (!response.isSuccessful()) {
                throw new RequestException("Request failed with code: " + response.code());
            }

            if (response.body() == null) {
                log.warn("Response body is null for URL: {}", url);
                return null;
            }

            String responseBody = response.body().string();

            return objectMapper.readValue(responseBody, returnType);
        }
    }

    private String formatMethodArgs(Method method, Object[] args)
    {
        if (args == null || args.length == 0) {
            return "";
        }
        Parameter[] parameters = method.getParameters();
        return Arrays.stream(parameters)
                .map(param -> String.format("%s=%s",
                        param.getName(),
                        args[Arrays.asList(parameters).indexOf(param)]))
                .collect(Collectors.joining(", "));
    }
}
