<template>
  <div>
    <!-- 面包xie导航 -->
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item
        ><a href="#/patientList">预约住院信息管理</a></el-breadcrumb-item
      >
      <el-breadcrumb-item>预约信息管理列表</el-breadcrumb-item>
    </el-breadcrumb>
    <el-row style="margin-top: 10px; margin-bottom: 10px">
      <el-col :span="2">
        <el-input v-model="patientName" placeholder="姓名"> </el-input>
      </el-col>

      <el-col :span="4">
        <el-input v-model="cardId" placeholder="卡号"> </el-input>
      </el-col>

      <el-col :span="2">
        <el-select v-model="patientSex" placeholder="性别">
          <el-option label="性别" value=""></el-option>
          <el-option label="男" value="男"></el-option>
          <el-option label="女" value="女"></el-option>
        </el-select>
      </el-col>

      <el-col :span="1" style="margin-left: 2px">
        <el-button type="success" @click="search">查询</el-button>
      </el-col>

      <el-col :span="1" style="margin-left: 2px">
        <el-button
          type="warning"
          @click="
            (patientName = ''),
              (cardId = ''),
              (patientSex = ''),
              (paheNum = 1),
              (pageSize = 5)
          "
          >清空</el-button
        >
      </el-col>
    </el-row>
    <!-- 
            el-table数据表格
            :data 动态绑定 data中对象数组
            scope.row 表示对象数组的当前行对象
         -->
    <el-table :data="inHospitalTableList" style="width: 100%">
      <el-table-column type="index" :index="getIndex" label="序号" width="50">
      </el-table-column>
      <el-table-column
        prop="patientName"
        label="姓名"
        width="80"
      ></el-table-column>
      <el-table-column
        prop="patientSex"
        label="性别"
        width="80"
      ></el-table-column>
      <el-table-column
        prop="patientAge"
        label="年龄"
        width="80"
      ></el-table-column>
      <el-table-column prop="cardId" label="卡号" width="180"></el-table-column>
      <el-table-column
        prop="telephone"
        label="联系方式"
        width="180"
      ></el-table-column>
      <el-table-column
        prop="deptName"
        label="预约部门"
        width="80"
      ></el-table-column>
      <el-table-column prop="reason" label="病况" width="80"></el-table-column>
      <el-table-column
        prop="inHosptialTime"
        label="预约时间"
        width="180"
      ></el-table-column>
      <el-table-column prop="status" label="预约状态" width="80">
        <!-- <template slot-scope="scope">
          {{ scope.row.status === "1" ? "未审核" : "已审核" }}
        </template> -->
        <template slot-scope="scope">
          <span v-if="scope.row.status=='1'">待审核</span>
          <span v-if="scope.row.status=='2'">已审核</span>
        </template>
      </el-table-column>

      <!-- <el-table-column
                label="头像"
                width="100">
                 <template slot-scope="scope">
                    <img  :src="scope.row.avatar" class="a-avatar" >
                </template>
            </el-table-column> -->

      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="primary"
            @click="
              gotoUpdateInHospitalTable(
                scope.row.id,
                scope.row.patientName,
                scope.row.patientSex,
                scope.row.patientAge,
                scope.row.cardId,
                scope.row.telephone,
                scope.row.reason,
                scope.row.deptName,
                scope.row.inHosptialTime,
                scope.row.status
              )
            "
            >审核</el-button
          >
          <!-- <el-button size="mini" type="danger"
                  @click="gotoDeleteInHospitalTable( scope.row.id)">驳回</el-button> -->
        </template>
      </el-table-column>
    </el-table>

    <el-pagination
      background
      layout="prev, pager,next, sizes,->,total"
      :total="total"
      :current-page="pageNum"
      :page-sizes="[5, 10, 15, 20]"
      :page-size="pageSize"
      @size-change="changeSize"
      @current-change="changePage"
    >
    </el-pagination>

    <!-- 审核预约住院 -->
    <el-dialog :visible.sync="updateInHospitalTableForm">
      <h1 align="center">审核预约住院</h1>
      <br />
      <template>
        <el-descriptions class="margin-top" title="" :column="3" border>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-user"></i>
              姓名
            </template>
            {{ updateInHospitalTable.patientName }}
          </el-descriptions-item>

          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-tickets"></i>
              性别
            </template>
            {{ updateInHospitalTable.patientSex }}
          </el-descriptions-item>

          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-tickets"></i>
              年龄
            </template>
            {{ updateInHospitalTable.patientAge }}
          </el-descriptions-item>

          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-tickets"></i>
              卡号
            </template>
            {{ updateInHospitalTable.cardId }}
          </el-descriptions-item>

          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-tickets"></i>
              电话
            </template>
            {{ updateInHospitalTable.telephone }}
          </el-descriptions-item>

          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-tickets"></i>
              科室
            </template>
            {{
              updateInHospitalTable.deptName === ""
                ? "暂未分配"
                : updateInHospitalTable.deptName
            }}
          </el-descriptions-item>

          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-tickets"></i>
              时间
            </template>
            {{
              updateInHospitalTable.inHosptialTime === ""
                ? "暂未分配"
                : updateInHospitalTable.inHosptialTime
            }}
          </el-descriptions-item>

          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-tickets"></i>
              病因
            </template>
            {{
              updateInHospitalTable.reason === ""
                ? "暂未分配"
                : updateInHospitalTable.reason
            }}
          </el-descriptions-item>

          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-tickets"></i>
              病因
            </template>
            {{ updateInHospitalTable.status === "1" ? "未审核" : "已审核" }}
          </el-descriptions-item>
        </el-descriptions>
      </template>

      <el-divider></el-divider>

      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="doUpdateInHospitalTable"
          >审 核</el-button
        >
        <el-button @click="closeUpdateInHospitalTable">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      patientName: "",
      cardId: "",
      patientSex: "",
      inHospitalTableList: [],
      total: 100,
      pageNum: 1,
      pageSize: 5,

      updateInHospitalTableForm: false, //控制是否显示审核对话框
      formLabelWidth: "60px",
      updateInHospitalTable: {
        id: "",
        patientName: "",
        patientSex: "",
        patientAge: "",
        cardId: "",
        telephone: "",
        reason: "",
        deptName: "",
        inHosptialTime: "",
        status: "",
      },
      headers: {},
    };
  },
  created() {
    this.search();
    this.headers = { tokenStr: window.localStorage.getItem("tokenStr") };
  },
  methods: {
    doUpdateInHospitalTable() {
      //    console.log(res.data)
      var inHospitalTable = this.updateInHospitalTable;
      this.$axios
        .post("/api/doctor/updateInHospitalTable", inHospitalTable)
        .then((res) => {
          if (res.data.status == 4001) {
            this.$message({
              type: "error",
              message: "没有权限!",
              duration: 2000,
            });
          } else {
            this.$message({
              type: "success",
              message: "审核成功!",
              duration: 2000,
            });
            this.closeUpdateInHospitalTable();
            this.search();
          }
        });
    },
    closeUpdateInHospitalTable() {
      this.updateInHospitalTable = {
        id: "",
        patientName: "",
        patientSex: "",
        patientAge: "",
        cardId: "",
        telephone: "",
        reason: "",
        deptName: "",
        inHosptialTime: "",
        status: "",
      };
      this.updateInHospitalTableForm = false;
    },
    gotoUpdateInHospitalTable(
      id,
      patientName,
      patientSex,
      patientAge,
      cardId,
      telephone,
      reason,
      deptName,
      inHosptialTime,
      status
    ) {
      this.updateInHospitalTable = {
        id: id,
        patientName: patientName,
        patientSex: patientSex,
        patientAge: patientAge,
        cardId: cardId,
        telephone: telephone,
        reason: reason,
        deptName: deptName,
        inHosptialTime: inHosptialTime,
        status: status,
      };
      this.updateInHospitalTableForm = true;
    },

    //查询预约住院列表
    search() {
      console.log("---");
      this.$axios
        .get("/api/doctor/getInHospitalTableList", {
          params: {
            patientName: this.patientName,
            cardId: this.cardId,
            patientSex: this.patientSex,
            pageNum: this.pageNum,
            pageSize: this.pageSize,
          },
        })
        .then((res) => {
          console.log("=========================");
          console.log(res.data.data.list);
          this.inHospitalTableList = res.data.data.list;
          this.total = res.data.data.total;
          this.pageNum = res.data.data.pageNum;
          this.pageSize = res.data.data.pageSize;
        });
    },
    changeSize(value) {
      this.pageSize = value;
      this.pageNum = 1;
      this.search();
    },
    changePage(value) {
      this.pageNum = value;
      this.$axios
        .get("/api/doctor/getInHospitalTableList", {
          params: {
            patientName: this.patientName,
            cardId: this.cardId,
            patientSex: this.patientSex,
            pageNum: this.pageNum,
            pageSize: this.pageSize,
          },
        })
        .then((res) => {
          this.consultationApplicationList = res.data.data.list;
          this.total = res.data.data.total;
          this.pageNum = res.data.data.pageNum;
          this.pageSize = res.data.data.pageSize;
        });
    },
    getIndex(i) {
      return i + 1 + this.pageSize * (this.pageNum - 1);
    },
  },
};
</script>

<style scoped>
</style>
