<template>
    <div>
        <!-- 面包xie导航 -->
        <el-breadcrumb separator="/">
            <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item><a href="#/patientList">医嘱信息管理</a></el-breadcrumb-item>
            <el-breadcrumb-item>医嘱信息管理列表</el-breadcrumb-item>
        </el-breadcrumb>
        <el-row style="margin-top:10px;margin-bottom:10px">

            <el-col :span="2">
                <el-select v-model="adviceCategory" placeholder="医嘱类型">
                    <!-- <el-option label="医嘱类型" value="" ></el-option> -->
                    <el-option label="长期医嘱" value="1" ></el-option>
                    <el-option label="临时医嘱" value="2" ></el-option>
                    <el-option label="一般医嘱" value="3" ></el-option>
                </el-select>
            </el-col>

            <el-col :span="2">
                <el-select v-model="adviceStatus" placeholder="医嘱状态">
                    <el-option label="待校对" value="1" ></el-option>
                    <el-option label="待执行" value="2" ></el-option>
                    <el-option label="已执行" value="3" ></el-option>
                    <el-option label="已停止" value="4" ></el-option>
                </el-select>
            </el-col>

            <el-col :span="1" style="margin-left:2px;">
                <el-button type="success" @click="search">查询</el-button>
            </el-col>

            <el-col :span="1" style="margin-left:2px;">
                 <el-button type="warning" @click="adviceCategory='',adviceStatus='',paheNum=1,pageSize=5">清空</el-button>
            </el-col>
            
        </el-row>
        <!-- 
            el-table数据表格
            :data 动态绑定 data中对象数组
            scope.row 表示对象数组的当前行对象
         -->
        <el-table :data="medicalAdviceList" style="width: 100%">
            <el-table-column type="index" :index='getIndex' label="序号" width="50"></el-table-column>
            <el-table-column prop="patientName" label="病人" width="80"></el-table-column>
            <el-table-column prop="no" label="病人编号" width="140"></el-table-column>
            <el-table-column prop="doctorName" label="主治医生" width="80"></el-table-column>
            <el-table-column prop="adviceCategory" label="医嘱类型" width="130">
                <template slot-scope="scope">
                    <span v-if="scope.row.adviceCategory=='1'">长期医嘱</span>
                    <span v-if="scope.row.adviceCategory=='2'">临时医嘱</span>
                    <span v-if="scope.row.adviceCategory=='3'">一般医嘱</span>
                </template>
            </el-table-column>

            <el-table-column prop="projectId" label="项目类型" width="130">
              <template slot-scope="scope">
                    <span v-if="scope.row.projectId=='1'">肠胃科检查</span>
                    <span v-if="scope.row.projectId=='2'">内科检查</span>
                    <span v-if="scope.row.projectId=='3'">感冒检查</span>
                    <span v-if="scope.row.projectId=='4'">失眠检查</span>
                    <span v-if="scope.row.projectId=='5'">脑部检查</span>
                    <span v-if="scope.row.projectId=='6'">肝部检查</span>
                    <span v-if="scope.row.projectId=='7'">发热检查</span>
                    <span v-if="scope.row.projectId=='8'">关节检查</span>
                    <span v-if="scope.row.projectId=='9'">肾部检查</span>
                    <span v-if="scope.row.projectId=='10'">开颅手术</span>
                    <span v-if="scope.row.projectId=='11'">内科手术</span>
                    <span v-if="scope.row.projectId=='12'">肠胃手术</span>
                    <span v-if="scope.row.projectId=='13'">包皮切除手术</span>
                    <span v-if="scope.row.projectId=='14'">肺部手术</span>
                    <span v-if="scope.row.projectId=='15'">外科手术</span>
                </template>
            </el-table-column>

            

            <el-table-column prop="adviceDescription" label="医嘱描述" width="180">
            </el-table-column>

            <el-table-column prop="adviceStatus" label="医嘱状态" width="110">
                <template slot-scope="scope">
                <span v-if="scope.row.adviceStatus=='1'">
                  <el-tag type="danger">待校对</el-tag>
                  </span>
                <span v-if="scope.row.adviceStatus=='2'">
                  <el-tag type="success">待执行</el-tag>
                  </span>
                <span v-if="scope.row.adviceStatus=='3'">
                  <el-tag>已执行</el-tag>
                  </span>
                <span v-if="scope.row.adviceStatus=='4'">
                  <el-tag type="info">已停止</el-tag>
                  </span>
                </template>
            </el-table-column>

            <el-table-column prop="createTime" label="创建时间" width="180">
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
                  <el-button size="mini" type="primary" v-if='scope.row.adviceStatus==3'
                  @click="gotoUpdateMedicalAdvice(
                      scope.row.id,
                      scope.row.patientName,
                      scope.row.doctorName,
                      scope.row.adviceCategory,
                      scope.row.projectId,
                      scope.row.projectName,
                      scope.row.adviceDescription,
                      scope.row.adviceStatus,
                      scope.row.createTime
                      )">项目执行</el-button>
                  <el-button size="mini" type="success" v-if='scope.row.adviceStatus==3'
                  @click="gotoAddPrescription(
                      scope.row.id,
                      scope.row.patientName,
                      scope.row.doctorName,
                      scope.row.patientId,
                      scope.row.doctorId,
                      scope.row.adviceCategory,
                      scope.row.projectId,
                      scope.row.projectName,
                      scope.row.adviceDescription
                      )">下达处方</el-button>
                  <el-button v-if='scope.row.adviceStatus==3' size="mini" type="warning" @click="gotoAddCase(
                    scope.row.id,
                    scope.row.patientName,
                    scope.row.doctorName,
                    scope.row.patientId,
                    scope.row.doctorId,
                    scope.row.projectId,
                    scope.row.projectName,
                    scope.row.adviceDescription
                  )">添加病例</el-button>
                  <el-button size="mini" type="danger" v-if='scope.row.adviceStatus==1'
                  @click="gotoStopMedicalAdvice( scope.row.id)">停止医嘱</el-button>
               </template>
            </el-table-column>
        </el-table>
       
        <el-pagination
            background
            layout="prev, pager,next, sizes,->,total"
            :total="total"
            :current-page='pageNum'
            :page-sizes=[5,10,15,20]
            :page-size='pageSize'
            @size-change='changeSize'
            @current-change='changePage'
           >
        </el-pagination>

    <!-- 项目执行 -->
    <el-dialog :visible.sync="updateMedicalAdviceForm">
      <h1 align="center">项目执行</h1>
      <br />
      <template>
        <el-descriptions class="margin-top" title="" :column="3" border>
          <el-descriptions-item>
            <template slot="label"><i class="el-icon-user"></i>病人</template>
            {{ updateMedicalAdvice.patientName }}
          </el-descriptions-item>

          <el-descriptions-item>
            <template slot="label"><i class="el-icon-tickets"></i>主治医生</template>
            {{ updateMedicalAdvice.doctorName }}
          </el-descriptions-item>

          <el-descriptions-item>
            <template slot="label"><i class="el-icon-tickets"></i>医嘱类型</template>
            {{ updateMedicalAdvice.adviceCategory ==='1'?'长期医嘱': 
               updateMedicalAdvice.adviceCategory ==='2'?'临时医嘱':'一般医嘱' }}
          </el-descriptions-item>
     
          <el-descriptions-item>
            <template slot="label"><i class="el-icon-tickets"></i>医嘱状态</template>
            {{ updateMedicalAdvice.adviceStatus ===1?'待校对': 
               updateMedicalAdvice.adviceStatus ===2?'待执行':
               updateMedicalAdvice.adviceStatus ===3?'已执行':'已停止' }}
          </el-descriptions-item>

          <el-descriptions-item>
            <template slot="label"><i class="el-icon-tickets"></i>创建时间</template>
            {{ updateMedicalAdvice.createTime }}
          </el-descriptions-item>

          <el-descriptions-item>
            <template slot="label"><i class="el-icon-tickets"></i>医嘱描述</template>
            {{ updateMedicalAdvice.adviceDescription }}
          </el-descriptions-item>
        </el-descriptions>
      </template>

      <el-divider></el-divider>

      <el-form :model="updateMedicalAdvice">

        <el-form-item label="项目" :label-width="formLabelWidth" >
            <el-select v-model="updateMedicalAdvice.projectId" placeholder="项目" >
                <el-option v-for="project in projectList" :key="project.id" :label="project.name" :value="project.id"  ></el-option>
            </el-select>
        </el-form-item>

      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="doUpdateMedicalAdvice">执 行</el-button>
        <el-button @click="closeupdateMedicalAdviceForm">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 下达处方 -->
    <el-dialog :visible.sync="addPrescriptionForm">
      <h1 align="center">下达处方</h1>
      <br />
      <template>
        <el-descriptions class="margin-top" title="" :column="3" border>
          <el-descriptions-item>
            <template slot="label"><i class="el-icon-user"></i>病人</template>
            {{ addPrescription.patientName }}
          </el-descriptions-item>

          <el-descriptions-item>
            <template slot="label"><i class="el-icon-user"></i>主治医生</template>
            {{ addPrescription.doctorName }}
          </el-descriptions-item>

          <el-descriptions-item>
            <template slot="label"><i class="el-icon-tickets"></i>医嘱类型</template>
            {{ addPrescription.adviceCategory ==='1'?'长期医嘱': 
               addPrescription.adviceCategory ==='2'?'临时医嘱':'一般医嘱' }}
          </el-descriptions-item>

          <el-descriptions-item>
            <template slot="label"><i class="el-icon-tickets"></i>项目类型</template>
            {{ addPrescription.projecId ==='1'?'肠胃科检查':
               addPrescription.projecId ==='2'?'内科检查':
               addPrescription.projecId ==='3'?'感冒检查':
               addPrescription.projecId ==='4'?'失眠检查':
               addPrescription.projecId ==='5'?'脑部检查':
               addPrescription.projecId ==='6'?'肝部检查':
               addPrescription.projecId ==='7'?'发热检查':
               addPrescription.projecId ==='8'?'关节检查':
               addPrescription.projecId ==='9'?'肾部检查':
               addPrescription.projecId ==='10'?'开颅手术':
               addPrescription.projecId ==='11'?'内科手术':
               addPrescription.projecId ==='12'?'肠胃手术':
               addPrescription.projecId ==='13'?'包皮切除手术':
               addPrescription.projecId ==='14'?'肺部手术':'外科手术'}}
          </el-descriptions-item>

          <el-descriptions-item>
            <template slot="label"><i class="el-icon-user"></i>描述</template>
            {{ addPrescription.adviceDescription }}
          </el-descriptions-item>

        </el-descriptions>
      </template>

      <el-divider></el-divider>

      <el-form :model="addPrescription" :rules="rules1" ref="addPrescription">

          <el-form-item label="药品" :label-width="formLabelWidth" prop="drugId">
            <el-select v-model="addPrescription.drugId" placeholder="药品" >
                <el-option v-for="drug in drugList" :key="drug.id" :label="drug.name" :value="drug.id"  ></el-option>
            </el-select>
        </el-form-item>

        <el-form-item label="数量" :label-width="formLabelWidth" prop="num">
          <!-- <el-input v-model="addPrescription.num" autocomplete="off"></el-input> -->
          <el-input-number v-model="addPrescription.num" @change="handleChange" :min="1" :max="20" label="描述文字"></el-input-number>
        </el-form-item>

      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="doAddPrescription('addPrescription')">下 达</el-button>
        <el-button @click="addPrescriptionForm = false;addPrescription={}">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 添加病例 -->
    <el-dialog :visible.sync="addCaseForm">
      <h1 align="center">添加病例</h1>
      <br />
      <template>
        <el-descriptions class="margin-top" title="" :column="3" border>
          <el-descriptions-item>
            <template slot="label"><i class="el-icon-user"></i>病人</template>
            {{ addCase.patientName }}
          </el-descriptions-item>

          <el-descriptions-item>
            <template slot="label"><i class="el-icon-user"></i>主治医生</template>
            {{ addCase.doctorName }}
          </el-descriptions-item>

          <el-descriptions-item>
            <template slot="label"><i class="el-icon-tickets"></i>医嘱类型</template>
            {{ addCase.adviceCategory ==='1'?'长期医嘱': 
               addCase.adviceCategory ==='2'?'临时医嘱':'一般医嘱' }}
          </el-descriptions-item>

          <el-descriptions-item>
            <template slot="label"><i class="el-icon-tickets"></i>项目类型</template>
            {{ addCase.projecId ==='1'?'肠胃科检查':
               addCase.projecId ==='2'?'内科检查':
               addCase.projecId ==='3'?'感冒检查':
               addCase.projecId ==='4'?'失眠检查':
               addCase.projecId ==='5'?'脑部检查':
               addCase.projecId ==='6'?'肝部检查':
               addCase.projecId ==='7'?'发热检查':
               addCase.projecId ==='8'?'关节检查':
               addCase.projecId ==='9'?'肾部检查':
               addCase.projecId ==='10'?'开颅手术':
               addCase.projecId ==='11'?'内科手术':
               addCase.projecId ==='12'?'肠胃手术':
               addCase.projecId ==='13'?'包皮切除手术':
               addCase.projecId ==='14'?'肺部手术':'外科手术'}}
          </el-descriptions-item>
        

          <el-descriptions-item>
            <template slot="label"><i class="el-icon-user"></i>描述</template>
            {{ addCase.adviceDescription }}
          </el-descriptions-item>

        </el-descriptions>
      </template>

      <el-divider></el-divider>

      <el-form :model="addCase" :rules="rules" ref="addCase">

        <el-form-item label="病症" :label-width="formLabelWidth" prop="spare1">
          <el-input v-model="addCase.spare1" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="病例描述" :label-width="formLabelWidth" prop="caseDescription">
          <el-input v-model="addCase.caseDescription" autocomplete="off"></el-input>
        </el-form-item>

      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="doAddCase('addCase')">提 交</el-button>
        <el-button @click="addCaseForm = false;addCase={}">取 消</el-button>
      </div>
    </el-dialog>

    

    </div>
</template>

<script>

import qs from 'qs'

export default {
   data() {
      return {
        adviceCategory:'',
        adviceStatus:'',
        medicalAdviceList: [],
        total:100,
        pageNum:1,
        pageSize:5,
        headers:{},
        updateMedicalAdviceForm:false,
        updateMedicalAdvice:{
            id:'',
            patientName:'',
            doctorName:'',
            adviceCategory:'',
            projectId:'',
            projectName:'',
            adviceStatus:'',
            createTime:'',
            adviceDescription:''
            
        },
        projectList:[],
        formLabelWidth: '120px',

        addPrescriptionForm: false,
        addPrescription:{
          drugId:'',
          num:''
        },
         rules1: {
          drugId: [
            { required: true, message: '请选择药品', trigger: 'change' },
            // { min: 3, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur' }
          ],
          num: [
            { required: true, message: '请输入数量', trigger: 'blur' },
            // { min: 3, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur' }
          ]
        },

        drugList:[],
        num:'',

        doctorId:'',

        addCaseForm:false,
        addCase:{
          id:'',
          patientName:'',
          doctorName:'',
          patientId:'',
          doctorId:'',
          projectId:'',
          projectName:'',
          adviceDescription:'',
          spare1:'',
          caseDescription:''
        },
        rules: {
          spare1: [
            { required: true, message: '请输入病例症状', trigger: 'blur' },
            // { min: 3, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur' }
          ],
          caseDescription: [
            { required: true, message: '请输入症状描述', trigger: 'blur' },
            // { min: 3, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur' }
          ]
        },

      }
   },
   created(){
      
    this.$axios.get("/api/doctor/findProjectList")
          .then(res=>{
              console.log(res.data);
              this.projectList=res.data;
          }),
    this.$axios.get("/api/doctor/findDrugList")
          .then(res=>{
              console.log(res.data);
              this.drugList=res.data;
          }),
    this.headers={tokenStr:window.localStorage.getItem('tokenStr')};

    var roleId = window.localStorage.getItem("roleId")
    if(roleId=='5'){
        this.doctorId =  parseInt(window.localStorage.getItem("workerId"))
      }else if(roleId=='9'){
        this.doctorId=''
      }
      this.search();
   },
   methods:{
     //执行下达处方
     doAddPrescription(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            var prescription=this.addPrescription;
           console.log(prescription)
            this.$axios.post("/api/doctor/gotoAddPrescription",prescription)
            .then(res=>{
                if(res.status==4001){
                     this.$message({
                        type: "error",
                         message: "没有权限!",
                          duration:2000
                     });
                }else{
                     this.$message({
                        type: "success",
                         message: "下达处方成功!",
                         duration:2000
                     });
                    this.closeAddPrescriptionForm();
                    this.search();
                }
            })
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },  

     //执行添加病例
     doAddCase(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            var cases = this.addCase;
          //  console.log(prescription)
            this.$axios.post("/api/doctor/gotoAddCase",cases)
            .then(res=>{
                if(res.status==4001){
                     this.$message({
                        type: "error",
                         message: "没有权限!",
                          duration:2000
                     });
                }else{
                     this.$message({
                        type: "success",
                         message: "新增病例成功!",
                         duration:2000
                     });
                     this.addCaseForm=false;
                    this.search();
                }
            })
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },  

       //准备下达处方
       gotoAddCase(id,patientName,doctorName,patientId,doctorId,projectId,projectName,adviceDescription){
           this.addCase={
                id:id,
                patientName:patientName,
                doctorName:doctorName,
                patientId:patientId,
                doctorId:doctorId,
                projectId:projectId,
                projectName:projectName,
                adviceDescription:adviceDescription
           };
           this.addCaseForm=true;
       },


        handleChange(value) {
        console.log(value);
      },
      //关闭下达处方窗口
       closeAddPrescriptionForm(){
            this.addPrescription={
                id:'',
                patientName:'',
                doctorName:'',
                patientId: '',
                doctorId: '',
                adviceCategory:'',
                projectId:'',
                projectName:'',
                adviceDescription:'',
                    };
            this.addPrescriptionForm=false;
       },
    

       //准备下达处方
       gotoAddPrescription(id,patientName,doctorName,patientId,doctorId,adviceCategory,projectId,projectName,adviceDescription){
           this.addPrescription={
                id:id,
                patientName:patientName,
                doctorName:doctorName,
                patientId: patientId,
                doctorId: doctorId,
                adviceCategory:adviceCategory,
                projectId:projectId,
                projectName:projectName,
                // createTime:createTime,
                adviceDescription:adviceDescription,
                // adviceStatus:adviceStatus,
                
           };
           this.addPrescriptionForm=true;
       },


    //停止医嘱
    gotoStopMedicalAdvice(id){
           this.$confirm('确定要停止医嘱吗?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
            }).then(() => {
                this.$axios.post("/api/doctor/gotoStopMedicalAdviceById",qs.stringify({'id':id}),{
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
                            message: '停止成功!',
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

        //关闭修改医嘱窗口
       closeupdateMedicalAdviceForm(){
            this.updateMedicalAdvice={
                        id:'',
                        patientName:'',
                        doctorName:'',
                        adviceCategory:'',
                        projectId:'',
                        projectName:'',
                        adviceStatus:'',
                        createTime:'',
                        adviceDescription:''
                    };
            this.updateMedicalAdviceForm=false;
       },

       //执行医嘱项目
       doUpdateMedicalAdvice(){
           var medicalAdvice=this.updateMedicalAdvice;
            this.$axios.post("/api/doctor/updateMedicalAdviceProject",medicalAdvice)
            .then(res=>{
                if(res.status==4001){
                     this.$message({
                        type: "error",
                         message: "没有权限!",
                          duration:2000
                     });
                }else{
                     this.$message({
                        type: "success",
                         message: "修改成功!",
                         duration:2000
                     });
                    this.closeupdateMedicalAdviceForm();
                    this.search();
                }
            })
       },

       //准备执行医嘱
       gotoUpdateMedicalAdvice(id,
                      patientName,
                      doctorName,
                      adviceCategory,
                      projectId,
                      projectName,
                      adviceDescription,
                      adviceStatus,
                      createTime){
           this.updateMedicalAdvice={
                id:id,
                patientName:patientName,
                doctorName:doctorName,
                adviceCategory:adviceCategory,
                projectId:projectId,
                projectName:projectName,
                adviceDescription:adviceDescription,
                adviceStatus:adviceStatus,
                createTime:createTime
           };
           this.updateMedicalAdviceForm=true;
       },

      //查询医嘱列表
        search(){
            console.log("---")
            this.$axios.get("/api/doctor/getMedicalAdviceList",{params:{
            doctorId:this.doctorId,
            adviceCategory:this.adviceCategory,
            adviceStatus:this.adviceStatus,
            pageNum:this.pageNum,
            pageSize:this.pageSize
            }})
            .then(res=>{
                 console.log("=========================");
               console.log(res.data.data.list);
                this.medicalAdviceList=res.data.data.list;
                this.total=res.data.data.total;
                this.pageNum=res.data.data.pageNum;
                this.pageSize=res.data.data.pageSize;

            })
        },
        changeSize(value){
            this.pageSize=value;
            this.pageNum=1;
            this.search();
        },
        changePage(value){
            this.pageNum=value;
            this.search();
        },
          getIndex(i){
            return (i+1)+this.pageSize*(this.pageNum-1);
         },
   }
}
</script>

<style scoped>

</style>
