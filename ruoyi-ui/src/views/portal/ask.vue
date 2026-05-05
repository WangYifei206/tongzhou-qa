<template>
  <el-card shadow="never">
    <div slot="header">发起匿名提问</div>
    <el-form ref="form" :model="form" label-width="100px">
      <el-form-item label="问题分类">
        <el-select v-model="form.categoryId" placeholder="请选择分类" style="width: 100%">
          <el-option v-for="item in categories" :key="item.categoryId" :label="item.categoryName" :value="item.categoryId" />
        </el-select>
      </el-form-item>
      <el-form-item label="问题标题">
        <el-input v-model="form.title" maxlength="120" show-word-limit placeholder="例如：大二开始准备考研晚不晚？" />
      </el-form-item>
      <el-form-item label="问题内容">
        <el-input v-model="form.content" type="textarea" :rows="8" placeholder="尽量把你的背景、困惑和想了解的信息写清楚，方便得到更有价值的回答" />
      </el-form-item>
      <el-form-item label="匿名发布">
        <el-switch v-model="form.isAnonymous" active-value="Y" inactive-value="N" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submit">提交审核</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>

<script>
import { createPortalQuestion, getPortalCategories } from '@/api/portal'

export default {
  name: 'PortalAsk',
  data() {
    return {
      categories: [],
      form: {
        categoryId: undefined,
        title: '',
        content: '',
        isAnonymous: 'Y'
      }
    }
  },
  created() {
    getPortalCategories().then(res => {
      this.categories = res.data || []
    })
  },
  methods: {
    submit() {
      if (!this.form.categoryId || !this.form.title || !this.form.content) {
        this.$message.warning('请把问题信息填写完整')
        return
      }
      createPortalQuestion(this.form).then(() => {
        this.$message.success('问题已提交，等待审核')
        this.$router.push('/portal/me')
      })
    }
  }
}
</script>
