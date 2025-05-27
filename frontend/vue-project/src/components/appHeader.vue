<template>
  <header class="sticky-top" v-if="!isAuthPage">
    <div class="container">
      <div class="d-flex align-items-center justify-content-between py-3">
        <!-- Mobile menu toggle -->
        <button class="btn btn-link d-lg-none me-2" @click="toggleMobileMenu">
          <i class="fas fa-bars"></i>
        </button>

        <!-- Logo -->
        <router-link to="/" class="navbar-brand me-4">
          <img src="@/assets/logo.jpeg" alt="ShoeStore" height="50" />
        </router-link>

        <!-- Main Navigation -->
        <nav class="d-none d-lg-block">
          <ul class="nav justify-content-center gap-4 align-items-center">
            <li class="nav-item" v-for="(item, index) in navItems" :key="index">
              <router-link :to="item.path" class="nav-link">{{ item.title }}</router-link>
            </li>

            <!-- Dropdown Danh mục -->
            <li class="nav-item dropdown" @mouseenter="showCategoryDropdown = true" @mouseleave="showCategoryDropdown = false">
              <a class="nav-link dropdown-toggle" href="#">Danh mục</a>
              <ul class="dropdown-menu" :class="{ 'show': showCategoryDropdown }">
                <li v-for="category in categories" :key="category.categoryId">
                  <router-link
                    class="dropdown-item"
                    :to="`/products?category=${category.categoryId}`"
                    @click="showCategoryDropdown = false"
                  >
                    {{ category.categoryName }}
                  </router-link>
                </li>
              </ul>
            </li>

            <!-- About -->
            <li class="nav-item">
              <router-link to="/about" class="nav-link">Về chúng tôi</router-link>
            </li>
          </ul>
        </nav>

        <!-- Search + Wishlist + Cart + Auth -->
        <ul class="nav align-items-center m-0">
          <!-- Search box -->
          <li class="nav-item me-3 d-none d-lg-flex bg-gray">
            <div class="input-group">
              <span class="input-group-text bg-gray border-0 rounded-start-50" @click="gotoProductView">
                <i class="fas fa-search"></i>
              </span>
              <input
                type="text"
                class="form-control border-0 bg-gray rounded-end-50"
                placeholder="Tìm kiếm giày..."
                v-model="searchQuery"
                @keyup.enter="gotoProductView"
              />
            </div>
          </li>

          <!-- Wishlist -->
          <li class="nav-item me-2">
            <router-link to="/wishlist" class="btn btn-link position-relative">
              <i class="fas fa-heart"></i>
              <span v-if="wishlistCount > 0" class="position-absolute top-0 start-100 translate-middle badge bg-danger">
                {{ wishlistCount }}
              </span>
            </router-link>
          </li>

          <!-- Cart -->
          <li class="nav-item me-2">
            <router-link to="/cart" class="btn btn-link position-relative bg-gray rounded-5">
              <i class="fas fa-shopping-cart text-dark"></i>
              <span v-if="cartCount > 0" class="position-absolute top-0 start-100 translate-middle badge bg-danger">
                {{ cartCount }}
              </span>
            </router-link>
          </li>

          <!-- Auth Links -->
          <li class="nav-item" v-if="!isAuthenticated">
            <router-link to="/login" class="nav-link">Đăng nhập</router-link>
          </li>
          <li class="nav-item" v-if="!isAuthenticated">
            <router-link to="/register" class="nav-link">Đăng ký</router-link>
          </li>
        </ul>
      </div>
    </div>

    <!-- Mobile Menu -->
    <div class="mobile-menu" :class="{ 'show': mobileMenuOpen }">
      <div class="mobile-menu-header">
        <button class="btn btn-close" @click="toggleMobileMenu"></button>
      </div>
      <div class="mobile-menu-body">
        <ul class="nav flex-column">
          <li class="nav-item">
            <router-link to="/" class="nav-link" @click="toggleMobileMenu">Trang chủ</router-link>
          </li>
          <li class="nav-item">
            <a class="nav-link" @click="toggleMobileCategoryMenu">
              Danh mục <i class="fas fa-chevron-down"></i>
            </a>
            <ul class="mobile-submenu" :class="{ 'show': showMobileCategoryMenu }">
              <li v-for="category in categories" :key="category.categoryId">
                <router-link
                  class="nav-link"
                  :to="`/products?category=${category.categoryId}`"
                  @click="toggleMobileMenu"
                >
                  {{ category.categoryName }}
                </router-link>
              </li>
            </ul>
          </li>
          <li class="nav-item">
            <router-link to="/about" class="nav-link" @click="toggleMobileMenu">Về chúng tôi</router-link>
          </li>
          <li class="nav-item" v-if="!isAuthenticated">
            <router-link to="/login" class="nav-link" @click="toggleMobileMenu">Đăng nhập</router-link>
          </li>
          <li class="nav-item" v-if="!isAuthenticated">
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
import { ref, computed, onMounted } from 'vue';
import { useCartStore } from '@/stores/cartStore';
import { useWishlistStore } from '@/stores/wishlist';
import AuthModal from '@/components/AuthModal.vue';
import { useRouter } from 'vue-router';
import axios from 'axios';

const router = useRouter();
const cartStore = useCartStore();
const wishlistStore = useWishlistStore();

const mobileMenuOpen = ref(false);
const showAuthModal = ref(false);
const searchQuery = ref('');
const showCategoryDropdown = ref(false);
const showMobileCategoryMenu = ref(false);
const categories = ref([]);
const navItems = [
  { title: 'Trang chủ', path: '/' },
  { title: 'Sản phẩm', path: '/products' },
];
const isAuthPage = computed(() => {
  const path = router.currentRoute.value.path;
  return path === '/login' || path === '/register';
});

// Fetch categories from API
const fetchCategories = async () => {
  try {
    const response = await axios.get('http://localhost:8080/api/product-categories');
    categories.value = response.data;
  } catch (error) {
    console.error('Error fetching categories:', error);
    // Fallback data
  }
};

onMounted(() => {
  fetchCategories();
});


const toggleMobileMenu = () => {
  mobileMenuOpen.value = !mobileMenuOpen.value;
  if (!mobileMenuOpen.value) {
    showMobileCategoryMenu.value = false;
  }
};

const toggleMobileCategoryMenu = () => {
  showMobileCategoryMenu.value = !showMobileCategoryMenu.value;
};

const toggleAuthModal = () => {
  showAuthModal.value = !showAuthModal.value;
};

const gotoProductView = () => {
  if (searchQuery.value.trim()) {
    router.push({ path: '/products', query: { q: searchQuery.value } });
  }
  searchQuery.value = "";
};

// Computed properties
const cartCount = computed(() => cartStore.totalItemsCount);
const wishlistCount = computed(() => wishlistStore.items.length);
</script>

<style scoped>
.dropdown-menu {
  display: none;
  position: absolute;
  background-color: white;
  box-shadow: 0 8px 16px rgba(0,0,0,0.1);
  border-radius: 4px;
  padding: 0.5rem 0;
  z-index: 1000;
  min-width: 200px;
}

.dropdown-menu.show {
  display: block;
}

.dropdown-item {
  padding: 0.5rem 1.5rem;
  white-space: nowrap;
  color: #333;
  text-decoration: none;
  display: block;
}

.dropdown-item:hover {
  background-color: #f8f9fa;
}

.mobile-submenu {
  display: none;
  padding-left: 1.5rem;
  background-color: #f8f9fa;
}

.mobile-submenu.show {
  display: block;
}

.nav-link {
  cursor: pointer;
  position: relative;
}

.bg-gray {
  background-color: #f8f9fa;
}

.mobile-menu {
  max-height: 0;
  overflow: hidden;
  transition: max-height 0.3s ease;
  background-color: white;
}

.mobile-menu.show {
  max-height: 100vh;
}

.mobile-menu-header {
  display: flex;
  justify-content: flex-end;
  padding: 1rem;
}

.mobile-menu-body {
  padding: 0 1rem 1rem;
}

.navbar-brand {
  margin-right: 2rem;
}

.input-group {
  width: 250px;
}

.fa-chevron-down {
  font-size: 0.8rem;
  margin-left: 0.5rem;
  transition: transform 0.3s;
}

.mobile-submenu.show .fa-chevron-down {
  transform: rotate(180deg);
}
</style>
<style scoped>
header {
  background-color: white;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  z-index: 1000;
}

.nav-link {
  font-weight: 550;
  color: #777676;
  padding: 0.5rem 1rem;
  transition: all 0.3s;
}

.nav-link:hover,
.nav-link.router-link-active {
  color: #130808;
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
  border-radius: 50px;
  transition: border 0.3s ease;
}

.form-control:focus{
  box-shadow: none;
}

.input-group:focus-within {
  border: 1px solid #130808;
  border-radius: 50px;

}

.input-group:focus-within .input-group-text {
background-color: #fff !important;
}
.btn{
  transition: all 0.1s ease-in-out;
}
.btn:hover {
  background-color: #ddd;
  border-radius: 0.5rem;
}
</style>
