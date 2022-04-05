<template>
    <div class="divClass">
        <el-row >
            <el-col :span="8" >
                <!-- 带查询查询文本框 -->
                <el-input placeholder="请输入配药师姓名" v-model="name" class="input-with-select" style="width:200px;margin-left:9px"></el-input>
            </el-col>
            <el-col :span="8"  style="margin-left:-351px">
                <el-button type="primary"  @click="createMethods()">查询<i class="el-icon-search el-icon--right"></i></el-button>
            </el-col>
        </el-row>
        
            <!-- 发药记录列表 -->
            <el-table :data="preData" style="width: 100%"  tooltip-effect="dark">
                <el-table-column type="index" :index="indexMethod" label="序号"></el-table-column>
                <el-table-column prop="doctorName" label="申请医生"></el-table-column>
                <el-table-column prop="preName" label="患者姓名" ></el-table-column>
                <el-table-column prop="sdTime" label="发药时间" ></el-table-column>
                <el-table-column prop="spare1" label="发药人" ></el-table-column>
                <el-table-column label="状态"  prop="spare2">
                    <template slot-scope="scope">
                        <el-tag v-if="scope.row.spare2 == '1'" type="danger"
                            disable-transitions>已发药</el-tag>
                    </template>
                </el-table-column>
                <el-table-column label="操作" align="center">
                    <template slot-scope="scope">
                        <el-button size="mini" type="info" @click="lookDrug(scope.row.pid)">处方信息</el-button>
                    </template>
                </el-table-column>
            </el-table>

            <!-- 药方详情模态框 -->
            <el-dialog title="药品信息" :visible.sync="updatedialogTableVisible" center>

                <el-table :data="drugNameAndNum" style="width: 100%">
                      <el-table-column prop="drugName" label="药品名称"></el-table-column>
                      <el-table-column prop="num" label="退药数量"></el-table-column>
                </el-table>
                
                <div slot="footer" class="dialog-footer">
                    <el-button @click="updatedialogTableVisible=false;drugNameAndNum=[];">返回</el-button>
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
export default {
   data() {
      return {
        //分页属性
        pageNum:1,  //当前页
        total:0,  //总页数
        pageSize:5,  //页尺寸
        name:'',
        preData:[],
        drugNameAndNum:[],
        updatedialogTableVisible:false,    //药方详情模态框
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
       //钩子方法
       createMethods(pageNum = 1){
           this.$axios.get("api/drug/findAllSendDrug",{
                params:{
                   name:this.name,
                   pageNum:pageNum,
                   pageSize:this.pageSize
               }
           }).then(res=>{
               if (res.data.status == 200) {
                this.preData=res.data.data.list
                this.total=res.data.data.total
                this.pageNum=res.data.data.pageNum
                this.pageSize=res.data.data.pageSize
               }
           })
       },
    //序号
    indexMethod(index){
        return (index +1)+this.pageSize*(this.pageNum-1)
    },
    //查看处方信息
    lookDrug(id){
        //查询该处方下的药品和数量
        this.$axios.get("api/drug/getDrugByPreId?pid="+id).then(res=>{
            if (res.data.status == 200) {
                this.drugNameAndNum=res.data.data
            }
        })
        this.updatedialogTableVisible=true
    }
   },
    created(){
        this.createMethods();
   },
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
