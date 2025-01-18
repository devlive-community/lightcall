package org.devlive.lightcall.example.delete;

import org.devlive.lightcall.annotation.Delete;
import org.devlive.lightcall.annotation.PathVariable;
import org.devlive.lightcall.example.PostModel;

public interface DeleteService
{
    @Delete("/posts/{id}")
    PostModel deletePost(@PathVariable("id") Long id);
}
