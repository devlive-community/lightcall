---
title: Header
---

LightCall 提供了 HTTP Header 的支持。

!!! danger "注意"

    LightCall 支持多种 Header 的传递方式，包括 `@Header` 和 `@Headers` 两种。

!!!

### 用法

```java
@Get("/posts")
List<Post> getPosts(
    @Header("Authorization") String authorization
);
```

该示例中的 `getPosts` 是一个 HTTP GET 请求，请求路径是 `/posts`，请求头是 `Authorization`。

### `@Header`

---

!!! info "注意"

    `@Header` 注解只能在参数上使用。

!!!

```java
@Get("/posts")
List<Post> getPosts(
    @Header("Authorization") String authorization
)
```

该示例中的 `getPosts` 是一个 HTTP GET 请求，请求路径是 `/posts`，请求头是 `Authorization`。系统会自动将 `authorization` 参数的值传递到请求头中。

### `@Headers`

---

!!! info "注意"

    `@Headers` 注解只能在方法上使用。

!!!

```java
@Get("/posts/{id}")
@Headers({
        "Accept: application/json",
        "User-Agent: LightCall/1.0"
})
Post getPostHeaderAndParam(
        @PathVariable("id") Long id,
        @Header("x-api-key") String apiKey,
        @RequestParam("title") String title
);
```

该示例中的 `getPostHeaderAndParam` 是一个 HTTP GET 请求，请求路径是 `/posts/{id}`，请求头是 `Accept: application/json`，`User-Agent: LightCall/1.0`，请求参数是 `title`。构建后的请求头是 `Accept: application/json`，`User-Agent: LightCall/1.0`，`x-api-key: apiKey`。