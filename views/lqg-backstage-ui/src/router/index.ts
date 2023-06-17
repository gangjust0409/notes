import { createRouter, createWebHashHistory, RouteRecordRaw } from 'vue-router'

const routes: Readonly<RouteRecordRaw[]> = [
    {
        path: '/',
        redirect: '/login'
    },
    {
        path: '/layout',
        component: () => import('../pages/layout.vue'),
        children: [
            {
                path: 'dashbard',
                component: () => import('../pages/dashbard/index.vue')
            },
            {
                path: 'notes/index',
                component: () => import('../pages/article/index.vue')
            },
            {
                path: 'shenghe',
                component: () => import('../pages/article/shenghe.vue')
            },
        ]
    },
    {
        path: '/login',
        component: () => import('../pages/login.vue')
    }
];

const router = createRouter({
    routes,
    history: createWebHashHistory()
})

export default router;