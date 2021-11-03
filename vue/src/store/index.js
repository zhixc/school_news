import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    userInfo: JSON.parse(sessionStorage.getItem("userInfo")) // 用户信息
  },
  mutations: {
    // set方法
    SET_USERINFO: (state, userInfo) => { // 接收登录后用户信息
      state.userInfo = userInfo
      // 将用户信息存到 session 中
      sessionStorage.setItem("userInfo", JSON.stringify(userInfo))
    },
    REMOVE_INFO: (state) => {
      state.userInfo = {}
      // 将用户信息从 session 中移除
      sessionStorage.setItem("userInfo", JSON.stringify(''))
    }
  },
  getters: {
    // get
    getUser: state => {
      // 从 state 的 变量 userInfo 中取用户信息
      return state.userInfo
    }
  },
  actions: {

  },
  modules: {
  }
})
