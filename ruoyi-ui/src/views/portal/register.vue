<template>
  <div class="auth-page">
    <el-card class="auth-card">
      <h2>校园用户注册</h2>
      <el-form :model="form" label-width="72px">
        <el-form-item label="用户名">
          <el-input v-model="form.username" />
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="form.password" type="password" />
        </el-form-item>
        <el-form-item label="昵称">
          <el-input v-model="form.nickName" />
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input v-model="form.email" />
        </el-form-item>
        <el-form-item label="手机">
          <el-input v-model="form.phonenumber" />
        </el-form-item>
        <el-form-item label="学院">
          <el-input v-model="form.college" />
        </el-form-item>
        <el-form-item label="年级">
          <el-input v-model="form.grade" />
        </el-form-item>
        <el-form-item label="专业">
          <el-input v-model="form.major" />
        </el-form-item>
        <el-form-item label="简介">
          <el-input v-model="form.introduction" type="textarea" :rows="3" />
        </el-form-item>
        <el-form-item v-if="captchaEnabled" label="验证码">
          <div class="captcha-row">
            <el-input v-model="form.code" />
            <img :src="codeUrl" class="captcha-img" @click="getCode" />
          </div>
        </el-form-item>
        <el-button type="primary" style="width: 100%" @click="submit">提交注册</el-button>
      </el-form>
    </el-card>
  </div>
</template>

<script>
import { getCodeImg } from '@/api/login'
import { portalRegister } from '@/api/portal'

export default {
  name: 'PortalRegister',
  data() {
    return {
      captchaEnabled: true,
      codeUrl: '',
      form: {
        username: '',
        password: '',
        nickName: '',
        email: '',
        phonenumber: '',
        college: '',
        grade: '',
        major: '',
        introduction: '',
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
      portalRegister(this.form).then(() => {
        this.$message.success('注册成功，请登录')
        this.$router.push('/portal/login')
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
  width: 480px;
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
