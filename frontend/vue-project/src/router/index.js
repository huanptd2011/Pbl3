
import { createRouter, createWebHistory } from 'vue-router';
import { useUserStore } from '@/stores/user'

const routes = [
  {
    path: '/',
    name: 'Home',
    component: () => import('@/views/HomeView.vue')
  },
  {
    path: '/products',
    name: 'Products',
    component: () => import('@/views/ProductsView.vue')
  },
  {
    path: '/products/:productId',
    name: 'ProductDetail',
    component: () => import('@/views/ProductDetailView.vue'),
    props: true
  },

  {
    path: '/sale',
    name: 'Sale',
    component: () => import('@/views/SaleView.vue')
  },
  {
    path: '/cart',
    name: 'Cart',
    component: () => import('@/views/CartView.vue'),
     meta: { requiresAuth: true }
  },
  {
    path: '/checkout',
    name: 'Checkout',
    component: () => import('@/views/CheckoutView.vue'),
     meta: { requiresAuth: true }
  },
  {
    path: '/account',
    name: 'Account',
    component: () => import('@/views/AccountView.vue'),
    meta: { requiresAuth: true }
  },
  {
    path: '/wishlist',
    name: 'Wishlist',
    component: () => import('@/views/WishlistView.vue'),
    // meta: { requiresAuth: true }
  },
  {
    path: '/about',
    name: 'About',
    component: () => import('@/views/AboutView.vue')
  },
  {
    path: '/contact',
    name: 'Contact',
    component: () => import('@/views/ContactView.vue')
  },
  {
    path: '/search',
    name: 'Search',
    component: () => import('@/views/ProductsView.vue')
    //component: () => import('@/views/SearchView.vue')
  },
  {
    path: '/:pathMatch(.*)*',
    name: 'NotFound',
    component: () => import('@/views/NotFoundView.vue')
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/LoginView.vue')
  },
  {
    path: '/register',
    name: 'Register',
    component: () => import('@/views/RegisterView.vue')
  },
  {
      path: '/admin',
      component: () => import('../layouts/AdminLayout.vue'), // Sử dụng AdminLayout làm component chính
      meta: { requiresAuth: true, requiredRole: 'ADMIN' }, // Bảo vệ route cha
      children: [
        {
          path: '', // Route con mặc định cho /admin (Dashboard)
          name: 'AdminDashboard',
          component: () => import('@/views/admin/AdminDashboard.vue'), // Component Dashboard Admin
          meta: { requiresAuth: true, requiredRole: 'ADMIN' }, // Bảo vệ route con
        },
        {
          path: 'users', // Đường dẫn con: /admin/users
          name: 'AdminUsers',
          component: () => import('@/views/admin/AdminUsers.vue'), // Component Quản lý Người dùng
          meta: { requiresAuth: true, requiredRole: 'ADMIN' }, // Bảo vệ route con
        },
        // Thêm các route con khác cho products, orders, etc. tại đây
        // {
        //   path: 'products',
        //   name: 'AdminProducts',
        //   component: () => import('@/views/admin/AdminProductsView.vue'),
        //   meta: { requiresAuth: true, requiredRole: 'ADMIN' },
        // },
        // {
        //   path: 'orders',
        //   name: 'AdminOrders',
        //   component: () => import('@/views/admin/AdminOrdersView.vue'),
        //   meta: { requiresAuth: true, requiredRole: 'ADMIN' },
        // },
      ],
  },
];

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes,
  scrollBehavior(to, from, savedPosition) {
    if (savedPosition) {
      return savedPosition;
    } else {
      return { top: 0 };
    }
  }
});
//phaan quyen o cho nay nhaaaaaa phan quyen cac view
// Authentication guard
 router.beforeEach((to, from, next) => {
 const authStore = useUserStore();
 const isAuthenticated = authStore.isLoggedIn;


   if (to.meta.requiresAuth && !isAuthenticated) {
    alert('Vui lòng đăng nhập!');
     next({ name: 'Login' });/// quay veef ddawng moup đăng nhập
   } else {
     next();
   }
 });

export default router;
