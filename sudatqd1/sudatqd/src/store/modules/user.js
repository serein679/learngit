import { login, getUserInfo, logout } from '@/api/login'
import { getToken, setToken, removeToken } from '@/utils/auth'
import router, { resetRouter } from '@/router'
import { Message } from 'element-ui'
import Cookies from 'js-cookie'

const state = {
  token: getToken(),
  name: '',
  roles: [],
  userType: [],
}

const mutations = {
  SET_TOKEN: (state, token) => {
    state.token = token
  },
  SET_ACCOUNT: (state, token) => {
    state.account = account
  },
  SET_NAME: (state, name) => {
    state.name = name
  },
  SET_USERID: (state, userId) => {
    state.userId = userId
  },
  SET_USERTYPE: (state, userType) => {
    state.userType = userType
  },
}

const actions = {
  // 用户登录
  login({ commit }, loginForm) {
    const { act, pwd } = loginForm
    //const uact = userInfo.act.trim()
    return new Promise((resolve, reject) => {
      login({ username: act, userpass: pwd }).then(response => {
        if (!response.data) {
          Message({
            message: response.message,
            type: 'error'
          })
        } else {
          commit('SET_TOKEN', response.data.token)
          sessionStorage.setItem('SET_TOKEN', response.data.token)
          sessionStorage.setItem('SET_ACCOUNT', response.data.useraccount)
          sessionStorage.setItem('SET_USERID', response.data.userId)
          sessionStorage.setItem('SET_NAME', response.data.username)
          sessionStorage.setItem('SET_USERTYPE', response.data.userType)
          // sessionStorage.setItem('SET_PMS', response.data.permissions)
          setToken(response.data.token)
          resolve()
          Message({
            message: "登录成功！",
            type: 'success'
          })
        }
      }).catch(error => {
        reject(error)
      })
    })
  },

  // get用户信息
  getInfo({ commit }) {
    return new Promise((resolve, reject) => {
      sessionStorage.setItem('SET_PERMISSIONS', [])
      getUserInfo().then(response => {
        //var realroles = []
        var realpermissions = []
        if (response.data) {
          //realroles = response.data.fields
          realpermissions = response.data.permissions
          sessionStorage.setItem('SET_PERMISSIONS', response.data.permissions)
        } else {
          //realroles = [0]
          realpermissions = [0]
        }
        const {
          data
        } = {
          data: {
            //roles: realroles, // 身份
            permissions: realpermissions
          }
        }
        if (!data) {
          reject('验证失败，请重新登录.')
        }

        const { roles, name, token } = data
        // if (!roles || roles.length <= 0) {
        //   reject('该成员角色无效!')
        // }
        commit('SET_ROLES', roles)
        commit('SET_NAME', name)
        commit('SET_TOKEN', token)
        resolve(data)
      })
    })
  },

  // 用户登出，直接把缓存全部清掉
  logout({ commit, state, dispatch }) {
    return new Promise((resolve, reject) => {
      //logout().then(response => {
      //if (response.data) {
      state.token = ''
      commit('SET_TOKEN', '')
      commit('SET_NAME', '')
      sessionStorage.setItem('SET_TOKEN', '')
      sessionStorage.setItem('SET_NAME', '')
      sessionStorage.setItem('SET_USERTYPE', '')
      sessionStorage.setItem('SET_ACCOUNT', '')
      sessionStorage.setItem('SET_PMS', '')
      sessionStorage.setItem('SET_USERID', '')

      Cookies.remove('Admin-Token')
      removeToken()
      resetRouter()
      //dispatch('tagsView/delAllViews', null, { root: true })
      resolve()
      Message({
        message: "已退出登录！",
        //type: 'error'
      })
    })
  },

  // remove token
  resetToken({ commit }) {
    return new Promise(resolve => {
      commit('SET_TOKEN', '')
      commit('SET_ROLES', [])
      removeToken()
      resolve()
    })
  },

  // dynamically modify permissions
  async changeRoles({ commit, dispatch }, role) {
    const token = role + '-token'

    commit('SET_TOKEN', token)
    setToken(token)
    const { roles } = await dispatch('getInfo')

    resetRouter()

    // generate accessible routes map based on roles
    const accessRoutes = await dispatch('permission/generateRoutes', roles, { root: true })
    // dynamically add accessible routes
    router.addRoutes(accessRoutes)
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
