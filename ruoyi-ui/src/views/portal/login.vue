<template>
  <div class="auth-page">
    <el-card class="auth-card">
      <h2>校园用户登录</h2>
      <el-form :model="form">
        <el-form-item>
          <el-input v-model="form.username" placeholder="用户名" />
        </el-form-item>
        <el-form-item>
          <el-input v-model="form.password" type="password" placeholder="密码" @keyup.enter.native="submit" />
        </el-form-item>
        <el-form-item v-if="captchaEnabled">
          <div class="captcha-row">
            <el-input v-model="form.code" placeholder="验证码" />
            <img :src="codeUrl" class="captcha-img" @click="getCode" />
          </div>
        </el-form-item>
        <el-button type="primary" style="width: 100%" @click="submit">登录</el-button>
        <el-button type="text" style="width: 100%; margin-top: 8px;" @click="$router.push('/portal/register')">没有账号，去注册</el-button>
      </el-form>
    </el-card>
  </div>
</template>

<script>
import { getCodeImg } from '@/api/login'
import { portalLogin } from '@/api/portal'
import { setToken } from '@/utils/auth'

export default {
  name: 'PortalLogin',
  data() {
    return {
      captchaEnabled: true,
      codeUrl: '',
      form: {
        username: '',
        password: '',
        code: '',
        uuid: ''
      }
    }
  },
  created() {
    this.getCode()
  },
  methods: {
    getCode() {
      getCodeImg().then(res => {
        this.captchaEnabled = res.captchaEnabled !== false
        if (this.captchaEnabled) {
          this.codeUrl = 'data:image/gif;base64,' + res.img
          this.form.uuid = res.uuid
        }
      })
    },
    submit() {
      portalLogin(this.form).then(res => {
        setToken(res.token)
        this.$router.push(this.$route.query.redirect || '/portal')
      })
    }
  }
}
</script>

<style scoped>
.auth-page {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
}

.auth-card {
  width: 420px;
  max-width: calc(100vw - 32px);
}

.captcha-row {
  display: grid;
  grid-template-columns: 1fr 120px;
  gap: 12px;
}

.captcha-img {
  width: 120px;
  height: 40px;
  border-radius: 6px;
  cursor: pointer;
}
</style>
