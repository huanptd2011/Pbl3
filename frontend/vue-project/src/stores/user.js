// stores/user.js
import { defineStore } from 'pinia'
import { useCartStore } from '@/stores/cartStore'

export const useUserStore = defineStore('user', {
  state: () => ({
    isLoggedIn: false,
    user: {
      username: '',
      email: '',
      role: '',
      userId: 0,
      token: '',
    }
  }),

  getters: {
    isAdmin: (state) => state.user.role === 'ADMIN',
  },

  actions: {
    // Khi đăng nhập:
    setUser(userData) {
      this.user = {
        username: userData.username,
        email: userData.email,
        role: userData.role,
        userId: Number(userData.userId),
        token: userData.token,
      };
      this.isLoggedIn = true;

      // Lưu vào localStorage
      localStorage.setItem('user', JSON.stringify(this.user));
    },

    // Load lại từ localStorage khi reload trang:
    loadUserFromStorage() {
      const savedUser = localStorage.getItem('user');
      if (savedUser) {
        this.user = JSON.parse(savedUser);
        this.isLoggedIn = true;
      } else {
        this.logout();
      }
    },

    // Đăng xuất
    logout() {
      const cartStore = useCartStore();
      cartStore.clearUserCart();

      this.isLoggedIn = false;
      this.user = {
        username: '',
        email: '',
        role: '',
        userId: 0,
        token: '',
      };

      localStorage.removeItem('user');
    }
  },

  persist: true // Nếu đang dùng plugin pinia-plugin-persistedstate
});