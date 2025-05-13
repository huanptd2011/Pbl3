// src/router/index.js

import { createRouter, createWebHistory } from 'vue-router';
import { useUserStore } from '@/stores/user'; // Import Pinia store

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
    meta: { requiresAuth: true } // Yêu cầu chỉ cần ĐĂNG NHẬP
  },
  {
    path: '/checkout',
    name: 'Checkout',
    component: () => import('@/views/CheckoutView.vue'),
    meta: { requiresAuth: true } // Yêu cầu chỉ cần ĐĂNG NHẬP
  },
  {
    path: '/account',
    name: 'Account',
    component: () => import('@/views/AccountView.vue'),
    meta: { requiresAuth: true } // Yêu cầu chỉ cần ĐĂng NHẬP
  },
  {
    path: '/wishlist',
    name: 'Wishlist',
    component: () => import('@/views/WishlistView.vue'),
    // meta: { requiresAuth: true } // Nếu muốn wishlist yêu cầu đăng nhập, bỏ comment dòng này
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


  // --- ROUTES CHO ADMIN ---
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
      {
        path: 'products',
        name: 'AdminProducts',
        component: () => import('@/views/admin/AdminProducts.vue'),
        meta: { requiresAuth: true, requiredRole: 'ADMIN' },
      },
      // {
      //   path: 'orders',
      //   name: 'AdminOrders',
      //   component: () => import('@/views/admin/AdminOrdersView.vue'),
      //   meta: { requiresAuth: true, requiredRole: 'ADMIN' },
      // },
    ],
  },
  // -------------------------

  // Route 404 Not Found (đặt cuối cùng)
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
  }
];

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes,
  // Logic cuộn trang
  scrollBehavior(to, from, savedPosition) {
    if (savedPosition) {
      return savedPosition;
    } else {
      return { top: 0 };
    }
  }
});

// --- BỘ BẢO VỆ ĐỊNH TUYẾN (NAVIGATION GUARD) ---
// Được chạy TRƯỚC MỖI lần chuyển hướng
router.beforeEach((to, from, next) => {
  const authStore = useUserStore();
  const isAuthenticated = authStore.isLoggedIn; // Lấy trạng thái đăng nhập từ store
  const userRole = authStore.user.role;             // Lấy vai trò người dùng từ store

  const requiresAuth = to.meta.requiresAuth; // Lấy giá trị meta 'requiresAuth' của route đích
  const requiredRole = to.meta.requiredRole; // Lấy giá trị meta 'requiredRole' của route đích
  console.log(`Đang kiểm tra route: ${to.path}`);
  console.log(`isAuthenticated: ${isAuthenticated}, userRole: ${userRole}, requiredRole: ${requiredRole}`);

  // Case 1: Route yêu cầu xác thực (đăng nhập) nhưng người dùng chưa đăng nhập
  if (requiresAuth && !isAuthenticated) {
    alert('Vui lòng đăng nhập để truy cập trang này.');
    // Chuyển hướng đến trang Login. Có thể thêm query 'redirect' để quay lại sau khi login
    next({ name: 'Login', query: { redirect: to.fullPath } });
  }
  // Case 2: Route yêu cầu vai trò cụ thể, người dùng ĐÃ đăng nhập, nhưng vai trò KHÔNG KHỚP
  // Điều kiện 'requiredRole' chỉ đúng nếu route đích có meta field 'requiredRole' được định nghĩa
  else if (requiredRole && isAuthenticated && userRole !== requiredRole) {
     alert('Bạn không có quyền truy cập trang này.'); // Thông báo lỗi phân quyền
     // Chuyển hướng người dùng đến trang không yêu cầu quyền (ví dụ: trang chủ)
     next({ path: '/' });
  }
  // Case 3: Các trường hợp còn lại (route không yêu cầu xác thực HOẶC đã xác thực và đủ quyền)
  else {
    next(); // Cho phép chuyển hướng đến route đích
  }
});
// ------------------------------------------------

export default router;
