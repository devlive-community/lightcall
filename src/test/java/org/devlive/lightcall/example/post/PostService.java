package org.devlive.lightcall.example.post;

import org.devlive.lightcall.annotation.Post;

public interface PostService
{
    @Post("/posts")
    org.devlive.lightcall.example.Post createPost(org.devlive.lightcall.example.Post post);
}
