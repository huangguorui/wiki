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
    <!-- <Page :total="pageInfo.total"
          :page-size="pageInfo.size"
          @on-change="page" />
    {{pageInfo.total}} -->

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

        <FormItem label="名称"
                  prop="name">
          <Input v-model="formData.name"
                 placeholder=""></Input>
        </FormItem>
        <FormItem label="父分类"
                  prop="parent">
          <Input v-model="formData.parent"
                 placeholder=""></Input>
        </FormItem>
        <FormItem label="排序"
                  prop="sort">
          <Input v-model="formData.sort"
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
  <!-- 树形接口 -->
  <!-- 数据提交副分支hr -->
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
      search: "",
      formData: {
        "parent": null,
        "name": null,
        "sort": null
      },

      ruleValidate: {
        name: [
          { required: true, message: '不能为空', trigger: 'blur' }
        ],
        parent: [
          { required: true, validator: validateTest, trigger: 'blur' }
        ],
        sort: [
          { required: true, validator: validateTest, trigger: 'blur' }
        ]

      },

      titleTable: [
        {
          title: 'ID',
          key: 'id'
        },
        {
          title: '名称',
          key: 'name'
        },
        {
          title: '父分类',
          key: 'parent'
        },
        {
          title: '顺序',
          key: 'sort'
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
    seleteValue () {
      this.pageInfo.page = 1
      this.getList()
    },
    del () {
      this.isDeleteDrawer = false
      this.$axios({
        url: "/category/delete/" + this.delList,
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
        url: "/category/all",
        method: "get",
        params: {
          // page: this.pageInfo.page,
          // size: this.pageInfo.size,
          // name: this.search
        }
      }).then(res => {
        const data = res.data
        if (data.success) {
          this.list = data.content
          // this.pageInfo.total = data.content.total
        } else {
          this.$Message.error(data.message);
        }

        this.loading = false


      })
    },
    save (data) {
      this.$axios({
        url: "/category/save",
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