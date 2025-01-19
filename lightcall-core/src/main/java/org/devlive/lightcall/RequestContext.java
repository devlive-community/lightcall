package org.devlive.lightcall;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

import java.nio.charset.StandardCharsets;

@Getter
public class RequestContext
{
    private final static ObjectMapper objectMapper = new ObjectMapper();
    private final HttpUrl.Builder urlBuilder;
    private final Request.Builder requestBuilder;
    private RequestBody body;
    private MediaType mediaType = MediaType.parse("application/json");  // 默认 MediaType

    private RequestContext(HttpUrl.Builder urlBuilder, Request.Builder requestBuilder)
    {
        this.urlBuilder = urlBuilder;
        this.requestBuilder = requestBuilder;
    }

    public static RequestContext create(String baseUrl)
    {
        HttpUrl url = HttpUrl.parse(baseUrl);
        if (url == null) {
            throw new IllegalArgumentException("Base URL cannot be null");
        }

        HttpUrl.Builder urlBuilder = url.newBuilder();
        Request.Builder requestBuilder = new Request.Builder();
        return new RequestContext(urlBuilder, requestBuilder);
    }

    public void setBody(Object body)
            throws JsonProcessingException
    {
        setBody(body, this.mediaType);
    }

    public void setBody(Object body, MediaType mediaType)
            throws JsonProcessingException
    {
        if (body == null) {
            throw new IllegalArgumentException("Request body cannot be null");
        }
        if (mediaType == null) {
            throw new IllegalArgumentException("MediaType cannot be null");
        }
        this.mediaType = mediaType;
        this.body = RequestBody.create(
                objectMapper.writeValueAsString(body).getBytes(StandardCharsets.UTF_8),
                mediaType
        );
    }

    public void setMediaType(MediaType mediaType)
    {
        if (mediaType == null) {
            throw new IllegalArgumentException("MediaType cannot be null");
        }
        this.mediaType = mediaType;
    }
}
