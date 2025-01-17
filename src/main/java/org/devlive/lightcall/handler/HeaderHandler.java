package org.devlive.lightcall.handler;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;
import org.devlive.lightcall.annotation.Header;
import org.devlive.lightcall.annotation.Headers;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

@Slf4j
public class HeaderHandler
        implements ParameterHandler
{
    private final Request.Builder requestBuilder;
    private final Method method;

    private HeaderHandler(
            Request.Builder requestBuilder,
            Method method
    )
    {
        this.requestBuilder = requestBuilder;
        this.method = method;

        // 在构造时直接处理方法级 Headers
        if (method.isAnnotationPresent(Headers.class)) {
            handleMethodHeaders();
        }
    }

    public static HeaderHandler create(
            Request.Builder requestBuilder,
            Method method
    )
    {
        return new HeaderHandler(requestBuilder, method);
    }

    @Override
    public boolean canHandle(Parameter parameter)
    {
        return parameter != null && parameter.isAnnotationPresent(Header.class);
    }

    @Override
    public String handle(
            Parameter parameter,
            Object arg,
            String path
    )
    {
        // 处理参数级 @Header
        if (parameter != null && parameter.isAnnotationPresent(Header.class)) {
            handleParameterHeader(parameter, arg);
        }
        return path;
    }

    private void handleParameterHeader(
            Parameter parameter,
            Object arg
    )
    {
        Header annotation = parameter.getAnnotation(Header.class);
        String headerName = annotation.value();

        if (arg == null) {
            if (annotation.required()) {
                throw new IllegalArgumentException(
                        String.format("Header '%s' is required but was null", headerName));
            }
            return;
        }

        log.debug("Adding parameter header - {}: {}", headerName, arg);
        requestBuilder.addHeader(headerName, String.valueOf(arg));
    }

    private void handleMethodHeaders()
    {
        Headers headersAnnotation = method.getAnnotation(Headers.class);
        for (String header : headersAnnotation.value()) {
            int index = header.indexOf(':');
            if (index == -1 || index == header.length() - 1) {
                log.warn("Invalid header format: {}", header);
                continue;
            }

            String name = header.substring(0, index).trim();
            String value = header.substring(index + 1).trim();

            log.debug("Adding method header - {}: {}", name, value);
            requestBuilder.addHeader(name, value);
        }
    }
}
