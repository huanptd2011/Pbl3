<template>
  <div class="order-history">
    <h1>Đơn Hàng Của Tôi</h1>

    <!-- Tab trạng thái đơn hàng -->
    <div class="status-tabs">
      <button
        v-for="tab in tabs"
        :key="tab.value"
        @click="activeTab = tab.value"
        :class="{ active: activeTab === tab.value }"
      >
        {{ tab.label }}
        <span class="badge">{{ getOrderCount(tab.value) }}</span>
      </button>
    </div>

    <!-- Nội dung đơn hàng -->
    <div v-if="loading" class="loading">
      <p>Đang tải đơn hàng...</p>
    </div>

    <div v-else-if="error" class="error">
      <p>{{ error }}</p>
      <button @click="fetchOrders">Thử lại</button>
    </div>

    <div v-else>
      <div v-if="filteredOrders.length === 0" class="empty-orders">
        <p>{{ getEmptyMessage(activeTab) }}</p>
        <router-link to="/products" class="shop-btn">Mua sắm ngay</router-link>
      </div>

      <div v-else class="orders-list">
        <div v-for="order in filteredOrders" :key="order.orderId" class="order-card">
          <!-- Phần header đơn hàng -->
          <div class="order-header">
            <div class="order-info">
              <p class="order-date"><strong>Ngày đặt: {{ formatDate(order.orderDate) }}</strong></p>
            </div>
            <p class="order-status-text" :class="getStatusClass(order.orderState)"><strong>
                {{ order.orderState }}
              </strong></p>
          </div>

          <!-- Phần thân đơn hàng -->
          <div class="order-body">
            <div class="shipping-info">
              <p><strong>Địa chỉ giao hàng:</strong> {{ order.address }}</p>
              <p><strong>SĐT nhận hàng:</strong> {{ order.phone }}</p>
              <p><strong>Sản phẩm:</strong></p>
              <p v-for="(item, index) in order.orderDetails" :key="index" class="product-item" >
                <span>{{ '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;' + item.productName }} - Số lượng: {{ item.quantity }}</span>
              </p>
              <div class="summary-row total">
                <span>Tổng cộng: {{ formatPrice(order.totalPrice) }}</span>
              </div>
            </div>
          </div>

          <!-- Phần footer đơn hàng -->
          <div class="order-footer">
            <button @click="viewOrderDetail(order.orderId)" class="detail-btn">Xem chi tiết</button>
            <button
              v-if="order.orderState === 'Chờ xác nhận'"
              @click="cancelOrder(order.orderId)"
              class="cancel-btn"
            >
              Hủy đơn hàng
            </button>
            <button
              v-if="order.orderState === 'Đang giao'"
              @click="confirmReceived(order.orderId)"
              class="confirm-btn"
            >
              Đã nhận hàng
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>


  <div v-if="showDetailModal" class="modal-overlay">
      <div class="modal-content">
        <button class="close-button" @click="closeModal">×</button>
        <OrderDetail :order="selectedOrder" v-if="selectedOrder" />
        <div v-else class="loading">Loading order details...</div>
      </div>
  </div>

  <!-- Nút quay lại đầu trang -->
    <button 
    v-show="showBackToTop" 
    @click="scrollToTop"
    class="back-to-top-btn rounded-circle shadow"
    aria-label="Quay lại đầu trang">
    <i class="fa-solid fa-arrow-up"></i>
  </button>
</template>

<script setup>
import OrderDetail from '../components/orderDetail.vue'
import { ref, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import axios from 'axios'
import { useUserStore } from '@/stores/user'

const userStore = useUserStore()

const router = useRouter()

// Modal state
const showDetailModal = ref(false)
const selectedOrder = ref(null)
const detailLoading = ref(false)

const cancel = 'cancel'
const complete = 'complete'

// Danh sách các tab trạng thái
const tabs = [
  { label: 'Tất cả', value: 'all' },
  { label: 'Chờ xác nhận', value: 'Chờ xác nhận' },
  { label: 'Đang giao', value: 'Đang giao' },
  { label: 'Đã nhận hàng', value: 'Đã giao' },
  { label: 'Đã hủy', value: 'Đã hủy' }
]

const activeTab = ref('all')
const orders = ref([])
const loading = ref(false)
const error = ref(null)

// Lọc đơn hàng theo tab hiện tại
const filteredOrders = computed(() => {
  if (activeTab.value === 'all') return orders.value
  return orders.value.filter(order => order.orderState === activeTab.value)
})

// Đếm số đơn hàng theo trạng thái
const getOrderCount = (status) => {
  if (status === 'all') return orders.value.length
  return orders.value.filter(order => order.orderState === status).length
}

// Thông báo khi không có đơn hàng
const getEmptyMessage = (tab) => {
  switch (tab) {
    case 'Chờ xác nhận': return 'Bạn không có đơn hàng nào chờ xác nhận'
    case 'Đang giao': return 'Bạn không có đơn hàng nào đang giao'
    case 'Đã nhận hàng': return 'Bạn chưa có đơn hàng nào đã nhận'
    case 'Đã hủy': return 'Bạn chưa hủy đơn hàng nào'
    default: return 'Bạn chưa có đơn hàng nào'
  }
}

// Format ngày tháng
const formatDate = (dateString) => {
  const date = new Date(dateString)
  return date.toLocaleDateString('vi-VN', {
    day: '2-digit',
    month: '2-digit',
    year: 'numeric',
    hour: '2-digit',
    minute: '2-digit'
  })
}

// Format giá tiền
const formatPrice = (price) => {
  return new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(price)
}

// Class CSS cho trạng thái
const getStatusClass = (status) => {
  return {
    'Chờ xác nhận': 'status-pending',
    'Đang giao': 'status-shipping',
    'Đã nhận hàng': 'status-delivered',
    'Đã hủy': 'status-cancelled'
  }[status] || ''
}

// Hàm hủy đơn hàng
const cancelOrder = async (orderId) => {
  if (confirm('Bạn có chắc chắn muốn hủy đơn hàng này?')) {
    try {
      await axios.put(`http://localhost:8080/api/orders/${orderId}/${cancel}`)
      const orderIndex = orders.value.findIndex(order => order.orderId === orderId)
      if (orderIndex !== -1) {
        orders.value[orderIndex].orderState = 'Đã hủy'
      }
    } catch (err) {
      alert('Có lỗi xảy ra khi hủy đơn hàng')
      console.error('Error cancelling order:', err)
    }
  }
}

// Hàm xác nhận đã nhận hàng
const confirmReceived = async (orderId) => {
  if (confirm('Bạn đã nhận được hàng?')) {
    try {
      await axios.put(`http://localhost:8080/api/orders/${orderId}/${complete}`)
      const orderIndex = orders.value.findIndex(order => order.orderId === orderId)
      if (orderIndex !== -1) {
        orders.value[orderIndex].orderState = 'Đã giao'
      }
    } catch (err) {
      alert('Có lỗi xảy ra khi xác nhận đơn hàng')
      console.error('Error completing order:', err)
    }
  }
}

// Xem chi tiết đơn hàng
// View order details in modal
const viewOrderDetail = async (orderId) => {
  showDetailModal.value = true
  detailLoading.value = true
  try {
    const response = await axios.get(`http://localhost:8080/api/orders/${orderId}`)
    selectedOrder.value = response.data
  } catch (err) {
    console.error('Error fetching order details:', err)
    alert('Không thể tải chi tiết đơn hàng')
  } finally {
    detailLoading.value = false
  }
}

const closeModal = () => {
  showDetailModal.value = false
  selectedOrder.value = null
}

// Lấy danh sách đơn hàng từ API
const fetchOrders = async () => {
  loading.value = true
  error.value = null

  try {
    const response = await axios.get(`http://localhost:8080/api/orders/user/${userStore.user.userId}`)
    orders.value = response.data
    console.log('Orders fetched:', orders.value)
  } catch (err) {
    error.value = 'Đã xảy ra lỗi khi tải đơn hàng'
    console.error('Error fetching orders:', err)
  } finally {
    loading.value = false
  }
}

// Scroll to top functionality
const showBackToTop = ref(false)

// Hàm xử lý scroll
const handleScroll = () => {
  showBackToTop.value = window.scrollY > 300
}

// Hàm cuộn lên đầu trang
const scrollToTop = () => {
  window.scrollTo({
    top: 0,
    behavior: 'smooth'
  })
}

onMounted(() => {
  window.addEventListener('scroll', handleScroll)
  fetchOrders()
})
</script>

<style scoped>
.order-history {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
}

.status-tabs {
  display: flex;
  gap: 10px;
  margin-bottom: 20px;
  overflow-x: auto;
  padding-bottom: 10px;
}

.status-tabs button {
  padding: 10px 15px;
  border: 1px solid #ddd;
  background: white;
  border-radius: 4px;
  cursor: pointer;
  white-space: nowrap;
  position: relative;
}

.status-tabs button.active {
  background: var(--reset-button);
  color: white;
  border-color: #1d3557;
}

.badge {
  background: #e63946;
  color: white;
  border-radius: 50%;
  padding: 3px 6px;
  font-size: 0.8rem;
  margin-left: 5px;
}

.order-card {
  border: 1px solid #eee;
  border-radius: 8px;
  margin-bottom: 20px;
  overflow: hidden;
}

.order-header {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  padding: 15px;
  background: #f8f9fa;
  border-bottom: 1px solid #eee;
  flex-wrap: wrap;
  gap: 10px;
}

.order-info {
  flex: 1;
  min-width: 200px;
}

.order-info h3 {
  margin: 0 0 5px;
  font-size: 1.1rem;
}

.order-date {
  margin: 5px 0;
  color: #666;
  font-size: 0.9rem;
}

.order-status-text {
  display: inline-block;
  padding: 3px 8px;
  border-radius: 4px;
  font-size: 0.9rem;
  margin-top: 5px;
}

.payment-method {
  font-size: 0.9rem;
  color: #555;
}

.order-body {
  padding: 15px;
}

.shipping-info {

}

.shipping-info p {
  margin: 5px 0;
}

.products-list {
  margin-bottom: 15px;
}

.product-item {
  display: flex;
  gap: 15px;
}

.product-item:last-child {
  border-bottom: none;
  margin-bottom: 0;
  padding-bottom: 0;
}

.product-image-placeholder {
  width: 80px;
  height: 80px;
  background: #f0f0f0;
  border-radius: 4px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #999;
}

.product-details {
  flex: 1;
}

.product-details h4 {
  margin: 0 0 5px;
  font-size: 1rem;
}

.product-attributes {
  display: flex;
  gap: 10px;
  margin: 5px 0;
  font-size: 0.9rem;
  color: #666;
  flex-wrap: wrap;
}

.price {
  color: #e63946;
  font-weight: bold;
}

.order-summary {
  margin-top: 20px;
  padding-top: 15px;
}

.summary-row {
  display: flex;
  justify-content: space-between;
}

.summary-row.total {
  font-weight: bold;
  font-size: 1.1rem;
}

.order-footer {
  padding: 15px;
  background: #f8f9fa;
  display: flex;
  gap: 10px;
  flex-wrap: wrap;
}

.order-footer button {
  padding: 8px 15px;
  border-radius: 4px;
  cursor: pointer;
  font-size: 0.9rem;
}

.detail-btn {
  background: var(--submit-button);
  color: white;
  border: none;
}

.cancel-btn {
  background: white;
  color: #e63946;
  border: 1px solid #e63946;
}

.confirm-btn {
  background: #42b983;
  color: white;
  border: none;
}

.status-pending {
  background: #fff3cd;
  color: #856404;
}

.status-shipping {
  background: #cce5ff;
  color: #004085;
}

.status-delivered {
  background: #d4edda;
  color: #155724;
}

.status-cancelled {
  background: #f8d7da;
  color: #721c24;
}

.loading, .error, .empty-orders {
  text-align: center;
  padding: 40px;
}

.error {
  color: #e63946;
}

.error button {
  margin-top: 10px;
  padding: 8px 15px;
  background: #e63946;
  color: white;
  border: none;
  border-radius: 4px;
}

.empty-orders p {
  font-size: 1.1rem;
  color: #666;
}

.shop-btn {
  display: inline-block;
  margin-top: 15px;
  padding: 10px 20px;
  background: var(--submit-button);
  color: white;
  text-decoration: none;
  border-radius: 4px;
}

@media (max-width: 768px) {
  .order-header {
    flex-direction: column;
  }

  .product-item {
    flex-direction: column;
  }

  .product-image-placeholder {
    width: 100%;
    height: 120px;
  }
}

/* Modal styles */
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.modal-content {
  position: relative;
  background-color: white;
  border-radius: 8px;
  padding: 30px;
  width: 90%;
  max-width: 800px;
  max-height: 90vh;
  overflow-y: auto;
}

.close-button {
  position: absolute;
  top: 10px;
  right: 10px;
  background: none;
  border: none;
  font-size: 24px;
  cursor: pointer;
  color: #666;
}

.close-button:hover {
  color: #333;
}

.loading {
  padding: 20px;
  text-align: center;
  font-style: italic;
  color: #666;
}

.fa-spinner {
  margin-right: 8px;
}

/* Style cho nút back to top */
.back-to-top-btn {
  position: fixed;
  bottom: 30px;
  right: 30px;
  width: 50px;
  height: 50px;
  background-color: #4e73df;
  color: white;
  border: none;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 1.2rem;
  cursor: pointer;
  z-index: 99;
  opacity: 0.8;
  transition: all 0.3s ease;
}

.back-to-top-btn:hover {
  opacity: 1;
  background-color: #2e59d9;
  transform: translateY(-3px);
}

/* Hiệu ứng khi xuất hiện */
.back-to-top-btn {
  animation: fadeIn 0.3s;
}

@keyframes fadeIn {
  from { opacity: 0; transform: translateY(20px); }
  to { opacity: 0.8; transform: translateY(0); }
}
</style>
