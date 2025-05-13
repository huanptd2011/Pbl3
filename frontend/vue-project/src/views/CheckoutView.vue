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
                  <form>
                      <!-- Số điện thoại -->
                      <div class="mb-3">
                          <label for="phoneNumber" class="form-label">Số điện thoại</label>
                          <input 
                              type="tel" 
                              class="form-control" 
                              id="phoneNumber" 
                              placeholder="Số điện thoại" 
                              required
                              v-model="userPhone"
                          >
                      </div>

                      <!-- Địa chỉ -->
                      <div class="mb-3">
                          <label for="deliveryAddress" class="form-label">Địa chỉ nhận hàng</label>
                          <textarea class="form-control" 
                                    id="deliveryAddress" 
                                    rows="2" 
                                    placeholder="Tên, Số nhà, đường, phường/xã, quận/huyện, tỉnh/thành phố"
                                    v-model="userAddress"></textarea>
                      </div>

                      <!-- Ghi chú (tuỳ chọn) -->
                      <div class="mb-3">
                          <label for="deliveryNotes" class="form-label">Ghi chú (nếu có)</label>
                          <textarea class="form-control" 
                                    id="deliveryNotes" 
                                    rows="2" 
                                    placeholder="Ghi chú"
                                    v-model="userNote"></textarea>
                      </div>
                </form>
              </div>
          </div>

          <h4>Phương thức thanh toán</h4>
          <div class="card mb-4">
            <div class="card-body">
              <div class="payment-methods">
                <!-- Trạng thái loading -->
                <div v-if="isLoadingPaymentMethods" class="text-center py-3 loading-spinner">
                  <div class="spinner-border text-primary" role="status">
                    <span class="visually-hidden">Loading...</span>
                  </div>
                  <p class="text-muted mt-2">Đang tải phương thức thanh toán...</p>
                </div>

                <!-- Hiển thị lỗi nếu có -->
                <div v-else-if="paymentMethodsError" class="alert alert-danger">
                  {{ paymentMethodsError }}
                </div>

                <!-- Hiển thị danh sách payment methods -->
                <div v-else>
                  <div 
                    v-for="method in paymentMethods" 
                    :key="method.paymentMethodId"
                    class="payment-method"
                    :class="{ active: selectedPaymentMethod === method.paymentMethodId }"
                    @click="selectedPaymentMethod = Number(method.paymentMethodId)"
                  >
                    <div class="d-flex align-items-center">
                      <div>
                        <h6 class="mb-1">{{ method.paymentMethodName }}</h6>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <!-- Thêm trường ẩn để lưu phương thức thanh toán đã chọn -->
          <input type="hidden" name="payment_method" :value="selectedPaymentMethod">

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
  import { useUserStore } from '@/stores/user'; // Import user store

  const cartStore = useCartStore();  
  const userStore = useUserStore();
  const router = useRouter();

  const userPhone = userStore.user.phone; // Lấy số điện thoại từ store
  const userAddress = userStore.user.fullName + ", " + userStore.user.address; // Lấy địa chỉ từ store
  const userNote = '';


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

      if (!selectedPaymentMethod.value) {
          alert('Vui lòng điền đầy đủ thông tin giao hàng và chọn phương thức thanh toán.');
          return;
      }

      // Bắt đầu quá trình xử lý đặt hàng
      isProcessingOrder.value = true;

      // Chuẩn bị dữ liệu đơn hàng để gửi lên backend
      const orderPayload = {
          // Lấy danh sách các item đã chọn từ store
          listOrderDetail: cartStore.selectedItems.map(item => ({
              productId: item.productId,
              color: item.color,
              size: item.size,
              quantity: item.quantity,
              price: item.price
          })),
          //Thong tin nguoi dung
          userId: userStore.user.userId, // Lấy userId từ store
          address: userAddress, // Địa chỉ từ người dùng
          phone: userPhone,
          paymentMethodId: selectedPaymentMethod.value, // Lấy phương thức thanh toán đã chọn
          notes: userNote || '', // Ghi chú từ người dùng
          totalPrice: cartStore.totalSelectedPrice // Tổng giá trị đơn hàng
      };

      console.log("Dữ liệu gửi đi để tạo đơn hàng:", orderPayload); // Log dữ liệu gửi đi

      try {
          const token = userStore.user.token; // Hoặc từ Vuex nếu dùng Vuex

          console.log("Token:", token); // Log token để kiểm tra

        if (!token) {
            alert('Bạn cần đăng nhập để thực hiện đơn hàng!');
            router.push({ name: 'Login' }); // Chuyển hướng đến trang đăng nhập
            return;
        }
        console.log("token:", token); // Log token để kiểm tra
        // Gửi yêu cầu POST đến backend với token trong header
        const response = await axios.post('http://localhost:8080/api/orders/add', orderPayload);
        console.log("token:", token); // Log token để kiểm tra
        console.log("Kết quả từ backend:", response.data);

        const createdOrder = response.data;

        // Sau khi đặt hàng thành công
        const productIds = orderPayload.listOrderDetail.map(item => item.productId);
        cartStore.removeItems(productIds);
        cartStore.removeSelectedItems(); // Xóa các sản phẩm khỏi giỏ hàng
        alert('Đặt hàng thành công!');


        // router.push({ name: 'OrderConfirmation', params: { orderId: createdOrder.orderId } });
      } catch (error) {
          console.error('Lỗi khi đặt hàng:', error);
          alert('Đã xảy ra lỗi khi đặt hàng. Vui lòng thử lại.'); // Thông báo lỗi đơn giản
          // Xử lý lỗi chi tiết hơn nếu cần (ví dụ: tồn kho không đủ)
      } finally {
          // Kết thúc quá trình xử lý
          isProcessingOrder.value = false;
      }
  };



    // <-- TẢI DANH SÁCH PHƯƠNG THỨC THANH TOÁN -->
    // Thêm state cho payment methods
    const paymentMethods = ref([]);
    const selectedPaymentMethod = ref(null);
    const isLoadingPaymentMethods = ref(false);
    const paymentMethodsError = ref(null);

    // Hàm load payment methods
    const loadPaymentMethods = async () => {
      try {
        isLoadingPaymentMethods.value = true;
        paymentMethodsError.value = null;
        
        const response = await axios.get('http://localhost:8080/api/payment-methods');
        paymentMethods.value = response.data;
        
      } catch (error) {
        console.error('Error loading payment methods:', error);
        paymentMethodsError.value = 'Không thể tải phương thức thanh toán. Vui lòng thử lại sau.';
      } finally {
        isLoadingPaymentMethods.value = false;
      }
    };


  // Kiểm tra khi component được mount: nếu không có item nào được chọn, chuyển hướng về giỏ hàng
  onMounted(() => {
      loadPaymentMethods();
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


    .payment-method {
        border: 1px solid #dee2e6;
        border-radius: 8px;
        padding: 15px;
        margin-bottom: 10px;
        cursor: pointer;
        transition: all 0.3s;
    }
    .payment-method:hover {
        border-color: #0d6efd;
        background-color: #f8f9fa;
    }
    .payment-method.active {
        border-color: #0d6efd;
        background-color: #e7f1ff;
    }
    .payment-method img {
        height: 30px;
        margin-right: 15px;
    }
 </style>
