# 通州问答系统

一个基于前后端分离架构实现的校园问答平台项目，包含面向普通用户的问答门户和面向管理员的后台管理端。项目围绕提问、回答、分类管理、用户资料维护和内容审核等典型场景展开，适合作为 Java Web 全栈方向的课程项目与个人项目展示。

## 项目简介

该项目主要分为两个部分：

- 前台问答门户：面向普通用户提供注册登录、问题浏览、问题发布、回答提交、收藏问题、个人中心等功能
- 后台管理平台：面向管理员提供问题管理、回答管理、分类管理、用户档案管理和数据概览等功能

## 技术栈

### 后端

- Java 17
- Spring Boot 3.5
- Spring Security
- MyBatis
- Druid
- MySQL
- Redis
- JWT
- Maven

### 前端

- Vue 2
- Vue Router
- Vuex
- Element UI
- Axios
- ECharts

### 部署与运行

- Nginx
- Linux Systemd
- Shell Script

## 核心功能

### 前台问答门户

- 支持用户注册、登录和门户访问鉴权
- 支持问题列表浏览、热门问题展示和问题详情查看
- 支持按分类筛选问题与关键词搜索问题
- 支持用户发布问题、提交回答和匿名发布
- 支持收藏问题、查看我的提问和我的回答
- 支持个人资料维护与用户档案展示

### 后台管理平台

- 支持管理员登录和后台权限控制
- 支持问答分类的新增、修改、删除和状态管理
- 支持问题内容审核、查看、筛选和统计
- 支持回答内容审核、查看、筛选和统计
- 支持用户档案管理与业务菜单扩展
- 支持问答数据概览面板展示

## 项目亮点

- 基于 RuoYi-Vue 开源框架进行二次开发，保留原始许可证并结合具体业务完成定制化实现
- 在原有后台管理体系上扩展了前台问答门户，形成“用户端 + 管理端”双端业务结构
- 围绕问答场景补充了问题、回答、分类、收藏、个人资料等业务模型及对应接口
- 通过 Spring Security + JWT 实现前后台统一认证与访问控制
- 使用 MyBatis 完成问答业务数据访问，支持列表、详情、统计与审核等常见场景
- 提供 SQL 初始化脚本和部署脚本，方便本地演示与服务器部署

## 项目结构

```text
tongzhou-qa/
  ruoyi-admin/       后端启动模块
  ruoyi-framework/   框架核心模块
  ruoyi-system/      系统与问答业务模块
  ruoyi-common/      公共工具模块
  ruoyi-ui/          前端项目
  sql/               数据库初始化脚本
  deploy/            部署脚本
```

## 快速启动

### 1. 启动后端

先准备本地环境：

- JDK 17
- MySQL 8.x
- Redis

根据你的本地环境修改配置文件：

- `ruoyi-admin/src/main/resources/application.yml`
- `ruoyi-admin/src/main/resources/application-druid.yml`

建议至少配置以下内容：

- 数据库连接地址
- 数据库用户名和密码
- Redis 地址和端口
- JWT 密钥

执行数据库脚本：

```bash
mysql -u your_username -p your_database < sql/ry_20260417.sql
mysql -u your_username -p your_database < sql/tongzhou_qa.sql
```

启动后端：

```bash
mvn clean package
mvn --pl ruoyi-admin spring-boot:run
```

后端默认地址：

- `http://localhost:8080`

### 2. 启动前端

```bash
cd ruoyi-ui
npm install
npm run dev
```

前端开发环境默认地址：

- `http://localhost:80`

如果需要打包前端：

```bash
cd ruoyi-ui
npm run build:prod
```

## 构建与部署

### 后端打包

```bash
mvn clean package -DskipTests
```

### 前端打包

```bash
cd ruoyi-ui
npm run build:prod
```

### Linux 部署参考

仓库中提供了部署相关文件，可按需参考：

- `deploy/systemd/start.sh`
- `deploy/systemd/tongzhou.service`
- `deploy/nginx/tongzhou.conf`

## 开源说明

本项目基于 [RuoYi-Vue](https://gitee.com/y_project/RuoYi-Vue) 二次开发，遵循原项目 `MIT License` 开源协议。

- 原始项目：RuoYi-Vue
- 原始仓库：https://gitee.com/y_project/RuoYi-Vue
- 原始许可证：MIT

本仓库保留原项目许可证和相关版权声明，重点展示在原框架基础上的业务二次开发成果。
