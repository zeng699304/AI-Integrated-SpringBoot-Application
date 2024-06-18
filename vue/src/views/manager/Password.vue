<template>
  <div>
    <el-card style="width: 50%; padding-right: 30px">
      <el-form :model="user" label-width="80px" :rules="rules" ref="formRef">
        <el-form-item label="原始密码" prop="password">
          <el-input show-password v-model="user.password" placeholder="原始密码"></el-input>
        </el-form-item>
        <el-form-item label="新密码" prop="newPassword">
          <el-input show-password v-model="user.newPassword" placeholder="新密码"></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="confirmPassword">
          <el-input show-password v-model="user.confirmPassword" placeholder="确认密码"></el-input>
        </el-form-item>
      </el-form>
      <div style="margin: 15px 0;text-align: center">
        <el-button type="primary" @click="update">确认修改</el-button>
      </div>
    </el-card>
  </div>
</template>

<script>
export default {
  name: "Password",
  data() {
    const validatePassword = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请确认密码'));
      } else if(value!==this.user.newPassword){
        callback(new Error('两次输入密码不一致'));
      }else{
        callback();
      }
    };
    return {
      user: JSON.parse(localStorage.getItem('honey-user') || '{}'),
      rules: {
        password: [
          {required: true, message: '请输入原始密码', trigger: 'blur'}
        ],
        newPassword: [
          {required: true, message: '请输入新密码', trigger: 'blur'}
        ],
        confirmPassword: [
          {required: true, validator: validatePassword, trigger: 'blur'}
        ]
      }
    }
  },
  methods: {
    handleAvatarSuccess(response,file,fileList){
      // 把user的头像属性设置为上传成功后的图片地址
      this.user.avatar = response.data
    },
    update(){
      this.$refs.formRef.validate((valid)=> {
        if (valid) {
          this.user.password = this.user.newPassword
          //保存当前用户信息到数据库
          this.$request.put('/user/update',this.user).then(res=>{
            if(res.code==='200'){
              this.$message.success('保存成功')
              this.$router.push('/login')
            }else{
              this.$message.error(res.msg)
            }
          })
        }
      })
    }
  }
}
</script>

<style scoped>
/deep/.el-form-item__label{
  font-weight: bold !important;
}
</style>