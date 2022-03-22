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
                <el-input v-model="code" placeholder="编号">
                </el-input>
            </el-col>
            
            <el-col :span="3">
                <el-select v-model="deptId" placeholder="科室">
                    <el-option v-for="dept in deptList" :key="dept.id" :label="dept.name" :value="dept.id"  ></el-option>
                </el-select>
            </el-col>
           
            <el-col :span="1" style="margin-left:2px;">
                <el-button type="success" @click="search">查询</el-button>
               
            </el-col>

            <el-col :span="1" style="margin-left:2px;">
                 <el-button type="warning" @click="code='',deptId='',paheNum=1,pageSize=5">清空</el-button>
                
            </el-col>
            
        </el-row>
        <!-- 
            el-table数据表格
            :data 动态绑定 data中对象数组
            scope.row 表示对象数组的当前行对象
         -->
        <el-table
            :data="bedList"
            style="width: 100%">

            <el-table-column
                type="index"
                :index='getIndex'
                label="序号"
                width="50">
            </el-table-column>

             <el-table-column
                prop="code"
                label="床位编号"
                width="80">
            </el-table-column>

            <el-table-column
                prop="deptName"
                label="科室名"
                width="180">
            </el-table-column>

            <el-table-column
                label="状态"
                width="180">
                 <template slot-scope="scope">
                     <el-tag :type="scope.row.status==='1'?'primary':scope.row.status==='2'?'danger':'warning'" disable-transitions>
                         {{scope.row.status==='1'?'可使用':scope.row.status==='2'?'已被用':'已预约'}}
                     </el-tag>
                </template>
            </el-table-column>

            <el-table-column label="操作">
               <template slot-scope="scope">
                  <el-button
                  size="mini"
                  type="primary"
                  @click="gotoBedInfo(scope.row.id,scope.row.deptName,scope.row.patientName,scope.row.code,scope.row.patientNo,scope.row.status,scope.row.patientId)">详细</el-button>
                  <el-button
                  size="mini"
                  type="danger"
                  @click="stopUseBed(scope.row.id,scope.row.patientId)">停止使用</el-button>
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
        <el-dialog :visible.sync="bedViewForm" >
        <h1 align="center">床位详细 </h1><br>
        <template>
        <el-descriptions class="margin-top" title="" :column="3"  border>
            <el-descriptions-item>
            <template slot="label">
                <i class="el-icon-user"></i>
                床位编号
            </template>
            {{bed.code}}
            </el-descriptions-item>
            
            <el-descriptions-item>
            <template slot="label">
                <i class="el-icon-tickets"></i>
                科室
            </template>
            {{bed.deptName}}
            </el-descriptions-item>
            
            <el-descriptions-item>
            <template slot="label">
                <i class="el-icon-tickets"></i>
                使用病人
            </template>
            {{bed.patientName}}
            </el-descriptions-item>
            <el-descriptions-item>
            <template slot="label" >
                <i class="el-icon-tickets"></i>
                病人编号
            </template>
           {{bed.patientNo}}
            </el-descriptions-item>
            <el-descriptions-item>
            <template slot="label">
                <i class="el-icon-tickets"></i>
                床位状态
            </template>
            <el-tag :type="bed.status==='1'?'primary':bed.status==='2'?'danger':'warning'" disable-transitions>
                         {{bed.status==='1'?'可使用':bed.status==='2'?'已被用':'已预约'}}
                     </el-tag>
            </el-descriptions-item>
        </el-descriptions>
        </template>

        <el-divider></el-divider>
            <div slot="footer" class="dialog-footer">
                <el-button @click="closeBedInfoForm">确定</el-button>
            </div>
        </el-dialog>

    </div>
</template>

<script>
export default {
   data() {
      return {
        code:'',
        deptId:'',
        bedList: [],
        total:100,
        pageNum:1,
        pageSize:5,
        headers:{},
        deptList:[],
        formLabelWidth: '120px',
        bedViewForm:false,
        bed:{
            id:'',
            code:'',
            deptName:'',
            patientName:'',
            patientNo:'',
            status:'',
            patientId:''
        },
      }
   },
   created(){
      this.search();
        this.$axios.get("/api/findDepts")
            .then(res=>{
               console.log(res.data);
                this.deptList=res.data;
            })
      this.headers={tokenStr:window.localStorage.getItem('tokenStr')};
   },
   methods:{
       stopUseBed(bedId,id){
           var patient={id:id,deptId:bedId}
           this.$axios.post("/api/stopUseBed",patient)
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
                         message: "修改成功!",
                         duration:2000
                     });
                    this.search();
                }
            })

       },
       closeBedInfoForm(){
            this.bed={
                id:'',
                code:'',
                deptName:'',
                patientName:'',
                patientNo:'',
                status:'',
                patientId:''
            }
            this.bedViewForm=true;

       },
       gotoBedInfo(id,deptName,patientName,code,no,status,patientId){
             this.bed={
                id:id,
                code:code,
                deptName:deptName,
                patientName:patientName,
                patientNo:no,
                status:status,
                patientId:patientId
            }
            this.bedViewForm=true;
       },

      //查询病人信息列表
        search(){
            
            this.$axios.get("/api/findBeds",{params:{code:this.code,deptId:this.deptId,pageNum:1,pageSize:this.pageSize}})
            .then(res=>{
               console.log(res.data);
                this.bedList=res.data.list;
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
            this.$axios.get("/api/findBeds",{params:{code:this.code,deptId:this.deptId,pageNum:this.pageNum,pageSize:this.pageSize}})
            .then(res=>{
                this.bedList=res.data.list;
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
