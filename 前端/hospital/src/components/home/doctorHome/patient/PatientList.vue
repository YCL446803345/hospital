<template>
    <div>
        <!-- 面包xie导航 -->
        <el-breadcrumb separator="/">
            <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item><a href="#/patientList">住院病人信息管理</a></el-breadcrumb-item>
            <el-breadcrumb-item>住院信息管理列表</el-breadcrumb-item>
        </el-breadcrumb>
        <el-row style="margin-top:10px;margin-bottom:10px">
            <el-col :span="2">
                <el-input v-model="name" placeholder="姓名">
                </el-input>
            </el-col>
            
            <el-col :span="3">
                <el-input v-model="no" placeholder="编号">
                </el-input>
            </el-col>
            
            <el-col :span="2">
                <el-select v-model="gender" placeholder="性别">
                    <el-option label="性别" value="" ></el-option>
                    <el-option label="男" value="1" ></el-option>
                    <el-option label="女" value="2" ></el-option>
                </el-select>
            </el-col>

            <el-col :span="4">
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
                width="120">
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
                width="150">
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
                prop="nurseName"
                label="护士名"
                width="80">
            </el-table-column>

            <el-table-column
                prop="doctorName"
                label="主治医生"
                width="80">
            </el-table-column>

            <el-table-column
                prop="baseDesc"
                label="病情"
                width="80">
            </el-table-column>
            
            <el-table-column label="操作">
               <template slot-scope="scope">
                  <el-button size="mini" type="success"
                  @click="gotoAddMedicalAdvice(scope.row.id)">下医嘱</el-button>
                  <el-button size="mini" type="success"
                  @click="gotoAddConsultationApplication( scope.row.id)">开会诊</el-button>
                  <el-button size="mini" type="success"
                  @click="gotoAddPrescription(scope.row.id)">开处方</el-button>
                  <el-button size="mini" type="success"
                  @click="gotoAddLeaveHospital(scope.row.id)">提出院</el-button>
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
        // updateTeacherForm:false,
        headers:{}
      }
   },
   created(){
      this.search();
      this.headers={tokenStr:window.localStorage.getItem('tokenStr')};
   },
   methods:{
      //查询病人信息列表
        search(){
            
            console.log("---"+this.cardId)
            this.$axios.get("/api/doctor/getPatientList",{params:{name:this.name,no:this.no,gender:this.gender,
                  cardId:this.cardId,status:this.status,pageNum:1,pageSize:this.pageSize}})
            .then(res=>{
               console.log(res.data.data);
                this.patientList=res.data.data.list;
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
            this.$axios.get("/api/findPatients",{params:{name:this.searchName,no:this.no,gender:this.gender,
                  caedId:this.cardId,status:this.status,pageNum:this.pageNum,pageSize:this.pageSize}})
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
