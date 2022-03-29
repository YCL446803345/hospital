<template>
    <div>
        <!-- 面包xie导航 -->
        <br>
        <el-breadcrumb separator="/">
            <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item><a href="#/costInfo">在院病人费用信息管理</a></el-breadcrumb-item>
            <el-breadcrumb-item>在院病人信息费用管理列表</el-breadcrumb-item>
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
           
            <el-col :span="1.5" style="margin-left:2px;">
                <el-button type="success" @click="search">查询</el-button>
               
            </el-col>

            <el-col :span="1" style="margin-left:2px;">
                 <el-button type="warning" @click="name='',no='',paheNum=1,pageSize=5">清空</el-button>
            </el-col>
            
        </el-row>
        <!-- 
            el-table数据表格
            :data 动态绑定 data中对象数组
            scope.row 表示对象数组的当前行对象
         -->
        <el-table
            :data="costList"
            style="width: 100%">

             <el-table-column
                type="index"
                :index='getIndex'
                label="序号"
                width="50">
            </el-table-column>

            <el-table-column
                prop="patient.name"
                label="姓名"
                width="80">
            </el-table-column>

            <el-table-column
                prop="patient.no"
                label="病人编号"
                width="150">
            </el-table-column>

             
            <el-table-column
                prop="days"
                label="住院天数"
                width="80">
                <template slot-scope="scope">
                         {{scope.row.days===null?'0':scope.row.days}}
                </template>
            </el-table-column>

            <el-table-column
                prop="hospitalization"
                label="住院费用"
                width="80">
            </el-table-column>

             <el-table-column
                prop="prescription"
                label="药品费用"
                width="80">
            </el-table-column>

            <el-table-column
                prop="medicalAdvice"
                label="医嘱费用"
                width="80">
            </el-table-column>

            <el-table-column
                prop="drugOut"
                label="退药费用"
                width="80">
            </el-table-column>

            <el-table-column
                prop="balance"
                label="余额"
                width="100">
            </el-table-column>
            
            <el-table-column label="操作">
               <template slot-scope="scope">
                  <el-button
                  size="mini"
                  type="primary"
                  @click="gotoSettlement(scope.row.patient.id,scope.row.drugOut,scope.row.hospitalization,scope.row.medicalAdvice,scope.row.prescription)">结算</el-button>
                  <el-button
                  size="mini"
                  type="danger"
                  @click="showCost(scope.row.patient.id,scope.row.drugOut,scope.row.hospitalization,scope.row.medicalAdvice,scope.row.prescription)">详情</el-button>
                    <el-button
                  size="mini"
                  type="success"
                  @click="pay(scope.row.patient.id)">支付宝充值</el-button>
                    <el-button
                  size="mini"
                  type="warning"
                  @click="payCash(scope.row.patient.id)">现金充值</el-button>
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

        <!-- 费用详情 -->
        <el-dialog :visible.sync="costViewForm" >
        <h1 align="center">费用详情 </h1><br>

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
                退药详情
            </template>
             <el-table
            :data="patient.drugOutList"
            style="width: 100%">

                <el-table-column
                    prop="name"
                    label="药品名"
                    width="180">
                </el-table-column>
                <el-table-column
                    prop="salePrice"
                    label="药品价格"
                    width="180">
                </el-table-column>
                <el-table-column
                    prop="outNum"
                    label="退药数量"
                    width="180">
                </el-table-column>
             </el-table>
            </el-descriptions-item>

            <el-descriptions-item>
            <template slot="label">
                <i class="el-icon-tickets"></i>
                购药详情
            </template>
             <el-table
            :data="patient.drugInList"
            style="width: 100%">
                <el-table-column
                    prop="name"
                    label="药品名"
                    width="180">
                </el-table-column>
                <el-table-column
                    prop="salePrice"
                    label="药品价格"
                    width="180">
                </el-table-column>
                <el-table-column
                    prop="inNum"
                    label="购药数量"
                    width="180">
                </el-table-column>
             </el-table>
            </el-descriptions-item>


            
            <el-descriptions-item>
            <template slot="label">
                <i class="el-icon-tickets"></i>
                项目详情
            </template>
             <el-table
            :data="patient.projectList"
            style="width: 100%">
                <el-table-column
                    prop="name"
                    label="项目名"
                    width="180">
                </el-table-column>
                <el-table-column
                    prop="price"
                    label="项目价格"
                    width="180">
                </el-table-column>
             </el-table>
            </el-descriptions-item>

           

        </el-descriptions>
        </template>

         <template>
        <el-descriptions class="margin-top" title="" :column="3"  border>
         <el-descriptions-item>
            <template slot="label">
                <i class="el-icon-tickets"></i>
                住院天数
            </template>
            {{patient.days}}
            </el-descriptions-item>

            <el-descriptions-item>
            <template slot="label">
                <i class="el-icon-tickets"></i>
                余额
            </template>
            {{patient.balance}}
            </el-descriptions-item>

            <el-descriptions-item>
            <template slot="label">
                <i class="el-icon-tickets"></i>
                小结
            </template>
            {{patient.hospitalization + patient.prescription + patient.medicalAdvice - patient.drugOut}}
            </el-descriptions-item>
        </el-descriptions>
        </template>


        <el-divider></el-divider>
            <div slot="footer" class="dialog-footer">
                <el-button @click="closeCostInfoForm">确定</el-button>
            </div>
        </el-dialog>



               <!-- 费用结算 -->
        <el-dialog :visible.sync="costSettlementForm" >
        <h1 align="center">费用结算</h1><br>

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
                退药详情
            </template>
             <el-table
            :data="patient.drugOutList"
            style="width: 100%">

                <el-table-column
                    prop="name"
                    label="药品名"
                    width="180">
                </el-table-column>
                <el-table-column
                    prop="salePrice"
                    label="药品价格"
                    width="180">
                </el-table-column>
                <el-table-column
                    prop="outNum"
                    label="退药数量"
                    width="180">
                </el-table-column>
             </el-table>
            </el-descriptions-item>

            <el-descriptions-item>
            <template slot="label">
                <i class="el-icon-tickets"></i>
                购药详情
            </template>
             <el-table
            :data="patient.drugInList"
            style="width: 100%">
                <el-table-column
                    prop="name"
                    label="药品名"
                    width="180">
                </el-table-column>
                <el-table-column
                    prop="salePrice"
                    label="药品价格"
                    width="180">
                </el-table-column>
                <el-table-column
                    prop="inNum"
                    label="购药数量"
                    width="180">
                </el-table-column>
             </el-table>
            </el-descriptions-item>


            
            <el-descriptions-item>
            <template slot="label">
                <i class="el-icon-tickets"></i>
                项目详情
            </template>
             <el-table
            :data="patient.projectList"
            style="width: 100%">
                <el-table-column
                    prop="name"
                    label="项目名"
                    width="180">
                </el-table-column>
                <el-table-column
                    prop="price"
                    label="项目价格"
                    width="180">
                </el-table-column>
             </el-table>
            </el-descriptions-item>

           

        </el-descriptions>
        </template>

         <template>
        <el-descriptions class="margin-top" title="" :column="3"  border>
         <el-descriptions-item>
            <template slot="label">
                <i class="el-icon-tickets"></i>
                住院天数
            </template>
            {{patient.days}}
            </el-descriptions-item>

            <el-descriptions-item>
            <template slot="label">
                <i class="el-icon-tickets"></i>
                余额
            </template>
            {{patient.balance}}
            </el-descriptions-item>

            <el-descriptions-item>
            <template slot="label">
                <i class="el-icon-tickets"></i>
                小结
            </template>
            {{(patient.hospitalization + patient.prescription + patient.medicalAdvice - patient.drugOut).toFixed(2)}}
            </el-descriptions-item>
        </el-descriptions>
        </template>


        <el-divider></el-divider>
            <div slot="footer" class="dialog-footer">
                <el-button @click="closeSettlement">取消</el-button>
                <el-button @click="settlement">结算</el-button>
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
        status:1,
        costList: [],
        total:100,
        pageNum:1,
        pageSize:5,
        costViewForm:false,
        costSettlementForm:false,
        patient:{
            id:0,
            name:"",
            no:"",
            drugOutList:[],
            drugInList:[],
            projectList:[],
            days:0,
            drugOut:0,
            prescription:0,
            hospitalization:0,
            medicalAdvice:0,
            balance:0,
        },
        token:""
      }
   },
   created(){
      this.search();
    //   this.$axios.get("/api/check")
   },
   methods:{
        //支付宝支付
        pay(id){
             this.$prompt('请输入充值金额', '支付宝充值', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'success'
              }).then((value) => {
                    this.$axios.get("/api/gotoPay",{params:{patientId:id,newMoney:value.value}}).then(res=>{
                        const divForm = document.getElementsByTagName("div");
                        if (divForm.length) {
                            document.body.removeChild(divForm[0]);
                        }
                        const div = document.createElement("div");
                        div.innerHTML = res.data;
                        document.body.appendChild(div);
                        document.forms[0].submit();
                        document.forms[0].setAttribute("target", "_blank"); // 新开窗口跳转
                    })
              }).catch(() => {
                this.$message({
                  type: 'info',
                  message: '已取消缴费'
                }); 
              })
        },
        //现金支付
        payCash(id){
            this.$prompt('请输入充值金额', '现金充值', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'success'
              }).then((value) => {
                    this.$axios.get("/api/addMoney",{params:{id:id,money:value.value}}).then(res=>{
                        if(res.data === "OK"){
                             this.$message({
                                type: "success",
                                message: "支付成功!",
                            });
                            this.search();
                        }else{
                           this.$message({
                                type: "danger",
                                message: "支付失败!",
                            }); 
                        }
                    })
              }).catch(() => {
                this.$message({
                  type: 'info',
                  message: '已取消缴费'
                }); 
              })
        },
        //结算病人账单
        settlement(){
            this.$axios.get("/api/advancePayment",{params:{id:this.patient.id,token:this.token}}).then(res=>{
                 if(res.data === "OK"){
                            this.$message({
                            type: "success",
                            message: "结算成功!",
                        });
                    }else if(res.data == "err"){
                         this.$message({
                            type: "info",
                            message: "请不要重复点击!",
                        });
                    }else{
                         this.$alert('以欠费'+res.data+',请先充值', '欠费', {
                            confirmButtonText: '确定'
                         })
                    }
                    this.closeSettlement();
                    this.search();
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '系统异常'
                }); 
                this.closeSettlement();
            })
        },
        //打开结算病人账单模态框
        gotoSettlement(id,drugOut,hospitalization,medicalAdvice,prescription){
            this.$axios.get("/api/query",{params:{id:id}}).then(res=>{
                this.patient.id = id;
                this.patient.name = res.data.patient.name;
                this.patient.no = res.data.patient.no;
                this.patient.drugOutList = res.data.drugOutList;
                this.patient.drugInList = res.data.drugInList;
                this.patient.projectList = res.data.projectList;
                this.patient.days = res.data.days;
                this.patient.balance = res.data.balance;
                this.patient.hospitalization = drugOut
                this.patient.prescription = hospitalization
                this.patient.medicalAdvice = medicalAdvice
                this.patient.drugOut = prescription
                this.costSettlementForm = true;
                this.$axios.get("/api/createToken").then(res=>{
                    this.token = res.data;
                })
            })
        },
        //关闭结算病人账单模态框
        closeSettlement(){
                this.patient={
                    name:"",
                    no:"",
                    drugOutList:[],
                    drugInList:[],
                    projectList:[],
                    days:0,
                    drugOut:0,
                    prescription:0,
                    hospitalization:0,
                    medicalAdvice:0,
                    balance:0,
                }
            this.costSettlementForm = false;
        },
      //查询病人信息列表
        search(){
            this.$axios.get("/api/queryAllCost",{params:{name:this.name,no:this.no,
                                        pageNum:1,pageSize:this.pageSize}})
            .then(res=>{
               console.log(res.data);
               this.costList = res.data.list
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
             this.$axios.get("/api/queryAllCost",{params:{name:this.name,no:this.no,
                                        pageNum:this.pageNum,pageSize:this.pageSize}})
            .then(res=>{
                console.log(res.data);
               this.costList = res.data.list
                // this.pageNum=res.data.pageNum;
                // this.pageSize=res.data.pageSize;
                this.total = res.data.total;
            })
        },
        showCost(id,drugOut,hospitalization,medicalAdvice,prescription){
            this.$axios.get("/api/query",{params:{id:id}}).then(res=>{
                this.patient.name = res.data.patient.name;
                this.patient.no = res.data.patient.no;
                this.patient.drugOutList = res.data.drugOutList;
                this.patient.drugInList = res.data.drugInList;
                this.patient.projectList = res.data.projectList;
                this.patient.days = res.data.days;
                this.patient.balance = res.data.balance;
                this.patient.hospitalization = drugOut
                this.patient.prescription = hospitalization
                this.patient.medicalAdvice = medicalAdvice
                this.patient.drugOut = prescription
                this.costViewForm = true;
            })
        },
        getIndex(i){
            return (i+1)+this.pageSize*(this.pageNum-1);
        },
        closeCostInfoForm(){
            this.patient={
                    name:"",
                    no:"",
                    drugOutList:[],
                    drugInList:[],
                    projectList:[],
                    days:0,
                    drugOut:0,
                    prescription:0,
                    hospitalization:0,
                    medicalAdvice:0,
                    balance:0,
                }
            this.costViewForm = false;
        }
   }
}
</script>

<style scoped>

</style>