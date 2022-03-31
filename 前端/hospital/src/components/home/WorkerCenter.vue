<template>
    <div>
      <el-calendar v-model="value" >
            <template
                slot="dateCell"
                slot-scope="{date, data}">
                <p :class="data.isSelected ? 'is-selected' : ''">
                  {{ data.day.split('-').slice(1).join('-') }} {{ data.isSelected ? '✔️' : ''}}
                </p>
                  <el-col :span="5" v-for="Scheduling in SchedulingList" :key="Scheduling.schedulingId"  >
                  <div style="padding: 0px" align="center">
                    <el-card :body-style="{ padding: '0px' }"  shadow="hover" v-if='Scheduling.time==data.day'>
                        <div style="padding: 0px">
                          <div style="padding: 0px">
                              <i class="el-icon-timer"></i>
                               <!-- <el-tag type="success" disable-transitions> -->
                                         <!-- <template slot-scope="scope"> -->
                                            <span v-if="Scheduling.schedulingId == '1'" style="background-color:yellow">白班</span>
                                            <span v-if="Scheduling.schedulingId == '2'" style="background-color:black">夜班</span>
                                            <span v-if="Scheduling.schedulingId == '3'" style="background-color:green">轮休</span>
                                            <span v-if="Scheduling.schedulingId == '4'" style="background-color:blue">病假</span>
                                            <span v-if="Scheduling.schedulingId == '5'" style="background-color:brown">事假</span>
                                         <!-- </template> -->
                               <!-- </el-tag> -->
                              </div>
                        </div>
                          
                    </el-card>
                </div>
                </el-col>
            </template>
      </el-calendar>

    </div>
</template>

<script>
export default {
   data() {
      return {
        thisDay:'',
        SchedulingList:[],
      }
   },
   created(){
        let account = window.localStorage.getItem("account")
        this.$axios.get("/api/getMyScheduling",{params:{account:account}}).then(res=>{
            this.SchedulingList = res.data;
            console.log(this.SchedulingList);
        })
   },
   methods:{
     

   }
}
</script>

<style scoped>
.is-selected {
    color: #1989FA;
  }
</style>
