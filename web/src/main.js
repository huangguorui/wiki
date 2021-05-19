import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import iView from 'iview';
import 'iview/dist/styles/iview.css';
Vue.config.productionTip = false
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
Vue.use(iView);
Vue.use(ElementUI);




//导入全局组件
import axios from 'axios'
Vue.prototype.$axios = axios
axios.defaults.baseURL = process.env.VUE_APP_SERVER

console.log('环境：',process.env.NODE_ENV)
console.log('服务器：',process.env.VUE_APP_SERVER)

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')