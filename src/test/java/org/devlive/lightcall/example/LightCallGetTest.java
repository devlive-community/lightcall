package org.devlive.lightcall.example;

import org.devlive.lightcall.LightCall;
import org.devlive.lightcall.config.LightCallConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class LightCallGetTest
{
    @Test
    public void testGetPosts()
    {
        LightCallConfig config = LightCallConfig.create("https://jsonplaceholder.typicode.com");

        PostService service = LightCall.create(PostService.class, config);

        Assertions.assertNotNull(service.getPosts());
    }
}
