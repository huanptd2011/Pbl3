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
      gender: '',
      fullName: '',
      phone: '',
      address: '',
      avatar: '',
      dob: '',
    }
  }),

  getters: {
    isAdmin: (state) => state.user.role === 'ADMIN',
  },

  actions: {
    // Khi đăng nhập hoặc cập nhật hồ sơ
    setUser(userData) {
      this.user = {
        username: userData.username || '',
        email: userData.email || '',
        role: userData.role || '',
        userId: Number(userData.userId) || 0,
        token: userData.token || '',
        gender: userData.gender || '',
        fullName: userData.fullName || '',
        phone: userData.phone || '',
        address: userData.address || '',
        avatar: userData.avatar || '',
        dob: userData.dob ? userData.dob.substring(0, 10) : '', // xử lý an toàn hơn với ISO date
      };
      this.isLoggedIn = true;

      // Lưu vào localStorage
      localStorage.setItem('user', JSON.stringify(this.user));
    },

    // Load lại từ localStorage khi reload trang
    loadUserFromStorage() {
      const savedUser = localStorage.getItem('user');
      if (savedUser) {
        try {
          const parsedUser = JSON.parse(savedUser);
          this.user = {
            ...this.user,
            ...parsedUser
          };
          this.isLoggedIn = true;
        } catch (error) {
          console.error('Lỗi khi parse user từ localStorage:', error);
          this.logout();
        }
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
        gender: '',
        fullName: '',
        phone: '',
        address: '',
        avatar: '',
        dob: ''
      };

      localStorage.removeItem('user');
    }
  },

  persist: true // Nếu đang dùng plugin pinia-plugin-persistedstate
});
