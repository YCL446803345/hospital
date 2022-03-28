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
                    <!-- <el-option label="医嘱状态" value="" ></el-option> -->
                    <el-option label="待校对" value="1" ></el-option>
                    <el-option label="已校对" value="2" ></el-option>
                    <el-option label="已执行" value="3" ></el-option>
                    <!-- <el-option label="已停止" value="4" ></el-option> -->
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
            <el-table-column prop="doctorName" label="主治医生" width="80"></el-table-column>
            <el-table-column prop="adviceCategory" label="医嘱类型" width="180">
                <template slot-scope="scope">
                    <span v-if="scope.row.adviceCategory=='1'">长期医嘱</span>
                    <span v-if="scope.row.adviceCategory=='2'">临时医嘱</span>
                    <span v-if="scope.row.adviceCategory=='3'">一般医嘱</span>
                </template>
            </el-table-column>

            <el-table-column prop="projectId" label="项目类型" width="180">
                <template slot-scope="scope">
                    <span v-if="scope.row.projectId=='1'">内外科查体</span>
                    <span v-if="scope.row.projectId=='2'">眼科视力检查</span>
                    <span v-if="scope.row.projectId=='3'">尿检</span>
                    <span v-if="scope.row.projectId=='4'">乙肝</span>
                    <span v-if="scope.row.projectId=='5'">血脂</span>
                    <span v-if="scope.row.projectId=='6'">血糖</span>
                    <span v-if="scope.row.projectId=='7'">头颅CT</span>
                    <span v-if="scope.row.projectId=='8'">肺部CT</span>
                    <span v-if="scope.row.projectId=='9'">核磁共振成像</span>
                    <span v-if="scope.row.projectId=='10'">开颅手术</span>
                    <span v-if="scope.row.projectId=='11'">脂肪瘤切除手术</span>
                    <span v-if="scope.row.projectId=='12'">痔疮切除手术</span>
                    <span v-if="scope.row.projectId=='13'">包皮切除手术</span>
                    <span v-if="scope.row.projectId=='14'">清创缝合术</span>
                    <span v-if="scope.row.projectId=='15'">肌腱吻合术</span>
                </template>
            </el-table-column>

            <el-table-column prop="adviceDescription" label="医嘱描述" width="180">
            </el-table-column>

            <el-table-column prop="adviceStatus" label="医嘱状态" width="180">
                <template slot-scope="scope">
                <span v-if="scope.row.adviceStatus=='1'">待校对</span>
                <span v-if="scope.row.adviceStatus=='2'">已校对</span>
                <span v-if="scope.row.adviceStatus=='3'">已执行</span>
                <span v-if="scope.row.adviceStatus=='4'">已停止</span>
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
                  <el-button size="mini" type="primary" @click="gotoUpdateMedicalAdvice(
                      scope.row.id,
                      scope.row.patientName,
                      scope.row.doctorName,
                      scope.row.adviceCategory,
                      scope.row.projectId,
                      scope.row.adviceDescription,
                      scope.row.adviceStatus,
                      scope.row.createTime
                      )">项目执行</el-button>
                  <el-button size="mini" type="success" @click="gotoAddPrescription(
                      scope.row.id,
                      scope.row.patientName,
                      scope.row.doctorName,
                      scope.row.patientId,
                      scope.row.doctorId,
                      scope.row.adviceCategory,
                      scope.row.projectId,
                      scope.row.adviceDescription
                      )">下达处方</el-button>
                  <el-button size="mini" type="danger"
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
            {{ updateMedicalAdvice.adviceStatus ==='1'?'待执行': 
               updateMedicalAdvice.adviceStatus ==='2'?'已执行':'已撤销' }}
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
            {{ addPrescription.projectId ==='1'?'内外科查体': 
               addPrescription.projectId ==='2'?'眼科视力检查': 
               addPrescription.projectId ==='3'?'尿检': 
               addPrescription.projectId ==='4'?'乙肝': 
               addPrescription.projectId ==='5'?'血脂': 
               addPrescription.projectId ==='6'?'血糖': 
               addPrescription.projectId ==='7'?'头颅CT': 
               addPrescription.projectId ==='8'?'肺部CT': 
               addPrescription.projectId ==='9'?'核磁共振成像': 
               addPrescription.projectId ==='10'?'开颅手术': 
               addPrescription.projectId ==='11'?'脂肪瘤切除手术': 
               addPrescription.projectId ==='12'?'痔疮切除手术': 
               addPrescription.projectId ==='13'?'包皮切除手术': 
               addPrescription.projectId ==='14'?'清创缝合术':'肌腱吻合术' }}
          </el-descriptions-item>
        

          <el-descriptions-item>
            <template slot="label"><i class="el-icon-user"></i>描述</template>
            {{ addPrescription.adviceDescription }}
          </el-descriptions-item>

        </el-descriptions>
      </template>

      <el-divider></el-divider>

      <el-form :model="addPrescription">

          <el-form-item label="药品" :label-width="formLabelWidth" >
            <el-select v-model="addPrescription.drugId" placeholder="药品" >
                <el-option v-for="drug in drugList" :key="drug.id" :label="drug.name" :value="drug.id"  ></el-option>
            </el-select>
        </el-form-item>

        <el-form-item label="数量" :label-width="formLabelWidth">
          <!-- <el-input v-model="addPrescription.num" autocomplete="off"></el-input> -->
          <el-input-number v-model="addPrescription.num" @change="handleChange" :min="1" :max="20" label="描述文字"></el-input-number>
        </el-form-item>

      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="doAddPrescription">下 达</el-button>
        <el-button @click="addPrescriptionForm = false;addPrescription={}">取 消</el-button>
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
            adviceStatus:'',
            createTime:'',
            adviceDescription:''
        },
        projectList:[],
        formLabelWidth: '120px',

        addPrescriptionForm: false,
        addPrescription:{},
        drugList:[],
        num:'',

      }
   },
   created(){
      this.search();
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


   },
   methods:{
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
                adviceDescription:'',
                    };
            this.addPrescriptionForm=false;
       },
    
    //执行医嘱项目
       doAddPrescription(){
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
       },
       //准备下达处方
       gotoAddPrescription(id,patientName,doctorName,patientId,doctorId,adviceCategory,projectId,adviceDescription){
           this.addPrescription={
                id:id,
                patientName:patientName,
                doctorName:doctorName,
                patientId: patientId,
                doctorId: doctorId,
                adviceCategory:adviceCategory,
                projectId:projectId,
                // createTime:createTime,
                adviceDescription:adviceDescription,
                // adviceStatus:adviceStatus,
                
           };
           this.addPrescriptionForm=true;
       },





    //停止会诊
    gotoStopMedicalAdvice(id) {
        console.log(id)
        this.$axios.post("/api/doctor/gotoStopMedicalAdviceById",qs.stringify({'id':id}),{
            params: { id: id }
        }).then(res => {  
          console.log(res.data)
            if(res.status===200){
                this.$message({
                    showClose: true,
                    message: "操作成功",
                    type: "success",
                    duration: 600
                });
                this.search();
            }else if(res.status===4001){
                this.$message({
                    showClose: true,
                    message: "没有权限",
                    type: "error",
                    duration: 600
                });
                this.search();
            }
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
                      adviceDescription,
                      adviceStatus,
                      createTime){
           this.updateMedicalAdvice={
                id:id,
                patientName:patientName,
                doctorName:doctorName,
                adviceCategory:adviceCategory,
                projectId:projectId,
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
