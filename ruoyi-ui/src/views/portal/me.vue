<template>
  <div>
    <el-card shadow="never" class="profile-card">
      <div slot="header">个人资料</div>
      <el-form :model="profile" label-width="80px">
        <el-row :gutter="16">
          <el-col :md="12">
            <el-form-item label="昵称">
              <el-input v-model="profile.nickName" />
            </el-form-item>
          </el-col>
          <el-col :md="12">
            <el-form-item label="邮箱">
              <el-input v-model="profile.email" />
            </el-form-item>
          </el-col>
          <el-col :md="12">
            <el-form-item label="手机">
              <el-input v-model="profile.phonenumber" />
            </el-form-item>
          </el-col>
          <el-col :md="12">
            <el-form-item label="学院">
              <el-input v-model="profile.college" />
            </el-form-item>
          </el-col>
          <el-col :md="12">
            <el-form-item label="年级">
              <el-input v-model="profile.grade" />
            </el-form-item>
          </el-col>
          <el-col :md="12">
            <el-form-item label="专业">
              <el-input v-model="profile.major" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="简介">
          <el-input v-model="profile.introduction" type="textarea" :rows="3" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="saveProfile">保存资料</el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <el-tabs value="questions">
      <el-tab-pane label="我的提问" name="questions">
        <el-table :data="questions">
          <el-table-column prop="title" label="标题" min-width="240" />
          <el-table-column prop="categoryName" label="分类" width="120" />
          <el-table-column prop="status" label="状态" width="100">
            <template slot-scope="scope">{{ statusMap[scope.row.status] }}</template>
          </el-table-column>
          <el-table-column prop="answerCount" label="回答数" width="100" />
          <el-table-column label="操作" width="120">
            <template slot-scope="scope">
              <el-button type="text" @click="$router.push(`/portal/question/${scope.row.questionId}`)">查看</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>
      <el-tab-pane label="我的回答" name="answers">
        <el-table :data="answers">
          <el-table-column prop="questionTitle" label="问题" min-width="220" />
          <el-table-column prop="content" label="回答内容" min-width="280" show-overflow-tooltip />
          <el-table-column prop="status" label="状态" width="100">
            <template slot-scope="scope">{{ statusMap[scope.row.status] }}</template>
          </el-table-column>
        </el-table>
      </el-tab-pane>
      <el-tab-pane label="我的收藏" name="favorites">
        <el-table :data="favorites">
          <el-table-column prop="title" label="问题标题" min-width="260" />
          <el-table-column prop="categoryName" label="分类" width="120" />
          <el-table-column prop="favoriteCount" label="收藏数" width="100" />
        </el-table>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import { getMyAnswers, getMyFavorites, getMyQuestions, getPortalProfile, updatePortalProfile } from '@/api/portal'

export default {
  name: 'PortalMe',
  data() {
    return {
      profile: {},
      questions: [],
      answers: [],
      favorites: [],
      statusMap: {
        0: '待审核',
        1: '已通过',
        2: '已驳回',
        3: '已下架'
      }
    }
  },
  created() {
    this.fetchAll()
  },
  methods: {
    fetchAll() {
      getPortalProfile().then(res => {
        this.profile = res.data || {}
      })
      getMyQuestions().then(res => {
        this.questions = res.data || []
      })
      getMyAnswers().then(res => {
        this.answers = res.data || []
      })
      getMyFavorites().then(res => {
        this.favorites = res.data || []
      })
    },
    saveProfile() {
      updatePortalProfile(this.profile).then(() => {
        this.$message.success('资料已更新')
        this.fetchAll()
      })
    }
  }
}
</script>

<style scoped>
.profile-card {
  margin-bottom: 18px;
}
</style>
