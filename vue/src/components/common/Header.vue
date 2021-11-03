<template>
  <div>
    <!--   链接跳转   -->
    <div class="z-action">
      <span><el-link href="/">主页</el-link></span>

      <el-divider direction="vertical"></el-divider>

      <span><el-link href="/about">关于</el-link></span>

      <el-divider direction="vertical"></el-divider>

      <span v-if="roleId === 2 || roleId === 1"><el-link href="/adminLayout">后台管理</el-link></span>

      <div class="z-info">
        <span v-if="!hasLogin"><el-link href="/login" type="info">登录</el-link></span>
        &nbsp;
        <span  v-if="!hasLogin"><el-link href="/register" type="info">注册</el-link></span>
        <!-- 头像   -->
<!--        <span v-if="hasLogin"><el-avatar :size="50" :src="user.avatar"></el-avatar></span>-->
        <!--  用户名  -->
        <span>{{user.username}}</span>
        &nbsp;
        <span v-if="hasLogin"><el-link @click="logout" type="danger">退出</el-link></span>
      </div>

    </div>
  </div>
</template>

<script>
  export default {
    name: "Header",
    data() {
      return {
        user: {},
        hasLogin: false,
        roleId: 3
      }
    },
    methods: {
      logout() { // 退出方法，清除session，localStorage里面的所有用户信息
        const _this = this // 预存 this
        // 将 session 中的 userInfo 转为 User 对象，传给服务器
        //this.user = JSON.parse(sessionStorage.getItem("userInfo"))
        this.$axios.get("http://localhost:8081/logout").then(res => {
          if (res.data.code === 200) { // 退出成功
            _this.$message({ // 调用elementUI的消息弹窗
              type: 'success',
              message: res.data.msg
            })
            _this.$store.commit("REMOVE_INFO")
            // 跳转到登录页面
            _this.$router.push('/login')
          }else { // 退出失败
            _this.$message({ // 调用elementUI的消息弹窗
              type: 'error',
              message: res.data.msg
            })
            _this.$store.commit("REMOVE_INFO")
            // 跳转到登录页面
            _this.$router.push('/login')
          }
        })
      }
    },
    created() {
      if (this.$store.getters.getUser.username){
        this.user.username = this.$store.getters.getUser.username
        this.hasLogin = true
        this.roleId = this.$store.getters.getUser.roleId
      }
    }
  }
</script>

<style scoped>

  .z-action{
    max-width: 960px;
    line-height: 60px;
    margin: 20px auto;
  }
  .z-info{
    float: right;
    margin-right: 20px;
  }
</style>