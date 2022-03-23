<template>
    <div>
        <!-- 面包xie导航 -->
        <el-breadcrumb separator="/">
            <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item><a href="#/patientList">会诊信息管理</a></el-breadcrumb-item>
            <el-breadcrumb-item>会诊信息管理列表</el-breadcrumb-item>
        </el-breadcrumb>
        <el-row style="margin-top:10px;margin-bottom:10px">

            <el-col :span="2">
                <el-select v-model="consultationEmergencyId" placeholder="会诊情况">
                    <el-option label="会诊情况" value="" ></el-option>
                    <el-option label="紧急" value="1" ></el-option>
                    <el-option label="24小时" value="2" ></el-option>
                    <el-option label="一般" value="3" ></el-option>
                </el-select>
            </el-col>
            <el-col :span="2">
                <el-select v-model="consultationCategoryId" placeholder="会诊类别">
                    <el-option label="会诊类别" value="" ></el-option>
                    <el-option label="它科会诊" value="1" ></el-option>
                    <el-option label="科内会诊" value="2" ></el-option>
                    <el-option label="院外会诊" value="3" ></el-option>
                </el-select>
            </el-col>
            <el-col :span="2">
                <el-select v-model="status" placeholder="会诊状态">
                    <el-option label="会诊状态" value="" ></el-option>
                    <el-option label="未执行" value="1" ></el-option>
                    <el-option label="已执行" value="2" ></el-option>
                    <el-option label="已撤销" value="3" ></el-option>
                </el-select>
            </el-col>

           
            <el-col :span="1" style="margin-left:2px;">
                <el-button type="success" @click="search">查询</el-button>
               
            </el-col>

            <el-col :span="1" style="margin-left:2px;">
                 <el-button type="warning" @click="consultationEmergencyId='',consultationCategoryId='',status='',paheNum=1,pageSize=5">清空</el-button>
            </el-col>
            
        </el-row>
        <!-- 
            el-table数据表格
            :data 动态绑定 data中对象数组
            scope.row 表示对象数组的当前行对象
         -->
        <el-table
            :data="consultationApplicationList"
            style="width: 100%">

            <el-table-column
                type="index"
                :index='getIndex'
                label="序号"
                width="50">
            </el-table-column>

             <el-table-column
                prop="patientName"
                label="病人"
                width="80">
            </el-table-column>

            <el-table-column
                prop="doctorName"
                label="主治医生"
                width="80">
            </el-table-column>

            <el-table-column
                prop="reason"
                label="原因"
                width="180">
            </el-table-column>

            <el-table-column
                prop="desc"
                label="描述"
                width="180">
            </el-table-column>

            <el-table-column
                prop="consultationEmergencyName"
                label="紧急度"
                width="180">
            </el-table-column>

            <el-table-column
                prop="consultationCategoryName"
                label="类别"
                width="180">
            </el-table-column>

            <el-table-column
                prop="consultationDate"
                label="会诊时间"
                width="180">
            </el-table-column>

            <el-table-column
                prop="status"
                label="会诊状态"
                width="80">
                <template slot-scope="scope">
                         {{scope.row.status==='1'?'未会诊':'已会诊'}}
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
                  @click="gotoUpdateConsultationApplication(scope.row.id)">编辑会诊</el-button>
                  <el-button
                  size="mini"
                  type="danger"
                  @click="deleteConsultationApplication( scope.row.id)">删除会诊</el-button>
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
        consultationEmergencyId:'',
        consultationCategoryId:'',
        status:"",
        consultationApplicationList: [],
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
      //查询会诊列表
        search(){
            
            console.log("---")
            this.$axios.get("/api/doctor/getConsultationApplication",{params:{
            consultationEmergencyId:this.consultationEmergencyId,
            consultationCategoryId:this.consultationCategoryId,
            status:this.status,
            pageNum:this.pageNum,
            pageSize:this.pageSize
            }})
            .then(res=>{
               console.log(res.data.data);
                this.consultationApplicationList=res.data.data.list;
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
            this.$axios.get("/api/doctor/getConsultationApplication",{params:{consultationEmergencyId:this.consultationEmergencyId,
            consultationCategoryId:this.consultationCategoryId,status:this.status,pageNum:this.pageNum,pageSize:this.pageSize}})
            .then(res=>{
                this.consultationApplicationList=res.data.data.list;
                this.total=res.data.data.total;
                this.pageNum=res.data.data.pageNum;
                this.pageSize=res.data.data.pageSize;
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
