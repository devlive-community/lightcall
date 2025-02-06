package org.devlive.lightcall.example.part.map;

import org.devlive.lightcall.annotation.PartMap;
import org.devlive.lightcall.annotation.Post;

import java.io.File;
import java.util.Map;

public interface PartMapService
{
    @Post("/upload/multiple")
    Object apply(@PartMap(value = "files") Map<String, File> files);
}
