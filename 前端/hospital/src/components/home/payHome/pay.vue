<template>
  <div>
     <!-- 查询 -->
     <br>
          <el-row>
    
          <el-form :model="ruleForm"  ref="ruleForm" label-width="100px" class="demo-ruleForm">
            
              <el-col :span="10">
             
            <el-form-item label="申请时间" >
              <el-col :span="11">
                <el-form-item prop="startTime">
                  <el-date-picker type="date" placeholder="选择日期" v-model="ruleForm.startTime" style="width: 100%;"  @change="getData()"></el-date-picker>
                </el-form-item>
              </el-col>
              <el-col class="line" :span="2">-</el-col>
              <el-col :span="11">
                <el-form-item prop="endTime">
                  <el-date-picker type="date" placeholder="选择日期" v-model="ruleForm.endTime" style="width: 100%;"  @change="getData()"></el-date-picker>
                </el-form-item>
              </el-col>
            
            </el-form-item>
              </el-col>
             <el-col :span="10">
            <el-form-item>
              <el-button type="primary" @click="getData()">查询</el-button>
              <el-button @click="resetForm('ruleForm')">重置</el-button>
            </el-form-item>
             </el-col>
          </el-form> 
     
    </el-row>
 <el-divider></el-divider>
  
      <el-row >
        <el-col :span="12"> <div id="test" style="width:500px;height:400px"></div></el-col>
        <el-col :span="12"><div id="report" style="width: 500px;height: 400px;"></div></el-col>
      </el-row>


     
  </div>
</template>

<script>
import  echarts from 'echarts';


export default {
  data(){
    return{
        ruleForm: {
          startTime: '',
          endTime: '',

        },
      countData:[],
      charts: "",
      score: "100",
      source: [
          ["入院缴费",1,1],
          ["医疗项目",2,3],
          ["处方缴费",4,5]
      ],
      option : {
          //标题
          // title: {
          //   text: '这是个标题',
          //   x: 'left' ,              //标题位置
          //   // textStyle: { //标题内容的样式
          //   //   color: '#000',
          //   //   fontStyle: 'normal',
          //   //   fontWeight: 100,
          //   //   fontSize: 16 //主题文字字体大小，默认为18px
          //   // },
          // },
          // stillShowZeroSum: true,
          //鼠标划过时饼状图上显示的数据
          tooltip: {
            trigger: 'item',
            formatter: '{a}<br/>{b}:{c}'+'元'+' ({d}%)'
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
            data: ['入院缴费', '医疗项目', '处方缴费']//图例上显示的饼图各模块上的名字
          },
          //饼图中各模块的颜色
          color: ['#34C447', '#A8A8A8', '#5ab1ef'],
          // 饼图数据
          series: {

            name: '收入费用占比',
            type: 'pie',             //echarts图的类型   pie代表饼图
            radius: '70%',           //饼图中饼状部分的大小所占整个父元素的百分比
            center: ['50%', '50%'],  //整个饼图在整个父元素中的位置
            // data:''               //饼图数据
            data: [                  //每个模块的名字和值
              { name: '入院缴费', value: 10 },
              { name: '医疗项目', value: 30 },
              { name: '处方缴费', value: 50 }
            ],
            itemStyle: {
              normal: {
                label: {
                  show: true,//饼图上是否出现标注文字 标注各模块代表什么  默认是true
                   position: 'top',//控制饼图上标注文字相对于饼图的位置  默认位置在饼图外
                  textStyle:{
                    color:'black',
                    fontSize:16
                  }
                },
                labelLine: {
                  show: true//官网demo里外部标注上的小细线的显示隐藏    默认显示
                }
              }
            },
          }

        },
    }
  },
  methods:{
     resetForm(formName) {
        this.$refs[formName].resetFields();
      },
    getData(){
         let startTime = "";
         if(this.ruleForm.startTime!=''){
           startTime=this.dateFormat(this.ruleForm.startTime)
         }

          let endTime = "";
         if(this.ruleForm.endTime!=''){
           endTime=this.dateFormat(this.ruleForm.endTime)
         }
    //查图标所需要的数据
    //住院费用 总次数
    //检查费用 总次数
    //处方费用 总次数
    this.$axios.get("/api/getAllPayCount",{params:{
        startTime:startTime,
        endTime:endTime
    }}).then(re=>{
      this.source[0]=["入院缴费", "医疗项目", "处方缴费"]
      this.source[1]=["入院缴费",re.data.data[0],re.data.data[1]]
      this.source[2]=["医疗项目",re.data.data[2],re.data.data[3]]
      this.source[3]=["处方缴费",re.data.data[4],re.data.data[5]]
      this.countData=re.data.data
      this.option.series.data[0].value=re.data.data[0]
      this.option.series.data[1].value=re.data.data[2]
      this.option.series.data[2].value=re.data.data[4]
       var myChart = echarts.init(document.querySelector('#test'))
        myChart.setOption(this.option)
          this.$nextTick(function() {
            this.draw("report");
          });
    })
    },
    draw(id) {
      this.charts = echarts.init(document.getElementById(id));
      this.charts.setOption({
        legend: {},
        tooltip: {},
        dataset: {
          source: this.source    //连接数据
        },
        xAxis: { type: "category" },    
        yAxis: {
        //这个地方如果需要在Y轴定义最大值就放开,如果需要根据数据自适应的话,就注释掉
          // type: "value",           
          // max: this.score,
          // maxInterval: this.score * 0.2,
          // minInterval: 1,
          // splitNumber: 4
        },
        grid: { bottom: 30 },
        series: [
          {
            color: "#999" ,
            type: "bar",    //表示这个是柱状图
            barCategoryGap: "40%",
            itemStyle: { color: "#999" ,
             normal:{
                label:{
                show:true,
                position:'top',
                color: "#999" ,
                textStyle:{
                  color:'black',
                  fontSize:16
                }
              }
             }
              
            },   //定义颜色
            tooltip: {
              formatter: params => {     
              //console.log(params)  //打印这个params,按自己需要拼接字符串
                return ` ${params.value[0]} <br/>
                         ${params.seriesName}:${params.value[1]}`+"元";
              }
            }
          },
          
        ]
      });
    },
     //日期格式转换方法
      dateFormat(time){
        var date=new Date(time);
        var year=date.getFullYear();
        /* 在日期格式中，月份是从0开始的，因此要加0
        * 使用三元表达式在小于10的前面加0，以达到格式统一  如 09:11:05
        * */
        var month= date.getMonth()+1<10 ? "0"+(date.getMonth()+1) : date.getMonth()+1;
        var day=date.getDate()<10 ? "0"+date.getDate() : date.getDate();
        var hours=date.getHours()<10 ? "0"+date.getHours() : date.getHours();
        var minutes=date.getMinutes()<10 ? "0"+date.getMinutes() : date.getMinutes();
        var seconds=date.getSeconds()<10 ? "0"+date.getSeconds() : date.getSeconds();
        // 拼接
        return year+"-"+month+"-"+day+" "+hours+":"+minutes+":"+seconds;
      }


  },
  created(){
  this.getData()    

  },
  
  
  mounted(){
   var myChart = echarts.init(document.querySelector('#test'))
   myChart.setOption(this.option)
    this.$nextTick(function() {
      this.draw("report");
    });
  },
  watch:{
    option:function(){
       var myChart = echarts.init(document.querySelector('#test'))
        myChart.setOption(this.option)
          this.$nextTick(function() {
            this.draw("report");
          });
    },
    source(){
       var myChart = echarts.init(document.querySelector('#test'))
        myChart.setOption(this.option)
        this.$nextTick(function() {
          this.draw("report");
        });
    }
  }

}
</script>

<style>

</style>