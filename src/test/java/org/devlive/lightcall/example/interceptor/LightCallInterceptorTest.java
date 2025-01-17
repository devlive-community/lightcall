package org.devlive.lightcall.example.interceptor;

import org.devlive.lightcall.LightCall;
import org.devlive.lightcall.config.LightCallConfig;
import org.devlive.lightcall.example.PostService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class LightCallInterceptorTest
{
    private final LightCallConfig config = LightCallConfig.create("https://jsonplaceholder.typicode.com")
            .addInterceptor(new LoggingInterceptor());
    private final PostService service = LightCall.create(PostService.class, config);

    @Test
    public void testGetPosts()
    {
        Assertions.assertNotNull(service.getPosts());
    }
}
