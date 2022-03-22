<template>
    <div>
        <!-- 面包屑导航 -->
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/gotoHome' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item :to="{path: '/gotoHome/medicine/home'}">药品信息</el-breadcrumb-item>
            <el-breadcrumb-item>药品详情</el-breadcrumb-item>
        </el-breadcrumb>

        <!-- 查询列表 -->
        <!-- <el-row style="margin-top:10px">
            <el-col :span="8" >
                <el-input placeholder="请输入药品名称" v-model="name" class="input-with-select"></el-input>
            </el-col>
            <el-col :span="4">
            <el-select v-model="subjectid" clearable placeholder="请选择" >
                <el-option v-for="sub in subjets" :key="sub.id" :label="sub.name" :value="sub.id"></el-option>
            </el-select>
            </el-col>
            <el-col :span="10"  style="margin-left:10px">
                <el-button  icon="el-icon-search" @click="queryTeacher()"></el-button>
                <el-button type="primary" >清除条件</el-button>
                <el-button type="success" >添加讲师</el-button>
            </el-col>
        </el-row> -->
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
