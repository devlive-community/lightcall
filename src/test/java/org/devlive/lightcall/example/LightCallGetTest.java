package org.devlive.lightcall.example;

import org.devlive.lightcall.LightCall;
import org.devlive.lightcall.config.LightCallConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class LightCallGetTest
{
    private final LightCallConfig config = LightCallConfig.create("https://jsonplaceholder.typicode.com");
    private final PostService service = LightCall.create(PostService.class, config);

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

    @Test
    public void testGetPost()
    {
        Assertions.assertNotNull(service.getPost(1L));
    }

    @Test
    public void testGetPostParamAndPath()
    {
        Assertions.assertNotNull(service.getPostParamAndPath(1L, "title"));
    }

    @Test
    public void testGetPostPathAndHeader()
    {
        Assertions.assertNotNull(service.getPostPathAndHeader(1L, "apiKey"));
    }
}
