<div align=center>
    <img src="https://picmwz.oss-cn-hangzhou.aliyuncs.com/logo%E9%85%8D%E8%89%B2%E5%9B%BE.png" width="275"  alt="KaiBai"/>
</div>

#### 项目介绍

KaiBai 是一个基于 Spring Cloud + React 开发的弹幕视频网站，出于兴趣和学习的目的开发了此项目，后续仍会更新。

- 后端 RESTful 风格的 API 设计。
- 支持视频投稿，播放，发送弹幕，点赞收藏，转发，评论等
- 支持关注用户，收藏视频，获取关注动态等
- 支持视频排行功能，对播放量进行统计
- 基于 JWT + Redis 实现登录鉴权及身份认证。
- 基于 RBAC 实现的权限校验
- 基于 ElaticSearch + kibana 实现的全局搜索
- 基于 Jenkins + Docker 实现的自动化部署

#### 项目结构

```
kb -- 父工程
├── kb-common -- 通用模块
├── kb-file -- 基于 Aliyun OSS 构建的文件微服务
├── kb-gateway -- 基于 Spring Cloud Gateway 构建的网关服务
├── kb-oauth -- 基于 Spring Cloud Oauth 构建的认证服务
├── kb-search -- 基于 Elasticsearch 构建的搜索微服务
├── kb-user -- 用户中心微服务
└── kb-video -- 视频中心微服务
```

#### 技术选型

| 技术                 | 说明                      |
| -------------------- | ------------------------- |
| Spring Boot          | Spring 应用快速开发脚手架 |
| Spring Cloud         | 微服务架构解决方案        |
| Nacos                | 注册中心和配置中心        |
| Sentinel             | 限流/熔断/降级            |
| Spring Cloud Gateway | 微服务网关                |
| MyBatis              | 持久层 ORM 框架           |
| PageHelper           | MyBatis 分页插件          |
| JJWT                 | JWT 登录支持              |
| Redis Cluster        | Redis集群方案             |
| Elasticsearch        | 搜索引擎                  |
| RocketMQ             | 消息队列                  |
| Aliyun OSS           | 阿里云对象存储服务        |
| MySQL                | 数据库服务                |
| Lombok               | 简化对象封装工具          |
| Swagger              | API 文档生成工具          |
| Jenkins              | 持续集成工具              |
| Docker               | 应用容器引擎              |
| Jave                 | 视频处理工具              |



