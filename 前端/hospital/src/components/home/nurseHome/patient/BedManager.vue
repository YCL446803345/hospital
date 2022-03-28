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
            
            <el-col :span="3" v-if='this.nurseRoleId==9'>
                <el-select v-model="deptId" placeholder="科室" v-if='this.nurseRoleId==9'>
                    <el-option v-for="dept in deptList" :key="dept.id" :label="dept.name" :value="dept.id"  ></el-option>
                </el-select>
            </el-col>
           
            <el-col :span="1.5" style="margin-left:2px;">
                <el-button type="success" @click="search">查询</el-button>
               
            </el-col>

            <el-col :span="1.5" style="margin-left:2px;">
                 <el-button type="warning" @click="code='',deptId='',paheNum=1,pageSize=5">清空</el-button>
                
            </el-col>

             <el-col :span="1.5" style="margin-left:2px;">
                 <el-button type="danger" @click="gotoAddBed">添加</el-button>
                
            </el-col>

            <el-col :span="1" style="margin-left:2px;">
                 <el-button type="success" @click="gotoBedView">床位概况</el-button>
                
            </el-col>
            
        </el-row>

        <el-row>
        <el-col :span="4" v-for="bed in bedList" :key="bed.id"  >
            <el-card :body-style="{ padding: '0px' }"  >
            <img src="../../../../css/image/医院.jpg" class="image">
            <div style="padding: 14px;">
                <span>
                     <el-tag type="primary" disable-transitions>
                        {{bed.code}}
                     </el-tag>

                    <el-tag type="primary" disable-transitions>
                        {{bed.deptName}}
                    </el-tag>

                    <el-tag :type="bed.status==1?'warning':'danger'" disable-transitions>
                        {{bed.status==1?'未使用':'使用中'}}
                    </el-tag>
                    <el-button type="success" class="button" 
                    @click="gotoBedInfo(bed.id,bed.deptName,bed.patientName,bed.code,bed.patientNo,bed.status,bed.patientId)">详情</el-button>
                </span>

                
                <div class="bottom clearfix">
                <time class="time">{{ currentDate }}</time>
                
                <el-button v-if='bed.status==2' type="danger" class="button" 
                @click="stopUseBed(bed.id,bed.patientId)">停止床位</el-button>

                <el-button v-if='bed.status==1' type="info" class="button" disabled size='medium'
                @click="stopUseBed(bed.id,bed.patientId)">停止床位</el-button>
                </div>
            </div>
            </el-card>
        </el-col>
        </el-row>
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
            {{bed.patientName==''?'---':bed.patientName}}
            </el-descriptions-item>
            <el-descriptions-item>
            <template slot="label" >
                <i class="el-icon-tickets"></i>
                病人编号
            </template>
           {{bed.patientNo==''?'---':bed.patientNo}}
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


        <!-- 添加床位 -->
        <el-dialog :visible.sync="addBedForm"  >
        <h1 align="center">添加床位 </h1><br>
        <el-form :model="addBed1">
            <el-form-item label="护理等级" :label-width="formLabelWidth">
                <el-select v-model="addBed1.deptId" placeholder="科室" >
                    <el-option v-for="dept in deptList" :key="dept.id" :label="dept.name" :value="dept.id"  ></el-option>
                </el-select>
            </el-form-item>
          
        </el-form>
           
            
            <div slot="footer" class="dialog-footer">
                <el-button type="danger" @click="addBed">添 加</el-button>
                <el-button @click="closeaddBedForm">取 消</el-button>
            </div>
        </el-dialog>


         <!-- 床位概况 -->
        <!-- <el-dialog :visible.sync="bedViewForm1"  >
        <h1 align="center">床位概况 </h1><br>
       
       <div id='chartbox'></div>
            
            <div slot="footer" class="dialog-footer">
                <el-button type="danger" @click="closeBedForm">确定</el-button>
            </div>
        </el-dialog> -->

    </div>
</template>

<script>
import echarts from 'echarts'

export default {
   data() {
      return {
        bedViewForm1:false,
        addBed1:{
            deptId:''
        },
        addBedForm:false,
        currentDate: new Date(),
        nurseRoleId:'',
        code:'',
        deptId:'',
        bedList: [],
        // total:100,
        // pageNum:1,
        // pageSize:5,
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
    var a = window.localStorage.getItem("roleId")
    this.nurseRoleId=a;
      
      if(a=='4'||a=='2'){
        this.deptId = parseInt(window.localStorage.getItem("deptId"))
      }else if(a=='9'){
        this.deptId=''
      }
      this.search();
        this.$axios.get("/api/findDepts")
            .then(res=>{
               console.log(res.data);
                this.deptList=res.data;
            })
      this.headers={tokenStr:window.localStorage.getItem('tokenStr')};
   },
   methods:{
       closeBedForm(){
           this.bedViewForm1=false
       },
       gotoBedView(){
           var option={
               title:{
                   text:'echarts 数据统计'
               },
               tooltip:{},
               legend:{
                   data:["A类","B类"]
               },
               xAxis:{
                    data:["衣服","裤子","袜子","鞋子"]
               },
               yAxis:{},
               series:[{
                   name:'销量',
                   type:'bar',
                   data:[500,200,360,100]
               }]
           };
           var myChart=echarts.init(document.getElementById('chartbox'))
           myChart.setOption(option)
           window.addEventListener('resize',function(){
               myChart.resize();
           })
        //    this.bedViewForm1=true
       },
       addBed(){
           this.$confirm('确定要在该科室添加床位吗?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                // type: 'warning'
            }).then(() => {
                 this.$axios.get("/api/addBed",{params:{deptId:this.addBed1.deptId}})
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
                         message: "添加成功!",
                         duration:2000
                     });
                    this.search();
                }
            })

            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消添加',
                     duration:2000
                });          
            });
              
       },
       closeaddBedForm(){
            this.addBedForm=false;
            this.deptId1='';
       },
       gotoAddBed(){
           if(this.deptId==''){
                this.addBedForm=true;
           }else{
               this.$confirm('确定要在本科室添加床位吗?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                // type: 'warning'
            }).then(() => {
                 this.$axios.get("/api/addBed",{params:{deptId:this.deptId}})
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
                         message: "添加成功!",
                         duration:2000
                     });
                    this.search();
                }
            })

            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消添加',
                     duration:2000
                });          
            });
              
           }

       },
       stopUseBed(bedId,id,deptId){
           var patient={id:id,bedId:bedId,deptId:deptId}
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
            this.bedViewForm=false;

       },
       gotoBedInfo(id,deptName,patientName,code,no,status,patientId){
           if(patientName==null){
                this.bed={
                    id:id,
                    code:code,
                    deptName:deptName,
                    patientName:'',
                    patientNo:'',
                    status:status,
                    patientId:''
                }
           }else{
               this.bed={
                    id:id,
                    code:code,
                    deptName:deptName,
                    patientName:patientName,
                    patientNo:no,
                    status:status,
                    patientId:patientId
                }
           }
            

            console.log(this.bed)
            this.bedViewForm=true;
       },

      //查询病人信息列表
        search(){
            
            this.$axios.get("/api/findBeds",{params:{code:this.code,deptId:this.deptId,pageNum:1}})
            .then(res=>{
               console.log(res.data);
                this.bedList=res.data;
            })
        },
        // changeSize(value){
        //     this.pageSize=value;
        //     this.pageNum=1;
        //     this.search();
        // },
        // changePage(value){
        //     this.pageNum=value;
        //     this.$axios.get("/api/findBeds",{params:{code:this.code,deptId:this.deptId,pageNum:this.pageNum,pageSize:this.pageSize}})
        //     .then(res=>{
        //         this.bedList=res.data.list;
        //         this.total=res.data.total;
        //         this.pageNum=res.data.pageNum;
        //         this.pageSize=res.data.pageSize;
        //     })
        // },
        //   getIndex(i){
        //     return (i+1)+this.pageSize*(this.pageNum-1);
        //  },
   }
}
</script>

<style scoped>

.time {
    font-size: 13px;
    color: #999;
  }
  
  .bottom {
    margin-top: 13px;
    line-height: 12px;
  }

  .button {
    padding: 0;
    float: right;
  }

  .image {
    width: 100%;
    display: block;
  }

  .clearfix:before,
  .clearfix:after {
      display: table;
      content: "";
  }
  
  .clearfix:after {
      clear: both
  }

</style>
