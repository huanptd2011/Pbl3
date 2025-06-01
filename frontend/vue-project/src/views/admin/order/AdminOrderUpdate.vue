<template>
  <div id="printArea">
  <div class="order-detail-container">
    <div class="card">
      <div class="card-header d-flex justify-content-between align-items-center">
        <span>Chi tiết Đơn hàng #{{ order.orderId }}</span>
        <button class="btn btn-secondary btn-sm" @click="goBack">
           <i class="fas fa-arrow-left me-1"></i>
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
              <!-- <div class="mb-2">
                <strong>Trạng thái đơn hàng:</strong>
                <span :class="getOrderStateClass(order.orderState)">{{ order.orderState }}</span>
              </div>
              <div class="mb-2">
                <strong>Trạng thái thanh toán:</strong>
                <span :class="getPaymentStateClass(order.paymentState)">{{ order.paymentState }}</span>
              </div> -->
              <div class="mb-2" v-if="order.note">
                <strong>Ghi chú:</strong> {{ order.note }}
              </div>
            </div>
          </div>

          <!-- Chi tiết sản phẩm -->
          <div class="mb-4">
            <h5>Chi tiết sản phẩm</h5>
            <div class="table-responsive">
              <table class="table table-bordered">
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
              Cập nhật trạng thái
            </button>
            <button class="btn btn-outline-primary" @click="printOrder">
              <i class="fas fa-print me-1"></i>
              In đơn hàng
            </button>
          </div>
        </div>
      </div>
    </div>

    <!-- Modal cập nhật trạng thái -->
    <div v-if="showUpdateOrderModal" class="modal d-block" tabindex="-1" style="background-color: rgba(0,0,0,0.5);">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">Cập nhật trạng thái đơn hàng</h5>
            <button type="button" class="btn-close" @click="showUpdateOrderModal = false"></button>
          </div>
          <div class="modal-body">
            <div class="mb-3">
              <label for="orderState" class="form-label">Trạng thái đơn hàng</label>
              <select class="form-select" id="orderState" v-model="updateForm.orderState">
                <option value="Chờ xác nhận">Chờ xác nhận</option>
                <option value="Đã xác nhận">Đã xác nhận</option>
                <option value="Đang chuẩn bị">Đang chuẩn bị</option>
                <option value="Đang giao">Đang giao</option>
                <option value="Đã giao">Đã giao</option>
                <option value="Đã hủy">Đã hủy</option>
              </select>
            </div>
            <div class="mb-3">
              <label for="paymentState" class="form-label">Trạng thái thanh toán</label>
              <select class="form-select" id="paymentState" v-model="updateForm.paymentState">
                <option value="Chưa thanh toán">Chưa thanh toán</option>
                <option value="Đã thanh toán">Đã thanh toán</option>
                <option value="Hoàn tiền">Hoàn tiền</option>
              </select>
            </div>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-secondary" @click="showUpdateOrderModal = false">Hủy</button>
            <button type="button" class="btn btn-primary" @click="updateOrderStatus" :disabled="isUpdating">
              <span v-if="isUpdating" class="spinner-border spinner-border-sm me-2"></span>
              {{ isUpdating ? 'Đang cập nhật...' : 'Cập nhật' }}
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
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

const productDetails = ref({}); // Cache thông tin sản phẩm
const loadingOrder = ref(true);
const showUpdateOrderModal = ref(false);
const isUpdating = ref(false);

// Form cập nhật trạng thái
const updateForm = ref({
  orderState: '',
  paymentState: ''
});

// Fetch thông tin đơn hàng
async function fetchOrderData(orderId) {
  loadingOrder.value = true;
  try {
    const response = await axios.get(`http://localhost:8080/api/orders/${orderId}`);
    order.value = response.data;

    // Khởi tạo form cập nhật với dữ liệu hiện tại
    updateForm.value.orderState = order.value.orderState;
    updateForm.value.paymentState = order.value.paymentState;

    // Fetch thông tin sản phẩm cho từng order detail
    await fetchProductDetails();

    console.log('Fetched order data:', order.value);
  } catch (error) {
    console.error('Error fetching order data:', error);
    alert('Không thể tải thông tin đơn hàng.');
  } finally {
    loadingOrder.value = false;
  }
}

// Fetch thông tin chi tiết sản phẩm
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
  if (isUpdating.value) return;

  isUpdating.value = true;

  try {
    const updateData = {
      orderId: order.value.orderId, // Bổ sung để phù hợp với backend nhận trong body
      orderState: updateForm.value.orderState,
      paymentState: updateForm.value.paymentState
    };

    await axios.put(`http://localhost:8080/api/orders/status`, updateData); // endpoint đúng với controller

    // Cập nhật dữ liệu local
    order.value.orderState = updateForm.value.orderState;
    order.value.paymentState = updateForm.value.paymentState;

    showUpdateOrderModal.value = false;
    alert(' Cập nhật trạng thái đơn hàng thành công!');
  } catch (error) {
    console.error(' Lỗi khi cập nhật trạng thái đơn hàng:', error);
    alert(' Có lỗi xảy ra khi cập nhật trạng thái đơn hàng.');
  } finally {
    isUpdating.value = false;
  }
}


// Utility functions
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
  return ['Chờ xác nhận', 'Đã xác nhận', 'Đang chuẩn bị', 'Đang giao'].includes(order.value.orderState);
}

function printOrder() {
  window.print();
}

function goBack() {
  router.back();
}

// Khởi tạo component
onMounted(() => {
  const orderId = route.params.orderId;

  if (orderId) {
    order.value.orderId = orderId;
    fetchOrderData(orderId);
  } else {
    console.error("Order ID is missing from route parameters.");
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

.table th {
  background-color: #f8f9fa;
  font-weight: 600;
}

.badge {
  font-size: 0.875em;
}

.modal {
  z-index: 1050;
}

@media print {
  /* Ẩn tất cả nội dung ban đầu */
  body * {
    visibility: hidden;
  }

  /* Chỉ hiển thị vùng cần in */
  #printArea,
  #printArea * {
    visibility: visible;
  }

  #printArea {
    position: absolute;
    left: 0;
    top: 0;
    width: 100%;
    padding: 20px;
    background: white;
  }

  /* Ẩn các phần không cần thiết */
  button,
  .btn,
  .card-header,
  .modal,
  .mt-4,
  .modal-backdrop {
    display: none !important;
  }

  /* Tùy chỉnh bảng hoặc text nếu cần */
  .table {
    font-size: 14px;
  }

  .text-primary {
    color: black !important;
  }
}

</style>
