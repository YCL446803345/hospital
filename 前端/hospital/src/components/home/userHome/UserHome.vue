<template>
  <el-container>
    <el-aside :style="{width:collpaseWidth}">
      <!--default-active代表导航栏默认选中哪个index, :collapse决定导航栏是否展开，为boolean类型
            :router决定导航栏是否开启路由模式，即在菜单item上设置路由是否生效，值为boolean类型-->
      <el-menu default-active="0" class="el-menu-vertical-demo" :collapse="isCollapse" :router="true">

      </el-menu>
    </el-aside>

    <el-container>
      <el-header style="height:80px;">
        <el-row style="margin: 15px 15px">
          <el-col :span="9">
            <h1>成都协和医院</h1>
          </el-col>
          <el-col :span="9">
            <h1>病人预约</h1>
          </el-col>
          <el-col :span="6" class="col_r">
            <h1 style="color:red">用户：{{telephone}} <el-button @click="logout">退出</el-button>
            </h1>

          </el-col>
        </el-row>
      </el-header>


      <el-main>


        <div class="login">
          <!-- 用户预约病人表 -->
          <div class="loginPart">
            <h2>万物皆虚,万事皆允</h2>
            <el-form :model="inHospitalTable" :rules="rules" ref="ruleForm">
              <el-form-item prop="patientName">
                <div class="inputElement">
                  <el-input v-model="inHospitalTable.patientName" placeholder="请输入病人名字"></el-input>
                </div>
              </el-form-item>
              <el-form-item prop="patientSex">
                <div class="inputElement">
                  <el-radio v-model="inHospitalTable.patientSex" label="男" style="color:white"></el-radio>
                  <el-radio v-model="inHospitalTable.patientSex" label="女" style="color:white"></el-radio>
                </div>
              </el-form-item>
              <el-form-item prop="patientAge">
                <div class="inputElement">
                  <el-input v-model="inHospitalTable.patientAge" placeholder="请输入年龄" onkeyup="this.value=this.value.replace(/\D|^0/g,'')" onafterpaste="this.value=this.value.replace(/\D|^0/g,'')"></el-input>
                </div>
              </el-form-item>
              <el-form-item prop="cardId">
                <div class="inputElement">
                  <el-input v-model="inHospitalTable.cardId" placeholder="请输入身份证号码"></el-input>
                </div>
              </el-form-item>
              <el-form-item prop="deptId">
                <div class="inputElement">
                  <el-select v-model="inHospitalTable.deptId" placeholder="请选择部门">
                    <el-option v-for="dept in deptData" :label="dept.name" :value="dept.id" :key="dept.id"></el-option>
                  </el-select>
                </div>
              </el-form-item>
              <el-form-item prop="reason">
                <div class="inputElement">
                  <el-input v-model="inHospitalTable.reason" placeholder="请输入病情"></el-input>
                </div>
              </el-form-item>


              <div align="center">

                <div class="inputElement">
                  <el-button type="info" icon="el-icon-user-solid" @click="addInHospitalTable('ruleForm')">病人预约</el-button>
                  <el-button type="info" icon="el-icon-user" @click="getInHospitalTableByTelephone()">预约查询</el-button>
                </div>
              </div>
            </el-form>
          </div>
        </div>

        <!-- 病人预约详情表 -->
        <el-dialog title="病人预约情况" :visible.sync="Visible" class="mydig" width="60%" :showClose="false" align="center">

          <el-table :data="inHospitalTableData">
            <!-- 
                el-table-column列
                prop 绑定data数组中对象的属性
             -->

            <el-table-column prop="patientName" label="姓名" width="100"> </el-table-column>
            <el-table-column prop="patientSex" label="性别" width="100"> </el-table-column>
            <el-table-column prop="patientAge" label="年龄" width="100"> </el-table-column>
            <el-table-column prop="cardId" label="身份证号码" width="200"> </el-table-column>
            <el-table-column prop="telephone" label="手机号码" width="130"> </el-table-column>
            <el-table-column prop="deptName" label="科室" width="100"> </el-table-column>
            <el-table-column prop="reason" label="病情" width="100"> </el-table-column>
            <el-table-column prop="inHosptialTime" label="预约时间" width="100"> </el-table-column>
            <el-table-column prop="status" label="状态" width="120">
              <template slot-scope="scope">
                <span v-if="scope.row.status=='1'">已预约</span>
                <span v-if="scope.row.status=='2'">已审核</span>
                <span v-if="scope.row.status=='3'">住院中</span>
              </template>
            </el-table-column>

          </el-table>

          <div slot="footer" class="dialog-footer">

            <el-button @click="Visible = false">返回</el-button>
          </div>
        </el-dialog>

        <!--路由渲染-->
        <router-view></router-view>
      </el-main>
      <el-footer class="footer">
        <h4>版权所有@成都协和医院 地址：四川省成都市二环路东五段成仁路口 联系电话：028 82216779 Copyright 2014-2022 Macgn Association All Rights 保留所有权利
        </h4>
      </el-footer>
    </el-container>
  </el-container>

</template>

<script>
  export default {
    data() {
      return {
        rules: {
          patientName: [{
            required: true,
            message: '请输入病人名字',
            trigger: 'blur'
          }, ],
          patientSex: [{
            required: true,
            message: '请选择性别',
            trigger: 'blur'
          }, ],
          patientAge: [{
            required: true,
            message: '请输入病人年龄',
            trigger: 'blur'
          }, ],
          cardId: [{
            required: true,
            message: '请输入病人身份证号码',
            trigger: 'blur'
          }, ],
          deptId: [{
            required: true,
            message: '请选择科室',
            trigger: 'blur'
          }, ],

          reason: [{
            required: true,
            message: '请输入病情',
            trigger: 'blur'
          }, ]
        },



        inHospitalTable: {
          patientName: "",
          patientSex: "",
          patientAge: "",
          cardId: "",
          telephone: "",
          deptId: "",
          bedId: "",
          reason: "",
          inHosptialTime: "",
          status: "",
        },

        deptData: [],
        bedData: [],
        inHospitalTableData: [],
        Visible: false,
        formLabelWidth: "120px",
        isCollapse: false, // 决定左侧导航栏是否展开
        fits: 'scale-down',
        headers: "",
        menuDate: [],
        status: '2',

      }
    },
    computed: {
      collpaseIcon: function () { // 左侧导航栏是否展开状态的图标
        // 如果是展开状态就图标向右，否则图标向左
        return this.isCollapse ? 'el-icon-s-fold' : 'el-icon-s-unfold';
      },
      collpaseWidth: function () { // 左侧导航栏是否展开状态的宽度
        // 如果是展开状态就导航栏宽度为65px，否则200px
        return this.isCollapse ? '65px' : '200px';
      }
    },
    methods: {


      //缴纳手续费
      pay(value) {
        this.$axios.get("/test/pay", {
          params: {
            phone: value
          }
        }).then(res => {
          const divForm = document.getElementsByTagName("div");
          if (divForm.length) {
            document.body.removeChild(divForm[0]);
          }
          const div = document.createElement("div");
          div.innerHTML = res.data;
          document.body.appendChild(div);
          document.forms[0].submit();
          document.forms[0].setAttribute("target", "_blank"); // 新开窗口跳转
        })
      },
      //部门列表
      findDeptList() {
        //axios请求拿数据
        this.$axios.get("/test/dept/list", {
            params: {},
          })
          .then((res) => {
            //设置部门列表数据
            this.deptData = res.data.data;
          })
          .catch((res) => {
            this.$message({
              type: "error",
              message: "获取部门列表错误!",
            });
          });
      },
      //床位列表
      findBedList() {
        //axios请求拿数据
        this.$axios.get("/test/findBedList", {
            params: {},
          })
          .then(res => {
            console.log(res.data.data);
            this.bedData = res.data.data;
          }).catch((res) => {
            this.$message({
              type: "error",
              message: "获取床位列表错误!",
            });
          });
      },


      //添加病人
      addInHospitalTable(formName) {

         this.$refs[formName].validate((valid) => {
          if (valid) {
 this.$axios.get("/test/queryUserStatus", {
          params: {
            phone: this.telephone
          }
        }).then(res => {
          if (window.localStorage.getItem("status") == "1") {
            window.localStorage.setItem("status", 2)
            window.localStorage.setItem("inHospitalTable", JSON.stringify(this.inHospitalTable))
            this.$alert('请先缴纳手续费', '缴费', {
              confirmButtonText: '确定',
              callback: action => {
                this.pay(this.telephone);
              }
            })
          } else {
            this.inHospitalTable.telephone = window.localStorage.getItem("telephone")
            this.$axios.post("/test/inHospitalTable/add", this.inHospitalTable).then((res) => {
              if (res.data.status == 200) {

                this.$message({
                  showClose: true,
                  message: "添加成功",
                  type: "success",
                  duration: 600,
                });
                this.inHospitalTable = {};
              } else if (res.data.status == 2008) {
                this.$message({
                  showClose: true,
                  message: res.data.msg,
                  type: "error",
                  duration: 600,
                  duration: 2000,
                  onClose: () => {
                    this.user.spare2 = "";
                  }
                });
              } else {
                this.$message({
                  showClose: true,
                  message: "添加失败",
                  type: "error",
                  duration: 600,
                });
              }
            });

          }
          this.status = res.data;
        })
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },

      //查询病人预约
      getInHospitalTableByTelephone() {
        this.Visible = true;

        //发送axios请求
        //axios请求拿数据
        this.$axios
          .get("/test/inHospitalTable/getByTelephone", {
            params: {
              telephone: this.telephone
            },
          })
          .then((res) => {
            console.log(res.data.data);
            //设置病人预约列表数据
            this.inHospitalTableData = res.data.data;
          })
          .catch((res) => {
            this.$message({
              type: "error",
              message: "获取病人预约列表错误!",
            });
          });
      },


      logout() {
        this.$confirm('是否退出本系统?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          window.localStorage.removeItem("tokenStr");
          this.$router.push("/");
        })


      },
      changeCollapse: function () { // 更改左侧导航栏展示状态
        this.isCollapse = !this.isCollapse;
      },

    },



    mounted: function () {
      if (location.href.indexOf("#reloaded") == -1) {
        location.href = location.href + "#reloaded";
        location.reload();
      }
    },

    created() {

      //从本地浏览器拿名字
      this.telephone = window.localStorage.getItem("telephone")

      this.status = window.localStorage.getItem("status")


      this.$axios.get("/test/perms/findMenuPerms", {
          params: {
            telephone: this.telephone
          }
        }).then(res => {
          console.log(res.data)
          if (res.data.status == 200) {
            this.menuDate = res.data.data
          }
        }),

        this.findDeptList();
      this.findBedList();
      if (this.status == "2") {
        this.status = "1"
        window.localStorage.setItem("status", 1)
        this.inHospitalTable = JSON.parse(window.localStorage.getItem("inHospitalTable"))
        //发送axios请求
        this.inHospitalTable.telephone = window.localStorage.getItem("telephone")
        this.$axios.post("/test/inHospitalTable/add", this.inHospitalTable).then((res) => {
          if (res.data.status == 200) {
            this.$message({
              showClose: true,
              message: "添加成功",
              type: "success",
              duration: 600,
            });
            this.inHospitalTable = {};
          } else {
            this.$message({
              showClose: true,
              message: "添加失败",
              type: "error",
              duration: 600,
            });
          }
        });
      }
    }

  }

</script>

<style scoped>
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


  /*整体显示区域布局样式*/
  .el-container {
    height: 100%;
  }

  .el-header {
    padding: 0;
    background: #E6E6F2;

  }

  .el-main {
    padding: 0;
    background: url("../../../assets/css/image/宣言.jpg") center center no-repeat;
  }

  /*左边导航栏具体样式*/
  .el-menu-vertical-demo.el-menu {
    background: #FFDAC8;
    padding-left: 20px;
    text-align: left;
    height: 100%;
    padding: 0;
  }

  el-container>.el-menu-vertical-demo.el-menu {
    padding: 0;
  }

  .el-submenu .el-menu-item,
  .el-menu-item {
    min-width: 50px;
  }

  .el-menu-item {
    padding: 0;
    text-align: center;
  }

  .footer {
    background: #C4E1E1;
  }

  h4 {
    text-align: center;
    line-height: 50px;
  }

  .mydig {
    background: url("../../../assets/css/image/OIP-C.jpg");
  }

</style>
