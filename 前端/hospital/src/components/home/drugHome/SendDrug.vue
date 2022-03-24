<template>
    <div>
        <el-breadcrumb separator="/">
            <el-breadcrumb-item :to="{ path: '/gotoHome'}">首页</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path: '/drug/send/drug'}">发药记录</el-breadcrumb-item>
            <el-breadcrumb-item>退药记录</el-breadcrumb-item>
        </el-breadcrumb>

        <el-row style="margin-top:10px">
            <el-col :span="8" >
                <!-- 带查询查询文本框 -->
                <el-input placeholder="请输入配药师姓名" v-model="name" class="input-with-select"></el-input>
            </el-col>
            <el-col :span="8"  style="margin-left:10px">
                <el-button  icon="el-icon-search" @click="createMethods()"></el-button>
            </el-col>
        </el-row>
        
            <!-- 发药列表 -->
            <el-table :data="preData" style="width: 100%">
                <el-table-column type="index" :index="indexMethod" label="序号"></el-table-column>
                <el-table-column prop="prescriptionId" label="处方编号" width="100"></el-table-column>
                <el-table-column prop="spare1" label="配药医师" width="120"></el-table-column>
                <el-table-column prop="sendDrugTime" label="发药时间" width="180"></el-table-column>

                <el-table-column label="操作" align="center">
                    <template slot-scope="scope">
                        <el-button size="mini" @click="lookDrug(scope.row)">处方信息</el-button>
                    </template>
                </el-table-column>
            </el-table>

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
        preData:[]
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
               }
           })
       },
    //序号
    indexMethod(index){
        return (index +1)+this.pageSize*(this.pageNum-1)
    },
    //查看处方信息
    lookDrug(row){
        this.$axios.get("api/")
    }
   },
    created(){
        this.createMethods();
   },
}
</script>

<style>

</style>
