<template>
  <div class="app-container">
    <el-form :inline="true" :model="queryParams" size="small">
      <el-form-item>
        <el-input v-model="queryParams.categoryName" placeholder="分类名称" @keyup.enter.native="getList" />
      </el-form-item>
      <el-form-item>
        <el-select v-model="queryParams.status" clearable placeholder="状态">
          <el-option label="正常" value="0" />
          <el-option label="停用" value="1" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="getList">搜索</el-button>
        <el-button @click="resetQuery">重置</el-button>
        <el-button type="success" @click="openDialog()">新增分类</el-button>
      </el-form-item>
    </el-form>

    <el-table :data="categoryList" v-loading="loading">
      <el-table-column prop="categoryName" label="分类名称" />
      <el-table-column prop="categoryCode" label="编码" />
      <el-table-column prop="sortNum" label="排序" width="100" />
      <el-table-column prop="status" label="状态" width="100">
        <template slot-scope="scope">{{ scope.row.status === '0' ? '正常' : '停用' }}</template>
      </el-table-column>
      <el-table-column prop="description" label="描述" />
      <el-table-column label="操作" width="160">
        <template slot-scope="scope">
          <el-button type="text" @click="openDialog(scope.row)">编辑</el-button>
          <el-button type="text" @click="handleDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" />

    <el-dialog :title="form.categoryId ? '编辑分类' : '新增分类'" :visible.sync="open" width="520px">
      <el-form :model="form" label-width="80px">
        <el-form-item label="名称">
          <el-input v-model="form.categoryName" />
        </el-form-item>
        <el-form-item label="编码">
          <el-input v-model="form.categoryCode" />
        </el-form-item>
        <el-form-item label="排序">
          <el-input-number v-model="form.sortNum" :min="1" />
        </el-form-item>
        <el-form-item label="状态">
          <el-radio-group v-model="form.status">
            <el-radio label="0">正常</el-radio>
            <el-radio label="1">停用</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="描述">
          <el-input v-model="form.description" type="textarea" :rows="3" />
        </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button @click="open = false">取消</el-button>
        <el-button type="primary" @click="submitForm">保存</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { addCategory, delCategory, listCategory, updateCategory } from '@/api/tongzhou/category'

export default {
  name: 'TongzhouCategory',
  data() {
    return {
      loading: false,
      total: 0,
      open: false,
      categoryList: [],
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        categoryName: '',
        status: ''
      },
      form: {
        categoryId: undefined,
        categoryName: '',
        categoryCode: '',
        sortNum: 1,
        status: '0',
        description: ''
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.loading = true
      listCategory(this.queryParams).then(res => {
        this.categoryList = res.rows || []
        this.total = res.total || 0
      }).finally(() => {
        this.loading = false
      })
    },
    resetQuery() {
      this.queryParams = { pageNum: 1, pageSize: 10, categoryName: '', status: '' }
      this.getList()
    },
    openDialog(row) {
      this.form = row ? { ...row } : { categoryId: undefined, categoryName: '', categoryCode: '', sortNum: 1, status: '0', description: '' }
      this.open = true
    },
    submitForm() {
      const req = this.form.categoryId ? updateCategory : addCategory
      req(this.form).then(() => {
        this.$message.success('保存成功')
        this.open = false
        this.getList()
      })
    },
    handleDelete(row) {
      this.$confirm(`确认删除分类“${row.categoryName}”吗？`, '提示').then(() => delCategory(row.categoryId)).then(() => {
        this.$message.success('删除成功')
        this.getList()
      })
    }
  }
}
</script>
