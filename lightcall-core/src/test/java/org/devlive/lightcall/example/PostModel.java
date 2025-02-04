package org.devlive.lightcall.example;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PostModel
        implements Serializable
{
    private String body;
    private String title;
    private Long id;
    private Long userId;

    private PostModel() {}

    public static PostModel create()
    {
        return new PostModel();
    }

    public PostModel body(String body)
    {
        this.body = body;
        return this;
    }

    public PostModel title(String title)
    {
        this.title = title;
        return this;
    }

    public PostModel id(Long id)
    {
        this.id = id;
        return this;
    }

    public PostModel userId(Long userId)
    {
        this.userId = userId;
        return this;
    }
}
