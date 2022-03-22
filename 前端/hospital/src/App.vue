<template>
  <div id="app">
      <!-- 路由出口 -->
      <router-view></router-view>
  </div>
</template>

<script>
//导入路由
import router from './router/router.js'

export default {
  components: {

  },
  //注册路由
  router
}
</script>

<style>
      /* // 整体布局样式，让整个视图都铺满
    // html, body, #app {
    //     height: 100%;
    //     width: 100%;
    //     margin: 0;
    //     padding: 0;
    // } */

</style>
