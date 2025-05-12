<template>
  <div class="shapee-container">
    <!-- Header -->
    <div class="header">
      <h1>Danh sách đơn hàng</h1>
    </div>

    <!-- Order Status Tabs -->
    <div class="status-tabs">
      <div
        v-for="status in allStatuses"
        :key="status.key"
        class="status-tab"
        :class="{ active: activeStatus === status.key }"
        @click="changeStatus(status.key)"
      >
        {{ status.label }}
      </div>
    </div>

    <!-- Orders List -->
    <div class="orders-list">
      <div v-if="loading" class="loading">Loading...</div>
      <div v-else-if="orders.length === 0" class="no-orders">Chưa có đơn hàng</div>
      <div v-else>
        <div v-for="order in orders" :key="order.orderId" class="order-item">
          <div class="order-header">
            <span class="order-id">Đơn hàng #{{ order.orderId }}</span>
            <span class="order-status">Trạng thái: {{ order.orderState }}</span>
          </div>
          <div class="order-details">
            <div class="order-customer">Khách hàng: {{ order.userId }}</div>
            <div class="order-date">
              Ngày đặt: {{ new Date(order.orderDate).toLocaleDateString() }}
            </div>
            <div class="order-total">Tổng cộng: {{ order.totalPrice | currency }}</div>
            <div class="order-note">Ghi chú: {{ order.note }}</div>
          </div>
          <div class="order-products">
            <div v-for="item in order.orderDetails" :key="item.productId" class="order-product">
              <div class="product-name">
                Sản phẩm #{{ item.productId }} (Màu: {{ item.color }}, Kích cỡ: {{ item.size }})
              </div>
              <div class="product-quantity">Số lượng: {{ item.quantity }}</div>
              <div class="product-total">Giá: {{ item.total_price | currency }}</div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      allStatuses: [
        { key: '', label: 'Tất cả' },
        { key: 'Chờ xác nhận', label: 'Chờ xác nhận' },
        { key: 'shipping', label: 'Vận chuyển' },
        { key: 'Đang giao', label: 'Chờ giao hàng' },
        { key: 'Đã giao', label: 'Hoàn thành' },
        { key: 'cancelled', label: 'Đã hủy' },
        { key: 'return_refund', label: 'Trả hàng/Hoàn tiền' },
      ],
      activeStatus: '',
      orders: [],
      loading: false,
      userId: 2,
    }
  },
  mounted() {
    this.fetchOrdersByStatus()
  },
  methods: {
    changeStatus(status) {
      this.activeStatus = status
      this.fetchOrdersByStatus()
    },
    async fetchOrdersByStatus() {
      this.loading = true
      try {
        const url = `http://localhost:8080/api/orders/group-by-status?userId=${this.userId}${
          this.activeStatus ? `&orderState=${this.activeStatus}` : ''
        }`
        const response = await fetch(url)
        if (!response.ok) {
          throw new Error('Failed to fetch orders')
        }
        const data = await response.json()
        console.log('Fetched orders:', data)
        if(this.activeStatus == "")
        this.orders = Object.values(data).flat()
         else
          this.orders = data[this.activeStatus] || [] // Adjusting data structure based on the response format
      } catch (error) {
        console.error('Error fetching orders:', error)
        this.orders = [] // If there's an error, set orders to empty
      } finally {
        this.loading = false
      }
    },
  },
  filters: {
    currency(value) {
      if (!value) return ''
      return value.toLocaleString('vi-VN', { style: 'currency', currency: 'VND' })
    },
  },
}
</script>

<style scoped>
.shapee-container {
  font-family: Arial, sans-serif;
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
}

.header {
  text-align: center;
  margin-bottom: 20px;
}

.status-tabs {
  display: flex;
  margin: 20px 0;
  gap: 10px;
}

.status-tab {
  padding: 8px 15px;
  background-color: #f1f2f6;
  border-radius: 5px;
  cursor: pointer;
}

.status-tab.active {
  background-color: #ff4757;
  color: white;
}

.orders-list {
  margin-top: 20px;
}

.order-item {
  padding: 15px;
  border: 1px solid #eee;
  border-radius: 5px;
  margin-bottom: 10px;
}

.order-header {
  display: flex;
  justify-content: space-between;
  margin-bottom: 10px;
  font-weight: bold;
}

.order-details {
  font-size: 14px;
  color: #555;
}

.order-customer,
.order-date,
.order-total,
.order-note {
  margin: 5px 0;
}

.order-products {
  margin-top: 20px;
}

.order-product {
  padding: 10px;
  background-color: #f9f9f9;
  border-radius: 5px;
  margin-bottom: 10px;
}

.product-name,
.product-quantity,
.product-total {
  font-size: 14px;
  color: #333;
}

.loading,
.no-orders {
  text-align: center;
  padding: 20px;
  color: #666;
}
</style>
