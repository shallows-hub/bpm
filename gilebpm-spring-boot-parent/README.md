# agilebpm-base-spring-boot

## 项目介绍

本项目 为  AgileBPM 的SpringBoot 案例工程

AgileBPM 主工程 ：https://gitee.com/agile-bpm/agile-bpm-basic     ，源码都在该工程中！

该项目含一下模块

-  demo 案例业务模块
-  agilebpm-app-samples 案例 APP 程序入口

AgileBPM SpringBoot 的 Starter 工程在 https://gitee.com/agile-bpm/agilebpm-spring-boot-starters
 
 
服务整合，模块详细介绍请参考 https://agile-bpm.gitee.io/docs/bootstrap/integration.html#spring-boot-1-X-整合


#### 软件架构

目前基于spring boot 2x


#### 使用说明
- Clone Spring Boot 版本项目
```
https://gitee.com/agile-bpm/agilebpm-base-spring-boot.git
```
- 对根目录执行 maven 命令 `install` 安装相关依赖jar

- 引入 Maven Spring Boot 案例项目  `agilebpm-app-samples`
 
- 启动项目, 在 SamplesApplication.java 中执行 main 方法

- 访问 http://localhost:8080 即可体验 AgileBPM ！


#### 修改数据库链接 
配置文件位于：application.yml

使用主工程建表语句，创建数据库  https://gitee.com/agile-bpm/agile-bpm-basic/tree/master/_doc
或者复制DEMO 库 47.106.139.29  3306 demo/demo   angular库


#### 参与贡献

1. Fork 本项目
2. 新建 Feat_xxx 分支
3. 提交代码
4. 新建 Pull Request
