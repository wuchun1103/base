// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import api from './http'
import i18n from './i18n'
import store from './store'
import global from '@/utils/global'
import ElementUI from 'element-ui'
// import 'element-ui/lib/theme-chalk/index.css';
import 'element-ui/lib/theme-chalk/index.css' // 默认主题
import 'font-awesome/css/font-awesome.min.css'
import VueQuillEditor from 'vue-quill-editor'
import 'quill/dist/quill.core.css'
import 'quill/dist/quill.snow.css'
import 'quill/dist/quill.bubble.css'

import VueParticles from 'vue-particles'//粒子效果
// 引入echarts
import echarts from 'echarts'

Vue.prototype.$echarts = echarts


Vue.use(VueParticles)
Vue.use(ElementUI)
Vue.use(VueQuillEditor)
Vue.use(api)

Vue.prototype.global = global

/* eslint-disable no-new */
new Vue({
  el: '#app',
  i18n,
  router,
  store,
  render: h => h(App)
})
