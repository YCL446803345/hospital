<template>
    <div>
        <!-- 面包屑导航 -->
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/gotoHome' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item :to="{path: '/gotoHome/medicine/home'}">药品信息</el-breadcrumb-item>
            <el-breadcrumb-item>药品详情</el-breadcrumb-item>
        </el-breadcrumb>

        <!--  查询 -->
        <el-row style="margin-top:10px">
            <el-col :span="8" >
                <!-- 带查询查询文本框 -->
                <el-input placeholder="请输入药品名称" v-model="name" class="input-with-select"></el-input>
            </el-col>
            <el-col :span="8"  style="margin-left:10px">
                <el-button  icon="el-icon-search" @click="queryConsumer()"></el-button>
                <el-button type="primary" @click="account='';" >清除条件</el-button>
                <el-button type="success">添加用户</el-button>
            </el-col>
        </el-row>
        
        <!-- 顾客列表 -->
        <el-table :data="consumerData" style="width: 100%">
            <el-table-column type="index" :index="indexMethod" label="序号"></el-table-column>

            <el-table-column label="账户名称" width="180">
            <template slot-scope="scope">
                <el-popover trigger="hover" placement="top">
                <p>邮箱: {{ scope.row.email }}</p>
                <p>电话: {{ scope.row.telphone }}</p>
                <div slot="reference" class="name-wrapper">
                    <el-tag size="medium">{{ scope.row.account }}</el-tag>
                </div>
                </el-popover>
            </template>
            </el-table-column>
            
            <el-table-column prop="avatar" label="头像" width="100" align="center"></el-table-column>
            <el-table-column label="注册时间" width="180" prop="regtime" align="center"></el-table-column>
            <el-table-column label="状态" width="180" align="center">
                <template slot-scope="scope">
                    <el-tag type="primary" effect="dark" v-if="scope.row.status == '0'" disable-transitions>已激活</el-tag>
                    <el-tag type="info" effect="dark" v-if="scope.row.status == '1'" disable-transitions>未激活</el-tag>
                    <el-tag type="danger"   effect="dark"  v-if="scope.row.status == '2'" disable-transitions>已锁定</el-tag>
                </template>
            </el-table-column>

            <el-table-column label="操作" align="center">
            <!-- <template slot-scope="scope"> -->
                <el-button size="mini" type="warning" >下架</el-button>
            <!-- </template> -->
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
        
      }
   },
   created(){

   },
   methods:{

   }
}
</script>

<style>

</style>
