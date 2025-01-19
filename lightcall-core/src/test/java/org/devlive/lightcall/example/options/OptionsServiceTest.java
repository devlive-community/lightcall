package org.devlive.lightcall.example.options;

import org.devlive.lightcall.LightCall;
import org.devlive.lightcall.config.LightCallConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class OptionsServiceTest
{
    private final LightCallConfig config = LightCallConfig.create("https://jsonplaceholder.typicode.com");
    private final OptionsService service = LightCall.create(OptionsService.class, config);

    @Test
    void test()
    {
        Assertions.assertNull(service.apply());
    }
}