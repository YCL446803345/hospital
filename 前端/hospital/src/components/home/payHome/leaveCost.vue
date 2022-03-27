<template>
    <div>
        <!-- 面包xie导航 -->
        <el-breadcrumb separator="/">
            <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item><a href="#/patientInfo">在院病人费用信息管理</a></el-breadcrumb-item>
            <el-breadcrumb-item>在院病人费用信息查询列表</el-breadcrumb-item>
        </el-breadcrumb>
        <el-row style="margin-top:10px;margin-bottom:10px">
            <el-col :span="3">
                <el-input v-model="name" placeholder="姓名">
                </el-input>
            </el-col>
            
            <el-col :span="5">
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

            <el-col :span="1.5" style="margin-left:2px;">
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
                width="160">
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
                width="120">
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
                prop="doctorName"
                label="主治医生"
                width="80">
            </el-table-column>
            
            <el-table-column label="操作">
               <template slot-scope="scope">
                  <el-button
                  size="mini"
                  type="primary"
                  @click="findShow(scope.row)">查询病人费用详情</el-button>

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


        <!-- 缴费详情模态框 -->
        <el-dialog :visible.sync="costViewForm" >
        <h1 align="center">缴费详情 </h1><br>

         <template>
        <el-descriptions class="margin-top" title="" :column="2"  border>
            <el-descriptions-item>
            <template slot="label">
                <i class="el-icon-user"></i>
                病人编号
            </template>
            {{patient.no}}
            </el-descriptions-item>
            <el-descriptions-item>
            <template slot="label">
                <i class="el-icon-tickets"></i>
                姓名
            </template>
            {{patient.name}}
            </el-descriptions-item>

        </el-descriptions>
        </template>

        <template>
        <el-descriptions class="margin-top" title="" :column="1"  border>

            <el-descriptions-item>
            <template slot="label">
                <i class="el-icon-tickets"></i>
                费用记录
            </template>
             <el-table
            :data="paymentRecordList"
            style="width: 100%">
                <el-table-column
                    prop="type"
                    label="类型"
                    width="180">
                     <template slot-scope="scope">
                        <span v-if="scope.row.type == '1'">现金支付</span>
                        <span v-if="scope.row.type == '2'">处方支付</span>
                        <span v-if="scope.row.type == '3'">医嘱支付</span>
                        <span v-if="scope.row.type == '4'">退药支付</span>
                        <span v-if="scope.row.type == '5'">住院支付</span>
                        <span v-if="scope.row.type == '6'">支付宝支付</span>
                    </template>
                </el-table-column>
                <el-table-column
                    prop="updatemoney"
                    label="费用"
                    width="180">
                </el-table-column>


                <el-table-column
                    prop="time"
                    label="时间"
                    width="180">
                </el-table-column>
             </el-table>
            </el-descriptions-item>
        
        </el-descriptions>

        <el-pagination
            background
            layout="prev, pager,next, sizes,->,total"
            :total="Intotal"
            :current-page='num'
            :page-sizes=[5,10,15,20]
            :page-size='size'
            @size-change='changeInSize'
            @current-change='changeInPage'
           >
        </el-pagination>
        </template>

        <el-divider></el-divider>
            <div slot="footer" class="dialog-footer">
                <el-button @click="closeCostInfoForm">确定</el-button>
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
        costViewForm:false,
        paymentRecordList:[],
        patient:{
            name:"",
            no:"",
        },
        num:1,
        size:5,
          patientId:0,
          Intotal:0,
      }
   },
   created(){
      this.search();
      this.headers={tokenStr:window.localStorage.getItem('tokenStr')};
   },
   methods:{
      //查询病人信息列表
        search(){
            this.$axios.get("/api/queryPatientsOut",{params:{name:this.name,no:this.no,gender:this.gender,
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
            this.$axios.get("/api/queryPatientsOut",{params:{name:this.searchName,no:this.no,gender:this.gender,
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
         findShow(patient){
            this.$axios.get("/api/queryPayment",{params:{id:patient.id}}).then(res=>{
                console.log(res.data);
                this.patient.name = patient.name;
                this.patient.no = patient.no;
                this.paymentRecordList = res.data;
                this.costViewForm = true;
            })
         },
         closeCostInfoForm(){
             this.costViewForm = false;
         },
          changeInPage(value){
            this.num=value;
            this.$axios.get("/api/queryPayment",{params:{
                id:this.patientId,
                pageNum:this.num,pageSize:this.size
                  }})
            .then(res=>{
                this.paymentRecordList = res.data.list;
                this.Intotal = res.data.total;
                this.num = res.data.pageNum;
                this.size = res.data.pageSize;
            })
        },
        changeInSize(value){
            this.size=value;
            this.num=1;
            this.$axios.get("/api/queryPayment",{params:{
                id:this.patientId,
                pageNum:this.num,pageSize:this.size
                  }})
            .then(res=>{
                this.paymentRecordList = res.data.list;
                this.Intotal = res.data.total;
                this.num = res.data.pageNum;
                this.size = res.data.pageSize;
            })
        },

   }
}
</script>

<style scoped>

</style>
