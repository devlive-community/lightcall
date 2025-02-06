package org.devlive.lightcall.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import org.devlive.lightcall.RequestContext;
import org.devlive.lightcall.RequestException;
import org.devlive.lightcall.annotation.PartMap;

import java.io.File;
import java.lang.reflect.Parameter;
import java.util.Map;

@Slf4j
public class PartMapHandler
        implements ParameterHandler
{
    private final RequestContext context;

    private PartMapHandler(RequestContext context)
    {
        this.context = context;
    }

    public static PartMapHandler create(RequestContext context)
    {
        if (context == null) {
            throw new NullPointerException("RequestContext cannot be null");
        }
        log.debug("Creating PartMapHandler");
        return new PartMapHandler(context);
    }

    @Override
    public boolean canHandle(Parameter parameter)
    {
        return parameter.isAnnotationPresent(PartMap.class);
    }

    @Override
    public String handle(Parameter parameter, Object arg, String path)
    {
        if (arg == null) {
            PartMap annotation = parameter.getAnnotation(PartMap.class);
            if (annotation.required()) {
                throw new IllegalArgumentException("PartMap is required but was null");
            }
            return path;
        }

        if (!(arg instanceof Map)) {
            throw new IllegalArgumentException("Parameter annotated with @PartMap must be a Map");
        }

        Map<?, ?> map = (Map<?, ?>) arg;
        MultipartBody.Builder multipartBuilder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);

        PartMap annotation = parameter.getAnnotation(PartMap.class);
        String formName = annotation.value(); // 获取表单字段名前缀
        String mimeType = annotation.mimeType();

        for (Map.Entry<?, ?> entry : map.entrySet()) {
            Object value = entry.getValue();

            if (value instanceof File) {
                File file = (File) value;
                RequestBody fileBody = RequestBody.create(
                        file,
                        MediaType.parse(mimeType)
                );

                multipartBuilder.addFormDataPart(
                        formName,
                        file.getName(),
                        fileBody
                );
                log.debug("Added file part {}: {}", formName, file.getName());
            }
        }

        try {
            context.setBody(multipartBuilder.build(), MultipartBody.FORM);
        }
        catch (JsonProcessingException e) {
            throw new RequestException("Failed to set form parts", e);
        }

        return path;
    }
}
