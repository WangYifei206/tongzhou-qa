<template>
  <div class="app-container">
    <el-form :inline="true" :model="queryParams" size="small">
      <el-form-item>
        <el-input v-model="queryParams.keyword" placeholder="标题/内容关键词" @keyup.enter.native="getList" />
      </el-form-item>
      <el-form-item>
        <el-select v-model="queryParams.categoryId" clearable placeholder="分类">
          <el-option v-for="item in categories" :key="item.categoryId" :label="item.categoryName" :value="item.categoryId" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-select v-model="queryParams.status" clearable placeholder="状态">
          <el-option label="待审核" value="0" />
          <el-option label="已通过" value="1" />
          <el-option label="已驳回" value="2" />
          <el-option label="已下架" value="3" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="getList">搜索</el-button>
      </el-form-item>
    </el-form>

    <el-table :data="questionList" v-loading="loading">
      <el-table-column prop="title" label="标题" min-width="220" show-overflow-tooltip />
      <el-table-column prop="categoryName" label="分类" width="120" />
      <el-table-column prop="publisherNickName" label="发布者" width="140" />
      <el-table-column prop="status" label="状态" width="100">
        <template slot-scope="scope">{{ statusMap[scope.row.status] }}</template>
      </el-table-column>
      <el-table-column prop="answerCount" label="回答" width="80" />
      <el-table-column prop="viewCount" label="浏览" width="80" />
      <el-table-column label="操作" width="240">
        <template slot-scope="scope">
          <el-button type="text" @click="viewDetail(scope.row)">详情</el-button>
          <el-button type="text" @click="review(scope.row, '1')">通过</el-button>
          <el-button type="text" @click="review(scope.row, '2')">驳回</el-button>
          <el-button type="text" @click="offline(scope.row)">下架</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" />

    <el-dialog title="问题详情" :visible.sync="open" width="760px">
      <div v-if="detail.questionId">
        <h3>{{ detail.title }}</h3>
        <p>{{ detail.content }}</p>
        <p>真实发布人：{{ detail.publisherNickName || detail.publisherName }}</p>
        <p>匿名展示：{{ detail.isAnonymous === 'Y' ? '是' : '否' }}</p>
        <p v-if="detail.rejectReason">驳回原因：{{ detail.rejectReason }}</p>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getPortalCategories } from '@/api/portal'
import { getQuestion, listQuestion, offlineQuestion, reviewQuestion } from '@/api/tongzhou/question'

export default {
  name: 'TongzhouQuestion',
  data() {
    return {
      loading: false,
      total: 0,
      open: false,
      categories: [],
      questionList: [],
      detail: {},
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        keyword: '',
        categoryId: undefined,
        status: ''
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
    this.getList()
    getPortalCategories().then(res => {
      this.categories = res.data || []
    })
  },
  methods: {
    getList() {
      this.loading = true
      listQuestion(this.queryParams).then(res => {
        this.questionList = res.rows || []
        this.total = res.total || 0
      }).finally(() => {
        this.loading = false
      })
    },
    viewDetail(row) {
      getQuestion(row.questionId).then(res => {
        this.detail = res.data || {}
        this.open = true
      })
    },
    review(row, status) {
      const rejectReason = status === '2' ? window.prompt('请输入驳回原因', '') : ''
      if (status === '2' && rejectReason === null) return
      reviewQuestion({ id: row.questionId, status, rejectReason }).then(() => {
        this.$message.success('审核完成')
        this.getList()
      })
    },
    offline(row) {
      offlineQuestion(row.questionId).then(() => {
        this.$message.success('已下架')
        this.getList()
      })
    }
  }
}
</script>
