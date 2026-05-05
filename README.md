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

## 二次开发内容详解

为了避免项目看起来只是对原框架的简单复用，本项目在若依原有后台管理能力基础上，围绕“校园问答”业务场景完成了较完整的功能扩展。具体新增和改造内容如下。

### 1. 新增前台问答门户

原始 RuoYi-Vue 更偏向后台管理系统，本项目新增了独立的前台门户页面，提供面向普通用户的问答使用入口，主要包括：

- `portal/home`：前台首页，用于展示问题列表、热门问题和分类筛选结果
- `portal/question`：问题详情页，用于查看问题正文、回答列表和互动信息
- `portal/ask`：提问页，支持用户提交新的问题内容
- `portal/login`：前台登录页，与后台登录入口分离
- `portal/register`：前台注册页，支持普通用户注册
- `portal/me`：个人中心页，用于查看和维护个人资料、我的提问、我的回答、我的收藏
- `portal/layout`：前台门户整体布局页，用于组织导航、内容区和登录态切换

这一部分的意义在于：项目不再只是“管理员录入数据、管理员查看数据”的单端系统，而是形成了“普通用户产生内容，管理员管理内容”的完整业务闭环。

### 2. 新增问答核心业务模型

围绕问答平台场景，项目补充了原框架中不存在的业务实体，主要包括：

- `TzQuestion`：问题实体，保存问题标题、正文、分类、发布用户、浏览量、回答数、收藏数、审核状态等信息
- `TzAnswer`：回答实体，保存回答内容、所属问题、发布用户、点赞数、审核状态等信息
- `TzCategory`：问答分类实体，用于对问题进行分类管理
- `TzUserProfile`：用户档案实体，扩展普通用户的学院、年级、专业、简介等资料信息

同时，为了适配前台接口交互，又补充了多组数据传输对象：

- `PortalQuestionBody`：前台提交问题时使用
- `PortalAnswerBody`：前台提交回答时使用
- `PortalProfileBody`：前台修改个人资料时使用
- `PortalRegisterBody`：前台用户注册时使用
- `TzReviewBody`：后台审核问题或回答时使用

为了适配前台展示，又新增了多组视图对象：

- `PortalQuestionListVo`：前台问题列表展示对象
- `PortalQuestionDetailVo`：前台问题详情展示对象
- `PortalAnswerVo`：前台回答展示对象
- `PortalProfileVo`：前台用户资料展示对象
- `TzOverviewVo`：后台概览统计展示对象

这一部分体现的是：项目不仅增加了页面，还补齐了完整的数据结构设计。

### 3. 新增前台认证与社区接口

为支持普通用户在门户端完成注册、登录、提问、回答、收藏和查看个人内容，项目新增了面向前台的控制器与接口能力，主要包括：

- `PortalAuthController`
  - 处理前台用户注册
  - 处理前台用户登录
  - 处理前台身份认证相关逻辑
- `PortalCommunityController`
  - 获取问题列表
  - 获取热门问题列表
  - 获取问题详情
  - 提交问题
  - 提交回答
  - 收藏/取消收藏问题
  - 获取我的提问、我的回答、我的收藏
  - 获取和修改个人资料

与原始后台接口不同，这一部分接口是专门面向普通用户使用场景设计的，体现了项目从“管理系统”向“业务系统”的扩展。

### 4. 新增后台问答管理模块

为了让管理员可以对前台产生的数据进行管理和审核，项目在后台新增了专门的问答业务模块，主要包括：

- `TzCategoryController`
  - 问答分类的新增、编辑、删除、状态管理
- `TzQuestionController`
  - 问题列表查看
  - 问题状态审核
  - 问题详情查看
- `TzAnswerController`
  - 回答列表查看
  - 回答状态审核
  - 回答详情查看
- `TzUserProfileController`
  - 前台用户档案查看与维护
- `TzDashboardController`
  - 后台问答数据统计与概览展示

前端后台页面中也同步新增了对应模块：

- `tongzhou/category`：分类管理
- `tongzhou/question`：问题管理
- `tongzhou/answer`：回答管理
- `tongzhou/user`：用户档案管理
- `tongzhou/dashboard`：问答业务概览看板

这一部分体现的是：你不只是加了“前台发帖功能”，还补上了配套的后台管理能力。

### 5. 新增问答业务查询与统计能力

在数据访问层，项目新增了大量围绕问答场景的查询逻辑，支持：

- 按分类筛选问题
- 按关键词搜索问题
- 查询热门问题
- 查询问题详情及其回答列表
- 查询我的提问、我的回答、我的收藏
- 统计用户提问数、回答数、收藏数
- 统计后台总问题数、总回答数、总分类数
- 对问题浏览量、回答数、收藏数进行动态维护

这说明项目不仅做了增删改查页面，也实现了与业务相关的统计和联动逻辑。

### 6. 对原框架权限和路由进行了业务化改造

为了适配“前台门户 + 后台管理”的双端结构，项目对原有框架还做了路由和权限层面的改造，例如：

- 增加前台门户路由
- 区分前台公开页面与需要登录后访问的页面
- 在统一权限控制中兼容后台管理路径和前台门户路径
- 为新增后台问答模块补充菜单、权限标识和页面入口

这一部分体现的是：项目改动不仅停留在业务表和页面，还深入到了框架的访问控制流程中。

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
