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

### 返回值

返回值类型为 List<Post>。