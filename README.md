# KaiBai 后端项目

## 项目简介
KaiBai 是一个基于 Spring Cloud 的弹幕视频平台后端项目，采用微服务架构实现用户、视频、搜索、认证、网关等核心能力。

## 核心能力
- 提供 RESTful API。
- 支持视频上传、播放、弹幕、评论、点赞、收藏、排行榜。
- 支持用户关注与动态订阅。
- 基于 OAuth2 + JWT + Redis 的认证方案。
- 基于 Elasticsearch 的检索能力。

## 项目结构
```text
kb-BE
├─ kb-common    公共组件
├─ kb-file      文件与评论服务
├─ kb-gateway   网关服务
├─ kb-oauth     认证服务
├─ kb-search    搜索服务
├─ kb-user      用户服务
└─ kb-video     视频服务
```

## 技术栈
- Spring Boot
- Spring Cloud
- Nacos
- Redis
- MyBatis / MyBatis-Plus
- Elasticsearch
- RocketMQ
- Docker
- Jenkins

## 构建与运行
```bash
mvn clean install -DskipTests
```

按模块启动时，可进入对应目录执行：
```bash
mvn spring-boot:run
```

## 维护记录
### 2026-04-24
- 清理部分历史注释与冲突标记。
- 调整文档与构建脚本结构。
