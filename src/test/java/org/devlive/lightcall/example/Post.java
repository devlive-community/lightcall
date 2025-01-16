package org.devlive.lightcall.example;

import lombok.Data;

@Data
public class Post
{
    private String body;
    private String title;
    private Long id;
    private Long userId;
}
