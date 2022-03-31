<template>
   <el-container>
      <el-aside :style="{width:collpaseWidth}">
         <!--default-active代表导航栏默认选中哪个index, :collapse决定导航栏是否展开，为boolean类型
            :router决定导航栏是否开启路由模式，即在菜单item上设置路由是否生效，值为boolean类型-->
         <el-menu
                     default-active="0"
                     class="el-menu-vertical-demo"
                     :collapse="isCollapse"
                     :router="true"
            >
               <!--index设置当前item的下标，:route则是传一个对象进行，指定路由-->
              <a href="#" @click="changeCollapse" style="font-size: 25px;color:#909399;"><i
                                 :class="collpaseIcon"></i></a>

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
      </el-aside>

         <el-container>
            <el-header style="height:80px;">
               <el-row style="margin: 15px 15px">
                     <el-col :span="9"><h1>成都协和医院</h1></el-col>
                     <el-col :span="9"><h1>职工后台</h1></el-col>
                     <el-col :span="6" class="col_r">
                                 <el-dropdown @command="handleCommand">
                                 <el-avatar shape="square"  :size="60" :fit="fits" :src="AcatarUrl"></el-avatar>
                                 <el-dropdown-menu slot="dropdown">
                                    <el-dropdown-item command="workerCenter">个人中心</el-dropdown-item>
                                    <el-dropdown-item command="logout">退出</el-dropdown-item>
                                 </el-dropdown-menu>
                                 </el-dropdown>
                     </el-col>
               </el-row>
            </el-header>

            <el-main>
                  <!--路由渲染-->
                  <router-view></router-view>
            </el-main>
            <el-footer class="footer"><h4>版权所有@成都协和医院 地址：四川省成都市二环路东五段成仁路口 联系电话：028 82216779 Copyright 2014-2022 Macgn Association All Rights 保留所有权利</h4></el-footer>
      </el-container>
   </el-container>

</template>

<script>
export default {
   data() {
      return {
         isCollapse: false, // 决定左侧导航栏是否展开
         // imgSrc:require("../../assets/css/image/OIP-C"),   //字体log图片
          fits: 'scale-down',
         AcatarUrl:require('../../assets/css/image/鸟.jpeg'), //头像图片
         account:"",
         menuDate:[]
      }
   },
   computed: {
            collpaseIcon: function () { // 左侧导航栏是否展开状态的图标
                // 如果是展开状态就图标向右，否则图标向左
                return this.isCollapse ? 'el-icon-s-fold' : 'el-icon-s-unfold';
            },
            collpaseWidth: function () { // 左侧导航栏是否展开状态的宽度
                // 如果是展开状态就导航栏宽度为65px，否则200px
                return this.isCollapse ? '65px' : '200px';
            }
        },
   methods:{
      handleCommand(command){
         if (command == "workerCenter") {
            this.$router.push('/workerCenter')
         }
         if (command == "logout") {
                      this.$confirm('是否退出本系统?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
           window.localStorage.removeItem("tokenStr");
           this.$router.push("/gotoWorkesLogin");
        })
         }

      },
      changeCollapse: function () { // 更改左侧导航栏展示状态
                this.isCollapse = !this.isCollapse;
      },

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

<style scoped >

   /*整体显示区域布局样式*/
    .el-container {
        height: 100%;
    }

    .el-header {
        padding: 0;
         background: #f1f1f1;

    }
   .el-main {
        padding: 0;
    }

    /*左边导航栏具体样式*/
    .el-menu-vertical-demo.el-menu {
       background: #f1f1f1;
        padding-left: 20px;
        text-align: left;
        height: 100%;
        padding: 0;
    }

    el-container > .el-menu-vertical-demo.el-menu {
        padding: 0;
    }

    .el-submenu .el-menu-item, .el-menu-item {
        min-width: 50px;
    }

    .el-menu-item {
        padding: 0;
        text-align: center;
    }
    .footer{
       background:#E7EBEE;
    }
    h4{
       text-align: center;
       line-height: 50px;
    }

</style>
