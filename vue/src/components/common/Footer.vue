<template>
  <div class="z-footer">
    友情链接：
    <span><el-link :href="url">SpringBoot</el-link></span>
    <el-divider direction="vertical"></el-divider>
    <span><el-link href="#">MyBatis Plus</el-link></span>
    <el-divider direction="vertical"></el-divider>
    <span><el-link href="#">ElementUI</el-link></span>
    <el-divider direction="vertical"></el-divider>
    <span><el-link href="#">Vue</el-link></span>
  </div>
</template>

<script>
  export default {
    name: "Footer",
    data() {
      return {
        url: 'https://spring.io/projects/spring-boot'
      }
    }
  }
</script>

<style scoped>
 .z-footer{
   max-width: 960px;
   margin: 20px auto;
   text-align: center;
 }
</style>