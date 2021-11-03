<template>
  <div>
    <!-- 后台管理头部   -->
    <AdminHeader/>
    <!--    主体  -->
    <div style="display: flex">
      <!--  侧边栏    -->
      <!--  引用自定义的 Aside 标签    -->
      <!-- 后台管理侧边栏   -->
      <AdminAside/>

      <!--  路由子组件 Admin 显示的内容区域      -->
      <router-view style="flex: 1" />
    </div>


  </div>
</template>

<script>
  // 引入 AdminHeader 组件
  import AdminHeader from "./AdminHeader";
  // 引入 AdminAside 组件
  import AdminAside from "./AdminAside";
  export default {
    name: "AdminLayout",
    components: {
      AdminHeader, // 将 AdminHeader 组件 注册为 <AdminHeader> 标签
      AdminAside  // 将 AdminAside 组件 注册为 <AdminAside> 标签
    }
  }
</script>

<style scoped>

</style>