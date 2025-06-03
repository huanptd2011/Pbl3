<template>
    <div class="order-management-container">
        <div class="card">
            <div class="card-header d-flex justify-content-between align-items-center">
                <span>Quản lý Đơn hàng</span>
                <div class="d-flex align-items-center">
                    <select v-model="filterStatus" class="form-select form-select-sm me-2 calenda" style="width: 150px;">
                        <option value="">Tất cả Trạng thái</option>
                        <option value="Chờ xác nhận">Chờ xác nhận</option>
                        <option value="Đã xác nhận">Đã xác nhận</option>
                        <option value="Đang giao">Đang giao</option>
                        <option value="Đã giao">Đã giao</option>
                        <option value="Đã hủy">Đã hủy</option>
                    </select>

                    <select v-model="filterPaymentStatus" class="form-select form-select-sm me-2 calenda" style="width: 150px;">
                        <option value="">Tất cả Thanh toán</option>
                        <option value="Đã thanh toán">Đã thanh toán</option>
                        <option value="Chưa thanh toán">Chưa thanh toán</option>
                        <option value="Đã hoàn tiền">Đã hoàn tiền</option>
                    </select>

                    <input type="text" class="form-control form-control-sm me-2 calenda"
                        placeholder="Tìm kiếm đơn hàng..." style="width: 200px;" v-model="searchKeyword">

                    <button class="btn btn-success btn-sm bg-success bor-success me-2" @click="exportOrders">
                        <i class="fas fa-download me-1"></i>
                        Xuất Excel
                    </button>
                </div>
            </div>
            <div class="card-body">
                <div v-if="loadingOrders" class="loading-overlay">
                    <div class="spinner-border text-primary" role="status">
                        <span class="visually-hidden">Đang tải...</span>
                    </div>
                </div>
                <table class="table table-striped table-bordered custom-orders-table">
                    <thead>
                        <tr>
                            <th>Mã đơn hàng</th>
                            <th>Khách hàng</th>
                            <th>Ngày đặt</th>
                            <th>Tổng tiền</th>
                            <th>Trạng thái</th>
                            <th>Thanh toán</th>
                            <th>Thao tác</th>
                        </tr>
                    </thead>
                    <tbody>
                        <template v-if="paginatedOrders.length > 0">
                            <tr v-for="order in paginatedOrders" :key="order.orderId">
                                <td>
                                    <span class="order-code">#{{ order.orderId }}</span>
                                </td>
                                <td>
                                    <div class="customer-info">
                                        <div class="customer-name">{{ order.username }}</div>
                                        <div class="customer-phone" v-if="order.phone">{{ order.phone }}</div>
                                    </div>
                                </td>
                                <td>{{ formatDate(order.orderDate) }}</td>
                                <td>
                                    <span class="total-amount">{{ formatCurrency(order.totalPrice) }}</span>
                                </td>
                                <td>
                                    <span :class="['status-badge', getStatusClass(order.orderState)]">
                                        {{ order.orderState }}
                                    </span>
                                </td>
                                <td>
                                    <span :class="['payment-badge', getPaymentStatusClass(order.paymentState)]">
                                        {{ order.paymentState || 'Chưa xác định' }}
                                    </span>
                                </td>
                                <td>
                                    <i class="fas fa-edit text-warning me-2 icon"
                                        @click="viewOrderDetail(order.orderId)"
                                        title="Cập nhật trạng thái"></i>
                                </td>
                            </tr>
                        </template>
                        <tr v-else-if="paginatedOrders.length === 0 && !loadingOrders">
                            <td colspan="7" class="text-center cl-note">Không tìm thấy đơn hàng nào.</td>
                        </tr>

                        <template v-else>
                            <tr v-for="n in pageSize" :key="'placeholder-' + n">
                                <td>...</td>
                                <td>...</td>
                                <td>...</td>
                                <td>...</td>
                                <td>...</td>
                                <td>...</td>
                                <td>...</td>
                            </tr>
                        </template>
                    </tbody>
                </table>

                <nav aria-label="Page navigation mt-0">
                    <ul class="pagination justify-content-center">
                        <li class="page-item" :class="{ 'disabled': currentPage === 1 }">
                            <a class="page-link" href="#" @click.prevent="changePage(currentPage - 1)">Previous</a>
                        </li>
                        <li class="page-item" v-for="page in totalPages" :key="page"
                            :class="{ 'active': page === currentPage }">
                            <a class="page-link" href="#" @click.prevent="changePage(page)">{{ page }}</a>
                        </li>
                        <li class="page-item" :class="{ 'disabled': currentPage === totalPages }">
                            <a class="page-link" href="#" @click.prevent="changePage(currentPage + 1)">Next</a>
                        </li>
                    </ul>
                </nav>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref, onMounted, watch, computed } from 'vue';
import axios from 'axios';
import { useRouter } from 'vue-router';

const allOrders = ref([]);
const filterStatus = ref('');
const filterPaymentStatus = ref('');
const loadingOrders = ref(true);
const searchKeyword = ref('');
const currentPage = ref(1);
const pageSize = 6; // Số lượng đơn hàng trên mỗi trang

const router = useRouter();

// Fetch danh sách đơn hàng
async function fetchOrders() {
    loadingOrders.value = true;
    try {
        const response = await axios.get('http://localhost:8080/api/orders');
        allOrders.value = response.data;
        console.log('Fetched orders:', allOrders.value);
    } catch (error) {
        console.error('Error fetching orders:', error);
        allOrders.value = [];
    } finally {
        loadingOrders.value = false;
    }
}

// Computed để lọc đơn hàng
const filteredOrders = computed(() => {
    let filtered = allOrders.value;

    // Lọc theo trạng thái đơn hàng
    if (filterStatus.value) {
        filtered = filtered.filter(order => order.orderState === filterStatus.value);
    }

    // Lọc theo trạng thái thanh toán
    if (filterPaymentStatus.value) {
        filtered = filtered.filter(order => order.paymentState === filterPaymentStatus.value);
    }

    // Tìm kiếm theo mã đơn hàng hoặc tên khách hàng
    if (searchKeyword.value) {
        const keyword = searchKeyword.value.toLowerCase();
        filtered = filtered.filter(order =>
            order.username?.toLowerCase().includes(keyword) ||
            order.phone?.toString().toLowerCase().includes(keyword)
        );
    }
    return filtered;
});

// Computed để phân trang
const paginatedOrders = computed(() => {
    const startIndex = (currentPage.value - 1) * pageSize;
    return filteredOrders.value.slice(startIndex, startIndex + pageSize);
});

const totalPages = computed(() => {
    return Math.ceil(filteredOrders.value.length / pageSize);
});



// Hàm chuyển trang
function changePage(page) {
    if (page >= 1 && page <= totalPages.value) {
        currentPage.value = page;
    }
}

// Hàm định dạng tiền tệ
function formatCurrency(value) {
    if (value == null) return '';
    return value.toLocaleString('vi-VN', { style: 'currency', currency: 'VND' });
}

// Hàm định dạng ngày
function formatDate(dateString) {
    if (!dateString) return '';
    const date = new Date(dateString);
    return date.toLocaleDateString('vi-VN', {
        day: '2-digit',
        month: '2-digit',
        year: 'numeric',
        hour: '2-digit',
        minute: '2-digit'
    });
}

function getStatusClass(status) {
    const statusClasses = {
        'Chờ xác nhận': 'status-pending',
        'Đã xác nhận': 'status-confirmed',
        'Đang giao': 'status-shipping',
        'Đã giao': 'status-delivered',
        'Đã hủy': 'status-cancelled'
    };
    return statusClasses[status] || 'status-default';
}

 function   getPaymentStatusClass(paymentStatus) {
            const paymentClasses = {
                'Đã thanh toán': 'payment-paid',
                'Chưa thanh toán': 'payment-unpaid',
                'Đã hoàn tiền': 'payment-refunded'
            };
            return paymentClasses[paymentStatus] || 'payment-default';
        }


// Hàm xem chi tiết đơn hàng
function viewOrderDetail(orderId) {
    router.push(`/admin/order/update/${orderId}`);
}


// Hàm xuất Excel
function exportOrders() {
    // Logic xuất Excel
    window.open('/admin/order/export', '_blank');
}

// Fetch data khi component được mount
onMounted(() => {
    fetchOrders();
});

// Watch để reset trang khi filter thay đổi
watch([searchKeyword, filterStatus, filterPaymentStatus], () => {
    console.log("Filter/Search criteria changed, resetting page to 1.");
    currentPage.value = 1;
});
</script>

<style scoped>
/* Sử dụng lại các style chung cho card */
.card {
    background-color: #0b1739;
    border-radius: 12px;
    box-shadow: #0105114d 0px 8px 28px 0px;
    color: #fff;
    display: flex;
    flex-direction: column;
    gap: 15px;
    line-height: 24px;
    padding: 18px 18px 18px 20px;
}

.card-header {
    background-color: #0b1739;
    border-bottom: 1px solid #1e293b;
}

.card-body {
    position: relative;
    min-height: 600px;
}

.custom-orders-table tbody tr {
    border-color: #1e293b;
    height: 70px;
    vertical-align: middle;
}

/* Style cho mã đơn hàng */
.order-code {
    font-weight: 600;
    color: #cb3cff;
}

/* Style cho thông tin khách hàng */
.customer-info {
    display: flex;
    flex-direction: column;
}

.customer-name {
    font-weight: 500;
    color: #fff;
}

.customer-phone {
    font-size: 0.85em;
    color: #aeb9e1;
}

/* Style cho tổng tiền */
.total-amount {
    font-weight: 600;
    color: #10b981;
}
:root {
    --color-pending: #f59e0b;
    --color-confirmed: #3b82f6;
    --color-shipping: #8b5cf6;
    --color-delivered: #10b981;
    --color-cancelled: #ef4444;
    --color-default: #6b7280; /* gray-500 */
}

/* Status Badges cho trạng thái đơn hàng */
.status-badge {
    display: inline-block;
    padding: 0.4em 0.6em;
    font-size: 0.75em;
    font-weight: 500;
    line-height: 1;
    text-align: center;
    white-space: nowrap;
    vertical-align: baseline;
    border-radius: 4px;
}
.status-badge.status-pending {
    background-color: #f59e0b2e;
    color: #f59e0b;
    border: 0.2px solid #f59e0b;
}

.status-badge.status-confirmed {
    background-color: #3b82f62e;
    color: #3b82f6;
    border: 0.2px solid #3b82f6;
}

.status-badge.status-shipping {
    background-color: #8b5cf62e;
    color: #8b5cf6;
    border: 0.2px solid #8b5cf6;
}

.status-badge.status-delivered {
    background-color: #10b9812e;
    color: #139f81;
    border: 0.2px solid #139f81;
}

.status-badge.status-cancelled {
    background-color: #e2232330;
    color: #ef4444;
    border: 0.2px solid #ef4444;
}

/* Payment Status Badges */
.payment-badge {
    display: inline-block;
    padding: 0.4em 0.6em;
    font-size: 0.75em;
    font-weight: 500;
    line-height: 1;
    text-align: center;
    white-space: nowrap;
    vertical-align: baseline;
    border-radius: 4px;
}

.payment-badge.payment-paid {
    background-color: #10b9812e;
    color: #139f81;
    border: 0.2px solid #139f81;
}

.payment-badge.payment-unpaid {
    background-color: #e2232330;
    color: #ef4444;
    border: 0.2px solid #ef4444;
}

.payment-badge.payment-refunded {
    background-color: #6b72802e;
    color: #6b7280;
    border: 0.2px solid #6b7280;
}

/* Action Icons */
.custom-orders-table td i {
    cursor: pointer;
    font-size: 1.1em;
    padding: 0.3em;
    border-radius: 4px;
    transition: all 0.2s ease;
}

.custom-orders-table td i:hover {
    background-color: rgba(255, 255, 255, 0.1);
}

/* Phân trang */
.pagination {
    display: flex;
    justify-content: center;
    position: absolute;
    bottom: 0;
    left: 40%;
}

.pagination .page-item .page-link {
    background-color: #161e39;
    border-color: #1e293b;
    color: #aeb9e1;
}

.pagination .page-item.active .page-link {
    background-color: rgba(203, 60, 255);
    color: #fff;
}

.pagination .page-item.disabled .page-link {
    color: #555;
    pointer-events: none;
    background-color: #161e39;
    border-color: #1e293b;
}

.pagination .page-item .page-link:hover:not(.disabled) {
    background-color: rgba(203, 60, 255);
}

.pagination .page-item .page-link:focus {
    outline: none;
}

.loading-overlay {
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background-color: rgba(11, 23, 57, 0.8);
    display: flex;
    justify-content: center;
    align-items: center;
    z-index: 10;
    opacity: 1;
    transition: opacity 0.3s ease-in-out;
}

input::placeholder {
    color: #aeb9e1;
}

.cl-note {
    color: #aeb9e1;
    font-style: italic;
}

.icon {
    transition: transform 0.2s ease;
}

.icon:hover {
    transform: scale(1.1);
}
input:focus{
    border-color: #aeb9e1;
    box-shadow: 0 0 0 0.2rem rgba(203, 60, 255, 0.25);
}

</style>
