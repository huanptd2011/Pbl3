// src/stores/buyNowStore.js
import { defineStore } from 'pinia';
import { ref, computed } from 'vue';

export const useBuyNowStore = defineStore('buyNow', () => {
    const item = ref(null); // Lưu trữ sản phẩm "Mua ngay" tạm thời

    // Computed property để dễ dàng truy cập sản phẩm (nếu có)
    const buyNowItem = computed(() => item.value);

    // Action để thiết lập sản phẩm "Mua ngay"
    const setBuyNowItem = (product) => {
        item.value = product;
    };

    // Action để xóa sản phẩm "Mua ngay" (sau khi đặt hàng hoặc rời trang)
    const clearBuyNowItem = () => {
        item.value = null;
    };

    return {
        item, // Export ref gốc
        buyNowItem, // Export computed (nên dùng cái này để truy cập)
        setBuyNowItem,
        clearBuyNowItem,
    };
});