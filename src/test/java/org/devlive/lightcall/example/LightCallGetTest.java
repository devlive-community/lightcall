package org.devlive.lightcall.example;

import org.devlive.lightcall.LightCall;
import org.devlive.lightcall.config.LightCallConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class LightCallGetTest
{
    private LightCallConfig config = LightCallConfig.create("https://jsonplaceholder.typicode.com");
    private PostService service = LightCall.create(PostService.class, config);

    @Test
    public void testGetPosts()
    {
        Assertions.assertNotNull(service.getPosts());
    }

    @Test
    public void testGetPostsPaged()
    {
        Assertions.assertNotNull(service.getPostsPaged(1, 10));
    }
}
