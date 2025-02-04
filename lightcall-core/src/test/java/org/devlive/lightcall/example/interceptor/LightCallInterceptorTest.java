package org.devlive.lightcall.example.interceptor;

import org.devlive.lightcall.LightCall;
import org.devlive.lightcall.config.LightCallConfig;
import org.devlive.lightcall.example.get.GetService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class LightCallInterceptorTest
{
    private final LightCallConfig config = LightCallConfig.create("http://mockaroo.devlive.org")
            .addInterceptor(new LoggingInterceptor());
    private final GetService service = LightCall.create(GetService.class, config);

    @Test
    public void testGetPosts()
    {
        Assertions.assertNotNull(service.getPosts());
    }
}
