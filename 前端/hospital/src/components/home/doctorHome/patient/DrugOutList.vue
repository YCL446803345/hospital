<template>
    <div>
        <!-- 面包xie导航 -->
        <el-breadcrumb separator="/">
            <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item><a href="#/patientList">退药信息管理</a></el-breadcrumb-item>
            <el-breadcrumb-item>退药信息管理列表</el-breadcrumb-item>
        </el-breadcrumb>
        <el-row style="margin-top:10px;margin-bottom:10px">

            <el-col :span="2">
                <el-select v-model="outStatus" placeholder="退药状态">
                    <!-- <el-option label="处方状态" value="" ></el-option> -->
                    <el-option label="待审核" value="1" ></el-option>
                    <el-option label="待退药" value="2" ></el-option>
                    <!-- <el-option label="已退药" value="3" ></el-option> -->
                    <!-- <el-option label="已停止" value="4" ></el-option> -->
                </el-select>
            </el-col>

           
            <el-col :span="1" style="margin-left:2px;">
                <el-button type="success" @click="search">查询</el-button> 
            </el-col>

            <el-col :span="1" style="margin-left:2px;">
                 <el-button type="warning" @click="outStatus='',paheNum=1,pageSize=5">清空</el-button>
            </el-col>
            
        </el-row>
        <!-- 
            el-table数据表格
            :data 动态绑定 data中对象数组
            scope.row 表示对象数组的当前行对象
         -->
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
                prop="patientName"
                label="病人"
                width="80">
            </el-table-column>

            <el-table-column
                prop="doctorName"
                label="主治医生"
                width="80">
            </el-table-column>

            <el-table-column
                prop="drugName"
                label="药品"
                width="180">
            </el-table-column>

            <el-table-column
                prop="drugType"
                label="类型"
                width="80">
            </el-table-column>

            <el-table-column
                prop="specifications"
                label="规格"
                width="120">
            </el-table-column>

            <el-table-column
                prop="num"
                label="数量"
                width="80">
            </el-table-column>

            <el-table-column
                prop="spare2"
                label="制造商"
                width="270">
            </el-table-column>

            <el-table-column
                prop="createTime"
                label="创建时间"
                width="170">
            </el-table-column>

            <el-table-column
                prop="outCause"
                label="退药原因"
                width="120">
            </el-table-column>


            <el-table-column
                prop="outStatus"
                label="退药状态"
                width="100">
                <template slot-scope="scope">
                <span v-if="scope.row.outStatus=='1'">待审核</span>
                <span v-if="scope.row.outStatus=='2'">待退药</span>
                <span v-if="scope.row.outStatus=='3'">已退药</span>
                <span v-if="scope.row.outStatus=='4'">已撤销</span>
                <!-- <span v-if="scope.row.outStatus=='5'">已停止</span> -->
                </template>

            </el-table-column>
            
            <el-table-column label="操作">
               <template slot-scope="scope">
                  <!-- <el-button size="mini" type="primary" @click="gotoAddDrugOut(scope.row.id)">编辑</el-button> -->
                  <el-button size="mini" type="danger" @click="gotoStopDrugOut( 
                      scope.row.id,
                      scope.row.patientName,
                      scope.row.doctorName,
                      scope.row.drugName,
                      scope.row.drugType,
                      scope.row.specifications,
                      scope.row.num,
                      scope.row.spare2,
                      scope.row.createTime,
                      scope.row.outStatus
                      )">撤 销</el-button>
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

         <!-- 撤销退药 -->
    <el-dialog :visible.sync="stopDrugOutForm">
      <h1 align="center">撤销退药</h1>
      <br />
      <template>
        <el-descriptions class="margin-top" title="" :column="3" border>
          <el-descriptions-item>
            <template slot="label"><i class="el-icon-user"></i>病人</template>
            {{ stopDrugOut.patientName }}
          </el-descriptions-item>

          <el-descriptions-item>
            <template slot="label"><i class="el-icon-user"></i>类型</template>
            {{ stopDrugOut.drugType }}
          </el-descriptions-item>

          <el-descriptions-item>
            <template slot="label"><i class="el-icon-user"></i>主治医生</template>
            {{ stopDrugOut.doctorName }}
          </el-descriptions-item>

          <el-descriptions-item>
            <template slot="label"><i class="el-icon-user"></i>药品</template>
            {{ stopDrugOut.drugName }}
          </el-descriptions-item>

          <el-descriptions-item>
            <template slot="label"><i class="el-icon-user"></i>规格</template>
            {{ stopDrugOut.specifications }}
          </el-descriptions-item>

          <el-descriptions-item>
            <template slot="label"><i class="el-icon-user"></i>数量</template>
            {{ stopDrugOut.num }}
          </el-descriptions-item>

          <el-descriptions-item>
            <template slot="label"><i class="el-icon-user"></i>制造商</template>
            {{ stopDrugOut.spare2 }}
          </el-descriptions-item>

          <el-descriptions-item>
            <template slot="label"><i class="el-icon-user"></i>创建时间</template>
            {{ stopDrugOut.createTime }}
          </el-descriptions-item>
        
          <el-descriptions-item>
            <template slot="label"><i class="el-icon-tickets"></i>退药状态</template>
            {{ stopDrugOut.outStatus ===1?'待审核': 
               stopDrugOut.outStatus ===2?'待退药':
               stopDrugOut.outStatus ===3?'已退药':'已撤销' }}
          </el-descriptions-item>

        </el-descriptions>
      </template>

      <el-divider></el-divider>

      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="doStopDrugOut">确 认</el-button>
        <el-button @click="stopDrugOutForm = false;stopDrugOut={}">取 消</el-button>
      </div>
    </el-dialog>

    </div>
</template>

<script>

import qs from 'qs'

export default {
   data() {
      return {
        outStatus:'',
        drugOutList: [],
        total:100,
        pageNum:1,
        pageSize:5,
        headers:{},

        stopDrugOutForm: false,
        stopDrugOut:{},
      }
   },
   created(){
      this.search();
      this.headers={tokenStr:window.localStorage.getItem('tokenStr')};
   },
   methods:{
      doStopDrugOut() {
      var drugOut = this.stopDrugOut;
      console.log(drugOut)
      this.$axios
        .post("/api/doctor/stopDrugOut", drugOut)
        .then((res) => {
          if (res.data.status == 4001) {
            this.$message({
              type: "error",
              message: "没有权限!",
              duration: 2000,
            });
          } else {
            this.$message({
              type: "success",
              message: "撤销成功!",
              duration: 2000,
            });
            this.closeStopDrugOutForm();
            this.search();
          }
        });

    },
    closeStopDrugOutForm() {
      this.stopDrugOut = {
        id:'',
        patientName:'',
        doctorName:'',
        drugName:'',
        drugType:'',
        specifications:'',
        num:'',
        spare2:'',
        createTime:'',
        outStatus:''
      };
      this.stopDrugOutForm = false;
    },
    gotoStopDrugOut(
        id,
        patientName,
        doctorName,
        drugName,
        drugType,
        specifications,
        num,
        spare2,
        createTime,
        outStatus
    ) {
      this.stopDrugOut = {
        id:id,
        patientName:patientName,
        doctorName:doctorName,
        drugName: drugName,
        drugType:drugType,
        specifications:specifications,
        num:num,
        spare2:spare2,
        createTime:createTime,
        outStatus:outStatus
      };
      this.stopDrugOutForm = true;
    },



      //查询处方列表
        search(){
            console.log("---")
            this.$axios.get("/api/doctor/getDrugOutList",{params:{
            outStatus:this.outStatus,
            pageNum:this.pageNum,
            pageSize:this.pageSize
            }})
            .then(res=>{
                 console.log("=========================");
               console.log(res.data.data.list);
                this.drugOutList=res.data.data.list;
                this.total=res.data.data.total;
                this.pageNum=res.data.data.pageNum;
                this.pageSize=res.data.data.pageSize;

            })
        },
        changeSize(value){
            this.pageSize=value;
            this.search();
        },
        changePage(value){
            this.pageNum=value;
            this.search();
        },
          getIndex(i){
            return (i+1)+this.pageSize*(this.pageNum-1);
         },
   }
}
</script>

<style scoped>

</style>
