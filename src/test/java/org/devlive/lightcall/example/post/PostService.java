package org.devlive.lightcall.example.post;

import org.devlive.lightcall.annotation.Body;
import org.devlive.lightcall.annotation.Post;

public interface PostService
{
    @Post("/posts")
    org.devlive.lightcall.example.Post createPost(@Body org.devlive.lightcall.example.Post post);
}
