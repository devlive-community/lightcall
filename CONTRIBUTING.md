# Contributing to LightCall

首先，感谢您考虑为 LightCall 项目做出贡献！👍 这是一个开源项目，它的发展离不开像您这样的贡献者。

## 目录

- [行为准则](#行为准则)
- [如何贡献](#如何贡献)
    - [报告 Bug](#报告-bug)
    - [提出新功能](#提出新功能)
    - [提交代码](#提交代码)
- [开发指南](#开发指南)
    - [开发环境设置](#开发环境设置)
    - [代码风格](#代码风格)
    - [提交消息规范](#提交消息规范)
    - [分支管理](#分支管理)
- [Pull Request 指南](#pull-request-指南)
- [版本发布流程](#版本发布流程)

## 行为准则

本项目采用 [Contributor Covenant](https://www.contributor-covenant.org/version/2/0/code_of_conduct/) 行为准则。我们希望所有参与者都能遵守这些准则，共同创造一个友好的社区环境。

## 如何贡献

### 报告 Bug

如果您发现了 bug，请在我们的 [Issue Tracker](https://github.com/devlive-community/lightcall/issues) 创建一个新的 issue。为了帮助我们更好地理解和解决问题，请：

1. 使用清晰的标题描述问题
2. 详细描述复现步骤
3. 描述预期行为和实际行为
4. 提供环境信息（操作系统、JDK 版本等）
5. 如果可能，提供相关的日志或截图

### 提出新功能

如果您有好的想法要与我们分享，请：

1. 先检查 [Issue Tracker](https://github.com/devlive-community/lightcall/issues) 确保这个想法还没有被提出
2. 创建一个新的 issue，标记为 "enhancement"
3. 描述这个功能可以解决什么问题
4. 如果可能，提供一些实现建议

### 提交代码

1. Fork 项目仓库
2. 创建您的特性分支 (`git checkout -b feature/amazing-feature`)
3. 提交您的改动 (`git commit -m 'Add some amazing feature'`)
4. 推送到分支 (`git push origin feature/amazing-feature`)
5. 创建一个 Pull Request

## 开发指南

### 开发环境设置

1. 克隆项目：
```bash
git clone https://github.com/devlive-community/lightcall.git
cd lightcall
```

2. 安装依赖：
```bash
./mvnw clean install
```

3. 运行测试：
```bash
./mvnw test
```

### 代码风格

我们使用 Google Java Style Guide 作为代码风格指南。请确保：

1. 使用 UTF-8 编码
2. 使用 4 个空格缩进（不要使用 tab）
3. 遵循驼峰命名规则
4. 添加适当的注释和文档

您可以使用以下命令检查代码风格：
```bash
./mvnw checkstyle:check
```

### 提交消息规范

我们使用 [Conventional Commits](https://www.conventionalcommits.org/) 规范。每个提交消息应该包含以下结构：

```
<type>(<scope>): <description>

[optional body]

[optional footer]
```

类型（type）包括：
- feat: 新功能
- fix: 修复 bug
- docs: 文档更新
- style: 代码风格调整
- refactor: 代码重构
- test: 测试相关
- chore: 构建过程或辅助工具的变动

### 分支管理

- main: 主分支，用于发布
- develop: 开发分支，所有特性分支都从这里分出
- feature/*: 特性分支
- bugfix/*: Bug 修复分支
- release/*: 发布分支

## Pull Request 指南

1. 确保 PR 描述清晰地说明了改动的内容和原因
2. 确保所有自动化检查都通过
3. 如果适用，添加或更新测试用例
4. 更新相关文档
5. 如果 PR 解决了某个 issue，请在描述中引用该 issue

## 版本发布流程

1. 从 develop 分支创建 release 分支
2. 更新版本号和 CHANGELOG
3. 进行必要的测试和修复
4. 合并到 main 分支并打标签
5. 发布到 Maven 中央仓库

## 提示

- 在开始大量工作之前，最好先创建一个 issue 讨论您的想法
- 保持 PR 的小而精，这样更容易审查
- 编写清晰的提交消息，帮助他人理解您的改动
- 及时响应 review 意见

再次感谢您的贡献！如果您有任何问题，欢迎在 [Discussions](https://github.com/devlive-community/lightcall/discussions) 中提出。