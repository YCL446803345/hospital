<template>
  <div>
    <!-- 面包xie导航 -->
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item><a href="#/workers">排班管理</a></el-breadcrumb-item>
      <el-breadcrumb-item>职工列表</el-breadcrumb-item>
    </el-breadcrumb>
    <el-row style="margin-top: 10px; margin-bottom: 10px">
      <el-dialog title="个人排班" :visible.sync="dialogVisible" width="30%" :before-close="handleClose">
        <!-- 日历 -->

        <div class="calendar_box">
          <el-row :gutter="20">
            <el-col :span="18">
              <full-calendar ref="fullCalendar" style="height: 100%" :options="calendarOptions"></full-calendar>
            </el-col>
          </el-row>

      
        </div>
 <label class='label_canlendar'><input class='ace' type='radio' value='#3a87ad' name='code' checked><span
                        class='classes' style='color:#3a87ad'>白班</span></label>
                <label class='label_canlendar'><input class='ace' type='radio' value='#82af6f' name='code'><span
                        class='classes' style='color:#82af6f'>晚班</span></label>
                <label class='label_canlendar'><input class='ace' type='radio' value='#d15b47' name='code'><span
                        class='classes' style='color:#d15b47'>轮休</span></label>
                <label class='label_canlendar'><input class='ace' type='radio' value='#d15b47' name='code'><span
                        class='classes' style='color:#bbb'>事假</span></label>
                <label class='label_canlendar'><input class='ace' type='radio' value='#d15b47' name='code'><span
                        class='classes' style='color:red'>病假</span></label>
        <span slot="footer" class="dialog-footer">
          <el-button @click="dialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
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

          `<el-table-column prop="roleName" label="角色" width="100">
          </el-table-column>`
          <el-table-column prop="deptName" label="部门" width="100">
          </el-table-column>

          <el-table-column label="操作">
            <!-- eslint-disable-next-line -->
            <template slot-scope="scope">
              <el-button type="info" @click="dialogVisible = true">排班管理</el-button>
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
  import FullCalendar from "@fullcalendar/vue";
  import dayGridPlugin from "@fullcalendar/daygrid";
  import interactionPlugin from "@fullcalendar/interaction";

  export default {
    name: "FindPaiban",
    components: {
      FullCalendar,
    },

    data() {
      return {
        dialogVisible: false,
        isShowBtn: false,
        dialogFormVisible: false,
        form: {
          id: "",
          title: "",
          doctorId: "",
          ctime: "",
          doctorName: "",
          phone: "",
          ksName: "",
        },
        formLabelWidth: 120,
        calendarOptions: {
          //   timeGridPlugin  可显示每日时间段
          height: 700,
          plugins: [dayGridPlugin, interactionPlugin],
          headerToolbar: {
            left: "prev,next today",
            center: "title",
            right: "dayGridMonth,dayGridWeek,dayGrid",
            // right: 'agendaWeek,dayGridWeek,dayGrid'
          },
          buttonText: {
            // 设置按钮
            today: "今天",
            month: "月",
            week: "周",
            dayGrid: "天",
          },
          // allDaySlot: false,
          editable: true,
          selectable: true,
          navLinks: true,
         
          // displayEventEnd: true,//所有视图显示结束时间
          initialView: "dayGridMonth", // 设置默认显示月，可选周、日
          dateClick: this.handleDateClick,
          eventClick: this.handleEventClick,
          eventsSet: this.handleEvents,
          select: this.handleDateSelect,
          
          // timezone
          // 设置日程
          events: [],
          //events格式如下
          // {
          // 	id: 1,
          // 	title: '09:30~11:30 小破孩',
          // 	start: '2020-12-11',
          // 	end: '2020-12-13',
          // 	color: '#f08f00',
          // },
          // {
          // 	id: 2,
          // 	title: '9:30~14:30 项目会议',
          // 	start: '2020-12-14',
          // 	end: '2020-12-14',
          // 	color: '#6bb377',

          eventColor: "#f08f00", // 修改日程背景色
          locale: "zh-cn", // 设置语言
          weekNumberCalculation: "ISO", // 周数
          customButtons: {},
        },
       

        userid: "",
        id: "",
        workerData: [],
        currentPage: 1, //当前页码
        total: 0, //总记录数
        pageSize: 5, //页大小
        worker: {},
        rightDialogFormVisible: false,
        treeData: [],
        defaultProps: {
          children: "children",
          label: "name",
        },

        formLabelWidth: "120px",

        headers: {},

        
      };

       // 添加排班
        function select(start, end, allDay) {

            layer.open({
                type: 1,
                shade: false,
                content: $box,
                title: '选择班次',
                btn: ['确认', '关闭'],
                btn1: function() {
                    layer.closeAll();
                },
                yes: addCallback
            });

            // 添加排班 - 弹窗回调
            function addCallback() {
                var bgColor = $box.find("input[name=code]:checked").val();
                var title = $box.find("input[name=code]:checked").next().text();
                var text = $box.find("option:selected").text();

                var json = {
                    title: text + ' - ' + title,
                    text: text,
                    allDay: allDay,
                    backgroundColor: bgColor,
                    start: start.format('YYYY-MM-DD'),
                    end: end.format('YYYY-MM-DD')
                };

                // test
                $('#calendar').fullCalendar('renderEvent', json, true); // 添加日历

                // 添加排班 post
                function addEvent(data) {
                    if (data.title == null || data.title == '') {
                        return false;
                    }
                    if (checkStr(data.title) == false) {
                        alert("请不要输入非法字符！");
                        return;
                    }

                    $.post("Canlendar/postEvent", data, function(data) {
                        calendar.fullCalendar('renderEvent', json, true); // 添加日历
                    }, "json");
                }

                layer.closeAll();
            }

            calendar.fullCalendar('unselect');
        }
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
