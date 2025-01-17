---
title: Interceptor
---

LightCall 支持拦截器，可以在请求和响应之前做一些操作

!!! info "提示"

    LightCall 支持多个拦截器，可以在请求和响应之前做一些操作。支持用户自定义拦截器。

!!!

## 用法

---

!!! info "提示"

    只需要在初始化配置的时候使用 `addInterceptor` 方法添加拦截器即可

!!!

```java
LightCallConfig config = LightCallConfig.create("https://jsonplaceholder.typicode.com")
            .addInterceptor(new LoggingInterceptor());
```