import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import echarts from 'echarts'
import './permission'
import 'default-passive-events'
import 'normalize.css/normalize.css' // A modern alternative to CSS resets
import Element from 'element-ui'
import { Upload } from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css';
// import './theme/element-variables.scss'
import axios from 'axios'
import Cookies from 'js-cookie'
//因为scrollTo是一个方法所以要用{}包裹
import { scroller } from '@/scroll/scrollTo.js'
// import "swiper/swiper.min.css"
// 全局注册
// 引入Viewer插件的图片预览器的样式
import VueAMap from 'vue-amap';
import mavonEditor from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'
//Url加密
import BaiduMap from 'vue-baidu-map'
import Base64 from './assets/js/base64.js'
// import 'viewerjs/dist/viewer.css'
// import Viewer from 'v-viewer'
// Vue.use(Viewer)

// import timelineCarousel from 'timeline-carousel'
// Vue.use(timelineCarousel)
axios.defaults.baseURL = 'http://111.231.53.121:9998/'
// axios.defaults.baseURL = 'http://localhost:9998/'
// VUE_APP_BASE_API = 'http://localhost:9998/'
Vue.config.productionTip = false
Vue.use(Upload)
Vue.prototype.$Base64 = Base64;
//把scrollTo挂载到vue原型上，想用就用（为什么叫$scrollTo，这个是自定义的，喜欢叫什么就定义什么，本人出于自己的习惯喜欢加个$+方法名）
Vue.prototype.$scrollTo = scroller()
Vue.prototype.$echarts = echarts
Vue.prototype.$baseURL = process.env.VUE_APP_BASE_API
// use
Vue.use(mavonEditor)
// new Vue({
//   'el': '#main'
// })
Vue.use(VueAMap);
VueAMap.initAMapApiLoader({
  //高德key  
  key: '909ea768de1ecc4a0343ed23ae9d1be1',
  //插件集合  
  plugin: ['AMap.Scale', 'AMap.OverView', 'AMap.ToolBar', 'AMap.MapType', 'AMap.Geocoder'],
  //高德地图版本  
  v: '1.4.4'
});

Vue.use(BaiduMap, {
  ak: 'qSnlEuO9cBNRGwWXvEdyJagc6RpNlYZc'  //这个地方是官方提供的ak密钥
})

// if (process.env.NODE_ENV === 'production') {
//   const { mockXHR } = require('../mock')
//   mockXHR()
// }

Vue.use(Element, {
  size: Cookies.get('size') || 'medium', // set element-ui default size
  //locale: enLang // 如果使用中文，无需设置，请删除
})

new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App)
})

// 请求拦截器，用于做登录验证
axios.interceptors.request.use(config => {
  // 为每一次调用api挂载请求头，用于验证有权限的api调用
  config.headers.Authorization = sessionStorage.getItem('SET_TOKEN')
  return config
})
axios.defaults.baseURL = '/api'

// 在response拦截器中，隐藏进度条 NProgress.done()
axios.interceptors.response.use(config => {
  return config
},
  error => {
    //在此处处理网络请求的错误
    console.log(error)
    Vue.prototype.$message.error('网络请求失败，请检查网络连接：' + error.response.data)
  })

Vue.prototype.$http = axios
// Vue.prototype.$axios = axios
Vue.config.productionTip = false


