<template>
  <div>
    <!-- 面包xie导航 -->
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: '/gotoHome' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item><a href="#/worker/scheduling">排班管理</a></el-breadcrumb-item>
      <el-breadcrumb-item>职工列表</el-breadcrumb-item>
    </el-breadcrumb>
    <el-row style="margin-top: 10px; margin-bottom: 10px">

      <el-dialog title="个人排班" :visible.sync="dialogVisible" width="30%">
        <el-form :model="updateSchedulingForm">

        
             <el-form-item label="班次" :label-width="formLabelWidth">
              <el-select v-model="updateSchedulingForm.spare1" placeholder="请选择班次" prop="spare1">
                <el-option v-for="scheduling in schedulingData" :key="scheduling.id" :label="scheduling.worktime" :value="scheduling.id"></el-option>
              </el-select>
            </el-form-item>
        </el-form>

        <span slot="footer" class="dialog-footer">
          <el-button type="primary" @click="updateScheduling()">确 定</el-button>
          <el-button @click="dialogVisible = false">取 消</el-button>
        </span>
      </el-dialog>

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
          <el-table-column prop="name" label="姓名" width="100">
          </el-table-column>

          <el-table-column prop="roleName" label="角色" width="100">
          </el-table-column>
          <el-table-column prop="deptName" label="部门" width="100">
          </el-table-column>
          <el-table-column prop="shift" label="班次" width="100">
          </el-table-column>

          <el-table-column label="操作">
            <!-- eslint-disable-next-line -->
            <template slot-scope="scope">
              <el-button type="info" @click="openUpdateScheduling(scope.row)">排班管理</el-button>
            </template>
          </el-table-column>
        </el-table>
        <el-pagination background :cuttent-page="currentPage" layout="prev, pager, next,sizes,->,total"
          :page-sizes="[5, 10, 15, 20, 25]" :total="total" :page-size="pageSize" @size-change="handleSizeChange"
          @current-change="handleCurrentChange">
        </el-pagination>
      </el-col>
    </el-row>

  </div>

</template>

<script>
  export default {


    components: {

    },

    data() {
      return {

        dialogVisible: false,
        id: "",
        workerData: [],
        schedulingData:[],
        currentPage: 1, //当前页码
        total: 0, //总记录数
        pageSize: 5, //页大小
        worker: {},
        defaultProps: {
          children: "children",
          label: "name",
        },

        formLabelWidth: "120px",
        updateSchedulingForm: {
          id: "",
          spare1: "",
        },
        headers: {},



      };
    },

    created() {
      this.findWorkerList();

      this.findPermsList();

      this.findSchedulingList();
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


      //打开修改班次列表
      openUpdateScheduling(row) {
       

          let {
            id,
            name,
            spare1,

          } = row;
          this.updateSchedulingForm.id = id;
          this.updateSchedulingForm.name = name;
          this.updateSchedulingForm.spare1 = spare1;



          //显示编辑对话框
          this.dialogVisible = true;
       
      },

 //执行修改班次
      updateScheduling() {

        this.$axios
          .post("/api/scheduling/update", this.updateSchedulingForm)
          .then((res) => {
            if (res.data.status == 200) {
              this.$message({
                showClose: true,
                message: "修改成功",
                type: "success",
                duration: 600,
              });
              this.dialogVisible = false;
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
      //添加排班
  


      // select(start, end, allDay) {
      //       layer.open({
      //           type: 1,
      //           shade: false,
      //           content: $box,
      //           title: '选择班次',
      //           btn: ['确认', '关闭'],
      //           btn1: function() {
      //               layer.closeAll();
      //           },
      //           yes: addCallback
      //       });
      //       function addCallback() {
      //           var bgColor = $box.find("input[name=code]:checked").val();
      //           var title = $box.find("input[name=code]:checked").next().text();
      //           var text = $box.find("option:selected").text();

      //           var json = {
      //               title: text + ' - ' + title,
      //               text: text,
      //               allDay: allDay,
      //               backgroundColor: bgColor,
      //               start: start.format('YYYY-MM-DD'),
      //               end: end.format('YYYY-MM-DD')
      //           };

      //           // test
      //           $('#calendar').fullCalendar('renderEvent', json, true); // 添加日历

      //           // 添加排班 post
      //           function addEvent(data) {
      //               if (data.title == null || data.title == '') {
      //                   return false;
      //               }
      //               if (checkStr(data.title) == false) {
      //                   alert("请不要输入非法字符！");
      //                   return;
      //               }

      //               $.post("Canlendar/postEvent", data, function(data) {
      //                   calendar.fullCalendar('renderEvent', json, true); // 添加日历
      //               }, "json");
      //           }

      //           layer.closeAll();
      //       }

      //       calendar.fullCalendar('unselect');
      //   }
    },
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
    border-color: #409eff;
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
