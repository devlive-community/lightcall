package org.devlive.lightcall.processor;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.devlive.lightcall.RequestContext;
import org.devlive.lightcall.annotation.Get;
import org.devlive.lightcall.interceptor.Interceptor;

import java.util.List;

@Slf4j
public class GetProcessor
        extends AbstractMethodProcessor<Get>
{
    public static GetProcessor create(OkHttpClient client, ObjectMapper objectMapper, List<Interceptor> interceptors)
    {
        return new GetProcessor(client, objectMapper, interceptors);
    }

    private GetProcessor(OkHttpClient client, ObjectMapper objectMapper, List<Interceptor> interceptors)
    {
        super(client, objectMapper, interceptors);
    }

    @Override
    public Class<Get> getAnnotationType()
    {
        return Get.class;
    }

    @Override
    protected String getPath(Get annotation)
    {
        return annotation.value();
    }

    @Override
    protected Request buildRequest(HttpUrl url, RequestContext context)
    {
        return context.getRequestBuilder()
                .url(url)
                .get()
                .build();
    }
}
