<template>
  <div class="about">
    <h1>后台管理</h1>
    <Input v-model="search"
           placeholder="Enter something..."
           style="width: 300px" />
    <Button @click="seleteValue">搜索</Button>
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

        <FormItem label="昵称"
                  prop="name">
          <Input v-model="formData.name"
                 placeholder=""></Input>
        </FormItem>
        <FormItem label="登录名"
                  prop="loginName">
          <Input v-model="formData.loginName"
                 placeholder=""></Input>
        </FormItem>
        <FormItem label="密码"
                  prop="password">
          <Input v-model="formData.password"
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
    // const validateTest = (rule, value, callback) => {
    //   if (/^[0-9]+$/.test(value)) {
    //     callback();
    //   } else {
    //     return callback(new Error("请填写整数"));
    //   }
    // };
    return {
      search: "",
      formData: {
        id: null,//不添加会替换
        name: "",
        loginName: "",
        password: ""
      },

      ruleValidate: {
        name: [
          { required: true, message: '不能为空', trigger: 'blur' }
        ],
        loginName: [
          { required: true, message: '不能为空', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '不能为空', trigger: 'blur' }
        ]
      },
      titleTable: [
        {
          title: '昵称',
          key: 'name'
        },
        {
          title: '登录名',
          key: 'loginName'
        },
        {
          title: '密码',
          key: 'password'
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
        size: 10,
        page: 1
      },
      delList: null,

    }
  },
  created () {
    this.getList()
  },
  methods: {
    seleteValue () {
      this.pageInfo.page = 1
      this.getList()
    },
    del () {
      this.isDeleteDrawer = false
      this.$axios({
        url: "/user/delete/" + this.delList,
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
      this.$refs["formData"].resetFields();

      this.isCloseDrawer = true
      this.titleDrawer = "新增资源"
    },
    handleSubmit (name) {
      this.$refs[name].validate((valid) => {
        if (valid) {
          //   this.$Message.success('Success!');
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
        url: "/user/list",
        method: "get",
        params: {
          page: this.pageInfo.page,
          size: this.pageInfo.size,
          loginName: this.search
        }
      }).then(res => {
        const data = res.data
        if (data.success) {
          this.list = data.content.list
          this.pageInfo.total = data.content.total
        } else {
          this.$Message.error(data.message);
        }

        this.loading = false


      })
    },
    save (data) {
      this.$axios({
        url: "/user/save",
        method: "post",
        data: data
      }).then(res => {
        const data = res.data
        if (data.success) {
          this.getList()
          this.isCloseDrawer = false

        } else {
          this.isCloseDrawer = true
          this.$Message.error(data.message);
        }
      })
    }

  },

}
</script>