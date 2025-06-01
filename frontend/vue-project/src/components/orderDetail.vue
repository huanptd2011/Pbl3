<script setup>
const props = defineProps({
  order: {
    type: Object,
    required: true,
    validator: (value) => {
      return (
        value.orderId &&
        value.orderDate &&
        value.address &&
        value.phone &&
        value.orderDetails &&
        value.totalPrice !== undefined
      );
    }
  }
});

const formatDate = (dateString) => {
  if (!dateString) return '';
  const date = new Date(dateString);
  return date.toLocaleDateString('vi-VN', {
    day: '2-digit',
    month: '2-digit',
    year: 'numeric',
    hour: '2-digit',
    minute: '2-digit'
  });
};

const formatPrice = (price) => {
  if (price === undefined || price === null) return '0 ₫';
  return new Intl.NumberFormat('vi-VN', { 
    style: 'currency', 
    currency: 'VND',
    minimumFractionDigits: 0
  }).format(price);
};

const getStatusClass = (status) => {
  return {
    'Chờ xác nhận': 'status-pending',
    'Đang giao': 'status-shipping',
    'Đã giao': 'status-delivered',
    'Đã hủy': 'status-cancelled'
  }[status] || '';
};
</script>

<template>
  <div class="order-detail" v-if="order">
    <div class="order-header">
      <h2><strong>Chi tiết đơn hàng</strong></h2>
      <span :class="['order-status', getStatusClass(order.orderState)]">
        {{ order.orderState }}
      </span>
    </div>

    <div class="order-info">
      <div class="info-row">
        <span class="info-label">Ngày đặt:</span>
        <span class="info-value">{{ formatDate(order.orderDate) }}</span>
      </div>
      <div class="info-row">
        <span class="info-label">Địa chỉ:</span>
        <span class="info-value">{{ order.address }}</span>
      </div>
      <div class="info-row">
        <span class="info-label">Số điện thoại:</span>
        <span class="info-value">{{ order.phone }}</span>
      </div>
      <div class="info-row">
        <span class="info-label">Thanh toán:</span>
        <span class="info-value">{{ order.paymentMethod?.paymentMethodName || 'Không xác định' }}</span>
      </div>
      <div class="info-row">
        <span class="info-label">Ghi chú:</span>
        <span class="info-value">{{ order.note || 'Không có' }}</span>
      </div>
    </div>

    <div class="order-items">
      <h3><strong>Danh sách sản phẩm</strong></h3>
      <div v-for="(item, index) in order.orderDetails" :key="index" class="item">
        <div class="product-image-placeholder">
          <img :src="item.imageUrl" alt="Ảnh sản phẩm" />
        </div>
        <div class="product-details">
          <div class="item-row">
            <span class="item-label">Tên sản phẩm: &nbsp;&nbsp;&nbsp;</span>
            <span class="item-value">{{ item.productName }}</span>
          </div>
          <div class="item-row">
            <span class="item-label">Màu sắc: &nbsp;</span>
            <span class="item-value">{{ item.color + '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'}} </span>
            <span class="item-label">Size: &nbsp;</span>
            <span class="item-value">{{ item.size + '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'}} </span>
            <span class="item-label">Số lượng: &nbsp;</span>
            <span class="item-value">{{ item.quantity || 0 }}</span>
          </div>
          <div class="item-row">
            <span class="item-label">Đơn giá: &nbsp;</span>
            <span class="item-value price">{{ formatPrice(item.total_price) }}</span>
          </div>
          <div class="item-row">
            <span class="item-label">Thành tiền: &nbsp;</span>
            <span class="item-value price">{{ formatPrice(item.total_price * item.quantity) }}</span>
          </div>
        </div>
      </div>
    </div>

    <div class="order-summary">
      <div class="summary-row">
        <span class="summary-label">Tổng cộng:</span>
        <span class="summary-value">{{ formatPrice(order.totalPrice) }}</span>
      </div>
    </div>
  </div>
</template>

<style scoped>
.order-detail {
  font-family: Arial, sans-serif;
  padding: 20px;
  max-width: 800px;
  margin: 0 auto;
}

.order-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
  padding-bottom: 10px;
  border-bottom: 1px solid #eee;
}

.order-status {
  padding: 4px 8px;
  border-radius: 4px;
  font-weight: bold;
  text-transform: uppercase;
  font-size: 0.9em;
}

.status-pending {
  background-color: #FFF3CD;
  color: #856404;
}

.status-shipping {
  background-color: #D1ECF1;
  color: #0C5460;
}

.status-delivered {
  background-color: #D4EDDA;
  color: #155724;
}

.status-cancelled {
  background-color: #F8D7DA;
  color: #721C24;
}

.info-row {
  display: flex;
  margin-bottom: 10px;
}

.info-label {
  font-weight: bold;
  min-width: 120px;
}

.info-value {
  flex: 1;
}

.order-items {
  margin-top: 20px;
  border-top: 1px solid #eee;
  padding-top: 15px;
}

.item {
  display: flex;
  padding: 15px;
  margin-bottom: 15px;
  border: 1px solid #eee;
  border-radius: 4px;
}

.product-image-placeholder {
  width: 100px;
  height: 100px;
  flex-shrink: 0;
  margin-right: 16px;
  border: 1px solid #ddd;
  border-radius: 4px;
  overflow: hidden;
  display: flex;
  align-items: center;
  justify-content: center;
}

.product-image-placeholder img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}


.item-row {
  display: flex;
}

.item-label {
  font-weight: bold;
}

.item-value {
  flex: 1;
}

.price {
  color: #d32f2f;
  font-weight: bold;
}

.order-summary {
  margin-top: 20px;
  padding-top: 15px;
  border-top: 1px solid #eee;
  text-align: right;
}

.summary-row {
  font-size: 1.44em;
  font-weight: bold;
}

.summary-label {
  margin-right: 10px;
}

.summary-value {
  color: #d32f2f;
}
</style>