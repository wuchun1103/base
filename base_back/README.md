# kkframe 基于springcloud搭建的通用微服务脚手架

### 项目介绍

- kkframe是基于Spring Boot、Spring Cloud、Vue、Element实现的Java快速开发平台。

- 目标是搭建出一套简洁易用的快速解决方案，可以帮助用户有效降低项目开发难度和成本。

- 个人博客提供本项目开发过程同步系列教程文章，手把手的教你如何开发同类系统。



### 技术交流

为了方便大家提问和技术交流，可以加我QQ，欢迎童鞋们加入。
QQ：416964490

### 博客教程 卡卡他大哥 博客园博客

本人技术博客提供同步系列文章教程, 讲解了kkframe从零开始搭建的全过程：

skywalking部署参数
-javaagent:F:\workspace\kkframe\skywalking\agent\skywalking-agent.jar
-Dskywalking.agent.service_name=kfmadmin-service
-Dskywalking.collector.backend_service=10.4.0.87:11800


jar包发布：
nohup java -javaagent:/usr/local/skywalking/agentskywalking-agent.jar -Dskywalking.agent.service_name=im-service -Dskywalking.collector.backend_service=10.10.10.35:11800 -jar zv-imservice.jar &
