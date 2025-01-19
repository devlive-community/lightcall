package org.devlive.lightcall.processor;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.devlive.lightcall.RequestContext;
import org.devlive.lightcall.annotation.Put;
import org.devlive.lightcall.error.ErrorHandler;
import org.devlive.lightcall.interceptor.Interceptor;

import java.util.List;

@Slf4j
public class PutProcessor
        extends AbstractMethodProcessor<Put>
{
    private PutProcessor(OkHttpClient client, ObjectMapper objectMapper, List<Interceptor> interceptors, List<ErrorHandler> errorHandlers)
    {
        super(client, objectMapper, interceptors, errorHandlers);
    }

    public static PutProcessor create(OkHttpClient client, ObjectMapper objectMapper, List<Interceptor> interceptors, List<ErrorHandler> errorHandlers)
    {
        return new PutProcessor(client, objectMapper, interceptors, errorHandlers);
    }

    @Override
    public Class<Put> getAnnotationType()
    {
        return Put.class;
    }

    @Override
    protected String getPath(Put annotation)
    {
        return annotation.value();
    }

    @Override
    protected Request buildRequest(HttpUrl url, RequestContext context)
    {
        return context.getRequestBuilder()
                .url(url)
                .put(context.getBody())
                .build();
    }
}
