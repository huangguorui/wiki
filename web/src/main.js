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

axios.interceptors.request.use(function (config) {
  config.headers.token = store.state.user.token
  console.log('请求参数：', config);
  // const token = store.state.user.token;
  // if (Tool.isNotEmpty(token)) {
  //   config.headers.token = token;
  //   console.log("请求headers增加token:", token);
  // }
  return config;
}, error => {
  return Promise.reject(error);
});
axios.interceptors.response.use(function (response) {
  console.log('返回结果：', response);
  return response;
}, error => {
  console.log('返回错误：', error);
  // const response = error.response;
  // const status = response.status;
  // if (status === 401) {
  //   // 判断状态码是401 跳转到首页或登录页
  //   console.log("未登录，跳到首页");
  //   store.commit("setUser", {});
  //   message.error("未登录或登录超时");
  //   router.push('/');
  // }
  return Promise.reject(error);
});

console.log('环境：', process.env.NODE_ENV)
console.log('服务器：', process.env.VUE_APP_SERVER)

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')