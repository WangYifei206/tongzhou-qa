<template>
  <div class="app-container home">
    <el-row :gutter="20">
      <el-col :xs="24" :lg="16">
        <el-card class="hero-card" shadow="never">
          <div class="hero-badge">Tongzhou QA Admin</div>
          <h1>同舟问答后台管理</h1>
          <p class="hero-text">
            这是同舟问答的管理后台，用于维护分类、审核问题与回答、查看用户数据，以及保障校园问答内容有序运行。
          </p>
          <div class="hero-meta">
            <span>当前版本 v{{ version }}</span>
            <span>运行环境 Spring Boot + Vue</span>
          </div>
        </el-card>
      </el-col>
      <el-col :xs="24" :lg="8">
        <el-card class="quick-card" shadow="never">
          <div slot="header">
            <span>使用建议</span>
          </div>
          <ul class="plain-list">
            <li>先维护“分类管理”，保证前台提问可正确归类。</li>
            <li>重点关注“问题审核”和“回答审核”，避免低质量内容直接展示。</li>
            <li>定期查看“用户管理”和“通知公告”，保持平台信息同步。</li>
          </ul>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="20" class="section-row">
      <el-col :xs="24" :md="12" :lg="8">
        <el-card class="info-card" shadow="hover">
          <div slot="header">
            <span>核心功能</span>
          </div>
          <ul class="plain-list">
            <li>分类管理：维护问答场景与展示顺序。</li>
            <li>问题审核：控制问题发布状态与驳回原因。</li>
            <li>回答审核：管理回答内容质量与展示权限。</li>
            <li>用户管理：查看普通用户与管理员信息。</li>
          </ul>
        </el-card>
      </el-col>
      <el-col :xs="24" :md="12" :lg="8">
        <el-card class="info-card" shadow="hover">
          <div slot="header">
            <span>前台入口</span>
          </div>
          <ul class="plain-list">
            <li>门户首页：`/portal`</li>
            <li>用户登录：`/portal/login`</li>
            <li>用户注册：`/portal/register`</li>
            <li>管理登录：`/login`</li>
          </ul>
        </el-card>
      </el-col>
      <el-col :xs="24" :md="24" :lg="8">
        <el-card class="info-card" shadow="hover">
          <div slot="header">
            <span>部署检查</span>
          </div>
          <ul class="plain-list">
            <li>后端服务：确认 `tongzhou.service` 为运行状态。</li>
            <li>前端静态资源：确认 nginx 已加载最新 `dist` 文件。</li>
            <li>数据库连接：确认 `ruoyi / 123456.` 可连接 `ry_vue`。</li>
            <li>外网访问：确认服务器安全组已放行 80 端口。</li>
          </ul>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  name: 'Index',
  data() {
    return {
      version: '3.9.2'
    }
  }
}
</script>

<style scoped lang="scss">
.home {
  color: #303133;
}

.hero-card,
.quick-card,
.info-card {
  border-radius: 16px;
  border: 1px solid #ebeef5;
}

.hero-card {
  min-height: 220px;
  background: linear-gradient(135deg, #f5f7fa 0%, #ecf5ff 100%);
}

.hero-badge {
  display: inline-block;
  padding: 6px 12px;
  border-radius: 999px;
  background: #1f6feb;
  color: #fff;
  font-size: 12px;
  letter-spacing: 0.5px;
}

h1 {
  margin: 18px 0 12px;
  font-size: 30px;
  font-weight: 700;
  color: #1f2d3d;
}

.hero-text {
  margin: 0;
  line-height: 1.8;
  color: #606266;
  font-size: 15px;
}

.hero-meta {
  display: flex;
  flex-wrap: wrap;
  gap: 12px;
  margin-top: 20px;
  color: #409eff;
  font-size: 14px;
}

.section-row {
  margin-top: 20px;
}

.plain-list {
  margin: 0;
  padding-left: 18px;
  line-height: 2;
  color: #606266;
}
</style>
