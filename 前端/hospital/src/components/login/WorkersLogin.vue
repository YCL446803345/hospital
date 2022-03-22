<template>

  <div class="login" clearfix>
    <div class="login-wrap">
      <el-row type="flex" justify="center">
        <el-form ref="loginForm" :model="worker" :rules="rules"  status-icon label-width="80px">
          <h3>登录</h3>
          <hr>
          <el-form-item prop="account" label="用户名">
            <el-input v-model="worker.account" placeholder="请输入用户名" prefix-icon></el-input>
          </el-form-item>
          <el-form-item id="password" prop="password" label="密码">
            <el-input v-model="worker.password" show-password placeholder="请输入密码"></el-input>
          </el-form-item>
          <router-link to="/">找回密码</router-link>
          <router-link to="/register">注册账号</router-link>
          <el-form-item>
            <el-button type="primary" icon="el-icon-upload" @click="doLogin('loginForm')">登 录</el-button>
          </el-form-item>
        </el-form>
      </el-row>
    </div>
  </div>

</template>

<script>
import qs from 'qs'
export default {
   data() {
      return {
      worker: {
        account: "",
        password: ""
      },
      rules:{ 
        account:[
                {required: true, message: '请输入用户名', trigger: 'blur'},
                { min: 3, max: 12, message: '长度在 3 到 12 个字符', trigger: 'blur' }
            ],
        password:[
                {required: true, message: '请输入密码', trigger: 'blur'},
                { min: 3, max: 12, message: '长度在 4 到 12 个字符', trigger: 'blur' }
        ]
      }, 
      }
   },
   created(){

   },
   methods:{    
     doLogin(forName){
        let data = {account:this.worker.account,password:this.worker.password}
        let reqUrl = qs.stringify(data)
            this.$refs[forName].validate((valid)=>{
              //表单验证通过
              if(valid){
                  //跨域后的url
                  this.$axios.post("api/login",reqUrl,{
                      Headers:{"Content-Type":"application/x-www/form-urlencoded"}
                  })
                  .then(res=>{
                      if(res.data.status == 200){
                          //消息框
                        this.$message({
                              showClose: true,
                              message: '欢迎你，'+this.worker.account,
                              type: 'success',
                              duration:1500
                          });
                          //获取响应过来的token，存入本地浏览器
                          window.localStorage.setItem("tokenStr",res.data.data)
                          //将当前用户名存入本地浏览器
                          window.localStorage.setItem("account",this.worker.account)
                          //跳转
                          this.$router.push('/gotoHome')
                      }else{
                          this.$router.push('/gotoWorkesLogin')
                          this.$message({
                                  showClose: true,
                                  message: res.data.msg,
                                  type: 'error',
                                  duration:1500,
                          })
                      }
                  }).catch(res =>{
                      this.$router.push('/gotoWorkesLogin')
                      this.$message({
                          showClose: true,
                          message: '异常',
                          type: 'success',
                          duration:1500
                          });
                          //跳转
                  })
              } else{
                  //验证不通过
                  return false;
              }
          })
     }
   }
}
</script>

<style>

.login {
  width: 100%;
  height: 740px;
  /* background: url("../assets/images/bg1.png") no-repeat; */
  background-size: cover;
  overflow: hidden;
}
.login-wrap {
  /* background: url("../assets/images/login_bg.png") no-repeat; */
  background-size: cover;
  width: 400px;
  height: 300px;
  margin: 215px auto;
  overflow: hidden;
  padding-top: 10px;
  line-height: 40px;
}
#password {
  margin-bottom: 5px;
}
h3 {
  color: #0babeab8;
  font-size: 24px;
}
hr {
  background-color: #444;
  margin: 20px auto;
}
a {
  text-decoration: none;
  color: #aaa;
  font-size: 15px;
}
a:hover {
  color: coral;
}
.el-button {
  width: 80%;
  margin-left: -50px;
}

</style>
