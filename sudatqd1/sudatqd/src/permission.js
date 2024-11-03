import router from './router'
import { getToken, removeToken } from '@/utils/auth'
import store from '@/store'
import user from './store/modules/user'
import { login, getUserInfo, logout } from '@/api/login'
// NProgress.configure({ showSpinner: false }) // NProgress Configuration
const whiteList = [
    '/',
    '/dashboard',
    'user/logout',
    '/question/index',
    '/login/index',
    '/login/passportFin',
    '/introduction/echarts',
    '/introduction/echartsAr',
    '/introduction/echartsIn',
    '/login/enroll',
    '/introduction/',
    '/introduction/select',
    '/introduction/selectAr',
    '/introduction/selectIn',
    '/introduction/selectMa',
    '/fullySearch',
    '/resource',
    '/course',
    '/forum',
    '/teacher',
    '/student',
    '/teacherDetail',
    '/project',
    '/projectDetail',
    '/teachers',
    '/introduction/index',
    '/resource/index',
    '/resource/select',
    '/course/index',
    '/teachers/index',
    '/introduction/adetails',
    '/introduction/1',
    '/resource/detail',
    '/course/detail',
    '/teachers/detail',
    '/login/passportFind',
    // '/management/projectManagement',
]

// router.beforeEach(async (to, from, next) => {
//     const userPermissions = sessionStorage.getItem('SET_USERTYPE');
//     const requiredPermission = to.meta.permission;
//     const token = getToken()
//     if (token) {
//         console.log(token)
//         if (requiredPermission && !userPermissions.includes(requiredPermission)) {
//             console.log(requiredPermission)
//             console.log(userPermissions)
//             next(
//                 { name: "401" }
//             )
//         }
//         else {
//             next();
//         }
//         next()
//         return
//     } else {
//         if (whiteList.indexOf(to.path) !== -1)
//             next()
//         else {
//             alert('请先登录！')
//             next({ path: '/login/index' })
//             return
//         }
//     }
// })


