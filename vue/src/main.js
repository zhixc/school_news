import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'


/**
 * 解决跨域时 session 失效问题
 */
//使用vue.axios发送请求时配置如下：
axios.defaults.withCredentials = true

/**
 * 引入 mavon-editor
 */
import mavonEditor from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'
Vue.use(mavonEditor)

/**
 * 引入ElementUI
 */
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
Vue.use(ElementUI)

/**
 * 引入axios，并全局使用
 */
import axios from 'axios'
Vue.prototype.$axios = axios

Vue.config.productionTip = false

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
