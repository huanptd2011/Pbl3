import { defineStore } from 'pinia'
import axios from 'axios'

export const useCartStore = defineStore('cart', {
    state: () => ({
        items: [],
        isLoggedIn: false,
        cartId: null,
        userId: null
    }),

    actions: {
        async loadUserCart(userID) {
            try {
              const url = `http://localhost:8080/api/carts/user/${userID}`;
              const response = await axios.get(url);

              if (response.status === 200 && Array.isArray(response.data.listCartItem)) {
                    this.userId = userID;
                    this.cartId = response.data.cartId;
                    this.items = response.data.listCartItem.map(item => ({
                      ...item,
                      isSelected: true,
                    }));
                    this.isLoggedIn = true;
                  } else {
                    console.error('Dữ liệu trả về không hợp lệ hoặc lỗi API', response.data);
                  }
            } catch (error) {
              console.error('Lỗi khi tải giỏ hàng', error);
            }

        },

        clearUserCart() {
            this.items = []
            this.isLoggedIn = false
        },

        //Thêm cart item
        async addItem(itemDetails) {
            if (!this.isLoggedIn) {
                alert('Vui lòng đăng nhập!');
                return;
            }

            const cartItemRequest = {
                cartId: this.cartId,
                productId: itemDetails.productId,
                quantity: itemDetails.quantity,
                price: itemDetails.price,
                color: itemDetails.color,
                size: itemDetails.size
            };

            try {
                await axios.post('http://localhost:8080/api/cart-items/add', cartItemRequest);
                await this.loadUserCart(this.userId);
            } catch (error) {
                console.error('Thêm sản phẩm thất bại', error);
            }
        },

        //xóa cart item
        async removeItem(cartItemId) {
            try {
                await axios.delete(`http://localhost:8080/api/cart-items/delete/${cartItemId}`);
                this.items = this.items.filter(item => item.cartItemId !== cartItemId);
            } catch (error) {
                console.error('Xoá sản phẩm thất bại', error);
            }
        },

        async updateItemQuantity(cartItemId, newQuantity) {
            newQuantity = parseInt(newQuantity, 10);
            if (isNaN(newQuantity) || newQuantity < 0) {
                console.warn('Số lượng không hợp lệ:', newQuantity);
                return;
            }

            try {
                await axios.put(`http://localhost:8080/api/cart-items/update-quantity/${cartItemId}?quantity=${newQuantity}`);
                const item = this.items.find(item => item.cartItemId === cartItemId);
                if (item) {
                    item.quantity = newQuantity;
                }
            } catch (error) {
                console.error('Cập nhật số lượng thất bại:', error);
            }
        },

        setItemSelected(productId, color, size, isSelected) {
            const item = this.items.find(
                (item) => item.productId === productId && item.color === color && item.size === size,
            )
            if (item) {
                item.isSelected = isSelected
            }
        },

        setAllItemsSelected(isSelected) {
            this.items.forEach((item) => {
                item.isSelected = isSelected
            })
        },

        removeSelectedItems() {
            this.items = this.items.filter((item) => !item.isSelected)
        },
    },

    getters: {
        totalItemsCount(state) {
            return state.items.reduce((total, item) => total + item.quantity, 0)
        },
        totalPrice(state) {
            return state.items.reduce((total, item) => total + item.price * item.quantity, 0)
       },
       selectedItems(state) {
            return state.items.filter((item) => item.isSelected)
       },
       totalSelectedItemsCount(state) {
            return state.items
                   .filter((item) => item.isSelected)
                   .reduce((total, item) => total + item.quantity, 0)
       },
       totalSelectedPrice(state) {
             return state.items
                    .filter((item) => item.isSelected)
                    .reduce((total, item) => total + (item.price * item.quantity), 0);
        }
    }
})
