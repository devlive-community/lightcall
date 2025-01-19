---
title: 自定义拦截器
---

LightCall 支持自定义拦截器，可以在请求和响应之前做一些操作

---

!!! info "提示"

    只需要实现 `Interceptor` 接口，然后实现 `beforeRequest` 和 `afterResponse` 方法即可

!!!

```java
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
```