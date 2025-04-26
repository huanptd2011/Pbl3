import { defineStore } from 'pinia'

// <-- HÀM NÀY ĐỂ TẢI DỮ LIỆU BAN ĐẦU TỪ LOCAL STORAGE -->
const getInitialCartState = () => {
    // Lấy dữ liệu từ localStorage với key 'cartItems'
    const savedCart = localStorage.getItem('cartItems')
    if (savedCart) {
        try {
            // Parse chuỗi JSON thành object/array JavaScript.
            // Nếu quá trình parse lỗi (dữ liệu bị hỏng), trả về mảng rỗng.
            return JSON.parse(savedCart)
        } catch (e) {
            console.error('Failed to parse cart data from localStorage', e)
            return [] // Trả về mảng rỗng nếu dữ liệu lỗi
        }
    }
    return [] // Trả về mảng rỗng nếu không tìm thấy dữ liệu trong localStorage
}

// Định nghĩa store có ID là 'cart'
export const useCartStore = defineStore('cart', {
    // State: Nơi lưu trữ dữ liệu của giỏ hàng
    state: () => ({
        // <-- KHỞI TẠO STATE TỪ LOCAL STORAGE -->
        items: getInitialCartState(),
    }), // Actions: Các hàm để thay đổi state hoặc thực hiện logic

    actions: {
        // <-- HÀM HỖ TRỢ ĐỂ LƯU STATE VÀO LOCAL STORAGE -->
        saveCartToLocalStorage() {
            // Chuyển object/array items thành chuỗi JSON và lưu vào localStorage với key 'cartItems'
            localStorage.setItem('cartItems', JSON.stringify(this.items))
        },

        addItem(itemDetails) {
            const existingItem = this.items.find(
                (item) =>
                    item.productId === itemDetails.productId &&
                    item.color === itemDetails.color &&
                    item.size === itemDetails.size,
            )

            if (existingItem) {
                existingItem.quantity += itemDetails.quantity
            } else {
                this.items.push({ ...itemDetails, isSelected: true })
            }

            this.saveCartToLocalStorage() // <-- LƯU VÀO LOCAL STORAGE SAU KHI THAY ĐỔI
        },

        removeItem(productId, color, size) {
            this.items = this.items.filter(
                (item) => !(item.productId === productId && item.color === color && item.size === size),
            )
            this.saveCartToLocalStorage() // <-- LƯU VÀO LOCAL STORAGE SAU KHI THAY ĐỔI
        },

        updateItemQuantity(productId, color, size, newQuantity) {
            newQuantity = parseInt(newQuantity, 10)
            if (isNaN(newQuantity) || newQuantity < 0) {
                console.warn('Số lượng không hợp lệ:', newQuantity)
                return
            }

            const itemToUpdate = this.items.find(
                (item) => item.productId === productId && item.color === color && item.size === size,
            )

            if (itemToUpdate) {
                if (newQuantity === 0) {
                    this.removeItem(productId, color, size) // removeItem đã gọi save
                } else {
                    itemToUpdate.quantity = newQuantity
                    this.saveCartToLocalStorage() // <-- LƯU VÀO LOCAL STORAGE SAU KHI THAY ĐỔI
                }
            }
        },

        setItemSelected(productId, color, size, isSelected) {
            const item = this.items.find(
                (item) => item.productId === productId && item.color === color && item.size === size,
            )
            if (item) {
                item.isSelected = isSelected
                this.saveCartToLocalStorage() // <-- LƯU VÀO LOCAL STORAGE SAU KHI THAY ĐỔI
            }
        },

        setAllItemsSelected(isSelected) {
            this.items.forEach((item) => {
                item.isSelected = isSelected
            })
            this.saveCartToLocalStorage() // <-- LƯU VÀO LOCAL STORAGE SAU KHI THAY ĐỔI
        },

        removeSelectedItems() {
            this.items = this.items.filter((item) => !item.isSelected)
            this.saveCartToLocalStorage() // <-- LƯU VÀO LOCAL STORAGE SAU KHI THAY ĐỔI
        },
    }, // Getters: Các hàm để tính toán dữ liệu từ state (giống computed properties)

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
                .filter((item) => item.isSelected) // Bước 1: Lọc ra các item được chọn
                .reduce((total, item) => total + (item.price * item.quantity), 0); // Bước 2: Tính tổng giá trị (giá * số lượng) của các item đã lọc
        }
    },
})
