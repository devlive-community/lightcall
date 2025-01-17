package org.devlive.lightcall.example.interceptor;

import okhttp3.Request;
import okhttp3.Response;
import org.devlive.lightcall.interceptor.Interceptor;

public class LoggingInterceptor
        implements Interceptor
{
    @Override
    public Request beforeRequest(Request request)
    {
        System.out.println("Sending request: " + request.url());
        return request;
    }

    @Override
    public Response afterResponse(Response response)
    {
        System.out.println("Received response: " + response.code());
        return response;
    }
}
