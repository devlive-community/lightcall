---
title: Error
---

LightCall 支持错误处理。

!!! info "提示"

    LightCall 支持多个错误处理规则，用户可以通过 `addErrorHandler` 方法添加错误处理规则。

!!!

## 用法

---

!!! info "提示"

    只需要在初始化配置的时候使用 `addErrorHandler` 方法添加错误处理规则即可

!!!

```java
LightCallConfig config = LightCallConfig.create("https://jsonplaceholder.typicode.com")
            .addErrorHandler(new DefaultErrorHandler());
```

默认提供了

- DefaultErrorHandler