<template>
  <div>
    <!-- 面包xie导航 -->
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: '/gotoHome' }">首页</el-breadcrumb-item>

      <el-breadcrumb-item>排班列表</el-breadcrumb-item>
    </el-breadcrumb>
    
    <el-row style="margin-top: 10px; margin-bottom: 10px">
    
      <el-col :span="12" style="margin-left: 10px">

         <!-- 
            el-table数据表格
            :data 动态绑定 data中对象数组
            scope.row 表示对象数组的当前行对象
         -->
    <el-table :data="schedulingData" style="width: 100%">
      <!-- 
                el-table-column列
                prop 绑定data数组中对象的属性
             -->
      <el-table-column type="index" :index="indexMethod" label="序号" width="60">
      </el-table-column>
      <el-table-column prop="remarks" label="姓名" width="100"> </el-table-column>
      <el-table-column prop="worktime" label="班次" width="100"> </el-table-column>

     

      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button size="mini" type="primary" @click="handleEdit(scope.row)">排班</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination background :cuttent-page="currentPage" layout="prev, pager, next,sizes,->,total"
      :page-sizes="[5, 10, 15, 20, 25]" :total="total" :page-size="pageSize" @size-change="handleSizeChange"
      @current-change="handleCurrentChange">
    </el-pagination>

    

<!-- 修改职工 -->
       <el-dialog title="修改职工" :visible.sync="editSchedulingDialogFormVisible">
          <el-form :model="editSchedulingForm">
           <el-form-item
              label="职工班次"
              :label-width="formLabelWidth"
              prop="worktime"
            >
             <el-input
                v-model="editSchedulingForm.worktime"
                autocomplete="off"
              ></el-input>

          
             
            </el-form-item>
          

          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button type="primary" @click="updateWorker">确 定</el-button>
            <el-button @click="editSchedulingDialogFormVisible = false;scheduling={}">取 消</el-button>
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
        searchName: "",
        schedulingData: [],
        currentPage: 1, //当前页码
        total: 0, //总记录数
        pageSize: 5, //页大小
        scheduling: {},
        editSchedulingDialogFormVisible: false, //控制是否显示编辑职工对话框
        formLabelWidth: "120px",
        editWorkerForm: {
          id: "",
          spare1: "",
        },

        headers: {},

      };
    },
    created() {
      this.findSchedulingList();
    },

    methods: {
   //职工列表
      findSchedulingList(pageNum) {
        //axios请求拿数据
        this.$axios
          .get("/api/scheduling/list", {
            params: {
              searchName: this.searchName,
              pageNum: pageNum,
              pageSize: this.pageSize,
            },
          })
          .then((res) => {
            console.log(res.data);
            //设置职工列表数据
            this.schedulingData = res.data.list;
            this.currentPage = res.data.pageNum;
            this.total = res.data.total; //设置总记录数
          })
        // .catch((res) => {
        //   this.$message({
        //     type: "error",
        //     message: "连接超时,请重新登录!",
        //   });
        //   this.$router.push("/login");
        // });
      },

      indexMethod(index) {
        return index + 1 + (this.currentPage - 1) * this.pageSize;
      },

      handleSizeChange(value) {
        this.pageSize = value;
        //加载改变大小后的列表
        this.findSchedulingList(1);
      },
      //页面改变
      handleCurrentChange(value) {
        this.findSchedulingList(value);
      },
     

      //编辑职工
     handleEdit(row) {
        console.log(row);
        let {
          id,
          worktime,
        } = row;
        this.editWorkerForm.id = id;
        this.editWorkerForm.spare1 = spare1;
        

        //显示编辑对话框
        this.editSchedulingDialogFormVisible = true;
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
              this.scheduling = {};
              this.editSchedulingDialogFormVisible = false;
              this.findSchedulingList(1); //刷新列表
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
