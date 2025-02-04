package org.devlive.lightcall.example.delete;

import org.devlive.lightcall.LightCall;
import org.devlive.lightcall.config.LightCallConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DeleteServiceTest
{
    private final LightCallConfig config = LightCallConfig.create("http://mockaroo.devlive.org");
    private final DeleteService service = LightCall.create(DeleteService.class, config);

    @Test
    void test()
    {
        Assertions.assertNotNull(service.deletePost(1L));
    }
}
