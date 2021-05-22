<style lang="less">
.vertical-center-modal {
  display: flex;
  align-items: center;
  justify-content: center;

  .ivu-modal {
    top: 0;
  }
}
</style>
<template>
  <div @click="handle">
    <div @click="modal = true">登录</div>
    <Modal title="用户登陆"
           v-model="modal"
           :maskClosable="false"
           :footerHide="true"
           width="320"
           class-name="vertical-center-modal">
      <Form ref="formData"
            :model="formData"
            :rules="ruleInline">
        <FormItem prop="loginName">
          <Input type="text"
                 v-model="formData.loginName"
                 placeholder="请输入登录名">
          <Icon type="ios-person-outline"
                slot="prepend"></Icon>
          </Input>
        </FormItem>
        <FormItem prop="password">
          <Input type="password"
                 v-model="formData.password"
                 placeholder="请输入密码">
          <Icon type="ios-lock-outline"
                slot="prepend"></Icon>
          </Input>
        </FormItem>
        <FormItem>
          <Button type="primary"
                  @click="handleSubmit('formData')">登陆</Button>
        </FormItem>
      </Form>

    </Modal>
  </div>
</template>
<script>
export default {
  data () {
    return {
      current: 0,
      modal9: false,
      modal: false,
      formData: {
        loginName: '',
        password: ''
      },
      ruleInline: {
        loginName: [
          { required: true, message: '请输入用户名', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { type: 'string', min: 8, message: '密码的长度要大于或者等于8位', trigger: 'blur' }
        ]
      }
    }
  },
  // props: {
  //   forData: {
  //     type: Object
  //   }
  // },
  methods: {
    handleSubmit (name) {
      this.$refs[name].validate((valid) => {
        if (valid) {
          this.modal = false;
          this.$axios({
            url: "/user/login",
            method: "post",
            data: this.formData
          }).then(res => {
            const data = res.data
            console.log(data)
            if (data.success) {
              this.modal = false
            } else {
              this.$Message.error(data.message);
              this.modal = true

            }
          })
        } else {
          this.$Message.error('登录失败');
        }
      })
    },
    handle () {
      this.$emit('on-ok')
      //console.log('调用组件')
    }
  }
}
</script>
