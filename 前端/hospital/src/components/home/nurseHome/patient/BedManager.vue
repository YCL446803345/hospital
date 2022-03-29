<template>
    <div>
        <!-- 面包xie导航 -->
        <el-breadcrumb separator="/">
            <el-breadcrumb-item :to="{ path: '/gotoHome' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item ><a href="#/nurse/bed">床位管理</a></el-breadcrumb-item>
            <el-breadcrumb-item>床位管理</el-breadcrumb-item>
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
                 <el-button type="primary" @click="gotoBedView">床位概况</el-button>
                
            </el-col>
            
        </el-row>

        <el-row>
        <el-col :span="4" v-for="bed in bedList" :key="bed.id"  >
            <div style="padding: 8px">
                
              <el-card :body-style="{ padding: '0px' }"  shadow="hover">
                  <div style="padding: 0px">
                      <img v-if='bed.status==1' src="../../../../css/image/医院1.png" class="image">
                    <img v-if='bed.status==2' src="../../../../css/image/医院.png" class="image">
                <!-- <img v-if='bed.status==1' src="../../../../css/image/医院1.png" class="image"> -->
                    <div style="padding: 0px">
                  
                          <span>
                              <!-- <el-tag type="danger" disable-transitions>
                                  {{bed.code}}
                                  </el-tag>

                              <el-tag type="danger" disable-transitions>
                                  {{bed.deptName}}
                              </el-tag>

                              <el-tag :type="bed.status==1?'warning':'danger'" disable-transitions>
                                  {{bed.status==1?'未使用':'使用中'}}
                              </el-tag> -->

                              {{bed.code}}&nbsp;&nbsp;
                              {{bed.deptName}}&nbsp;&nbsp;
                              {{bed.status==1?'未使用':'使用中'}}
                          </span>
                    
                        <div class="bottom clearfix">
                              <!-- <time class="time">{{ currentDate }}</time> -->
                              <el-button type="success" class="button" 
                                  @click="gotoBedInfo(bed.id,bed.deptName,bed.patientName,bed.code,bed.patientNo,bed.status,bed.patientId)">详情</el-button>
                              <el-button v-if='bed.status==2' type="danger" class="button" 
                              @click="stopUseBed(bed.id,bed.patientId,bed.deptId)">停止床位</el-button>

                              <!-- <el-button v-if='bed.status==1' type="info" class="button" disabled size='medium'
                              @click="stopUseBed(bed.id,bed.patientId)">停止床位</el-button> -->
                        </div>
                    </div>
                  </div>
                    
              </el-card>
            </div>
           
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
            <el-form-item label="科室" :label-width="formLabelWidth">
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
        <el-dialog :visible.sync="bedViewForm1" @open="open()" >
        <h1 align="center"> </h1><br>
        <el-row>
                <el-col :span="10" >
                        <div style="width:450px;height:450px;" id="chartbox"></div>
                </el-col>
                 <el-col :span="10" >
                        <div style="width:450px;height:450px;" id="chartbox1"></div>
                </el-col>
        </el-row>

        <el-divider><i class="el-icon-collection-tag"></i></el-divider>
         <el-row>
                <el-col :span="10" >
                        <div style="width:450px;height:450px;" id="chartbox2"></div>
                </el-col>
                
        </el-row>
         <el-divider><i class="el-icon-collection-tag"></i></el-divider>
         <el-row>
                 <el-col :span="10" >
                        <div style="width:450px;height:450px;" id="chartbox3"></div>
                </el-col>
                
        </el-row>
        <div slot="footer" class="dialog-footer">
            <el-button type="danger" @click="closeBedForm">确定</el-button>
        </div>
        </el-dialog>

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
        deptId1:1,
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
         data:[
            { name: '使用中', value: 20 },
            { name: '未使用', value: 20 },
          ],
        data1:[
        { name: '使用中', value: 10 },
        { name: '未使用', value: 30 },
        ],
         data2:[
        { name: '内科', value: 10 },
        { name: '外科', value: 30 },
        { name: '妇产科', value: 30 },
        { name: '儿科', value: 30 },
        { name: '肿瘤科', value: 30 },
        { name: '中医科', value: 30 },
        { name: '传染科', value: 30 },
        { name: '医学影像科', value: 30 },
        { name: '免疫科', value: 30 },
        { name: '人事部', value: 30 },
        { name: '财务部', value: 30 },
        ],
          data4:[
        { name: '内科', value: 10 },
        { name: '外科', value: 30 },
        { name: '妇产科', value: 30 },
        { name: '儿科', value: 30 },
        { name: '肿瘤科', value: 30 },
        { name: '中医科', value: 30 },
        { name: '传染科', value: 30 },
        { name: '医学影像科', value: 30 },
        { name: '免疫科', value: 30 },
        { name: '人事部', value: 30 },
        { name: '财务部', value: 30 },
        ],

        data3:[
        { name: '内科', value: '内科' },
        { name: '外科', value: '外科' },
        { name: '妇产科', value: '妇产' },
        { name: '儿科', value: '儿科' },
        { name: '肿瘤科', value: '肿瘤' },
        { name: '中医科', value: '中医' },
        { name: '传染科', value: '传染' },
        { name: '医学影像科', value: '影像' },
        { name: '免疫科', value: '免疫' },
        { name: '人事部', value: '人事' },
        { name: '财务部', value: '财务' },
        ],
        colorList: [
        //设置发货柱子的，普通的柱子颜色
        "#69D3BE",
        "#9D9E9F",
        "#F88282",
        "#69D3BE",
        "#9D9E9F",
        "#F88282",
        "#69D3BE",
        "#9D9E9F",
        "#F88282",
        "#69D3BE",
        "#9D9E9F",
        ],
        colorList1: [
        //设置发货柱子的，普通的柱子颜色
        "#606266",
        "#606266",
        "#606266",
        "#606266",
        "#606266",
        "#606266",
        "#606266",
        "#606266",
        "#606266",
        "#606266",
        "#606266",
        
        ],
      }
   },
   created(){
    var a = window.localStorage.getItem("roleId")
    this.nurseRoleId=a;
      
      if(a=='4'||a=='2'){
        this.deptId = parseInt(window.localStorage.getItem("deptId"))
        this.deptId1=parseInt(window.localStorage.getItem("deptId"))
      }else if(a=='9'){
        this.deptId=''
        this.deptId1=1
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
       drawLine3(){
            this.chart = this.$echarts.init(window.document.getElementById('chartbox3'));
            let _this = this;
            this.chart.setOption({
                title: {
                    text: '各科室床位数量统计',
                    x: 'left' ,              //标题位置
                    // textStyle: { //标题内容的样式
                    //   color: '#000',
                    //   fontStyle: 'normal',
                    //   fontWeight: 100,
                    fontSize: 12//主题文字字体大小，默认为18px
                    // },
                },
                grid: {
                left: "50"
                // right: "60"
                },
                
                legend: {
                show: false,
                data: this.legends
                },
                tooltip: {
                trigger: "axis",
                show: true,
                axisPointer: {
                    // 坐标轴指示器，坐标轴触发有效
                    type: "shadow" // 默认为直线，可选为：'line' | 'shadow'
                }
                },
                xAxis: {
                axisLine: { show: false }, // 轴线
                axisTick: { show: false }, // 刻度
                type: "category",
                data: this.data3,//X轴显示
                axisLabel: {
                    color: "#333",
                    interval: 0 //0：不隔行显示，1：隔一行显示
                }
                },
                yAxis: {
                type: "value",
                nameTextStyle: {
                    fontWeight: "bold",
                    align: "left",
                    padding: [0, 50, 10, 0],
                    color: "#ffffff"
                },
                axisLine: { show: false }, // 轴线
                axisTick: { show: false }, // 刻度
                axisLabel: {
                    color: "#333",
                    formatter: `{value}`//Y轴的显示形式，value，percent
                }
                },
                series: [
                {//实现普通色的柱子
                    data: this.data2,
                    type: "bar",
                    smooth: true, // 平滑
                    symbol: "none",
                    lineStyle: {
                    color: "#FF5858"
                    },
                    barMaxWidth: 30,
                    itemStyle: {
                    normal: {
                        label: {
                        show: true, //开启显示
                        position: "top", //在上方显示
                        textStyle: {
                            //数值样式
                            color: "#222",
                            fontSize: 14
                        }
                        },
                        color: params => {
                        let colorList = _this.colorList;//柱子的颜色是普通的颜色
                        let index = params.dataIndex;
                        if (params.dataIndex >= colorList.length) {
                            index = params.dataIndex - colorList.length;
                        }
                        return colorList[index];
                        }
                    }
                    }
                },
                 {//实现普通色的柱子
                    data: this.data4,
                    type: "bar",
                    smooth: true, // 平滑
                    symbol: "none",
                    lineStyle: {
                    color: "#FF5858"
                    },
                    barMaxWidth: 30,
                    itemStyle: {
                    normal: {
                        label: {
                        show: true, //开启显示
                        position: "top", //在上方显示
                        textStyle: {
                            //数值样式
                            color: "#222",
                            fontSize: 14
                        }
                        },
                        color: params => {
                        let colorList = _this.colorList1;//柱子的颜色是普通的颜色
                        let index = params.dataIndex;
                        if (params.dataIndex >= colorList.length) {
                            index = params.dataIndex - colorList.length;
                        }
                        return colorList[index];
                        }
                    }
                    }
                }
                ]
                });
            
       },
       drawLine(){
                     // 绘制图表
        var myChart = echarts.init(window.document.getElementById('chartbox'));
        // 指定图表的配置项和数据
        var option = {
          //标题
          title: {
            text: '本科室床位使用情况统计',
            x: 'left' ,              //标题位置
            // textStyle: { //标题内容的样式
            //   color: '#000',
            //   fontStyle: 'normal',
            //   fontWeight: 100,
              fontSize: 12//主题文字字体大小，默认为18px
            // },
          },
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
          color: [ '#F56C6C','#909399'],
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

        drawLine1(){
                     // 绘制图表
        var myChart = echarts.init(window.document.getElementById('chartbox1'));
        // 指定图表的配置项和数据
        var option = {
          //标题
          title: {
            text: '住院部全床位使用情况统计',
            x: 'left' ,              //标题位置
            // textStyle: { //标题内容的样式
            //   color: '#000',
            //   fontStyle: 'normal',
            //   fontWeight: 100,
              fontSize: 16 //主题文字字体大小，默认为18px
            // },
          },
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
          color: [ '#E6A23C','#409EFF'],
        //   color: [ '#E6A23C','#F56C6C', '#409EFF','#C0C4CC'],
          // 饼图数据
          series: {
             name: '缴费统计',
            type: 'pie',             //echarts图的类型   pie代表饼图
            radius: ['50%','70%'],           //饼图中饼状部分的大小所占整个父元素的百分比
            center: ['50%', '50%'],  //整个饼图在整个父元素中的位置
            // data:''               //饼图数据
            data:  this.data1,
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

        drawLine2(){
                     // 绘制图表
        var myChart = echarts.init(window.document.getElementById('chartbox2'));
        // 指定图表的配置项和数据
        var option = {
          //标题
          title: {
            text: '各科室床位占比统计',
            x: 'left' ,              //标题位置
            // textStyle: { //标题内容的样式
            //   color: '#000',
            //   fontStyle: 'normal',
            //   fontWeight: 100,
              fontSize: 16 //主题文字字体大小，默认为18px
            // },
          },
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
        //   color: [ '#E6A23C','#409EFF'],
          color: [ '#E6A23C','#F56C6C', '#409EFF','#C0C4CC'],
          // 饼图数据
          series: {
             name: '缴费统计',
            type: 'pie',             //echarts图的类型   pie代表饼图
            radius: ['50%','70%'],           //饼图中饼状部分的大小所占整个父元素的百分比
            center: ['50%', '50%'],  //整个饼图在整个父元素中的位置
            // data:''               //饼图数据
            data:  this.data2,
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
           this.$axios.get("/api/getBedData",{params:{deptId:this.deptId1}})
            .then(res=>{
                console.log(res.data);
                this.data[0].value=parseInt(res.data[0])
                this.data[1].value=parseInt(res.data[1])
                this.data1[0].value=parseInt(res.data[2])
                this.data1[1].value=parseInt(res.data[3])

                 this.$axios.get("/api/getBedsByDept")
                 .then(res=>{
                         this.data2[0].value=parseInt(res.data[0])
                         this.data2[1].value=parseInt(res.data[1])
                         this.data2[2].value=parseInt(res.data[2])
                         this.data2[3].value=parseInt(res.data[3])
                         this.data2[4].value=parseInt(res.data[4])
                         this.data2[5].value=parseInt(res.data[5])
                         this.data2[6].value=parseInt(res.data[6])
                         this.data2[7].value=parseInt(res.data[7])
                         this.data2[8].value=parseInt(res.data[8])
                         this.data2[9].value=parseInt(res.data[9])
                         this.data2[10].value=parseInt(res.data[10])

                    this.$axios.get("/api/getBedsInUseByDept")
                    .then(res=>{
                        this.data4[0].value=parseInt(res.data[0])
                         this.data4[1].value=parseInt(res.data[1])
                         this.data4[2].value=parseInt(res.data[2])
                         this.data4[3].value=parseInt(res.data[3])
                         this.data4[4].value=parseInt(res.data[4])
                         this.data4[5].value=parseInt(res.data[5])
                         this.data4[6].value=parseInt(res.data[6])
                         this.data4[7].value=parseInt(res.data[7])
                         this.data4[8].value=parseInt(res.data[8])
                         this.data4[9].value=parseInt(res.data[9])
                         this.data4[10].value=parseInt(res.data[10])

                          window.setTimeout(() => {
                        this.drawLine();	// 执行echarts画图方法
                        this.drawLine1();	// 执行echarts画图方法
                        this.drawLine2();	// 执行echarts画图方法
                        this.drawLine3();	// 执行echarts画图方法
                        }, 0);
                    })
                 })
            })
       },
       closeBedForm(){
           this.bedViewForm1=false
       },
       gotoBedView(){
        this.bedViewForm1=true
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
    display: block;
  }

  .button {
    padding: 0;
    float: right;
    display: block;
  }

  .image {
    width: 100%;
    height: 100%;
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
