<template>
  <div class="about">
    <h1>后台管理</h1>
    <Button @click="add">新增</Button>
    <Table :columns="titleTable"
           :loading="loading"
           :data="list"></Table>
    <Page :total="pageInfo.total"
          :page-size="pageInfo.size"
          @on-change="page" />
    {{pageInfo.total}}

    <Modal v-model="isDeleteDrawer"
           title="删除数据"
           :footer-hide="true">
      <div>您确定删除数据id为{{this.delList}}的数据吗</div>
      <Button @click="del">确定</Button>
    </Modal>
    <Modal v-model="isCloseDrawer"
           :title="titleDrawer"
           :footer-hide="true">
      <Form ref="formData"
            :model="formData"
            :rules="ruleValidate"
            :label-width="80">
        <FormItem label="封面"
                  prop="cover">
          <Input v-model="formData.cover"
                 placeholder=""></Input>
        </FormItem>
        <FormItem label="名称"
                  prop="name">
          <Input v-model="formData.name"
                 placeholder=""></Input>
        </FormItem>
        <FormItem label="分类一"
                  prop="category1Id">
          <Input v-model="formData.category1Id"
                 placeholder=""></Input>
        </FormItem>
        <FormItem label="分类二"
                  prop="category2Id">
          <Input v-model="formData.category2Id"
                 placeholder=""></Input>
        </FormItem>
        <FormItem label="描述"
                  prop="description">
          <Input v-model="formData.description"
                 placeholder=""></Input>
        </FormItem>

        <FormItem>
          <Button type="primary"
                  @click="handleSubmit('formData')">Submit</Button>
          <Button @click="handleReset('formData')"
                  style="margin-left: 8px">Reset</Button>
        </FormItem>
      </Form>
    </Modal>
  </div>
</template>
<script>
export default {

  data () {
    const validateTest = (rule, value, callback) => {
      if (/^[0-9]+$/.test(value)) {
        callback();
      } else {
        return callback(new Error("请填写整数"));
      }
    };
    return {
      formData: {
        // cover: "",
        // name: "",
        // category1Id: null,
        // category2Id: null,
        // description: ""
        category1Id: null,
        category2Id: null,
        cover: "",
        description: "",
        docCount: 0,
        id: null,
        name: "",
        viewCount: 0,
        voteCount: 0,
      },

      ruleValidate: {

        cover: [
          { required: true, message: '不能为空', trigger: 'blur' }
        ],
        name: [
          { required: true, message: '不能为空', trigger: 'blur' }
        ],
        category1Id: [
          { required: true, validator: validateTest, trigger: 'blur' }
        ],
        category2Id: [
          { required: true, validator: validateTest, trigger: 'blur' }
        ],
        description: [
          { required: true, message: '不能为空', trigger: 'blur' }
        ],
      },
      titleTable: [
        {
          title: '封面',
          key: 'cover'
        },
        {
          title: '名称',
          key: 'name'
        },
        {
          title: '分类一',
          key: 'category1Id'
        },
        {
          title: '分类二',
          key: 'category2Id'
        },
        {
          title: '文档数',
          key: 'docCount'
        },
        {
          title: '阅读量',
          key: 'viewCount'
        },
        {
          title: '点赞数',
          key: 'voteCount'
        },
        {
          title: '操作',
          align: 'center',
          type: 'text',
          width: 260,
          render: (h, params) => {
            return h('div', [
              h('Button', {
                props: {
                  type: 'primary',
                  size: 'small'
                },
                style: {
                  marginRight: '5px'
                },
                // props: {
                //   size: 'small'
                // },
                on: {
                  click: () => {
                    this.isCloseDrawer = true
                    this.titleDrawer = '编辑资源'
                    // 防止表格中的数据随着修改而发生改变
                    this.formData = Object.assign({}, params.row)
                    console.log(params.row)
                  }
                }
              }, '编辑'),
              h('Button', {
                props: {
                  type: 'error',
                  size: 'small'
                },
                on: {
                  click: () => {
                    console.log(params.row)
                    // this.delList = [params.row.id]
                    this.delList = params.row.id
                    this.isDeleteDrawer = true
                  }
                }
              }, '删除')
            ])
          }
        }

      ],
      loading: true,
      isCloseDrawer: false,
      titleDrawer: "",
      isDeleteDrawer: false,
      list: [],
      pageInfo: {
        total: 0,
        size: 2,
        page: 1
      },
      delList: null,

    }
  },
  created () {
    this.getList()
  },
  methods: {
    del () {
      this.isDeleteDrawer = false
      this.$axios({
        url: "/ebook/delete/" + this.delList,
        method: "delete",
        //  data: {
        //       "dailyId": "dbc576e9-2979-4443-8288-cffb234772b9"
        //     }
      }).then(res => {
        if (res.data.success) {
          this.getList()

        }


      })
    },
    add () {
      //   this.formData = {}
      this.isCloseDrawer = true
      this.titleDrawer = "新增资源"
    },
    handleSubmit (name) {
      this.$refs[name].validate((valid) => {
        if (valid) {
          this.$Message.success('Success!');
          this.isCloseDrawer = false
          //校验成功，发送请求
          this.save(this.formData)

        } else {
          this.$Message.error('Fail!');
          this.isCloseDrawer = true

        }
      })
    },
    handleReset (name) {
      this.$refs[name].resetFields();
    },

    page (e) {
      console.log(e)
      this.pageInfo.page = e
      this.getList()

    },
    getList () {
      this.loading = true
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
        this.loading = false


      })
    },
    save (data) {
      this.$axios({
        url: "/ebook/save",
        method: "post",
        data: data
      }).then(res => {
        if (res.data.success) {
          this.getList()

        }


      })
    }

  },

}
</script>