package org.devlive.lightcall.handler;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;
import org.devlive.lightcall.annotation.Header;

import java.lang.reflect.Parameter;

@Slf4j
public class HeaderHandler
        implements ParameterHandler
{
    private final Request.Builder requestBuilder;

    private HeaderHandler(Request.Builder requestBuilder)
    {
        this.requestBuilder = requestBuilder;
    }

    public static HeaderHandler create(Request.Builder requestBuilder)
    {
        return new HeaderHandler(requestBuilder);
    }

    @Override
    public boolean canHandle(Parameter parameter)
    {
        return parameter.isAnnotationPresent(Header.class);
    }

    @Override
    public String handle(Parameter parameter, Object arg, String path)
    {
        // 处理参数级 @Header
        if (parameter != null && parameter.isAnnotationPresent(Header.class)) {
            handleParameterHeader(parameter, arg);
        }
        return path;
    }

    private void handleParameterHeader(Parameter parameter, Object arg)
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
}
