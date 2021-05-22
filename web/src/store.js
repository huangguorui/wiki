import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    user: {}
  },
  mutations: {
    setUser(state, user) {
      //没有值优先读取本地缓存

      // if (localStorage.getItem("user") != null) {
      //   state.user = localStorage.getItem("user")
      // } else {
      //   state.user = user
      // }

    }
  },
  actions: {

  }
})