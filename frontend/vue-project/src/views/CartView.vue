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
  // Không cần useRouter ở đây nếu chỉ checkout toàn bộ giỏ hàng đã chọn
  
  const cartStore = useCartStore();
  
  // Hàm format giá
  const formatPrice = (price) => {
     return price?.toLocaleString('vi-VN') ?? '';
  };
  
  // Computed property để quản lý checkbox "Chọn tất cả"
  const allSelected = computed({
      // Getter: Trả về true nếu TẤT CẢ item đều được chọn và có ít nhất 1 item
      get: () => cartStore.items.length > 0 && cartStore.items.every(item => item.isSelected),
      // Setter: Được gọi khi checkbox "Chọn tất cả" thay đổi trạng thái
      set: (value) => {
          cartStore.setAllItemsSelected(value); // Gọi action trong store để cập nhật tất cả item
      }
  });
  
  // Computed property cho trạng thái indeterminate của checkbox "Chọn tất cả"
  const isIndeterminate = computed(() => {
      const selectedCount = cartStore.selectedItems.length;
      return selectedCount > 0 && selectedCount < cartStore.items.length;
  });
  
  
  // Xử lý khi checkbox của TỪNG item thay đổi trạng thái (Tùy chọn, vì v-model đã hoạt động)
  // Hàm này hữu ích nếu bạn muốn thêm logic phụ sau khi 1 item được chọn/bỏ chọn
  const handleItemSelection = (item) => {
      console.log(`Item ${item.name} (${item.color}, ${item.size}) đã được ${item.isSelected ? 'chọn' : 'bỏ chọn'}`);
      // Không cần gọi store action ở đây vì v-model="item.isSelected" đã tự động cập nhật state trong store
  };
  
  // Xử lý khi checkbox "Chọn tất cả" thay đổi trạng thái
  // Logic chính đã nằm trong setter của computed property allSelected
  const handleSelectAll = (event) => {
      // Setter của computed property allSelected sẽ được gọi khi giá trị của event.target.checked thay đổi
      // Tùy chọn: Thêm console log để debug
      console.log('Checkbox "Chọn tất cả" thay đổi:', event.target.checked);
  };
  
  
  // Hàm xử lý khi nhấn nút Xóa item
  const handleRemoveItem = (item) => {
      if (confirm(`Bạn có chắc chắn muốn xóa sản phẩm "${item.name}" (${item.color}, ${item.size}) khỏi giỏ hàng không?`)) {
          cartStore.removeItem(item.productId, item.color, item.size);
      }
  };
  
  // Hàm xử lý khi thay đổi số lượng item
  const handleUpdateQuantity = (item, event) => {
      const newQuantity = parseInt(event.target.value, 10);
  
      if (isNaN(newQuantity) || newQuantity < 0) {
          alert('Số lượng không hợp lệ. Vui lòng nhập số nguyên dương.');
          event.target.value = item.quantity; 
          return;
      }
  
      // Gọi action updateItemQuantity từ store
      cartStore.updateItemQuantity(item.productId, item.color, item.size, newQuantity);
  };
  
  // Lưu ý: Nút "Tiến hành Thanh toán" sẽ tự động sử dụng cartStore.selectedItems 
  // (getter mới) để lấy danh sách các sản phẩm cần xử lý trên trang CheckoutView.vue
  // Bạn sẽ cần chỉnh sửa component CheckoutView.vue để lấy dữ liệu từ cartStore.selectedItems
  // thay vì cartStore.items
  
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