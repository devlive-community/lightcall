package org.devlive.lightcall.example.part;

import org.devlive.lightcall.annotation.Part;
import org.devlive.lightcall.annotation.Post;

import java.io.File;

public interface PartService
{
    @Post("/upload")
    String apply(@Part("file") File file);
}
