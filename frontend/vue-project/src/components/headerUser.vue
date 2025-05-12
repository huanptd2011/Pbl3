<template>
  <header class="sticky-top">
      <div class="container">
          <div class="d-flex align-items-center justify-content-between py-3">
              <!-- Mobile menu toggle -->
              <button class="btn btn-link d-lg-none me-2" @click="toggleMobileMenu">
                  <i class="fas fa-bars"></i>
              </button>

              <!-- Logo -->
              <router-link to="/" class="navbar-brand me-44">
                  <img src="@/assets/logo.jpeg" alt="ShoeStore" height="50">
              </router-link>

              <!-- Main Navigation -->
              <nav class="d-none d-lg-block">
                  <ul class="nav justify-content-center gap-4">
                      <li class="nav-item" v-for="(item, index) in navItems" :key="index">
                          <router-link :to="item.path" class="nav-link">{{ item.title }}</router-link>
                      </li>
                  </ul>
              </nav>

              <!-- Search and User Actions -->
              <div class="d-flex ">
                  <div class="input-group me-3 d-none d-lg-flex  bg-gray">
                      <span class="input-group-text bg-gray border-0 rounded-start-50" >
                          <i class="fas fa-search"></i>
                      </span>
                      <input type="text" class="form-control border-0 bg-gray rounded-end-50" placeholder="Tìm kiếm giày..." v-model="searchQuery" @keyup.enter="gotoProductView" />
                  </div>

                  <router-link to="/wishlist" class="btn btn-link position-relative me-2">
                      <i class="fas fa-heart"></i>
                      <span class="position-absolute top-0 start-100 translate-middle badge bg-danger"
                          v-if="wishlistCount > 0">
                          {{ wishlistCount }}
                      </span>
                  </router-link>

                  <router-link to="/cart" class="btn btn-link position-relative me-2 bg-gray rounded-5">
                      <i class="fas fa-shopping-cart text-dark	"></i>
                      <span class="position-absolute top-0 start-100 translate-middle badge bg-danger"
                          v-if="cartCount > 0">
                          {{ cartCount }}
                      </span>
                  </router-link>
                  <button class="btn btn-link bg-gray rounded-5" @click="toggleAuthModal">
                      <i class="fas fa-user text-dark	"></i>
                  </button>
              </div>
          </div>
      </div>

      <!-- Mobile Menu -->
      <div class="mobile-menu" :class="{ 'show': mobileMenuOpen }">
          <div class="mobile-menu-header">
              <button class="btn btn-close" @click="toggleMobileMenu"></button>
          </div>
          <div class="mobile-menu-body">
              <ul class="nav flex-column">
                  <li class="nav-item" v-for="(item, index) in navItems" :key="index">
                      <router-link :to="item.path" class="nav-link" @click="toggleMobileMenu">{{ item.title
                          }}</router-link>
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
import { useWishlistStore } from '@/stores/wishlist';
import AuthModal from '@/components/AuthModal.vue';
import { useRouter } from 'vue-router';

const router = useRouter();


const cartStore = useCartStore();
const wishlistStore = useWishlistStore();

const mobileMenuOpen = ref(false);
const showAuthModal = ref(false);
const searchQuery = ref('');

const navItems = [
  { title: 'Trang chủ', path: '/' },
  { title: 'Sản phẩm', path: '/products' },
  { title: 'Khuyến mãi', path: '/sale' },
  { title: 'Bộ sưu tập', path: '/collections' },
  { title: 'Về chúng tôi', path: '/about' },
];

const toggleMobileMenu = () => {
  mobileMenuOpen.value = !mobileMenuOpen.value;
};

const toggleAuthModal = () => {
  showAuthModal.value = !showAuthModal.value;
};

const gotoProductView = () => {
  // Xử lý tìm kiếm
  if (searchQuery.value.trim()) {
      router.push({ path: '/products', query: { q: searchQuery.value } });
  }
  searchQuery.value = ""
};

// Computed properties
const cartCount = computed(() => cartStore.totalItemsCount);
const wishlistCount = computed(() => wishlistStore.items.length);
</script>

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
