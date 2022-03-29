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

            <el-col :span="3">
                <el-select v-model="status" placeholder="病人状态">
                    <el-option label="所有病人" value="4" ></el-option>
                    <el-option label="在院病人" value="1"></el-option>
                    <el-option label="出院病人" value="2" ></el-option>
                </el-select>
            </el-col>
           
            <el-col :span="1.5" style="margin-left:2px;">
                <el-button type="success" @click="search">查询</el-button>
            </el-col>

            <el-col :span="1" style="margin-left:2px;">
                 <el-button type="warning" @click="name='',no='',paheNum=1,pageSize=5,gender='',cardId='',status=''">清空</el-button>
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
                width="150">
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
                prop="bedCode"
                label="床位编号"
                width="80">
                 <template slot-scope="scope">
                     <el-tag :type="scope.row.bedCode===null?'danger':'primary'" disable-transitions>
                         {{scope.row.bedCode==null?'未分配':scope.row.bedCode}}
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
                label="主治医生"
                width="80">
                <template slot-scope="scope">
                     <el-tag :type="scope.row.doctorName==='未分配'?'danger':'primary'" disable-transitions>
                         {{scope.row.doctorName}}
                     </el-tag>
                </template>
            </el-table-column>

            <el-table-column
                label="状态"
                width="120">
                <template slot-scope="scope">
                     <el-tag :type="scope.row.status==3?'danger':'primary'" disable-transitions>
                         {{scope.row.status==3?'已出院':scope.row.status==='2'?'出院审核':scope.row.status==='4'?'出院审核通过':'住院'}}
                     </el-tag>
                </template>
            </el-table-column>

            <!-- <el-table-column
                prop="baseDesc"
                label="病情"
                width="20">
            </el-table-column> -->
            
            <el-table-column label="操作">
               <template slot-scope="scope">
                  <el-button
                  size="mini"
                  type="primary"
                  @click="gotoBillViewForm(scope.row.id,scope.row.name,scope.row.no,scope.row.cardId,scope.row.deptName)">费用查询</el-button>
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

        <!-- 账单-->
        <el-dialog :visible.sync=" billViewForm" >
        <h1 align="center"></h1><br>
        <template>
            <el-descriptions class="margin-top" title="" :column="1"  border>
            <el-descriptions-item>
                <template slot="label">
                    <i class="el-icon-tickets"></i>
                        表名
                </template>
                    病人账单表&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            </el-descriptions-item>

           
        </el-descriptions>

        <el-descriptions class="margin-top" title="" :column="2"  border>
            <el-descriptions-item>
            <template slot="label">
                <i class="el-icon-user"></i>
                姓名
            </template>
            {{patient.name}}
            </el-descriptions-item>
            
            <el-descriptions-item>
            <template slot="label">
                <i class="el-icon-tickets"></i>
                编号
            </template>
            {{patient.no}}
            </el-descriptions-item>
            
            <el-descriptions-item>
            <template slot="label">
                <i class="el-icon-tickets"></i>
                科室
            </template>
            {{patient.deptName}}
            </el-descriptions-item>
            
            <el-descriptions-item>
            <template slot="label" >
                <i class="el-icon-tickets"></i>
                身份证
            </template>
           {{patient.cardId}}
            </el-descriptions-item>
            
        </el-descriptions>



        <el-descriptions class="margin-top" title="" :column="1"  border>
            <el-descriptions-item>
                <template slot="label">
                    <i class="el-icon-shopping-cart-full"></i>
                        住院费用
                </template>
                         住院时间：{{hospitalizationBill.payDays}}天
                         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            </el-descriptions-item>

            <el-descriptions-item>
                <template slot="label">
                    <i class="el-icon-more-outline"></i>
                        小计
                </template>
                        <span style="color:red">{{hospitalizationBill.sumMoney}} </span>元
                        
            </el-descriptions-item>
        </el-descriptions>
        

        <el-descriptions class="margin-top" title="" :column="1"  border>
            <el-descriptions-item>
                <template slot="label">
                    <i class="el-icon-shopping-cart-full"></i>
                        处方账单
                </template>

                    <el-table
                    :data="drugList"
                    style="width: 100%">

                    <el-table-column
                        type="index"
                        :index='getIndex'
                        label="序号"
                        width="50">
                    </el-table-column>

                    <el-table-column
                        prop="name"
                        label="药名"
                        width="80">
                    </el-table-column>

                    <el-table-column
                        prop="drugType"
                        label="种类"
                        width="80">
                    </el-table-column>

                    <el-table-column
                        prop="salePrice"
                        label="单价"
                        width="80">
                    </el-table-column>

                    <el-table-column
                        prop="num"
                        label="数量"
                        width="80">
                    </el-table-column>

                    <el-table-column
                        prop="createTime"
                        label="创建时间"
                        width="80">
                    </el-table-column>

                    <el-table-column
                        prop="money"
                        label="小计"
                        width="80">
                    </el-table-column>
                </el-table>
            </el-descriptions-item>

            <el-descriptions-item>
                <template slot="label">
                    <i class="el-icon-more-outline"></i>
                        小计
                </template>
                        <span style="color:red">{{getPrescriptionBillMoney}}</span>元
            </el-descriptions-item>
        </el-descriptions>

        <el-descriptions class="margin-top" title="" :column="1"  border>
            <el-descriptions-item>
                <template slot="label">
                    <i class="el-icon-shopping-cart-full"></i>
                        医嘱账单
                    </template>
                    <el-table
                        :data="MedicalAdviceList"
                        style="width: 100%">

                    <el-table-column
                        type="index"
                        :index='getIndex'
                        label="序号"
                        width="100">
                    </el-table-column>

                    <el-table-column
                        prop="name"
                        label="项目名"
                        width="120">
                    </el-table-column>

                    <el-table-column
                        prop="createTime"
                        label="创建时间"
                        width="190">
                    </el-table-column>

                    <el-table-column
                        prop="price"
                        label="价格"
                        width="120">
                    </el-table-column>

                </el-table>
            </el-descriptions-item>

            <el-descriptions-item>
                <template slot="label">
                    <i class="el-icon-more-outline"></i>
                        小计
                </template>
                       <span style="color:red">{{getMedicalAdviceBillMoney}}</span>元
            </el-descriptions-item>
        </el-descriptions>

        <el-descriptions class="margin-top" title="" :column="1"  border>
            <el-descriptions-item>
                <template slot="label">
                    <i class="el-icon-shopping-cart-full"></i>
                        退药账单
                </template>
                        <el-table
                    :data="drugOutList"
                    style="width: 100%">

                    <el-table-column
                        type="index"
                        :index='getIndex'
                        label="序号"
                        width="50">
                    </el-table-column>

                    <el-table-column
                        prop="name"
                        label="药名"
                        width="80">
                    </el-table-column>

                    <el-table-column
                        prop="drugType"
                        label="种类"
                        width="80">
                    </el-table-column>

                    <el-table-column
                        prop="salePrice"
                        label="单价"
                        width="80">
                    </el-table-column>

                    <el-table-column
                        prop="num"
                        label="数量"
                        width="80">
                    </el-table-column>

                     <el-table-column
                        prop="createTime"
                        label="创建时间"
                        width="80">
                    </el-table-column>

                    <el-table-column
                        prop="money"
                        label="小计"
                        width="80">
                    </el-table-column>
                </el-table>
            </el-descriptions-item>

            <el-descriptions-item>
                <template slot="label">
                    <i class="el-icon-more-outline"></i>
                        小计
                </template>
                         <span style="color:green">{{-getDrugOutBillMoney}}</span>元
            </el-descriptions-item>
        </el-descriptions>

        <el-descriptions class="margin-top" title="" :column="3"  border>
             <el-descriptions-item>
                <template slot="label">
                    <i class="el-icon-more"></i>
                        住院账单小计
                </template>
                        <span style="color:red">{{hospitalizationBill.sumMoney}}</span>元
            </el-descriptions-item>

            <el-descriptions-item>
                <template slot="label">
                    <i class="el-icon-more"></i>
                        处方账单小计
                </template>
                        <span style="color:red">{{getPrescriptionBillMoney}}</span>元
            </el-descriptions-item>

            <el-descriptions-item>
                <template slot="label">
                    <i class="el-icon-more"></i>
                        医嘱账单小计
                </template>
                        <span style="color:red">{{getMedicalAdviceBillMoney}}</span>元
            </el-descriptions-item>
            
            <el-descriptions-item>
                <template slot="label">
                    <i class="el-icon-more"></i>
                        退款
                </template>
                        <span style="color:green">-{{getDrugOutBillMoney}}</span>元
            </el-descriptions-item>
            
            <el-descriptions-item>
                <template slot="label">
                    <i class="el-icon-more"></i>
                        总费用
                </template>
                        <span style="color:red">{{hospitalizationBill.sumMoney+getPrescriptionBillMoney+getMedicalAdviceBillMoney+getDrugOutBillMoney}}</span>元
            </el-descriptions-item>

        </el-descriptions>

        </template>

        <div slot="footer" class="dialog-footer">
            <el-button @click="closeBillView">确定</el-button>
        </div>
        </el-dialog>
            

    </div>
</template>

<script>
export default {
   data() {
      return {
        nurseDeptId:'',
        no:'',
        name:'',
        gender:'',
        cardId:'',
        status:"4",
        patientList: [],
        total:100,
        pageNum:1,
        pageSize:5,
        hospitalizationBill:{
            payDays:0,
            sumMoney:0
        },
        drugList:[],
        patient:{
            id:'',
            name:'',
            no:'',
            cardId:'',
            deptName:''
        },
        formLabelWidth: '120px',
        billViewForm:false,
        getPrescriptionBillMoney:0,
        drugOutList:[],
        getDrugOutBillMoney:0,
        MedicalAdviceList:[],
        getMedicalAdviceBillMoney:0
      }
   },
   created(){
        var a = window.localStorage.getItem("roleId")
      
      if(a=='4'||a=='2'){
        this.nurseDeptId = parseInt(window.localStorage.getItem("deptId"))
      }else if(a=='9'){
        this.nurseDeptId=''
        
      }
      this.search();
   },
   methods:{
       closeBillView(){
             this.patient={
                id:'',
                name:'',
                no:'',
                cardId:'',
                deptName:''
            }

            this.hospitalizationBill={
                 payDays:0,
                 sumMoney:0
            };
            this.drugList=[];
            this.getPrescriptionBillMoney=0;
            this.drugOutList=[],
            this.getDrugOutBillMoney=0
            this.MedicalAdviceList=[],
            this.getMedicalAdviceBillMoney=0

            this.billViewForm=false;
       },
       gotoBillViewForm(id,name,no,cardId,deptName){
            this.patient={
                id:id,
                name:name,
                no:no,
                cardId:cardId,
                deptName:deptName
            }
            var patient=this.patient;
            
            this.$axios.post("/api/getHospitalizationBill",patient)
            .then(res=>{
                
                if(res.data.sumMoney!=null){
                    this.hospitalizationBill=res.data;
                }
                this.$axios.post("/api/getPrescriptionBill",patient)
                .then(res=>{
                    this.drugList=res.data;
                    var getPrescriptionBillMoney=0;
                    this.drugList.forEach(drug=>{
                        getPrescriptionBillMoney+=drug.money;
                    })

                    this.getPrescriptionBillMoney=getPrescriptionBillMoney;

                    this.$axios.post("/api/getMedicalAdviceBill",patient)
                    .then(res=>{
                        this.MedicalAdviceList=res.data;
                        var getMedicalAdviceBillMoney=0;
                        this.MedicalAdviceList.forEach(project=>{
                            getMedicalAdviceBillMoney+=project.price;
                        })

                        this.getMedicalAdviceBillMoney=getMedicalAdviceBillMoney;

                        this.$axios.post("/api/getDrugOutBill",patient)
                        .then(res=>{
                            this.drugOutList=res.data;

                            var getDrugOutBillMoney=0;
                            this.drugOutList.forEach(drug=>{
                                getDrugOutBillMoney+=drug.money;
                            })

                            this.getDrugOutBillMoney=getDrugOutBillMoney;

                            this.billViewForm=true;
                        })
                        
                    })
                })

            })
            
       },
      //查询病人信息列表
        search(){
            this.$axios.get("/api/findPatientsByChangeDept",{params:{name:this.name,no:this.no,gender:this.gender,
                  cardId:this.cardId,deptId:this.nurseDeptId,deptId:this.nurseDeptId,status:this.status,pageNum:1,pageSize:this.pageSize}})
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
