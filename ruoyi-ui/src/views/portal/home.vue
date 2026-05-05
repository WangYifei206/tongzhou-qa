<template>
  <div>
    <section class="hero">
      <div>
        <span class="hero-badge">打破校园信息差</span>
        <h2>有问题，不用再硬着头皮问熟人。</h2>
        <p>匿名提问、分类沉淀、学长学姐经验可追踪留存，把“人脉优势”变成每个人都能用的产品能力。</p>
      </div>
      <div class="hero-actions">
        <el-button type="primary" @click="$router.push('/portal/ask')">立即提问</el-button>
        <el-button @click="$router.push('/portal/me')">查看我的内容</el-button>
      </div>
    </section>

    <el-card shadow="never" class="filter-card">
      <el-form :inline="true" size="small">
        <el-form-item>
          <el-input v-model="query.keyword" placeholder="搜索考研、实习、选课、竞赛..." clearable @keyup.enter.native="fetchQuestions" />
        </el-form-item>
        <el-form-item>
          <el-select v-model="query.categoryId" clearable placeholder="全部分类" style="width: 180px">
            <el-option v-for="item in categories" :key="item.categoryId" :label="item.categoryName" :value="item.categoryId" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="fetchQuestions">筛选</el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <section class="content-grid">
      <div class="main-column">
        <el-card v-for="item in questions" :key="item.questionId" shadow="hover" class="question-card">
          <div class="question-head">
            <div>
              <el-tag size="mini">{{ item.categoryName }}</el-tag>
              <el-tag size="mini" type="info">{{ statusMap[item.status] || '已发布' }}</el-tag>
            </div>
            <span class="meta">{{ parseTime(item.createTime) }}</span>
          </div>
          <h3 class="title" @click="openQuestion(item.questionId)">{{ item.title }}</h3>
          <p class="summary">{{ item.summary || item.content }}</p>
          <div class="question-foot">
            <span>{{ item.isAnonymous === 'Y' ? '匿名发布' : (item.publisherNickName || item.publisherName) }}</span>
            <span>{{ item.answerCount }} 回答</span>
            <span>{{ item.viewCount }} 浏览</span>
            <span>{{ item.favoriteCount }} 收藏</span>
          </div>
        </el-card>
        <el-empty v-if="!questions.length" description="暂无问题内容" />
        <div class="pager">
          <el-pagination
            background
            layout="prev, pager, next"
            :current-page.sync="query.pageNum"
            :page-size="query.pageSize"
            :total="total"
            @current-change="fetchQuestions"
          />
        </div>
      </div>

      <aside class="side-column">
        <el-card shadow="never">
          <div slot="header">热门问题</div>
          <div v-for="item in hotQuestions" :key="item.questionId" class="hot-item" @click="openQuestion(item.questionId)">
            <strong>{{ item.title }}</strong>
            <span>{{ item.answerCount }} 回答 · {{ item.viewCount }} 浏览</span>
          </div>
        </el-card>
        <el-card shadow="never" style="margin-top: 16px;">
          <div slot="header">话题分类</div>
          <div class="category-list">
            <el-tag v-for="item in categories" :key="item.categoryId" @click.native="pickCategory(item.categoryId)">{{ item.categoryName }}</el-tag>
          </div>
        </el-card>
      </aside>
    </section>
  </div>
</template>

<script>
import { getPortalCategories, getPortalHotQuestions, getPortalQuestions } from '@/api/portal'
import { parseTime } from '@/utils/ruoyi'

export default {
  name: 'PortalHome',
  data() {
    return {
      categories: [],
      questions: [],
      hotQuestions: [],
      total: 0,
      query: {
        pageNum: 1,
        pageSize: 10,
        keyword: '',
        categoryId: undefined
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
    this.fetchCategories()
    this.fetchQuestions()
    this.fetchHotQuestions()
  },
  methods: {
    parseTime,
    fetchCategories() {
      getPortalCategories().then(res => {
        this.categories = res.data || []
      })
    },
    fetchQuestions() {
      getPortalQuestions(this.query).then(res => {
        const page = res.data || {}
        this.questions = page.rows || []
        this.total = page.total || 0
      })
    },
    fetchHotQuestions() {
      getPortalHotQuestions().then(res => {
        this.hotQuestions = res.data || []
      })
    },
    pickCategory(categoryId) {
      this.query.categoryId = categoryId
      this.query.pageNum = 1
      this.fetchQuestions()
    },
    openQuestion(questionId) {
      this.$router.push(`/portal/question/${questionId}`)
    }
  }
}
</script>

<style scoped>
.hero {
  display: flex;
  justify-content: space-between;
  gap: 20px;
  align-items: center;
  padding: 28px 32px;
  border-radius: 28px;
  background: linear-gradient(135deg, #0f172a 0%, #1d4ed8 55%, #0f766e 100%);
  color: #fff;
  margin-bottom: 20px;
}

.hero-badge {
  display: inline-block;
  padding: 4px 10px;
  border-radius: 999px;
  background: rgba(255, 255, 255, 0.14);
  margin-bottom: 16px;
}

.hero h2 {
  margin: 0 0 10px;
  font-size: 34px;
  line-height: 1.15;
}

.hero p {
  max-width: 680px;
  margin: 0;
  color: rgba(255, 255, 255, 0.88);
}

.filter-card {
  margin-bottom: 20px;
}

.content-grid {
  display: grid;
  grid-template-columns: 1fr 320px;
  gap: 20px;
}

.question-card {
  margin-bottom: 16px;
  border-radius: 20px;
}

.question-head,
.question-foot,
.hot-item {
  display: flex;
  justify-content: space-between;
  gap: 12px;
}

.title {
  cursor: pointer;
  margin: 14px 0 10px;
}

.summary {
  color: #475569;
  line-height: 1.7;
}

.question-foot {
  color: #64748b;
  font-size: 13px;
}

.meta {
  color: #94a3b8;
  font-size: 12px;
}

.hot-item {
  flex-direction: column;
  cursor: pointer;
  padding: 10px 0;
  border-bottom: 1px solid #eef2f7;
}

.category-list {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
}

.pager {
  margin-top: 20px;
  text-align: center;
}

@media (max-width: 960px) {
  .content-grid {
    grid-template-columns: 1fr;
  }

  .hero {
    flex-direction: column;
    align-items: flex-start;
  }
}
</style>
