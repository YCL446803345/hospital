<template>
 <!-- container容器 -->
  <el-container style="height:100%">
    <!-- 头部 -->
    <el-header style="height:80px">
      <el-row>
        <el-col :span="8" style="text-align:left;"><img style="height:80px"></el-col>
        <el-col :span="8"><h1 style="color:#00B54E;font-size:40px">成都协和医院系统</h1></el-col>
        <el-col :span="8">
          
          <!-- <span style="color:black">欢迎光临 
          
               <el-popover
               placement="right"
               width="400"
               trigger="hover">
               <el-descriptions title="个人信息中心" direction="vertical" :column="4" border>
               <el-descriptions-item label="用户名">{{doctorInfo.name}}</el-descriptions-item>
               <el-descriptions-item label="部门" :span="2">{{doctorInfo.deptName}}</el-descriptions-item>
               <el-descriptions-item label="职位">
               <el-tag size="small">{{doctorInfo.roleName}}</el-tag>
               </el-descriptions-item>
               <el-descriptions-item label="卡号">{{doctorInfo.card}}</el-descriptions-item>
               </el-descriptions>
               <el-button slot="reference">
                     <Avatar  :username="doctorName+''"

                     background-color="rgb(60, 119, 248)"
                     color="#fff"
                     style=" vertical-align: middle;"
                     :inline="true">
                     </Avatar>  
               </el-button>
          </el-popover>
          </span> -->
          <el-button type="info"  circle  @click="logout()">退出</el-button>
          </el-col>
      </el-row>
    </el-header>
    <el-container style="height:100%">

      <!-- 左侧 -->
      <el-aside width="200px" >
         <el-row class="tac" style="height:100%">
               <el-col :span="24">
                     <el-menu
                     :router="true"
                     style="height:100%"
                     default-active="2"
                     class="el-menu-vertical-demo"
                     background-color="#F5F5F5"
                     text-color="#252526"
                     active-text-color="#00A0FF">
                     <el-menu-item index="/showinfo">
                        <i class="el-icon-setting"></i>
                        <span slot="title">后台首页</span>
                     </el-menu-item>

                     <el-submenu :index="String(menu.id)" v-for="menu in menuDate" :key="menu.id">
                        <template slot="title">
                           <i class="el-icon-office-building"></i>
                           <span>{{menu.name}}</span>
                        </template>
                           <el-menu-item :index="item.pageLink" v-for="item in menu.children" :key="item.id">
                              <i class="el-icon-menu"></i>
                              <span slot="title">{{item.name}}</span>
                           </el-menu-item>
                     </el-submenu>
                     </el-menu>
                  </el-col>
            </el-row>
      </el-aside>

      <!-- 中间主要的 -->
      <el-main>
        <router-view></router-view>
      </el-main>

    </el-container>
  </el-container>
</template>

<script>
export default {
   data() {
      return {
         account:"",
         menuDate:[]
      }
   },
   methods:{

   },
   created(){
      //从本地浏览器拿名字
   this.account = window.localStorage.getItem("account")
    this.$axios.get("api/perms/findMenuPerms",{
       params:{
          account:this.account
       }
    }).then(res=>{
       console.log(res.data)
       if (res.data.status == 200) {
          this.menuDate=res.data.data
       }
    })
   }
}
</script>

<style>

</style>
