<template>
    <div>
        <el-breadcrumb separator="/">
            <el-breadcrumb-item :to="{ path: '/gotoHome'}">首页</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path: '/drug/send/drug'}">退药记录</el-breadcrumb-item>
        </el-breadcrumb>

        <el-row style="margin-top:10px">
            <el-col :span="8" >
                <!-- 带查询查询文本框 -->
                <el-input placeholder="请输入配药师姓名" v-model="name" clearable class="input-with-select"></el-input>
            </el-col>
            <el-col :span="8"  style="margin-left:10px">
                <el-button  icon="el-icon-search" @click="createMethods()"></el-button>
            </el-col>
        </el-row>
        
            <!-- 退药记录列表 -->
            <el-table :data="preData" style="width: 100%">
                <el-table-column type="index" :index="indexMethod" label="序号"></el-table-column>

                <el-table-column prop="doctorName" label="申请医生" width="180"></el-table-column>
                <el-table-column prop="preName" label="患者姓名" width="180"></el-table-column>
                <el-table-column prop="outTime" label="退药时间" width="180"></el-table-column>
                <el-table-column prop="outCause" label="退药原因" width="180"></el-table-column>
                <el-table-column prop="outName" label="退药人" width="120"></el-table-column>
                <el-table-column label="状态"  prop="spare1">
                    <template slot-scope="scope">
                        <el-tag v-if="scope.row.spare1 == '1'" type="danger"
                            disable-transitions>已退药</el-tag>
                    </template>
                </el-table-column>
                
                <el-table-column label="操作" align="center">
                    <template slot-scope="scope">
                        <el-button size="mini" type="info" @click="lookDrug(scope.row.drugOutId)">退药信息</el-button>
                    </template>
                </el-table-column>
            </el-table>

            <!-- 药方详情模态框 -->
            <el-dialog title="退药详情" :visible.sync="updatedialogTableVisible">
                <!-- 药品名字和数量 -->
                <el-collapse accordion>
                    <el-collapse-item>
                        <el-table :data="drugNameAndNum" style="width: 100%">
                            <el-table-column prop="drugName" label="药品名称" ></el-table-column>
                            <el-table-column prop="num" label="已退药数量" ></el-table-column>
                        </el-table>
                    </el-collapse-item>
                </el-collapse>

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
           this.$axios.get("api/drug/findAllOutDrugRecord",{
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
    //查看退药方信息
    lookDrug(id){
        //查询该处方下的药品和数量
        this.$axios.get("api/drug/getDrugByOutDrugId?pid="+id).then(res=>{
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

</style>
