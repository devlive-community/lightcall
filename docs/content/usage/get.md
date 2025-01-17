---
title: Get
---

LightCall 提供了 HTTP GET 请求的支持。

!!! danger "注意"

    LightCall 需要在方法上添加 `@Get` 注解来标识该方法是一个 HTTP GET 请求。并且添加 `@Get` 注解的类必须是一个接口。

!!!

我们使用的模拟数据是，他的代码可以在 [这里](https://github.com/devliveorg/lightcall/blob/dev/src/test/java/org/devlive/lightcall/example/PostService.java "PostService" "_blank") 查看。

```java
public interface PostService
{}
```

### 用法

```java

@Get("/posts")
List<Post> getPosts();
```

该示例中的 `getPosts` 是一个 HTTP GET 请求，请求路径是 `/posts`。

### `@RequestParam`

---

!!! info "提示"

    你可以使用 `@RequestParam` 注解标记在请求中的参数。

!!!

```java
@Get("/posts")
List<Post> getPosts(
    @RequestParam("page") int page,
    @RequestParam("size") int size
);
```

该示例中的 `page` 和 `size` 是请求参数。系统会将路径构建为

```
/posts?page=1&size=10
```

### `@PathVariable`

---

!!! info "提示"

    你可以使用 `@PathVariable` 注解标记在路径中的参数。

!!!

```java
@Get("/posts/{id}")
Post getPost(@PathVariable("id") Long id);
```

该示例中的 `id` 是路径参数。系统会将路径构建为

```
/posts/1
```

!!! danger "注意"

    如果需要传递 HTTP Header，需要使用 `@Header` 或 `@Headers` 注解。请参考 [Header](/usage/header.html)。

!!!