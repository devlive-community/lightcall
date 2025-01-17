package org.devlive.lightcall.handler;

import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl;
import org.devlive.lightcall.annotation.RequestParam;

import java.lang.reflect.Parameter;

@Slf4j
public class RequestParamHandler
        implements ParameterHandler
{
    private final HttpUrl.Builder urlBuilder;

    private RequestParamHandler(HttpUrl.Builder urlBuilder)
    {
        this.urlBuilder = urlBuilder;
    }

    public static RequestParamHandler create(HttpUrl.Builder urlBuilder)
    {
        log.debug("Creating RequestParamHandler");
        return new RequestParamHandler(urlBuilder);
    }

    @Override
    public boolean canHandle(Parameter parameter)
    {
        return parameter.isAnnotationPresent(RequestParam.class);
    }

    @Override
    public String handle(Parameter parameter, Object arg, String path)
    {
        if (arg == null) {
            if (parameter.getAnnotation(RequestParam.class).required()) {
                throw new IllegalArgumentException(
                        String.format("Query parameter '%s' is required but was null", parameter.getName()));
            }
            return path;
        }

        RequestParam annotation = parameter.getAnnotation(RequestParam.class);
        String paramName = annotation.value().isEmpty() ? parameter.getName() : annotation.value();

        log.debug("Adding query parameter: {}={}", paramName, arg);
        urlBuilder.addQueryParameter(paramName, String.valueOf(arg));

        return path;
    }
}
