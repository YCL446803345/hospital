<template>
    <div>

        <el-row>
            <el-col :span="10">
                <div class="grid-content bg-purple" style="width:100%;height:500px;">
                    <div style="width:500px;height:500px;" id="pie1"></div>
                </div>
            </el-col>
            <!-- <el-col :span="12">
                <div class="grid-content bg-purple-light" style="width:100%;height:500px;">
                    <div style="width:500px;height:500px;" id="pie2"></div>
                </div>
            
            </el-col> -->
        </el-row>
        <!-- <el-row>
            <el-col :span="10">
                <div class="grid-content bg-purple" style="width:100%;height:500px;">
                     <div style="width:500px;height:500px;" id="pie3"></div>
                </div>
            </el-col>
            <el-col :span="12">
                <div class="grid-content bg-purple-light" style="width:100%;height:500px;">
                    <div style="width:500px;height:500px;" id="pie4"></div>
                </div>
                </el-col>
        </el-row> -->

    </div>
</template>

<script>
import echarts from 'echarts'
export default {
    
    data(){
        return{
          data1:[
            { name: '未缴费', value: 10 },
              { name: '已缴费', value: 30}
          ],
          // data2:[
          //   { name: '未缴费', value: 10 },
          //     { name: '已缴费', value: 30}
          // ],
          // data3:[
          //   { name: '未缴费', value: 10 },
          //     { name: '已缴费', value: 30}
          // ],
          //  data4:[
          //   { name: '未退费', value: 10 },
          //     { name: '已退费', value: 30}
          // ],
        }
    },
    methods:{
         getPie1() {
        // 绘制图表
        var myChart1 = echarts.init(document.getElementById('pie1'))
        // 指定图表的配置项和数据
        var option1 = {
          //标题
          title: {
            text: '住院缴费统计',
            x: 'top' ,              //标题位置
            // textStyle: { //标题内容的样式
            //   color: '#000',
            //   fontStyle: 'normal',
            //   fontWeight: 100,
            //   fontSize: 16 //主题文字字体大小，默认为18px
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
            // orient: 'vertical',//图例的显示方式  默认横向显示
            bottom: 10,//控制图例出现的距离  默认左上角
            left: 'center',//控制图例的位置
            // itemWidth: 16,//图例颜色块的宽度和高度
            // itemHeight: 12,
            textStyle: {//图例中文字的样式
              color: '#000',
              fontSize: 16
            },
            data: ['未缴费', '已缴费']//图例上显示的饼图各模块上的名字
          },
          //饼图中各模块的颜色
          color: [ '#A8A8A8','#34C447', '#5ab1ef'],
          // 饼图数据
          series: {
             name: '住院缴费统计',
            type: 'pie',             //echarts图的类型   pie代表饼图
            radius: '70%',           //饼图中饼状部分的大小所占整个父元素的百分比
            center: ['50%', '50%'],  //整个饼图在整个父元素中的位置
            // data:''               //饼图数据
            data: this.data1,
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
        myChart1.setOption(option1)
      
      },

          getPie2() {
        // 绘制图表
        var myChart = echarts.init(document.getElementById('pie2'))
        // 指定图表的配置项和数据
        var option = {
          //标题
          title: {
            text: '医疗项目缴费统计',
            x: 'left' ,              //标题位置
            // textStyle: { //标题内容的样式
            //   color: '#000',
            //   fontStyle: 'normal',
            //   fontWeight: 100,
            //   fontSize: 16 //主题文字字体大小，默认为18px
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
            // orient: 'vertical',//图例的显示方式  默认横向显示
            bottom: 10,//控制图例出现的距离  默认左上角
            left: 'center',//控制图例的位置
            // itemWidth: 16,//图例颜色块的宽度和高度
            // itemHeight: 12,
            textStyle: {//图例中文字的样式
              color: '#000',
              fontSize: 16
            },
            data: ['未缴费', '已缴费']//图例上显示的饼图各模块上的名字
          },
          //饼图中各模块的颜色
          color: [ '#A8A8A8','#34C447', '#5ab1ef'],
          // 饼图数据
          series: {
             name: '医疗项目缴费',
            type: 'pie',             //echarts图的类型   pie代表饼图
            radius: '70%',           //饼图中饼状部分的大小所占整个父元素的百分比
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
        myChart.setOption(option)
      
      },


         getPie3() {
        // 绘制图表
        var myChart = echarts.init(document.getElementById('pie3'))
        // 指定图表的配置项和数据
        var option = {
          //标题
          title: {
            text: '处方缴费统计',
            x: 'left' ,              //标题位置
            // textStyle: { //标题内容的样式
            //   color: '#000',
            //   fontStyle: 'normal',
            //   fontWeight: 100,
            //   fontSize: 16 //主题文字字体大小，默认为18px
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
            // orient: 'vertical',//图例的显示方式  默认横向显示
            bottom: 10,//控制图例出现的距离  默认左上角
            left: 'center',//控制图例的位置
            // itemWidth: 16,//图例颜色块的宽度和高度
            // itemHeight: 12,
            textStyle: {//图例中文字的样式
              color: '#000',
              fontSize: 16
            },
            data: ['未缴费', '已缴费']//图例上显示的饼图各模块上的名字
          },
          //饼图中各模块的颜色
          color: [ '#A8A8A8','#34C447', '#5ab1ef'],
          // 饼图数据
          series: {
            name: '处方统计分布',
            type: 'pie',             //echarts图的类型   pie代表饼图
            radius: '70%',           //饼图中饼状部分的大小所占整个父元素的百分比
            center: ['50%', '50%'],  //整个饼图在整个父元素中的位置
            // data:''               //饼图数据
            data: this.data3,
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
        myChart.setOption(option)
      
      },

      getPie4() {
        // 绘制图表
        var myChart = echarts.init(document.getElementById('pie4'))
        // 指定图表的配置项和数据
        var option = {
          //标题
          title: {
            text: '退费执行统计',
            x: 'left' ,              //标题位置
            // textStyle: { //标题内容的样式
            //   color: '#000',
            //   fontStyle: 'normal',
            //   fontWeight: 100,
            //   fontSize: 16 //主题文字字体大小，默认为18px
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
            // orient: 'vertical',//图例的显示方式  默认横向显示
            bottom: 10,//控制图例出现的距离  默认左上角
            left: 'center',//控制图例的位置
            // itemWidth: 16,//图例颜色块的宽度和高度
            // itemHeight: 12,
            textStyle: {//图例中文字的样式
              color: '#000',
              fontSize: 16
            },
            data: ['未退费', '已退费']//图例上显示的饼图各模块上的名字
          },
          //饼图中各模块的颜色
          color: [ '#A8A8A8','#34C447', '#5ab1ef'],
          // 饼图数据
          series: {
             name: '退费统计',
            type: 'pie',             //echarts图的类型   pie代表饼图
            radius: '70%',           //饼图中饼状部分的大小所占整个父元素的百分比
            center: ['50%', '50%'],  //整个饼图在整个父元素中的位置
            // data:''               //饼图数据
            data: this.data4,
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
        myChart.setOption(option)
      
      },
      
    
    },
    created(){
      //发送axios查询住院缴费
      this.$axios.get("account/countHospitalization",{headers:{strtoken:localStorage.getItem("token")}}).then(re=>{
        this.data1[0].value=re.data.data[0]
        this.data1[1].value=re.data.data[1]
        this.getPie1()
      })
    },
    mounted(){
        this.$nextTick(function() {
            this.getPie1()
        })
    }
}
</script>

<style>

</style>