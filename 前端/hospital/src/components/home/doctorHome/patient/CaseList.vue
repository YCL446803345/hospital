<template>
    <div>
        <!-- 面包xie导航 -->
        <el-breadcrumb separator="/">
            <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item><a href="#">病例中心信息管理</a></el-breadcrumb-item>
            <el-breadcrumb-item>病例中心信息管理列表</el-breadcrumb-item>
        </el-breadcrumb>
        <el-row style="margin-top:10px;margin-bottom:10px">

            <el-col :span="2">
                <el-input v-model="spare1" placeholder="病例"> </el-input>
            </el-col>

            <el-col :span="1" style="margin-left:2px;">
                <el-button type="success" @click="search">查询</el-button>
               
            </el-col>

            <el-col :span="1" style="margin-left:2px;">
                 <el-button type="warning" @click="spare1='',paheNum=1,pageSize=5">清空</el-button>
            </el-col>
            
        </el-row>
        <!-- 
            el-table数据表格
            :data 动态绑定 data中对象数组
            scope.row 表示对象数组的当前行对象
         -->
        <el-table
            :data="caseList"
            style="width: 100%">

            <el-table-column
                type="index"
                :index='getIndex'
                label="序号"
                width="50">
            </el-table-column>

             <el-table-column
                prop="spare1"
                label="症状"
                width="80">
            </el-table-column>

            <el-table-column
                prop="patientName"
                label="患者"
                width="80">
            </el-table-column>

            <el-table-column
                prop="no"
                label="患者编号"
                width="140">
            </el-table-column>

            <el-table-column
                prop="gender"
                label="性别"
                width="80">
                <template slot-scope="scope">
                <span v-if="scope.row.gender=='2'">
                  <el-tag type="danger">女</el-tag>
                  </span>
                <span v-if="scope.row.gender=='1'">
                  <el-tag>男</el-tag>
                  </span>
                </template>
            </el-table-column>

            <el-table-column
                prop="phone"
                label="电话"
                width="140">
            </el-table-column>

            <el-table-column
                prop="doctorName"
                label="主治医生"
                width="80">
            </el-table-column>

            <el-table-column
                prop="deptName"
                label="科室"
                width="80">
            </el-table-column>

            <el-table-column prop="bedCode" label="床位编号" width="80">
                <template slot-scope="scope">
                <el-tag :type="scope.row.bedCode === '未分配' ? 'danger' : 'primary'" disable-transitions>
                    {{ scope.row.bedCode }}
                </el-tag>
                </template>
            </el-table-column>


            <el-table-column
                prop="caseDescription"
                label="症状描述"
                width="180">
            </el-table-column>

            <el-table-column
                prop="adviceDescription"
                label="治疗"
                width="120">
            </el-table-column>

            <el-table-column
                prop="createTime"
                label="时间"
                width="180">
            </el-table-column>

            
            
            <el-table-column label="操作">
               <template slot-scope="scope">
                  <!-- <el-button size="mini" type="primary" @click="gotoUpdateConsultationApplication(scope.row.id)">编辑</el-button> -->
                  <el-button size="mini" type="danger" @click="gotoDeleteCase(scope.row.id)">撤 销</el-button>
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
        spare1:'',
        caseList: [],
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
    gotoDeleteCase(id){
           this.$confirm('确定要撤销吗?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
            }).then(() => {
                
                this.$axios.post("/api/doctor/gotoDeleteCase",qs.stringify({'id':id}),{
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
            this.$axios.get("/api/doctor/getCaseList",{params:{
            spare1:this.spare1,
            pageNum:this.pageNum,
            pageSize:this.pageSize
            }})
            .then(res=>{
                 console.log("=========================");
               console.log(res.data.data.list);
                this.caseList=res.data.data.list;
                this.total=res.data.data.total;
                this.pageNum=res.data.data.pageNum;
                this.pageSize=res.data.data.pageSize;

            })
        },
        changeSize(value){
            this.pageSize=value;
            // this.pageNum=1;
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
