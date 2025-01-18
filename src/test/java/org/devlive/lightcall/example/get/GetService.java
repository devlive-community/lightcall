package org.devlive.lightcall.example.get;

import org.devlive.lightcall.annotation.Get;
import org.devlive.lightcall.annotation.Header;
import org.devlive.lightcall.annotation.Headers;
import org.devlive.lightcall.annotation.PathVariable;
import org.devlive.lightcall.annotation.RequestParam;
import org.devlive.lightcall.example.Post;

import java.util.List;

public interface GetService
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

    @Get("/posts/{id}")
    Post getPostPathAndHeader(
            @PathVariable("id") Long id,
            @Header("x-api-key") String apiKey
    );

    @Get("/posts/{id}")
    @Headers({
            "Accept: application/json",
            "User-Agent: LightCall/1.0"
    })
    Post getPostHeaderAndParam(
            @PathVariable("id") Long id,
            @Header("x-api-key") String apiKey,
            @RequestParam("title") String title
    );
}
