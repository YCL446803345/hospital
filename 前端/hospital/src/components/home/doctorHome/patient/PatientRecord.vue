<template>
    <div>
        <!-- 面包xie导航 -->
        <el-breadcrumb separator="/">
            <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item><a href="#/patientInfo">在院病人信息管理</a></el-breadcrumb-item>
            <el-breadcrumb-item>在院病人护理列表</el-breadcrumb-item>
        </el-breadcrumb>
        <el-row style="margin-top:10px;margin-bottom:10px">
            <el-col :span="2">
                <el-input v-model="name" placeholder="姓名">
                </el-input>
            </el-col>
            
            <el-col :span="4">
                <el-input v-model="no" placeholder="编号">
                </el-input>
            </el-col>
            
            <el-col :span="2">
                <el-select v-model="gender" placeholder="性别">
                    <!-- <el-option label="性别" value="" ></el-option> -->
                    <el-option label="男" value="1" ></el-option>
                    <el-option label="女" value="2" ></el-option>
                </el-select>
            </el-col>

            <el-col :span="4">
                <el-input v-model="cardId" placeholder="身份证">
                </el-input>
                <!--带搜索按钮的文本框 -->
            </el-col>
           
            <el-col :span="1.5" style="margin-left:2px;">
                <el-button type="success" @click="search">查询</el-button>
               
            </el-col>

            <el-col :span="1" style="margin-left:2px;">
                 <el-button type="warning" @click="name='',no='',paheNum=1,pageSize=5,gender='',cardId='',deptId=''">清空</el-button>
            </el-col>
            
        </el-row>
        <!-- 
            el-table数据表格
            :data 动态绑定 data中对象数组
            scope.row 表示对象数组的当前行对象
         -->
        <el-table
            :data="patientList"
            style="width: 100%">

            <el-table-column
                type="index"
                :index='getIndex'
                label="序号"
                width="50">
            </el-table-column>

             <el-table-column
                prop="name"
                label="姓名"
                width="80">
            </el-table-column>

            <el-table-column
                prop="no"
                label="病人编号"
                width="120">
            </el-table-column>

            <!-- <el-table-column
                label="头像"
                width="100">
                 <template slot-scope="scope">
                    <img  :src="scope.row.avatar" class="a-avatar" >
                </template>
            </el-table-column> -->

            <el-table-column
                prop="gender"
                label="性别"
                width="80">
                <template slot-scope="scope">
                <span v-if="scope.row.gender=='2'">
                  <el-tag type="danger">女</el-tag>
                  </span>
                <span v-if="scope.row.gender=='1'">
                  <el-tag>男</el-tag>
                  </span>
                </template>
            </el-table-column>

             
            <el-table-column
                prop="age"
                label="年龄"
                width="80">
            </el-table-column>

            <el-table-column
                prop="cardId"
                label="身份证号"
                width="180">
            </el-table-column>

            <el-table-column
                prop="phone"
                label="手机号"
                width="150">
            </el-table-column>

            <el-table-column
                label="入院时间"
                width="200">
                <template slot-scope="scope">
                    <i class="el-icon-time"></i>
                    <span style="margin-left: 10px">{{ scope.row.appointmenttTime}}</span>
                </template>
            </el-table-column>

            <el-table-column
                prop="deptName"
                label="科室"
                width="80">
            </el-table-column>

            <el-table-column
                prop="bedCode"
                label="床位编号"
                width="80">
                 <template slot-scope="scope">
                     <el-tag :type="scope.row.bedCode===null?'danger':'primary'" disable-transitions>
                         {{scope.row.bedCode===null?'未分配':scope.row.bedCode}}
                     </el-tag>
                </template>
            </el-table-column>

            <el-table-column
                label="护士名"
                width="80">
                <template slot-scope="scope">
                     <el-tag :type="scope.row.nurseName==='未分配'?'danger':'primary'" disable-transitions>
                         {{scope.row.nurseName}}
                     </el-tag>
                </template>
            </el-table-column>

            <el-table-column
                label="主治医生"
                width="80">
                <template slot-scope="scope">
                     <el-tag :type="scope.row.doctorName==='未分配'?'danger':'primary'" disable-transitions>
                         {{scope.row.doctorName}}
                     </el-tag>
                </template>
            </el-table-column>
            <el-table-column label="操作">
               <template slot-scope="scope">
                  <el-button
                  size="mini"
                  type="primary"
                  @click="gotoAddNursingViewRecord(scope.row.id,scope.row.no,scope.row.bedCode,scope.row.deptName,scope.row.name)">查看护理记录</el-button>
                   <!-- <el-button
                  size="mini"
                  type="warning"
                  @click="gotoAddNursingRecord(scope.row.id,scope.row.no,scope.row.bedCode,scope.row.deptName,scope.row.name)">添加护理记录</el-button> -->
                   
               </template>
            </el-table-column>
        </el-table>
       
        <el-pagination
            background
            layout="prev, pager,next, sizes,->,total"
            :total="total"
            :current-page='pageNum'
            :page-sizes=[5,10,15,20]
            :page-size='pageSize'
            @size-change='changeSize'
            @current-change='changePage'
           >
        </el-pagination>


        <!-- 添加护理记录 -->
        <el-dialog :visible.sync="addNursingRecordForm"  >
        <h1 align="center">添加护理记录 </h1><br>
        <template>
        <el-descriptions class="margin-top" title="" :column="2"  border>
            <el-descriptions-item>
            <template slot="label">
                <i class="el-icon-user"></i>
                姓名
            </template>
            {{addNursingRecord.name}}
            </el-descriptions-item>
            
            <el-descriptions-item>
            <template slot="label">
                <i class="el-icon-tickets"></i>
                编号
            </template>
            {{addNursingRecord.no}}
            </el-descriptions-item>
            
            <el-descriptions-item>
            <template slot="label">
                <i class="el-icon-tickets"></i>
                床位号
            </template>
            {{addNursingRecord.bedCode}}
            </el-descriptions-item>
            <el-descriptions-item>
            <template slot="label" >
                <i class="el-icon-tickets"></i>
                科室
            </template>
           {{addNursingRecord.deptName}}
            </el-descriptions-item>
            
        </el-descriptions>
        </template>

        <el-divider></el-divider>


        <el-form :model="nursingRecord">

            <el-form-item label="体温" :label-width="formLabelWidth">
                 <el-input v-model="nursingRecord.temperature" autocomplete="off"></el-input>
            </el-form-item>

            <el-form-item label="体重" :label-width="formLabelWidth">
                 <el-input v-model="nursingRecord.weight" autocomplete="off"></el-input>
            </el-form-item>

            <el-form-item label="呼吸" :label-width="formLabelWidth">
                 <el-input v-model="nursingRecord.breathe" autocomplete="off"></el-input>
            </el-form-item>

            <el-form-item label="心率" :label-width="formLabelWidth">
                 <el-input v-model="nursingRecord.pulse" autocomplete="off"></el-input>
            </el-form-item>

            <el-form-item label="是否受吃药" :label-width="formLabelWidth">
                 <el-switch
                    v-model="nursingRecord.takeMedicine"
                    active-color="#13ce66"
                    inactive-color="#ff4949">
                </el-switch>
            </el-form-item>
            
            <el-form-item label="护理等级" :label-width="formLabelWidth">
                <el-select v-model="nursingRecord.nursingLevel" placeholder="护理等级">
                    <el-option label="一般护理记录" value=1></el-option>
                    <el-option label="危重护理等级" value=2></el-option>
                    <el-option label="特殊护理等级" value=3></el-option>
                </el-select>
            </el-form-item>

             <el-form-item label="描述" :label-width="formLabelWidth">
                 <el-input
                        type="textarea"
                        :autosize="{ minRows: 2, maxRows: 4}"
                        placeholder="请输入内容"
                        v-model="nursingRecord.decs"
                        autocomplete="off">
                </el-input>
            </el-form-item>
            
        </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button type="primary" @click="addNursingRecord1">添 加</el-button>
                <el-button @click="closeNursingRecordForm">取 消</el-button>
            </div>
        </el-dialog>



         <!-- 查看病人护理记录 -->
        <el-dialog :visible.sync="nursingRecordViewForm" width="83%">
        <h1 align="center">查看护理记录 </h1><br>
        <template>
        <el-descriptions class="margin-top" title="" :column="4"  border>
            <el-descriptions-item>
            <template slot="label">
                <i class="el-icon-user"></i>
                姓名
            </template>
            {{addNursingRecord.name}}
            </el-descriptions-item>
            
            <el-descriptions-item>
            <template slot="label">
                <i class="el-icon-tickets"></i>
                编号
            </template>
            {{addNursingRecord.no}}
            </el-descriptions-item>
            
            <el-descriptions-item>
            <template slot="label">
                <i class="el-icon-tickets"></i>
                床位号
            </template>
            {{addNursingRecord.bedCode}}
            </el-descriptions-item>
            <el-descriptions-item>
            <template slot="label" >
                <i class="el-icon-tickets"></i>
                科室
            </template>
           {{addNursingRecord.deptName}}
            </el-descriptions-item>
            
        </el-descriptions>
        </template>

        <el-divider></el-divider>


        <el-form :model="nursingRecord">
            <!-- <el-col :span="3">
                <el-select v-model="nursingLevel" placeholder="护理等级">
                    <el-option label="请选择" value="" ></el-option>
                    <el-option label="一般护理等级" value="1" ></el-option>
                    <el-option label="危重护理等级" value="2" ></el-option>
                    <el-option label="特殊护理等级" value="3" ></el-option>
                </el-select>
            </el-col>
           
            <el-col :span="1.5" style="margin-left:2px;">
                <el-button type="success" @click="search1">查询</el-button>
            </el-col>

            <el-col :span="1" style="margin-left:2px;">
                 <el-button type="warning" @click="nursingLevel='',paheNum1=1,pageSize1=5">清空</el-button>
            </el-col> -->
         
        
        
        <!-- 
            el-table数据表格
            :data 动态绑定 data中对象数组
            scope.row 表示对象数组的当前行对象
         -->
        <el-table
            :data="nursingRecordList"
            style="width: 100%">

            <el-table-column
                type="index"
                :index='getIndex1'
                label="序号"
                width="50">
            </el-table-column>

             <el-table-column
                prop="patientName"
                label="病人姓名"
                width="80">
            </el-table-column>

            <el-table-column
                label="体温"
                width="80">
                 <template slot-scope="scope">
                     <span>{{scope.row.temperature}}</span>℃
                </template>
            </el-table-column>

            <el-table-column
                label="体重"
                width="80">
                 <template slot-scope="scope">
                     <span>{{scope.row.weight}}</span>kg
                </template>
            </el-table-column>

            <el-table-column
                label="呼吸"
                width="100">
                 <template slot-scope="scope">
                     <span>{{scope.row.breathe}}</span>次/min
                </template>
            </el-table-column>
            <!-- 11 -->
            <el-table-column
                label="心率"
                width="100">
                 <template slot-scope="scope">
                     <span>{{scope.row.pulse}}</span>次/min
                </template>
            </el-table-column>

            <el-table-column
                label="是否吃药"
                width="80">
                 <template slot-scope="scope">
                    <el-tag :type="scope.row.takeMedicine==1?'primary':'danger'" disable-transitions>
                         {{scope.row.takeMedicine==1?'已吃药':'未吃药'}}
                     </el-tag>
                </template>
            </el-table-column>

            <el-table-column
                prop="decs"
                label="备注"
                width="80">
            </el-table-column>


            <el-table-column
                prop="createTime"
                label="护理时间"
                width="120">
            </el-table-column>

            <el-table-column
                label="护理等级"
                width="120">
                <template slot-scope="scope">
                     <el-tag :type="scope.row.nursingLevel==3?'danger':scope.row.nursingLevel==2?'warning':'primary'" disable-transitions>
                         {{scope.row.nursingLevel==3?'特殊护理等级':scope.row.nursingLevel==='2'?'危重护理等级':'一般护理等级'}}
                     </el-tag>
                </template>
            </el-table-column>

            <el-table-column
                prop="nurseName"
                label="护理护士"
                width="80">
            </el-table-column>

             <el-table-column
                label="修改时间"
                width="120">
                <template slot-scope="scope" >
                    <span style="align:center">{{scope.row.modifyTime==null?'---':scope.row.modifyTime}}</span>
                </template>
            </el-table-column>

             <el-table-column
                label="修改护士"
                width="80">
                  <template slot-scope="scope">
                    <span style="align:center">{{scope.row.modifyName==null?'---':scope.row.modifyName}}</span>
                </template>
            </el-table-column>
            
            <!-- <el-table-column label="操作">
               <template slot-scope="scope">
                  <el-button
                  size="mini"
                  type="warning"
                  @click="gotoUpdateNursingRecord(
                      scope.row.id,
                      scope.row.patientId,
                      scope.row.nursingLevel,
                      scope.row.decs,
                      scope.row.temperature,
                      scope.row.weight,
                      scope.row.breathe,
                      scope.row.pulse,
                      scope.row.takeMedicine,
                      )">修改</el-button>
                   <el-button
                  size="mini"
                  type="danger"
                  @click="deleteNursingRecord(scope.row.id)">删除</el-button>
                   
               </template>
            </el-table-column> -->
        </el-table>
       
        <el-pagination
            background
            layout="prev, pager,next, sizes,->,total"
            :total="total1"
            :current-page='pageNum1'
            :page-sizes=[5,10,15,20]
            :page-size='pageSize1'
            @size-change='changeSize1'
            @current-change='changePage1'
           >
        </el-pagination>
            
        </el-form>
            
            
            <div slot="footer" class="dialog-footer">
                <el-button type="primary" @click="closeNursingRecord">确 定</el-button>
            </div>
        </el-dialog>


        <!-- 修改护理记录 -->
        <el-dialog :visible.sync="updateNursingRecordForm"  >
        <h1 align="center">修改护理记录 </h1><br>
        <el-form :model="nursingRecord">

            <el-form-item label="体温" :label-width="formLabelWidth">
                 <el-input v-model="nursingRecord.temperature" autocomplete="off"></el-input>
            </el-form-item>

            <el-form-item label="体重" :label-width="formLabelWidth">
                 <el-input v-model="nursingRecord.weight" autocomplete="off"></el-input>
            </el-form-item>

            <el-form-item label="呼吸" :label-width="formLabelWidth">
                 <el-input v-model="nursingRecord.breathe" autocomplete="off"></el-input>
            </el-form-item>

            <el-form-item label="心率" :label-width="formLabelWidth">
                 <el-input v-model="nursingRecord.pulse" autocomplete="off"></el-input>
            </el-form-item>

            <el-form-item label="是否受吃药" :label-width="formLabelWidth">
                 <el-switch
                    v-model="nursingRecord.takeMedicine"
                    active-color="#13ce66"
                    inactive-color="#ff4949">
                </el-switch>
            </el-form-item>
            
            <el-form-item label="护理等级" :label-width="formLabelWidth">
                <el-select v-model="nursingRecord.nursingLevel" placeholder="护理等级">
                    <el-option label="一般护理记录" value=1></el-option>
                    <el-option label="危重护理等级" value=2></el-option>
                    <el-option label="特殊护理等级" value=3></el-option>
                </el-select>
            </el-form-item>

             <el-form-item label="描述" :label-width="formLabelWidth">
                 <el-input
                        type="textarea"
                        :autosize="{ minRows: 2, maxRows: 4}"
                        placeholder="请输入内容"
                        v-model="nursingRecord.decs"
                        autocomplete="off">
                </el-input>
            </el-form-item>
            
        </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button type="primary" @click="updateNursingRecord">修 改</el-button>
                <el-button @click="closeUpdateNursingRecordForm">取 消</el-button>
            </div>
        </el-dialog>


    </div>
</template>

<script>
export default {
   data() {
      return {
        nurseId:'',
        nurseDeptId:'',
        no:'',
        name:'',
        gender:'',
        cardId:'',
        status:1,
        patientList: [],
        total:100,
        pageNum:1,
        pageSize:5,
        total1:100,
        pageNum1:1,
        pageSize1:5,
        updateNursingRecordForm:false,
        headers:{},
        nursingLevel:'',
        addNursingRecordForm:false,
        nursingRecordViewForm:false,
        addNursingRecord:{
            id:'',
            no:'',
            bedCode:'',
            deptName:'',
            name:''
        },
        nursingRecord:{
            id:'',
            patientId:'',
            nurseId:'',
            nursingLevel:1,
            decs:'',
            temperature:'',
            weight:'',
            breathe:'',
            pulse:'',
            takeMedicine:true
        },
        formLabelWidth: '120px',
        nursingRecordList:[]
      }
   },
   created(){
      var a = window.localStorage.getItem("roleId")
      
      if(a=='4'){
        this.nurseDeptId = parseInt(window.localStorage.getItem("deptId"))
      }else if(a=='2'){
        this.nurseId =  parseInt(window.localStorage.getItem("workerId"))
      }else if(a=='9'){
        this.nurseDeptId=''
        this.nurseId=''
      }

    //   console.log('nurseDeptId'+this.nurseDeptId)
    //   console.log('nurseId'+this.nurseId)

      this.search();
   },
   methods:{
       updateNursingRecord(){
            var nursingRecord=this.nursingRecord
             if(this.nursingRecord.takeMedicine){
                this.nursingRecord.takeMedicine=1
            }else{
                this.nursingRecord.takeMedicine=2
            }
             this.$axios.post("/api/updateNursingRecord",nursingRecord)
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
                         message: "修改成功!",
                         duration:2000
                     });
                    this.closeUpdateNursingRecordForm();
                    this.search1();
                }
            })
           
       },
       closeUpdateNursingRecordForm(){
            this. nursingRecord={
            id:'',
            patientId:'',
            nurseId:'',
            nursingLevel:1,
            decs:'',
            temperature:'',
            weight:'',
            breathe:'',
            pulse:'',
            takeMedicine:true
            }

            this.updateNursingRecordForm=false
            
       },
       gotoUpdateNursingRecord(id,patientId,nursingLevel,decs,temperature,weight,breathe,pulse,takeMedicine){
           this. nursingRecord={
            id:id,
            patientId:patientId,
            nurseId:'',
            nursingLevel:nursingLevel,
            decs:decs,
            temperature:temperature,
            weight:weight,
            breathe:breathe,
            pulse:pulse,
            takeMedicine:takeMedicine
        }

        if(this.nursingRecord.takeMedicine='1'){
                this.nursingRecord.takeMedicine=true
        }else{
              this.nursingRecord.takeMedicine=false
        }
        this.nursingRecord.nurseId=parseInt(window.localStorage.getItem("workerId"))

        this.updateNursingRecordForm=true
       },
        deleteNursingRecord(id){
            this.$confirm('此操作将永久讲师数据, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                // type: 'warning'
            }).then(() => {
                this.$axios.get("/api/deleteNursingRecord",{params:{id:id}})
                .then(res=>{
                    if(res.data.status==4001){
                            this.$message({
                            type: "error",
                            message: "没有权限!",
                             duration:2000
                        });
                     }else{
                        this.$message({
                            type: 'success',
                            message: '删除成功!',
                             duration:2000
                        });
                        this.search1();
                     }
                })
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消删除',
                     duration:2000
                });          
            });

            
       },   
       search1(){
        this.$axios.get("/api/findNursingRecordsByPatientId",{params:{patientId:this.addNursingRecord.id,nursingLevel:this.nursingLevel,pageNum:1,pageSize:this.pageSize1}})
         .then(res=>{
               console.log(res.data);
                this.nursingRecordList=res.data.list;
                this.total1=res.data.total;
                this.pageNum1=res.data.pageNum;
                this.pageSize1=res.data.pageSize;
            })
       },
       changeSize1(value){
            this.pageSize1=value;
            this.pageNum1=1;
            this.search1()
       },
       changePage1(value){
             this.pageNum1=value;
             this.$axios.get("/api/findNursingRecordsByPatientId",{params:{patientId:this.addNursingRecord.id,nursingLevel:this.nursingLevel,pageNum:this.pageNum1,pageSize:this.pageSize1}})
            .then(res=>{
               console.log(res.data);
                this.nursingRecordList=res.data.list;
                this.total1=res.data.total;
                this.pageNum1=res.data.pageNum;
                this.pageSize1=res.data.pageSize;
            })
       },
       closeNursingRecord(){
           this.addNursingRecord={
               id:'',
               no:'',
               bedCode:'',
               deptName:'',
               name:''
           }
           this.nursingRecordViewForm=false;
       },
       gotoAddNursingViewRecord(id,no,bedCode,deptName,name){
            this.addNursingRecord={
               id:id,
               no:no,
               bedCode:bedCode,
               deptName:deptName,
               name:name
           }
           this.search1();
           this.nursingRecordViewForm=true;
       },
       addNursingRecord1(){
           this.nursingRecord.patientId=this.addNursingRecord.id;
           this.nursingRecord.nurseId=parseInt(window.localStorage.getItem("workerId"))
            if(this.nursingRecord.takeMedicine){
                this.nursingRecord.takeMedicine=1
            }else{
                this.nursingRecord.takeMedicine=2
            }
           var nursingRecord=this.nursingRecord
           this.$axios.post("/api/addNursingRecord",nursingRecord)
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
                         message: "修改成功!",
                         duration:2000
                     });
                    this.closeNursingRecordForm();
                    this.search();
                }
            })

       },
       closeNursingRecordForm(){
            this.addNursingRecord={
               id:'',
               no:'',
               bedCode:'',
               deptName:'',
               name:''
           }

           this.nursingRecord={
            id:'',
            patientId:'',
            nurseId:'',
            nursingLevel:1,
            decs:'',
            temperature:'',
            weight:'',
            breathe:'',
            pulse:'',
            takeMedicine:true
            },
            this.addNursingRecordForm=false;
       },
       gotoAddNursingRecord(id,no,bedCode,deptName,name){
           this.addNursingRecord={
               id:id,
               no:no,
               bedCode:bedCode,
               deptName:deptName,
               name:name
           }
            this.addNursingRecordForm=true;
       },
        search(){
            
            this.$axios.get("/api/findPatientsByChangeDept",{params:{name:this.name,no:this.no,gender:this.gender,
                  cardId:this.cardId,nurseId:this.nurseId,deptId:this.nurseDeptId,status:this.status,pageNum:1,pageSize:this.pageSize}})
            .then(res=>{
               console.log(res.data);
                this.patientList=res.data.list;
                this.total=res.data.total;
                this.pageNum=res.data.pageNum;
                this.pageSize=res.data.pageSize;
            })
        },
        changeSize(value){
            this.pageSize=value;
            this.pageNum=1;
            this.search();
        },
        changePage(value){
            this.pageNum=value;
            this.$axios.get("/api/findPatientsByChangeDept",{params:{name:this.name,no:this.no,gender:this.gender,
                  cardId:this.cardId,nurseId:this.nurseId,deptId:this.nurseDeptId,status:this.status,pageNum:this.pageNum,pageSize:this.pageSize}})
            .then(res=>{
                this.patientList=res.data.list;
                this.total=res.data.total;
                this.pageNum=res.data.pageNum;
                this.pageSize=res.data.pageSize;
            })
        },
          getIndex(i){
            return (i+1)+this.pageSize*(this.pageNum-1);
         },
         getIndex1(i){
            return (i+1)+this.pageSize1*(this.pageNum1-1);
         },
   }
}
</script>

<style scoped>

</style>
