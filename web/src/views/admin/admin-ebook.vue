<template>
  <div class="about">
    <h1>后台管理</h1>
    <Table :columns="columns1"
           :data="list"></Table>
  </div>
</template>
<script>
export default {

  data () {
    return {
      columns1: [
        {
          title: 'name',
          key: 'name'
        },

      ],

      //       category1Id: null
      // category2Id: null
      // cover: null
      // description: "零基础入门 Java 开发，企业级应用开发最佳首选框架"
      // docCount: 0
      // id: 1
      // name: "Spring Boot 入门教程"
      // viewCount: 0
      // voteCount: 0
      list: [
        {
          name: 'John Brown',
          age: 18,
          address: 'New York No. 1 Lake Park',
          date: '2016-10-03'
        },

      ]
    }
  },
  created () {
    this.getList()
  },
  methods: {
    getList () {
      this.$axios({
        url: "/ebook/list?name=Spring",
        method: "get",
        params: {}
      }).then(res => {
        console.log(res)
        this.list = res.data.content
        console.log(res)

      })
    }

  },

}
</script>