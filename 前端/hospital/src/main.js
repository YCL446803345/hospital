
import Vue from 'vue'
import App from './App.vue'

import Element from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import './assets/css/commons.css'
import axios from './uitls/axiosUtil.js'
//安装element
Vue.use(Element)

Vue.config.productionTip = false

//为VUE实例创建axios的共享的属性
Vue.prototype.$axios = axios

new Vue({
  el: '#app',
  components: {App},
  template: '<App/>'
})

