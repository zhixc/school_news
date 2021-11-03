<template>
  <div>
    <el-container>
      <el-header>
        <h2>欢迎注册</h2>
      </el-header>

      <el-main>
        <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
          <el-form-item label="用户名" prop="username">
            <el-input v-model="ruleForm.username"></el-input>
          </el-form-item>
          <el-form-item label="密码" prop="password">
            <el-input type="password" v-model="ruleForm.password" show-password></el-input>
          </el-form-item>
          <el-form-item label="确认密码" prop="password2">
            <el-input type="password" v-model="ruleForm.password2" show-password></el-input>
          </el-form-item>

          <el-form-item>
            <el-button type="primary" @click="submitForm('ruleForm')">注册</el-button>
            <el-button @click="resetForm('ruleForm')">重置</el-button>
            <el-button @click="backToIndex" >返回首页</el-button>
          </el-form-item>
        </el-form>
      </el-main>

    </el-container>
  </div>
</template>

<script>
  export default {
    name: "Register",
    data(){
      return{
        ruleForm: {
          username: '',
          password: '',
          password2: ''
        },
        rules: {
          username: [
            {required: true, message: '请输入用户名', trigger: 'blur'}
            // {min: 3, max: 15, message: '长度在 3 到 15 个字符', trigger: 'blur'}
          ],
          password: [
            {required: true, message: '请输入密码', trigger: 'change'}
          ],
          password2: [
            {required: true, message: '请输入确认密码', trigger: 'change'}
          ]
        }
      }
    },
    methods: {
      submitForm(formName) {
        // 预存整个项目的 this
        const _this = this
        //alert('submit!');
        this.$axios.post('http://localhost:8081/register', this.ruleForm).then(res => { // 进入回调函数
          if (res.data.code === 200) { // 注册成功
            _this.$message({// 调用elementUI的消息弹窗
              type: 'success',
              message: res.data.msg
            })
            // 注册成功后跳转到登录页面
            _this.$router.push("/login")
          }else {
            _this.$message({// 调用elementUI的消息弹窗
              type: 'error',
              message: res.data.msg
            })
          }
        })
      },
      resetForm(formName) { // 重置表单的方法
        this.$refs[formName].resetFields();
      },
      backToIndex() {
        this.$router.push('/')
      }
    }
  }
</script>

<style scoped>
  .el-header, .el-footer {
    background-color: white;
    text-align: center;
    line-height: 40px;
  }


  .el-main {
    /*background-color: #E9EEF3;*/
    color: #333;
    text-align: center;
    line-height: 160px;
  }

  body > .el-container {
    margin-bottom: 40px;
  }

  .el-container:nth-child(5) .el-aside,
  .el-container:nth-child(6) .el-aside {
    line-height: 260px;
  }

  .el-container:nth-child(7) .el-aside {
    line-height: 320px;
  }

  .demo-ruleForm {
    max-width: 500px;
    margin: 0 auto;
  }
</style>