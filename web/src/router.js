import Vue from 'vue'
import Router from 'vue-router'
import Home from './views/home.vue'
import About from './views/about.vue'
import AdminEbook from './views/admin/admin-ebook.vue'
import AdminCategory from './views/admin/admin-category.vue'
import AdminUser from './views/admin/admin-user.vue'
import store from '@/store'


Vue.use(Router)
import iView from 'iview';
Vue.use(iView);
const router = new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  linkActiveClass: 'active',
  routes: [{
      path: '/',
      name: 'Home',
      component: Home,
      meta: {
        title: "首页-前端博客",
      }
    },
    {
      path: '/about',
      name: 'About',
      component: About,
      meta: {
        title: "关于我们",
        loginRequire: true

      }
    },
    {
      path: '/admin/ebook',
      name: 'AdminEbook',
      component: AdminEbook,
      meta: {
        title: "后台管理",
        loginRequire: true

      }
    },
    {
      path: '/admin/category',
      name: 'AdminCategory',
      component: AdminCategory,
      meta: {
        title: "后台主题管理",
        loginRequire: true

      }
    },
    {
      path: '/admin/user',
      name: 'AdminUser',
      component: AdminUser,
      meta: {
        title: "后台主题管理",
        loginRequire: true

      }
    }


    // {
    //   path: '/404',
    //   name: '404',
    //   component: nofound
    // },
    // {
    //   path: "*", // 此处需特别注意置于最底部
    //   redirect: "/404"
    // }
  ],
  scrollBehavior(to, from, savePosition) { //点击浏览器的前进后退或切换导航触发
    //to   当前需要进入的目标路由对象（要去向哪里）
    //from  离开的路由对象    （从哪里来）
    //savePosition   滚动的坐标
    //console.log(to);
    //console.log(from);
    //console.log(savePosition);
    if (savePosition) {
      return savePosition;
    } else {
      return {
        x: 0,
        y: 0
      }
    }
  }
})



// 路由登录拦截
router.beforeEach((to, from, next) => {
  // 要不要对meta.loginRequire属性做监控拦截
  if (to.matched.some(function (item) {
      console.log(item, "是否需要登录校验：", item.meta.loginRequire);
      return item.meta.loginRequire
    })) {
    const loginUser = store.state.user;

    if ("{}" == JSON.stringify(loginUser)) {
      // 满足条件就是空
      console.log("用户未登录！");
      next('/');
    } else {
      next();
    }
  } else {
    next();
  }
});


// router.beforeEach((to, from, next) => {

//   /* 路由发生变化修改页面meta */
//   if (to.meta.content) {
//     let head = document.getElementsByTagName('head');
//     let meta = document.createElement('meta');

//     meta.content = 'width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no';
//     meta.name = 'viewport';
//     head[0].appendChild(meta)
//   }
//   /* 路由发生变化修改页面title */
//   if (to.meta.title) {
//     document.title = to.meta.title;
//   }
//   //console.log('拦截到的数据为',to)

//   iView.LoadingBar.start();
//   next();
// });

router.afterEach(() => {
  iView.LoadingBar.finish();
});

export default router