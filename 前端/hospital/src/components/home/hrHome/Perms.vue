<template>
  <div>
    <!-- 面包xie导航 -->
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item><a href="#/workers">权限管理</a></el-breadcrumb-item>
      <el-breadcrumb-item>职工列表</el-breadcrumb-item>
    </el-breadcrumb>
    <el-row style="margin-top: 10px; margin-bottom: 10px">

      <el-col :span="8">
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


          <el-table-column label="操作">
            <template slot-scope="scope">
              <el-button type="warning" @click="openRightDialog(scope.row.id)">权限加载</el-button>
            </template>
          </el-table-column>
        </el-table>
        <el-pagination background :cuttent-page="currentPage" layout="prev, pager, next,sizes,->,total"
          :page-sizes="[5, 10, 15, 20, 25]" :total="total" :page-size="pageSize" @size-change="handleSizeChange"
          @current-change="handleCurrentChange">
        </el-pagination>
      </el-col>

    </el-row>

    <el-dialog title="权限加载" :visible.sync="rightDialogFormVisible">
      <el-tree ref="permsTree" :data="treeData" show-checkbox node-key="id" :check-strictly="true"
        :default-expand-all="true" :props="defaultProps">
      </el-tree>
      <div slot="footer" class="dialog-footer">
        <el-button @click="rightDialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="assignRight">确 定</el-button>
      </div>
    </el-dialog>

  </div>

</template>

<script>
  export default {
    data() {

      return {
        userid: "",
        id: "",
        workerData: [],
        currentPage: 1, //当前页码
        total: 0, //总记录数
        pageSize: 5, //页大小
        worker: {},
        deptData: [],
        roleData: [],
        rightDialogFormVisible: false,
        treeData: [],
        defaultProps: {
          children: "children",
          label: "name",
        },

        formLabelWidth: "120px",

        headers: {},

      };
    },
    created() {
      this.findWorkerList();
      this.findPermsList();

    },

    methods: {

      //职工列表
      findWorkerList(pageNum) {
        //axios请求拿数据
        this.$axios
          .get("/api/worker/list", {
            params: {
              searchName: this.searchName,
              pageNum: pageNum,
              pageSize: this.pageSize,
            },
          })
          .then((res) => {
            console.log(res.data);
            //设置职工列表数据
            this.workerData = res.data.list;
            this.currentPage = res.data.pageNum;
            this.total = res.data.total; //设置总记录数
          })
          .catch((res) => {
            this.$message({
              type: "error",
              message: "连接超时,请重新登录!",
            });
            this.$router.push("/login");
          });
      },


      findPermsList() {
        this.$axios.get("/api/perms/all").then((res) => {
          this.treeData = res.data.data;
          console.log(this.treeData);

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

      //打开权限分配对话框
      openRightDialog(id) {
        this.userid = id
        this.rightDialogFormVisible = true;

        // 将当前用户权限勾选中
        this.$axios
          .get("/api/user/permsByUserid", {
            params: {
              id: id
            }
          })
          .then(res => {
            console.log(res.data.data);
            this.$nextTick(() => {
              //等待DOM对象创建完成
              console.log(this.$refs.permsTree)
              this.$refs.permsTree.setCheckedKeys(res.data.data); //设置选中
            });
          });
      },


      //设置权限
      assignRight() {
        //获取当前要设置权限的用户id
        //获取treeData中已选中的权限的id
        let permsids = this.$refs.permsTree.getCheckedKeys();
        //将整数id数组转换为String
        let permsidsStr = permsids.join(",");

        //发送axios请求，进行更新数据库
        this.$axios.post("/api/user/updateUserPerms", "userId=" + this.userid + "&permsId=" + permsidsStr, {
            headers: {
              "Content-Type": "application/x-www-form-urlencoded"
            }
          })
          .then(res => {
            console.log(res);
            if (res.data.status = 200) {
              this.$message({
                showClose: true,
                message: "权限设置成功",
                type: "success",
                duration: 600,
              });

              this.rightDialogFormVisible = false;
              location.reload();
            } else {
              this.$message({
                showClose: error,
                message: "权限设置失败",
                type: "success",
                duration: 600,
              });
              this.rightDialogFormVisible = false;
            }
          }).catch(() => {

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
