package org.devlive.lightcall.proxy;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import org.devlive.lightcall.RequestContext;
import org.devlive.lightcall.config.LightCallConfig;
import org.devlive.lightcall.processor.GetProcessor;
import org.devlive.lightcall.processor.MethodProcessor;
import org.devlive.lightcall.processor.PostProcessor;
import org.devlive.lightcall.processor.PutProcessor;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
public class LightCallProxy
        implements InvocationHandler
{
    private final OkHttpClient client;
    private final LightCallConfig config;
    private final ObjectMapper objectMapper;
    private final Map<Class<? extends Annotation>, MethodProcessor<?>> processors;

    public LightCallProxy(LightCallConfig config)
    {
        log.debug("Initializing LightCallProxy with config: {}", config);
        this.config = config;
        this.client = new OkHttpClient.Builder()
                .connectTimeout(config.getConnectTimeout(), TimeUnit.MILLISECONDS)
                .readTimeout(config.getReadTimeout(), TimeUnit.MILLISECONDS)
                .build();
        this.objectMapper = new ObjectMapper();
        this.processors = new HashMap<>();

        // 注册默认的处理器
        registerProcessor(GetProcessor.create(client, objectMapper, config.getInterceptors(), config.getErrorHandlers()));
        registerProcessor(PostProcessor.create(client, objectMapper, config.getInterceptors(), config.getErrorHandlers()));
        registerProcessor(PutProcessor.create(client, objectMapper, config.getInterceptors(), config.getErrorHandlers()));
    }

    public <A extends Annotation> void registerProcessor(MethodProcessor<A> processor)
    {
        processors.put(processor.getAnnotationType(), processor);
        log.debug("Registered processor for annotation: {}", processor.getAnnotationType().getSimpleName());
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

        // 查找方法上的所有注解
        for (Annotation annotation : method.getAnnotations()) {
            MethodProcessor<?> processor = processors.get(annotation.annotationType());
            if (processor != null) {
                // 创建请求上下文
                RequestContext context = RequestContext.create(config.getBaseUrl());
                return invokeProcessor(processor, proxy, method, args, annotation, context);
            }
        }

        throw new UnsupportedOperationException(
                String.format("Method %s has no registered processor for its annotations", method.getName()));
    }

    @SuppressWarnings("unchecked")
    private <A extends Annotation> Object invokeProcessor(
            MethodProcessor<?> processor,
            Object proxy,
            Method method,
            Object[] args,
            Annotation annotation,
            RequestContext context)
            throws Throwable
    {
        return ((MethodProcessor<A>) processor).process(proxy, method, args, (A) annotation, context);
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
