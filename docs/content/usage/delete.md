---
title: Delete
---

LightCall 提供了 HTTP Delete 请求的支持。

!!! danger "注意"

    LightCall 需要在方法上添加 `@Delete` 注解来标识该方法是一个 HTTP DELETE 请求。并且添加 `@Delete` 注解的类必须是一个接口。

!!!

我们使用的模拟数据是，他的代码可以在 [这里](https://github.com/devlive-community/lightcall/blob/dev/src/test/java/org/devlive/lightcall/example/delete/DeleteService.java "DeleteService" "_blank") 查看。

```java
public interface DeleteService
{}
```

### 用法

在方法上添加 `@Delete` 注解，就可以实现 HTTP DELETE 请求了。

```java
@Put("/posts/{id}")
PostModel deletePost(@PathVariable("id") Long id);
```

该示例中的 `deletePost` 是一个 HTTP POST 请求，请求路径是 `/posts`。

### @Body

---

在方法上添加 `@Body` 注解，就可以实现 HTTP DELETE 请求的请求体了。

```java
@Post("/posts")
PostModel deletePost(@Body PostModel post);
```

该示例构建的请求体是 `PostModel` 对象，请求路径是 `/posts`。

!!! info "提示"

    `@Body` 支持自定义 MediaType，默认是 `application/json`。通过 `mediaType` 参数可以指定 MediaType。

!!!