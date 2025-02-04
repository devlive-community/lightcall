package org.devlive.lightcall.example.head;

import org.devlive.lightcall.LightCall;
import org.devlive.lightcall.config.LightCallConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class HeadServiceTest
{
    private final LightCallConfig config = LightCallConfig.create("http://mockaroo.devlive.org");
    private final HeadService service = LightCall.create(HeadService.class, config);

    @Test
    void test()
    {
        Assertions.assertNull(service.apply());
    }
}