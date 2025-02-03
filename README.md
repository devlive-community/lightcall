<div align="center">

<img width=180 src="docs/assets/logo.svg" />

LightCall 是一个轻量级的声明式服务调用框架，让服务调用像写接口一样简单。通过简洁的注解方式，帮助开发者以最小的代价实现优雅的服务访问。

</div>

## 特性

- 🚀 极简API：仅需几个注解即可完成服务调用
- 🎯 类型安全：完整的泛型支持，编译时类型检查
- 🔌 可扩展：支持自定义注解处理器和拦截器
- 🛠 配置灵活：支持全局配置和接口级别配置
- 💡 智能处理：自动处理请求参数、响应序列化等
- 🎨 优雅设计：遵循 Java 设计规范，无侵入性

## 快速开始

### Maven 依赖

```xml
<dependency>
    <groupId>org.devlive.lightcall</groupId>
    <artifactId>lightcall</artifactId>
    <version>${latest.version}</version>
</dependency>
```

## 设计理念

LightCall 的设计目标是提供一个简单、直观且功能强大的服务调用框架。通过声明式的方式，让开发者专注于业务逻辑而不是底层的 HTTP 调用细节。

主要设计原则：
1. 约定优于配置
2. 最小惊讶原则
3. 可扩展性优先
4. 开发体验至上

## 

## 贡献指南

我们欢迎任何形式的贡献，包括但不限于：

- 提交问题和建议
- 改进文档
- 提交代码改进
- 分享使用经验

请参考我们的[贡献指南](CONTRIBUTING.md)了解详细信息。

## 开源协议

本项目采用 [MIT 协议](LICENSE)。

## 致谢

感谢所有为这个项目做出贡献的开发者。特别感谢 Retrofit 项目给予的灵感。