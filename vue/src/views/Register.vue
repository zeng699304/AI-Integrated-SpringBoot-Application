<template>
  <div style="height: 100vh;overflow: hidden;display: flex;align-items: center;justify-content: center;background-color: #518ae2">
    <div style="display: flex;background-color: white;width: 70%;border-radius: 5px">
      <div style="flex: 1">
        <img src="@/assets/register.png" style="width: 100%;border-radius: 5px">
      </div>
      <div style="flex: 1;display: flex;align-items: center;justify-content: center">
        <el-form :model="user" :rules="rules" ref="registerRef" style="width: 80%">
          <div style="font-size: 20px;font-weight: bold;text-align: center;margin-bottom: 20px">欢迎注册后台管理系统</div>
          <el-form-item prop="username">
            <el-input prefix-icon="el-icon-user" placeholder="请输入账号" v-model="user.username"></el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input prefix-icon="el-icon-lock" show-password placeholder="请输入密码" v-model="user.password"></el-input>
          </el-form-item>
          <el-form-item prop="confirmPass">
            <el-input prefix-icon="el-icon-lock" show-password placeholder="请确认密码" v-model="user.confirmPass"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" style="width: 100%" @click="register">注册</el-button>
          </el-form-item>
          <div style="display: flex;justify-content: space-between">
            <div>拥有账号？请<span style="color: #409EFF;cursor: pointer"  @click="$router.push('/login')">登录</span></div>
          </div>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script>

export default {
  name: "Register",
  data(){
    const validatePassword = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请确认密码'));
      } else if(value!==this.user.password){
        callback(new Error('两次输入密码不一致'));
      }else{
        callback();
      }
    };
    return{
      user:{
        username:'',
        password:'',
        confirmPass:''
      },
      rules: {
        username: [
          {required: true, message: '请输入账号', trigger: 'blur'},
        ],
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'},
        ],
        confirmPass: [
          { validator: validatePassword, trigger: 'blur' },
        ],
      }
    }
  },
  methods:{
    register(){
      this.$refs["registerRef"].validate((valid) => {
        if (valid) {
          //验证通过
          this.$request.post('/register',this.user).then(res=>{
            if(res.code=='200'){
              this.$router.push('/login')
              this.$message.success("注册成功")
            }else {
              this.$message.error(res.msg)
            }
          })
        }
      })
    }
  }
}
</script>

<style>

</style>