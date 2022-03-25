<template>
    <div>
        <!-- 面包xie导航 -->
        <el-breadcrumb separator="/">
            <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item><a href="#/patientInfo">在院病人信息管理</a></el-breadcrumb-item>
            <el-breadcrumb-item>在院病人信息管理列表</el-breadcrumb-item>
        </el-breadcrumb>
        <el-row style="margin-top:10px;margin-bottom:10px">
            <el-col :span="8">
                <el-input v-model="name" placeholder="姓名">
                </el-input>
            </el-col>
            
            <el-col :span="8">
                <el-input v-model="no" placeholder="编号">
                </el-input>
            </el-col>
            
            <el-col :span="3">
                <el-select v-model="gender" placeholder="性别">
                    <el-option label="性别" value="" ></el-option>
                    <el-option label="男" value="1" ></el-option>
                    <el-option label="女" value="2" ></el-option>
                </el-select>
            </el-col>

            <el-col :span="8">
                <el-input v-model="cardId" placeholder="身份证">
                </el-input>
                <!--带搜索按钮的文本框 -->
            </el-col>
           
            <el-col :span="1.5" style="margin-left:2px;">
                <el-button type="success" @click="search1">查询</el-button>
               
            </el-col>

            <el-col :span="1" style="margin-left:2px;">
                 <el-button type="warning" @click="name='',no='',paheNum=1,pageSize=5,gender='',cardId='',deptId=''">清空</el-button>
            </el-col>
            
        </el-row>
        
        <el-table
            :data="patientList"
            style="width: 100%">

            <el-table-column
                type="index"
                :index='getIndex'
                label="序号"
                width="50">
            </el-table-column>

             <el-table-column
                prop="name"
                label="姓名"
                width="80">
            </el-table-column>

            <el-table-column
                prop="no"
                label="病人编号"
                width="180">
            </el-table-column>
            
            <el-table-column
                prop="gender"
                label="性别"
                width="50">
                <template slot-scope="scope">
                         {{scope.row.gender==='1'?'男':'女'}}
                </template>
            </el-table-column>

             
            <el-table-column
                prop="age"
                label="年龄"
                width="50">
            </el-table-column>

            <el-table-column
                prop="cardId"
                label="身份证号"
                width="180">
            </el-table-column>

            <el-table-column
                prop="phone"
                label="手机号"
                width="150">
            </el-table-column>

            <el-table-column
                label="入院时间"
                width="180">
                <template slot-scope="scope">
                    <i class="el-icon-time"></i>
                    <span style="margin-left: 10px">{{ scope.row.appointmenttTime}}</span>
                </template>
            </el-table-column>

            <el-table-column
                prop="deptName"
                label="科室"
                width="80">
            </el-table-column>

            <el-table-column
                prop="bedCode"
                label="床位编号"
                width="80">
                 <template slot-scope="scope">
                     <el-tag :type="scope.row.bedCode===null?'danger':'primary'" disable-transitions>
                         {{scope.row.bedCode===null?'未分配':scope.row.bedCode}}
                     </el-tag>
                </template>
            </el-table-column>

            <el-table-column
                label="护士名"
                width="80">
                <template slot-scope="scope">
                     <el-tag :type="scope.row.nurseName==='未分配'?'danger':'primary'" disable-transitions>
                         {{scope.row.nurseName}}
                     </el-tag>
                </template>
            </el-table-column>

            <el-table-column
                label="主治医生"
                width="80">
                <template slot-scope="scope">
                     <el-tag :type="scope.row.doctorName==='未分配'?'danger':'primary'" disable-transitions>
                         {{scope.row.doctorName}}
                     </el-tag>
                </template>
            </el-table-column>
            <el-table-column label="操作">
               <template slot-scope="scope">
                  <el-button
                  size="mini"
                  type="primary"
                  @click="gotoMedicalAdviceView(scope.row.id,scope.row.no,scope.row.bedCode,scope.row.deptName,scope.row.name)">查看医嘱记录</el-button>
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

         <!-- 查看病人护理记录 -->
        <el-dialog :visible.sync="medicalAdviceViewForm" width="83%">
        <h1 align="center">查看医嘱记录 </h1><br>
        <template>
        <el-descriptions class="margin-top" title="" :column="4"  border>
            <el-descriptions-item>
            <template slot="label">
                <i class="el-icon-user"></i>
                姓名
            </template>
            {{patient.name}}
            </el-descriptions-item>
            
            <el-descriptions-item>
            <template slot="label">
                <i class="el-icon-tickets"></i>
                编号
            </template>
            {{patient.no}}
            </el-descriptions-item>
            
            <el-descriptions-item>
            <template slot="label">
                <i class="el-icon-tickets"></i>
                床位号
            </template>
            {{patient.bedCode}}
            </el-descriptions-item>
            <el-descriptions-item>
            <template slot="label" >
                <i class="el-icon-tickets"></i>
                科室
            </template>
           {{patient.deptName}}
            </el-descriptions-item>
            
        </el-descriptions>
        </template>

        <el-divider></el-divider>

        <template>
        <el-tabs style="height:50px;" @tab-click="changeStatus"  v-model="adviceStatus">
            <el-tab-pane label="医嘱信息" name=''></el-tab-pane>
            <el-tab-pane label="校对医嘱" name='1'></el-tab-pane>
            <el-tab-pane label="执行医嘱" name='2'></el-tab-pane>
        </el-tabs>

        
        
        
        <el-form >

            <el-col :span="8">
                <el-input v-model="doctorName" placeholder="医生姓名">
                </el-input>
            </el-col>

            <el-col :span="3">
                <el-select v-model="adviceCategory" placeholder="医嘱类型">
                    <el-option label="请选择" value="" ></el-option>
                    <el-option label="长期医嘱" value="1" ></el-option>
                    <el-option label="临时医嘱" value="2" ></el-option>
                    <el-option label="一般医嘱" value="3" ></el-option>
                </el-select>
            </el-col>

            <el-col :span="3">
                <el-select v-model="projectType" placeholder="项目类型">
                    <el-option label="请选择" value="" ></el-option>
                    <el-option label="检查" value="1" ></el-option>
                    <el-option label="体检" value="2" ></el-option>
                    <el-option label="手术" value="3" ></el-option>
                </el-select>
            </el-col>
           
            <el-col :span="1.5" style="margin-left:2px;">
                <el-button type="success" @click="search1">查询</el-button>
            </el-col>

            <el-col :span="1" style="margin-left:2px;">
                 <el-button type="warning" @click="doctorName='',paheNum1=1,pageSize1=5,adviceCategory='',projectType=''">清空</el-button>
            </el-col>
         
        
        <el-table
            :data="medicalAdviceList"
            style="width: 100%">

            <el-table-column
                type="index"
                :index='getIndex1'
                label="序号"
                width="50">
            </el-table-column>

            <el-table-column
                label="经治医生"
                width="100">
                 <template slot-scope="scope">
                     <el-tag type="primary" disable-transitions>
                         {{scope.row.doctorName}}
                     </el-tag>
                </template>
            </el-table-column>

             <el-table-column
                label="医嘱类型"
                width="100">
                 <template slot-scope="scope">
                     <el-tag :type="scope.row.adviceCategory===1?'danger':scope.row.adviceCategory===2?'warning':'primary'" disable-transitions>
                         {{scope.row.bedCode==1?'长期医嘱':scope.row.bedCode==2?'临时医嘱':'一般医嘱'}}
                     </el-tag>
                </template>
            </el-table-column>

            <el-table-column
                prop="projectName"
                label="项目名"
                width="180">
            </el-table-column>

             <el-table-column
                label="项目类型"
                width="100">
                 <template slot-scope="scope">
                     <el-tag :type="scope.row.projectType===3?'danger':scope.row.projectType===2?'warning':'primary'" disable-transitions>
                         {{scope.row.projectType==3?'手术':scope.row.projectType==2?'体检':'检查'}}
                     </el-tag>
                </template>
            </el-table-column>


            <el-table-column
                prop="adviceDescription"
                label="描述"
                width="200">
            </el-table-column>

            <el-table-column
                label="创建时间"
                width="180">
                <template slot-scope="scope">
                    <i class="el-icon-time"></i>
                    <span style="margin-left: 10px">{{ scope.row.createTime}}</span>
                </template>
            </el-table-column>

          
           <el-table-column
                label="医嘱状态"
                width="100">
                 <template slot-scope="scope">
                     <el-tag :type="scope.row.adviceStatus===1?'warning':scope.row.adviceStatus===2?'primary':scope.row.adviceStatus===3?'sucssess':'info'" disable-transitions>
                         {{scope.row.adviceStatus==1?'校对中':scope.row.adviceStatus==2?'待执行':scope.row.adviceStatus==3?'已执行':'已撤销'}}
                     </el-tag>
                </template>
            </el-table-column>
            <el-table-column label="操作">
               <template slot-scope="scope">
                  <el-button 
                  v-if='scope.row.adviceStatus==1'
                  size="mini"
                  type="warning"
                  
                  @click="checkMedicalAdvice(scope.row.id)">校对</el-button>
                  <el-button 
                  v-if='scope.row.adviceStatus==1'
                  size="mini"
                  type="danger"
                  @click="stopMedicalAdvice(scope.row.id)">停止医嘱</el-button>
                   
                   <el-button
                   v-if='scope.row.adviceStatus==2'
                  size="mini"
                  type="warning"
                  @click="doMedicalAdvice(scope.row.id)">执行医嘱</el-button>

                   <el-button
                   v-if='scope.row.adviceStatus==3'
                  size="mini"
                  type="info"
                  disabled
                  @click="doMedicalAdvice(scope.row.id)">无操作</el-button>

                   <el-button
                   v-if='scope.row.adviceStatus==4'
                  size="mini"
                  type="info"
                  disabled
                  @click="doMedicalAdvice(scope.row.id)">无操作</el-button>
                   
               </template>
            </el-table-column>
        </el-table>
       
        <el-pagination
            background
            layout="prev, pager,next, sizes,->,total"
            :total="total1"
            :current-page='pageNum1'
            :page-sizes=[5,10,15,20]
            :page-size='pageSize1'
            @size-change='changeSize1'
            @current-change='changePage1'
           >
        </el-pagination>
            
        </el-form>     


        </template>
            
            <div slot="footer" class="dialog-footer">
                <el-button type="primary" @click="closeMedicalAdviceView">确 定</el-button>
            </div>

        <template>
            
        </template>
            
        </el-dialog>


    </div>
</template>

<script>
export default {
   data() {
      return {
        nurseId:'',
        nurseDeptId:'',
        no:'',
        name:'',
        gender:'',
        cardId:'',
        status:1,
        patientList: [],
        total:100,
        pageNum:1,
        pageSize:5,
        total1:100,
        pageNum1:1,
        pageSize1:5,
        headers:{},
        nursingLevel:'',
        medicalAdviceViewForm:false,
        patient:{
            id:'',
            no:'',
            bedCode:'',
            deptName:'',
            name:''
        },
        formLabelWidth: '120px',
        medicalAdviceList:[],
        projectType:'',
        doctorName:'',
        adviceStatus:'',
        adviceCategory:''
      }
   },
   created(){
      var a = window.localStorage.getItem("roleId")
      
      if(a=='4'){
        this.nurseDeptId = parseInt(window.localStorage.getItem("deptId"))
      }else if(a=='2'){
        this.nurseId =  parseInt(window.localStorage.getItem("workerId"))
      }else if(a=='9'){
        this.nurseDeptId=''
        this.nurseId=''
      }

    //   console.log('nurseDeptId'+this.nurseDeptId)
    //   console.log('nurseId'+this.nurseId)

      this.search();
   },
   methods:{
        checkMedicalAdvice(id){
            this.$confirm('是否完成校对?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                // type: 'warning'
            }).then(() => {
                 this.$axios.get("/api/checkMedicalAdvice",{params:{id:id}})
                .then(res=>{
                if(res.data.status==4001){
                     this.$message({
                        type: "error",
                         message: "没有权限!",
                          duration:2000
                     });
                }else{
                    var patient=this.updateBed;
                     this.$message({
                        // type: "error",
                         message: "校对成功!",
                         duration:2000
                     });
                    this.search1();
                }
                })
            
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消',
                     duration:2000
                });          
            });



           
            
        },
        stopMedicalAdvice(id){
            this.$confirm('此操作将永久撤销数据, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                // type: 'warning'
            }).then(() => {
                this.$axios.get("/api/stopMedicalAdvice",{params:{id:id}})
                .then(res=>{
                if(res.data.status==4001){
                     this.$message({
                        type: "error",
                         message: "没有权限!",
                          duration:2000
                     });
                }else{
                    var patient=this.updateBed;
                     this.$message({
                        // type: "error",
                         message: "撤销成功!",
                         duration:2000
                     });
                    this.search1();
                }
            })
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消',
                     duration:2000
                });          
            });


        },
        doMedicalAdvice(id){
                 this.$confirm('是否完成执行?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                // type: 'warning'
            }).then(() => {
                this.$axios.get("/api/doMedicalAdvice",{params:{id:id}})
                .then(res=>{
                if(res.data.status==4001){
                     this.$message({
                        type: "error",
                         message: "没有权限!",
                          duration:2000
                     });
                }else{
                    var patient=this.updateBed;
                     this.$message({
                        // type: "error",
                         message: "执行成功!",
                         duration:2000
                     });
                    this.search1();
                }
            })
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消',
                     duration:2000
                });          
            });

        },
       changeStatus(){
           this.search1();
       },
       search1(){
        this.$axios.get("/api/getMedicalAdvices",{params:{patientId:this.patient.id,projectType:this.projectType,doctorName:this.doctorName,
        adviceStatus:this.adviceStatus,adviceCategory:this.adviceCategory,pageNum:this.pageNum1,pageSize:this.pageSize1}})
         .then(res=>{
               console.log(res.data);
                this.medicalAdviceList=res.data.list;
                this.total1=res.data.total;
                this.pageNum1=res.data.pageNum;
                this.pageSize1=res.data.pageSize;
        })
       },
       changeSize1(value){
            this.pageSize1=value;
            this.pageNum1=1;
            this.search1()
       },
       changePage1(value){
             this.pageNum1=value;
             this.$axios.get("/api/findNursingRecordsByPatientId",{params:{patientId:this.patient.id,patientId:this.addNursingRecord.id,nursingLevel:this.nursingLevel,pageNum:this.pageNum1,pageSize:this.pageSize1}})
            .then(res=>{
               console.log(res.data);
                this.medicalAdviceList=res.data.list;
                this.total1=res.data.total;
                this.pageNum1=res.data.pageNum;
                this.pageSize1=res.data.pageSize;
            })
       },
       closeMedicalAdviceView(){
           this.patient={
               id:'',
               no:'',
               bedCode:'',
               deptName:'',
               name:''
           }

           this.projectType=''
           this.doctorName=''
           this.adviceStatus=''
           this.adviceCategory=''
           this.medicalAdviceViewForm=false;
       },
       gotoMedicalAdviceView(id,no,bedCode,deptName,name){
            this.patient={
               id:id,
               no:no,
               bedCode:bedCode,
               deptName:deptName,
               name:name
           }
           this.search1();
           this.medicalAdviceViewForm=true;
       },
        search(){
            
            this.$axios.get("/api/findPatientsByChangeDept",{params:{name:this.name,no:this.no,gender:this.gender,
                  cardId:this.cardId,nurseId:this.nurseId,deptId:this.nurseDeptId,status:this.status,pageNum:1,pageSize:this.pageSize}})
            .then(res=>{
               console.log(res.data);
                this.patientList=res.data.list;
                this.total=res.data.total;
                this.pageNum=res.data.pageNum;
                this.pageSize=res.data.pageSize;
            })
        },
        changeSize(value){
            this.pageSize=value;
            this.pageNum=1;
            this.search();
        },
        changePage(value){
            this.pageNum=value;
            this.$axios.get("/api/findPatientsByChangeDept",{params:{name:this.name,no:this.no,gender:this.gender,
                  cardId:this.cardId,nurseId:this.nurseId,deptId:this.nurseDeptId,status:this.status,pageNum:this.pageNum,pageSize:this.pageSize}})
            .then(res=>{
                this.patientList=res.data.list;
                this.total=res.data.total;
                this.pageNum=res.data.pageNum;
                this.pageSize=res.data.pageSize;
            })
        },
          getIndex(i){
            return (i+1)+this.pageSize*(this.pageNum-1);
         },
         getIndex1(i){
            return (i+1)+this.pageSize1*(this.pageNum1-1);
         },
   }
}
</script>

<style scoped>

</style>
