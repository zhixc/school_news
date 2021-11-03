import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from "../views/common/Home";
import About from "../views/common/About"

import Login from "../views/common/Login";
import Admin from "../views/admin/Admin";
import Register from "../views/common/Register";
import NewsDetail from "../views/common/NewsDetail";
import AdminLayout from "../components/admin/AdminLayout";
import NewsManager from "../views/admin/NewsManager";


Vue.use(VueRouter)

const routes = [
  {
    path: '/',   //首页
    name: 'Home',
    component: Home
  },
  {
    path: '/about', // 关于页面
    name: 'About',
    component: About
  },
  {
    path: '/newsDetail/:newsId',//新闻内容展示页面
    name: 'NewsDetail',
    component: NewsDetail
  },
  {
    path: '/login', // 登录页面
    name: 'Login',
    component: Login
  },
  {
    path: '/register', // 注册页面
    name: 'Register',
    component: Register
  },
  {
    path: '/adminLayout', // 管理员页面
    name: 'AdminLayout',
    component: AdminLayout,
    redirect: '/NewsManager',
    children: [ // 嵌套路由，嵌套子组件
      {
        path: '/admin', // Admin 组件映射的路径
        name: 'Admin',
        component: Admin // 使用 Admin 组件
      },
      {
        path: '/newsManager', // NewsManager 组件映射的路径
        name: 'NewsManager',
        component: NewsManager // 使用 NewsManager 组件
      }
    ]
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
