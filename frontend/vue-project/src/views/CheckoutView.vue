<template>
    <div class="container py-5">
      <h2>Thanh toán</h2>
  
      <div v-if="cartStore.selectedItems.length === 0" class="alert alert-warning mt-4">
          Không có sản phẩm nào được chọn để thanh toán.
          <router-link to="/cart" class="alert-link">Quay lại giỏ hàng</router-link> để chọn sản phẩm.
      </div>
  
      <div v-else class="row">
        <div class="col-md-8">
          <h4>Sản phẩm đã chọn ({{ cartStore.totalSelectedItemsCount }})</h4>
          <ul class="list-group mb-4">
            <li v-for="item in cartStore.selectedItems" :key="`${item.productId}-${item.color}-${item.size}`" class="list-group-item d-flex justify-content-between align-items-center">
               <div class="d-flex align-items-center">
                   <img :src="item.imageUrl" alt="Ảnh sản phẩm" style="width: 50px; height: 50px; object-fit: cover; margin-right: 15px; border-radius: 4px;">
                   <div>
                       <h6 class="my-0">{{ item.name }} <span class="text-muted small">({{ item.color }}, {{ item.size }})</span></h6>
                       <small class="text-muted">Số lượng: {{ item.quantity }}</small>
                   </div>
               </div>
               <span class="text-success fw-bold">{{ formatPrice(item.price * item.quantity) }}₫</span>
            </li>
          </ul>
  
          <h4>Thông tin giao hàng</h4>
          <div class="card mb-4">
              <div class="card-body">
                  <p class="card-text text-muted">
                      [Form nhập thông tin giao hàng: Tên, địa chỉ, SĐT... ]
                  </p>
                  </div>
          </div>
  
           <h4>Phương thức thanh toán</h4>
          <div class="card mb-4">
              <div class="card-body">
                   <p class="card-text text-muted">
                      [Chọn phương thức thanh toán: COD, Chuyển khoản, Cổng thanh toán...]
                  </p>
                  </div>
          </div>
  
          </div>
  
        <div class="col-md-4">
          <div class="card">
            <div class="card-header">
              Tóm tắt đơn hàng
            </div>
            <div class="card-body">
              <div class="d-flex justify-content-between">
                   <p>Tổng giá sản phẩm:</p>
                   <p class="fw-bold">{{ formatPrice(cartStore.totalSelectedPrice) }}₫</p>
              </div>
              
              <div class="d-flex justify-content-between text-muted small">
                   <p>Phí vận chuyển:</p>
                   <p>[Tính toán...]</p>
               </div>
                <div class="d-flex justify-content-between text-muted small">
                   <p>Mã giảm giá:</p>
                   <p>[Áp dụng...]</p>
               </div>
               
               <hr> <div class="d-flex justify-content-between align-items-center mb-3">
                   <h5 class="mb-0">Thành tiền:</h5>
                   <h5 class="mb-0 text-success">{{ formatPrice(cartStore.totalSelectedPrice /* + shipping - discount */) }}₫</h5>
               </div>
  
  
              <button 
                class="btn btn-primary btn-lg w-100 mt-3" 
                @click="placeOrder"
                :disabled="isProcessingOrder || cartStore.selectedItems.length === 0" 
              >
                <span v-if="isProcessingOrder" class="spinner-border spinner-border-sm me-2" role="status" aria-hidden="true"></span>
                {{ isProcessingOrder ? 'Đang xử lý...' : 'Xác nhận đặt hàng' }}
              </button>
               <small class="text-muted d-block mt-2 text-center">[Cần điền đủ thông tin để đặt hàng]</small> </div>
          </div>
        </div>
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref, onMounted } from 'vue'; // Import onMounted
  import { useCartStore } from '@/stores/cartStore'; // Import cart store
  import { useRouter } from 'vue-router'; // Import useRouter
  import axios from 'axios'; // Import axios
  
  const cartStore = useCartStore();
  const router = useRouter();
  
  const isProcessingOrder = ref(false); // State để hiển thị loading trên nút
  
  // Hàm format giá
  const formatPrice = (price) => {
     return price?.toLocaleString('vi-VN') ?? '';
  };
  
  // <-- HÀM XỬ LÝ KHI NHẤN NÚT "Xác nhận đặt hàng" -->
  const placeOrder = async () => {
      // Kiểm tra xem có sản phẩm nào được chọn không
      if (cartStore.selectedItems.length === 0) {
          alert('Vui lòng chọn sản phẩm để thanh toán.');
          router.push({ name: 'Cart' }); // Chuyển hướng về giỏ hàng nếu không có item
          return;
      }
  
      // Kiểm tra xem người dùng đã điền đủ thông tin giao hàng/thanh toán chưa (Placeholder)
      // Đây là nơi bạn sẽ validate các form nhập liệu. 
      // Hiện tại bỏ qua bước này cho đơn giản.
      // if (!isShippingInfoValid || !isPaymentMethodSelected) {
      //     alert('Vui lòng điền đầy đủ thông tin giao hàng và chọn phương thức thanh toán.');
      //     return;
      // }
  
      // Bắt đầu quá trình xử lý đặt hàng
      isProcessingOrder.value = true;
  
      // Chuẩn bị dữ liệu đơn hàng để gửi lên backend
      const orderPayload = {
          // Lấy danh sách các item đã chọn từ store
          items: cartStore.selectedItems.map(item => ({
              productId: item.productId,
              color: item.color,
              size: item.size,
              quantity: item.quantity,
              priceAtPurchase: item.price, // Nên lưu giá tại thời điểm mua để tránh thay đổi giá sau này
              // Có thể thêm các thông tin khác nếu backend cần (tên sản phẩm, ảnh...)
              name: item.name,
              imageUrl: item.imageUrl,
              brand: item.brand
          })),
          // Thêm thông tin người dùng, địa chỉ giao hàng, phương thức thanh toán... vào đây
          // userId: ...,
          // shippingAddress: {...},
          // paymentMethod: ...,
          // notes: ...,
          // totalPrice: cartStore.totalSelectedPrice // Backend nên tính toán lại giá cuối cùng
      };
  
      console.log("Dữ liệu gửi đi để tạo đơn hàng:", orderPayload); // Log dữ liệu gửi đi
  
      try {
          // <-- GỬI YÊU CẦU ĐẾN ENDPOINT TẠO ĐƠN HÀNG CỦA BACKEND -->
          // Endpoint này cần được bạn xây dựng ở backend (ví dụ: POST /api/orders hoặc POST /checkout)
          // Endpoint này sẽ nhận orderPayload, kiểm tra tồn kho lần cuối, trừ tồn kho, tạo đơn hàng trong DB, v.v.
          const response = await axios.post('http://localhost:8080/ShopNHN/orders', orderPayload); // <-- Thay thế URL này bằng endpoint backend thực tế của bạn
  
          console.log("Kết quả từ backend:", response.data);
  
          // Giả định backend trả về thông tin đơn hàng sau khi thành công
          const createdOrder = response.data; 
  
          // <-- SAU KHI ĐẶT HÀNG THÀNH CÔNG -->
          // 1. Xóa các sản phẩm vừa checkout khỏi giỏ hàng frontend
          cartStore.removeSelectedItems(); 
  
          // 2. Chuyển hướng người dùng đến trang xác nhận đơn hàng hoặc trang cảm ơn
          // Truyền ID đơn hàng nếu có để hiển thị chi tiết trên trang xác nhận
          alert('Đặt hàng thành công!'); // Thông báo thành công đơn giản
          router.push({ name: 'OrderConfirmation', params: { orderId: createdOrder.orderId } }); // <-- Cần có route 'OrderConfirmation'
  
      } catch (error) {
          console.error('Lỗi khi đặt hàng:', error);
          alert('Đã xảy ra lỗi khi đặt hàng. Vui lòng thử lại.'); // Thông báo lỗi đơn giản
          // Xử lý lỗi chi tiết hơn nếu cần (ví dụ: tồn kho không đủ)
      } finally {
          // Kết thúc quá trình xử lý
          isProcessingOrder.value = false;
      }
  };
  
  // Kiểm tra khi component được mount: nếu không có item nào được chọn, chuyển hướng về giỏ hàng
  onMounted(() => {
      if (cartStore.selectedItems.length === 0) {
          console.warn("Checkout page loaded with no selected items. Redirecting to cart.");
          router.replace({ name: 'Cart' }); // Sử dụng replace để người dùng không thể quay lại trang checkout trống bằng nút back
      }
       // Tùy chọn: Khôi phục thông tin giao hàng/thanh toán đã nhập trước đó nếu có
  });
  
  
  // Bạn sẽ cần một component OrderConfirmationView.vue và route '/order-confirmation/:orderId'
  // để hiển thị trang xác nhận đơn hàng.
  </script>
  
  <style scoped>
  /* Thêm CSS cho trang checkout nếu cần */
  .list-group-item img {
      flex-shrink: 0; /* Ngăn ảnh bị co */
  }
  </style>