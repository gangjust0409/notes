import { createRouter, createWebHashHistory } from 'vue-router'

const routes = [
    {
        path: '/',
        redirect: '/home'
    },
    {
        path: '/home',
        component: () => import('../pages/home.vue')
    },
    {
        path: '/catalog',
        component: () => import('../pages/catalog.vue')
    },
    {
        path: '/user/center',
        component: () => import('../pages/user.vue')
    },
    {
        path: '/query',
        component: () => import('../pages/search.vue')
    },
    {
        path: '/detail',
        component: () => import('../pages/detail.vue')
    },
    {
        path: '/release',
        component: () => import('../pages/add.vue')
    },
    {
        path: '/to/user',
        component: () => import('../pages/otherUser.vue')
    },
    {
        path: '/login',
        component: () => import('../pages/login.vue')
    },
    {
        path: '/regist',
        component: () => import('../pages/regist.vue')
    },
    {
        path: '/home/list',
        component: () => import('../pages/articleList.vue')
    },
];

const router = createRouter({
    history: createWebHashHistory(),
    routes,
})


export default router;