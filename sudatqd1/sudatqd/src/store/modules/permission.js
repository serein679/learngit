import { asyncRoutes, constantRoutes } from '@/router'
import store from '@/store'
import { login, getUserInfo, logout } from '@/api/login'

/**
 * Use meta.role to determine if the current user has permission
 * @param userType
 * @param route
 */
function hasPermission(userType, route) {
  if (route.meta && route.meta.permissions) {
    return userType.some(userType => route.meta.userType.includes(userType))
  } else {
    return true
  }
}

/**
 * Filter asynchronous routing tables by recursion
 * @param routes asyncRoutes
 * @param userType
 */
export function filterAsyncRoutes(routes, userType) {
  const res = []
  routes.forEach(route => {
    const tmp = { ...route }
    if (hasPermission(userType, tmp)) {
      if (tmp.children) {
        tmp.children = filterAsyncRoutes(tmp.children, userType)
      }
      res.push(tmp)
    }
  })
  return res
}

const state = {
  routes: [],
  addRoutes: []
}

const mutations = {
  SET_ROUTES: (state, routes) => {
    state.addRoutes = routes
    state.routes = constantRoutes.concat(routes)
  }
}

const actions = {
  generateRoutes(userType) {
    return new Promise(resolve => {
      const accessedRoutes = filterAsyncRoutes(asyncRoutes, userType)
      this.routers = constantRoutes.concat(accessedRoutes)
      resolve(this.routers)

    })

  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
