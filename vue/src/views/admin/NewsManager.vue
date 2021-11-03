<template>
  <div style="padding: 10px">

    <!-- 功能按钮区域   -->
    <!-- 搜索区域 -->
    <div>
      <el-input v-model="search" placeholder="请输入内容" clearable style="width: 30%"></el-input>
      <el-button type="primary" @click="load" icon="el-icon-search" style="margin: 10px">查询</el-button>
      <el-button type="primary" @click="add" icon="el-icon-plus" style="margin: 10px">新增</el-button>
    </div>

    <!-- 表格数据区域   -->
    <el-table
      :data="tableData"
      border
      stripe
      style="width: 100%">
      <el-table-column
        prop="id"
        label="ID"
        sortable
      >
      </el-table-column>

      <el-table-column
        prop="title"
        label="标题">
      </el-table-column>

      <el-table-column
        prop="author"
        label="作者">
      </el-table-column>

      <el-table-column
        prop="content"
        label="内容">
      </el-table-column>

      <el-table-column
        label="图片">
        <template #default="scope">
          <el-image
            style="width: 100px; height: 100px"
            :src="scope.row.pictureUrl"
            :preview-src-list="[scope.row.pictureUrl]">
          </el-image>
        </template>
      </el-table-column>

      <el-table-column
        prop="typeId"
        label="类型">
        <template #default="scope">
          <span v-if="scope.row.typeId === 1">政治要闻</span>
          <span v-if="scope.row.typeId === 2">生活日常</span>
          <span v-if="scope.row.typeId === 3">军事要闻</span>
        </template>
      </el-table-column>

      <el-table-column
        prop="userId"
        label="用户id">
      </el-table-column>

      <el-table-column
        prop="issueTime"
        label="发布时间">
      </el-table-column>

      <el-table-column
        prop="updateTime"
        label="修改时间">
      </el-table-column>

      <el-table-column
        prop="status"
        label="状态">
      </el-table-column>

      <!--  表格行操作区域    -->
      <el-table-column
        fixed="right"
        label="操作">
        <template #default="scope">
          <el-button @click="handleEdit(scope.row)" icon="el-icon-edit" type="primary" size="small">编辑</el-button>

          <el-popconfirm title="确定删除？" @confirm="handleDelete(scope.row.id)">
            <template #reference>
              <el-button type="danger" icon="el-icon-delete"  size="small">删除</el-button>
            </template>
          </el-popconfirm>
        </template>
      </el-table-column>

    </el-table>

    <div style="margin: 10px 0">
      <!--  分页条    -->
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        :page-sizes="[3, 5, 10]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
      </el-pagination>

      <!--  新增( 或编辑 ) 对话框 (弹窗)    -->
      <el-dialog title="提示" :visible.sync="dialogVisible" width="80%">
        <el-form :model="form" :rules="rules" ref="form" label-width="120px">
          <el-form-item label="标题">
            <el-input v-model="form.title"></el-input>
          </el-form-item>
          <el-form-item label="内容">
            <mavon-editor v-model="form.content"></mavon-editor>
          </el-form-item>
          <el-form-item label="作者">
            <el-input v-model="form.author"></el-input>
          </el-form-item>
          <el-form-item label="图片">
            <el-upload action="/api/files/upload"
            :on-success="filesUploadSuccess" ref="upload">
              <el-button size="small" type="primary">点击上传</el-button>
            </el-upload>
          </el-form-item>
          <el-form-item label="类型">
            <el-radio v-model="form.typeId" label="1">政治要闻</el-radio>
            <el-radio v-model="form.typeId" label="2">生活日常</el-radio>
            <el-radio v-model="form.typeId" label="3">军事要闻</el-radio>
          </el-form-item>
        </el-form>
        <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="save">确 定</el-button>
        </span>
        </template>
      </el-dialog>

    </div>

  </div>
</template>

<script>
  export default {
    name: "NewsManager",
    data() {
      return {
        form: {}, // 新增对话框里面的 form 表单，默认为空
        dialogVisible: false,  // 新增对话框默认不显示
        search: '', // 搜索关键字
        currentPage: 1, // 当前页
        pageSize: 3, // 默认每页 3 条数据
        total: 0, // 总的记录数，默认为 0 条
        tableData: [],  // 表格数据
        rules: {
          title: [
            {required: true, message: '请输入标题', trigger: 'blur'},
            {min: 3, max: 15, message: '长度在 3 到 15 个字符', trigger: 'blur'}
          ],
          content: [
            {required: true, message: '请输入内容', trigger: 'blur'}
          ],
          author: [
            {required: true, message: '请输入作者', trigger: 'blur'}
          ],
          typeId: [
            {required: true, message: '请选择新闻类型', trigger: 'blur'}
          ]
        }
      }
    },
    created() { // 页面加载时调用
      this.load()
    },
    methods: {
      filesUploadSuccess(res){ // 定义文件上传成功后回调的方法
        //console.log(res)
        this.form.pictureUrl = res.data
      },
      load() { // 加载表格数据
        // axios 发起 get 请求调用接口 http://localhost:8081/news/list
        this.$axios.get("/api/news/list", {
          params: {
            currentPage: this.currentPage, // 将前端页面中的当前页 currentPage 赋值给参数 currentPage
            pageSize: this.pageSize, // 将前端页面中的每页显示数据条数 pageSize 赋值给参数 pageSize
          }
        }).then(res => { // 调用接口后，后端返回的数据封装到了 res 参数
          //console.log(res) // 通过查看控制台的输出，可以得知每一行的数据都在 data.data 下的 records 里面
          this.tableData = res.data.data.records
          this.total = res.data.data.total // 通过查看控制台的输出，可以得知总记录数都在 data.data 里面的 total
        })
      },
      save() { // 新增对话框里面的 确认按钮 保存方法
        if (this.form.id) { // 判断新增（或编辑）对话框中的 form 的 id 是否存在
          // 存在，说明执行的是编辑修改操作
          // axios发起 put 请求 调用接口 http://localhost:8081/news/update
          // 传入参数 form
          this.$axios.put("/api/news/update", this.form).then(res => {
            if (res.data.code === 200) { // 如果后端返回的状态码 code 为 200，说明修改成功
              this.$message({ // Element UI 的消息功能
                type: "success",
                message: res.data.msg
              })
              this.dialogVisible  = false  // 关闭对话框
            }else { // 修改失败
              this.$message({ // Element UI 的消息功能
                type: "error",
                message: res.data.msg
              })
            }
            this.load()  // 调用 load() 方法， 重新加载页面
          })
        }else {
          // axios发起 post 请求 调用接口 http://localhost:8081/news/save
          // 传入参数 form
          this.$axios.post("/api/news/save", this.form).then(res => {
            if (res.data.code === 403) { // 后端判断校验，返回相关的错误信息
              this.$message({
                type: "error",
                message: res.data.msg
              })
            }
            if (res.data.code === 200) { // 后端判断用户名和密码都不为空，成功执行新增用户，返回状态码：200
              this.$message({
                type: "success",
                message: res.data.msg
              })
              this.dialogVisible  = false // 关闭对话框
              this.load()  // 调用 load() 方法， 重新加载页面
            }
          })
        }
      },
      add() { // 新增（添加）方法
        this.dialogVisible = true // 打开新增对话框（弹窗）
        this.form =  {}  // 重置新增对话框里面的 form 表单内容
        this.$nextTick(() => { // 处理未来元素的方法
          this.$refs["upload"].clearFiles() // 清除历史文件列表
        })
      },
      handleEdit(row) { // 编辑方法
        this.form = JSON.parse(JSON.stringify(row))
        this.dialogVisible = true  // 打开编辑对话框（弹窗）
        this.$nextTick(() => { // 处理未来元素的方法
          this.$refs["upload"].clearFiles() // 清除历史文件列表
        })
      },
      handleSizeChange(pageSize) { // 改变每页显示数据处理方法
        this.pageSize = pageSize
        this.currentPage = 1  // 每次改变每页显示数据条数时，应当重置当前页，避免 bug
        console.log("当前页是：" + this.currentPage)
        this.load()
      },
      handleCurrentChange(pageNum) { // 改变当前页处理方法
        this.currentPage = pageNum
        this.load()
      },
      handleDelete(id) { // 删除数据
        const _this = this
        this.$axios.delete("/api/news/delete/"+id).then(res => {
          //console.log(id)
          if (res.data.code === 200) { // 如果后端返回的状态码 code 为 200，说明删除成功
            this.$message({ // Element UI 的消息功能
              type: "success",
              message: res.data.msg
            })
          }else { // 删除失败
            this.$message({ // Element UI 的消息功能
              type: "error",
              message: res.data.msg
            })
          }
          this.load()  // 调用 load() 方法， 重新加载页面
        })
      }
    }
  }
</script>

<style scoped>

</style>