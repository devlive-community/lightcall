---
title: 开始使用
---

LightCall 是一个简单、直观且功能强大的服务调用框架。通过声明式的方式，让开发者专注于业务逻辑而不是底层的 HTTP 调用细节。

我们可以通过下面的步骤来快速上手：

## 添加依赖

---

!!! info "提示"

    这里我们以 Maven 为例，您可以根据自己的需求选择不同的依赖管理工具

!!!

```xml
<dependency>
    <groupId>org.devlive.lightcall</groupId>
    <artifactId>lightcall</artifactId>
    <version>${latest.version}</version>
</dependency>
```

## 定义接口

---

```java
public interface PostService
{
    @Get("/posts")
    List<Post> getPosts();
}
```

## 初始化并使用

---

```java
private final LightCallConfig config = LightCallConfig.create("http://mockaroo.devlive.org");
private final PostService service = LightCall.create(PostService.class, config);

List<Post> posts = service.getPosts();
```