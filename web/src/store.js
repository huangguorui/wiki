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
      console.log(user)
      state.user = user

    }
  },
  actions: {

  }
})