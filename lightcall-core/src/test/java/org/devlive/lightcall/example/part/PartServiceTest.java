package org.devlive.lightcall.example.part;

import org.devlive.lightcall.LightCall;
import org.devlive.lightcall.config.LightCallConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.nio.file.Path;

class PartServiceTest
{
    // 使用一个支持文件上传的测试服务
    private final LightCallConfig config = LightCallConfig.create("https://httpbin.org");
    private final PartService service = LightCall.create(PartService.class, config);

    @Test
    void testFileUpload(@TempDir Path tempDir)
            throws Exception
    {
        // 创建测试文件
        File testFile = tempDir.resolve("test.txt").toFile();
        testFile.createNewFile();

        // 测试文件上传
        String response = service.apply(testFile);
        Assertions.assertNotNull(response);
    }
}