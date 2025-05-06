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
    // *** ĐẢM BẢO ACTION loadUserFromStorage ĐƯỢC ĐỊNH NGHĨA Ở ĐÂY ***
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
        // Xóa dữ liệu nếu không đầy đủ
        this.logout();
      }
    },

    // Action login (đảm bảo cập nhật role ở đây)
    login(userData) {
      this.token = userData.token;
      this.username = userData.username;
      this.role = userData.role; // Set the role
      this.email = userData.email;
      this.isLoggedIn = true;

      // Lưu vào localStorage
      localStorage.setItem('token', userData.token);
      localStorage.setItem('username', userData.username);
      localStorage.setItem('role', userData.role);
      localStorage.setItem('email', userData.email);
    },

    // Action logout
    logout() {
      this.isLoggedIn = false;
      this.token = null;
      this.username = null;
      this.role = null; // Clear the role
      this.email = null;
      localStorage.removeItem('token');
      localStorage.removeItem('username');
      localStorage.removeItem('role');
      localStorage.removeItem('email');
    },
    // ***************************************************************
  },
});
