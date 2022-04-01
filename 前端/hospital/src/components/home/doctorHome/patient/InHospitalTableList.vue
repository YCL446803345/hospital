<template>
  <div>
    <!-- 面包xie导航 -->
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item
        ><a href="#/patientList">预约住院信息管理</a></el-breadcrumb-item
      >
      <el-breadcrumb-item>预约信息管理列表</el-breadcrumb-item>
    </el-breadcrumb>
    <el-row style="margin-top: 10px; margin-bottom: 10px">
      <el-col :span="2">
        <el-input v-model="patientName" placeholder="姓名"> </el-input>
      </el-col>

      <el-col :span="4">
        <el-input v-model="cardId" placeholder="身份证号"> </el-input>
      </el-col>

      <el-col :span="2">
        <el-select v-model="patientSex" placeholder="性别">
          <el-option label="男" value="男"></el-option>
          <el-option label="女" value="女"></el-option>
        </el-select>
      </el-col>

      <el-col :span="1" style="margin-left: 2px">
        <el-button type="success" @click="search">查询</el-button>
      </el-col>

      <el-col :span="1.5" style="margin-left: 2px">
        <el-button
          type="warning"
          @click="
            (patientName = ''),
              (cardId = ''),
              (patientSex = ''),
              (paheNum = 1),
              (pageSize = 5)
          "
          >清空</el-button
        >
      </el-col>
      <el-col :span="1.5" style="margin-left: 2px">
        <el-button type="primary" @click="gotoAddInHospital()">添加</el-button>
      </el-col>
    </el-row>
    <!-- 
            el-table数据表格
            :data 动态绑定 data中对象数组
            scope.row 表示对象数组的当前行对象
         -->
    <el-table :data="inHospitalTableList" style="width: 100%">
      <el-table-column type="index" :index="getIndex" label="序号" width="50">
      </el-table-column>
      <el-table-column
        prop="patientName"
        label="姓名"
        width="80"
      ></el-table-column>

      <el-table-column
                prop="patientSex"
                label="性别"
                width="80">
                <template slot-scope="scope">
                <span v-if="scope.row.patientSex=='女'">
                  <el-tag type="danger">女</el-tag>
                  </span>
                <span v-if="scope.row.patientSex=='男'">
                  <el-tag>男</el-tag>
                  </span>
                </template>
            </el-table-column>

      <el-table-column
        prop="patientAge"
        label="年龄"
        width="80"
      ></el-table-column>
      <el-table-column prop="cardId" label="身份证号" width="200"></el-table-column>
      <el-table-column
        prop="telephone"
        label="联系方式"
        width="140"
      ></el-table-column>
      <el-table-column
        prop="deptName"
        label="预约部门"
        width="80"
      ></el-table-column>
      <el-table-column prop="reason" label="病况" width="140"></el-table-column>
      <el-table-column
        prop="inHosptialTime"
        label="预约时间"
        width="180"
      ></el-table-column>
      <el-table-column prop="status" label="预约状态" width="120">
        <template slot-scope="scope" >
          <span v-if="scope.row.status=='1'">
             <el-tag type="danger">待审核</el-tag>
            </span>
          <span v-if="scope.row.status=='2'">
            <el-tag type="success">已审核</el-tag>
            </span>
          <span v-if="scope.row.status=='3'">
            <el-tag>住院中</el-tag>
            </span>
        </template>
      </el-table-column>

      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="primary"
            @click="
              gotoUpdateInHospitalTable(
                scope.row.id,
                scope.row.patientName,
                scope.row.patientSex,
                scope.row.patientAge,
                scope.row.cardId,
                scope.row.telephone,
                scope.row.reason,
                scope.row.deptName,
                scope.row.deptId,
                scope.row.inHosptialTime,
                scope.row.status
              )">审核</el-button>
          <el-button size="mini" type="danger" @click="gotoDeleteInHospitalTable( scope.row.id)">驳回</el-button>
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

    <!-- 审核预约住院 -->
    <el-dialog :visible.sync="updateInHospitalTableForm">
      <h1 align="center">审核预约住院</h1>
      <br />
      <template>
        <el-descriptions class="margin-top" title="" :column="3" border>
          <el-descriptions-item>
            <template slot="label"><i class="el-icon-user"></i>姓名</template>
            {{ updateInHospitalTable.patientName }}
          </el-descriptions-item>

          <el-descriptions-item>
            <template slot="label"><i class="el-icon-tickets"></i>性别</template>
            {{ updateInHospitalTable.patientSex }}
          </el-descriptions-item>

          <el-descriptions-item>
            <template slot="label"><i class="el-icon-tickets"></i>年龄</template>
            {{ updateInHospitalTable.patientAge }}
          </el-descriptions-item>

          <el-descriptions-item>
            <template slot="label"><i class="el-icon-tickets"></i>身份证号</template>
            {{ updateInHospitalTable.cardId }}
          </el-descriptions-item>

          <el-descriptions-item>
            <template slot="label"><i class="el-icon-tickets"></i>电话</template>
            {{ updateInHospitalTable.telephone }}
          </el-descriptions-item>

          <el-descriptions-item>
            <template slot="label"><i class="el-icon-tickets"></i>科室</template>
            {{ updateInHospitalTable.deptName === ""? "暂未分配" : updateInHospitalTable.deptName }}
          </el-descriptions-item>

          <el-descriptions-item>
            <template slot="label"><i class="el-icon-tickets"></i>时间</template>
            {{ updateInHospitalTable.inHosptialTime === "" ? "暂未分配" : updateInHospitalTable.inHosptialTime }}
          </el-descriptions-item>

          <el-descriptions-item>
            <template slot="label"><i class="el-icon-tickets"></i>病因</template>
            {{ updateInHospitalTable.reason === "" ? "暂未分配" : updateInHospitalTable.reason }}
          </el-descriptions-item>

          <el-descriptions-item>
            <template slot="label"> <i class="el-icon-tickets"></i>状态</template>
            {{ updateInHospitalTable.status === "1" ? "待审核" : "已审核" }}
          </el-descriptions-item>
        </el-descriptions>
      </template>

      <el-divider></el-divider>

      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="doUpdateInHospitalTable">审 核</el-button>
        <el-button @click="closeUpdateInHospitalTable">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 添加住院通知单 -->
    <el-dialog :visible.sync="addInHospitalForm">
      <h1 align="center">添加住院通知单</h1><br />
      <el-form :model="addInHospital" :rules="rules" ref="addInHospital">
        <el-form-item label="姓名" :label-width="formLabelWidth" prop="patientName">
          <el-input v-model="addInHospital.patientName" autocomplete="off"></el-input>
        </el-form-item>

        <!-- <el-form-item label="性别" :label-width="formLabelWidth" prop="patientSex">
          <el-radio v-model="addInHospital.patientSex" label="男" value='男'>男</el-radio>
          <el-radio v-model="addInHospital.patientSex" label="女" value='女'>女</el-radio>
        </el-form-item> -->

        <el-form-item label="性别" :label-width="formLabelWidth" prop="patientSex">
          <el-select v-model="addInHospital.patientSex" placeholder="请选择性别">
            <el-option label="男" value="男"></el-option>
            <el-option label="女" value="女"></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="年龄" :label-width="formLabelWidth" prop="patientAge">
          <el-input v-model="addInHospital.patientAge" autocomplete="off" ></el-input>
        </el-form-item>

        <el-form-item label="身份证号" :label-width="formLabelWidth" prop="cardId">
          <el-input v-model="addInHospital.cardId" autocomplete="off" ></el-input>
        </el-form-item>

        <el-form-item label="电话" :label-width="formLabelWidth" prop="telephone">
          <el-input v-model="addInHospital.telephone" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="部门" :label-width="formLabelWidth" prop="deptId">
          <el-select v-model="addInHospital.deptId" placeholder="请选择部门">
            <el-option v-for="dept in deptList" :label="dept.name" :value="dept.id" :key="dept.id"></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="原因" :label-width="formLabelWidth" prop="reason">
          <el-input v-model="addInHospital.reason" autocomplete="off"></el-input>
        </el-form-item>

        <!-- <el-form-item label="时间" :label-width="formLabelWidth">
          <el-input v-model="addInHospital.inHosptialTime" autocomplete="off"></el-input>
        </el-form-item> -->

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="doAddInHospital('addInHospital')">提 交</el-button>
        <!-- <el-button @click="resetForm()">重 置</el-button> -->
        <el-button @click="addInHospitalForm = false;addInHospital={}">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import qs from 'qs'

export default {
  data() {
    return {
      patientName: "",
      cardId: "",
      patientSex: "",
      inHospitalTableList: [],
      total: 100,
      pageNum: 1,
      pageSize: 5,

      deptList:[],

      addInHospital:{
        patientName: '',
        patientSex:'',
        patientAge:'',
        cardId:'',
        telephone:'',
        deptId:'',
        reason:''
      },
      rules: {
          patientName: [
            { required: true, message: '请输入患者名称', trigger: 'blur' },
            { min: 3, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur' }
          ],
          patientSex: [
            { required: true, message: '请至少选择一个性别', trigger: 'change' }
          ],
          patientAge: [
            { required: true, message: '年龄不能为空', trigger: 'blur' },
            // { type: 'number', message: '年龄必须为数字', trigger: 'blur' }
            // { type: 'number', required: true, message: '年龄必须是数字', trigger: 'change' }
          ],
          cardId: [
            { required: true, message: '身份证号不能为空', trigger: 'blur' },
            // { type: 'number', message: '身份证号必须是数字', trigger: 'blur' }
            // { type: 'number', required: true, message: '身份证号必须是数字', trigger: 'change' }
          ],
          telephone: [
            { required: true, message: '联系方式不能为空', trigger: 'blur' },
            // { type: 'number', message: '联系方式必须是数字', trigger: 'blur' }
            // { type: 'number', required: true, message: '联系方式必须是数字', trigger: 'change' }
          ],
          deptId: [
            { required: true, message: '请输入预约部门', trigger: 'change' },
            // { type: 'number', message: '部门必须是数字', trigger: 'change' }
            // { required: true, message: '请选择活动资源', trigger: 'change' }
          ],
          reason: [
            { required: true, message: '请输入描述原因', trigger: 'blur' },
            // { min: 3, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur' }
            // { required: true, message: '请填写活动形式', trigger: 'blur' }
          ]
        },
      addInHospitalForm: false,

      updateInHospitalTableForm: false, //控制是否显示审核对话框
      formLabelWidth: "100px",
      updateInHospitalTable: {
        id: "",
        patientName: "",
        patientSex: "",
        patientAge: "",
        cardId: "",
        telephone: "",
        reason: "",
        deptName: "",
        deptId:"",
        inHosptialTime: "",
        status: "",
      },
      headers: {},
    };
  },
  created() {
    this.search();
    this.findDeptList();
    this.headers = { tokenStr: window.localStorage.getItem("tokenStr") };
  },
  methods: {

    doAddInHospital(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            var inHospitalTable = this.addInHospital;
        // this.$axios.get("api/doctor/addRedisToken").then(res=>{
                // window.localStorage.setItem('redisToken',res.data.data)
                this.$axios.post("apidoctor/addInHospitalTable", inHospitalTable).then(res=>{
                    if(res.data.status ==200){
                        this.$message({
                          showClose: true,
                          message: "添加成功",
                          type: "success",
                          duration: 600,
                        });
                        this.addInHospital = {};
                        this.addInHospitalForm = false;
                        this.search() //刷新列表
                        // this.drugInfo=res.data.data
                        // this.dialogTableVisible=true
                    }else{
                        this.$message({
                        showClose: true,
                        message: '查看失败, 系统维护中',
                        type: 'warning',
                        duration:2000
                        });
                    }
                })
        // })
            // alert('submit!');
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
      resetForm() {
        this.addInHospital={
        patientName: '',
        patientSex:'',
        patientAge:'',
        cardId:'',
        telephone:'',
        deptId:'',
        reason:''
      }
      },

    //删除职工
    gotoDeleteInHospitalTable(id){
           this.$confirm('确定要驳回吗?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
            }).then(() => {
                this.$axios.post("/api/doctor/gotoDeleteInHospitalTable",qs.stringify({'id':id}),{
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
                            message: '驳回成功!',
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


      //打开添加住院通知单
      gotoAddInHospital() {
        this.addInHospitalForm = true;
      },

      //添加职工
    // doAddInHospital(){
    //   var inHospitalTable = this.addInHospital;
    //     // this.$axios.get("api/doctor/addRedisToken").then(res=>{
    //             // window.localStorage.setItem('redisToken',res.data.data)
    //             this.$axios.post("apidoctor/addInHospitalTable", inHospitalTable).then(res=>{
    //                 if(res.data.status ==200){
    //                     this.$message({
    //                       showClose: true,
    //                       message: "添加成功",
    //                       type: "success",
    //                       duration: 600,
    //                     });
    //                     this.addInHospital = {};
    //                     this.addInHospitalForm = false;
    //                     this.search() //刷新列表
    //                     // this.drugInfo=res.data.data
    //                     // this.dialogTableVisible=true
    //                 }else{
    //                     this.$message({
    //                     showClose: true,
    //                     message: '查看失败, 系统维护中',
    //                     type: 'warning',
    //                     duration:2000
    //                     });
    //                 }
    //             })
    //     // })
    // },


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

    doUpdateInHospitalTable() {
      var inHospitalTable = this.updateInHospitalTable;
      this.$axios
        .post("/api/doctor/updateInHospitalTable", inHospitalTable)
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
              message: "审核成功!",
              duration: 2000,
            });
            this.closeUpdateInHospitalTable();
            this.search();
          }
        });

    },
    closeUpdateInHospitalTable() {
      this.updateInHospitalTable = {
        id: "",
        patientName: "",
        patientSex: "",
        patientAge: "",
        cardId: "",
        telephone: "",
        reason: "",
        deptName: "",
        deptId: "",
        inHosptialTime: "",
        status: "",
      };
      this.updateInHospitalTableForm = false;
    },
    gotoUpdateInHospitalTable(
      id,
      patientName,
      patientSex,
      patientAge,
      cardId,
      telephone,
      reason,
      deptName,
      deptId,
      inHosptialTime,
      status
    ) {
      this.updateInHospitalTable = {
        id: id,
        patientName: patientName,
        patientSex: patientSex,
        patientAge: patientAge,
        cardId: cardId,
        telephone: telephone,
        reason: reason,
        deptName: deptName,
        deptId: deptId,
        inHosptialTime: inHosptialTime,
        status: status,
      };
      this.updateInHospitalTableForm = true;
    },

    //查询预约住院列表
    search() {
      console.log("---");
      this.$axios
        .get("/api/doctor/getInHospitalTableList", {
          params: {
            patientName: this.patientName,
            cardId: this.cardId,
            patientSex: this.patientSex,
            pageNum: this.pageNum,
            pageSize: this.pageSize,
          },
        })
        .then((res) => {
          console.log("=========================");
          console.log(res.data.data.list);
          this.inHospitalTableList = res.data.data.list;
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
