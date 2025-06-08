<template>
  <div id="printArea">
  <div class="order-detail-container">
    <div class="card">
      <div class="card-header d-flex justify-content-between align-items-center">
        <span>Chi tiết Đơn hàng #{{ order.orderId }}</span>
        <button class="btn btn-secondary btn-sm fixed" @click="goBack">
           <i class="fas fa-arrow-left me-1 "></i>
           Quay lại
        </button>
      </div>
      <div class="card-body">
        <div v-if="loadingOrder" class="text-center text-muted">Đang tải thông tin đơn hàng...</div>
        <div v-else-if="!order || !order.orderId" class="text-center text-danger">Không tìm thấy thông tin đơn hàng.</div>
        <div v-else>
          <!-- Thông tin khách hàng -->
          <div class="row mb-4">
            <div class="col-md-6">
              <h5>Thông tin khách hàng</h5>
              <div class="mb-2">
                <strong>Tên khách hàng:</strong> {{ order.username }}
              </div>
              <div class="mb-2" v-if="order.phone">
                <strong>Số điện thoại:</strong> {{ order.phone }}
              </div>
              <div class="mb-2" v-if="order.address">
                <strong>Địa chỉ:</strong> {{ order.address }}
              </div>
            </div>
            <div class="col-md-6">
              <h5>Thông tin đơn hàng</h5>
              <div class="mb-2">
                <strong>Ngày đặt:</strong> {{ formatDate(order.orderDate) }}
              </div>
              <div class="mb-2">
                <strong>Phương thức thanh toán:</strong> {{ order.paymentMethod.paymentMethodName }}
              </div>
              <div class="mb-2">
                <strong>Trạng thái đơn hàng:</strong>
                <span class="ms-3" :class=" getOrderStateClass(order.orderState)">{{ order.orderState }}</span>
              </div>
              <div class="mb-2">
                <strong>Trạng thái thanh toán:</strong>
                <span :class="getPaymentStateClass(order.paymentState)">{{ order.paymentState }}</span>
              </div>
              <div class="mb-2" v-if="order.note">
                <strong>Ghi chú:</strong> {{ order.note }}
              </div>
            </div>
          </div>

          <!-- Chi tiết sản phẩm -->
          <div class="mb-4">
            <h5>Chi tiết sản phẩm</h5>
            <div class="table-responsive">
              <table class="table table-striped table-bordered custom-orders-table">
                <thead class="table-light">
                  <tr>
                    <th>Mã sản phẩm</th>
                    <th>Tên sản phẩm</th>
                    <th>Màu sắc</th>
                    <th>Kích thước</th>
                    <th>Số lượng</th>
                    <th>Đơn giá</th>
                    <th>Thành tiền</th>
                  </tr>
                </thead>
                <tbody>
                  <tr v-for="(detail, index) in order.orderDetails" :key="index">
                    <td>{{ detail.productId }}</td>
                    <td>
                      <div v-if="productDetails[detail.productId]">
                        {{ productDetails[detail.productId].productName }}
                      </div>
                      <div v-else class="text-muted">Đang tải...</div>
                    </td>
                    <td>{{ detail.color }}</td>
                    <td>{{ detail.size }}</td>
                    <td>{{ detail.quantity }}</td>
                    <td>{{ formatCurrency(detail.total_price / detail.quantity) }}</td>
                    <td>{{ formatCurrency(detail.total_price) }}</td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>

          <!-- Tổng cộng -->
          <div class="row">
            <div class="col-md-6 ms-auto">
              <div class="card">
                <div class="card-body">
                  <div class="d-flex justify-content-between mb-2">
                    <span>Tổng tiền hàng:</span>
                    <span>{{ formatCurrency(calculateSubtotal()) }}</span>
                  </div>
                  <div class="d-flex justify-content-between mb-2">
                    <span>Phí vận chuyển:</span>
                    <span>0 VNĐ</span>
                  </div>
                  <hr>
                  <div class="d-flex justify-content-between fw-bold fs-5">
                    <span>Tổng thanh toán:</span>
                    <span class="text-primary">{{ formatCurrency(order.totalPrice) }}</span>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <!-- Các hành động -->
          <div class="mt-4 d-flex gap-2">
            <button
              v-if="canUpdateOrderState()"
              class="btn btn-primary"
              @click="showUpdateOrderModal = true"
              style="background-color: #8b5cf6; border-color: #8b5cf6;">
              <i class="fas fa-edit me-1"></i>
              Cập nhật trạng thái đơn hàng
            </button>
            <button
              v-if="canUpdatePaymentState()"
              class="btn btn-success"
              @click="showUpdatePaymentModal = true">
              <i class="fas fa-credit-card me-1"></i>
              Cập nhật trạng thái thanh toán
            </button>
            <button class="btn btn-outline-primary" @click="printOrder">
              <i class="fas fa-print me-1"></i>
              In đơn hàng
            </button>
          </div>
        </div>
      </div>
    </div>

    <!-- Modal cập nhật trạng thái đơn hàng -->
    <div v-if="showUpdateOrderModal" class="modal d-block" tabindex="-1" style="background-color: rgba(0,0,0,0.5);">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">Cập nhật trạng thái đơn hàng</h5>
            <button type="button" class="btn-close" @click="showUpdateOrderModal = false"></button>
          </div>
          <div class="modal-body">
            <div class="mb-3">
              <label for="orderState" class="form-label">Trạng thái đơn hàng hiện tại</label>
              <div class="alert alert-info">
                <strong>{{ order.orderState }}</strong>
              </div>
            </div>
            <div class="mb-3">
              <label for="newOrderState" class="form-label">Trạng thái mới</label>
              <select class="form-select" id="newOrderState" v-model="updateOrderForm.orderState">
                <option v-for="state in availableOrderStates" :key="state" :value="state">
                  {{ state }}
                </option>
              </select>
            </div>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-secondary" @click="showUpdateOrderModal = false">Hủy</button>
            <button type="button" class="btn btn-primary" @click="updateOrderStatus" :disabled="isUpdatingOrder">
              <span v-if="isUpdatingOrder" class="spinner-border spinner-border-sm me-2"></span>
              {{ isUpdatingOrder ? 'Đang cập nhật...' : 'Cập nhật trạng thái' }}
            </button>
          </div>
        </div>
      </div>
    </div>

    <!-- Modal cập nhật trạng thái thanh toán -->
    <div v-if="showUpdatePaymentModal" class="modal d-block" tabindex="-1" style="background-color: rgba(0,0,0,0.5);">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">Cập nhật trạng thái thanh toán</h5>
            <button type="button" class="btn-close" @click="showUpdatePaymentModal = false"></button>
          </div>
          <div class="modal-body">
            <div class="mb-3">
              <label for="paymentState" class="form-label">Trạng thái thanh toán hiện tại</label>
              <div class="alert alert-warning">
                <strong>{{ order.paymentState }}</strong>
              </div>
            </div>
            <div class="mb-3">
              <label for="newPaymentState" class="form-label">Trạng thái mới</label>
              <select class="form-select" id="newPaymentState" v-model="updatePaymentForm.paymentState">
                <option v-for="state in availablePaymentStates" :key="state" :value="state">
                  {{ state }}
                </option>
              </select>
            </div>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-secondary" @click="showUpdatePaymentModal = false">Hủy</button>
            <button type="button" class="btn btn-success" @click="updatePaymentStatus" :disabled="isUpdatingPayment">
              <span v-if="isUpdatingPayment" class="spinner-border spinner-border-sm me-2"></span>
              {{ isUpdatingPayment ? 'Đang cập nhật...' : 'Cập nhật thanh toán' }}
            </button>
          </div>
        </div>
      </div>
    </div>

  </div>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue';
import axios from 'axios';
import { useRouter, useRoute } from 'vue-router';

const router = useRouter();
const route = useRoute();

// State quản lý dữ liệu đơn hàng
const order = ref({
  orderId: null,
  userId: null,
  username: '',
  paymentMethod: {
    paymentMethodId: null,
    paymentMethodName: ''
  },
  orderDate: '',
  totalPrice: 0,
  orderState: '',
  paymentState: '',
  note: '',
  phone: '',
  address: '',
  orderDetails: []
});

const productDetails = ref({});
const loadingOrder = ref(true);

// Modal states
const showUpdateOrderModal = ref(false);
const showUpdatePaymentModal = ref(false);
const isUpdatingOrder = ref(false);
const isUpdatingPayment = ref(false);

// Form cập nhật trạng thái đơn hàng
const updateOrderForm = ref({
  orderState: ''
});

// Form cập nhật trạng thái thanh toán
const updatePaymentForm = ref({
  paymentState: '',
});

// Danh sách trạng thái đơn hàng có thể cập nhật
const availableOrderStates = computed(() => {
  const currentState = order.value.orderState;
  // const allStates = ["Chờ xác nhận", "Đã xác nhận", "Đang giao", "Đã giao", "Đã hủy"];

  switch (currentState) {
    case "Chờ xác nhận":
      return ["Đang giao", "Đã hủy"];
    case "Đang giao":
      return ["Đã giao","Đã hủy"];
    // case "Đã giao":
    //   return ["Đã hủy"];
    default:
      return [];
  }
});

// Danh sách trạng thái thanh toán có thể cập nhật dựa trên trạng thái đơn hàng
const availablePaymentStates = computed(() => {
  const orderState = order.value.orderState;
  const currentPaymentState = order.value.paymentState;

  switch (orderState) {
    case "Đã hủy":
      return currentPaymentState === "Đã thanh toán" ? ["Đã hoàn tiền"] : ["Chưa thanh toán"];
    case "Đã giao":
      return ["Đã thanh toán"];
    default:
      return ["Chưa thanh toán", "Đã thanh toán"];
  }
});

// Fetch dữ liệu đơn hàng
async function fetchOrderData(orderId) {
  loadingOrder.value = true;
  try {
    const response = await axios.get(`http://localhost:8080/api/orders/${orderId}`);
    order.value = response.data;
    updateOrderForm.value.orderState = order.value.orderState;
    updatePaymentForm.value.paymentState = order.value.paymentState;
    await fetchProductDetails();
  } catch (error) {
    console.error('Error fetching order data:', error);
    alert('Không thể tải thông tin đơn hàng.');
  } finally {
    loadingOrder.value = false;
  }
}

// Lấy thông tin chi tiết sản phẩm
async function fetchProductDetails() {
  const productIds = [...new Set(order.value.orderDetails.map(detail => detail.productId))];
  for (const productId of productIds) {
    try {
      const response = await axios.get(`http://localhost:8080/api/products/${productId}`);
      productDetails.value[productId] = response.data;
    } catch (error) {
      console.error(`Error fetching product ${productId}:`, error);
      productDetails.value[productId] = { productName: 'Không tìm thấy sản phẩm' };
    }
  }
}


// Cập nhật trạng thái đơn hàng
async function updateOrderStatus() {
  if (isUpdatingOrder.value) return;

  if (updateOrderForm.value.orderState === order.value.orderState) {
    alert('Vui lòng chọn trạng thái khác với trạng thái hiện tại.');
    return;
  }

  isUpdatingOrder.value = true;
  try {
    const updateData = {
      orderId: order.value.orderId,
      orderState: updateOrderForm.value.orderState
    };

    // Cập nhật trạng thái đơn hàng
    await axios.put(`http://localhost:8080/api/orders/status`, updateData);

   if (updateOrderForm.value.orderState === "Đã hủy") {
  for (const item of order.value.orderDetails) {
    const inventoryUpdate = {
      quantity: item.quantity // hoặc item.quantity + item.returnedQuantity nếu có logic hoàn trả
    };
    const encodedColor = encodeURIComponent(item.color);
const encodedSize = encodeURIComponent(item.size);

    const url = `http://localhost:8080/api/product-inventory/update/${item.productId}/${encodedColor}/${encodedSize}`;
    await axios.put(url, inventoryUpdate);
    // await axios.put(`http://localhost:8080/api/inventory/update/${item.productId}/${encodedColor}/${encodedSize}`, inventoryUpdate);
  }
}

    order.value.orderState = updateOrderForm.value.orderState;
    showUpdateOrderModal.value = false;
    alert('Cập nhật trạng thái đơn hàng thành công!');
  } catch (error) {
    console.error('Lỗi khi cập nhật trạng thái đơn hàng:', error);
    alert('Có lỗi xảy ra khi cập nhật trạng thái đơn hàng.');
  } finally {
    isUpdatingOrder.value = false;
  }
}


// Cập nhật trạng thái thanh toán
async function updatePaymentStatus() {
  if (isUpdatingPayment.value) return;

  if (updatePaymentForm.value.paymentState === order.value.paymentState) {
    alert('Vui lòng chọn trạng thái thanh toán khác với trạng thái hiện tại.');
    return;
  }

  isUpdatingPayment.value = true;
  try {
    const updateData = {
      orderId: order.value.orderId,
      paymentState: updatePaymentForm.value.paymentState,
    };
    await axios.put(`http://localhost:8080/api/orders/payment-status`, updateData);
    order.value.paymentState = updatePaymentForm.value.paymentState;
    showUpdatePaymentModal.value = false;
    alert('Cập nhật trạng thái thanh toán thành công!');
  } catch (error) {
    console.error('Lỗi khi cập nhật trạng thái thanh toán:', error);
    alert('Có lỗi xảy ra khi cập nhật trạng thái thanh toán.');
  } finally {
    isUpdatingPayment.value = false;
  }
}

// Các hàm tiện ích
function formatDate(dateString) {
  const date = new Date(dateString);
  return date.toLocaleString('vi-VN');
}

function formatCurrency(amount) {
  return new Intl.NumberFormat('vi-VN', {
    style: 'currency',
    currency: 'VND'
  }).format(amount);
}

function calculateSubtotal() {
  return order.value.orderDetails.reduce((sum, detail) => sum + detail.total_price, 0);
}

function canUpdateOrderState() {
  return ['Chờ xác nhận','Đang giao'].includes(order.value.orderState);
}

function canUpdatePaymentState() {
  const paymentState = order.value.paymentState;
  // Không cho phép cập nhật nếu đã hoàn tiền
  if (paymentState === 'Đã hoàn tiền') {
    return false;
  }

  return true;
}

function getOrderStateClass(state) {
  switch (state) {
    case 'Chờ xác nhận': return 'status-badge status-pending';
    case 'Đang giao': return 'status-badge status-shipping';
    case 'Đã giao': return 'status-badge status-delivered';
    case 'Đã hủy': return 'status-badge status-cancelled';
    default: return 'status-badge bg-light text-dark';
  }
}

function getPaymentStateClass(state) {
  switch (state) {
    case 'Chưa thanh toán': return 'status-badge status-pending ms-2';
    case 'Đã thanh toán': return 'status-badge status-delivered ms-2';
    case 'Đã hoàn tiền': return 'tatus-badge status-confirmed ms-2';
    default: return 'badge bg-light text-dark';
  }
}

function printOrder() {
  window.print();
}

function goBack() {
  router.back();
}

onMounted(() => {
  const orderId = route.params.orderId;
  if (orderId) {
    order.value.orderId = orderId;
    fetchOrderData(orderId);
  } else {
    alert("Không tìm thấy ID đơn hàng.");
    router.push('/admin/orders');
  }
});
</script>


<style scoped>
.order-detail-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
}

.card {
  box-shadow: 0 0 10px rgba(0,0,0,0.1);
  border: none;
}

/* .table th {
  background-color: #f8f9fa;
  font-weight: 600;
} */

.badge {
  font-size: 0.875em;
}

.modal {
  z-index: 1050;
}

@media print {
  body * {
    visibility: hidden;
  }

  #printArea,
  #printArea * {
    visibility: visible;
  }

  #printArea {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    background: white;
    padding: 20px;
  }

  .sidebar, .mt-auto, button, .nav, .modal {
    display: none !important;
  }
}


.fixed{
  position: fixed;
  top: 49px;
  right: 75px;
  z-index: 1050; /* Đảm bảo nút luôn hiển thị trên cùng */
}

</style>
