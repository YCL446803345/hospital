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
</style>
