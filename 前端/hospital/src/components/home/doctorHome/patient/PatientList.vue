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
      
      <el-table-column
                prop="gender"
                label="性别"
                width="80">
                <template slot-scope="scope">
                <span v-if="scope.row.gender=='2'">
                  <el-tag type="danger">女</el-tag>
                  </span>
                <span v-if="scope.row.gender=='1'">
                  <el-tag>男</el-tag>
                  </span>
                </template>
            </el-table-column>

      <el-table-column prop="age" label="年龄" width="60"> </el-table-column>

      <el-table-column prop="cardId" label="身份证号" width="180">
      </el-table-column>

      <el-table-column prop="phone" label="手机号" width="150">
      </el-table-column>

      <el-table-column prop="appointmenttTime" label="入院时间" width="160"></el-table-column>

      <el-table-column prop="deptName" label="科室" width="80">
      </el-table-column>

      <el-table-column prop="bedCode" label="床位编号" width="80">
        <template slot-scope="scope">
          <el-tag :type="scope.row.bedCode === null ? 'danger' : 'primary'" disable-transitions>
            {{ scope.row.bedCode === null?'未分配' :scope.row.bedCode}}
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
          <el-button size="mini" type="primary" @click="gotoUpdatePatient(
            scope.row.id,
            scope.row.name,
            scope.row.no,
            scope.row.gender,
            scope.row.age,
            scope.row.cardId,
            scope.row.phone,
            scope.row.appointmenttTime,
            scope.row.deptName,
            scope.row.deptId,
            scope.row.bedCode,
            scope.row.bedId,
            scope.row.nurseName,
            scope.row.nurseId,
            scope.row.doctorName,
            scope.row.doctorId,
            scope.row.baseDesc
            )">编辑患者</el-button>  
          <el-button size="mini" type="warning" @click="gotoAddConsultationApplication(
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
            )">申请会诊</el-button>
          
          <el-button size="mini" type="success" @click="gotoAddLeaveHospital(
              scope.row.id,
              scope.row.name,
              scope.row.doctorId,
              scope.row.doctorName,
              scope.row.no,
              scope.row.gender,
              scope.row.age,
              scope.row.phone,
              scope.row.cardId
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

    <!-- 申请出院 -->
    <el-dialog :visible.sync="addLeaveHospitalForm">
      <h1 align="center">申请出院</h1>
      <br />
      <template>
        <el-descriptions class="margin-top" title="" :column="3" border>
          <el-descriptions-item>
            <template slot="label"><i class="el-icon-user"></i>病人</template>
            {{ addLeaveHospital.name }}
          </el-descriptions-item>

          <!-- <el-descriptions-item>
            <template slot="label"><i class="el-icon-user"></i>主治医生</template>
            {{ addLeaveHospital.doctorName }}
          </el-descriptions-item> -->

          <el-descriptions-item>
            <template slot="label"><i class="el-icon-user"></i>编号</template>
            {{ addLeaveHospital.no }}
          </el-descriptions-item>

          <el-descriptions-item>
            <template slot="label"><i class="el-icon-user"></i>身份证号</template>
            {{ addLeaveHospital.cardId }}
          </el-descriptions-item>

          <el-descriptions-item>
            <template slot="label"><i class="el-icon-user"></i>性别</template>
            {{ addLeaveHospital.gender ==='1'?'男':'女'  }}
          </el-descriptions-item>

          <el-descriptions-item>
            <template slot="label"><i class="el-icon-user"></i>年龄</template>
            {{ addLeaveHospital.age }}
          </el-descriptions-item>

         <el-descriptions-item>
            <template slot="label"><i class="el-icon-user"></i>电话</template>
            {{ addLeaveHospital.phone }}
          </el-descriptions-item>

          

        </el-descriptions>
      </template>

      <el-divider></el-divider>

      <el-form :model="addLeaveHospital" :rules="rules" ref="addLeaveHospital">

        <el-form-item label="申请描述" :label-width="formLabelWidth" prop="leaveDescription">
          <el-input v-model="addLeaveHospital.leaveDescription" autocomplete="off"></el-input>
        </el-form-item>

      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="doAddLeaveHospital('addLeaveHospital')">下 达</el-button>
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

      <el-form :model="addConsultationApplication" :rules="rules1" ref="addConsultationApplication">

        <el-form-item label="紧急度" :label-width="formLabelWidth" prop="consultationEmergencyId">
          <el-select v-model="addConsultationApplication.consultationEmergencyId" placeholder="请选择紧急度">
            <el-option label="紧急" value="1"></el-option>
            <el-option label="24小时" value="2"></el-option>
            <el-option label="一般" value="3"></el-option>
          </el-select>

        </el-form-item>

        <el-form-item label="会诊类别" :label-width="formLabelWidth" prop="consultationCategoryId">
          <el-select v-model="addConsultationApplication.consultationCategoryId" placeholder="请选择会诊类别">
            <el-option label="它科会诊" value="1"></el-option>
            <el-option label="科内会诊" value="2"></el-option>
            <el-option label="一般会诊" value="3"></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="申请原因" :label-width="formLabelWidth" prop="reason">
          <el-input v-model="addConsultationApplication.reason" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="突发情况" :label-width="formLabelWidth" prop="desc">
          <el-input v-model="addConsultationApplication.desc" autocomplete="off"></el-input>
        </el-form-item>

      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="doAddConsultationApplication('addConsultationApplication')">下 达</el-button>
        <el-button @click="addConsultationApplicationForm = false;addConsultationApplication={}">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 编辑患者 -->
    <el-dialog :visible.sync="updatePatientForm">
      <h1 align="center">编辑患者信息</h1><br />
      <el-form :model="updatePatient">
        <el-form-item label="姓名" :label-width="formLabelWidth">
          <el-input v-model="updatePatient.name" autocomplete="off" readonly="readonly"></el-input>
        </el-form-item>

        <!-- <el-form-item label="主治医生" :label-width="formLabelWidth">
          <el-input v-model="updatePatient.doctorName" autocomplete="off" readonly="readonly"></el-input>
        </el-form-item>

        <el-form-item label="主治护士" :label-width="formLabelWidth">
          <el-input v-model="updatePatient.nurseName" autocomplete="off" readonly="readonly"></el-input>
        </el-form-item> -->

        <el-form-item label="编号" :label-width="formLabelWidth">
          <el-input v-model="updatePatient.no" autocomplete="off" readonly="readonly"></el-input>
        </el-form-item>

        <el-form-item label="性别" :label-width="formLabelWidth">
                <el-radio v-model="updatePatient.gender" label="1" value=1>男</el-radio>
                <el-radio v-model="updatePatient.gender" label="2" value=2>女</el-radio>
            </el-form-item>

        <el-form-item label="年龄" :label-width="formLabelWidth">
          <el-input v-model="updatePatient.age" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="电话" :label-width="formLabelWidth">
          <el-input v-model="updatePatient.phone" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="身份证号" :label-width="formLabelWidth">
          <el-input v-model="updatePatient.cardId" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="入院时间" :label-width="formLabelWidth">
          <el-input v-model="updatePatient.appointmenttTime" autocomplete="off" readonly="readonly"></el-input> 
        </el-form-item>

        <!-- <el-form-item label="科室" :label-width="formLabelWidth">
          <el-input v-model="updatePatient.deptName" autocomplete="off" readonly="readonly"></el-input>
        </el-form-item> -->

        <el-form-item label="科室" :label-width="formLabelWidth">
          <el-select v-model="updatePatient.deptId" placeholder="请选择科室">
            <el-option v-for="dept in deptList" :label="dept.name" :value="dept.id" :key="dept.id"></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="床位" :label-width="formLabelWidth">
          <el-input v-model="updatePatient.bedCode" autocomplete="off" readonly="readonly"></el-input>
        </el-form-item>

        <el-form-item label="描述" :label-width="formLabelWidth">
          <el-input v-model="updatePatient.baseDesc" autocomplete="off"></el-input>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="doUpdatePatient">提 交</el-button>
        <el-button @click="updatePatientForm = false;updatePatient={}">取 消</el-button>
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
      addLeaveHospital:{
        leaveDescription:''
      },
      rules: {
          leaveDescription: [
            { required: true, message: '请输入出院描述', trigger: 'blur' },
            // { min: 3, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur' }
          ]
        },
      addLeaveHospitalForm: false,

      addConsultationApplication:{
        consultationEmergencyId:'',
        consultationCategoryId:'',
        reason:'',
        desc:''
      },
       rules1: {
          consultationEmergencyId: [
            { required: true, message: '请选择紧急度', trigger: 'change' },
            // { min: 3, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur' }
          ],
          consultationCategoryId: [
            { required: true, message: '请选择会诊类别', trigger: 'change' }
          ],
          reason: [
            { required: true, message: '请输入申请原因', trigger: 'blur' },
          ],
          desc: [
            { required: true, message: '请输入突发情况', trigger: 'blur' },
          ],
        },


      addConsultationApplicationForm: false,

      
      updatePatientForm: false,
      updatePatient:{
        id:'',
        name:'',
        no:'',
        gender:'',
        age:'',
        cardId:'',
        phone:'',
        appointmenttTime:'',
        deptName:'',
        deptId:'',
        bedCode:'',
        bedId:'',
        nurseName:'',
        nurseId:'',
        doctorName:'',
        doctorId:'',
        baseDesc:''
      },
      deptList:[],
      doctorId:''
    };
  },
  created() {

    this.findDeptList();
    this.headers = { tokenStr: window.localStorage.getItem("tokenStr") };

    var roleId = window.localStorage.getItem("roleId")
    if(roleId=='5'){
        this.doctorId =  parseInt(window.localStorage.getItem("workerId"))
      }else if(roleId=='9'){
        this.doctorId=''
      }
       this.search();
  },
  methods: {
    doAddConsultationApplication(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
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
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },  


    doAddLeaveHospital(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
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
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },

    //部门列表
      findDeptList() {
        //axios请求拿数据
        this.$axios
          .get("/api/dept/list", {
            params: {},
          })
          .then((res) => {
            console.log(res.data.data);
            //设置部门列表数据
            this.deptList = res.data.data;
          })
          .catch((res) => {
            this.$message({
              type: "error",
              message: "获取部门列表错误!",
            });
          });
      },

      //执行修改
    doUpdatePatient() {
      var patient = this.updatePatient;

      this.$axios.post("/api/doctor/updatePatient",patient)
        .then((res) => {
          if (res.status == 4001) {
            this.$message({
              type: "error",
              message: "没有权限!",
              duration: 2000,
            });
          } else {
            this.$message({
              type: "success",
              message: "编辑成功!",
              duration: 2000,
            });
            this.updatePatientForm = false;
            this.search();
          }
        });
    },
    //准备修改
    gotoUpdatePatient(
        id,
        name,
        no,
        gender,
        age,
        cardId,
        phone,
        appointmenttTime,
        deptName,
        deptId,
        bedCode,
        bedId,
        nurseName,
        nurseId,
        doctorName,
        doctorId,
        baseDesc
    ){
      this.updatePatient = {
        id:id,
        name:name,
        no:no,
        gender:gender,
        age:age,
        cardId:cardId,
        phone:phone,
        appointmenttTime:appointmenttTime,
        deptName:deptName,
        deptId:deptId,
        bedCode:bedCode,
        bedId:bedId,
        nurseName:nurseName,
        nurseId:nurseId,
        doctorName:doctorName,
        doctorId:doctorId,
        baseDesc:baseDesc
      };
      this.updatePatientForm = true;
    },

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

      //准备出院
      gotoAddLeaveHospital(id,name,doctorId,doctorName,no,gender,age,phone,cardId) {
        this.addLeaveHospital = {
        id: id,
        name: name,
        doctorId: doctorId,
        doctorName: doctorName,
        no: no,
        gender: gender,
        age: age,
        phone: phone,
        cardId: cardId
      };
        this.addLeaveHospitalForm = true;
      },

    //查询病人信息列表
    search() {
      console.log(this.doctorId);
      this.$axios
        .get("/api/doctor/getPatientList", {
          params: {
            doctorId:this.doctorId,
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
