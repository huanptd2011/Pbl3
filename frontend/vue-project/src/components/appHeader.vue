<template>
  <!-- Conditionally render the header based on the current route -->
  <header class="sticky-top" v-if="!isAuthPage">
    <div class="container">
      <div class="d-flex align-items-center py-3">
        <!-- Mobile menu toggle -->
        <button class="btn btn-link d-lg-none me-2" @click="toggleMobileMenu">
          <i class="fas fa-bars"></i>
        </button>

        <!-- Logo -->
        <router-link to="/" class="navbar-brand me-auto">
          <img src="@/assets/logo.jpeg" alt="ShoeStore" height="40" />
        </router-link>

        <!-- Main Navigation (center) -->
        <nav class="d-none d-lg-block me-3">
          <ul class="nav">
            <li class="nav-item" v-for="(item, index) in navItems" :key="index">
              <router-link :to="item.path" class="nav-link">{{ item.title }}</router-link>
            </li>
          </ul>
        </nav>

        <!-- Search and User Actions (right) -->
        <div class="d-flex align-items-center ms-auto">
          <!-- Search (only on large) -->
          <div class="input-group me-3 d-none d-lg-flex">
            <input type="text" class="form-control" placeholder="Tìm kiếm giày..." v-model="searchQuery" />
            <button class="btn btn-outline-secondary" @click="performSearch">
              <i class="fas fa-search"></i>
            </button>
          </div>

          <!-- Cart -->
          <router-link to="/cart" class="btn btn-link position-relative me-2">
            <i class="fas fa-shopping-cart"></i>
            <span class="position-absolute top-0 start-100 translate-middle badge bg-danger" v-if="cartCount > 0">
              {{ cartCount }}
            </span>
          </router-link>

          <!-- Auth Links -->
          <div class="d-flex align-items-center ms-auto">
            <router-link to="/login" class="btn btn-link me-2" style="white-space: nowrap;">Đăng nhập</router-link>
            <router-link to="/register" class="btn btn-link" style="white-space: nowrap;">Đăng ký</router-link>
          </div>
        </div>
      </div>
    </div>

    <!-- Mobile Menu -->
    <div class="mobile-menu" :class="{ show: mobileMenuOpen }">
      <div class="mobile-menu-header">
        <button class="btn btn-close" @click="toggleMobileMenu"></button>
      </div>
      <div class="mobile-menu-body">
        <ul class="nav flex-column">
          <li class="nav-item" v-for="(item, index) in navItems" :key="index">
            <router-link :to="item.path" class="nav-link" @click="toggleMobileMenu">
              {{ item.title }}
            </router-link>
          </li>
          <li class="nav-item">
            <router-link to="/login" class="nav-link" @click="toggleMobileMenu">Đăng nhập</router-link>
          </li>
          <li class="nav-item">
            <router-link to="/register" class="nav-link" @click="toggleMobileMenu">Đăng ký</router-link>
          </li>
        </ul>
      </div>
    </div>

    <!-- Auth Modal -->
    <AuthModal v-if="showAuthModal" @close="toggleAuthModal" />
  </header>
</template>

<script setup>
import { ref, computed } from 'vue';
import { useCartStore } from '@/stores/cartStore';
import AuthModal from '@/components/AuthModal.vue';
import { useRouter, useRoute } from 'vue-router';

const router = useRouter();
const route = useRoute(); // Access the current route

const cartStore = useCartStore();

const mobileMenuOpen = ref(false);
const showAuthModal = ref(false);
const searchQuery = ref('');

// Define navigation items
const navItems = [
  { title: 'Trang chủ', path: '/' },
  { title: 'Sản phẩm', path: '/products' },
  { title: 'Khuyến mãi', path: '/sale' },
  { title: 'Bộ sưu tập', path: '/collections' },
  { title: 'Về chúng tôi', path: '/about' },
];

// Check if the current route is an auth-related page (login or register)
const isAuthPage = computed(() => {
  return route.path === '/products' || route.path === '/register';
});

const toggleMobileMenu = () => {
  mobileMenuOpen.value = !mobileMenuOpen.value;
};

const toggleAuthModal = () => {
  showAuthModal.value = !showAuthModal.value;
};

const performSearch = () => {
  if (searchQuery.value.trim()) {
    router.push({ path: '/search', query: { q: searchQuery.value } });
  }
};

const cartCount = computed(() => cartStore.totalItemsCount);
</script>

<style scoped>
header {
  background-color: white;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  z-index: 1000;
}

.nav-link {
  font-weight: 500;
  color: #333;
  padding: 0.5rem 1rem;
  transition: all 0.3s;
}

.nav-link:hover,
.nav-link.router-link-active {
  color: #d32f2f;
}

.mobile-menu {
  position: fixed;
  top: 0;
  left: 0;
  width: 80%;
  max-width: 300px;
  height: 100vh;
  background: white;
  z-index: 1100;
  transform: translateX(-100%);
  transition: transform 0.3s ease;
  box-shadow: 2px 0 10px rgba(0, 0, 0, 0.1);
}

.mobile-menu.show {
  transform: translateX(0);
}

.mobile-menu-header {
  padding: 1rem;
  display: flex;
  justify-content: flex-end;
}

.mobile-menu-body {
  padding: 1rem;
}

.badge {
  font-size: 0.7rem;
}

.input-group {
  max-width: 300px;
}
</style>
