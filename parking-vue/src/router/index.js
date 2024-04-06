import { createRouter, createWebHistory } from 'vue-router';

const routes = [
    { path: '/',component: ()=>import("@/pages/login.vue") },
    { path: '/Home',component: ()=>import("@/pages/Home.vue") ,

        children:[
            { path: '/index',component: ()=>import("@/pages/index.vue") },
            { path: '/tip',component: ()=>import("@/pages/tips.vue") },
            { path: '/system',component: ()=>import("@/pages/system.vue") },
            { path: '/order',component: ()=>import("@/pages/order.vue") },
            { path: '/feedback',component: ()=>import("@/pages/feedback.vue") },
            { path: '/denunciate',component: ()=>import("@/pages/denunciate.vue") },
        ]
    },
    { path: '/map',component: ()=>import("@/components/map.vue") },

];

const router = createRouter({
    history: createWebHistory(),
    routes
});

export default router;
