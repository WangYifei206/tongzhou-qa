<template>
  <div class="portal-shell">
    <header class="portal-header">
      <div class="brand" @click="$router.push('/portal')">
        <span class="brand-mark">同舟</span>
        <div>
          <h1>同舟问答</h1>
          <p>校园匿名经验平台</p>
        </div>
      </div>
      <nav class="portal-nav">
        <el-button type="text" @click="$router.push('/portal')">首页</el-button>
        <el-button type="text" @click="$router.push('/portal/ask')">发提问</el-button>
        <el-button type="text" @click="$router.push('/portal/me')">个人中心</el-button>
      </nav>
      <div class="portal-actions">
        <template v-if="token">
          <el-button size="mini" @click="handleLogout">退出</el-button>
        </template>
        <template v-else>
          <el-button size="mini" @click="$router.push('/portal/login')">登录</el-button>
          <el-button size="mini" type="primary" @click="$router.push('/portal/register')">注册</el-button>
        </template>
      </div>
    </header>
    <main class="portal-main">
      <router-view />
    </main>
  </div>
</template>

<script>
import { logout } from '@/api/login'
import { getToken, removeToken } from '@/utils/auth'

export default {
  name: 'PortalLayout',
  computed: {
    token() {
      return getToken()
    }
  },
  methods: {
    handleLogout() {
      logout(getToken()).finally(() => {
        removeToken()
        this.$router.push('/portal')
      })
    }
  }
}
</script>

<style scoped>
.portal-shell {
  min-height: 100vh;
  background:
    radial-gradient(circle at top left, rgba(37, 99, 235, 0.16), transparent 32%),
    radial-gradient(circle at top right, rgba(16, 185, 129, 0.14), transparent 28%),
    linear-gradient(180deg, #f8fafc 0%, #eef2ff 100%);
}

.portal-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 16px;
  padding: 18px 32px;
  background: rgba(255, 255, 255, 0.78);
  backdrop-filter: blur(12px);
  border-bottom: 1px solid rgba(148, 163, 184, 0.18);
  position: sticky;
  top: 0;
  z-index: 10;
}

.brand {
  display: flex;
  align-items: center;
  gap: 14px;
  cursor: pointer;
}

.brand-mark {
  width: 48px;
  height: 48px;
  border-radius: 16px;
  display: inline-flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, #2563eb, #0f766e);
  color: #fff;
  font-weight: 700;
}

.brand h1 {
  margin: 0;
  font-size: 22px;
}

.brand p {
  margin: 2px 0 0;
  color: #64748b;
  font-size: 12px;
}

.portal-main {
  max-width: 1200px;
  margin: 0 auto;
  padding: 28px 20px 48px;
}

@media (max-width: 768px) {
  .portal-header {
    flex-wrap: wrap;
    padding: 16px;
  }

  .portal-nav {
    width: 100%;
    display: flex;
    justify-content: flex-start;
  }
}
</style>
