<template>
  <div>
    <div class="z-container">
      <el-timeline>
        <el-timeline-item v-for="news in newslist" :timestamp="news.issueTime" placement="top">
          <el-card>
            <!--  路由跳转到博客详情页，并带上参数 id       -->
            <router-link :to="{name: 'NewsDetail', params: {newsId: news.id}}">
              <h4>{{news.title}}</h4>
            </router-link>
            <p>作者：{{news.author}}</p>
          </el-card>
        </el-timeline-item>

      </el-timeline>
    </div>
    <!--  分页条    -->
    <el-pagination class="z-page"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="currentPage"
      :page-sizes="[3, 5, 10]"
      :page-size="pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total">
    </el-pagination>

  </div>
</template>

<script>
  export default {
    name: "Container",
    data() {
      return {
        newslist: [], // 新闻列表数据，里面存着新闻对象
        currentPage: 1, // 当前页，默认：1
        pageSize: 3,   // 每页显示多少条数据，默认 3
        total: 0    // 总记录数，默认 0
      }
    },
    created() { //页面一加载，就调用里面的方法
      this.load()
    },
    methods: {
      load() { // 查询新闻数据，并将之渲染到界面
        const _this = this
        this.$axios.get('http://localhost:8081/news/list', {
          params: {
            currentPage: this.currentPage, // 将前端页面中的当前页 currentPage 赋值给参数 currentPage
            pageSize: this.pageSize, // 将前端页面中的每页显示数据条数 pageSize 赋值给参数 pageSize
          }
        }).then(res => {
          _this.newslist = res.data.data.records
          _this.currentPage = res.data.data.current
          _this.pageSize = res.data.data.size
          _this.total = res.data.data.total
        })
      },
      handleSizeChange(pageSize) { // 改变每页显示数据的方法
        this.pageSize = pageSize
        //this.currentPage = 1  // 每次改变每页显示数据条数时，应当重置当前页，避免 bug
        console.log(this.currentPage)
        this.load() // 重载当前页面
      },
      handleCurrentChange(currentPage) { // 改变当前页的方法
        this.currentPage = currentPage
        this.load() // 重载当前页面
      }
    }
  }
</script>

<style scoped>
  .z-container{
    max-width: 960px;
    margin: 20px auto;
  }
  .z-page{
    max-width: 960px;
    margin: 20px auto;
    text-align: center;
  }
</style>