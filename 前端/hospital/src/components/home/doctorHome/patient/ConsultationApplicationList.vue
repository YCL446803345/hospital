<template>
  <div>
    <!-- 面包xie导航 -->
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item><a href="#/patientList">会诊信息管理</a></el-breadcrumb-item>
      <el-breadcrumb-item>会诊信息管理列表</el-breadcrumb-item>
    </el-breadcrumb>
    <el-row style="margin-top: 10px; margin-bottom: 10px">
      <el-col :span="2">
        <el-select v-model="consultationEmergencyId" placeholder="会诊情况">
          <!-- <el-option label="会诊情况" value=""></el-option> -->
          <el-option label="紧急" value="1"></el-option>
          <el-option label="24小时" value="2"></el-option>
          <el-option label="一般" value="3"></el-option>
        </el-select>
      </el-col>
      <el-col :span="2">
        <el-select v-model="consultationCategoryId" placeholder="会诊类别">
          <!-- <el-option label="会诊类别" value=""></el-option> -->
          <el-option label="它科会诊" value="1"></el-option>
          <el-option label="科内会诊" value="2"></el-option>
          <el-option label="院外会诊" value="3"></el-option>
        </el-select>
      </el-col>
      <el-col :span="2">
        <el-select v-model="status" placeholder="会诊状态">
          <!-- <el-option label="会诊状态" value=""></el-option> -->
          <el-option label="待会诊" value="1"></el-option>
          <el-option label="已会诊" value="2"></el-option>
          <!-- <el-option label="已取消" value="3"></el-option> -->
        </el-select>
      </el-col>

      <el-col :span="1" style="margin-left: 2px">
        <el-button type="success" @click="search">查询</el-button>
      </el-col>

      <el-col :span="1" style="margin-left: 2px">
        <el-button type="warning" @click="(consultationEmergencyId = ''),(consultationCategoryId = ''),
            (status = ''),(paheNum = 1),(pageSize = 5)">清空</el-button>
      </el-col>
    </el-row>
    <!-- 
            el-table数据表格
            :data 动态绑定 data中对象数组
            scope.row 表示对象数组的当前行对象
         -->
    <el-table :data="consultationApplicationList" style="width: 100%">
      <el-table-column type="index" :index="getIndex" label="序号" width="50">
      </el-table-column>

      <el-table-column prop="patientName" label="病人" width="80">
      </el-table-column>

      <el-table-column prop="no" label="病人编号" width="140">
      </el-table-column>

      <el-table-column prop="doctorName" label="主治医生" width="80">
      </el-table-column>

      <el-table-column prop="reason" label="会诊原因" width="180">
      </el-table-column>

      <el-table-column prop="desc" label="病情描述" width="180"> </el-table-column>

      <el-table-column prop="consultationEmergencyId" label="紧急度" width="120">
          <template slot-scope="scope">
          <span v-if="scope.row.consultationEmergencyId=='1'">紧急</span>
          <span v-if="scope.row.consultationEmergencyId=='2'">24小时</span>
          <span v-if="scope.row.consultationEmergencyId=='3'">一般</span>
        </template>
      </el-table-column>

      <el-table-column prop="consultationCategoryId" label="类别" width="120">
          <template slot-scope="scope">
          <span v-if="scope.row.consultationCategoryId=='1'">它科会诊</span>
          <span v-if="scope.row.consultationCategoryId=='2'">科内会诊</span>
          <span v-if="scope.row.consultationCategoryId=='3'">院外会诊</span>
        </template>
      </el-table-column>

      <el-table-column prop="consultationDate" label="会诊时间" width="180">
      </el-table-column>

      <el-table-column prop="status" label="会诊状态" width="100">
        <template slot-scope="scope">
          <span v-if="scope.row.status=='1'">
            <el-tag type="danger">待会诊</el-tag>
            </span>
          <span v-if="scope.row.status=='2'">
            <el-tag type="success">已会诊</el-tag>
            </span>
          <span v-if="scope.row.status=='3'">
            <el-tag type="info">已取消</el-tag>
            </span>
        </template>
      </el-table-column>

      <!-- <el-table-column
                label="头像"
                width="100">
                 <template slot-scope="scope">
                    <img  :src="scope.row.avatar" class="a-avatar" >
                </template>
            </el-table-column> -->

      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button size="mini" type="primary" @click=" gotoUpdateConsultationApplication(
                scope.row.id,
                scope.row.patientName,
                scope.row.doctorName,
                scope.row.reason,
                scope.row.desc,
                scope.row.consultationEmergencyId,
                scope.row.consultationCategoryId,
                scope.row.consultationDate,
              )">编辑会诊</el-button>
          <el-button v-if='scope.row.status==2'  size="mini" type="success" @click="gotoAddMedicalAdvice(
            scope.row.patientName,
            scope.row.doctorName,
            scope.row.patientId,
            scope.row.doctorId,
            scope.row.reason,
            scope.row.desc,
            scope.row.consultationEmergencyId,
            scope.row.consultationCategoryId
            )">下达医嘱</el-button>
          
          <el-button size="mini" type="danger" v-if='scope.row.status !=2 '
          @click="gotoCancelConsultationApplication(scope.row.id)">取消会诊</el-button>
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

    <!-- 编辑会诊 -->
    <el-dialog :visible.sync="updateConsultationApplicationForm">
      <h1 align="center">编辑会诊信息</h1><br />
      <el-form :model="updateConsultationApplication">
        <el-form-item label="姓名" :label-width="formLabelWidth">
          <el-input v-model="updateConsultationApplication.patientName" autocomplete="off" readonly="readonly"></el-input>
        </el-form-item>

        <el-form-item label="主治医生" :label-width="formLabelWidth">
          <el-input v-model="updateConsultationApplication.doctorName" autocomplete="off" readonly="readonly"></el-input>
        </el-form-item>

        <el-form-item label="会诊原因" :label-width="formLabelWidth">
          <el-input v-model="updateConsultationApplication.reason" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="描述" :label-width="formLabelWidth">
          <el-input v-model="updateConsultationApplication.desc" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="紧急度" :label-width="formLabelWidth">
            <el-radio v-model="updateConsultationApplication.consultationEmergencyId" label="1" value=1>紧急</el-radio>
            <el-radio v-model="updateConsultationApplication.consultationEmergencyId" label="2" value=2>24小时</el-radio>
            <el-radio v-model="updateConsultationApplication.consultationEmergencyId" label="3" value=3>一般</el-radio>
        </el-form-item>

        <el-form-item label="类别" :label-width="formLabelWidth">
            <el-radio v-model="updateConsultationApplication.consultationCategoryId" label="1" value=1>它科会诊</el-radio>
            <el-radio v-model="updateConsultationApplication.consultationCategoryId" label="2" value=2>科内会诊</el-radio>
            <el-radio v-model="updateConsultationApplication.consultationCategoryId" label="3" value=3>院外会诊</el-radio>                         
        </el-form-item>

        <el-form-item label="会诊时间" :label-width="formLabelWidth">
          <el-input v-model="updateConsultationApplication.consultationDate" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="doUpdateConsultationApplication">提 交</el-button>
        <el-button @click="closeUpdateConsultationApplication">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 下达医嘱 -->
    <el-dialog :visible.sync="addMedicalAdviceForm">
      <h1 align="center">下达医嘱</h1>
      <br />
      <template>
        <el-descriptions class="margin-top" title="" :column="3" border>
          <el-descriptions-item>
            <template slot="label"><i class="el-icon-user"></i>病人</template>
            {{ addMedicalAdvice.patientName }}
          </el-descriptions-item>

          <el-descriptions-item>
            <template slot="label"><i class="el-icon-user"></i>主治医生</template>
            {{ addMedicalAdvice.doctorName }}
          </el-descriptions-item>

          
          <el-descriptions-item>
            <template slot="label"><i class="el-icon-user"></i>下达原因</template>
            {{ addMedicalAdvice.reason }}
          </el-descriptions-item>

          <el-descriptions-item>
            <template slot="label"><i class="el-icon-user"></i>描述</template>
            {{ addMedicalAdvice.desc }}
          </el-descriptions-item>

          <el-descriptions-item>
            <template slot="label"><i class="el-icon-tickets"></i>紧急度</template>
            {{ addMedicalAdvice.consultationEmergencyId ==='1'?'紧急': 
               addMedicalAdvice.consultationEmergencyId ==='2'?'24小时':'一般' }}
          </el-descriptions-item>

          <el-descriptions-item>
            <template slot="label"><i class="el-icon-tickets"></i>类别</template>
            {{ addMedicalAdvice.consultationCategoryId ==='1'?'它科会诊': 
               addMedicalAdvice.consultationCategoryId ==='2'?'科内会诊':'一般会诊' }}
          </el-descriptions-item>


        </el-descriptions>
      </template>

      <el-divider></el-divider>

      <el-form :model="addMedicalAdvice">

        <el-form-item label="医嘱类型" :label-width="formLabelWidth">
            <el-radio v-model="addMedicalAdvice.adviceCategory" label="1" value=1>长期医嘱</el-radio>
            <el-radio v-model="addMedicalAdvice.adviceCategory" label="2" value=2>临时医嘱</el-radio>
            <el-radio v-model="addMedicalAdvice.adviceCategory" label="3" value=3>一般医嘱</el-radio>
        </el-form-item>

        <el-form-item label="医嘱描述" :label-width="formLabelWidth">
          <el-input v-model="addMedicalAdvice.adviceDescription" autocomplete="off"></el-input>
        </el-form-item>

      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="doAddMedicalAdvice">下 达</el-button>
        <el-button @click="addMedicalAdviceForm = false;addMedicalAdvice={}">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>

import qs from 'qs'

export default {
  data() {
    return {
      consultationEmergencyId: "",
      consultationCategoryId: "",
      status: "",
      consultationApplicationList: [],
      total: 100,
      pageNum: 1,
      pageSize: 5,

      addMedicalAdvice:{},
      addMedicalAdviceForm: false, //控制是否显示下达医嘱对话框
      
    
      updateConsultationApplicationForm: false, //控制是否显示编辑会诊对话框
      formLabelWidth: "80px",
      updateConsultationApplication: {
        id: "",
        patientName: "",
        doctorName: "",
        reason: "",
        desc: "",
        consultationEmergencyId: '',
        consultationCategoryId: '',
        consultationDate: "",
      },
      headers: {},
    };
  },
  created() {
    this.search();
    this.headers = { tokenStr: window.localStorage.getItem("tokenStr") };
  },
  methods: {
    //打开下达医嘱列表

      gotoAddMedicalAdvice(patientName,doctorName,patientId,doctorId,reason,desc,consultationEmergencyId,consultationCategoryId) {
        this.addMedicalAdvice = {
        patientName: patientName,
        doctorName: doctorName,
        patientId: patientId,
        doctorId: doctorId,

        reason: reason,
        desc: desc,
        consultationEmergencyId: consultationEmergencyId,
        consultationCategoryId: consultationCategoryId
      };
        this.addMedicalAdviceForm = true;
      },

      //下达医嘱
      doAddMedicalAdvice() {
       
        // console.log("=======================" + this.addMedicalAdvice);
        //发送axios请求
        var medicalAdvice=this.addMedicalAdvice;
         console.log("所说的上档次")
        //  alert(medicalAdvice)
         console.log(medicalAdvice)
        this.$axios.post("/api/doctor/gotoAddMedicalAdvice",medicalAdvice).then((res) => {
          console.log(res.data);
          if (res.status == 200) {
            this.$message({
              showClose: true,
              message: "下达成功",
              type: "success",
              duration: 600,
            });
            this.addMedicalAdvice = {};
            this.addMedicalAdviceForm = false;
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


    //取消会诊
    gotoCancelConsultationApplication(id){
           this.$confirm('确定要取消吗?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
            }).then(() => {
                this.$axios.post("/api/doctor/gotoCancelConsultationApplicationById",qs.stringify({'id':id}),{
            params: { id: id }
            }).then(res=>{
                    if(res.status==4001){
                            this.$message({
                            type: "error",
                            message: "没有权限!",
                             duration:2000
                        });
                     }else{
                        this.$message({
                            type: 'success',
                            message: '取消成功!',
                            duration:2000
                        });
                        this.search();
                     }
                })
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消操作',
                     duration:2000
                });          
            });
       },



      //执行修改
    doUpdateConsultationApplication() {
        //  console.log(res.data)
      var consultationApplication = this.updateConsultationApplication;

      this.$axios.post(
          "/api/doctor/updateConsultationApplication",
          consultationApplication
        )
        .then((res) => {
          if (res.data.status == 4001) {
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
            this.search();
            this.closeUpdateConsultationApplication();
          }
        });
    },
    //关闭执行窗口
    closeUpdateConsultationApplication() {
      this.updateConsultationApplication = {
        id: "",
        patientName: "",
        doctorName: "",
        reason: "",
        desc: "",
        consultationEmergencyId: "",
        consultationCategoryId: "",
        consultationDate: "",
      };
      this.updateConsultationApplicationForm = false;
    },
    //准备修改
    gotoUpdateConsultationApplication(
      id,
      patientName,
      doctorName,
      reason,
      desc,
      consultationEmergencyId,
      consultationCategoryId,
      consultationDate
    ){
      this.updateConsultationApplication = {
        id: id,
        patientName: patientName,
        doctorName: doctorName,
        reason: reason,
        desc: desc,
        consultationEmergencyId: consultationEmergencyId,
        consultationCategoryId: consultationCategoryId,
        consultationDate: consultationDate,
      };
      this.updateConsultationApplicationForm = true;
    },

    //查询会诊列表
    search() {
      console.log("---");
      this.$axios
        .get("/api/doctor/getConsultationApplication", {
          params: {
            consultationEmergencyId: this.consultationEmergencyId,
            consultationCategoryId: this.consultationCategoryId,
            status: this.status,
            pageNum: this.pageNum,
            pageSize: this.pageSize,
          },
        })
        .then((res) => {
          console.log(res.data.data);
          this.consultationApplicationList = res.data.data.list;
          this.total = res.data.data.total;
          this.pageNum = res.data.data.pageNum;
          this.pageSize = res.data.data.pageSize;
        });
    },
    changeSize(value) {
      this.pageSize = value;
      this.pageNum = 1;
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
