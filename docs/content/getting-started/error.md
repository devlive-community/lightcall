---
title: 自定义错误处理器
---

LightCall 支持自定义错误处理器。

!!! info "提示"

    只需要实现 `ErrorHandler` 接口，然后实现 `canHandle` 和 `handle` 方法即可

!!!

```java
package org.devlive.lightcall.example.error;

import okhttp3.Request;
import okhttp3.Response;
import org.devlive.lightcall.error.ErrorHandler;

public class CustomErrorHandler
        implements ErrorHandler
{
    @Override
    public int order() {
        return 50; // 优先级高于默认处理器
    }

    @Override
    public boolean canHandle(Request request, Response response, Exception exception) {
        // 处理特定的业务异常
        return response != null && response.code() == 400;
    }

    @Override
    public Object handle(Request request, Response response, Exception exception, Class<?> returnType) throws Exception {
        // 读取错误响应并转换为业务对象
        String errorBody = response.body().string();
        // 进行自定义处理
        return null; // 或者返回默认值
    }
}
```