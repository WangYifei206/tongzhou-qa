<template>
  <div class="app-container">
    <el-form :inline="true" :model="queryParams" size="small">
      <el-form-item>
        <el-input v-model="queryParams.userName" placeholder="用户名" @keyup.enter.native="getList" />
      </el-form-item>
      <el-form-item>
        <el-input v-model="queryParams.nickName" placeholder="昵称" @keyup.enter.native="getList" />
      </el-form-item>
      <el-form-item>
        <el-input v-model="queryParams.college" placeholder="学院" @keyup.enter.native="getList" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="getList">搜索</el-button>
      </el-form-item>
    </el-form>

    <el-table :data="userList" v-loading="loading">
      <el-table-column prop="userName" label="用户名" width="140" />
      <el-table-column prop="nickName" label="昵称" width="140" />
      <el-table-column prop="college" label="学院" width="160" />
      <el-table-column prop="grade" label="年级" width="120" />
      <el-table-column prop="major" label="专业" width="160" />
      <el-table-column prop="questionCount" label="提问数" width="100" />
      <el-table-column prop="answerCount" label="回答数" width="100" />
      <el-table-column prop="favoriteCount" label="收藏数" width="100" />
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" />
  </div>
</template>

<script>
import { listPortalUser } from '@/api/tongzhou/user'

export default {
  name: 'TongzhouUser',
  data() {
    return {
      loading: false,
      total: 0,
      userList: [],
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userName: '',
        nickName: '',
        college: ''
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.loading = true
      listPortalUser(this.queryParams).then(res => {
        this.userList = res.rows || []
        this.total = res.total || 0
      }).finally(() => {
        this.loading = false
      })
    }
  }
}
</script>
