<template>
    <div>
      <el-calendar v-model="value" >
            <template
                slot="dateCell"
                slot-scope="{date, data}">
                <p :class="data.isSelected ? 'is-selected' : ''" @click="openForm(data.day)">
                  {{ data.day.split('-').slice(1).join('-') }} {{ data.isSelected ? '✔️' : ''}}
                </p>

                  <el-col :span="5" v-for="a in allWorkersScheduling" :key="a.id"  >
                  <div style="padding: 0px" align="center">
                    <el-card :body-style="{ padding: '0px' }"  shadow="hover" v-if='a.time==data.day'>
                        <div style="padding: 0px">
                          <div style="padding: 0px">
                            <i class="el-icon-user-solid"></i>
                                <span>
                                    <el-tag type="danger" disable-transitions >
                                        {{a.workerName}}
                                    </el-tag>
                                   
                                </span>
                              </div>
                        </div>
                          
                    </el-card>
                </div>
                </el-col>
            </template>

            
      </el-calendar>

        <!-- 查看本日排班员工 -->
        <el-dialog :visible.sync="workForm" >
        <h1 align="center">本日排班名单 </h1><br>
        <template align="center">
             <el-col :span="5" v-for="a in workerAndSchedulingList" :key="a.id"  >
                <div style="padding: 0px" align="center">
                  <el-card :body-style="{ padding: '0px' }"  shadow="hover">
                      <div style="padding: 0px">
                        <div style="padding: 0px">
                          <i class="el-icon-user-solid"></i>
                              <span>
                                  <el-tag type="danger" disable-transitions>
                                      {{a.workerName}}
                                      </el-tag>
                                  <el-tag type="danger" disable-transitions>
                                      {{a.roleName}}
                                  </el-tag>

                                  <el-tag :type="a.schedulingId==3?'info':a.schedulingId==4?'info':a.schedulingId==5?'info':'warning'" disable-transitions>
                                      {{a.schedulingId==1?'白班':a.schedulingId==2?'夜班':
                                      a.schedulingId==3?'轮休':a.schedulingId==4?'病假':'事假'}}
                                  </el-tag>
                              </span>
                            </div>
                      </div>
                        
                  </el-card>
              </div>
         </el-col>
         <e-form>

         </e-form>
        </template>
            <br>
            <div slot="footer" class="dialog-footer">
              <el-button type="danger" @click="gotoSchedulingForm">排 班</el-button>
                <el-button type="primary" @click="closeForm">确 定</el-button>
            </div>
        </el-dialog>

        <!-- 排班模块 -->
        <el-dialog :visible.sync="schedulingForm" >
        <h1 align="center">员工排班 </h1><br>
              <el-form :model="worker" :rules="rules" ref="form1">
                  <el-form-item label="科室" :label-width="formLabelWidth">
                      <el-select v-model="worker.deptId" placeholder="科室" @change="getRoleList" prop="deptId">
                          <el-option v-for="dept in deptList" :key="dept.id" :label="dept.name" :value="dept.id"  ></el-option>
                      </el-select>
                  </el-form-item>

                  <el-form-item label="职位" :label-width="formLabelWidth">
                      <el-select v-model="worker.roleId" placeholder="职位" v-if="worker.deptId!=''" @change="getWorkerList" prop="roleId">
                          <el-option v-for="role in roleList" :key="role.id" :label="role.name" :value="role.id"  ></el-option>
                      </el-select>
                  </el-form-item>

                  <el-form-item label="员工" :label-width="formLabelWidth">
                      <el-select v-model="worker.id" placeholder="员工" v-if="worker.roleId!=''" prop="id">
                          <el-option v-for="worker in workerList" :key="worker.id" :label="worker.name" :value="worker.id"  ></el-option>
                      </el-select>
                  </el-form-item>

                  <el-form-item label="班次" :label-width="formLabelWidth">
                      <el-select v-model="worker.schedulingId" placeholder="班次" v-if="worker.id!=''"  prop="schedulingId">
                          <el-option v-for="scheduling in schedulingList" :key="scheduling.id" :label="scheduling.worktime" :value="scheduling.id"  ></el-option>
                      </el-select>
                  </el-form-item>
              </el-form>
        
            <div slot="footer" class="dialog-footer">
              <el-button type="danger" @click="scheduling('form1')">排 班</el-button>
                <el-button type="primary" @click="closeSchedulingForm">取 消</el-button>
            </div>
        </el-dialog>

    </div>
</template>

<script>
export default {
   data() {
        return {
        
        workerAndSchedulingList:[],
        formLabelWidth: '120px',
        worker:{
          deptId:'',
          roleId:'',
          id:'',
          schedulingId:'',
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
        allWorkersScheduling:[],
        rules:{ 
        deptId:[
                {required: true, message: '不能为空', trigger: 'change' }
            ],
        roleId:[
                { required: true, message: '不能为空', trigger: 'change'  }
        ],
        schedulingId:[
                {required: true, message: '不能为空', trigger: 'change'  }
        ],
        id:[
                { required: true, message: '不能为空', trigger: 'change' }
        ]
      }, 

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
    this.search1();
      
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
     scheduling(form1){
         this.$refs[form1].validate((valid)=>{
           if(valid){
              
         this.$confirm('确定为该员工排班吗?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                // type: 'warning'
            }).then(() => {
              var worker = this.worker;
              worker.time=this.thisDay;
                   this.$axios.post("/api/scheduling",worker)
                      .then(res=>{
                      if(res.data.status==4001){
                          this.$message({
                              type: "error",
                              message: "没有权限!",
                                duration:2000
                          });
                      }else{
                          this.$message({
                              // type: "error",
                              message: "排班成功!",
                              duration:2000
                     });
                     this.search(this.thisDay);
                     this.search1();
                     this.closeSchedulingForm();
                     
                }
            })

            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消',
                     duration:2000
                });          
            });
           }
         })

     },
     closeSchedulingForm(){
       this.worker={
         deptId:'',
          roleId:'',
          id:'',
          schedulingId:'',
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
        this.search(a);

      },
      search(a){
         this.$axios.get("/api/findWorkersBySchedulingTime",{params:{time:a}})
            .then(res=>{
              console.log(res.data);
              this.workerAndSchedulingList=res.data;
        })
      },
      search1(){
          this.$axios.get("/api/findAllWorkersScheduling")
              .then(res=>{
                console.log(res.data);
                this.allWorkersScheduling=res.data;
          })
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
