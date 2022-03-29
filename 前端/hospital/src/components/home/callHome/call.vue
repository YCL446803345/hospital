<template>
    <div>
        <!-- 面包xie导航 -->
        <br>
        <el-breadcrumb separator="/">
            <el-breadcrumb-item :to="{ path: '/gotohome' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item><a href="#/">叫号管理</a></el-breadcrumb-item>
        </el-breadcrumb>
        <el-row style="margin-top:10px;margin-bottom:10px">
            <el-col :span="8">
                <el-select  v-model="deptId" placeholder="科室">
                    <el-option
                        v-for="dept in deptList"
                        :key="dept.id"
                        :label="dept.name"
                        :value="dept.id">
                    </el-option>
                </el-select>
            </el-col>

            <el-col :span="8">
                <el-input v-model="appointmentTime" type="date" placeholder="预约时间">
                </el-input>
            </el-col>
           
            <el-col :span="1.5" style="margin-left:2px;">
                <el-button type="success" @click="search">查询</el-button>
            </el-col>

            <el-col :span="1.5" style="margin-left:2px;">
                 <el-button type="info" @click="deptId='',appointmentTime='',paheNum=1,pageSize=5">清空</el-button>
            </el-col>

            <el-col :span="1.5" style="margin-left:2px;">
                <el-button type="warning" @click="call">叫号</el-button>
            </el-col>
            
        </el-row>
        <!-- 
            el-table数据表格
            :data 动态绑定 data中对象数组
            scope.row 表示对象数组的当前行对象
         -->
         <div style="display:table-cell;height:100px;vertical-align:middle;text-align:center">
             <span class="center_text" style="font-weight:bold;font-size: xx-large;line-height: 100px;margin-left:383px">{{callMsg}}</span>
         </div>
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
                prop="patientName"
                label="姓名"
                width="80">
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
                prop="deptName"
                label="科室"
                width="80">
            </el-table-column>

            <el-table-column
                prop="appointmentTime"
                label="预约时间"
                width="200">
                <template slot-scope="scope">
                    <i class="el-icon-time"></i>
                    <span style="margin-left: 10px">{{ scope.row.appointmentTime}}</span>
                </template>
            </el-table-column>

             <el-table-column
                prop="status"
                label="状态"
                width="100">
                 <template slot-scope="scope">
                    <span style="margin-left: 10px">{{ scope.row.status == '1'?'等待叫号':'正在叫号'}}</span>
                </template>
            </el-table-column>
        </el-table>
       
        <el-pagination
            background
            layout="prev,pager,next,sizes,->,total"
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
        deptList:{},
        deptId:'',
        appointmentTime:"",
        status:1,
        patientList: [],
        total:100,
        pageNum:1,
        pageSize:5,
        callMsg:'',
      }
   },
   created(){
       this.findDetpList();
      this.search();
      
   },
   methods:{
      //查询病人信息列表
        search(){
            this.$axios.get("/api/findCall",{params:{deptId:this.deptId,appointmentTime:this.appointmentTime,pageNum:this.pageNum,pageSize:this.pageSize}})
            .then(res=>{
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
           this.search();
        },
          getIndex(i){
            return (i+1)+this.pageSize*(this.pageNum-1);
         },
         findDetpList(){
             this.$axios.get("/api/findDepts").then(res=>{
                 this.deptList = res.data
             })
         },
         call(){
            let name = this.patientList[0].patientName
            let dept = this.patientList[0].deptName
            let msg = '请'+name+'到'+dept+'就诊'
            this.callMsg = '现在正在叫号:'+msg

            //将文字转换为语音
            const synth = window.speechSynthesis;
            let msg1 = new SpeechSynthesisUtterance(msg);
            msg1.lang = 'zh-CN';
            msg1.rate = 1;
            msg1.pitch = 3;
            msg1.volume = 5;
            synth.speak(msg1);
            synth.speak(msg1);


            this.patientList[0].status = "2"
             window.setTimeout(()=>{
                this.callMsg = '';
                this.$axios.get("/api/updateCallStatus",{params:{id:this.patientList[0].id}}).then(res=>{
                this.search();
            })
            },5000)
         }
   }
}
</script>

<style scoped>

</style>
