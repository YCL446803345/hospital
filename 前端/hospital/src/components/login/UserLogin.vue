<template>
  
<div class="login">
      <img :src="imgSrc" width="100%" height="100%" alt="" />
      <div class="loginPart">

        <h2>用户登录</h2>
        <el-form ref="loginForm" :model="user" :rules="rules">
          <el-form-item prop="telephone">
          <div class="inputElement" >
            <el-input v-model="user.telephone" placeholder="请输入手机号" prop="telephone"></el-input>
          </div>
          </el-form-item>
          <el-form-item prop="password">
          <div class="inputElement" >
            <el-input v-model="user.password" placeholder="请输入密码" prop="password" show-password></el-input>
          </div>
          </el-form-item>
          <div align="center">
          <el-button type="primary" icon="el-icon-loading" @click="doLogin('loginForm')" >登录</el-button>
          <el-button type="primary" icon="el-icon-s-custom" @click="doRegister" >注册</el-button>
          <el-button type="primary" icon="el-icon-close" @click="back" >返回</el-button>
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
            imgSrc:require("../../assets/css/image/用户前台.gif"),   //背景图片
            user: {
                telephone: "",
                password: ""
            },
            rules:{ 
                telephone:[
                        {required: true, message: '请输入手机号', trigger: 'blur'},
                        { min: 11, max: 11, message: '手机号格式不正确', trigger: 'blur' },
                        
                    ],
                password:[
                        {required: true, message: '请输入密码', trigger: 'blur'},
                        { min: 3, max: 3, message: '密码不为3位数或不一致', trigger: 'blur' }
                ]
            }, 
      }
   },
   created(){

   },
   methods:{    

     back(){
  this.$router.push('/')
},
       doRegister(){
 this.$router.push('/gotoUserRegister')
       },


     doLogin(forName){

        let data = {telephone:this.user.telephone,password:this.user.password}
     


        let reqUrl = qs.stringify(data)
            this.$refs[forName].validate((valid)=>{
              //表单验证通过
              if(valid){
               
                  //跨域后的url
                  this.$axios.post("test/userLogin",reqUrl,{
                      Headers:{"Content-Type":"application/x-www/form-urlencoded"}
                  })
                  .then(res=>{
                      if(res.data.status == 200){
                          //消息框
                        
                          this.$router.push('/')
                        this.$message({
                              showClose: true,
                              message: '欢迎你，'+this.user.telephone,
                              type: 'success',
                              duration:1500
                          });
                          //获取响应过来的token，存入本地浏览器
                          window.localStorage.setItem("tokenStr",res.data.data)
                          //将当前用户名存入本地浏览器
                          window.localStorage.setItem("telephone",this.user.telephone)
                          //将当前用户id存入本地浏览器
                          // window.localStorage.setItem("id",this.worker.id)
                          //跳转
                          this.$router.push('/gotoUserHome')
                        
                      }else{
                          this.$router.push('/gotoUserLogin')
                          this.$message({
                                  showClose: true,
                                  message: res.data.msg,
                                  type: 'error',
                                  duration:1500,
                          })
                      }
                  }).catch(res =>{
                      this.$router.push('/gotoUserLogin')
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
