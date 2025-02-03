package org.devlive.lightcall.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import org.devlive.lightcall.RequestContext;
import org.devlive.lightcall.RequestException;
import org.devlive.lightcall.annotation.Part;

import java.io.File;
import java.lang.reflect.Parameter;

@Slf4j
public class PartHandler
        implements ParameterHandler
{
    private final RequestContext context;

    private PartHandler(RequestContext context)
    {
        this.context = context;
    }

    public static PartHandler create(RequestContext context)
    {
        if (context == null) {
            throw new NullPointerException("RequestContext cannot be null");
        }

        log.debug("Creating PartHandler");
        return new PartHandler(context);
    }

    @Override
    public boolean canHandle(Parameter parameter)
    {
        return parameter.isAnnotationPresent(Part.class);
    }

    @Override
    public String handle(Parameter parameter, Object arg, String path)
    {
        if (arg == null) {
            throw new IllegalArgumentException("Part file cannot be null");
        }

        if (!(arg instanceof File)) {
            throw new IllegalArgumentException("Parameter annotated with @Part must be a File");
        }

        Part partAnnotation = parameter.getAnnotation(Part.class);
        File file = (File) arg;

        MultipartBody.Builder multipartBuilder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);

        RequestBody fileBody = RequestBody.create(
                file,
                MediaType.parse(partAnnotation.mime())
        );

        multipartBuilder.addFormDataPart(
                partAnnotation.value(),
                file.getName(),
                fileBody
        );

        try {
            context.setBody(multipartBuilder.build(), MultipartBody.FORM);
        }
        catch (JsonProcessingException e) {
            throw new RequestException("Failed to set form part", e);
        }

        log.debug("Added file part {}: {}", partAnnotation.value(), file.getName());

        return path;
    }
}
