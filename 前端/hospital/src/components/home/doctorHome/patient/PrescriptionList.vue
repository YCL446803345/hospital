<template>
    <div>
        <!-- 面包xie导航 -->
        <el-breadcrumb separator="/">
            <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item><a href="#/patientList">处方信息管理</a></el-breadcrumb-item>
            <el-breadcrumb-item>处方信息管理列表</el-breadcrumb-item>
        </el-breadcrumb>
        <el-row style="margin-top:10px;margin-bottom:10px">

            <el-col :span="2">
                <el-select v-model="prescriptionStatus" placeholder="处方状态">
                    <!-- <el-option label="处方状态" value="" ></el-option> -->
                    <el-option label="待审核" value="1" ></el-option>
                    <el-option label="待发药" value="2" ></el-option>
                    <el-option label="待执行" value="3" ></el-option>
                    <el-option label="已执行" value="4" ></el-option>
                    <!-- <el-option label="已退药" value="5" ></el-option> -->
                </el-select>
            </el-col>

           
            <el-col :span="1" style="margin-left:2px;">
                <el-button type="success" @click="search">查询</el-button>
               
            </el-col>

            <el-col :span="1" style="margin-left:2px;">
                 <el-button type="warning" @click="prescriptionStatus='',patientName='',paheNum=1,pageSize=5">清空</el-button>
            </el-col>
            
        </el-row>
        <!-- 
            el-table数据表格
            :data 动态绑定 data中对象数组
            scope.row 表示对象数组的当前行对象
         -->
        <el-table
            :data="prescriptionList"
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
                width="70">
            </el-table-column>

            <el-table-column
                prop="no"
                label="病人编号"
                width="120">
            </el-table-column>

            <el-table-column
                prop="doctorName"
                label="主治医生"
                width="80">
            </el-table-column>

            <el-table-column
                prop="drugName"
                label="药品"
                width="120">
            </el-table-column>

            <el-table-column
                prop="drugTypeName"
                label="类型"
                width="70">
            </el-table-column>

            <el-table-column
                prop="specificationsName"
                label="规格"
                width="120">
            </el-table-column>

            <el-table-column
                prop="num"
                label="数量"
                width="50">
            </el-table-column>
            
            <el-table-column
                prop="spare1"
                label="功效"
                width="400">
            </el-table-column>

            <el-table-column
                prop="spare2"
                label="制造商"
                width="250">
            </el-table-column>

            <el-table-column
                prop="createTime"
                label="创建时间"
                width="160">
            </el-table-column>

            <el-table-column
                prop="prescriptionStatus"
                label="处方状态"
                width="80">
                <template slot-scope="scope">
                <span v-if="scope.row.prescriptionStatus=='1'">
                  <el-tag type="danger">待审核</el-tag>
                  </span>
                <span v-if="scope.row.prescriptionStatus=='2'">
                  <el-tag type="warning">待发药</el-tag>
                  </span>
                <span v-if="scope.row.prescriptionStatus=='3'">
                  <el-tag type="success">待执行</el-tag>
                  </span>
                <span v-if="scope.row.prescriptionStatus=='4'">
                  <el-tag>已执行</el-tag>
                  </span>
                <span v-if="scope.row.prescriptionStatus=='5'">
                  <el-tag type="info">已退药</el-tag>
                  </span>
                </template>

            </el-table-column>

            <!-- <el-table-column
                label="头像"
                width="100">
                 <template slot-scope="scope">
                    <img  :src="scope.row.avatar" class="a-avatar" >
                </template>
            </el-table-column> -->
            
            <el-table-column label="操作">
               <template slot-scope="scope">
                  <!-- <el-button size="mini" type="primary" @click="gotoAddDrugOut(scope.row.id)">退药</el-button> -->
                  <el-button  v-if='scope.row.prescriptionStatus==4'
                  size="mini" type="danger" @click="gotoAddDrugOut(
                      scope.row.id,
                      scope.row.patientName,
                      scope.row.doctorName,
                      scope.row.patientId,
                      scope.row.doctorId,
                      scope.row.drugName,
                      scope.row.drugId,
                      scope.row.drugTypeName,
                      scope.row.specificationsName,
                      scope.row.num,
                      scope.row.spare2,
                      scope.row.prescriptionStatus
                      )">申请退药</el-button>
                  <el-button v-if='scope.row.prescriptionStatus ==1'
                  size="mini" type="danger" @click="gotoStopPrescription( scope.row.id)">撤 销</el-button>
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

        <!-- 申请退药 -->
    <el-dialog :visible.sync="addDrugOutForm">
      <h1 align="center">申请退药</h1>
      <br />
      <template>
        <el-descriptions class="margin-top" title="" :column="3" border>
          <el-descriptions-item>
            <template slot="label"><i class="el-icon-user"></i>病人</template>
            {{ addDrugOut.patientName }}
          </el-descriptions-item>

          <el-descriptions-item>
            <template slot="label"><i class="el-icon-user"></i>主治医生</template>
            {{ addDrugOut.doctorName }}
          </el-descriptions-item>

          <el-descriptions-item>
            <template slot="label"><i class="el-icon-user"></i>制造商</template>
            {{ addDrugOut.spare2 }}
          </el-descriptions-item>

          <el-descriptions-item>
            <template slot="label"><i class="el-icon-user"></i>药品</template>
            {{ addDrugOut.drugName }}
          </el-descriptions-item>

          <el-descriptions-item>
            <template slot="label"><i class="el-icon-user"></i>类型</template>
            {{ addDrugOut.drugTypeName }}
          </el-descriptions-item>

          <el-descriptions-item>
            <template slot="label"><i class="el-icon-user"></i>规格</template>
            {{ addDrugOut.specificationsName }}
          </el-descriptions-item>

        
          <el-descriptions-item>
            <template slot="label"><i class="el-icon-tickets"></i>状态</template>
            {{ addDrugOut.prescriptionStatus ===1?'待审核': 
               addDrugOut.prescriptionStatus ===2?'待发药':
               addDrugOut.prescriptionStatus ===3?'已发药':
               addDrugOut.prescriptionStatus ===4?'已执行':'已退药' }}
          </el-descriptions-item>

        </el-descriptions>
      </template>

      <el-divider></el-divider>

      <el-form :model="addDrugOut" :rules="rules" ref="addDrugOut">

          <el-form-item label="药品" :label-width="formLabelWidth" prop="drugName">
            <el-input v-model="addDrugOut.drugName" autocomplete="off" readonly="readonly"></el-input>
        </el-form-item>

        <el-form-item label="数量" :label-width="formLabelWidth" prop="num">
          <el-input-number v-model="addDrugOut.num" @change="handleChange" :min="1" :max="addDrugOut.num" label="描述文字"></el-input-number>
        </el-form-item>

        <el-form-item label="退药原因" :label-width="formLabelWidth" prop="outCause">
            <el-input v-model="addDrugOut.outCause" autocomplete="off" ></el-input>
        </el-form-item>

      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="doAddDrugOut('addDrugOut')">退 药</el-button>
        <el-button @click="addDrugOutForm = false;addDrugOut={}">取 消</el-button>
      </div>
    </el-dialog>

    </div>
</template>





<script>

import qs from 'qs'

export default {
   data() {
      return {
        prescriptionStatus:'',
        prescriptionList: [],
        total:100,
        pageNum:1,
        pageSize:5,
        headers:{},
        formLabelWidth: '120px',
        addDrugOutForm: false,
        addDrugOut:{
          num:'',
          outCause:'',
        },
         rules: {
          num: [
            { required: true, message: '请输入药品数量', trigger: 'blur' },
            // { min: 3, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur' }
          ],
          outCause: [
            { required: true, message: '请输入退药原因', trigger: 'blur' },
            // { min: 3, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur' }
          ]
        },
      }
   },
   created(){
      this.search();
      this.headers={tokenStr:window.localStorage.getItem('tokenStr')};
   },
   methods:{
      doAddDrugOut(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            var DrugOut=this.addDrugOut;
           console.log(DrugOut)
            this.$axios.post("/api/doctor/gotoAddDrugOut",DrugOut)
            .then(res=>{
                if(res.status==4001){
                     this.$message({
                        type: "error",
                         message: "没有权限!",
                          duration:2000
                     });
                }else{
                     this.$message({
                        type: "success",
                         message: "下达处方成功!",
                         duration:2000
                     });
                    this.closeAddDrugOutForm();
                    this.search();
                }
            })
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },    


         //撤销处方
    gotoStopPrescription(id){
           this.$confirm('确定要撤销吗?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
            }).then(() => {
                this.$axios.post("/api/doctor/gotoStopPrescription",qs.stringify({'id':id}),{
            params: { id: id }
            }).then(res=>{
                    if(res.status==4001){
                            this.$message({
                            type: "error",
                            message: "没有权限!",
                             duration:2000
                        });
                     }else{
                        this.$message({
                            type: 'success',
                            message: '撤销成功!',
                            duration:2000
                        });
                        this.search();
                     }
                })
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消操作',
                     duration:2000
                });          
            });
       },

       //药品数量
       handleChange(value) {
        console.log(value);
      },

       //关闭申请退药窗口
       closeAddDrugOutForm(){
            this.addDrugOut={
                id:'',
                patientName: '',
                doctorName: '',
                patientId: '',
                doctorId: '',
                drugName: '',
                drugId: '',
                drugTypeName: '',
                specificationsName: '',
                num: '',
                spare2: '',
                prescriptionStatus: ''
                };
            this.addDrugOutForm=false;
       },
    
    //执行申请退药
      //  doAddDrugOut(){
      //      var DrugOut=this.addDrugOut;
      //      console.log(DrugOut)
      //       this.$axios.post("/api/doctor/gotoAddDrugOut",DrugOut)
      //       .then(res=>{
      //           if(res.status==4001){
      //                this.$message({
      //                   type: "error",
      //                    message: "没有权限!",
      //                     duration:2000
      //                });
      //           }else{
      //                this.$message({
      //                   type: "success",
      //                    message: "下达处方成功!",
      //                    duration:2000
      //                });
      //               this.closeAddDrugOutForm();
      //               this.search();
      //           }
      //       })
      //  },
       //准备申请退药
       gotoAddDrugOut(id,patientName,doctorName,patientId,doctorId,drugName,drugId,drugTypeName,
            specificationsName,num,spare2,prescriptionStatus){
           this.addDrugOut={
                id:id,
                patientName: patientName,
                doctorName: doctorName,
                patientId: patientId,
                doctorId: doctorId,
                drugName: drugName,
                drugId: drugId,
                drugTypeName: drugTypeName,
                specificationsName: specificationsName,
                num: num,
                spare2:spare2,
                prescriptionStatus: prescriptionStatus
           };
           this.addDrugOutForm=true;
       },

      //查询处方列表
        search(){
            console.log("---")
            this.$axios.get("/api/doctor/getPrescriptionList",{params:{
            prescriptionStatus:this.prescriptionStatus,
            pageNum:this.pageNum,
            pageSize:this.pageSize
            }})
            .then(res=>{
                 console.log("=========================");
               console.log(res.data.data.list);
                this.prescriptionList=res.data.data.list;
                this.total=res.data.data.total;
                this.pageNum=res.data.data.pageNum;
                this.pageSize=res.data.data.pageSize;

            })
        },
        changeSize(value){
            this.pageSize=value;
            // this.pageNum=1;
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
