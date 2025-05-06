// stores/user.js
import { defineStore } from 'pinia'
import { useCartStore } from '@/stores/cartStore';

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
  actions: {
    setUser(userData) { //login
      this.user = {
        username: userData.username,
        email: userData.email,
        role: userData.role,
        userId: Number(userData.userId),
        token: userData.token,
      };
      this.isLoggedIn = true;
    },
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
    }
  },
  getters: {
      isAdmin: (state) => state.user.role === 'ADMIN',
  },
  persist: true, //Giữ dữ liệu trong localStorage sau reload
});
