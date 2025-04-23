// Biến toàn cục cho phân trang
let currentOrderPage = 0;
const orderPageSize = 8;
let totalOrderPages = 1;

// DOM elements
const searchOrderKeyword = document.getElementById('searchOrderKeyword');
const searchOrderStatus = document.getElementById('searchOrderStatus');
const searchOrderButton = document.getElementById('searchOrderButton');
const refreshOrderList = document.getElementById('refreshOrderList');
const prevOrderPage = document.getElementById('prevOrderPage');
const nextOrderPage = document.getElementById('nextOrderPage');
const orderTableBody = document.getElementById('orderTableBody');
const orderPageInfo = document.getElementById('orderPageInfo');

// Khởi tạo event listeners
function initializeEventListeners() {
    prevOrderPage.addEventListener('click', () => {
        if (currentOrderPage > 0) {
            currentOrderPage--;
            loadOrders();
        }
    });

    nextOrderPage.addEventListener('click', () => {
        if (currentOrderPage < totalOrderPages - 1) {
            currentOrderPage++;
            loadOrders();
        }
    });

    searchOrderButton.addEventListener('click', () => {
        currentOrderPage = 0;
        loadOrders();
    });

    refreshOrderList.addEventListener('click', () => {
        currentOrderPage = 0;
        searchOrderKeyword.value = '';
        searchOrderStatus.value = '';
        loadOrders();
    });
}

// Hàm chính tải dữ liệu
async function loadOrders() {
    showLoading();
    const token = localStorage.getItem("authToken");
    try {
        const params = new URLSearchParams({
            page: currentOrderPage,
            size: orderPageSize,
            keyword: searchOrderKeyword.value,
            status: searchOrderStatus.value
        });

        const response = await fetch(`http://localhost:8080/api/orders/search?${params}`, {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });

        if (!response.ok) {
            const error = await parseError(response);
            throw error;
        }

        const data = await response.json();
        handleDataResponse(data);
    } catch (error) {
        handleLoadError(error);
    } finally {
        hideLoading();
    }
}

// Các hàm hỗ trợ
async function parseError(response) {
    try {
        const error = await response.json();
        return new Error(error.message || `HTTP error! status: ${response.status}`);
    } catch {
        return new Error(`HTTP error! status: ${response.status}`);
    }
}

function handleDataResponse(data) {
    totalOrderPages = data.totalPages || 1;
    updatePaginationControls();
    renderOrders(data.content || []);
}

function updatePaginationControls() {
    orderPageInfo.textContent = `${currentOrderPage + 1}/${totalOrderPages}`;
    prevOrderPage.disabled = currentOrderPage === 0;
    nextOrderPage.disabled = currentOrderPage >= totalOrderPages - 1;
}

function renderOrders(orders) {
    orderTableBody.innerHTML = orders.length > 0 
        ? orders.map(order => createOrderRow(order)).join('')
        : '<tr><td colspan="6" class="text-center">Không có đơn hàng nào</td></tr>';
}

function createOrderRow(order) {
    return `
        <tr>
            <td>${order.orderId}</td>
            <td>${order.customerId}</td>
            <td>${formatDate(order.createdAt)}</td>
            <td>${formatCurrency(order.totalAmount)}</td>
            <td><span class="status ${order.status}">${getOrderStatusText(order.status)}</span></td>
            <td>
                <button class="btn btn-edit" data-order-id="${order.id}" onclick="viewOrderDetail('${order.id}')">
                    <i class="fas fa-eye"></i>
                </button>
                <button class="btn btn-delete" data-order-id="${order.orderId}">
                    <i class="fas fa-trash"></i>
                </button>
            </td>
        </tr>
    `;
}

// Hàm xử lý lỗi
function handleLoadError(error) {
    console.error('Error:', error);
    orderTableBody.innerHTML = `
        <tr>
            <td colspan="6" class="text-center error-message">
                ${error.message || 'Lỗi khi tải dữ liệu'}
            </td>
        </tr>
    `;
}

// Hàm tiện ích
function showLoading() {
    orderTableBody.innerHTML = `
        <tr>
            <td colspan="6" class="text-center">
                <div class="spinner-border text-primary" role="status">
                    <span class="visually-hidden">Loading...</span>
                </div>
            </td>
        </tr>
    `;
}

function hideLoading() {
    // Giữ lại để xử lý animation nếu cần
}

function formatDate(dateString) {
    const date = new Date(dateString);
    return date.toLocaleDateString('vi-VN', {
        day: '2-digit',
        month: '2-digit',
        year: 'numeric'
    });
}

function formatCurrency(amount) {
    return new Intl.NumberFormat('vi-VN', {
        style: 'currency',
        currency: 'VND'
    }).format(amount);
}

function getOrderStatusText(status) {
    const statusMap = {
        'pending': 'Chờ xác nhận',
        'confirmed': 'Đã xác nhận',
        'shipping': 'Đang giao hàng',
        'completed': 'Đã hoàn thành',
        'cancelled': 'Đã hủy'
    };
    return statusMap[status] || status;
}

// Hàm xử lý đơn hàng
async function confirmDeleteOrder(orderId) {
    if (!confirm('Bạn có chắc chắn muốn xóa đơn hàng này?')) return;
    const token = localStorage.getItem("authToken");

    try {
        const response = await fetch(`http://localhost:8080/api/orders/${orderId}`, {
            method: 'DELETE',
            headers: {
                
                'Authorization': `Bearer ${token}`
            }
                }).then(response => {
                    if (response.ok) {
                        alert('đơn hàng đã được xóa thành công!');
                        loadOrders();
                    } else {
                        alert('Có lỗi xảy ra khi xóa đơn hàng');
                    }
                }).catch(error => {
                    console.error('Error:', error);
                    alert('Có lỗi xảy ra khi xóa đơn hàng');
                });
            } catch (error) {
                console.error('Error:', error);
                alert('Có lỗi xảy ra khi thực hiện thao tác xóa đơn hàng');
            }
        }


// Khởi động ứng dụng
document.addEventListener('DOMContentLoaded', () => {
    initializeEventListeners();
    loadOrders();
});