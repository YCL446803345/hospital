<template>

  <div class="login">
    <img :src="imgSrc" width="100%" height="100%" alt="" />
    <div class="loginPart">
      <h2>用户注册</h2>
      <el-form ref="loginForm" :model="user" :rules="rules">
        <el-form-item prop="telephone">
          <div class="inputElement">
            <el-input v-model="user.telephone" placeholder="请输入手机号" prop="telephone"></el-input>
          </div>
        </el-form-item>
        <el-form-item prop="password">
          <div class="inputElement">
            <el-input v-model="user.password" placeholder="请输入密码" prop="password" show-password></el-input>
          </div>
        </el-form-item>
        <el-form-item prop="spare1">
          <div class="inputElement">
            <el-input v-model="user.spare1" placeholder="请输入邮箱" prop="spare1"></el-input>
            <el-button type="primary" v-if="msg=='点击发送验证码'" @click="sendCheck()" plain>{{msg}}</el-button>
            <el-button type="primary" v-if="msg!='点击发送验证码'" disabled plain>{{msg}}</el-button>
          </div>
        </el-form-item>
        <el-form-item prop="spare2">
          <div class="inputElement">
            <el-input maxlength="4" v-model="user.spare2" placeholder="验证码" prop="spare2"></el-input>
          </div>
        </el-form-item>
        <div align="center">
          <el-button type="primary" icon="el-icon-check" @click="doRegister('loginForm')">提交</el-button>
          <el-button type="primary" icon="el-icon-close" @click="back()">返回</el-button>
        </div>
      </el-form>
    </div>
  </div>

</template>

<script>
  import qs from 'qs'
  export default {
    data() {
      var checkPhone = (rule, value, callback) => {
        const phoneReg = /^1[3|4|5|7|8][0-9]{9}$/
        if (!value) {
          return callback(new Error('电话号码不能为空'))
        }
        setTimeout(() => {
          // Number.isInteger是es6验证数字是否为整数的方法,但是我实际用的时候输入的数字总是识别成字符串
          // 所以我就在前面加了一个+实现隐式转换

          if (!Number.isInteger(+value)) {
            callback(new Error('请输入数字值'))
          } else {
            if (phoneReg.test(value)) {
              callback()
            } else {
              callback(new Error('电话号码格式不正确'))
            }
          }
        }, 100)
      }
      var checkEmail = (rule, value, callback) => {
        const mailReg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/
        if (!value) {
          return callback(new Error('邮箱不能为空'))
        }
        setTimeout(() => {
          if (mailReg.test(value)) {
            callback()
          } else {
            callback(new Error('请输入正确的邮箱格式'))
          }
        }, 100)
      }


      return {
        imgSrc: require("../../assets/css/image/用户注册.jpg"), //背景图片
        user: {
          telephone: "",
          password: "",
          spare1: '',
          spare2: '',
        },
        time: 60,
        msg: "点击发送验证码",
        rules: {
          // telephone:[
          //         {required: true, message: '请输入手机号', trigger: 'blur'},
          //         { min: 11, max: 11, message: '长度为 11 个字符', trigger: 'blur' },

          //     ],
          password: [{
              required: true,
              message: '请输入密码',
              trigger: 'blur'
            },
            {
              min: 3,
              max: 3,
              message: '长度为3 个字符',
              trigger: 'blur'
            }
          ],
          telephone: [{
            validator: checkPhone,
            trigger: 'blur'
          }],
          spare1: [{
            validator: checkEmail,
            trigger: 'blur'
          }],
          spare2: [{
              required: true,
              message: '请输入验证码',
              trigger: 'blur'
            },
            // {len:4, type: 'number',message: '长度为4个数字', trigger: 'blur' },
          ]

        },
      }
    },
    created() {

    },
    methods: {
      //发送验证码
      sendCheck() {
 
        if(this.spare1!=null){
            this.$axios.get("/test/sendCheck", {
            params: {
              email: this.user.spare1
            }
          })
          this.changeButton();
        }else{
          this.$message({
                  showClose: true,
                  message: "邮箱不能为空",
                  type: "error",
                  duration: 600,
                  duration: 2000,
                });
        }
        
      },
      //验证码按钮变化
      changeButton() {
        if (this.time >= 0) {
          window.setTimeout(() => {
            this.msg = "已发送,请" + this.time + "秒后再点击";
            this.time = this.time - 1;
            this.changeButton();
          }, 1000)
        } else {
          this.msg = "点击发送验证码";
          this.time = 60;
        }
      },
      back() {
        this.$router.push('/gotoUserLogin')
      },
      doRegister(forName) {
        let data = {
          telephone: this.user.telephone,
          password: this.user.password
        }
        let reqUrl = qs.stringify(data)
        this.$refs[forName].validate((valid) => {
          //表单验证通过
          if (valid) {
            //发送axios请求
            this.$axios.post("/test/user/add", this.user).then((res) => {
              if (res.data.status == 200) {
                this.$message({
                  showClose: true,
                  message: "注册成功！即将跳转到登录页",
                  type: "success",
                  duration: 600,
                  duration: 2000,
                  onClose: () => {
                    //跳转页面或执行方法
                    this.$router.push('/gotoUserLogin')
                  }
                });
              } else if (res.data.status == 888) {
                this.$message({
                  showClose: true,
                  message: "验证码错误,注册失败",
                  type: "error",
                  duration: 600,
                  duration: 2000,
                  onClose: () => {
                    this.user.spare2 = "";
                  }
                });
              } else if (res.data.status == 2008) {
                this.$message({
                  showClose: true,
                  message: "用户已存在",
                  type: "error",
                  duration: 600,
                  duration: 2000,
                  onClose: () => {
                    
                  }
                });
              } else {
                {
                  this.$message({
                    showClose: true,
                    message: "注册失败",
                    type: "error",
                    duration: 600,
                  });
                }
              }
            });
          } else {
            //验证不通过
            return false;
          }
        })
      }
    }
  }

</script>

<style>
  .loginPart {
    position: absolute;
    /*定位方式绝对定位absolute*/
    top: 50%;
    left: 50%;
    /*顶和高同时设置50%实现的是同时水平垂直居中效果*/
    transform: translate(-50%, -50%);
    /*实现块元素百分比下居中*/
    width: 450px;
    padding: 50px;
    background: rgba(0, 0, 0, .5);
    /*背景颜色为黑色，透明度为0.8*/
    box-sizing: border-box;
    /*box-sizing设置盒子模型的解析模式为怪异盒模型，
    将border和padding划归到width范围内*/
    box-shadow: 0px 15px 25px rgba(0, 0, 0, .5);
    /*边框阴影  水平阴影0 垂直阴影15px 模糊25px 颜色黑色透明度0.5*/
    border-radius: 15px;
    /*边框圆角，四个角均为15px*/
  }

  .loginPart h2 {
    margin: 0 0 30px;
    padding: 0;
    color: #fff;
    text-align: center;
    /*文字居中*/
  }

  .loginPart .inputbox {
    position: relative;
  }

  .loginPart .inputElement input {
    width: 100%;
    padding: 10px 0;
    font-size: 16px;
    color: #fff;
    letter-spacing: 1px;
    /*字符间的间距1px*/
    margin-bottom: 30px;
    border: none;
    border-bottom: 1px solid #fff;
    outline: none;
    /*outline用于绘制元素周围的线
    outline：none在这里用途是将输入框的边框的线条使其消失*/
    background: transparent;
    /*背景颜色为透明*/
  }

  .login {
    width: 100%;
    height: 100%;
  }

</style>
