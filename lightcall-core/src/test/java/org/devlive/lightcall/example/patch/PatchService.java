package org.devlive.lightcall.example.patch;

import org.devlive.lightcall.annotation.Body;
import org.devlive.lightcall.annotation.Patch;
import org.devlive.lightcall.annotation.PathVariable;
import org.devlive.lightcall.example.PostModel;

public interface PatchService
{
    @Patch("/posts/{id}")
    PostModel patchPost(@PathVariable("id") Long id, @Body PostModel post);
}
