package org.devlive.lightcall.example.get;

import org.devlive.lightcall.annotation.Get;
import org.devlive.lightcall.annotation.Header;
import org.devlive.lightcall.annotation.Headers;
import org.devlive.lightcall.annotation.PathVariable;
import org.devlive.lightcall.annotation.Param;
import org.devlive.lightcall.example.PostModel;

import java.util.List;

public interface GetService
{
    @Get("/posts")
    List<PostModel> getPosts();

    @Get("/posts")
    List<PostModel> getPostsPaged(
            @Param("page") int page,
            @Param("size") int size
    );

    @Get("/posts/{id}")
    PostModel getPost(@PathVariable("id") Long id);

    @Get("/posts/{id}")
    PostModel getPostParamAndPath(
            @PathVariable("id") Long id,
            @Param("title") String title
    );

    @Get("/posts/{id}")
    PostModel getPostPathAndHeader(
            @PathVariable("id") Long id,
            @Header("x-api-key") String apiKey
    );

    @Get("/posts/{id}")
    @Headers({
            "Accept: application/json",
            "User-Agent: LightCall/1.0"
    })
    PostModel getPostHeaderAndParam(
            @PathVariable("id") Long id,
            @Header("x-api-key") String apiKey,
            @Param("title") String title
    );
}
