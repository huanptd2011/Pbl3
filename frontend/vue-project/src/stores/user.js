// stores/user.js
import { defineStore } from 'pinia'
import { useCartStore } from '@/stores/cartStore'

export const useUserStore = defineStore('user', {
  state: () => ({
    isLoggedIn: false,
    initialized: false, // Thêm flag để track việc khởi tạo
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
      this.initialized = true;

      // Lưu vào localStorage
      localStorage.setItem('user', JSON.stringify(this.user));
    },

    // Kiểm tra auth từ localStorage
    async checkAuth() {
      try {
        const savedUser = localStorage.getItem('user');
        if (savedUser) {
          const parsedUser = JSON.parse(savedUser);

          // Kiểm tra token có hợp lệ không (có thể gọi API để verify)
          if (parsedUser.token) {
            this.user = {
              ...this.user,
              ...parsedUser
            };
            this.isLoggedIn = true;
          } else {
            this.logout();
          }
        } else {
          this.logout();
        }
      } catch (error) {
        console.error('Lỗi khi kiểm tra auth:', error);
        this.logout();
      } finally {
        this.initialized = true;
      }
    },

    // Load lại từ localStorage khi reload trang (deprecated - dùng checkAuth thay thế)
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
      this.initialized = true;
    },

    // Đăng xuất
    logout() {
      const cartStore = useCartStore();
      cartStore.clearUserCart();

      this.isLoggedIn = false;
      this.initialized = true;
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
    },

    // Reset initialized flag (nếu cần)
    resetInitialized() {
      this.initialized = false;
    }
  },

  persist: true // Nếu đang dùng plugin pinia-plugin-persistedstate
});
