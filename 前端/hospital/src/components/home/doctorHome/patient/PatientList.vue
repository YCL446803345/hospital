<template>
  <div>
    <!-- 面包xie导航 -->
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item
        ><a href="#/patientList">住院病人信息管理</a></el-breadcrumb-item
      >
      <el-breadcrumb-item>住院信息管理列表</el-breadcrumb-item>
    </el-breadcrumb>
    <el-row style="margin-top: 10px; margin-bottom: 10px">
      <el-col :span="2">
        <el-input v-model="name" placeholder="姓名"> </el-input>
      </el-col>

      <el-col :span="3">
        <el-input v-model="no" placeholder="编号"> </el-input>
      </el-col>

      <el-col :span="2">
        <el-select v-model="gender" placeholder="性别">
          <!-- <el-option label="性别" value=""></el-option> -->
          <el-option label="男" value="1"></el-option>
          <el-option label="女" value="2"></el-option>
        </el-select>
      </el-col>

      <el-col :span="4">
        <el-input v-model="cardId" placeholder="身份证"> </el-input>
        <!--带搜索按钮的文本框 -->
      </el-col>

      <el-col :span="1" style="margin-left: 2px">
        <el-button type="success" @click="search">查询</el-button>
      </el-col>

      <el-col :span="1" style="margin-left: 2px">
        <el-button
          type="warning"
          @click="(name = ''),(no = ''),(paheNum = 1),(pageSize = 5),(gender = ''),(cardId = '')">清空</el-button>
      </el-col>
    </el-row>

        <!-- el-table数据表格
        :data 动态绑定 data中对象数组
        scope.row 表示对象数组的当前行对象 -->
        
    <el-table :data="patientList" style="width: 100%">
      <el-table-column type="index" :index="getIndex" label="序号" width="50">
      </el-table-column>

      <el-table-column prop="name" label="姓名" width="80"> </el-table-column>

      <el-table-column prop="no" label="病人编号" width="120">
      </el-table-column>

      <!-- <el-table-column
                label="头像"
                width="100">
                 <template slot-scope="scope">
                    <img  :src="scope.row.avatar" class="a-avatar" >
                </template>
            </el-table-column> -->

      <el-table-column prop="gender" label="性别" width="50">
        <template slot-scope="scope">
          {{ scope.row.gender === "1" ? "男" : "女" }}
        </template>
      </el-table-column>

      <el-table-column prop="age" label="年龄" width="80"> </el-table-column>

      <el-table-column prop="cardId" label="身份证号" width="180">
      </el-table-column>

      <el-table-column prop="phone" label="手机号" width="150">
      </el-table-column>

      <el-table-column prop="appointmenttTime" label="入院时间" width="160"></el-table-column>

      <el-table-column prop="deptName" label="科室" width="80">
      </el-table-column>

      <el-table-column prop="bedCode" label="床位编号" width="80">
        <template slot-scope="scope">
          <el-tag :type="scope.row.bedCode === '未分配' ? 'danger' : 'primary'" disable-transitions>
            {{ scope.row.bedCode }}
          </el-tag>
        </template>
      </el-table-column>

      <el-table-column prop="nurseName" label="护士名" width="80">
      </el-table-column>

      <el-table-column prop="doctorName" label="主治医生" width="80">
      </el-table-column>

      <el-table-column prop="baseDesc" label="病情" width="80">
      </el-table-column>

      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button size="mini" type="success" @click="gotoFindPatientInformation(scope.row.id)">患者详情</el-button>  
          <el-button size="mini" type="danger" @click="gotoAddConsultationApplication(
            scope.row.id,
            scope.row.name ,
            scope.row.no ,
            scope.row.age ,
            scope.row.cardId ,
            scope.row.phone ,
            scope.row.gender ,
            scope.row.deptName,
            scope.row.deptId,
            scope.row.baseDesc,
            scope.row.doctorName,
            scope.row.doctorId
            )">突发情况</el-button>
          
          <el-button size="mini" type="primary" @click="gotoAddLeaveHospital(
              scope.row.id,
              scope.row.name,
              scope.row.doctorId,
              scope.row.doctorName
              )">申请出院</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination
      background
      layout="prev, pager,next, sizes,->,total"
      :total="total"
      :current-page="pageNum"
      :page-sizes="[5, 10, 15, 20]"
      :page-size="pageSize"
      @size-change="changeSize"
      @current-change="changePage"
    >
    </el-pagination>

    <!-- 提出院 -->
    <el-dialog :visible.sync="addLeaveHospitalForm">
      <h1 align="center">申请出院</h1>
      <br />
      <template>
        <el-descriptions class="margin-top" title="" :column="3" border>
          <el-descriptions-item>
            <template slot="label"><i class="el-icon-user"></i>病人</template>
            {{ addLeaveHospital.name }}
          </el-descriptions-item>

          <el-descriptions-item>
            <template slot="label"><i class="el-icon-user"></i>主治医生</template>
            {{ addLeaveHospital.doctorName }}
          </el-descriptions-item>

        </el-descriptions>
      </template>

      <el-divider></el-divider>

      <el-form :model="addLeaveHospital">

        <el-form-item label="描述" :label-width="formLabelWidth">
          <el-input v-model="addLeaveHospital.leaveDescription" autocomplete="off"></el-input>
        </el-form-item>

      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="doAddLeaveHospital">下 达</el-button>
        <el-button @click="addLeaveHospitalForm = false;addLeaveHospital={}">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 突发情况 -->
    <el-dialog :visible.sync="addConsultationApplicationForm">
      <h1 align="center">申请会诊</h1>
      <br />
      <template>
        <el-descriptions class="margin-top" title="" :column="3" border>
          <el-descriptions-item>
            <template slot="label"><i class="el-icon-user"></i>病人</template>
            {{ addConsultationApplication.name }}
          </el-descriptions-item>

          <el-descriptions-item>
            <template slot="label"><i class="el-icon-user"></i>患者编号</template>
            {{ addConsultationApplication.no }}
          </el-descriptions-item>

          <el-descriptions-item>
            <template slot="label"><i class="el-icon-user"></i>主治医生</template>
            {{ addConsultationApplication.doctorName }}
          </el-descriptions-item>

          <el-descriptions-item>
            <template slot="label"><i class="el-icon-user"></i>年龄</template>
            {{ addConsultationApplication.age }}
          </el-descriptions-item>

          <el-descriptions-item>
            <template slot="label"><i class="el-icon-user"></i>身份证号</template>
            {{ addConsultationApplication.cardId }}
          </el-descriptions-item>
          
          <el-descriptions-item>
            <template slot="label"><i class="el-icon-user"></i>电话</template>
            {{ addConsultationApplication.phone }}
          </el-descriptions-item>

          <el-descriptions-item>
            <template slot="label"><i class="el-icon-user"></i>性别</template>
            {{ addConsultationApplication.gender ==='1'?'男':'女' }}
          </el-descriptions-item>

          <el-descriptions-item>
            <template slot="label"><i class="el-icon-user"></i>科室</template>
            {{ addConsultationApplication.deptName }}
          </el-descriptions-item>

          <el-descriptions-item>
            <template slot="label"><i class="el-icon-user"></i>症状</template>
            {{ addConsultationApplication.baseDesc }}
          </el-descriptions-item>

        </el-descriptions>
      </template>

      <el-divider></el-divider>

      <el-form :model="addConsultationApplication">

        <el-form-item label="紧急度" :label-width="formLabelWidth">
            <el-radio v-model="addConsultationApplication.consultationEmergencyId" label="1" value=1>紧急</el-radio>
            <el-radio v-model="addConsultationApplication.consultationEmergencyId" label="2" value=2>24小时</el-radio>
            <el-radio v-model="addConsultationApplication.consultationEmergencyId" label="3" value=3>一般</el-radio>
        </el-form-item>

        <el-form-item label="会诊类别" :label-width="formLabelWidth">
            <el-radio v-model="addConsultationApplication.consultationCategoryId" label="1" value=1>它科会诊</el-radio>
            <el-radio v-model="addConsultationApplication.consultationCategoryId" label="2" value=2>科内会诊</el-radio>
            <el-radio v-model="addConsultationApplication.consultationCategoryId" label="3" value=3>一般会诊</el-radio>
        </el-form-item>

        <el-form-item label="原因" :label-width="formLabelWidth">
          <el-input v-model="addConsultationApplication.reason" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="突发情况" :label-width="formLabelWidth">
          <el-input v-model="addConsultationApplication.desc" autocomplete="off"></el-input>
        </el-form-item>

      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="doAddConsultationApplication">下 达</el-button>
        <el-button @click="addConsultationApplicationForm = false;addConsultationApplication={}">取 消</el-button>
      </div>
    </el-dialog>



  </div>
</template>

<script>
export default {
  data() {
    return {
      no: "",
      name: "",
      gender: "",
      cardId: "",
      status: "",
      patientList: [],
      total: 100,
      pageNum: 1,
      pageSize: 5,
      headers: {},
      formLabelWidth: "80px",
      addLeaveHospital:{},
      addLeaveHospitalForm: false,

      addConsultationApplication:{},
      addConsultationApplicationForm: false,


    };
  },
  created() {
    this.search();
    this.headers = { tokenStr: window.localStorage.getItem("tokenStr") };
  },
  methods: {
    //打开下达医嘱列表
      gotoAddConsultationApplication(
        id,
        name ,
        no ,
        age ,
        cardId ,
        phone ,
        gender ,
        deptName,
        deptId,
        baseDesc,
        doctorName,
        doctorId
        ) {
        this.addConsultationApplication = {
        id:id,
        name:name ,
        no:no ,
        age:age ,
        cardId:cardId ,
        phone:phone ,
        gender: gender,
        deptName:deptName,
        deptId:deptId,
        baseDesc:baseDesc,
        doctorName:doctorName,
        doctorId:doctorId
      };
        this.addConsultationApplicationForm = true;
      },

      //执行突发情况
      doAddConsultationApplication() {
      
        //发送axios请求
        var consultationApplication=this.addConsultationApplication;
         console.log("所说的上档次")
        //  alert(medicalAdvice)
         console.log(consultationApplication)
        this.$axios.post("/api/doctor/gotoAddConsultationApplication",consultationApplication).then((res) => {
          console.log(res.data);
          if (res.status == 200) {
            this.$message({
              showClose: true,
              message: "下达成功",
              type: "success",
              duration: 600,
            });
            this.addConsultationApplication = {};
            this.addConsultationApplicationForm = false;
            // this.search(); //刷新列表
          } else {
            this.$message({
              showClose: true,
              message: "下达失败",
              type: "error",
              duration: 600,
            });
          }
        });
      },




      //准备出院
      gotoAddLeaveHospital(id,name,doctorId,doctorName) {
        this.addLeaveHospital = {
        id: id,
        name: name,
        doctorId: doctorId,
        doctorName: doctorName,
        
      };
        this.addLeaveHospitalForm = true;
      },

      //执行出院
      doAddLeaveHospital() {
        //发送axios请求
        var leaveHospital=this.addLeaveHospital;
         console.log("所说的上档次")
        //  alert(medicalAdvice)
         console.log(leaveHospital)
        this.$axios.post("/api/doctor/gotoAddLeaveHospital",leaveHospital).then((res) => {
          console.log(res.data);
          if (res.status == 200) {
            this.$message({
              showClose: true,
              message: "下达成功",
              type: "success",
              duration: 600,
            });
            this.addLeaveHospital = {};
            this.addLeaveHospitalForm = false;
            // this.search(); //刷新列表
          } else {
            this.$message({
              showClose: true,
              message: "下达失败",
              type: "error",
              duration: 600,
            });
          }
        });
      },


      
    //查询病人信息列表
    search() {
      console.log("---" + this.cardId);
      this.$axios
        .get("/api/doctor/getPatientList", {
          params: {
            name: this.name,
            no: this.no,
            gender: this.gender,
            cardId: this.cardId,
            status: this.status,
            pageNum: this.pageNum,
            pageSize: this.pageSize,
          },
        })
        .then((res) => {
          console.log(res.data.data);
          this.patientList = res.data.data.list;
          this.total = res.data.data.total;
          this.pageNum = res.data.data.pageNum;
          this.pageSize = res.data.data.pageSize;
        });
    },
    changeSize(value) {
      this.pageSize = value;
      // this.pageNum = 1;
      this.search();
    },
    changePage(value) {
      this.pageNum = value;
      this.search();
      
    },
    getIndex(i) {
      return i + 1 + this.pageSize * (this.pageNum - 1);
    },
  },
};
</script>

<style scoped>
</style>
