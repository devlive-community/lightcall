package org.devlive.lightcall.example.post;

import org.devlive.lightcall.annotation.Body;
import org.devlive.lightcall.annotation.Post;
import org.devlive.lightcall.example.PostModel;

public interface PostService
{
    @Post("/posts")
    PostModel createPost(@Body PostModel post);
}
