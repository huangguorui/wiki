
<style  lang="less" scoped>

</style>
<template>
  <div class="container">
    {{list}}
  </div>
</template>
<script>
export default {
  name: 'Index',
  components: {
  },

  data () {
    return {
      list: [
        {

        }
      ],
      pageInfo: {
        current: 1,
        size: 5,
        pages: 1,
        total: 1,
      }

    }
  },
  created(){
    this.getList()
  },
  methods: {
    getList () {
      this.$axios({
        url: process.env.VUE_APP_SERVER+"/ebook/list?name=Spring",
        method: "get",
        params: {}
      }).then(res => {
        this.list = res
        console.log(res)

      })
    }

  }
}
</script>
