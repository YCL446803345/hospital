<template>
  <div>
    <!-- 面包xie导航 -->
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item><a href="#/workers">职工管理</a></el-breadcrumb-item>
      <el-breadcrumb-item>职工列表</el-breadcrumb-item>
    </el-breadcrumb>
    <el-row style="margin-top: 10px; margin-bottom: 10px">
      <el-col :span="8">
        <el-input v-model="name" placeholder="请输入名字">
        </el-input>
      </el-col>
      <el-col :span="3">
        <el-select v-model="deptName" placeholder="部门">
          <el-option v-for="dept in deptData" :key="dept.id" :label="dept.name" :value="dept.name"></el-option>
        </el-select>
      </el-col>
      <el-col :span="3">
        <el-select v-model="roleName" placeholder="角色">
          <el-option v-for="role in roleData" :key="role.id" :label="role.name" :value="role.name"></el-option>
        </el-select>
      </el-col>

      <el-col :span="1.5" style="margin-left:2px;">
        <el-button type="success" @click="findWorkerList()">查询</el-button>

      </el-col>

      <el-col :span="1.5" style="margin-left:2px;">
        <el-button type="warning" @click="findWorkerList((name = '',roleName='',deptName=''))">清空</el-button>

      </el-col>
      <el-col :span="2" style="margin-left:2px;">
        <el-button type="success" @click="openAddWorker">添加职工</el-button>

      </el-col>
      <el-col :span="12" style="margin-left: 10px">

        <!-- 
            el-table数据表格
            :data 动态绑定 data中对象数组
            scope.row 表示对象数组的当前行对象
         -->
        <el-table :data="workerData" style="width: 100%">
          <!-- 
                el-table-column列
                prop 绑定data数组中对象的属性
             -->
          <el-table-column type="index" :index="indexMethod" label="序号" width="100">
          </el-table-column>
          <el-table-column prop="name" label="姓名" width="100"> </el-table-column>

          `<el-table-column prop="roleName" label="角色" width="100">
          </el-table-column>`
          <el-table-column prop="deptName" label="部门" width="100">
          </el-table-column>
          <el-table-column prop="gender" label="性别" width="100">
            <template slot-scope="scope">
              <el-tag :type="scope.row.status === '1' ? 'primary' : 'danger'" disable-transitions>
                {{ scope.row.status === "1" ? "男" : "女" }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="salary" label="薪资" width="100">
          </el-table-column>

          <el-table-column prop="status" label="在职状态" width="100">
            <template slot-scope="scope">
              <el-tag :type="scope.row.status === '1' ? 'primary' : 'danger'" disable-transitions>
                {{ scope.row.status === "1" ? "在职" : "离职" }}</el-tag>
            </template>
          </el-table-column>

          <el-table-column label="操作" width="100">
            <template slot-scope="scope">
              <el-button size="mini" type="primary" @click="handleEdit(scope.row)">编辑</el-button><br>
              <el-button size="mini" type="danger" @click="deleteWorker(scope.row.id)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
        <el-pagination background :cuttent-page="currentPage" layout="prev, pager, next,sizes,->,total"
          :page-sizes="[5, 10, 15, 20, 25]" :total="total" :page-size="pageSize" @size-change="handleSizeChange"
          @current-change="handleCurrentChange">
        </el-pagination>

        <!-- 添加Form -->

        <el-dialog title="添加职工" :visible.sync="addDialogWorkerFormVisible" :rules="rules">
          <el-form :model="worker" ref="loginForm">
            <el-form-item label="职工名字" :label-width="formLabelWidth"  prop="name">
              <el-input v-model="worker.name" autocomplete="off"  prop="name"></el-input>
            </el-form-item>
            <el-form-item label="职工账号" :label-width="formLabelWidth">
              <el-input v-model="worker.account" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="职工密码" :label-width="formLabelWidth">
              <el-input v-model="worker.password" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="性别" :label-width="formLabelWidth">
              <el-radio v-model="worker.gender" label="1">男</el-radio>
              <el-radio v-model="worker.gender" label="2">女</el-radio>
            </el-form-item>
            <el-form-item label="角色" :label-width="formLabelWidth">
              <el-select v-model="worker.roleId" placeholder="请选择角色">

                <el-option v-for="role in roleData" :label="role.name" :value="role.id" :key="role.id"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="部门" :label-width="formLabelWidth">
              <el-select v-model="worker.deptId" placeholder="请选择部门">
                <el-option v-for="role in deptData" :label="role.name" :value="role.id" :key="role.id"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="薪资" :label-width="formLabelWidth">
              <el-input v-model="worker.salary" autocomplete="off"></el-input>
            </el-form-item>


          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button type="primary" @click="addWorker('loginForm')">确 定</el-button>
            <el-button @click="addDialogWorkerFormVisible = false;worker={}">取 消</el-button>
          </div>
        </el-dialog>

        <!-- 修改职工 -->
        <el-dialog title="修改职工" :visible.sync="editWorkerDialogFormVisible">
          <el-form :model="editWorkerForm">
            <el-form-item label="职工姓名" :label-width="formLabelWidth" prop="name">
              <el-input v-model="editWorkerForm.name" autocomplete="off"></el-input>
            </el-form-item>


            <el-form-item label="角色" :label-width="formLabelWidth">
              <el-select v-model="editWorkerForm.roleId" placeholder="请选择角色" prop="roleId">
                <el-option v-for="role in roleData" :key="role.id" :label="role.name" :value="role.id"></el-option>
              </el-select>
            </el-form-item>

            <el-form-item label="部门" :label-width="formLabelWidth">
              <el-select v-model="editWorkerForm.deptId" placeholder="请选择部门" prop="deptId">
                <el-option v-for="dept in deptData" :key="dept.id" :label="dept.name" :value="dept.id"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="性别" :label-width="formLabelWidth" prop="gender">

              <el-radio v-model="editWorkerForm.gender" label=1>男</el-radio>
              <el-radio v-model="editWorkerForm.gender" label=2>女</el-radio>


            </el-form-item>
            <el-form-item label="薪资" :label-width="formLabelWidth" prop="salary">
              <el-input v-model="editWorkerForm.salary" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="状态" :label-width="formLabelWidth" prop="status">
              <el-radio v-model="editWorkerForm.status" label=1>在职</el-radio>
              <el-radio v-model="editWorkerForm.status" label=2>离职</el-radio>
            </el-form-item>

          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button type="primary" @click="updateWorker">确 定</el-button>
            <el-button @click="editWorkerDialogFormVisible = false;worker={}">取 消</el-button>
          </div>
        </el-dialog>


      </el-col>

    </el-row>

  </div>

</template>

<script>
  export default {
    data() {
      return {
        name: "",
        deptName: "",
        roleName: "",
        workerData: [],
        currentPage: 1, //当前页码
        total: 0, //总记录数
        pageSize: 5, //页大小
        worker: {},
        deptData: [],
        roleData: [],
        schedulingData: [],
        addDialogWorkerFormVisible: false,
        editWorkerDialogFormVisible: false, //控制是否显示编辑职工对话框
        formLabelWidth: "120px",
        editWorkerForm: {
          id: "",
          name: "",
          roleId: "",
          deptId: "",
          gender: "",
          salary: "",
          status: ""
        },
        rules: {
          name: [{
              required: true,
              message: '请输入手机号',
              trigger: 'blur'
            },
            {
              min: 11,
              max: 11,
              message: '手机号不为11位数或不一致',
              trigger: 'blur'
            },

          ],

        },
        headers: {},

      };
    },
    created() {
      this.findWorkerList();
      this.findRoleList();
      this.findDeptList();
      this.findSchedulingList();
    },

    methods: {

      //职工列表
      findWorkerList(pageNum) {
        //axios请求拿数据
        this.$axios
          .get("/api/worker/list", {
            params: {
              name: this.name,
              deptName: this.deptName,
              roleName: this.roleName,
              pageNum: pageNum,
              pageSize: this.pageSize,
            },
            headers: {
              "Content-Type": "application/x-www-form-urlencoded;charset=UTF-8"
            },
          })
          .then((res) => {
            if (res.data.status == 4001) {
              this.$message({
                showClose: true,
                message: res.data.msg,
                type: 'error',
                duration: 1000
              });
            } else {
              //设置职工列表数据
              this.workerData = res.data.list;
              this.currentPage = res.data.pageNum;
              this.total = res.data.total; //设置总记录数
            }

          })
          .catch((res) => {
            this.$message({
              type: "error",
              message: "连接超时,请重新登录!",
            });
            this.$router.push("/login");
          });
      },

      //部门列表
      findDeptList() {
        //axios请求拿数据
        this.$axios
          .get("/api/dept/list", {
            params: {},
          })
          .then((res) => {
            console.log(res.data.data);
            //设置部门列表数据
            this.deptData = res.data.data;
          })
          .catch((res) => {
            this.$message({
              type: "error",
              message: "获取部门列表错误!",
            });
          });
      },
      //角色列表
      findRoleList() {
        //axios请求拿数据
        this.$axios
          .get("/api/role/list", {
            params: {},
          })
          .then((res) => {
            console.log(res.data.data);
            //设置角色列表数据
            this.roleData = res.data.data;
          })
          .catch((res) => {
            this.$message({
              type: "error",
              message: "获取角色列表错误!",
            });
          });
      },

      //班次列表
      findSchedulingList() {
        //axios请求拿数据
        this.$axios
          .get("/api/scheduling/list", {
            params: {},
          })
          .then((res) => {

            console.log(res.data.list);
            //设置班次列表数据
            this.schedulingData = res.data.list;
          })
          .catch((res) => {
            this.$message({
              type: "error",
              message: "获取班次列表错误!",
            });
          });
      },


      indexMethod(index) {
        return index + 1 + (this.currentPage - 1) * this.pageSize;
      },

      handleSizeChange(value) {
        this.pageSize = value;
        //加载改变大小后的列表
        this.findWorkerList(1);
      },
      //页面改变
      handleCurrentChange(value) {
        this.findWorkerList(value);
      },
      //打开添加职工列表
      openAddWorker() {
        this.$axios.post("/api/worker/add", this.worker).then((res) => {
          console.log(res.data);

          if (res.data.status == 4001) {
            this.$message({
              showClose: true,
              message: res.data.msg,
              type: 'error',
              duration: 1000
            });
          } else {
            this.headers = {
              strToken: localStorage.getItem('strToken')
            }
            this.addDialogWorkerFormVisible = true;
          }

        });



      },


      //添加职工
      addWorker(forName) {


        this.$refs[forName].validate((valid) => {
          //表单验证通过
          if (valid) {

            //跨域后的url
            //发送axios请求
            this.$axios.post("/api/worker/add", this.worker).then((res) => {
              console.log(res.data);

              if (res.data.status == 200) {
                this.$message({
                  showClose: true,
                  message: "添加成功",
                  type: "success",
                  duration: 600,
                });
                this.worker = {};
                this.addDialogWorkerFormVisible = false;
                this.findWorkerList(1); //刷新列表
              } else if (res.data.status == 4001) {
                this.$message({
                  showClose: true,
                  message: res.data.msg,
                  type: 'error',
                  duration: 1000
                });
              } else {
                this.$message({
                  showClose: true,
                  message: "添加失败",
                  type: "error",
                  duration: 600,
                });
              }
            }).catch(res => {
              this.findWorkerList(1); //刷新列表
              this.$message({
                showClose: true,
                message: '异常',
                type: 'success',
                duration: 1500
              });
              //跳转
            })
          } else {
            //验证不通过
            return false;
          }
        })


        //发送axios请求
        this.$axios.post("/api/worker/add", this.worker).then((res) => {
          console.log(res.data);

          if (res.data.status == 200) {
            this.$message({
              showClose: true,
              message: "添加成功",
              type: "success",
              duration: 600,
            });
            this.worker = {};
            this.addDialogWorkerFormVisible = false;
            this.findWorkerList(1); //刷新列表
          } else if (res.data.status == 4001) {
            this.$message({
              showClose: true,
              message: res.data.msg,
              type: 'error',
              duration: 1000
            });
          } else {
            this.$message({
              showClose: true,
              message: "添加失败",
              type: "error",
              duration: 600,
            });
          }

        });
      },

      //删除职工
      deleteWorker(id) {
        this.$confirm("是否删除职工?", "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning",
          })
          .then(() => {
            this.$axios
              .get("/api/worker/delete", {
                params: {
                  id: id
                },
              })
              .then((res) => {
                if (res.data.status === 4001) {
                  this.$message({
                    showClose: true,
                    message: "没有权限",
                    type: "error",
                    duration: 600,
                  });
                } else if (res.data.status === 200) {
                  this.findWorkerList(1);

                  this.$message({
                    showClose: true,
                    message: "删除成功",
                    type: "success",
                    duration: 600,
                  });
                  this.findWorkerList(1);

                }

              })
              .catch((res) => {
                this.$message({
                  showClose: true,
                  message: "删除失败",
                  type: "error",
                  duration: 600,
                });
              });
          })
          .catch(() => {
            this.$message({
              showClose: true,
              message: "取消删除",
              duration: 600,
            });
          });
      },


      //编辑职工
      handleEdit(row) {
        this.$axios.post("/api/worker/update", this.worker).then((res) => {
          console.log(res.data);

          if (res.data.status == 4001) {
            this.$message({
              showClose: true,
              message: res.data.msg,
              type: 'error',
              duration: 1000
            });
          } else {
            let {
              id,
              name,
              roleId,
              deptId,
              gender,
              salary,
              status

            } = row;
            this.editWorkerForm.id = id;
            this.editWorkerForm.name = name;
            this.editWorkerForm.roleId = roleId;
            this.editWorkerForm.deptId = deptId;
            this.editWorkerForm.gender = gender;
            this.editWorkerForm.salary = salary;
            this.editWorkerForm.status = status


            //显示编辑对话框
            this.editWorkerDialogFormVisible = true;
          }
        });
      },




      //执行修改讲师
      updateWorker() {

        this.$axios
          .post("/api/worker/update", this.editWorkerForm)
          .then((res) => {
            if (res.data.status == 200) {
              this.$message({
                showClose: true,
                message: "修改成功",
                type: "success",
                duration: 600,
              });
              this.worker = {};
              this.editWorkerDialogFormVisible = false;
              this.findWorkerList(1); //刷新列表
            } else {
              this.$message({
                showClose: true,
                message: "修改失败",
                type: "error",
                duration: 600,
              });
            }
          });
      },


    }
  };

</script>

<style scoped lang="less">
  .el-breadcrumb {
    background: #d4dae0;
    padding-left: 20px;
    height: 40px;
    line-height: 40px;
  }

  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }

  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }

  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }

  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }

</style>
