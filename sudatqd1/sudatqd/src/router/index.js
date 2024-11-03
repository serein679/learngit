import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

/* Layout */
import Layout from '@/layout/index'
import Layout1 from '@/layout/houtai'
import Layout2 from '@/layout/houtai1'

const originalPush = VueRouter.prototype.push
const originalReplace = VueRouter.prototype.replace
VueRouter.prototype.push = function push(location, onResolve, onReject) {
        if (onResolve || onReject) return originalPush.call(this, location, onResolve, onReject)
        return originalPush.call(this, location).catch(err => err)
    }
    // replace
VueRouter.prototype.replace = function push(location, onResolve, onReject) {
    if (onResolve || onReject) return originalReplace.call(this, location, onResolve, onReject)
    return originalReplace.call(this, location).catch(err => err)
}
export const asyncRoutes = [

    {
        path: '/redirect',
        component: Layout,
        hidden: true,
        children: [{
            path: '/redirect/:path(.*)',
            component: () =>
                import ('@/views/redirect/index')
        }]
    },
    {
        path: '/hidden',
        component: Layout,
        hidden: true,
        children: [{
            path: 'hiddened',
            name: '401',
            component: () =>
                import ('@/views/hidden/hiddened')
        }]
    },

    {
        path: '/management',
        component: Layout1,
        hidden: true,
        children: [{
                path: 'resourceManagement',
                name: '资源管理吧',
                // path: '/management/:path(.*)',
                component: () =>
                    import ('@/views/management/resourceManagement'),
                // meta: { requiresAuth: true, roles: [0] },
                meta: { permission: 1 },
                // meta: { title: '资源管理', noCache: true }
            },
            {
                path: 'addProject',
                name: '项目管理',
                // path: '/management/:path(.*)',
                component: () =>
                    import ('@/views/management/addProject'),
                // meta: { title: '资源管理', noCache: true }
                meta: { permission: 1 },

            },

            {
                path: 'editorProject',
                name: '项目管理2',
                // path: '/management/:path(.*)',
                component: () =>
                    import ('@/views/management/editorProject'),
                meta: { permission: 1 },

            },
            {
                path: 'addMien',
                name: '风采管理',
                // path: '/management/:path(.*)',
                component: () =>
                    import ('@/views/management/addMien'),
                // meta: { title: '资源管理', noCache: true }
                meta: { permission: 1 },
            },
            {
                path: 'editorTeacher',
                name: '资源管理不v',
                // path: '/management/:path(.*)',
                component: () =>
                    import ('@/views/management/editorTeacher'),
                // meta: { title: '资源管理', noCache: true }
                meta: { permission: 1 },

            },

            {
                path: 'addTeacher',
                name: '资源管理突然',
                // path: '/management/:path(.*)',
                component: () =>
                    import ('@/views/management/addTeacher'),
                // meta: { title: '资源管理', noCache: true }
                meta: { permission: 1 },


            },
            {
                path: 'editorMien',
                name: '资源管理的',
                // path: '/management/:path(.*)',
                component: () =>
                    import ('@/views/management/editorMien'),
                // meta: { title: '资源管理', noCache: true }
                meta: { permission: 1 },

            },
            {
                path: 'addAchv',
                name: '资源管理5',
                // path: '/management/:path(.*)',
                component: () =>
                    import ('@/views/management/addAchv'),
                // meta: { title: '资源管理', noCache: true }
                meta: { permission: 1 },

            },
            {
                path: 'editorAchv',
                name: '资源管理55',
                // path: '/management/:path(.*)',
                component: () =>
                    import ('@/views/management/editorAchv'),
                // meta: { title: '资源管理', noCache: true }
                meta: { permission: 1 },

            },
            {
                path: 'projectManagement',
                name: '资源管理6',
                // path: '/management/:path(.*)',
                component: () =>
                    import ('@/views/management/projectManagement'),
                meta: { permission: 1 },

                // meta: { title: '资源管理', noCache: true }
            },
            {
                path: 'userManagement',
                name: '用户管理7',
                // path: '/management/:path(.*)',
                component: () =>
                    import ('@/views/management/userManagement'),
                meta: { permission: 1 },
                // meta: { title: '资源管理', noCache: true }
            },
            {
                path: 'introManagement',
                name: '简介管理',
                // path: '/management/:path(.*)',
                component: () =>
                    import ('@/views/management/introManagement'),
                meta: { title: "资源管理", permission: 1, noCache: true },
                // meta: { title: '资源管理', noCache: true }
            },
            {
                path: 'editorIntro',
                name: '资源管理8',
                // path: '/management/:path(.*)',
                component: () =>
                    import ('@/views/management/editorIntro'),
                // meta: { title: '资源管理', noCache: true }
                meta: { permission: 1 },

            },
            {
                path: 'addIntro',
                name: '资源管理9',
                // path: '/management/:path(.*)',
                component: () =>
                    import ('@/views/management/addIntro'),
                // meta: { title: '资源管理', noCache: true }
                meta: { permission: 1 },

            },
            {
                path: 'forumManagement',
                name: '资源管理2',
                // path: '/management/:path(.*)',
                component: () =>
                    import ('@/views/management/forumManagement'),
                meta: { permission: 1 },

                // meta: { title: '东吴论坛管理', noCache: true }
            },

            {
                path: 'editorIntro',
                component: () =>
                    import ('@/views/management/editorIntro'),
                hidden: true,
                meta: { permission: 1 },

            },
            {
                path: 'addIntro',
                component: () =>
                    import ('@/views/management/addIntro'),
                meta: { permission: 1 },
                hidden: true
            },
            {
                path: 'mienManagement',
                component: () =>
                    import ('@/views/management/mienManagement'),
                meta: { permission: 1 },
                hidden: true
            },

            {
                path: 'cooperationManagement',
                component: () =>
                    import ('@/views/management/cooperationManagement'),
                meta: { permission: 1 },
                hidden: true
            },

            {
                path: 'introManagement',
                component: () =>
                    import ('@/views/management/introManagement'),
                meta: { permission: 1 },
                // meta: { permission: 1 },
                hidden: true
            },
            {
                path: 'achvManagement',
                component: () =>
                    import ('@/views/management/achvManagement'),
                meta: { permission: 1 },
                hidden: true
            },
            {
                path: 'wordManagement',
                component: () =>
                    import ('@/views/management/wordManagement'),
                meta: { permission: 1 },
                hidden: true
            },
            {
                path: 'permissionManagement',
                component: () =>
                    import ('@/views/management/permissionManagement'),
                meta: { permission: 1 },
                hidden: true
            },
            {
                path: 'addUser',
                component: () =>
                    import ('@/views/management/addUser'),
                meta: { permission: 1 },
                hidden: true
            },
            {
                path: 'index',
                name: '资源管理',
                // path: '/management/:path(.*)',
                component: () =>
                    import ('@/views/management/index'),
                // meta: { requiresAuth: true, roles: [0] },
                meta: { permission: 1 },
            }
        ]
    },


    {
        path: '/management',
        component: () =>
            import ('@/views/management/achvManagement'),
        meta: { permission: 1 },

        hidden: true
    },

    {
        path: '/management',
        component: () =>
            import ('@/views/management/introManagement'),
        meta: { permission: 1 },

        // hidden: true
    },
    {
        path: '/management',
        component: () =>
            import ('@/views/management/resourceManagement'),
        meta: { permission: 1 },
        hidden: true
    },
    {
        path: '/management',
        component: () =>
            import ('@/views/management/projectManagement'),
        meta: { permission: 1 },

        hidden: true
    },
    {
        path: '/management',
        component: () =>
            import ('@/views/management/addUser'),
        meta: { permission: 1 },

        hidden: true
    },


    {
        path: '/management',
        component: () =>
            import ('@/views/management/cooperationManagement'),
        meta: { permission: 1 },
        hidden: true
    },
    {
        path: '/management',
        component: () =>
            import ('@/views/management/userManagement'),
        meta: { permission: 1 },
        hidden: true
    },
    {
        path: '/management',
        component: () =>
            import ('@/views/management/forumManagement'),
        meta: { permission: 1 },

        hidden: true
    },
    {
        path: '/management',
        component: () =>
            import ('@/views/management/permissionManagement'),
        meta: { permission: 1 },
        hidden: true
    },
    {
        path: '/management',
        component: () =>
            import ('@/views/management/mienManagement'),
        meta: { permission: 1 },

        hidden: true
    },

    {
        path: '/teachers',
        component: Layout,
        redirect: '/teachers/detail',
        children: [{
            path: 'detail',
            component: () =>
                import ('@/views/teachers/detail'),
            name: 'detail1',
            meta: { title: 'detail', noCache: true }
        }]
    },
    {
        path: '/login',
        component: Layout,
        redirect: '/login/index',
        children: [{
            path: 'index',
            component: () =>
                import ('@/views/login/index'),
            name: 'detail2',
            meta: { title: 'detail', noCache: true }
        }]
    },

    {
        path: '/login',
        component: Layout,
        redirect: '/login/passportFind',
        children: [{
                path: 'passportFind',
                component: () =>
                    import ('@/views/login/passportFind'),
                name: 'passportFind',
                meta: { title: 'passportFind', noCache: true }
            },
            {
                path: 'enroll',
                component: () =>
                    import ('@/views/login/enroll'),
                name: 'enroll',
                meta: { title: 'enroll', noCache: true }
            },
            {
                path: 'passportFin',
                component: () =>
                    import ('@/views/login/passportFin'),
                name: 'passportFin',
                meta: { title: 'passportFin', noCache: true }
            },
            {
                path: 'userInfo',
                component: () =>
                    import ('@/views/login/userInfo'),
                name: 'userInfo',
                meta: { title: 'userInfo', noCache: true }
            },
            {
                path: 'index',
                component: () =>
                    import ('@/views/login/index'),
                name: 'index',
                meta: { title: 'index', noCache: true }
            }
        ]
    },

    // 首页
    {
        path: '/',
        component: Layout,
        redirect: '/dashboard',
        children: [{
                path: 'dashboard',
                name: 'Dashboard',
                component: () =>
                    import ('@/views/dashboard/index'),
                meta: { title: '首页' }
            },
            {
                path: 'forum',
                component: () =>
                    import ('@/views/dashboard/forum'),
                name: '东吴论坛',
                meta: { title: '项目展示', },
            },



            {
                path: 'fullySearch',
                component: () =>
                    import ('@/views/dashboard/fullySearch'),
                name: '项目展示',
                meta: { title: '项目展示' },
            },

        ]
    },
    // 专业介绍
    {
        path: '/introduction',
        component: Layout,
        redirect: '/introduction/index',
        children: [{
                path: 'index',
                component: () =>
                    import ('@/views/introduction/index'),
                name: '专业介绍1',
                meta: { title: '专业介绍' }
            },
            {
                path: 'add',
                component: () =>
                    import ('@/views/introduction/add'),
                name: '专业介绍',
                meta: { permission: 0 },
            },
            {
                path: 'echarts',
                component: () =>
                    import ('@/views/introduction/echarts'),
                name: 'echarts图1',
                meta: { title: 'echarts图', noCache: true }
            },
            {
                path: 'echartsIn',
                component: () =>
                    import ('@/views/introduction/echartsIn'),
                name: 'echarts图2',
                meta: { title: 'echarts图', noCache: true }
            },
            {
                path: 'echartsAr',
                component: () =>
                    import ('@/views/introduction/echartsAr'),
                name: 'echarts图',
                meta: { title: 'echarts图', noCache: true }
            },
            // 新增路由//看这里
            {
                path: 'selectIn',
                component: () =>
                    import ('@/views/introduction/selectIn'),
                name: '国家级2',
                meta: { title: '国家级' },
            },
            {
                path: 'selectMa',
                component: () =>
                    import ('@/views/introduction/selectMa'),
                name: '国家级',
                meta: { title: '国家级' },
            },
            {
                path: 'select',
                component: () =>
                    import ('@/views/introduction/select'),
                name: '3',
                meta: { title: 'detail' }
            },
            {
                path: 'selectAr',
                component: () =>
                    import ('@/views/introduction/selectAr'),
                name: 'detail4',
                meta: { title: 'detail', noCache: true }
            },
            {
                path: 'selectXin',
                component: () =>
                    import ('@/views/introduction/selectXin'),
                name: 'detail4',
                meta: { title: 'detail', noCache: true }
            },

        ]
    },

    // 教学资源
    {
        path: '/resource',
        component: Layout,
        redirect: '/resource/index',
        children: [{
            path: 'index',
            component: () =>
                import ('@/views/resource/index'),
            name: '教学资源',
            meta: { title: '教学资源', noCache: true }
        }]
    },
    {
        path: '/question',
        component: Layout,
        redirect: '/question/index',
        children: [{
            path: 'index',
            component: () =>
                import ('@/views/question/index'),
            name: '图像识别',
            meta: { title: '图像识别', noCache: true }
        }]
    },
    // 教学资源
    {
        path: '/resource',
        component: Layout,
        redirect: '/resource/index',

    },



    {
        path: '/resource',
        component: Layout,
        redirect: '/resource/index',

    },

    // 专业课程
    {
        path: '/course',
        component: Layout,
        redirect: '/course/index',
        children: [{
            path: 'index',
            component: () =>
                import ('@/views/course/index'),
            name: '专业课程',
            meta: { title: '专业课程', noCache: true }
        }]
    },
    // 专业课程
    {
        path: '/course',
        component: Layout,
        redirect: '/course/index',

    },

    //教师的个人页面

    {
        path: '/teachers',
        component: Layout,
        redirect: '/teachers/detail',
        children: [{
            path: 'detail',
            component: () =>
                import ('@/views/teachers/detail'),
            name: 'detail5',
            meta: { title: 'detail' }
        }]
    },
    {
        path: '/login',
        component: Layout,
        redirect: '/login/login',
        children: [{
            path: 'detail',
            component: () =>
                import ('@/views/login/login'),
            name: 'detail',
            meta: { title: 'detail' }
        }]
    },

    // 师资力量
    {
        path: '/teachers',
        component: Layout,
        redirect: '/teachers/index',
        children: [{
            path: 'index',
            component: () =>
                import ('@/views/teachers/index'),
            name: '师资力量',
            meta: { title: '师资力量' }
        }]
    },
    // 风采展示

]

const createRouter = () => new VueRouter({
    mode: 'hash',
    // mode: 'history', // require service support
    scrollBehavior: () => ({ y: 0 }),
    routes: asyncRoutes
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
    const newRouter = createRouter()
    router.matcher = newRouter.matcher // reset router
}

export default router