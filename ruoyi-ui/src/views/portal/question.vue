<template>
  <div v-loading="loading">
    <el-card shadow="never" class="detail-card" v-if="detail.questionId">
      <div class="detail-top">
        <div>
          <el-tag>{{ detail.categoryName }}</el-tag>
          <el-tag type="info">{{ detail.isAnonymous === 'Y' ? '匿名提问' : '实名提问' }}</el-tag>
        </div>
        <div class="toolbar">
          <el-button size="mini" :type="detail.favorited === 'Y' ? 'warning' : 'default'" @click="toggleFavorite">
            {{ detail.favorited === 'Y' ? '取消收藏' : '收藏问题' }}
          </el-button>
        </div>
      </div>
      <h2>{{ detail.title }}</h2>
      <div class="detail-meta">
        <span>{{ detail.isAnonymous === 'Y' ? '匿名发布' : (detail.publisherNickName || detail.publisherName) }}</span>
        <span>{{ parseTime(detail.createTime) }}</span>
        <span>{{ detail.answerCount }} 回答</span>
        <span>{{ detail.viewCount }} 浏览</span>
      </div>
      <div class="detail-content">{{ detail.content }}</div>
      <el-alert v-if="detail.status !== '1'" type="warning" :closable="false" :title="statusMap[detail.status] || '当前不可见'" style="margin-top: 16px;" />
      <el-alert v-if="detail.rejectReason" type="error" :closable="false" :title="`驳回原因：${detail.rejectReason}`" style="margin-top: 12px;" />
    </el-card>

    <el-card shadow="never" class="answer-editor">
      <div slot="header">写回答</div>
      <el-form>
        <el-form-item>
          <el-input v-model="answerForm.content" type="textarea" :rows="5" placeholder="分享你的真实经验，帮助后来者少走弯路" />
        </el-form-item>
        <el-form-item>
          <el-switch v-model="answerForm.isAnonymous" active-value="Y" inactive-value="N" active-text="匿名回答" />
        </el-form-item>
        <el-button type="primary" @click="submitAnswer">提交回答</el-button>
      </el-form>
    </el-card>

    <el-card shadow="never">
      <div slot="header">回答区</div>
      <div v-if="detail.answers && detail.answers.length">
        <div v-for="item in detail.answers" :key="item.answerId" class="answer-item">
          <div class="answer-meta">
            <strong>{{ item.isAnonymous === 'Y' ? '匿名回答者' : (item.publisherNickName || item.publisherName) }}</strong>
            <span>{{ parseTime(item.createTime) }}</span>
          </div>
          <div class="answer-content">{{ item.content }}</div>
        </div>
      </div>
      <el-empty v-else description="还没有回答，来做第一个分享经验的人" />
    </el-card>
  </div>
</template>

<script>
import { createPortalAnswer, favoriteQuestion, getPortalQuestionDetail, unfavoriteQuestion } from '@/api/portal'
import { getToken } from '@/utils/auth'
import { parseTime } from '@/utils/ruoyi'

export default {
  name: 'PortalQuestion',
  data() {
    return {
      loading: false,
      detail: {
        answers: []
      },
      answerForm: {
        content: '',
        isAnonymous: 'Y'
      },
      statusMap: {
        0: '待审核',
        1: '已通过',
        2: '已驳回',
        3: '已下架'
      }
    }
  },
  created() {
    this.fetchDetail()
  },
  methods: {
    parseTime,
    fetchDetail() {
      this.loading = true
      getPortalQuestionDetail(this.$route.params.id).then(res => {
        this.detail = res.data || { answers: [] }
      }).finally(() => {
        this.loading = false
      })
    },
    submitAnswer() {
      if (!getToken()) {
        this.$router.push(`/portal/login?redirect=${encodeURIComponent(this.$route.fullPath)}`)
        return
      }
      if (!this.answerForm.content) {
        this.$message.warning('请先填写回答内容')
        return
      }
      createPortalAnswer(this.detail.questionId, this.answerForm).then(() => {
        this.$message.success('回答已提交，等待审核')
        this.answerForm.content = ''
        this.fetchDetail()
      })
    },
    toggleFavorite() {
      if (!getToken()) {
        this.$router.push(`/portal/login?redirect=${encodeURIComponent(this.$route.fullPath)}`)
        return
      }
      const action = this.detail.favorited === 'Y' ? unfavoriteQuestion : favoriteQuestion
      action(this.detail.questionId).then(() => {
        this.fetchDetail()
      })
    }
  }
}
</script>

<style scoped>
.detail-card,
.answer-editor {
  margin-bottom: 16px;
}

.detail-top,
.detail-meta,
.answer-meta {
  display: flex;
  justify-content: space-between;
  gap: 12px;
  flex-wrap: wrap;
}

.detail-content,
.answer-content {
  margin-top: 16px;
  line-height: 1.9;
  white-space: pre-wrap;
}

.detail-meta {
  color: #64748b;
  margin-top: 12px;
}

.answer-item {
  padding: 16px 0;
  border-bottom: 1px solid #eef2f7;
}

.answer-item:last-child {
  border-bottom: none;
}
</style>
