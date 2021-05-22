<style>
</style>

<template>
  <div id="app">
    <h1>
      <router-link to="/">首页</router-link>
      {{$store.state.user}}

    </h1>
    <login></login>
    <!-- <div id="nav">
      <router-link to="/">Home</router-link> |
      <router-link to="/about">About</router-link>
    </div> -->
    <!--    <head-nav></head-nav>-->
    <router-view />
    <!--    <foot-nav></foot-nav>-->
  </div>
</template>
<script>
import store from '@/store'

// import headNav from './components/header/header.vue'
//import vipHots from './components/vipHots/vipHots.vue'
// import footNav from './components/footer/footer.vue'
import login from './components/login.vue'
export default {
  data () {
    return {
      routerFlag: true,
      user: {}
    }
  },
  created () {
    this.load(),
      this.routerTo()
    // this.user = computed(() => {
    //   store.state.user
    // })
  },
  watch: {
    "$route": "routerLink"
  },
  methods: {
    start () {
      this.$Loading.start();
    },
    finish () {
      this.$Loading.finish();
    },
    error () {
      this.$Loading.error();
    },
    routerTo () {
      //order
      // if (this.$route.path === "/g" || this.$route.path === "/404") {
      //   this.routerFlag = false
      // }
      //console.log(this.$route.path === "/user")
    },
    load () {
      //console.log(1)

    },
    routerLink () {
      //console.log("aaa=", this.$route.path)
      // if (this.$route.path === "/user") {
      //   this.routerFlag = false
      // }
    }
  },
  components: {
    login
  }
}
</script>

<style lang="less">
-webkit-scrollbar {
  width: 0px;
  height: 0px;
}
li {
  list-style: none;
}
#app {
  width: 100%;
  min-height: 100%;
  position: relative;
  background: #f1f1ef;
}

//(<768px)	小屏幕 平板 (≥768px)	中等屏幕 桌面显示器 (≥992px)	大屏幕 大桌面显示器 (≥1200px)
</style>
