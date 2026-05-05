# 通州问答系统

本项目基于 [RuoYi-Vue](https://gitee.com/y_project/RuoYi-Vue) 进行二次开发，遵循原项目 `MIT License` 开源协议。

## 项目简介

通州问答系统面向问答业务场景，提供后台管理端与前台门户端能力，支持问答内容管理、用户资料维护和业务运营管理。

## 二次开发内容

- 新增前台门户注册、登录、个人中心
- 新增问题发布、回答提交、问答详情展示
- 新增问题分类、回答管理、用户档案管理
- 新增通州业务相关后台菜单与统计面板
- 调整初始化 SQL、部署脚本和项目结构以适配当前业务

## 技术栈

- 后端：Spring Boot 3、Spring Security、MyBatis、Druid、Redis、JWT
- 前端：Vue 2、Element UI
- 数据库：MySQL

## 项目结构

```text
ruoyi-admin        后端启动模块
ruoyi-framework    框架核心模块
ruoyi-system       业务模块
ruoyi-common       公共模块
ruoyi-ui           前端项目
sql                数据库脚本
deploy             部署脚本
```

## 启动说明

1. 准备 JDK 17、MySQL、Redis、Node.js 环境
2. 根据本地环境修改数据库、Redis、token 等配置
3. 执行 `sql/` 目录下的初始化脚本
4. 启动后端服务
5. 启动前端项目

## 配置说明

公开仓库中的数据库连接、账号密码、部署参数和 token 密钥已替换为示例占位值。实际部署请在本地或服务器环境中自行填写。

## 开源声明

- 原始项目：RuoYi-Vue
- 原始仓库：https://gitee.com/y_project/RuoYi-Vue
- 原始许可证：MIT

本仓库保留原项目许可证和相关版权声明，仅展示在原框架基础上的课程/业务二次开发成果。
