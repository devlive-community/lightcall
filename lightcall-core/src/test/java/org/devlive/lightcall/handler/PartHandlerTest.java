package org.devlive.lightcall.handler;

import org.devlive.lightcall.RequestContext;
import org.devlive.lightcall.annotation.Part;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.lang.reflect.Parameter;

class PartHandlerTest
{
    private PartHandler handler;
    private RequestContext context;

    @BeforeEach
    void setUp()
    {
        context = RequestContext.create("http://example.com");
        handler = PartHandler.create(context);
    }

    // 测试接口方法
    interface TestService
    {
        void upload(@Part("file") File file);

        void normalMethod(String text);
    }

    @Test
    void testCanHandle()
            throws NoSuchMethodException
    {
        // 测试带 @Part 注解的参数
        Parameter parameterWithPart = TestService.class
                .getMethod("upload", File.class)
                .getParameters()[0];
        assertTrue(handler.canHandle(parameterWithPart));

        // 测试不带注解的参数
        Parameter parameterWithoutPart = TestService.class
                .getMethod("normalMethod", String.class)
                .getParameters()[0];
        assertFalse(handler.canHandle(parameterWithoutPart));
    }

    @Test
    void testCreateHandler()
    {
        assertNotNull(PartHandler.create(context));
    }

    @Test
    void testCreateHandlerWithNullContext()
    {
        assertThrows(NullPointerException.class,
                () -> PartHandler.create(null));
    }
}
