package org.devlive.lightcall.example;

import org.devlive.lightcall.annotation.Get;

import java.util.List;

public interface PostService
{
    @Get("/posts")
    List<Post> getPosts();
}
