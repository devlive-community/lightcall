---
title: Part
---

LightCall 提供了文件上传的支持。

!!! danger "注意"

    LightCall 需要在参数上添加 `@Part` 注解来标识该参数是一个文件上传请求。并且添加 `@Part` 的类必须是一个接口。

!!!

我们使用的模拟数据是，他的代码可以在 [这里](https://github.com/devlive-community/lightcall/blob/dev/src/test/java/org/devlive/lightcall/example/part/PartService.java "PartService" "_blank") 查看。

```java
public interface PartService
{}
```

### 用法

在参数上添加 `@Part` 注解，就可以实现文件上传请求了。

```java
@Post("/upload")
String uploadFile(@Part("file") File file);
```

该示例中的 `uploadFile` 是一个文件上传请求，参数 `file` 表示要上传的文件，`"file"` 是文件参数的名称。请求路径是 `/upload`。

### 文件类型

---

`@Part` 注解支持 `java.io.File` 类型的参数，用于上传本地文件。

```java
@Post("/upload")
String apply(@Part("image") File image);
```

该示例中的 `apply` 方法用于上传图片文件，参数 `image` 表示要上传的图片文件，请求路径是 `/upload`。

!!! info "提示"

    文件上传请求会自动设置 `Content-Type` 为 `multipart/form-data`，并将文件作为表单的一部分上传。

!!!

### `mime` 类型

---

`@Part` 注解支持 `mime` 参数，用于指定上传文件的 MIME 类型，默认值为 `application/octet-stream`。

```java
@Post("/upload")
String apply(@Part(value = "image", mime = "image/jpeg") File image);
```

该示例中的 `apply` 方法用于上传图片文件，参数 `image` 表示要上传的图片文件，请求路径是 `/upload`，文件的 MIME 类型是 `image/jpeg`。