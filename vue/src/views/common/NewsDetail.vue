<template>
  <div>
    <Header></Header>
    <div class="z-news">
      <h2>{{news.title}}</h2>
      <div v-html="news.content"></div>
    </div>
  </div>

</template>

<script>
  import Header from "../../components/common/Header";
  export default {
    name: "NewsDetail",
    components: {
      Header
    },
    data() {
      return {
        news: {
          id: '',
          author: '',
          title: '',
          content: ''
        }
      }
    },
    created() {
      // 从路由中获取参数
      const newsId = this.$route.params.newsId
      // 预存储 this
      const _this = this
      this.$axios.get('http://localhost:8081/news/' + newsId).then(res => {
        console.log(res)
        const news = res.data.data
        _this.news.id = news.id
        _this.news.author = news.author
        _this.news.title = news.title


        let MarkdownIt = require('markdown-it')
        let md = new MarkdownIt()
        let result = md.render(news.content)

        _this.news.content = result
      })
    }
  }
</script>

<style scoped>
  .z-news{
    box-shadow: 0 2px 12px 0 rgba(0,0,0,0.1);
    max-width: 960px;
    margin: 0 auto;
    text-align: center;
    min-height: 700px;
  }
</style>