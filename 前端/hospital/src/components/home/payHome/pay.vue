<template>
    <div>
        <!-- 面包xie导航 -->
        <br>
        <el-breadcrumb separator="/">
            <el-breadcrumb-item :to="{ path: '/gotoHome' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item><a href="#/pay/inPay">在院病人信息管理</a></el-breadcrumb-item>
            <el-breadcrumb-item>在院病人信息管理列表</el-breadcrumb-item>
        </el-breadcrumb>
        <el-row style="margin-top:10px;margin-bottom:10px">
            <el-col :span="4">
                <el-input v-model="name" placeholder="姓名">
                </el-input>
            </el-col>
            
            <el-col :span="2">
                <el-select v-model="gender" placeholder="性别">
                    <el-option label="性别" value="" ></el-option>
                    <el-option label="男" value="1" ></el-option>
                    <el-option label="女" value="2" ></el-option>
                </el-select>
            </el-col>

            <el-col :span="8">
                <el-input v-model="cardId" placeholder="身份证">
                </el-input>
                <!--带搜索按钮的文本框 -->
            </el-col>
           
            <el-col :span="1.5" style="margin-left:2px;">
                <el-button type="success" @click="search">查询</el-button>
               
            </el-col>

            <el-col :span="1" style="margin-left:2px;">
                 <el-button type="warning" @click="name='',paheNum=1,pageSize=5,gender='',cardId=''">清空</el-button>
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
                width="180">
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
                width="50">
                <template slot-scope="scope">
                         {{scope.row.gender==='1'?'男':'女'}}
                </template>
            </el-table-column>

             
            <el-table-column
                prop="age"
                label="年龄"
                width="50">
            </el-table-column>

            <el-table-column
                prop="cardId"
                label="身份证号"
                width="180">
            </el-table-column>

            <el-table-column
                prop="phone"
                label="手机号"
                width="120">
            </el-table-column>

            <!-- <el-table-column
                prop="balance"
                label="余额"
                width="120">
                 <template slot-scope="scope">
                         {{scope.row.balance===null?0:scope.row.balance===null}}
                </template>
            </el-table-column> -->
            
            <el-table-column label="操作">
               <template slot-scope="scope">
                  <el-button
                  size="mini"
                  type="success"
                  @click="pay(scope.row.id)">支付宝充值</el-button>
                    <el-button
                  size="mini"
                  type="warning"
                  @click="payCash(scope.row.id)">现金充值</el-button>
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

    </div>
</template>

<script>
export default {
   data() {
      return {
        no:'',
        name:'',
        gender:'',
        cardId:'',
        status:1,
        patientList: [],
        total:100,
        pageNum:1,
        pageSize:5,
        updatePatientForm:false,
        formLabelWidth: '120px'
      }
   },
   created(){
      this.search();
   },
   methods:{
      //查询病人信息列表
        search(){
            this.$axios.get("/api/getAllInPatient",{params:{name:this.name,no:this.no,gender:this.gender,
                  cardId:this.cardId,pageNum:this.pageNum,pageSize:this.pageSize}})
            .then(res=>{
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
            this.search();
            // this.$axios.get("/api/getAllInPatient",{params:{name:this.searchName,no:this.no,gender:this.gender,
            //       caedId:this.cardId,pageNum:this.pageNum,pageSize:this.pageSize}})
            // .then(res=>{
            //     this.patientList=res.data.list;
            //     this.total=res.data.total;
            //     this.pageNum=res.data.pageNum;
            //     this.pageSize=res.data.pageSize;
            // })
        },
          getIndex(i){
            return (i+1)+this.pageSize*(this.pageNum-1);
         },
         pay(id){
             let regu =  /^(\+)?\d+(\.\d+)?$/;
             this.$prompt('请输入充值金额', '支付宝充值', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'success',
                 inputErrorMessage:'只能输入正数字！',
                inputValidator:(value)=>{
                    if(regu.test(value)){
                        return true;
                    }else{
                        return false;
                    }
                }
              }).then((value) => {
                    this.$axios.get("/api/gotoPay",{params:{patientId:id,newMoney:value.value,status:"1"}}).then(res=>{
                        const divForm = document.getElementsByTagName("div");
                        if (divForm.length) {
                            document.body.removeChild(divForm[0]);
                        }
                        const div = document.createElement("div");
                        div.innerHTML = res.data;
                        document.body.appendChild(div);
                        document.forms[0].submit();
                        document.forms[0].setAttribute("target", "_blank"); // 新开窗口跳转
                    })
              }).catch(() => {
                this.$message({
                  type: 'info',
                  message: '已取消缴费'
                }); 
              })
        },
        //现金支付
        payCash(id){
            let regu =  /^(\+)?\d+(\.\d+)?$/;
            this.$prompt('请输入充值金额', '现金充值', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'success',
                 inputErrorMessage:'只能输入正数字！',
                inputValidator:(value)=>{
                    if(regu.test(value)){
                        return true;
                    }else{
                        return false;
                    }
                }
              }).then((value) => {
                    this.$axios.get("/api/addMoney",{params:{id:id,money:value.value,status:"1"}}).then(res=>{
                        if(res.data === "OK"){
                             this.$message({
                                type: "success",
                                message: "支付成功!",
                            });
                            this.search();
                        }else{
                           this.$message({
                                type: "danger",
                                message: "支付失败!",
                            }); 
                        }
                    })
              }).catch(() => {
                this.$message({
                  type: 'info',
                  message: '已取消缴费'
                }); 
              })
        },
   }
}
</script>

<style scoped>

</style>
