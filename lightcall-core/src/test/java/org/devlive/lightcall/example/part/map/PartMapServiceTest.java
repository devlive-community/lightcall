package org.devlive.lightcall.example.part.map;

import org.devlive.lightcall.LightCall;
import org.devlive.lightcall.config.LightCallConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

class PartMapServiceTest
{
    // 使用一个支持文件上传的测试服务
    private final LightCallConfig config = LightCallConfig.create("http://mockaroo.devlive.org");
    private final PartMapService service = LightCall.create(PartMapService.class, config);

    @Test
    void testFileUpload(@TempDir Path tempDir)
            throws Exception
    {
        // 创建测试文件
        File testFile = tempDir.resolve("test.txt").toFile();
        testFile.createNewFile();

        // 创建测试文件
        File testFile2 = tempDir.resolve("test2.txt").toFile();
        testFile2.createNewFile();

        Map<String, File> files = new HashMap<>();
        files.put("file1", testFile);
        files.put("file2", testFile2);

        // 测试文件上传
        Object response = service.apply(files);
        Assertions.assertNotNull(response);
    }
}
