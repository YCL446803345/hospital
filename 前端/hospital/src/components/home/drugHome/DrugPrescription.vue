<template>
    <div>
        <el-table
            ref="multipleTable"
            :data="drugPreData"
            tooltip-effect="dark"
            style="width: 100%"
            @selection-change="handleSelectionChange">

            <el-table-column type="selection" width="55"></el-table-column>
            <el-table-column label="开方医生" width="120" prop="doctorName"></el-table-column>
            <el-table-column label="患者姓名" width="120" prop="paName"></el-table-column>
            <el-table-column label="开方时间" width="120" prop="createTime"></el-table-column>
            <el-table-column label="药方状态" width="120" prop="prescriptionStatus">
                <template slot-scope="scope">
                    <el-tag v-if="scope.row.prescriptionStatus == 1" type="danger"
                        disable-transitions>待发药</el-tag>
                </template>
            </el-table-column>
            <el-table-column label="操作" width="120">
                <template slot-scope="scope">
                    <el-button size="mini" type="primary" @click="gotoPre(scope.row)">药方详情</el-button>
                    <el-button size="mini" type="danger" @click="closeDrugPre(scope.row.id)">撤销药方</el-button>
                </template>
            </el-table-column>
        </el-table>
        <el-button size="mini" type="primary" @click="batchSendDrug">批量发药</el-button>

        <!-- 药方详情模态框 -->
            <el-dialog title="药方信息" :visible.sync="updatedialogTableVisible">

                <el-descriptions  direction="vertical" :column="4" border>
                <el-descriptions-item label="处方编号">{{drugIDN.id}}</el-descriptions-item>
                <el-descriptions-item label="开药医生">{{drugIDN.doctorname}}</el-descriptions-item>
                <el-descriptions-item label="审核护士" :span="2">{{drugIDN.nursename}}</el-descriptions-item>
                </el-descriptions>
                <!-- 药品名字和数量 -->
                  <el-table :data="drugND" border style="width: 100%">
                      <el-table-column prop="drugName" label="药品名称" width="180"></el-table-column>
                      <el-table-column prop="num" label="申请数量" width="180"></el-table-column>
                  </el-table>
                <div slot="footer" class="dialog-footer">
                    <el-button @click="updatedialogTableVisible=false;drugIDN={};drugND=[];">取消</el-button>
                    <el-button type="primary" @click="sendDrug">发药</el-button>
                </div>
            </el-dialog>

        <!-- 药品库存不足模特框 -->
            <el-dialog title="库存不足,请采购" :visible.sync="stockTableVisible">
                <span>药品名称</span>
                <el-descriptions   :column="1" border>
                <el-descriptions-item  v-for="name in faileDrugName" :key="name">{{name}}</el-descriptions-item>
                </el-descriptions>
                <div slot="footer" class="dialog-footer">
                    <el-button @click="stockTableVisible=false;faileDrugName=[];">取消</el-button>
                </div>
            </el-dialog>
    </div>
</template>

<script>
import qs from 'qs'
export default {
   data() {
      return {
        drugPreData:[],    //药方列表对象
        multipleSelection: [],  //选中的数组
        updatedialogTableVisible:false,   //药方详情模特框切换
        drugND:[],   //药方详情中的名字和数量
        drugIDN:{    //药方详情中的处方id开药医生审核护士
            id:'',
            doctorname:'',
            nursename:''
        },
        faileDrugName:[],   //药品库存不足的药品名字
        stockTableVisible:false,    //药品库存不足模态框控件
      }
   },
   methods:{
    //   用于提示哪些药品库存不足 
    open() {
        this.stockTableVisible=true;
      },
    //撤销药方
    closeDrugPre(id){
            this.$confirm('是否确定撤销该药方', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
        }).then(() => {
                    this.$axios.post("api/drug/updatePreStatus",qs.stringify({'pid':id,'account':window.localStorage.getItem("account")})).then(res=>{
            if (res.data.status == 200) {
                    this.$message({
                    showClose: true,
                    message: '撤销成功',
                    type: 'success',
                    duration:1500
                    });
                this.updatedialogTableVisible=false;
                this.createMethods();
            }else{
                this.$message({
                    showClose: true,
                    message: '撤销失败, 系统维护中',
                    type: 'warning',
                    duration:1500
                });
                this.createMethods();
                this.updatedialogTableVisible=false;
            }
        })
        })
    },
    //多个药方批量发药
    batchSendDrug() {
        if (this.multipleSelection == '') {
            this.$message({
                showClose: true,
                message: '请至少选择一个处方',
                type: 'warning',
                duration:1500
            });
        }else{
        let idList = []
        this.multipleSelection.forEach(item=>{
            idList.push(item.id)
        })
        let idStr = idList.join(",")
        this.$confirm('请仔细察看每个处方信息,是否批量发药', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
        }).then(() => {
            this.$axios.post("api/drug/sendDrug",qs.stringify({'idStrs':idStr,'account':window.localStorage.getItem("account")})).then(res=>{
                if (res.data.status == 200) {
                    if (res.data.data.length != 0) {
                        this.faileDrugName=res.data.data
                        this.open();
                        this.createMethods();
                    }else{
                    this.$message({
                    type: 'success',
                    message: '发药成功!'
                    });
                    this.createMethods();
                    }
                    this.createMethods();
                }else{
                    this.$message({
                    showClose: true,
                    message: '发药失败, 系统维护中',
                    type: 'warning',
                    duration:1500
                    });
                    this.createMethods();
                }
            })
        })
        }
    },
    //单个处方配药
    sendDrug(){
        let idStr = this.drugIDN.id+","
        this.$axios.post("api/drug/sendDrug",qs.stringify({'idStrs':idStr,'account':window.localStorage.getItem("account")})).then(res=>{
         if (res.data.status == 200) {
                    if (res.data.data.length != 0) {
                        this.faileDrugName=res.data.data
                        this.open();
                        this.createMethods();
                        this.updatedialogTableVisible=false;
                    }else{
                    this.$message({
                    type: 'success',
                    message: '发药成功!'
                    });
                    this.createMethods();
                    this.updatedialogTableVisible=false;
                    }
            }else{
                this.$message({
                    showClose: true,
                    message: '发药失败, 系统维护中',
                    type: 'warning',
                    duration:1500
                });
                this.createMethods();
                this.updatedialogTableVisible=false;
            }
        })
    },
    // 控制左侧动态导航栏
    toggleSelection(rows) {
        if (rows) {
          rows.forEach(row => {
            this.$refs.multipleTable.toggleRowSelection(row);
          });
        } else {
          this.$refs.multipleTable.clearSelection();
        }
      },
    handleSelectionChange(val) {
        this.multipleSelection = val;
        console.log(this.multipleSelection)
    },
    //进入药方详情
    gotoPre(row){
        this.drugIDN.id=row.id
        this.drugIDN.doctorname=row.doctorName
        this.drugIDN.nursename=row.nurseName
        //通过处方id查找该处方下所有药品及数量
        this.$axios.get("api/drug/getDrugByPreId?pid="+row.id).then(res=>{
            if (res.data.status == 200) {
                this.drugND=res.data.data;
            }
        })
        this.updatedialogTableVisible=true
    },
    //页数加载方法
    createMethods(){
        this.$axios.get("api/drug/prescription").then(res=>{
            if (res.data.status == 200) {
                this.drugPreData=res.data.data.list;
            }
        })  
    }
   },
    created(){
        this.createMethods();
   }
}
</script>

<style>

</style>
