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
           
            <el-col :span="1" style="margin-left:2px;">
                <el-button type="success" @click="search">查询</el-button>
               
            </el-col>

            <el-col :span="1" style="margin-left:2px;">
                 <el-button type="warning" @click="name='',no='',paheNum=1,pageSize=5,gender='',cardId=''">清空</el-button>
            </el-col>
            
        </el-row>
        <!-- 
            el-table数据表格
            :data 动态绑定 data中对象数组
            scope.row 表示对象数组的当前行对象
         -->
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

            <!-- <el-table-column
                label="头像"
                width="100">
                 <template slot-scope="scope">
                    <img  :src="scope.row.avatar" class="a-avatar" >
                </template>
            </el-table-column> -->
            
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
                     <el-tag :type="scope.row.bedCode==='未分配'?'danger':'primary'" disable-transitions>
                         {{scope.row.bedCode}}
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

            <!-- <el-table-column
                prop="baseDesc"
                label="病情"
                width="20">
            </el-table-column> -->
            
            <el-table-column label="操作">
               <template slot-scope="scope">
                  <el-button
                  size="mini"
                  type="primary"
                  @click="gotoUpdateBed(scope.row.id,scope.row.bedId,scope.row.deptId,scope.row.no
                   ,scope.row.phone,scope.row.bedCode,scope.row.deptName,scope.row.name)">修改床位</el-button>
                  <el-button
                  size="mini"
                  type="danger"
                  @click="gotoUpdateDoctorOrNurse(scope.row.id,scope.row.name,scope.row.no,scope.row.doctorName
                        ,scope.row.nurseName,scope.row.deptName,scope.row.deptId)">修改经理人</el-button>
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

          <!-- 修改床位 -->
        <el-dialog :visible.sync="updateBedForm" >
        <h1 align="center">修改床位 </h1><br>
        <template>
        <el-descriptions class="margin-top" title="" :column="3"  border>
            <el-descriptions-item>
            <template slot="label">
                <i class="el-icon-user"></i>
                姓名
            </template>
            {{updateBed.name}}
            </el-descriptions-item>
            
            <el-descriptions-item>
            <template slot="label">
                <i class="el-icon-tickets"></i>
                编号
            </template>
            {{updateBed.no}}
            </el-descriptions-item>
            
            <el-descriptions-item>
            <template slot="label">
                <i class="el-icon-tickets"></i>
                手机号
            </template>
            {{updateBed.phone}}
            </el-descriptions-item>
            <el-descriptions-item>
            <template slot="label" >
                <i class="el-icon-tickets"></i>
                科室
            </template>
           {{updateBed.deptName===''?'暂未分配':updateBed.deptName}}
            </el-descriptions-item>
            <el-descriptions-item>
            <template slot="label">
                <i class="el-icon-tickets"></i>
                床位名
            </template>
            {{updateBed.bedCode===''?'暂未分配':updateBed.bedCode}}
            </el-descriptions-item>
        </el-descriptions>
        </template>

        <el-divider></el-divider>


        <el-form :model="endUpdateBed">


            <!-- <el-form-item label="科室" :label-width="formLabelWidth" >
                <el-select v-model="endUpdateBed.deptId" placeholder="科室"  @change="getBedsByDeptId">
                    <el-option v-for="dept in deptList" :key="dept.id" :label="dept.name" :value="dept.id"  ></el-option>
                </el-select>
            </el-form-item> -->

            <el-form-item label="空床位" :label-width="formLabelWidth" >
                <el-select  v-model="endUpdateBed.bedId" placeholder="选择床位" >
                    <el-option v-for="bed in bedList" :key="bed.id" :label="bed.code" :value="bed.id"  ></el-option>
                </el-select>
            </el-form-item>
           
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button type="primary" @click="updateBed1">修 改</el-button>
                <el-button @click="closeUpdateBedForm">取 消</el-button>
            </div>
        </el-dialog>

        <!-- 修改医生和护士-->
        <el-dialog :visible.sync=" updateDoctorOrNurseForm" >
        <h1 align="center">修改医生/护士 </h1><br>
        <template>
        
        <el-descriptions class="margin-top" title="" :column="3"  border>
            
            <el-descriptions-item>
            <template slot="label">
                <i class="el-icon-user"></i>
                姓名
            </template>
            {{updateDoctorOrNurse.name}}
            </el-descriptions-item>
            
            <el-descriptions-item>
            <template slot="label">
                <i class="el-icon-tickets"></i>
                编号
            </template>
            {{updateDoctorOrNurse.no}}
            </el-descriptions-item>
            
            <el-descriptions-item>
            <template slot="label">
                <i class="el-icon-tickets"></i>
                科室
            </template>
            {{updateDoctorOrNurse.deptName}}
            </el-descriptions-item>
            <el-descriptions-item>
            <template slot="label" >
                <i class="el-icon-tickets"></i>
                护士
            </template>
           {{updateDoctorOrNurse.nurseName===''?'暂未分配':updateDoctorOrNurse.nurseName}}
            </el-descriptions-item>
            <el-descriptions-item>
            <template slot="label">
                <i class="el-icon-tickets"></i>
                医生名
            </template>
            {{updateDoctorOrNurse.doctorName===''?'暂未分配':updateDoctorOrNurse.doctorName}}
            </el-descriptions-item>
        </el-descriptions>
        </template>

        <el-divider></el-divider>
        
        <el-form :model="updateDoctorOrNurse">
            <el-form-item label="选择医生" :label-width="formLabelWidth" >
                <el-select v-model="updateDoctorOrNurse.doctorId" placeholder="选择医生"  @change="getBedsByDeptId">
                    <el-option v-for="doctor in doctorList" :key="doctor.id" :label="doctor.name" :value="doctor.id"  ></el-option>
                </el-select>
            </el-form-item>

            <el-form-item label="选择护士" :label-width="formLabelWidth" >
                <el-select  v-model="updateDoctorOrNurse.nurseId" placeholder="选择护士" >
                    <el-option v-for="nurse in nurseList" :key="nurse.id" :label="nurse.name" :value="nurse.id"  ></el-option>
                </el-select>
            </el-form-item>
           
         </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button type="primary" @click="updateDoctorOrNurse1">修 改</el-button>
                <el-button @click="closeUpdateDoctorOrNurse">取 消</el-button>
            </div>
        </el-dialog>
            

    </div>
</template>

<script>
export default {
   data() {
      return {
        no:'',
        name:'',
        gender:'',
        cardId:'',
        status:1,
        patientList: [],
        total:100,
        pageNum:1,
        pageSize:5,
        headers:{},
        updateBedForm:false,
        updateBed:{
            id:'',
            bedId:'',
            deptId:'',
            no:'',
            phone:'',
            bedCode:'',
            deptName:'',
            name:''
        },
        endUpdateBed:{
            id:'',
            bedId:'',
            deptId:'',
            no:'',
            phone:'',
            bedCode:'',
            deptName:'',
            name:''
        },
       updateDoctorOrNurse:{
           id:'',
           doctorId:'',
           nurseId:'',
           name:'',
           no:'',
           doctorName:'',
           deptName:'',
           nurseName:'',
           deptId:''
       },
        deptList:[],
        bedList:[],
        formLabelWidth: '120px',
        doctorList:[],
        nurseList:[],
        updateDoctorOrNurseForm:false
      }
   },
   created(){
      this.search();
      this.headers={tokenStr:window.localStorage.getItem('tokenStr')};
   },
   methods:{
       updateDoctorOrNurse1(){
            var patient=this.updateDoctorOrNurse;
            this.$axios.post("/api/updateDoctorOrNurse",patient)
            .then(res=>{
                if(res.data.status==4001){
                     this.$message({
                        type: "error",
                         message: "没有权限!",
                          duration:2000
                     });
                }else{
                    this.$message({
                        // type: "error",
                         message: "修改成功!",
                         duration:2000
                     });
                    this.closeUpdateDoctorOrNurse();
                    this.search();
                }
            })

       },
       closeUpdateDoctorOrNurse(){
           this.updateDoctorOrNurse={
               id:'',
               doctorId:'',
               nurseId:'',
               no:'',
               name:'',
               doctorName:'',
               nurseName:'',
               deptName:'',
               deptId:''
           };
           this.updateDoctorOrNurseForm=false;
       },
       closeUpdateBedForm(){
            this.updateBed={
                        id:'',
                        bedId:'',
                        deptId:'',
                        no:'',
                        phone:'',
                        bedCode:'',
                        deptName:'',
                        name:''
                    };
            this.endUpdateBed={
                        id:'',
                        bedId:'',
                        deptId:'',
                        no:'',
                        phone:'',
                        bedCode:'',
                        deptName:'',
                        name:''
                    };
            this.updateBedForm=false;
       },
       updateBed1(){
           var patient=this.endUpdateBed;
            this.$axios.post("/api/updateBed",patient)
            .then(res=>{
                if(res.data.status==4001){
                     this.$message({
                        type: "error",
                         message: "没有权限!",
                          duration:2000
                     });
                }else{
                    var patient=this.updateBed;
                    this.$axios.post("/api/changeBedStatusOff",patient).then(res=>{
                    this.$message({
                        // type: "error",
                         message: "修改成功!",
                         duration:2000
                     });
                    this.closeUpdateBedForm();
                    this.search();
                    })
                    
                }
            })
       },
       getBedsByDeptId(){
            
       },
       gotoUpdateDoctorOrNurse(id,name,no,doctorName,nurseName,deptName,deptId){
           this.updateDoctorOrNurse={
               id:id,
               doctorId:'',
               nurseId:'',
               no:no,
               name:name,
               doctorName:doctorName,
               nurseName:nurseName,
               deptName:deptName,
               deptId:deptId
           };
            this.$axios.get("/api/findDoctorsByDepeId",{params:{deptId:this.updateDoctorOrNurse.deptId}})
            .then(res=>{
                this.doctorList=res.data;
                console.log(res.data);
            })

            this.$axios.get("/api/findNursesByDepeId",{params:{deptId:this.updateDoctorOrNurse.deptId}})
            .then(res=>{
                this.nurseList=res.data;
                console.log(res.data);
            })
            this.updateDoctorOrNurseForm=true
       },
       gotoUpdateBed(id,bedId,deptId,no,phone,bedCode,deptName,name){
           this.updateBed={
                id:id,
                bedId:bedId,
                deptId:deptId,
                no:no,
                phone:phone,
                bedCode:bedCode,
                deptName:deptName,
                name:name
           };
           
           this.endUpdateBed={
                 id:id,
                bedId:bedId,
                deptId:deptId,
                no:no,
                phone:phone,
                bedCode:bedCode,
                deptName:deptName,
                name:name
            };

            // this.$axios.get("/api/findDepts")
            // .then(res=>{
            //    console.log(res.data);
            //     this.deptList=res.data;
            //     this.getBedsByDeptId();
            //     
            // })

             this.$axios.get("/api/findBedsByDeptId",{params:{deptId:this.endUpdateBed.deptId}})
                    .then(res=>{
                    this.bedList=res.data;
                    console.log(res.data);
                    this.updateBedForm=true;
                    })
            
       },
      //查询病人信息列表
        search(){
            
            this.$axios.get("/api/findPatients",{params:{name:this.name,no:this.no,gender:this.gender,
                  cardId:this.cardId,status:this.status,pageNum:1,pageSize:this.pageSize}})
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
            this.$axios.get("/api/findPatients",{params:{name:this.searchName,no:this.no,gender:this.gender,
                  caedId:this.cardId,status:this.status,pageNum:pageNum,pageSize:this.pageSize}})
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
   }
}
</script>

<style scoped>

</style>
