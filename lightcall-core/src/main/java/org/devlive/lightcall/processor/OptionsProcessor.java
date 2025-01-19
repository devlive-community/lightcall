package org.devlive.lightcall.processor;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.devlive.lightcall.RequestContext;
import org.devlive.lightcall.annotation.Options;
import org.devlive.lightcall.error.ErrorHandler;
import org.devlive.lightcall.interceptor.Interceptor;

import java.util.List;

@Slf4j
public class OptionsProcessor
        extends AbstractMethodProcessor<Options>
{
    private OptionsProcessor(OkHttpClient client, ObjectMapper objectMapper, List<Interceptor> interceptors, List<ErrorHandler> errorHandlers)
    {
        super(client, objectMapper, interceptors, errorHandlers);
    }

    public static OptionsProcessor create(OkHttpClient client, ObjectMapper objectMapper, List<Interceptor> interceptors, List<ErrorHandler> errorHandlers)
    {
        return new OptionsProcessor(client, objectMapper, interceptors, errorHandlers);
    }

    @Override
    public Class<Options> getAnnotationType()
    {
        return Options.class;
    }

    @Override
    protected String getPath(Options annotation)
    {
        return annotation.value();
    }

    @Override
    protected Request buildRequest(HttpUrl url, RequestContext context)
    {
        if (context.getBody() != null) {
            log.warn("Body is not allowed for OPTIONS method");
        }

        return context.getRequestBuilder()
                .url(url)
                .method("OPTIONS", null)
                .build();
    }
}
