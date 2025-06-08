// src/router/index.js

import { createRouter, createWebHistory } from 'vue-router';
import { useUserStore } from '@/stores/user'; // Pinia store

const routes = [
  // --- PUBLIC ROUTES ---
  { path: '/', name: 'Home', component: () => import('@/views/HomeView.vue') },
  { path: '/products', name: 'Products', component: () => import('@/views/ProductsView.vue') },
  { path: '/products/category/:categoryId', name: 'ProductCategory', component: () => import('@/views/ProductCategoryView.vue') },
  { path: '/products/:productId', name: 'ProductDetail', component: () => import('@/views/ProductDetailView.vue'), props: true },
  { path: '/sale', name: 'Sale', component: () => import('@/views/SaleView.vue') },
  { path: '/about', name: 'About', component: () => import('@/views/AboutView.vue') },
  { path: '/contact', name: 'Contact', component: () => import('@/views/ContactView.vue') },
  { path: '/login', name: 'Login', component: () => import('@/views/LoginView.vue') },
  { path: '/register', name: 'Register', component: () => import('@/views/RegisterView.vue') },

  // --- AUTHENTICATED USER ROUTES ---
  {
    path: '/cart',
    name: 'Cart',
    component: () => import('@/views/CartView.vue'),
    meta: { requiresAuth: true, requiredRoles: ['CUSTOMER'] }
  },
  {
    path: '/checkout',
    name: 'Checkout',
    component: () => import('@/views/CheckoutView.vue'),
    meta: { requiresAuth: true, requiredRoles: ['CUSTOMER'] }
  },
  {
    path: '/account',
    name: 'Account',
    component: () => import('@/views/AccountView.vue'),
    meta: { requiresAuth: true, requiredRoles: ['CUSTOMER'] }
  },
  {
    path: '/wishlist',
    name: 'Wishlist',
    component: () => import('@/views/WishlistView.vue'),
    meta: { requiresAuth: true, requiredRoles: ['CUSTOMER'] }
  },
  {
    path: '/order',
    name: 'Order',
    component: () => import('@/views/OrderView.vue'),
    meta: { requiresAuth: true, requiredRoles: ['CUSTOMER'] }
  },
  {
    path: '/change-password',
    name: 'ChangePassword',
    component: () => import('@/views/ChangePassView.vue'),
    meta: { requiresAuth: true, requiredRoles: ['CUSTOMER'] }
  },

  // --- ADMIN ROUTES ---
  {
    path: '/admin',
    component: () => import('@/layouts/AdminLayout.vue'),
    meta: { requiresAuth: true, requiredRoles: ['ADMIN'] },
    children: [
      {
        path: '',
        name: 'AdminDashboard',
        component: () => import('@/views/admin/AdminDashboard.vue'),
        meta: { requiresAuth: true, requiredRoles: ['ADMIN'] }
      },
      {
        path: 'profile',
        name: 'AdminProfile',
        component: () => import('@/views/admin/AdminProfile.vue'),
        meta: { requiresAuth: true, requiredRoles: ['ADMIN'] }
      },
      {
        path: 'users',
        name: 'AdminUsers',
        component: () => import('@/views/admin/AdminUsers.vue'),
        meta: { requiresAuth: true, requiredRoles: ['ADMIN'] }
      },
      {
        path: 'user/update/:userId',
        name: 'AdminUserEdit',
        component: () => import('@/views/admin/AdminUserEdit.vue'),
        meta: { requiresAuth: true, requiredRoles: ['ADMIN'] }
      },
      {
        path: 'products',
        name: 'AdminProducts',
        component: () => import('@/views/admin/AdminProducts.vue'),
        meta: { requiresAuth: true, requiredRoles: ['ADMIN'] }
      },
      {
        path: 'product/create',
        name: 'AdminCreateProduct',
        component: () => import('@/views/admin/product/AdminCreateProduct.vue'),
        meta: { requiresAuth: true, requiredRoles: ['ADMIN'] }
      },
      {
        path: 'product/edit/:id',
        name: 'AdminEditProduct',
        component: () => import('@/views/admin/product/AdminEditProduct.vue'),
        meta: { requiresAuth: true, requiredRoles: ['ADMIN'] }
      },
      {
        path: 'orders',
        name: 'AdminOrders',
        component: () => import('@/views/admin/AdminOrders.vue'),
        meta: { requiresAuth: true, requiredRoles: ['ADMIN'] }
      },
      {
        path: 'order/update/:orderId',
        name: 'AdminOrderUpdate',
        component: () => import('@/views/admin/order/AdminOrderUpdate.vue'),
        meta: { requiresAuth: true, requiredRoles: ['ADMIN'] }
      }
    ]
  },

  // --- UNAUTHORIZED ACCESS ---
  {
    path: '/unauthorized',
    name: 'Unauthorized',
    component: () => import('@/views/UnauthorizedView.vue')
  },

  // --- 404 NOT FOUND ---
  {
    path: '/:pathMatch(.*)*',
    name: 'NotFound',
    component: () => import('@/views/NotFoundView.vue')
  }
];

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes,
  scrollBehavior(to, from, savedPosition) {
    return savedPosition || { top: 0 };
  }
});

// --- NAVIGATION GUARD ---
router.beforeEach(async (to, from, next) => {
  const authStore = useUserStore();

  // Check if user data is loaded
  if (!authStore.initialized) {
    await authStore.checkAuth();
  }

  const isAuthenticated = authStore.isLoggedIn;
  const userRole = authStore.user?.role;

  const requiresAuth = to.meta.requiresAuth;
  const requiredRoles = to.meta.requiredRoles;

  console.log(`Route: ${to.path} | Auth: ${isAuthenticated} | Role: ${userRole} | Required: ${requiredRoles}`);

  // Handle authentication requirements
  if (requiresAuth && !isAuthenticated) {
    // Store the intended destination
    next({
      name: 'Login',
      query: { redirect: to.fullPath },
      replace: true
    });
    return;
  }

  // Handle role-based authorization
  if (requiredRoles && (!userRole || !requiredRoles.includes(userRole))) {
    console.warn('Access denied: insufficient permissions');

    if (isAuthenticated) {
      // User is logged in but doesn't have required role
      next({ name: 'Unauthorized', replace: true });
    } else {
      // User is not logged in
      next({ name: 'Login', query: { redirect: to.fullPath }, replace: true });
    }
    return;
  }

  // Allow navigation
  next();
});

// Handle authentication errors globally
router.onError((error) => {
  console.error('Router error:', error);
  if (error.message.includes('Authentication')) {
    router.push('/login');
  }
});

export default router;
