import axios from 'axios'
import store from '@/store'
// import { MessageBox, Message } from 'element-ui'
import { Message } from 'element-ui'
// import store from '@/store'
// import { getToken } from '@/utils/auth'
axios.defaults.headers['Content-Type'] = 'application/json;charset=utf-8'
// create an axios instance
const service = axios.create({
  baseURL: process.env.VUE_APP_BASE_API ,// url = base url + request url
  // withCredentials: true, // send cookies when cross-domain requests
  timeout: 5000 // request timeout
})
// request interceptor
service.interceptors.request.use(

  config => {
    // do something before request is sent
    if (sessionStorage.getItem('SET_TOKEN')) {
      // config.headers['Authorization'] = 'Bearer ' + getToken()
      config.headers.Authorization = sessionStorage.getItem('SET_TOKEN')
    }
    return config
  },
  error => {
    // do something with request error
    console.log(error.response) // for debug
    return Promise.reject(error)
  }
)

// response interceptor
service.interceptors.response.use(
  /**
   * If you want to get http information such as headers or status
   * Please return  response => response
  */
  /**
   * Determine the request status by custom code
   * Here is just an example
   * You can also judge the status by HTTP Status Code
   */
  response => {
    const res = response.data
    if (res.code === 401) {
      Message({
        message: '登录超时，请重新登录',
        type: 'error',
        duration: 2500
      })
      store.dispatch('user/logout').then(() => {
        location.reload(true)
      })
    } else if (res.code === 400) {
      Message({
        message: '服务器出错，请稍后再试',
        type: 'error',
        duration: 2500
      })
      this.$router.replace({
        path: '/'
      })
    } else if (res.code !== null) {
      return res
    }
  },
  error => {
    console.log('err' + error.message) // for debug
    const { status } = error.response
    if (status === 401) {
      Message({
        message: '登录超时，请重新登录',
        type: 'error',
        duration: 2500
      })
      store.dispatch('user/logout').then(() => {
        location.reload(true)
      })
    } else if (status === 400) {
      Message({
        message: '服务器出错，请稍后再试',
        type: 'error',
        duration: 2500
      })
      this.$router.replace({
        path: '/'
      })
    }
  }
)

export default service
