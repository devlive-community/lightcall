package org.devlive.lightcall.example;

import org.devlive.lightcall.annotation.Get;
import org.devlive.lightcall.annotation.PathVariable;
import org.devlive.lightcall.annotation.RequestParam;

import java.util.List;

public interface PostService
{
    @Get("/posts")
    List<Post> getPosts();

    @Get("/posts")
    List<Post> getPostsPaged(
            @RequestParam("page") int page,
            @RequestParam("size") int size
    );

    @Get("/posts/{id}")
    Post getPost(@PathVariable("id") Long id);

    @Get("/posts/{id}")
    Post getPostParamAndPath(
            @PathVariable("id") Long id,
            @RequestParam("title") String title
    );
}
