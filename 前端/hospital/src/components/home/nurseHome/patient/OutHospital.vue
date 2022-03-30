<template>
    <div>
        <!-- 面包xie导航 -->
        <el-breadcrumb separator="/">
            <el-breadcrumb-item :to="{ path: '/gotoHome' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item><a href="#/nurse/discharge">出院管理</a></el-breadcrumb-item>
            <el-breadcrumb-item>出院管理</el-breadcrumb-item>
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
                width="100">
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
                width="160">
            </el-table-column>

            <el-table-column
                prop="phone"
                label="手机号"
                width="120">
            </el-table-column>

            <el-table-column
                label="入院时间"
                width="140">
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
               
                <el-button
                  size="mini"
                  type="warning"
                  @click="doOutHospital(scope.row.id)">审核通过</el-button>
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
        <el-dialog :visible.sync=" billViewForm" @open='open'>
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
                         住院时间：{{hospitalizationBill.needPayDays}}天
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
                        需支付<span style="color:red">{{hospitalizationBill.sumMoney}} </span>元--
                        已支付<span style="color:green">{{havePayHospitalizationBill}} </span>元
                        
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
                        需支付<span style="color:red">{{getPrescriptionBillMoney}}</span>元--
                        已支付<span style="color:green">{{havePayPrescriptionBillMoney}}</span>元
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
                       需支付<span style="color:red">{{getMedicalAdviceBillMoney}}</span>元--
                       已支付<span style="color:green">{{havePayMedicalAdviceBill}}</span>元
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

         <el-descriptions class="margin-top" title="" :column="1"  border>
            <el-descriptions-item>
                <template slot="label">
                    <i class="el-icon-more"></i>
                        账单统计
                </template>
                <el-col :span="10" >
                        <div style="width:450px;height:450px;" id="chartbox"></div>
                </el-col>
                       
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
                        <span style="color:red">{{hospitalizationBill.sumMoney+getPrescriptionBillMoney+getMedicalAdviceBillMoney-getDrugOutBillMoney}}</span>元
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
import echarts from 'echarts'
export default {
   data() {
      return {
        havePayMedicalAdviceBill:0,
        havePayHospitalizationBill:0,
        havePayPrescriptionBillMoney:0,
        data:[
        { name: '住院费用', value:0 },
        { name: '医嘱费用', value:0 },
        { name: '药品费用', value:0 },
        ],
        nurseId:'',
        nurseDeptId:'',
        no:'',
        name:'',
        gender:'',
        cardId:'',
        status:3,
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
      
      if(a=='4'){
        this.nurseDeptId = parseInt(window.localStorage.getItem("deptId"))
      }else if(a=='2'){
        this.nurseId =  parseInt(window.localStorage.getItem("workerId"))
      }else if(a=='9'){
        this.nurseDeptId=''
        this.nurseId=''
      }
      this.search();
   },
   methods:{
        drawLine(){
                     // 绘制图表
        var myChart = echarts.init(window.document.getElementById('chartbox'));
        // 指定图表的配置项和数据
        var option = {
          //标题
        //   title: {
        //     text: '本科室床位使用情况统计',
        //     x: 'left' ,              //标题位置
        //     // textStyle: { //标题内容的样式
        //     //   color: '#000',
        //     //   fontStyle: 'normal',
        //     //   fontWeight: 100,
        //       fontSize: 12//主题文字字体大小，默认为18px
        //     // },
        //   },
          // stillShowZeroSum: true,
          //鼠标划过时饼状图上显示的数据
          tooltip: {
            trigger: 'item',
            formatter: '{a}<br/>{b}:{c} ({d}%)'
          },
          //图例
          legend: {//图例  标注各种颜色代表的模块
            type:'scroll',
            orient:'horizontal',
            // orient: 'vertical',//图例的显示方式  默认横向显示
            bottom: 0,//控制图例出现的距离  默认左上角
            left: '20',//控制图例的位置
            // itemWidth: 16,//图例颜色块的宽度和高度
            // itemHeight: 12,
            textStyle: {//图例中文字的样式
              color: '#000',
              fontSize: 16
            },
            // data: ['住院', '处方','医嘱','退药']//图例上显示的饼图各模块上的名字
          },
          //饼图中各模块的颜色
          color: [ '#69D3BE','#9D9E9F','#F88282'],
        //   color: [ '#E6A23C','#F56C6C', '#409EFF','#C0C4CC'],
          // 饼图数据
          series: {
             name: '缴费统计',
            type: 'pie',             //echarts图的类型   pie代表饼图
            radius: ['50%','70%'],           //饼图中饼状部分的大小所占整个父元素的百分比
            center: ['50%', '50%'],  //整个饼图在整个父元素中的位置
            // data:''               //饼图数据
            data:  this.data,
            itemStyle: {
              normal: {
                label: {
                  show: true,//饼图上是否出现标注文字 标注各模块代表什么  默认是true
                  // position: 'inner',//控制饼图上标注文字相对于饼图的位置  默认位置在饼图外
                },
                labelLine: {
                  show: true//官网demo里外部标注上的小细线的显示隐藏    默认显示
                }
              }
            },
          }

        }
        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option,true)
      
       },
       open(){
            var patient=this.patient;
            
            this.$axios.post("/api/getHospitalizationBill",patient)
            .then(res=>{
                
                if(res.data.sumMoney!=null){
                    this.hospitalizationBill=res.data;
                    this.data[0].value=this.hospitalizationBill.sumMoney;
                    this.havePayHospitalizationBill=res.data.payDays*200;
                }
                this.$axios.post("/api/getPrescriptionBill",patient)
                .then(res=>{
                    this.drugList=res.data;
                    var getPrescriptionBillMoney=0;
                    if( this.drugList.length>0){
                        this.drugList.forEach(drug=>{
                        getPrescriptionBillMoney+=drug.money;
                        this.getPrescriptionBillMoney=getPrescriptionBillMoney;
                    })

                    }
                    this.$axios.post("/api/getMedicalAdviceBill",patient)
                    .then(res=>{
                        this.MedicalAdviceList=res.data;
                        var getMedicalAdviceBillMoney=0;
                        this.MedicalAdviceList.forEach(project=>{
                            getMedicalAdviceBillMoney+=project.price;
                            
                        })
                        this.getMedicalAdviceBillMoney=getMedicalAdviceBillMoney;
                        
                     this.data[1].value=getMedicalAdviceBillMoney;
                        
                       
                       
                        this.$axios.post("/api/getDrugOutBill",patient)
                        .then(res=>{
                            if(res.data.length != 0){
                                this.drugOutList = res.data;
                            }
                            var getDrugOutBillMoney=0;
                            if(this.drugOutList.length>0){
                             
                            this.drugOutList.forEach(drug=>{
                            getDrugOutBillMoney+=drug.money;
                            })
                            this.getDrugOutBillMoney=getDrugOutBillMoney;
                            }

                            this.data[2].value=this.getPrescriptionBillMoney-getDrugOutBillMoney
                             window.setTimeout(() => {
                                this.drawLine();	// 执行echarts画图方法
                                }, 0);

                                console.log(this.data)
                           
                        })
                        
                    })
                })

            })

       },
       doOutHospital(id){
           this.$confirm('确定要通过审核吗?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                // type: 'warning'
            }).then(() => {
                this.$axios.get("/api/doOutHospital",{params:{id:id}})
                .then(res=>{
                    if(res.data.status==4001){
                            this.$message({
                            type: "error",
                            message: "没有权限!",
                             duration:2000
                        });
                     }else{
                        this.$message({
                            type: 'success',
                            message: '审核成功!',
                            duration:2000
                        });
                        this.search();
                     }
                })
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消审核',
                     duration:2000
                });          
            });
       },
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

             this.$axios.post("/api/getMedicalAdviceBillHavePay",patient).then(res=>{
                 this.havePayMedicalAdviceBill=res.data;
             })

             this.$axios.post("/api/getPrescriptionBillHavePay",patient).then(res=>{
                 if(res.data!=''){
                    this.havePayPrescriptionBillMoney=res.data;
                 }else{
                     this.havePayPrescriptionBillMoney=0;
                 }
                 
             })

            this.billViewForm=true;
            
            
       },
      //查询病人信息列表
        search(){
            this.$axios.get("/api/findPatientsByChangeDept",{params:{name:this.name,no:this.no,gender:this.gender,
                  cardId:this.cardId,nurseId:this.nurseId,deptId:this.nurseDeptId,status:3,pageNum:1,pageSize:this.pageSize}})
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
                  caedId:this.cardId,status:3,nurseId:this.nurseId,deptId:this.nurseDeptId,pageNum:this.pageNum,pageSize:this.pageSize}})
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
