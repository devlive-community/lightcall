package org.devlive.lightcall.proxy;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.devlive.lightcall.RequestException;
import org.devlive.lightcall.annotation.Get;
import org.devlive.lightcall.annotation.RequestParam;
import org.devlive.lightcall.config.LightCallConfig;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
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

        // 处理 Object 方法
        if (method.getDeclaringClass() == Object.class) {
            log.trace("Handling Object method: {}", method.getName());
            return method.invoke(this, args);
        }

        // 处理 Get 注解
        Get getAnnotation = method.getAnnotation(Get.class);
        if (getAnnotation != null) {
            log.debug("Processing @Get annotation with value: {}", getAnnotation.value());
            HttpUrl url = buildUrl(getAnnotation.value(), method, args);
            log.info("Executing GET request to URL: {}", url);
            return executeGet(url, method.getGenericReturnType());
        }

        String errorMessage = String.format("Method %s is not annotated with @Get", method.getName());
        log.error(errorMessage);
        throw new UnsupportedOperationException(errorMessage);
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

    private HttpUrl buildUrl(String path, Method method, Object[] args)
    {
        log.debug("Building URL for path: {} with args: {}", path, Arrays.toString(args));
        HttpUrl.Builder urlBuilder = HttpUrl.parse(config.getBaseUrl())
                .newBuilder()
                .addPathSegments(path.startsWith("/") ? path.substring(1) : path);

        // 处理请求参数
        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            RequestParam param = parameters[i].getAnnotation(RequestParam.class);
            if (param != null && args[i] != null) {
                String paramName = param.value().isEmpty() ? parameters[i].getName() : param.value();
                String paramValue = String.valueOf(args[i]);
                log.trace("Adding query parameter: {}={}", paramName, paramValue);
                urlBuilder.addQueryParameter(paramName, paramValue);
            }
        }

        HttpUrl url = urlBuilder.build();
        log.debug("Built URL: {}", url);
        return url;
    }

    private Object executeGet(HttpUrl url, Type returnType)
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
                String errorMessage = String.format("Request failed with code: %d - URL: %s",
                        response.code(), url);
                log.error(errorMessage);
                throw new RequestException(errorMessage);
            }

            if (response.body() == null) {
                log.warn("Response body is null for URL: {}", url);
                return null;
            }

            byte[] bodyBytes = response.body().bytes();
            log.trace("Response body: {}", new String(bodyBytes));

            JavaType javaType = getJavaType(returnType);
            log.debug("Deserializing response to type: {}", javaType);

            try {
                Object result = objectMapper.readValue(bodyBytes, javaType);
                log.debug("Successfully deserialized response - Result type: {}",
                        result != null ? result.getClass().getName() : "null");
                return result;
            }
            catch (Exception e) {
                log.error("Failed to deserialize response for URL: {} to type: {}", url, javaType, e);
                throw e;
            }
        }
        catch (Exception e) {
            log.error("Error executing GET request to URL: {}", url, e);
            throw e;
        }
    }

    private JavaType getJavaType(Type type)
    {
        log.trace("Resolving JavaType for: {}", type);
        TypeFactory typeFactory = objectMapper.getTypeFactory();

        if (type instanceof ParameterizedType) {
            log.debug("Handling parameterized type: {}", type);
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Type rawType = parameterizedType.getRawType();
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();

            JavaType[] javaTypes = new JavaType[actualTypeArguments.length];
            for (int i = 0; i < actualTypeArguments.length; i++) {
                javaTypes[i] = getJavaType(actualTypeArguments[i]);
            }

            return typeFactory.constructParametricType((Class<?>) rawType, javaTypes);
        }

        return typeFactory.constructType(type);
    }
}
