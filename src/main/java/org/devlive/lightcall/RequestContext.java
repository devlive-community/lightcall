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
        if (body == null) {
            throw new IllegalArgumentException("Request body cannot be null");
        }
        this.body = RequestBody.create(objectMapper.writeValueAsString(body).getBytes(StandardCharsets.UTF_8), MediaType.parse("application/json"));
    }
}
