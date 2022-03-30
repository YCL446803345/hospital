<template>
    <div>
        <!-- 面包xie导航 -->
        <el-breadcrumb separator="/">
            <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item><a href="#">出院申请信息管理</a></el-breadcrumb-item>
            <el-breadcrumb-item>出院信息管理列表</el-breadcrumb-item>
        </el-breadcrumb>
        <el-row style="margin-top:10px;margin-bottom:10px">

            <el-col :span="2">
                <el-select v-model="leaveStatus" placeholder="出院状态">
                    <!-- <el-option label="出院状态" value="" ></el-option> -->
                    <el-option label="待审核" value="2" ></el-option>
                    <el-option label="已审核" value="4" ></el-option>
                    <el-option label="已出院" value="3" ></el-option>
                    <el-option label="已撤销" value="1" ></el-option>
                </el-select>
            </el-col>

            <el-col :span="1" style="margin-left:2px;">
                <el-button type="success" @click="search">查询</el-button>
               
            </el-col>

            <el-col :span="1" style="margin-left:2px;">
                 <el-button type="warning" @click="leaveStatus='',paheNum=1,pageSize=5">清空</el-button>
            </el-col>
            
        </el-row>
        <!-- 
            el-table数据表格
            :data 动态绑定 data中对象数组
            scope.row 表示对象数组的当前行对象
         -->
        <el-table
            :data="leaveHospitalList"
            style="width: 100%">

            <el-table-column
                type="index"
                :index='getIndex'
                label="序号"
                width="50">
            </el-table-column>

            <el-table-column
                prop="patientName"
                label="姓名"
                width="80">
            </el-table-column>

            <el-table-column
                prop="leaveNo"
                label="病人编号"
                width="150">
            </el-table-column>

            
            <el-table-column
                prop="doctorName"
                label="主治医生"
                width="80">
            </el-table-column>

            <el-table-column
                prop="leaveGender"
                label="性别"
                width="80">
                <template slot-scope="scope">
                <span v-if="scope.row.leaveGender=='2'">
                  <el-tag type="danger">女</el-tag>
                  </span>
                <span v-if="scope.row.leaveGender=='1'">
                  <el-tag>男</el-tag>
                  </span>
                </template>

            </el-table-column>

            <el-table-column
                prop="leaveAge"
                label="年龄"
                width="80">
            </el-table-column>

            <el-table-column
                prop="leavePhone"
                label="联系方式"
                width="120">
            </el-table-column>

            <el-table-column
                prop="cardId"
                label="身份证号"
                width="180">
            </el-table-column>

            <el-table-column
                prop="leaveBalance"
                label="余额"
                width="120">
            </el-table-column>



            <el-table-column
                prop="leaveDescription"
                label="描述"
                width="180">
            </el-table-column>

            <el-table-column
                prop="leaveTime"
                label="日期"
                width="180">
            </el-table-column>

            <el-table-column
                prop="leaveStatus"
                label="出院状态"
                width="80">
                <template slot-scope="scope">
                <span v-if="scope.row.leaveStatus=='1'">
                    <el-tag type="info">已撤销</el-tag>
                    </span>
                <span v-if="scope.row.leaveStatus=='2'">
                    <el-tag type="danger">待审核</el-tag>
                    </span>
                <span v-if="scope.row.leaveStatus=='4'">
                    <el-tag type="success">已审核</el-tag>
                    </span>
                <span v-if="scope.row.leaveStatus=='3'">
                    <el-tag>已出院</el-tag>
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
                  <!-- <el-button size="mini" type="primary" @click="gotoUpdateConsultationApplication(scope.row.id)">编辑</el-button> -->
                  <el-button v-if='scope.row.leaveStatus==2'
                  size="mini" type="danger" @click="gotoCancelLeaveHospital( scope.row.id)">撤 销</el-button>
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

import qs from 'qs'

export default {
   data() {
      return {
        leaveStatus:'',
        leaveHospitalList: [],
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
       //撤销出院
    gotoCancelLeaveHospital(id){
           this.$confirm('确定要撤销吗?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
            }).then(() => {
                
                this.$axios.post("/api/doctor/gotoCancelLeaveHospitalById",qs.stringify({'id':id}),{
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
                            message: '撤销成功!',
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




      //查询出院列表
        search(){
            
            console.log("---")
            this.$axios.get("/api/doctor/getLeaveHospitalList",{params:{
            leaveStatus:this.leaveStatus,
            pageNum:this.pageNum,
            pageSize:this.pageSize
            }})
            .then(res=>{
                 console.log("=========================");
               console.log(res.data.data.list);
                this.leaveHospitalList=res.data.data.list;
                this.total=res.data.data.total;
                this.pageNum=res.data.data.pageNum;
                this.pageSize=res.data.data.pageSize;

            })
        },
        changeSize(value){
            this.pageSize=value;
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
