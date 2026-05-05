<template>
  <div class="app-container">
    <el-form :inline="true" :model="queryParams" size="small">
      <el-form-item>
        <el-input v-model="queryParams.content" placeholder="回答内容关键词" @keyup.enter.native="getList" />
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

    <el-table :data="answerList" v-loading="loading">
      <el-table-column prop="questionTitle" label="所属问题" min-width="220" show-overflow-tooltip />
      <el-table-column prop="content" label="回答内容" min-width="260" show-overflow-tooltip />
      <el-table-column prop="publisherNickName" label="发布者" width="140" />
      <el-table-column prop="status" label="状态" width="100">
        <template slot-scope="scope">{{ statusMap[scope.row.status] }}</template>
      </el-table-column>
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

    <el-dialog title="回答详情" :visible.sync="open" width="720px">
      <div v-if="detail.answerId">
        <p><strong>问题：</strong>{{ detail.questionTitle }}</p>
        <p><strong>真实发布人：</strong>{{ detail.publisherNickName || detail.publisherName }}</p>
        <p><strong>匿名展示：</strong>{{ detail.isAnonymous === 'Y' ? '是' : '否' }}</p>
        <p><strong>内容：</strong></p>
        <p>{{ detail.content }}</p>
        <p v-if="detail.rejectReason"><strong>驳回原因：</strong>{{ detail.rejectReason }}</p>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getAnswer, listAnswer, offlineAnswer, reviewAnswer } from '@/api/tongzhou/answer'

export default {
  name: 'TongzhouAnswer',
  data() {
    return {
      loading: false,
      total: 0,
      open: false,
      answerList: [],
      detail: {},
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        content: '',
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
  },
  methods: {
    getList() {
      this.loading = true
      listAnswer(this.queryParams).then(res => {
        this.answerList = res.rows || []
        this.total = res.total || 0
      }).finally(() => {
        this.loading = false
      })
    },
    viewDetail(row) {
      getAnswer(row.answerId).then(res => {
        this.detail = res.data || {}
        this.open = true
      })
    },
    review(row, status) {
      const rejectReason = status === '2' ? window.prompt('请输入驳回原因', '') : ''
      if (status === '2' && rejectReason === null) return
      reviewAnswer({ id: row.answerId, status, rejectReason }).then(() => {
        this.$message.success('审核完成')
        this.getList()
      })
    },
    offline(row) {
      offlineAnswer(row.answerId).then(() => {
        this.$message.success('已下架')
        this.getList()
      })
    }
  }
}
</script>
