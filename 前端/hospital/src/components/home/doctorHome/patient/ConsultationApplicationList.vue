<template>
  <div>
    <!-- 面包xie导航 -->
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item
        ><a href="#/patientList">会诊信息管理</a></el-breadcrumb-item
      >
      <el-breadcrumb-item>会诊信息管理列表</el-breadcrumb-item>
    </el-breadcrumb>
    <el-row style="margin-top: 10px; margin-bottom: 10px">
      <el-col :span="2">
        <el-select v-model="consultationEmergencyId" placeholder="会诊情况">
          <el-option label="会诊情况" value=""></el-option>
          <el-option label="紧急" value="1"></el-option>
          <el-option label="24小时" value="2"></el-option>
          <el-option label="一般" value="3"></el-option>
        </el-select>
      </el-col>
      <el-col :span="2">
        <el-select v-model="consultationCategoryId" placeholder="会诊类别">
          <el-option label="会诊类别" value=""></el-option>
          <el-option label="它科会诊" value="1"></el-option>
          <el-option label="科内会诊" value="2"></el-option>
          <el-option label="院外会诊" value="3"></el-option>
        </el-select>
      </el-col>
      <el-col :span="2">
        <el-select v-model="status" placeholder="会诊状态">
          <el-option label="会诊状态" value=""></el-option>
          <el-option label="待会诊" value="1"></el-option>
          <el-option label="已会诊" value="2"></el-option>
          <el-option label="已取消" value="3"></el-option>
        </el-select>
      </el-col>

      <el-col :span="1" style="margin-left: 2px">
        <el-button type="success" @click="search">查询</el-button>
      </el-col>

      <el-col :span="1" style="margin-left: 2px">
        <el-button
          type="warning"
          @click="
            (consultationEmergencyId = ''),
              (consultationCategoryId = ''),
              (status = ''),
              (paheNum = 1),
              (pageSize = 5)
          "
          >清空</el-button
        >
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

      <el-table-column prop="doctorName" label="主治医生" width="80">
      </el-table-column>

      <el-table-column prop="reason" label="原因" width="180">
      </el-table-column>

      <el-table-column prop="desc" label="描述" width="180"> </el-table-column>

      <el-table-column prop="consultationEmergencyId" label="紧急度" width="180">
          <template slot-scope="scope">
          <span v-if="scope.row.consultationEmergencyId=='1'">紧急</span>
          <span v-if="scope.row.consultationEmergencyId=='2'">24小时</span>
          <span v-if="scope.row.consultationEmergencyId=='3'">一般</span>
        </template>
      </el-table-column>

      <el-table-column prop="consultationCategoryId" label="类别" width="180">
          <template slot-scope="scope">
          <span v-if="scope.row.consultationCategoryId=='1'">它科会诊</span>
          <span v-if="scope.row.consultationCategoryId=='2'">科内会诊</span>
          <span v-if="scope.row.consultationCategoryId=='3'">院外会诊</span>
        </template>
      </el-table-column>

      <el-table-column prop="consultationDate" label="会诊时间" width="180">
      </el-table-column>

      <el-table-column prop="status" label="会诊状态" width="80">
        <!-- <template slot-scope="scope">
          {{ scope.row.status === "1" ? "未会诊" : "已会诊" }}
        </template> -->
        <template slot-scope="scope">
          <span v-if="scope.row.status=='1'">待会诊</span>
          <span v-if="scope.row.status=='2'">已会诊</span>
          <span v-if="scope.row.status=='3'">已取消</span>
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
          <el-button
            size="mini"
            type="primary"
            @click="
              gotoUpdateConsultationApplication(
                scope.row.id,
                scope.row.patientName,
                scope.row.doctorName,
                scope.row.reason,
                scope.row.desc,
                scope.row.consultationEmergencyId,
                scope.row.consultationCategoryId,
                scope.row.consultationDate
              )
            "
            >编辑会诊</el-button
          >
          <!-- <el-button
            size="mini"
            type="danger"
            @click="deleteConsultationApplication(scope.row.id)"
            >删除会诊</el-button
          > -->
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
          <el-input
            v-model="updateConsultationApplication.patientName"
            autocomplete="off"
          ></el-input>
        </el-form-item>

        <el-form-item label="主治医生" :label-width="formLabelWidth">
          <el-input
            v-model="updateConsultationApplication.doctorName"
            autocomplete="off"
          ></el-input>
        </el-form-item>

        <el-form-item label="会诊原因" :label-width="formLabelWidth">
          <el-input
            v-model="updateConsultationApplication.reason"
            autocomplete="off"
          ></el-input>
        </el-form-item>

        <el-form-item label="描述" :label-width="formLabelWidth">
          <el-input
            v-model="updateConsultationApplication.desc"
            autocomplete="off"
          ></el-input>
        </el-form-item>

        <!-- <el-form-item label="紧急度" :label-width="formLabelWidth">
          <el-input
            v-model="updateConsultationApplication.consultationEmergencyName"
            autocomplete="off"
          ></el-input>
        </el-form-item>

        <el-form-item label="类别" :label-width="formLabelWidth">
          <el-input
            v-model="updateConsultationApplication.consultationCategoryName"
            autocomplete="off"
          ></el-input>
        </el-form-item> -->

        <el-form-item label="紧急度" :label-width="formLabelWidth">
            <el-radio v-model="updateConsultationApplication.consultationEmergencyId" label="1" value=1>紧急</el-radio>
            <el-radio v-model="updateConsultationApplication.consultationEmergencyId" label="2" value=2>24小时</el-radio>
            <el-radio v-model="updateConsultationApplication.consultationEmergencyId" label="3" value=3>一般</el-radio>
          <!-- <el-select v-model="updateConsultationApplication.consultationEmergencyId" :label-width="formLabelWidth">

            <el-option label="紧急" value=1></el-option>                    
            <el-option label="24小时" value=2></el-option>
            <el-option label="一般" value=3></el-option>       
          </el-select> -->
        </el-form-item>

        <el-form-item label="类别" :label-width="formLabelWidth">
          <!-- <el-select v-model="updateConsultationApplication.consultationCategoryId" :label-width="formLabelWidth"> -->
            <el-radio v-model="updateConsultationApplication.consultationCategoryId" label="1" value=1>它科会诊</el-radio>
            <el-radio v-model="updateConsultationApplication.consultationCategoryId" label="2" value=2>科内会诊</el-radio>
            <el-radio v-model="updateConsultationApplication.consultationCategoryId" label="3" value=3>院外会诊</el-radio>                         
            <!-- <el-option label="它科会诊" value=1></el-option>                  
            <el-option label="科内会诊" value=2></el-option>
            <el-option label="院外会诊" value=3></el-option>
                           
          </el-select> -->
        </el-form-item>

        <el-form-item label="会诊时间" :label-width="formLabelWidth">
          <el-input
            v-model="updateConsultationApplication.consultationDate"
            autocomplete="off"
          ></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="doUpdateConsultationApplication"
          >提 交</el-button
        >
        <el-button @click="closeUpdateConsultationApplication">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
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
      // updateTeacherForm:false,
      
    
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
    
    gotoUpdateConsultationApplication(
      id,
      patientName,
      doctorName,
      reason,
      desc,
      consultationEmergencyId,
      consultationCategoryId,
      consultationDate
      
    )
    {
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
          this.consultationApplicationList = res.data.data.list;
          this.total = res.data.data.total;
          this.pageNum = res.data.data.pageNum;
          this.pageSize = res.data.data.pageSize;
        });
    },
    getIndex(i) {
      return i + 1 + this.pageSize * (this.pageNum - 1);
    },
  },
};
</script>

<style scoped>
</style>
