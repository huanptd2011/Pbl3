import { createRouter, createWebHistory } from 'vue-router'


import pageHome from '@/views/pageHome.vue'
import pageProduct from '@/views/pageProduct.vue'
import colectionKids from '@/views/colectionKids.vue'
import colectionMen from '@/views/colectionMen.vue'
import colectionWomen from '@/views/colectionWomen.vue'


const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: pageHome
    },
    {
      path: '/product',
      name: 'product',
      component: pageProduct
    },
    {
      path:'/colectionMen',
      name:'colectionMen',
      component: colectionMen
    },
    {
      path:'/colectionWomen',
      name:'colectionWomen',
      component: colectionWomen
    },
    {
      path:'/colectionKids',
      name:'colectionKids',
      component: colectionKids
    },
  ],
})

export default router
