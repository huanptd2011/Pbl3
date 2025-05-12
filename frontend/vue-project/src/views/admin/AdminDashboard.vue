<template>
  <div class="admin-dashboard-content">

    <!-- <div class="alert alert-info mb-4" role="alert">
        Chào mừng trở lại, <strong v-if="userStore.user.username">{{ userStore.user.username }}</strong>! Đây là bảng điều khiển của bạn.
      </div> -->

    <div class="row mb-4">
      <div class="col-md-6 col-lg-3 mb-3">
        <div class="card  bg-item">
          <div class="">
            <div>
              <i class="fas fa-dollar-sign me-2 cl-main"></i>
              <span class="card-title">Tổng Doanh thu</span>
            </div>
            <p class="card-text fs-3" v-if="metrics">{{ formatCurrency(metrics.totalRevenue) }}</p>
            <p class="card-text fs-3" v-else>...</p>
            <!-- <p class="card-text"><small v-if="metrics && metrics.revenueChangePercent !== undefined">+{{
              metrics.revenueChangePercent }}% so với kỳ trước</small></p> -->
          </div>
        </div>
      </div>

      <div class="col-md-6 col-lg-3 mb-3">
        <div class="card  bg-item">
          <div class="">
            <div>
              <i class="fas fa-shopping-cart me-2 cl-main"></i>
              <span class="card-title">Tổng Đơn hàng</span>
            </div>
            
            <p class="card-text fs-3" v-if="metrics">{{ metrics.totalOrders }}</p>
            <p class="card-text fs-3" v-else>...</p>
            <!-- <p class="card-text"><small v-if="metrics && metrics.ordersChangePercent !== undefined">+{{
              metrics.ordersChangePercent }}% so với kỳ trước</small></p> -->
          </div>
        </div>
      </div>

      <div class="col-md-6 col-lg-3 mb-3">
        <div class="card  bg-item">
          <div class="">
            <div>
              <i class="fas fa-users me-2 cl-main"></i>
              <span class="card-title">Tổng Người dùng</span>
            </div>
            <p class="card-text fs-3" v-if="metrics">{{ metrics.totalUsers }}</p>
            <p class="card-text fs-3" v-else>...</p>
            <!-- <p class="card-text"><small v-if="metrics && metrics.usersChangePercent !== undefined">+{{
              metrics.usersChangePercent }}% so với kỳ trước</small></p> -->
          </div>
        </div>
      </div>

      <div class="col-md-6 col-lg-3 mb-3">
        <div class="card  bg-item">
          <div class="">
            <div>
              <i class="fas fa-clock me-2 cl-main"></i>
              <span class="card-title">Đơn hàng Đang chờ</span>
            </div>
            <p class="card-text fs-3" v-if="metrics">{{ metrics.pendingOrders }}</p>
            <p class="card-text fs-3" v-else>...</p>
            <!-- <p class="card-text"></p> -->
          </div>
        </div>
      </div>
    </div>

    <div class="row mb-4">
      <div class="col-md-12 col-lg-8 mb-4">
        <div class="card">
          <div class="card-header">
            Biểu đồ Doanh thu (Ví dụ: Biểu đồ đường)
          </div>
          <div class="card-body">
            <div v-if="!salesChartData"
              style="height: 300px; background-color: #f8f9fa; border: 1px dashed #ccc; display: flex; align-items: center; justify-content: center;">
              <p class="text-muted">Đang tải dữ liệu biểu đồ doanh thu...</p>
            </div>
            <canvas v-else id="salesChart" ref="salesChartCanvas" style="height: 300px;"></canvas>
          </div>
        </div>
      </div>

      <div class="col-md-12 col-lg-4 mb-4">
        <div class="card">
          <div class="card-header">
            Biểu đồ Đơn hàng (Ví dụ: Biểu đồ cột/tròn)
          </div>
          <div class="card-body">
            <div v-if="!ordersChartData"
              style="height: 300px; background-color: #f8f9fa; border: 1px dashed #ccc; display: flex; align-items: center; justify-content: center;">
              <p class="text-muted">Đang tải dữ liệu biểu đồ đơn hàng...</p>
            </div>
            <canvas v-else id="ordersChart" ref="ordersChartCanvas" style="height: 300px;"></canvas>
          </div>
        </div>
      </div>
    </div>

    <div class="row">
      <div class="col-md-12">
        <div class="card">
          <div class="card-header">
            Đơn hàng Gần đây
          </div>
          <div class="card-body">
            <div v-if="loadingRecentOrders" class="text-center">Đang tải đơn hàng...</div>
            <div v-else-if="recentOrders.length === 0" class="text-center">Không có đơn hàng gần đây.</div>
            <div v-else>
              <table class="table table-striped table-bordered">
                <thead>
                  <tr>
                    <th>Mã ĐH</th>
                    <th>Khách hàng</th>
                    <th>Tổng tiền</th>
                    <th>Trạng thái</th>
                    <th>Ngày đặt</th>
                  </tr>
                </thead>
                <tbody>
                  <tr v-for="order in recentOrders" :key="order.orderId">
                    <td>{{ order.orderId }}</td>
                    <td>{{ order.username }}</td>
                    <td>{{ formatCurrency(order.totalPrice) }}</td>
                    <td>{{ order.orderState }}</td>
                    <td>{{ formatDate(order.orderDate) }}</td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </div>
    </div>


  </div>
</template>

<script setup>
import { ref, onMounted, nextTick } from 'vue';
import axios from 'axios';
import { useUserStore } from '@/stores/user';
// Import thư viện biểu đồ tại đây (ví dụ với Chart.js)
import Chart from 'chart.js/auto'; // Cần cài đặt: npm install chart.js

const userStore = useUserStore();

// Định nghĩa các biến state để lưu dữ liệu từ API
const metrics = ref(null);
const salesChartData = ref(null);
const ordersChartData = ref(null);
const recentOrders = ref([]);
const loadingRecentOrders = ref(true); // Thêm trạng thái loading cho bảng đơn hàng

const salesChartCanvas = ref(null);
const ordersChartCanvas = ref(null);
// Hàm gọi API lấy số liệu tổng quan
async function fetchMetrics() {
  try {
    const response = await axios.get('http://localhost:8080/api/dashboard/summary');
    metrics.value = response.data;
  } catch (error) {
    console.error('Error fetching metrics:', error);
    // Xử lý lỗi (ví dụ: hiển thị thông báo)
  }
}

// Hàm gọi API lấy dữ liệu biểu đồ doanh thu
async function fetchSalesChartData() {
  try {
    // Yêu cầu backend cung cấp dữ liệu theo tháng
    const response = await axios.get('http://localhost:8080/api/dashboard/sales-over-time?period=weekly&startDate=2025-01-01&endDate=2025-12-31');
    salesChartData.value = response.data;
    // Sau khi có dữ liệu, gọi hàm vẽ biểu đồ
    nextTick(() => {
      setTimeout(() => {
        renderSalesChart(salesChartData.value);
      }, 50); // Timeout nhỏ
    });

  } catch (error) {
    console.error('Error fetching sales chart data:', error);
    // Xử lý lỗi
  }
}

// Hàm gọi API lấy dữ liệu biểu đồ đơn hàng
async function fetchOrdersChartData() {
  try {
    // Yêu cầu backend cung cấp dữ liệu theo trạng thái
    const response = await axios.get('http://localhost:8080/api/dashboard/orders-data?type=by-status');
    ordersChartData.value = response.data;

    nextTick(() => {
      setTimeout(() => {
        renderOrdersChart(ordersChartData.value);
      }, 500); // Timeout nhỏ
    });
    console.log(ordersChartData.value);
    // Sau khi có dữ liệu, gọi hàm vẽ biểu đồ

  } catch (error) {
    console.error('Error fetching orders chart data:', error);
    // Xử lý lỗi
  }
}

// Hàm gọi API lấy danh sách đơn hàng gần đây
async function fetchRecentOrders() {
  loadingRecentOrders.value = true; // Bắt đầu loading
  try {
    // Yêu cầu backend cung cấp 5 đơn hàng gần đây
    const response = await axios.get('http://localhost:8080/api/dashboard/recent-orders?limit=5');
    recentOrders.value = response.data;
  } catch (error) {
    console.error('Error fetching recent orders:', error);
    // Xử lý lỗi
  } finally {
    loadingRecentOrders.value = false; // Kết thúc loading
  }
}

// Hàm định dạng tiền tệ (ví dụ: thêm dấu phân cách hàng nghìn)
function formatCurrency(value) {
  if (value == null) return '';
  // Sử dụng toLocaleString để định dạng tiền tệ
  return value.toLocaleString('vi-VN', { style: 'currency', currency: 'VND' });
}

// Hàm định dạng ngày tháng
function formatDate(dateString) {
  if (!dateString) return '';
  // Chuyển đổi chuỗi ngày ISO 8601 sang đối tượng Date và định dạng
  const options = { year: 'numeric', month: 'numeric', day: 'numeric', hour: 'numeric', minute: 'numeric' };
  return new Date(dateString).toLocaleDateString('vi-VN', options);
}


// Lifecycle hook: Chạy khi component được mount vào DOM
onMounted(() => {
  console.log('Admin Dashboard View mounted');
  console.log('Current user role:', userStore.role);

  // Gọi các hàm fetch data khi component được mount
  fetchMetrics();
  fetchSalesChartData();
  fetchOrdersChartData();
  fetchRecentOrders();
});

// --- Các hàm vẽ biểu đồ (cần tích hợp thư viện biểu đồ và uncomment) ---

function renderSalesChart(data) {
  const ctx = salesChartCanvas.value;
  if (!ctx) return; // Kiểm tra canvas tồn tại
  // Hủy biểu đồ cũ nếu có để tránh lỗi vẽ lại
  if (ctx.chart) {
    ctx.chart.destroy();
  }

  const labels = data.map(item => item.timeLabel);
  const counts = data.map(item => item.revenue);

  console.log(labels, counts);
  ctx.chart = new Chart(ctx, { // Lưu instance biểu đồ vào canvas element
    type: 'line', // Loại biểu đồ
    data: {
      labels: labels, // Nhãn trục X (ví dụ: tháng)
      datasets: [{
        label: 'Doanh thu',
        data: counts, // Dữ liệu trục Y
        borderColor: 'rgba(75, 192, 192, 1)',
        tension: 0.1,
        fill: false // Không tô màu dưới đường
      }]
    },
    options: {
      responsive: true, // Biểu đồ responsive
      maintainAspectRatio: false, // Cho phép điều chỉnh tỷ lệ khung hình
      scales: {
        y: { beginAtZero: true } // Trục Y bắt đầu từ 0
      }
    }
  });
}



function renderOrdersChart(data) {

  console.log(data, "77777777777777");
  const ctx = ordersChartCanvas.value;

  if (!ctx) {
    console.error("Canvas element for ordersChart not found via ref!");
    return
  }; // Kiểm tra canvas tồn tại
  // Hủy biểu đồ cũ nếu có
  if (ctx.chart) {
    ctx.chart.destroy();
  }

  const labels = data.map(item => item.label);
  const counts = data.map(item => item.count);

  console.log("123456");
  console.log(labels, counts);

  ctx.chart = new Chart(ctx, { // Lưu instance biểu đồ
    type: 'bar', // Hoặc 'doughnut', 'pie'
    data: {
      labels: labels,
      datasets: [{
        label: 'Số lượng Đơn hàng',
        data: counts,
        backgroundColor: [ // Màu nền cho các cột/lát cắt
          'rgba(255, 99, 132, 0.5)',
          'rgba(54, 162, 235, 0.5)',
          'rgba(255, 206, 86, 0.5)',
          'rgba(75, 192, 192, 0.5)',
          'rgba(153, 102, 255, 0.5)',
          'rgba(255, 159, 64, 0.5)'
        ],
        borderColor: [ // Màu viền
          'rgba(255, 99, 132, 1)',
          'rgba(54, 162, 235, 1)',
          'rgba(255, 206, 86, 1)',
          'rgba(75, 192, 192, 1)',
          'rgba(153, 102, 255, 1)',
          'rgba(255, 159, 64, 1)'
        ],
        borderWidth: 1
      }]
    },
    options: {
      responsive: true,
      maintainAspectRatio: false,
      scales: {
        y: { beginAtZero: true }
      }
    }
  });
}



</script>

<style scoped>
/* Tùy chỉnh CSS cho nội dung dashboard */
.admin-dashboard-content {
  /* Thêm padding hoặc margin nếu cần */
}

.card {

    background-color:#0b1739;
border-radius:12px;
box-shadow:#0105114d 0px 8px 28px 0px;
color:#fff;
display:flex;
flex-direction:column;
gap:15px;
line-height:24px;
padding: 18px 18px 18px 20px;
}

/* Tùy chỉnh riêng cho các thẻ thống kê màu */


.card-title {
  color:  #aeb9e1;
  font-family: Work Sans;
  font-size:  14px;
  font-weight:  500;
  line-height:  21.98px;
  gap: 12px;
}

.card-text {
  font-size:28px;
font-weight:600;
letter-spacing: 1px;
line-height: 32.676px;
}

/* Override màu chữ cho card trắng */
.card .card-body .card-text {
  gap: 18px !important;
}


.btn-primary {
  background-color: #007bff;
  border-color: #007bff;
}

.btn-primary:hover {
  background-color: #0056b3;
  border-color: #004085;
}

/* Đảm bảo màu chữ trắng cho tiêu đề và nội dung trong card màu */
.card.text-white .card-title,
.card.text-white .card-text {
  color: #fff !important;
}

/* Style cho bảng */
.table {
  margin-bottom: 0;
  /* Loại bỏ margin dưới cùng mặc định của bảng */
}
</style>
