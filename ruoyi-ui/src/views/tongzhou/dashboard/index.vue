<template>
  <div class="app-container">
    <el-row :gutter="16">
      <el-col :md="8" v-for="item in cards" :key="item.label">
        <el-card shadow="hover" class="stat-card">
          <div class="stat-label">{{ item.label }}</div>
          <div class="stat-value">{{ item.value }}</div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { getOverview } from '@/api/tongzhou/dashboard'

export default {
  name: 'TongzhouDashboard',
  data() {
    return {
      overview: {}
    }
  },
  computed: {
    cards() {
      return [
        { label: '问题总数', value: this.overview.questionCount || 0 },
        { label: '回答总数', value: this.overview.answerCount || 0 },
        { label: '分类总数', value: this.overview.categoryCount || 0 },
        { label: '待审问题', value: this.overview.pendingQuestionCount || 0 },
        { label: '待审回答', value: this.overview.pendingAnswerCount || 0 },
        { label: '收藏总数', value: this.overview.favoriteCount || 0 }
      ]
    }
  },
  created() {
    getOverview().then(res => {
      this.overview = res.data || {}
    })
  }
}
</script>

<style scoped>
.stat-card {
  margin-bottom: 16px;
}

.stat-label {
  color: #64748b;
}

.stat-value {
  font-size: 30px;
  font-weight: 700;
  margin-top: 12px;
}
</style>
