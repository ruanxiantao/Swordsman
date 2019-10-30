# Swordsman

## 项目简介

```shell
# 人员构成
	# 此项目由军、鹏、龙、康、超 ... 等人员参与主体开发
	
# 项目简介
	# 此项目致力于搭建时下最热门的项目技术体系开发脚手架 :
		# SpringBoot 
		# SpringCloud 
		# Vue 
		# Docker
		# ... 
	
# 如果你想加入项目的开发团队，请加入 QQ群号:283806874

# 如果你想加入项目的技术水群，请加入 QQ群号:764340624

# 欢迎各位 Star Or Fork
```

## 项目目录

### 文档目录

| 序号 | 目录  | 简介               |
| ---- | ----- | ------------------ |
| 01   | doc   | 此项目所有所需文档 |
| 02   | sql   | 此项目所有所需SQL  |
| 03   | error | 此项目所有异常文档 |

### 代码目录

| 序号 | 目录                   | 简介               |
| ---- | ---------------------- | ------------------ |
| 01   | swordsman-parent       | 父工程             |
| 02   | swordsman-common       | 公共组件           |
| 03   | swordsman-gateway      | 路由、限流、鉴权   |
| 04   | swordsman-user         | 用户中心           |
| 05   | swordsman-pay          | 支付中心           |
| 06   | swordsman-consumers    | 消费中心           |
| 07   | swordsman-monitor      | 监控中心           |
| 08   | swordsman-job-executor | xxl-job 任务执行器 |
| 09   | swordsman-wechat       | 微信端脚手架       |

## 功能清单

```shell
# 基于 SpringCloud Alibaba Nacos 的服务配置中心

# 基于 SpringCloud Alibaba Sentinel 的服务降级、熔断

# 基于 Skywalking 的服务链路追踪

# 基于 SpringCloud Gateway 的网关、鉴权、限流中心

# 基于 SpringCloud Greewith 的微服务组件

# 基于 Security 的 RBAC 权限模型

# 基于 SpringBoot 2.1 的框架搭建

# 基于 SpringBoot Admin 的Java 服务监控中心

# 基于 SpringData 的 Jpa、MongoDB、ElasticSearch、Redis 功能一揽子
	# <https://github.com/whvcse/RedisUtil>
	
# 基于 JDK1.8 的Lambda + Stream 编程

# 基于 MySQL 5.7、MongoDB 4.0、ElasticSearch 6.7

# 基于 Activiti 的工作流

# 基于 Wx-Java 框架的微信端功能一揽子

# 基于 SpringBoot 的 Upload 文件上传下载功能一揽子
	# 支持阿里云OSS、七牛云...

# 基于 SpringBoot Swagger 的接口文档
	
# 基于 SpringBoot 的支付组件
	# 支持微信、支付宝、银联...

# 基于 XXL-JOB 的分布式定时任务功能一揽子

# 基于 WebSocket 的全双工通信功能一揽子

# 基于 Spring Social 实现 OAuth2 规范的三方登录功能一揽子

# 基于 JWT + Redis 的单点登录

# 基于 RabbitMQ 的消息队列、分布式事务解决方案

# 基于 Greylog 的日志中心

# 基于 Durid 的SQL监控功能

# 基于 SpringBoot Email 的Error邮件发送

# 基于 Lombok + Hutool 的工具类库

# 基于 Dokcer 的服务容器化

# 基于 Jenkins 的持续集成、持续部署

# 基于 Kubernetes 的容器管理、扩容等一揽子功能

# 基于 Vue 的前端页面搭建
```

## 更新日志

### v1.0

#### 开发情况

| 序号 | 功能                                                         | 完成情况 | 完成人 |
| ---- | ------------------------------------------------------------ | -------- | ------ |
| 01   | 项目初始化、ReadMe 的编写                                    | ✅        | 超     |
| 02   | 基于Docker 安装MySQL、MongoDB、ElasticSearch<br />Jenkins、RabbitMQ、Nacos、Sentinel、Redis 等基础服务 |          |        |
| 03   | Common 项目的v1.0 开发                                       | ✅        | 超     |
| 04   | 基于Swagger 的接口文档                                       | ✅        | 超     |
| 05   | 基于Durid 的SQL 监控中心                                     | ✅        | 超     |
| 06   | 基于Graylog 的日志中心                                       |          |        |
| 07   | 基于SpringBoot Admin 的Java 项目监控中心                     |          |        |
| 08   | 基于SpringCloud Alibaba 的服务配置中心                       |          |        |
| 09   | 基于SpringCloud Gateway 的路由、限流、鉴权                   |          |        |

| 序号 | 功能                                                | 开发情况 | 完成人 |
| ---- | --------------------------------------------------- | -------- | ------ |
| 10   | 基于SpringBoot Email 的错误警告通知邮件             |          |        |
| 11   | 基于SpringData 的Jpa 通用多功能CRUD一揽子           | ✅        | 超     |
| 12   | 基于SpringData 的ElasticSearch 通用多功能CRUD一揽子 | ✅        | 超     |
| 13   | 基于SpringData 的Redis 通用多功能CRUD一揽子         | ✅        | 超     |
| 14   | 基于SpringData 的Mongo 通用多功能CRUD一揽子         | ✅        | 超     |
| 15   | 基于MyBatisPlus 的通用多功能CRUD 一揽子             |          |        |
| 16   | 基于IJPay 的支付宝支付功能一揽子                    |          |        |
| 17   | 基于IJPay 的微信支付功能一揽子                      |          |        |
| 18   | 基于IJPay 的银联支付功能一揽子                      |          |        |

| 序号 | 功能                           | 开发情况 | 完成人 |
| ---- | ------------------------------ | -------- | ------ |
| 19   | 基于Security 的RBAC权限模型    | ✅        | 超     |
| 20   | 基于JWT + Redis 的单点登录     |          |        |
| 21   | 文件服务 - 阿里云              | ✅        | 超     |
| 22   | 文件服务 - 七牛云              |          |        |
| 23   | 文件服务 - FastDFS             |          |        |
| 24   | 基于JustAuth 的QQ认证登录      |          |        |
| 25   | 基于JustAuth 的微信认证登录    |          |        |
| 26   | 基于JustAuth 的Github 认证登录 |          |        |
| 27   | 基于JustAuth 的微博认证登录    |          |        |

| 序号 | 功能                                  | 开发情况 | 完成人 |
| ---- | ------------------------------------- | -------- | ------ |
| 28   | 基于RabbitMQ 的点对点消息             |          |        |
| 29   | 基于RabbtitMQ 的Topic 消息            |          |        |
| 30   | 基于RabbtiMQ 的延迟队列               |          |        |
| 31   | 基于RabbitMQ 的手动ACK 确保消息准确性 |          |        |
| 32   | 基于RabbitMQ 的分布式任务解决方案     |          |        |
| 33   | 基于RabbitMQ 的批量消息               |          |        |
| 34   | 基于RabbitMQ 的顺序消息               |          |        |
| 35   | 短信服务 - 阿里云短信                 | ✅        | 超     |
| 36   |                                       |          |        |

