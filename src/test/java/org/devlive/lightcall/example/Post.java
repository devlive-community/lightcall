package org.devlive.lightcall.example;

import lombok.Data;

@Data
public class Post
{
    private String body;
    private String title;
    private Long id;
    private Long userId;

    private Post() {}

    public static Post create()
    {
        return new Post();
    }

    public Post body(String body)
    {
        this.body = body;
        return this;
    }

    public Post title(String title)
    {
        this.title = title;
        return this;
    }

    public Post id(Long id)
    {
        this.id = id;
        return this;
    }

    public Post userId(Long userId)
    {
        this.userId = userId;
        return this;
    }
}
