// src/stores/wishlist.js
import { defineStore } from 'pinia';

export const useWishlistStore = defineStore('wishlist', {
  state: () => ({
    items: []
  }),
  actions: {
    addToWishlist(item) {
      this.items.push(item);
    },
    removeFromWishlist(index) {
      this.items.splice(index, 1);
    },
    clearWishlist() {
      this.items = [];
    }
  }
});
