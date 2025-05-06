<template>
    <div class="container py-5">
      <h2>Giỏ hàng của bạn</h2>

      <div v-if="cartStore.items.length === 0" class="alert alert-info mt-4" role="alert">
        Giỏ hàng của bạn đang trống.
        <router-link to="/products" class="alert-link">Quay lại cửa hàng</router-link> để thêm sản phẩm nhé!
      </div>

      <div v-else>
        <div class="form-check mb-3">
            <input
                class="form-check-input"
                type="checkbox"
                :checked="allSelected"
                :indeterminate="isIndeterminate"
                @change="handleSelectAll($event)"
                id="selectAllItems"
            >
            <label class="form-check-label fw-semibold" for="selectAllItems">
                Chọn tất cả ({{ cartStore.items.length }} sản phẩm)
            </label>
        </div>

        <ul class="list-group mt-2"> <li
            v-for="item in cartStore.items"
            :key="`${item.productId}-${item.color}-${item.size}`"
            class="list-group-item d-flex justify-content-between align-items-center"
          >
            <div class="d-flex align-items-center">
                 <input
                     class="form-check-input me-3"
                     type="checkbox"
                     v-model="item.isSelected"
                     @change="handleItemSelection(item)"
                     :id="`item-select-${item.productId}-${item.color}-${item.size}`"
                 >

                <img :src="item.imageUrl" alt="Ảnh sản phẩm" style="width: 80px; height: 80px; object-fit: cover; margin-right: 15px; border-radius: 4px;">
                <div class="flex-grow-1">
                  <h5 class="mb-1">{{ item.name }}</h5>
                  <p class="mb-1 text-muted small">Màu: {{ item.color }} | Size: {{ item.size }}</p>
                  <p class="mb-0 fw-bold text-danger">{{ formatPrice(item.price) }}₫</p>
                </div>
            </div>

            <div class="d-flex align-items-center ms-3"> <input
                  type="number"
                  :value="item.quantity"
                  min="1"
                  @change="handleUpdateQuantity(item, $event)"
                  class="form-control form-control-sm text-center"
                  style="width: 70px; margin-right: 10px;"
              >

               <button
                  @click="handleRemoveItem(item)"
                  class="btn btn-outline-danger btn-sm"
                  aria-label="Remove item"
               >
                  Xóa
                  </button>
            </div>
          </li>
        </ul>

        <div class="card mt-4">
            <div class="card-body d-flex justify-content-between align-items-center flex-wrap">
                <h4 class="mb-2 mb-md-0">Tổng tiền đã chọn: <span class="text-success">{{ formatPrice(cartStore.totalSelectedPrice) }}₫</span></h4>

                <router-link
                   :to="{ name: 'Checkout' }"
                   class="btn btn-success btn-lg"
                   :disabled="cartStore.selectedItems.length === 0"
                >
                   Tiến hành Thanh toán ({{ cartStore.totalSelectedItemsCount }})
                </router-link>
            </div>
        </div>

      </div>
    </div>
  </template>

  <script setup>
  import { computed } from 'vue'; // Import computed
  import { useCartStore } from '@/stores/cartStore';
  import { useUserStore } from '@/stores/user';
  import { onMounted } from 'vue';



  const cartStore = useCartStore();
  const userStore = useUserStore();

  onMounted(async () => {
    await cartStore.loadUserCart(userStore.user.userId);
  });

  const formatPrice = (price) => {
     return price?.toLocaleString('vi-VN') ?? '';
  };

  const allSelected = computed({
      get: () => cartStore.items.length > 0 && cartStore.items.every(item => item.isSelected),
      set: (value) => {
          cartStore.setAllItemsSelected(value);
      }
  });

  const isIndeterminate = computed(() => {
      const selectedCount = cartStore.selectedItems.length;
      return selectedCount > 0 && selectedCount < cartStore.items.length;
  });

  const handleItemSelection = (item) => {
      console.log(`Item ${item.name} (${item.color}, ${item.size}) đã được ${item.isSelected ? 'chọn' : 'bỏ chọn'}`);
  };

  const handleSelectAll = (event) => {
      console.log('Checkbox "Chọn tất cả" thay đổi:', event.target.checked);
  };


  // Xóa cart item
  const handleRemoveItem = (item) => {
      if (confirm(`Xóa sản phẩm khỏi giỏ hàng không?`)) {
          cartStore.removeItem(item.cartItemId);
        }
  };

  // update quantity item
  const handleUpdateQuantity = (item, event) => {
      const newQuantity = parseInt(event.target.value, 10);

      if (isNaN(newQuantity) || newQuantity < 0) {
          alert('Số lượng không hợp lệ. Vui lòng nhập số nguyên dương.');
          event.target.value = item.quantity;
          return;
      }

      cartStore.updateItemQuantity(item.cartItemId, newQuantity);
  };

  </script>

  <style scoped>
  /* Thêm CSS nếu cần */
  .list-group-item img {
      flex-shrink: 0;
  }
  .list-group-item {
      align-items: center;
  }
  .form-control-sm.text-center {
      width: 70px !important;
  }
  /* Điều chỉnh layout cho responsive nếu cần */
  @media (max-width: 767px) {
      .list-group-item {
          flex-direction: column;
          align-items: flex-start;
      }
      .list-group-item .d-flex.align-items-center.flex-grow-1,
      .list-group-item .d-flex.align-items-center {
          width: 100%;
          justify-content: space-between;
          margin-bottom: 10px;
      }
      .list-group-item .d-flex.align-items-center img {
          margin-right: 10px;
      }
       .list-group-item .d-flex.align-items-center .flex-grow-1 {
           margin-right: 0;
       }
       .list-group-item .d-flex.align-items-center:last-child {
           margin-top: 0;
           justify-content: flex-end;
       }
        .form-control-sm.text-center {
          width: 60px !important; /* Adjust input width on small screens */
      }
  }
  </style>
