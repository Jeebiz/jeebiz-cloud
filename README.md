## Jeebiz Cloud 简介：

> [danger] Jeebiz Cloud 是基于 Spring Boot、Spring Cloud 构建的企业级微服务快速开发脚手架；

- 1、继承 Spring Boot、Spring Cloud 的 各种特性

- 2、支持多种注册中心：Consul、Eureka、Kubernetes、Nacos、Zookeeper 等

- 3、支持多种配置中心：Git、Redis、Apollo、Nacos 等

- 4、支持服务灰度发布：集成 Nepxion Discovery 实现灰度发布

- 5、集成主流技术：Spring Cloud Alibaba、Spring Security、Swagger、Mybatis-Plus、Redis、HikariCP、RocketMQ

- 6、自带通用实现：服务网关、认证授权服务、人脸识别服务、文件存储服务、工作流服务、消息通知服务、安全审计服务、数据字典服务、代码生成服务等

- 7、服务快速构建：基于代码生成服务，可快速的搭建一个独立微服务

- 8、应用自动初始化：基于Flyway实现数据库脚本版本管理和脚本自动升级能力

- 9、脚本化部署：自动构建可运行的脚本化部署程序

- 10、Docker镜像支持：支持基于Dockerfile手动构建Docker镜像到Docker Harbor；借助开源软件开源软件 Jenkins + Harbor + Rancher ，实现基于Docker的运维支撑；

- 11、Edas 支持：支持阿里云Edas集群自动化；借助开源软件开源软件 Jenkins + Edas ，实现基于阿里云的 Devops

#### Jeebiz Cloud 架构：

![](https://wiki.hiwepy.com/uploads/jeebiz-cloud/images/m_a85f16d4ee49b8ee2658f53a57ee7aed_r.png)

https://spring-cloud-alibaba-group.github.io/github-pages/hoxton/zh-cn/index.html

#### Jeebiz Cloud 说明：

1. 基础服务已经开发完成，需要不断的扩展有吸引力的增值服务模块；

2. 需要实现基于不同注册中心（consul、eureka、kubernetes、nacos、zookeeper）的整合

3. 前端界面（PC+移动）已经有初步模型

4. 计划模块

- [x] jeebiz-cloud-api	：与业务无关的通用对象、接口、工具
- [ ] jeebiz-cloud-authz-acl	：Spring Security ACL 实现整合
- [x] jeebiz-cloud-authz-dbperms：认证授权服务
- [x] jeebiz-cloud-authz-facex		：人脸识别
- [x] jeebiz-cloud-authz-facex-baidu	：基于百度SDK的人脸识别
- [x] jeebiz-cloud-authz-facex-arcsoft	：基于百度SDK的人脸识别
- [x] jeebiz-cloud-authz-feature	：功能菜单
- [x] jeebiz-cloud-authz-login	：登录策略
- [x] jeebiz-cloud-authz-org		：组织机构
- [x] jeebiz-cloud-authz-passwd	：密码找回功能实现
- [x] jeebiz-cloud-authz-rbac0		：认证授权服务RBAC0实现
- [x] jeebiz-cloud-authz-rbac1		：认证授权服务RBAC1实现
- [ ] jeebiz-cloud-authz-rbac2		：认证授权服务RBAC2实现
- [ ] jeebiz-cloud-authz-rbac3		：认证授权服务RBAC3实现
- [ ] jeebiz-cloud-authz-rbacx		：认证授权服务RBAC综合实现
- [x] jeebiz-cloud-base-device		：设备日志
- [x] jeebiz-cloud-base-dict		：数据字典
- [ ] jeebiz-cloud-base-guard		：安全加固
- [x] jeebiz-cloud-base-editor		：编辑器对接
- [x] jeebiz-cloud-base-logs		：安全审计：授权日志、操作日志
- [x] jeebiz-cloud-base-mqflow		：消息流水
- [x] jeebiz-cloud-base-quartz		：定时任务
- [ ] jeebiz-cloud-base-sessions	：会话管理
- [ ] jeebiz-cloud-base-task		：任务管理
- [ ] jeebiz-cloud-cache-redis		：缓存适配模块
- [x] jeebiz-cloud-message-core		：消息通知（核心对象）
- [x] jeebiz-cloud-message-dingtalk		：消息通知（钉钉）
- [x] jeebiz-cloud-message-goeasy		：消息通知（GoEasy）
- [x] jeebiz-cloud-message-jpush		：消息通知（极光）
- [x] jeebiz-cloud-message-weixin		：消息通知（微信）
- [x] jeebiz-cloud-message-email		：消息通知（邮件） 
- [x] jeebiz-cloud-oss-core	：文件存储（核心对象）
- [x] jeebiz-cloud-oss-flow	：文件存储（流水记录）
- [x] jeebiz-cloud-oss-aliyun	：文件存储（阿里云）
- [x] jeebiz-cloud-oss-fastdfs	：文件存储（FastDFS）
- [x] jeebiz-cloud-oss-minio	：文件存储（MinIO）
- [x] jeebiz-cloud-oss-sftp	：文件存储（FTP 共享）
- [x] jeebiz-cloud-oss-samba	：文件存储（Samba 共享）
- [x] jeebiz-cloud-plugin-api		：插件接口API
- [x] jeebiz-cloud-sms-core		：短信发送（核心对象）
- [x] jeebiz-cloud-sms-flow		：短信发送（流水记录）
- [x] jeebiz-cloud-sms-aliyun		：短信发送（阿里云）
- [x] jeebiz-cloud-sms-txcloud		：短信发送（腾讯云）
- [x] jeebiz-cloud-feign-autoconfigure：Feign自动配置
- [x] jeebiz-cloud-webflux-autoconfigure：WebFlux 默认扩展自动配置
- [x] jeebiz-cloud-webmvc-autoconfigure：WebWec 默认扩展自动配置