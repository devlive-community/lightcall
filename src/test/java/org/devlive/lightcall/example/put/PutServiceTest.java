package org.devlive.lightcall.example.put;

import org.devlive.lightcall.LightCall;
import org.devlive.lightcall.config.LightCallConfig;
import org.devlive.lightcall.example.PostModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PutServiceTest
{
    private final LightCallConfig config = LightCallConfig.create("https://jsonplaceholder.typicode.com");
    private final PutService service = LightCall.create(PutService.class, config);

    @Test
    void createPost()
    {
        PostModel post = PostModel.create()
                .title("这是 PUT 修改后的数据")
                .userId(1L)
                .id(1L)
                .body("这是测试数据");
        Assertions.assertNotNull(service.putPost(1L, post).getId());
    }
}
