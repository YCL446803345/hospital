<template>
    <div>
        <!-- 面包屑导航 -->
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/gotoHome' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item :to="{path: '/gotoHome/medicine/home'}">药品信息</el-breadcrumb-item>
            <el-breadcrumb-item>药品详情</el-breadcrumb-item>
        </el-breadcrumb>

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
