<template>
  <div style="height: 100vh;overflow: hidden;display: flex;align-items: center;justify-content: center;background-color: rgba(108,232,174,0.81)">
    <div style="display: flex;background-color: white;width: 70%;border-radius: 5px">
      <div style="flex: 1">
        <img src="@/assets/login.png" style="width: 100%;border-radius: 5px">
      </div>
      <div style="flex: 1;display: flex;align-items: center;justify-content: center">
        <el-form :model="user" :rules="rules" ref="loginRef" style="width: 80%">
          <div style="font-size: 20px;font-weight: bold;text-align: center;margin-bottom: 20px">欢迎登陆后台管理系统</div>
          <el-form-item prop="username">
            <el-input prefix-icon="el-icon-user" placeholder="请输入账号" v-model="user.username"></el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input prefix-icon="el-icon-lock" show-password placeholder="请输入密码" v-model="user.password"></el-input>
          </el-form-item>
          <el-form-item prop="code">
            <div style="display: flex">
              <el-input prefix-icon="el-icon-circle-check" style="flex: 1" v-model="user.code"></el-input>
              <div style="flex: 1;height: 31.99px">
                <valid-code @update:value="getCode"/>
              </div>
            </div>
          </el-form-item>
          <el-form-item>
            <el-button type="success" style="width: 100%" @click="login">登陆</el-button>
          </el-form-item>
          <div style="display: flex;justify-content: space-between">
            <div>还没有账号？请<span style="color: #0f9876;cursor: pointer" @click="$router.push('/register')">注册</span></div>
            <div style="color: #0f9876;cursor: pointer" @click="handleForgetPass">忘记密码</div>
          </div>
        </el-form>
      </div>
    </div>

    <el-dialog title="忘记密码" :visible.sync="forgetPassDialogVis" width="30%">
      <el-form :model="forgetUserForm" label-width="80px" style="padding-right: 20px">
        <el-form-item label="用户名">
          <el-input v-model="forgetUserForm.username" autocomplete="off" placeholder="请输入用户名"></el-input>
        </el-form-item>
        <el-form-item label="手机号">
          <el-input v-model="forgetUserForm.phone" autocomplete="off" placeholder="请输入手机号"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="forgetPassDialogVis = false">取 消</el-button>
        <el-button type="primary" @click="resetPassword">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import ValidCode from "@/components/ValidCode";

export default {
  name: "Login",
  components: {
    ValidCode
  },
  data(){
      const validateCode = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入验证码'));
      } else if(value.toLowerCase()!==this.code){
        callback(new Error('验证码错误'));
      }else{
        callback();
      }
    };

    return{
      forgetUserForm:{},
      forgetPassDialogVis: false,
      code:'',
      user:{
        code:'',
        username:'',
        password:''
      },
      rules: {
        username: [
          {required: true, message: '请输入账号', trigger: 'blur'},
        ],
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'},
        ],
        code: [
          { validator: validateCode, trigger: 'blur' }
        ]
      }
    }
  },
  methods:{
    handleForgetPass(){ //初始化表单的数据
      this.forgetUserForm={}
      this.forgetPassDialogVis=true
    },
    resetPassword(){
      this.$request.put('/password',this.forgetUserForm).then(res=>{
        if(res.code=='200'){
          this.$message.success("重置成功")
          this.forgetPassDialogVis=false
        }else {
          this.$message.error(res.msg)
        }
      })
    },
    getCode(code){
      console.log(code.toLowerCase())
      this.code=code.toLowerCase()
    },
    login(){
      this.$refs["loginRef"].validate((valid) => {
        if (valid) {
          //验证通过
          this.$request.post('/login',this.user).then(res=>{
            if(res.code=='200'){
              this.$router.push('/')
              this.$message.success("登陆成功")
              localStorage.setItem('honey-user',JSON.stringify(res.data)) //存储用户数据
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