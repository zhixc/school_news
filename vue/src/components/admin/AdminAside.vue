<template>
  <div>
    <!--    :default-openeds="1" 默认展开index值为1的选项    -->
    <el-menu router default-active="/newsManager" style="width: 200px; min-height: calc(100vh - 50px)" class="el-menu-vertical-demo">
      <!--   上一行的 el-menu标签里面的 router 属性允许 下面使用index属性的值
           进行路由跳转 -->

      <!-- 根据 roleId进行判断是否显示     -->
      <el-menu-item v-if="roleId === 1" index="/admin">
        <template #title>用户管理</template>
      </el-menu-item>

      <el-menu-item v-if="roleId === 1 || roleId === 2 " index="/newsManager">
        <template #title>新闻管理</template>
      </el-menu-item>

      <!--            <el-menu-item index="2">-->
      <!--                <i class="el-icon-menu"></i>-->
      <!--                <template #title>导航二</template>-->
      <!--            </el-menu-item>-->

      <!--            <el-menu-item index="4">-->
      <!--                <i class="el-icon-setting"></i>-->
      <!--                <template #title>导航四</template>-->
      <!--            </el-menu-item>-->

    </el-menu>
  </div>
</template>

<script>
  export default {
    name: "AdminAside",
    data() {
      return {
        roleId: 3
      }
    },
    created() {
      this.roleId = this.$store.getters.getUser.roleId
    }
  }
</script>

<style scoped>

</style>