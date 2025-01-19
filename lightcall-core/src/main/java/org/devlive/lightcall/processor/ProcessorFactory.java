package org.devlive.lightcall.processor;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import org.devlive.lightcall.error.ErrorHandler;
import org.devlive.lightcall.interceptor.Interceptor;

import java.lang.reflect.Method;
import java.util.List;

@Slf4j
public class ProcessorFactory
{
    public static MethodProcessor<?> createProcessor(
            Class<? extends MethodProcessor<?>> processorClass,
            OkHttpClient client,
            ObjectMapper objectMapper,
            List<Interceptor> interceptors,
            List<ErrorHandler> errorHandlers)
    {
        try {
            // 查找符合条件的 create 方法
            Method createMethod = processorClass.getMethod("create",
                    OkHttpClient.class,
                    ObjectMapper.class,
                    List.class,
                    List.class);

            // 调用静态 create 方法创建处理器实例
            return (MethodProcessor<?>) createMethod.invoke(null,
                    client,
                    objectMapper,
                    interceptors,
                    errorHandlers);
        }
        catch (Exception e) {
            log.error("Failed to create processor instance for class: {}", processorClass.getName(), e);
            throw new IllegalArgumentException("Failed to create processor instance", e);
        }
    }
}
