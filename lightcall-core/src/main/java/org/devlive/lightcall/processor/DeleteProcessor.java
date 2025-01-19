package org.devlive.lightcall.processor;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.devlive.lightcall.RequestContext;
import org.devlive.lightcall.annotation.Delete;
import org.devlive.lightcall.error.ErrorHandler;
import org.devlive.lightcall.interceptor.Interceptor;

import java.util.List;

@Slf4j
public class DeleteProcessor
        extends AbstractMethodProcessor<Delete>
{
    private DeleteProcessor(OkHttpClient client, ObjectMapper objectMapper, List<Interceptor> interceptors, List<ErrorHandler> errorHandlers)
    {
        super(client, objectMapper, interceptors, errorHandlers);
    }

    public static DeleteProcessor create(OkHttpClient client, ObjectMapper objectMapper, List<Interceptor> interceptors, List<ErrorHandler> errorHandlers)
    {
        return new DeleteProcessor(client, objectMapper, interceptors, errorHandlers);
    }

    @Override
    public Class<Delete> getAnnotationType()
    {
        return Delete.class;
    }

    @Override
    protected String getPath(Delete annotation)
    {
        return annotation.value();
    }

    @Override
    protected Request buildRequest(HttpUrl url, RequestContext context)
    {
        Request.Builder requestBuilder = context.getRequestBuilder()
                .url(url);

        return context.getBody() != null ?
                requestBuilder.delete(context.getBody()).build() :
                requestBuilder.delete().build();
    }
}
