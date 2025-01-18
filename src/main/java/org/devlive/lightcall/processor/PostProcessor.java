package org.devlive.lightcall.processor;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.devlive.lightcall.RequestContext;
import org.devlive.lightcall.annotation.Post;
import org.devlive.lightcall.error.ErrorHandler;
import org.devlive.lightcall.interceptor.Interceptor;

import java.util.List;

@Slf4j
public class PostProcessor
        extends AbstractMethodProcessor<Post>
{
    private PostProcessor(OkHttpClient client, ObjectMapper objectMapper, List<Interceptor> interceptors, List<ErrorHandler> errorHandlers)
    {
        super(client, objectMapper, interceptors, errorHandlers);
    }

    public static PostProcessor create(OkHttpClient client, ObjectMapper objectMapper, List<Interceptor> interceptors, List<ErrorHandler> errorHandlers)
    {
        return new PostProcessor(client, objectMapper, interceptors, errorHandlers);
    }

    @Override
    public Class<Post> getAnnotationType()
    {
        return Post.class;
    }

    @Override
    protected String getPath(Post annotation)
    {
        return annotation.value();
    }

    @Override
    protected Request buildRequest(HttpUrl url, RequestContext context)
    {
        return context.getRequestBuilder()
                .url(url)
                .post(RequestBody.create(null, ""))
                .build();
    }
}
