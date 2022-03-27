<template>
    <div>
    <el-tabs v-model="activeName" type="card" @tab-click="handleClick">
        <el-tab-pane name="first"><span slot="label"><i class="el-icon-tickets"></i>发药列表</span><sendDrugList/></el-tab-pane>
        <el-tab-pane name="second"><span slot="label"><i class="el-icon-tickets"></i>退药列表</span></el-tab-pane>
    </el-tabs>
    </div>
</template>

<script>
import sendDrugList from '../drugHome/sendDrugList.vue'
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
   components:{
        sendDrugList
   },
    created(){
        
   }
}
</script>

<style>

</style>
