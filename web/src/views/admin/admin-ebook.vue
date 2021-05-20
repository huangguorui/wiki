<template>
  <div class="about">
    <h1>后台管理</h1>
    <Table :columns="titleTable"
           :data="list"></Table>
    <Page :total="pageInfo.total"
          :page-size="pageInfo.size"
          @on-change="page" />
    {{pageInfo.total}}
  </div>
</template>
<script>
export default {

  data () {
    return {
      titleTable: [
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

      ],
      pageInfo: {
        total: 0,
        size: 2,
        page: 1
      }

    }
  },
  created () {
    this.getList()
  },
  methods: {
    page (e) {
      console.log(e)
      this.pageInfo.page = e
      this.getList()

    },
    getList () {
      this.$axios({
        url: "/ebook/list",
        method: "get",
        params: {
          page: this.pageInfo.page,
          size: this.pageInfo.size
        }
      }).then(res => {
        this.list = res.data.content.list
        this.pageInfo.total = res.data.content.total
        console.log(res.data.content.total)

        console.log(res)

      })
    }

  },

}
</script>