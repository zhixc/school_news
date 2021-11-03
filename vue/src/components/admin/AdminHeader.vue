<template>
  <div style="height: 50px; line-height: 50px; border-bottom: 1px solid #ccc; display: flex">

    <div style="width: 200px; padding-left: 30px; font-weight: bold; color: dodgerblue">后台管理</div>
    <span><el-link href="/">返回主页</el-link></span>


    <div style="flex: 1"></div>

    {{user.nickName}}
    &nbsp;
    <!--        -->
    <div style="width: 100px">

      <el-button @click="logout" type="danger">退出</el-button>
    </div>
  </div>
</template>

<script>
  export default {
    name: "AdminHeader",
    data() {
      return {
        user: {} // 声明一个变量user对象
      }
    },
    created() { // 当前组件被加载时调用这个里面的方法
      this.load()
    },
    methods: {
      load() {// 声明一个 load() 方法，用于从 sessionStorage 中获取 userInfo
        // 并赋值给上面声明的变量 user
        let user1 = sessionStorage.getItem("userInfo")
        this.user = JSON.parse(user1)
      },
      logout() { // 退出方法，清除session，localStorage里面的所有用户信息
        //const _this = this // 预存 this
        this.$axios.get("http://localhost:8081/logout").then(res => {
          if (res.data.code === 200) { // 退出成功
            this.$message({ // 调用 elementUI 的消息弹窗
              type: 'success',
              message: res.data.msg
            })
            // 调用 store 中的 REMOVE_INFO 方法，移除session 中的用户信息
            this.$store.commit("REMOVE_INFO")
            // 跳转到登录页面
            this.$router.push('/login')
          }else { // 退出失败
            this.$message({ // 调用 elementUI 的消息弹窗
              type: 'error',
              message: res.data.msg
            })
          }
        })
      }
    }
  }
</script>

<style scoped>

</style>