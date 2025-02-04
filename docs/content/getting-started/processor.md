---
title: 自定义请求处理器
---

LightCall 为开发者提供了一个强大的请求处理器机制，可以用于处理和修改请求的数据。可以自定义请求处理器，以便更好地适应不同的业务需求。

!!! info "提示"

    只需要实现 `AbstractMethodProcessor` 抽象类，然后实现相应的方法即可。

!!!

我们以 `OptionsProcessor` 为例，实现一个自定义的请求处理器，用于处理 HTTP Options 请求。

### 添加注解

---

```java
package org.devlive.lightcall.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Options
{
    String value() default "";
}
```

### 实现处理器

---

```java
package org.devlive.lightcall.processor;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.devlive.lightcall.RequestContext;
import org.devlive.lightcall.annotation.Options;
import org.devlive.lightcall.error.ErrorHandler;
import org.devlive.lightcall.interceptor.Interceptor;

import java.util.List;

@Slf4j
public class OptionsProcessor
        extends AbstractMethodProcessor<Options>
{
    private OptionsProcessor(OkHttpClient client, ObjectMapper objectMapper, List<Interceptor> interceptors, List<ErrorHandler> errorHandlers)
    {
        super(client, objectMapper, interceptors, errorHandlers);
    }

    public static OptionsProcessor create(OkHttpClient client, ObjectMapper objectMapper, List<Interceptor> interceptors, List<ErrorHandler> errorHandlers)
    {
        return new OptionsProcessor(client, objectMapper, interceptors, errorHandlers);
    }

    @Override
    public Class<Options> getAnnotationType()
    {
        return Options.class;
    }

    @Override
    protected String getPath(Options annotation)
    {
        return annotation.value();
    }

    @Override
    protected Request buildRequest(HttpUrl url, RequestContext context)
    {
        if (context.getBody() != null) {
            log.warn("Body is not allowed for OPTIONS method");
        }

        return context.getRequestBuilder()
                .url(url)
                .method("OPTIONS", null)
                .build();
    }
}
```

### 注册处理器

```java
LightCallConfig config = LightCallConfig.create("http://mockaroo.devlive.org")
        .addProcessor(OptionsProcessor.class);
```