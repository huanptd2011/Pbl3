<template>
  <div class="container py-5">
    <h2>Thanh toán</h2>

    <div v-if="itemsToCheckout.length === 0" class="alert alert-warning mt-4">
      Không có sản phẩm nào được chọn để thanh toán.
      <router-link to="/cart" class="alert-link">Quay lại giỏ hàng</router-link> để chọn sản phẩm hoặc mua ngay.
    </div>

    <div v-else class="row">
      <div class="col-md-8">
        <h4>Sản phẩm đã chọn ({{ totalItemsCount }})</h4>
        <ul class="list-group mb-4">
          <li v-for="item in itemsToCheckout" :key="`${item.productId}-${item.color}-${item.size}`"
            class="list-group-item d-flex justify-content-between align-items-center">
            <div class="d-flex align-items-center">
              <img :src="item.imageUrl" alt="Ảnh sản phẩm"
                style="width: 50px; height: 50px; object-fit: cover; margin-right: 15px; border-radius: 4px;">
              <div>
                <h6 class="my-0">{{ item.productName }} </h6>
                <span class="text-muted small">{{ 'Màu: ' + item.color + ',  Size: ' + item.size + ',  Số lượng: ' +
                  item.quantity}}</span>
              </div>
            </div>
            <span class="text-success fw-bold">{{ formatPrice(item.price * item.quantity) }}₫</span>
          </li>
        </ul>

        <h4>Thông tin giao hàng</h4>
        <div class="card mb-4">
          <div class="card-body">
            <form>
              <div class="mb-3">
                <label for="phoneNumber" class="form-label">Số điện thoại</label>
                <input type="tel" class="form-control" id="phoneNumber" placeholder="Số điện thoại" required
                  v-model="userPhone">
              </div>

              <div class="mb-3">
                <label for="deliveryAddress" class="form-label">Địa chỉ nhận hàng</label>
                <textarea class="form-control" id="deliveryAddress" rows="2"
                  placeholder="Tên, Số nhà, đường, phường/xã, quận/huyện, tỉnh/thành phố"
                  v-model="userAddress"></textarea>
              </div>

              <div class="mb-3">
                <label for="deliveryNotes" class="form-label">Ghi chú (nếu có)</label>
                <textarea class="form-control" id="deliveryNotes" rows="2" placeholder="Ghi chú"
                  v-model="userNote"></textarea>
              </div>
            </form>
          </div>
        </div>

        <h4>Phương thức thanh toán</h4>
        <div class="card mb-4">
          <div class="card-body">
            <div class="payment-methods">
              <div v-if="isLoadingPaymentMethods" class="text-center py-3 loading-spinner">
                <div class="spinner-border text-primary" role="status">
                  <span class="visually-hidden">Loading...</span>
                </div>
                <p class="text-muted mt-2">Đang tải phương thức thanh toán...</p>
              </div>

              <div v-else-if="paymentMethodsError" class="alert alert-danger">
                {{ paymentMethodsError }}
              </div>

              <div v-else>
                <div v-for="method in paymentMethods" :key="method.paymentMethodId" class="payment-method"
                  :class="{ active: selectedPaymentMethod === method.paymentMethodId }"
                  @click="selectedPaymentMethod = Number(method.paymentMethodId)">
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

        <input type="hidden" name="payment_method" :value="selectedPaymentMethod">

      </div>

      <div class="col-md-4">
        <div class="card">
          <div class="card-header">
            Tóm tắt đơn hàng
          </div>
          <div class="card-body">
            <div class="d-flex justify-content-between">
              <p>Tổng tiền:</p>
              <p class="fw-bold">{{ formatPrice(totalAmountToPay) }}₫</p>
            </div>
            <div class="d-flex justify-content-between">
              <p>Tổng số lượng sản phẩm:</p>
              <p class="fw-bold">{{ totalItemsCount }}</p>
            </div>

            <hr>
            <div class="d-flex justify-content-between align-items-center mb-3">
              <h5 class="mb-0">Thành tiền:</h5>
              <h5 class="mb-0 text-success">{{ formatPrice(totalAmountToPay /* + shipping - discount */) }}₫</h5>
            </div>


            <button class="btn btn-primary btn-lg w-100 mt-3" @click="placeOrder"
              :disabled="isProcessingOrder || itemsToCheckout.length === 0 || !userPhone || !userAddress || !selectedPaymentMethod">
              <span v-if="isProcessingOrder" class="spinner-border spinner-border-sm me-2" role="status"
                aria-hidden="true"></span>
              {{ isProcessingOrder ? 'Đang xử lý...' : 'Xác nhận đặt hàng' }}
            </button>
            <small class="text-muted d-block mt-2 text-center">[Cần điền đủ thông tin để đặt hàng]</small>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue'; // Import computed
import { useCartStore } from '@/stores/cartStore';
import { useRouter } from 'vue-router';
import axios from 'axios';
import { useUserStore } from '@/stores/user';
import { useBuyNowStore } from '@/stores/buyNowStore'; // Import buyNowStore

const cartStore = useCartStore();
const userStore = useUserStore();
const router = useRouter();
const buyNowStore = useBuyNowStore(); // Khai báo buyNowStore

// Sử dụng ref để đảm bảo tính reactive cho các trường input
const userPhone = ref('');
const userAddress = ref('');
const userNote = ref('');

const isProcessingOrder = ref(false); // State để hiển thị loading trên nút

// Computed property để xác định các sản phẩm sẽ được thanh toán
const itemsToCheckout = computed(() => {
  // Nếu có sản phẩm trong buyNowStore, ưu tiên thanh toán sản phẩm đó
  if (buyNowStore.buyNowItem) {
    return [buyNowStore.buyNowItem];
  }
  // Ngược lại, sử dụng các sản phẩm đã chọn từ giỏ hàng
  return cartStore.selectedItems;
});

// Computed property cho tổng số lượng sản phẩm sẽ thanh toán
const totalItemsCount = computed(() => {
  return itemsToCheckout.value.reduce((acc, item) => acc + item.quantity, 0);
});

// Computed property cho tổng tiền cần thanh toán
const totalAmountToPay = computed(() => {
  return itemsToCheckout.value.reduce((acc, item) => acc + (item.price * item.quantity), 0);
});

// Hàm format giá
const formatPrice = (price) => {
  return price?.toLocaleString('vi-VN') ?? '';
};

// <-- HÀM XỬ LÝ KHI NHẤN NÚT "Xác nhận đặt hàng" -->
const placeOrder = async () => {
  // Kiểm tra thông tin giao hàng và phương thức thanh toán
  if (!userPhone.value || !userAddress.value || !selectedPaymentMethod.value) {
    alert('Vui lòng điền đầy đủ thông tin giao hàng và chọn phương thức thanh toán.');
    isProcessingOrder.value = false;
    return;
  }

  // Kiểm tra xem có sản phẩm nào để thanh toán không
  if (itemsToCheckout.value.length === 0) {
    alert('Không có sản phẩm nào được chọn để thanh toán.');
    router.push({ name: 'Cart' });
    return;
  }

  // Bắt đầu quá trình xử lý đặt hàng
  isProcessingOrder.value = true;

  // Chuẩn bị dữ liệu đơn hàng để gửi lên backend
  const orderPayload = {
    // Lấy danh sách các item từ itemsToCheckout (có thể là từ giỏ hàng hoặc mua ngay)
    listOrderDetail: itemsToCheckout.value.map(item => ({
      productId: item.productId,
      color: item.color,
      size: item.size,
      quantity: item.quantity,
      price: item.price
    })),
    userId: userStore.user.userId,
    address: userAddress.value,
    phone: userPhone.value,
    paymentMethodId: selectedPaymentMethod.value,
    notes: userNote.value || '',
    totalPrice: totalAmountToPay.value // Tổng tiền từ itemsToCheckout
  };

  console.log("Dữ liệu gửi đi để tạo đơn hàng:", orderPayload);

  try {
    const token = userStore.user.token;

    if (!token) {
      alert('Bạn cần đăng nhập để thực hiện đơn hàng!');
      router.push({ name: 'Login' });
      return;
    }

    const response = await axios.post('http://localhost:8080/api/orders/add', orderPayload, {
      headers: {
        Authorization: `Bearer ${token}`
      }
    });
    console.log("Kết quả từ backend:", response.data);

    // Xử lý VNPay redirect
    if (selectedPaymentMethod.value === 2) { // Giả sử ID 2 là VNPay
      const vnPayResponse = await axios.get(`http://localhost:8080/api/vn-pay/create/${response.data.totalPrice}`);

      if (vnPayResponse.data && vnPayResponse.data.url) {
        window.location.href = vnPayResponse.data.url;
        return; // Ngừng xử lý tiếp nếu đã chuyển hướng
      } else {
        alert('Không thể tạo link thanh toán VNPay.');
        return;
      }
    }

    // Sau khi đặt hàng thành công (đối với COD hoặc VNPay nếu không redirect)
    // Xóa sản phẩm khỏi giỏ hàng HOẶC xóa sản phẩm mua ngay
    if (buyNowStore.buyNowItem) {
      buyNowStore.clearBuyNowItem(); // Xóa sản phẩm mua ngay
    } else {
      const productIds = orderPayload.listOrderDetail.map(item => item.productId);

      cartStore.removeItems(productIds);

      console.log("Đã xóa các sản phẩm khỏi giỏ hàng:", productIds);
      cartStore.removeSelectedItems(); 
      console.log("cac san pham con lai trong gio hang:", cartStore.items);
      cartStore.setAllItemsSelected(true)
      console.log("cac san pham con lai trong gio hang:", cartStore.loadUserCart(userStore.user.userId));
      // Xóa các sản phẩm khỏi giỏ hàng
    }

    alert('Đặt hàng thành công!');

    // --- CHUYỂN HƯỚNG SAU KHI ĐẶT HÀNG THÀNH CÔNG ---
    // Do bạn không có route OrderConfirmation với params, chúng ta sẽ chuyển hướng về giỏ hàng
    router.push({ name: 'Cart' });
    // Nếu bạn muốn một trang xác nhận đơn hàng, bạn cần định nghĩa route đó trong router/index.js
    // Ví dụ: { path: '/order-confirmation', name: 'OrderConfirmation', component: OrderConfirmationView }
    // Và sau đó sử dụng: router.push({ name: 'OrderConfirmation' });

  } catch (error) {
    console.error('Lỗi khi đặt hàng:', error.response?.data || error.message);
    alert('Đã xảy ra lỗi khi đặt hàng. Vui lòng thử lại. ' + (error.response?.data?.message || ''));
  } finally {
    isProcessingOrder.value = false;
  }
};

// <-- TẢI DANH SÁCH PHƯƠNG THỨC THANH TOÁN -->
const paymentMethods = ref([]);
const selectedPaymentMethod = ref(null);
const isLoadingPaymentMethods = ref(false);
const paymentMethodsError = ref(null);

const loadPaymentMethods = async () => {
  try {
    isLoadingPaymentMethods.value = true;
    paymentMethodsError.value = null;

    const response = await axios.get('http://localhost:8080/api/payment-methods');
    paymentMethods.value = response.data;

    // Mặc định chọn phương thức đầu tiên nếu có
    if (paymentMethods.value.length > 0) {
      selectedPaymentMethod.value = Number(paymentMethods.value[0].paymentMethodId);
    }

  } catch (error) {
    console.error('Error loading payment methods:', error);
    paymentMethodsError.value = 'Không thể tải phương thức thanh toán. Vui lòng thử lại sau.';
  } finally {
    isLoadingPaymentMethods.value = false;
  }
};

onMounted(() => {
  loadPaymentMethods();

  // Khôi phục thông tin giao hàng từ userStore nếu có
  if (userStore.user.phone) {
    userPhone.value = userStore.user.phone;
  }
  if (userStore.user.fullName || userStore.user.address) {
    userAddress.value = (userStore.user.fullName ? userStore.user.fullName + ", " : "") + (userStore.user.address || '');
  }

  // Nếu không có sản phẩm nào để thanh toán, chuyển hướng về giỏ hàng
  if (itemsToCheckout.value.length === 0) {
    console.warn("Checkout page loaded with no items to checkout. Redirecting to cart.");
    router.replace({ name: 'Cart' });
  }
});
</script>

<style scoped>
/* Giữ nguyên CSS của bạn */
.list-group-item img {
  flex-shrink: 0;
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

.card {
  background-color: #fff;
  border-radius: 12px;
  box-shadow: none;
  color: #212529;
}

.form-check-label {
  color: #131418;
}

.form-check-input:checked {
  background-color: #19181b;
  border-color: #1f1e22;
}

.form-label {
  color: #15171f;
}

/* Style cho nút submit */
.btn-primary {
  background-color: #2945e2;
  border-color: #3447f5;
}

.btn-primary:hover {
  background-color: #1725e6;
  border-color: #101dcf;
}

.card-header {
  background-color: #cacfcb;
  border-bottom: 1px solid #9b9ea3;
  color: #131416;
  border-top-left-radius: 11px;
  border-top-right-radius: 11px;
}
</style>