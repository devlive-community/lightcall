package org.devlive.lightcall.example.post;

import org.devlive.lightcall.LightCall;
import org.devlive.lightcall.config.LightCallConfig;
import org.devlive.lightcall.example.PostModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PostServiceTest
{
    private final LightCallConfig config = LightCallConfig.create("https://jsonplaceholder.typicode.com");
    private final PostService service = LightCall.create(PostService.class, config);

    @Test
    void createPost()
    {
        PostModel post = PostModel.create()
                .title("测试创建新的数据")
                .userId(1L)
                .body("这是测试数据");
        Assertions.assertNotNull(service.createPost(post).getId());
    }
}