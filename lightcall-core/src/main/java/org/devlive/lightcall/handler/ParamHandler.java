package org.devlive.lightcall.handler;

import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl;
import org.devlive.lightcall.annotation.Param;

import java.lang.reflect.Parameter;

@Slf4j
public class ParamHandler
        implements ParameterHandler
{
    private final HttpUrl.Builder urlBuilder;

    private ParamHandler(HttpUrl.Builder urlBuilder)
    {
        this.urlBuilder = urlBuilder;
    }

    public static ParamHandler create(HttpUrl.Builder urlBuilder)
    {
        log.debug("Creating ParamHandler");
        return new ParamHandler(urlBuilder);
    }

    @Override
    public boolean canHandle(Parameter parameter)
    {
        return parameter.isAnnotationPresent(Param.class);
    }

    @Override
    public String handle(Parameter parameter, Object arg, String path)
    {
        if (arg == null) {
            if (parameter.getAnnotation(Param.class).required()) {
                throw new IllegalArgumentException(
                        String.format("Query parameter '%s' is required but was null", parameter.getName()));
            }
            return path;
        }

        Param annotation = parameter.getAnnotation(Param.class);
        String paramName = annotation.value().isEmpty() ? parameter.getName() : annotation.value();

        log.debug("Adding query parameter: {}={}", paramName, arg);
        urlBuilder.addQueryParameter(paramName, String.valueOf(arg));

        return path;
    }
}
