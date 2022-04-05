<template>
    <div class="divClass">
        <!-- 添加查询搜索栏 -->
        <el-row >
            <el-col :span="7">
                <el-input placeholder="请输入退药医生姓名" v-model="doctorName" class="input-with-select" clearable style="width:200px;margin-left:9px"></el-input>
            </el-col>
            <el-col :span="7">
                <el-input placeholder="请输入患者姓名" v-model="preName" class="input-with-select" clearable style="width:200px;margin-left:-280px"></el-input>
            </el-col>
            <el-col :span="3" style="margin-left:-567px">
            <el-button type="primary" @click="createMethods()">查询<i class="el-icon-search el-icon--right"></i></el-button>
            <el-button  type="warning" @click="batchOutDrug">批量退药</el-button>
            </el-col>
        </el-row>

        <!-- 退药列表 -->
        <el-table
            ref="multipleTable"
            :data="drugPreData"
            tooltip-effect="dark"
            style="width: 100%"
            @selection-change="handleSelectionChange">
            <el-table-column type="selection" ></el-table-column>
            <el-table-column type="index" :index="indexMethod" label="序号"></el-table-column>
            <el-table-column label="退药医生"  prop="doctorName"></el-table-column>
            <el-table-column label="患者姓名"  prop="preName"></el-table-column>
            <el-table-column label="创建时间"  prop="ctime"></el-table-column>
            <el-table-column label="申请状态"  prop="ostatus">
                <template slot-scope="scope">
                    <el-tag v-if="scope.row.ostatus == 2" type="danger"
                        disable-transitions>待退药</el-tag>
                </template>
            </el-table-column>
            <el-table-column label="退药原因"  prop="ocause"></el-table-column>
            <el-table-column label="操作" >
                <template slot-scope="scope">
                    <el-button size="mini" type="primary" @click="gotoPre(scope.row.did)">退药详情</el-button>
                </template>
            </el-table-column>
        </el-table>

        <!-- 退药详情模态框 -->
            <el-dialog title="药品名称和数量" :visible.sync="updatedialogTableVisible" center>               
                  <el-table :data="drugND" style="width: 100%">
                      <el-table-column prop="drugName" label="药品名称" ></el-table-column>
                      <el-table-column prop="num" label="退药数量"></el-table-column>
                  </el-table>

                <div slot="footer" class="dialog-footer">
                    <el-button @click="updatedialogTableVisible=false;drugND=[];did='';">取消</el-button>
                    <el-button type="primary" @click="outDrug">退药</el-button>
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
        faileDrugName:[],   //药品库存不足的药品名字
        stockTableVisible:false,    //药品库存不足模态框控件

        doctorName:'',
        preName:'',
        did:'',
      }
   },
   methods:{
    //单个退药操作
    outDrug(){
        let Str = []
        this.drugND.forEach(item=>{
            Str.push(item.ddid+"-"+item.num)
        })
        let durgStr = Str.join(",")
        this.$axios.post("api/drug/single/outDrug",qs.stringify({'durgStr':durgStr,'account':window.localStorage.getItem("account"),'did':this.did})).then(res=>{
            if (res.data.status == 200) {
                this.$message({
                type: 'success',
                message: '操作成功!',
                duration:2000
                });
            this.createMethods()
            this.drugND=[]
            this.updatedialogTableVisible=false
            this.did=''
            }else{
                this.$message({
                    message: '很抱歉,'+res.data.msg,
                    type: 'warning',
                    duration:2000
                });
                this.createMethods()
                this.drugND=[]
                this.updatedialogTableVisible=false
                this.did=''
            }
        })
        
    },
    //序号
    indexMethod(index){
        return (index +1)+this.pageSize*(this.pageNum-1)
    },
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
    //多个药方批量退药
    batchOutDrug() {
        if (this.multipleSelection == '') {
            this.$message({
                message: '请至少选择一例申请',
                type: 'warning',
                duration:2000
            });
        }else{
        let idList = []
        this.multipleSelection.forEach(item=>{
            idList.push(item.did)
        })
        let idStr = idList.join(",")
        this.$confirm('请仔细察看每个退药信息,是否退药', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
        }).then(() => {
            this.$axios.post("api/drug/outDrug",qs.stringify({'idStrs':idStr,'account':window.localStorage.getItem("account")})).then(res=>{
                if (res.data.status == 200) {
                    this.$message({
                    type: 'success',
                    message: '操作成功!',
                    duration:2000
                    });
                this.createMethods();
                }else{
                    this.$message({
                    showClose: true,
                    message: '很抱歉,'+res.data.msg,
                    type: 'warning',
                    duration:2000
                    });
                this.createMethods();
                }
            })
        })
        }
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
    },
    //进入退药
    gotoPre(id){
        this.did=id
        //通过退药申请id查找该退掉的所有药品及数量
        this.$axios.get("api/drug/outDrug?pid="+id).then(res=>{
            if (res.data.status == 200) {
                this.drugND=res.data.data;
            }
        })
        this.updatedialogTableVisible=true
    },
    //加载方法
    createMethods(pageNum = 1){
        this.$axios.get("api/drug/outDrugList",{
            params:{
                doctorName:this.doctorName,
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
.divClass{
    margin-top: 10px;
    margin-bottom: 15px;
    margin-left: 5px;
    margin-right: 15px;
}

</style>
