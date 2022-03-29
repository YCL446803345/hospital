<template>
    <div>
        <!-- 面包xie导航 -->
        <br>
        <el-breadcrumb separator="/">
            <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item><a href="#/patientInfo">离院病人费用信息管理</a></el-breadcrumb-item>
            <el-breadcrumb-item>离院病人费用信息查询列表</el-breadcrumb-item>
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
                width="200">
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
                 <el-button
                  size="mini"
                  type="primary"
                  @click="showAnalysis(scope.row.id)">病人费用数据分析</el-button> 
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

         <el-dialog :visible.sync="analysisForm" @open="open">
        <h1 align="center">缴费详情 </h1><br>
        <template>
            <el-row>
                <el-col :span="10">
                    <div class="grid-content bg-purple" style="width:700px;height:500px;" id="pies">
                        <div style="width:500px;height:500px;" id="pie"></div>
                    </div>
                </el-col>
            </el-row>
        </template>
             <div slot="footer" class="dialog-footer">
                 <!-- <el-button @click="getPie">显示</el-button> -->
                <el-button @click="closeAnalysisForm">确定</el-button>
            </div>
        </el-dialog>

    </div>
</template>

<script>
import echarts from 'echarts'
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
        analysisForm:false,
        paymentRecordList:[],
        patient:{
            name:"",
            no:"",
        },
        num:1,
        size:5,
          patientId:0,
          Intotal:0,
          data:[
            { name: '住院', value: 10 },
            { name: '处方', value: 30 },
            { name: '医嘱', value: 30 },
            { name: '退药', value: 30 }
          ],
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
         closeCostInfoForm(){
             this.costViewForm = false;
         },
        getPie() {
        // 绘制图表
        var myChart = echarts.init(window.document.getElementById('pie'));
        // 指定图表的配置项和数据
        var option = {
          //标题
          title: {
            text: '住院病人缴费统计',
            x: 'left' ,              //标题位置
          },
          // stillShowZeroSum: true,
          //鼠标划过时饼状图上显示的数据
          tooltip: {
            trigger: 'item',
            formatter: '{a}<br/>{b}:{c} ({d}%)'
          },
          //图例
          legend: {//图例  标注各种颜色代表的模块
            // orient: 'vertical',//图例的显示方式  默认横向显示
            bottom: 10,//控制图例出现的距离  默认左上角
            left: 'center',//控制图例的位置
            // itemWidth: 16,//图例颜色块的宽度和高度
            // itemHeight: 12,
            textStyle: {//图例中文字的样式
              color: '#000',
              fontSize: 16
            },
            data: ['住院', '处方','医嘱','退药']//图例上显示的饼图各模块上的名字
          },
          //饼图中各模块的颜色
          color: [ '#A8A8A8','#34C447', '#5ab1ef','#EFB05A'],
          // 饼图数据
          series: {
             name: '缴费统计',
            type: 'pie',             //echarts图的类型   pie代表饼图
            radius: '70%',           //饼图中饼状部分的大小所占整个父元素的百分比
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

      showAnalysis(id){
            this.$axios.get("/api/queryPersonalData",{params:{id:id}}).then(res=>{
            this.data[0].value=res.data[0]
            this.data[1].value=res.data[1]
            this.data[2].value=res.data[2]
            this.data[3].value=res.data[3]
            this.analysisForm = true;
            // setTimeout(this.getPie(),2000)
        })
      },
        closeAnalysisForm(){
            echarts.init(window.document.getElementById('pie')).dispose()
            // let pies = window.document.getElementById('pie');
            // let pie = pies.childNodes[0];
            // pie.removeChild(pie.childNodes[0]);
            this.analysisForm = false;
        },
         open(){
            window.setTimeout(()=>{
                this.getPie();
            },0)
        }

   }
}
</script>

<style scoped>

</style>
