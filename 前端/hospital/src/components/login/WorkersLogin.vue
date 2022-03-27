<template>
  
<div class="login">
      <img :src="imgSrc" width="100%" height="100%" alt="" />
      <div class="loginPart">
        <h2>职工登录</h2>
        <el-form ref="loginForm" :model="worker" :rules="rules">
          <el-form-item prop="account">
          <div class="inputElement" >
            <el-input v-model="worker.account" placeholder="请输入用户名" prop="account"></el-input>
          </div>
          </el-form-item>
          <el-form-item prop="password">
          <div class="inputElement" >
            <el-input v-model="worker.password" placeholder="请输入密码" prop="password"></el-input>
          </div>
          </el-form-item>
          <div align="center">
          <el-button type="primary" icon="el-icon-upload" @click="doLogin('loginForm')" >登录</el-button>
          </div>
        </el-form>
      </div>
  </div>

</template>

<script>
import qs from 'qs'
export default {
   data() {
      return {
      imgSrc:require("../../assets/css/image/医院背景.jpg"),   //背景图片
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
                          //将当前用户id存入本地浏览器
                          // window.localStorage.setItem("id",this.worker.id)
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

.loginPart{
    position:absolute;
    /*定位方式绝对定位absolute*/
    top:50%;
    left:50%;
    /*顶和高同时设置50%实现的是同时水平垂直居中效果*/
    transform:translate(-50%,-50%);
    /*实现块元素百分比下居中*/
    width:450px;
    padding:50px;
    background: rgba(0,0,0,.5);
    /*背景颜色为黑色，透明度为0.8*/
    box-sizing:border-box;
    /*box-sizing设置盒子模型的解析模式为怪异盒模型，
    将border和padding划归到width范围内*/
    box-shadow: 0px 15px 25px rgba(0,0,0,.5);
    /*边框阴影  水平阴影0 垂直阴影15px 模糊25px 颜色黑色透明度0.5*/
    border-radius:15px;
    /*边框圆角，四个角均为15px*/
  }
  .loginPart h2{
    margin:0 0 30px;
    padding:0;
    color: #fff;
    text-align:center;
    /*文字居中*/
  }
  .loginPart .inputbox{
    position:relative;
  }
  .loginPart .inputElement input{
    width: 100%;
    padding:10px 0;
    font-size:16px;
    color:#fff;
    letter-spacing: 1px;
    /*字符间的间距1px*/
    margin-bottom: 30px;
    border:none;
    border-bottom: 1px solid #fff;
    outline:none;
    /*outline用于绘制元素周围的线
    outline：none在这里用途是将输入框的边框的线条使其消失*/
    background: transparent;
    /*背景颜色为透明*/
  }

  .login{
    width:100%;
    height:100%;
  }

</style>
