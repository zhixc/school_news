<template>
  <div>
    <el-container>
      <el-header>
        <h2>欢迎登录</h2>
      </el-header>

      <el-main>
        <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
          <el-form-item label="用户名" prop="username">
            <el-input v-model="ruleForm.username"></el-input>
          </el-form-item>
          <el-form-item label="密码" prop="password">
            <el-input type="password" v-model="ruleForm.password" show-password></el-input>
          </el-form-item>

          <el-form-item label="验证码" prop="verificationCode">
            <el-input v-model="ruleForm.newVerificationCode"></el-input>
          </el-form-item>

          <img id="verificationCodeImg" src="/api/api/kaptcha/defaultKaptcha"
               style="cursor: pointer;" title="看不清？换一张" />
          <a href="javascript:;" @click="loadCheckCode">换一张</a>

          <el-form-item>
            <el-button type="primary" @click="submitForm('ruleForm')">登录</el-button>
            <el-button @click="resetForm('ruleForm')">重置</el-button>
            <el-button @click="toRegister">注册</el-button>
            <el-button @click="backToIndex" >返回首页</el-button>
          </el-form-item>
        </el-form>
      </el-main>

    </el-container>
  </div>
</template>

<script>

  export default {
    name: "Login",
    data() {
      return {
        ruleForm: {
          username: '',
          password: '',
          newVerificationCode: ''
        },
        rules: {
          username: [
            {required: true, message: '请输入用户名', trigger: 'blur'}
            // {min: 3, max: 15, message: '长度在 3 到 15 个字符', trigger: 'blur'}
          ],
          password: [
            {required: true, message: '请输入密码', trigger: 'change'}
          ],
          newVerificationCode: [
            {required: true, message: '请输入验证码', trigger: 'change'}
          ]
        }
      }
    },
    created() {
      this.loadCheckCode()
    },
    methods: {
      timestamp(url) {
        var getTimestamp = new Date().getTime();
        if (url.indexOf("?") > -1) {
          url = url + "&timestamp=" + getTimestamp
        } else {
          url = url + "?timestamp=" + getTimestamp
        }
        return url;
       },
      loadCheckCode() { // 从服务器提供的接口请求加载验证码
        document.getElementById("verificationCodeImg").src
          = this.timestamp("/api/api/kaptcha/defaultKaptcha");

      },
      submitForm(formName) { // 表单提交
        this.$refs[formName].validate((valid) => {
          if (valid) {
            // 预存整个项目的 this
            const _this = this
            //alert('submit!');
            this.$axios.post('/api/login', this.ruleForm).then(res => { // 进入回调函数
              // console.log(res)
              if (res.data.code === 200){
                _this.$message({// 调用elementUI的消息弹窗
                  type: 'success',
                  message: res.data.msg
                })
                // 声明变量 userInfo，将服务器返回的用户登录信息存入变量中
                const userInfo = res.data.data
                // 调用 store的commit里面的  SET_USERINFO 方法，将用户信息存到session中
                _this.$store.commit("SET_USERINFO", userInfo)
                // 登录成功后，跳转到路径 / 下，即首页
                this.$router.push('/')
              }else {
                _this.$message({// 调用elementUI的消息弹窗
                  type: 'error',
                  message: res.data.msg
                })
              }
              // 可以取出用户信息
              // console.log(_this.$store.getters.getUser)
            })
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
      resetForm(formName) {
        this.$refs[formName].resetFields();
      },
      backToIndex() {
        this.$router.push('/')
      },
      toRegister() {
        this.$router.push('/register')
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