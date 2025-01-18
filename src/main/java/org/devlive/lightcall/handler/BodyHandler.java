package org.devlive.lightcall.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import org.devlive.lightcall.RequestContext;
import org.devlive.lightcall.RequestException;
import org.devlive.lightcall.annotation.Body;

import java.lang.reflect.Parameter;

@Slf4j
public class BodyHandler
        implements ParameterHandler
{
    private final RequestContext context;

    private BodyHandler(RequestContext context)
    {
        this.context = context;
    }

    public static BodyHandler create(RequestContext context)
    {
        log.debug("Creating BodyHandler");
        return new BodyHandler(context);
    }

    @Override
    public boolean canHandle(Parameter parameter)
    {
        return parameter.isAnnotationPresent(Body.class);
    }

    @Override
    public String handle(Parameter parameter, Object arg, String path)
    {
        if (arg == null) {
            throw new IllegalArgumentException("Request body cannot be null");
        }

        Body bodyAnnotation = parameter.getAnnotation(Body.class);
        MediaType mediaType = MediaType.parse(bodyAnnotation.mediaType());

        if (mediaType == null) {
            throw new IllegalArgumentException("Invalid MediaType specified: " + bodyAnnotation.mediaType());
        }

        log.debug("Setting request body with MediaType {}: {}", mediaType, arg);
        try {
            context.setBody(arg, mediaType);
        }
        catch (JsonProcessingException e) {
            throw new RequestException("Failed to serialize request body", e);
        }

        return path;
    }
}
