<template>
    <div>
        <!-- 添加查询搜索栏 -->
        <el-row>
            <el-col :span="7">
                <el-input placeholder="请输入退药医生姓名" v-model="doctorName" class="input-with-select" clearable></el-input>
            </el-col>
            <el-col :span="7">
                <el-input placeholder="请输入审核护士姓名" v-model="nurseName" class="input-with-select" clearable></el-input>
            </el-col>
            <el-col :span="7">
                <el-input placeholder="请输入患者姓名" v-model="preName" class="input-with-select" clearable></el-input>
            </el-col>
            <el-col :span="3">
            <el-button  icon="el-icon-search" @click="createMethods()"></el-button>
            <el-button size="mini" type="primary" @click="batchSendDrug">批量退药</el-button>
            </el-col>
        </el-row>
        <el-table
            ref="multipleTable"
            :data="drugPreData"
            tooltip-effect="dark"
            style="width: 100%"
            @selection-change="handleSelectionChange">

            <el-table-column type="selection" ></el-table-column>
            <el-table-column label="退药医生"  prop="doctorName"></el-table-column>
            <el-table-column label="患者姓名"  prop="paName"></el-table-column>
            <el-table-column label="退药时间"  prop="createTime"></el-table-column>
            <el-table-column label="药方状态"  prop="prescriptionStatus">
                <template slot-scope="scope">
                    <el-tag v-if="scope.row.prescriptionStatus == 1" type="danger"
                        disable-transitions>待退药</el-tag>
                </template>
            </el-table-column>
            <el-table-column label="操作" >
                <template slot-scope="scope">
                    <el-button size="mini" type="primary" @click="gotoPre(scope.row)">药方详情</el-button>
                </template>
            </el-table-column>
        </el-table>

        <!-- 药方详情模态框 -->
            <el-dialog title="药方信息" :visible.sync="updatedialogTableVisible">

                <el-descriptions  direction="vertical" :column="4" border>
                <el-descriptions-item label="处方编号">{{drugIDN.id}}</el-descriptions-item>
                <el-descriptions-item label="退药医生">{{drugIDN.doctorname}}</el-descriptions-item>
                <el-descriptions-item label="审核护士" :span="2">{{drugIDN.nursename}}</el-descriptions-item>
                </el-descriptions>
                <!-- 药品名字和数量 -->
                  <el-table :data="drugND" border style="width: 100%">
                      <el-table-column prop="drugName" label="药品名称" width="180"></el-table-column>
                      <el-table-column prop="num" label="退药数量" width="180"></el-table-column>
                  </el-table>
                <div slot="footer" class="dialog-footer">
                    <el-button @click="updatedialogTableVisible=false;drugIDN={};drugND=[];">取消</el-button>
                    <el-button type="primary" @click="sendDrug">退药</el-button>
                </div>
            </el-dialog>

        <!-- 分页 -->
        <el-pagination 
        :current-page="pageNum"
        :page-size="pageSize"
        background
        layout="prev, pager, next,sizes, total"
        :page-sizes="[5,15,20,25]"
        :total="total"
        @current-change="changePage"
        @size-change="handleSizeChange">
        </el-pagination>
    </div>
</template>

<script>
import qs from 'qs'
export default {
   data() {
      return {
        //分页属性
        pageNum:1,  //当前页
        total:0,  //总页数
        pageSize:5,  //页尺寸

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

        doctorName:'',
        nurseName:'',
        preName:'',
      }
   },
   methods:{
    //翻页事件
    changePage(value){
        this.createMethods(value)
    },
    //页尺寸改变事件
    handleSizeChange(value){
        this.pageSize=value
        //加载改变页尺寸大小和当前页后的列表
        this.createMethods()
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
    //加载方法
    createMethods(pageNum = 1){
        this.$axios.get("api/drug/prescription",{
            params:{
                doctorName:this.doctorName,
                nurseName:this.nurseName,
                preName:this.preName,
                pageNum:pageNum,
                pageSize:this.pageSize
            }
        }).then(res=>{
            if (res.data.status == 200) {
                this.drugPreData=res.data.data.list;
                this.total=res.data.data.total
                this.pageNum=res.data.data.pageNum
                this.pageSize=res.data.data.pageSize
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
