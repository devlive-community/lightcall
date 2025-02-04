package org.devlive.lightcall.example.get;

import org.devlive.lightcall.LightCall;
import org.devlive.lightcall.config.LightCallConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class GetServiceTest
{
    private final LightCallConfig config = LightCallConfig.create("http://mockaroo.devlive.org");
    private final GetService service = LightCall.create(GetService.class, config);

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

    @Test
    public void testGetPostHeaderAndParam()
    {
        Assertions.assertNotNull(service.getPostHeaderAndParam(1L, "apiKey", "title"));
    }
}
