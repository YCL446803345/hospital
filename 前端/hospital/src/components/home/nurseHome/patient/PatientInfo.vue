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
                width="120">
            </el-table-column>

            <el-table-column
                prop="appointmenttTime"
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
                prop="doctorName"
                label="主治医生"
                width="80">
                <template slot-scope="scope">
                     <el-tag :type="scope.row.doctorName==='未分配'?'danger':'primary'" disable-transitions>
                         {{scope.row.doctorName}}
                     </el-tag>
                </template>
            </el-table-column>

            <el-table-column
                prop="baseDesc"
                label="病情"
                width="150">
            </el-table-column>
            
            <el-table-column label="操作">
               <template slot-scope="scope">
                  <el-button
                  size="mini"
                  type="primary"
                  @click="gotoUpdatePatient(scope.row.id,scope.row.name,scope.row.gender,scope.row.cardId,scope.row.age,scope.row.baseDesc,scope.row.phone)">编辑</el-button>
                  <!-- <el-button
                  size="mini"
                  type="danger"
                  @click="deleteTeacher( scope.row.id)">删除</el-button> -->
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

        <!-- 修改讲师 -->
        <el-dialog :visible.sync="updatePatientForm" >
        <h1 align="center">修改病人信息 </h1><br>
        <el-form :model="updatePatient">
            <el-form-item label="姓名" :label-width="formLabelWidth">
                 <el-input v-model="updatePatient.name" autocomplete="off"></el-input>
            </el-form-item>

            <el-form-item label="身份证" :label-width="formLabelWidth">
                 <el-input v-model="updatePatient.cardId" autocomplete="off"></el-input>
            </el-form-item>

            <el-form-item label="年龄" :label-width="formLabelWidth">
                 <el-input v-model="updatePatient.age" autocomplete="off"></el-input>
            </el-form-item>

            <el-form-item label="性别" :label-width="formLabelWidth">
                <el-radio v-model="updatePatient.gender" label="1" value=1>男</el-radio>
                <el-radio v-model="updatePatient.gender" label="2" value=2>女</el-radio>
            </el-form-item>

            <el-form-item label="手机号" :label-width="formLabelWidth">
                 <el-input v-model="updatePatient.phone" autocomplete="off"></el-input>
            </el-form-item>

             <el-form-item label="病情" :label-width="formLabelWidth">
                 <el-input
                        type="textarea"
                        :autosize="{ minRows: 2, maxRows: 4}"
                        placeholder="请输入内容"
                        v-model="updatePatient.baseDesc"
                        autocomplete="off">
                </el-input>
            </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button type="primary" @click="updatePatient1">修 改</el-button>
                <el-button @click="closeUpdatePatient">取 消</el-button>
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
        updatePatientForm:false,
        headers:{},
        updatePatient:{
            id:'',
            name:'',
            gender:'',
            cardId:'',
            age:'',
            baseDesc:'',
            phone:''
        },
        formLabelWidth: '120px'
      }
   },
   created(){
      this.search();
      this.headers={tokenStr:window.localStorage.getItem('tokenStr')};
   },
   methods:{
       updatePatient1(){
            var patient=this.updatePatient;
            this.$axios.post("/api/updatePatient",patient)
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
                    this.closeUpdatePatient();
                    this.search();
                }
            })
       },
       closeUpdatePatient(){
            this.updatePatient={
                id:'',
                name:'',
                gender:'',
                cardId:'',
                age:'',
                baseDesc:'',
                phone:''
           }
           this.updatePatientForm=false;
       },
       gotoUpdatePatient(id,name,gender,cardId,age,baseDesc,phone){
           this.updatePatient={
                id:id,
                name:name,
                gender:gender,
                cardId:cardId,
                age:age,
                baseDesc:baseDesc,
                phone:phone
           }
           this.updatePatientForm=true;

       },
      //查询病人信息列表
        search(){
            console.log("---"+this.cardId)
            this.$axios.get("/api/findPatientsByChangeDept",{params:{name:this.name,no:this.no,gender:this.gender,
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
            this.$axios.get("/api/findPatientsByChangeDept",{params:{name:this.searchName,no:this.no,gender:this.gender,
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
