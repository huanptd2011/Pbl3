// src/stores/user.js
import { defineStore } from 'pinia';

export const useUserStore = defineStore('user', {
  state: () => ({
    isLoggedIn: false,
    token: null,
    username: null,
    role: null,
    email: null,
  }),
  getters: {
    isAdmin: (state) => state.role === 'ADMIN',
  },
  actions: {
    // Load thông tin người dùng từ localStorage
    loadUserFromStorage() {
      const token = localStorage.getItem('token');
      const username = localStorage.getItem('username');
      const role = localStorage.getItem('role');
      const email = localStorage.getItem('email');

      if (token && username && role && email) {
        this.token = token;
        this.username = username;
        this.role = role;
        this.email = email;
        this.isLoggedIn = true;
      } else {
        this.logout();
      }
    },

    // Đăng nhập người dùng
    login(userData) {
      this.token = userData.token;
      this.username = userData.username;
      this.role = userData.role;
      this.email = userData.email;
      this.isLoggedIn = true;

      localStorage.setItem('token', userData.token);
      localStorage.setItem('username', userData.username);
      localStorage.setItem('role', userData.role);
      localStorage.setItem('email', userData.email);
    },

    // Đăng xuất người dùng
    logout() {
      this.isLoggedIn = false;
      this.token = null;
      this.username = null;
      this.role = null;
      this.email = null;

      localStorage.removeItem('token');
      localStorage.removeItem('username');
      localStorage.removeItem('role');
      localStorage.removeItem('email');
    },
  },
});

