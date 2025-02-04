package org.devlive.lightcall.example.patch;

import org.devlive.lightcall.LightCall;
import org.devlive.lightcall.config.LightCallConfig;
import org.devlive.lightcall.example.PostModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PatchServiceTest
{
    private final LightCallConfig config = LightCallConfig.create("http://mockaroo.devlive.org");
    private final PatchService service = LightCall.create(PatchService.class, config);

    @Test
    void test()
    {
        PostModel post = PostModel.create();
        Assertions.assertNotNull(service.patchPost(1L, post).getId());
    }
}
