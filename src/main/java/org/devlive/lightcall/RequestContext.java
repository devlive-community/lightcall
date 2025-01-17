package org.devlive.lightcall;

import lombok.Getter;
import okhttp3.HttpUrl;
import okhttp3.Request;

@Getter
public class RequestContext
{
    private final HttpUrl.Builder urlBuilder;
    private final Request.Builder requestBuilder;

    private RequestContext(HttpUrl.Builder urlBuilder, Request.Builder requestBuilder)
    {
        this.urlBuilder = urlBuilder;
        this.requestBuilder = requestBuilder;
    }

    public static RequestContext create(String baseUrl)
    {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(baseUrl).newBuilder();
        Request.Builder requestBuilder = new Request.Builder();
        return new RequestContext(urlBuilder, requestBuilder);
    }
}
