<template>
    <div>
    <el-tabs v-model="activeName" type="card" @tab-click="handleClick">
        <el-tab-pane name="first"><span slot="label"><i class="el-icon-tickets"></i>发药记录</span><SendDrugRecord/></el-tab-pane>
        <el-tab-pane name="second"><span slot="label"><i class="el-icon-tickets"></i>退药记录</span><OutDrugRecord/></el-tab-pane>
    </el-tabs>
    </div>
</template>

<script>
import SendDrugRecord from './SendDrugRecord.vue'
import OutDrugRecord from './OutDrugRecord.vue'
export default {
   data() {
      return {
        activeName: 'first',//默认显示tabs页第一页
        first:true,
        second:false
      }
   },
   methods:{
    handleClick(tab) {
      if (tab.name === 'first') {
        this.first = true
        this.second = false
      } else if (tab.name === 'second') {
        this.first = false
        this.second = true
      }
    }
   },
    created(){

   },
   components:{
        SendDrugRecord,
        OutDrugRecord
   }
}
</script>

<style>

</style>
