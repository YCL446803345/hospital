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
                        disable-transitions>待配药</el-tag>
                </template>
            </el-table-column>
            <el-table-column label="操作" width="120">
                <template slot-scope="scope">
                    <el-button size="mini" type="primary" @click="gotoPre(scope.row)">药方详情</el-button>
                    <el-button size="mini" type="danger">撤销药方</el-button>
                </template>
            </el-table-column>
            

        </el-table>

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
                    <el-button>取消</el-button>
                    <el-button type="primary">配药</el-button>
                </div>
            </el-dialog>
    </div>
</template>

<script>
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
        }
      }
   },
   methods:{
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
    }
   },
    created(){
        this.$axios.get("api/drug/prescription").then(res=>{
            if (res.data.status == 200) {
                this.drugPreData=res.data.data.list;
            }
        })
   }
}
</script>

<style>

</style>
