<template>
    <div>
      <el-calendar v-model="value" >
            <template
                slot="dateCell"
                slot-scope="{date, data}">
                <p :class="data.isSelected ? 'is-selected' : ''" @click="openForm(data.day)">
                  {{ data.day.split('-').slice(1).join('-') }} {{ data.isSelected ? '✔️' : ''}}
                </p>
                
            </template>
      </el-calendar>

        <!-- 查看本日排班员工 -->
        <el-dialog :visible.sync="workForm" >
        <h1 align="center">本日排班名单 </h1><br>
        
            <div slot="footer" class="dialog-footer">
              <el-button type="danger" @click="gotoSchedulingForm">排 班</el-button>
                <el-button type="primary" @click="closeForm">确 定</el-button>
            </div>
        </el-dialog>

        <!-- 排班模块 -->
        <el-dialog :visible.sync="schedulingForm" >
        <h1 align="center">本日排班名单 </h1><br>
              <el-form :model="worker">
                  <el-form-item label="科室" :label-width="formLabelWidth">
                      <el-select v-model="worker.deptId" placeholder="科室" @change="getRoleList">
                          <el-option v-for="dept in deptList" :key="dept.id" :label="dept.name" :value="dept.id"  ></el-option>
                      </el-select>

                      <el-select v-model="worker.roleId" placeholder="职位" v-if="worker.deptId!=''" @change="getWorkerList">
                          <el-option v-for="role in roleList" :key="role.id" :label="role.name" :value="role.id"  ></el-option>
                      </el-select>

                      <el-select v-model="worker.id" placeholder="员工" v-if="worker.roleId!=''">
                          <el-option v-for="worker in workerList" :key="worker.id" :label="worker.name" :value="worker.id"  ></el-option>
                      </el-select>

                      <el-select v-model="worker.scheduling" placeholder="班次" v-if="worker.id!=''">
                          <el-option v-for="scheduling in schedulingList" :key="scheduling.id" :label="scheduling.worktime" :value="scheduling.id"  ></el-option>
                      </el-select>

                  </el-form-item>
              </el-form>
        
            <div slot="footer" class="dialog-footer">
              <el-button type="danger" @click="scheduling">排 班</el-button>
                <el-button type="primary" @click="closeSchedulingForm">取 消</el-button>
            </div>
        </el-dialog>

    </div>
</template>

<script>
export default {
   data() {
      return {
        formLabelWidth: '120px',
        worker:{
          deptId:'',
          roleId:'',
          id:'',
          scheduling:'',
          time:''
        },
        roleList:[],
        deptList:[],
        workerList:[],
        schedulingList:[],
        value: new Date(),
        workForm:false,
        thisDay:'',
        schedulingForm:false,
      }
   },
   created(){
      this.$axios.get("/api/findDepts")
            .then(res=>{
               console.log(res.data);
                this.deptList=res.data;
        })

      this.$axios.get("/api/findSchedulings")
            .then(res=>{
               console.log(res.data);
                this.schedulingList=res.data;
        })
   },
   methods:{
     getWorkerList(){
       this.$axios.get("/api/findWorkers",{params:{deptId:this.worker.deptId,roleId:this.worker.roleId}})
            .then(res=>{
               console.log(res.data);
                this.workerList=res.data;
        })

     },
     getRoleList(){
       this.worker.roleId='';
       this.worker.id='';

       this.$axios.get("/api/findRoles")
            .then(res=>{
               console.log(res.data);
                this.roleList=res.data;
        })

     },
     scheduling(){
       var patient = this.worker;
       patient.time=this.thisDay;
       this.$axios.post("/api/scheduling",patient)
            .then(res=>{
              this.closeSchedulingForm();
        })

     },
     closeSchedulingForm(){
       this.worker={
         deptId:'',
          roleId:'',
          id:'',
          scheduling:'',
          time:''
       }
        this.schedulingForm=false;
     },
     gotoSchedulingForm(){
        this.schedulingForm=true;
     },
     openForm(a){
        this.workForm=true;
        this.thisDay=a;
      },

      closeForm(){
        this.workForm=false;
      }

   }
}
</script>

<style scoped>
.is-selected {
    color: #1989FA;
  }
</style>
