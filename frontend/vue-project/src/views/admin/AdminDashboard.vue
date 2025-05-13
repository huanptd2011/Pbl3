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
          <div class="card-header d-flex justify-content-between align-items-center">
            <span>
              Biểu đồ Doanh thu
            </span>
            <div class="d-flex align-items-center">
              <select v-model="salesTimePeriod" class="form-select form-select-sm me-2 calenda" style="width: 100px;">
                <option value="daily">Ngày</option>
                <option value="weekly">Tuần</option>
                <option value="monthly">Tháng</option>
                <option value="yearly">Năm</option>
              </select>

              <input type="date" v-model="salesStartDate" class="form-control form-control-sm me-1 calenda"
                style="width: 130px;">
              <i class="fas fa-arrow-right" style="color: #aeb9e1;"></i>
              <input type="date" v-model="salesEndDate" class="form-control form-control-sm ms-1 calenda"
                style="width: 130px;">

            </div>
            <!-- <input type="month" id="orderMonthPicker" v-model="selectedOrderMonth" @change="fetchOrdersChartDataByMonth" class="form-control form-control-sm calenda" style="width: 120px;"> -->
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
          <div class="card-header d-flex justify-content-between align-items-center">
            <span>Biểu đồ Đơn hàng</span>
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
                    <th>
                      <i class="fas fa-shopping-cart me-2 cl-main"></i>
                      <span class="">Đơn hàng</span>
                    </th>
                    <th>
                      <i class="fas fa-user me-2 cl-main"></i>
                      <span class="">Khách hàng</span>
                    </th>
                    <th>
                      <i class="fas fa-money-bill-wave me-2 cl-main"></i>
                      <span class="">Tổng tiền</span>
                      </th>
                    <th>
                      <i class="fas fa-check-circle me-2 cl-main"></i>
                      <span class="">Trạng thái</span>
                    </th>
                    <th>
                      <i class="fas fa-calendar-alt me-2 cl-main"></i>
                      <span class="">Ngày đặt</span>
                    </th>
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
import { ref, onMounted, nextTick, watch } from 'vue';
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

const salesTimePeriod = ref('monthly'); // Mặc định kỳ là Tháng
const salesStartDate = ref(''); // Ngày bắt đầu cho khoảng thời gian
const salesEndDate = ref('');   // Ngày kết thúc cho khoảng thời gian

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

function formatDateToYMD(date) {
  if (!date) return '';
  const year = date.getFullYear();
  const month = (date.getMonth() + 1).toString().padStart(2, '0');
  const day = date.getDate().toString().padStart(2, '0');
  return `${year}-${month}-${day}`;
}

function setDefaultSalesDate() {
  const today = new Date();
  const startDate = new Date(today.getFullYear(), today.getMonth(), 1); // Ngày đầu tháng
  const endDate = new Date(today.getFullYear(), today.getMonth() + 1, 0); // Ngày cuối tháng
  salesStartDate.value = formatDateToYMD(startDate);
  salesEndDate.value = formatDateToYMD(endDate);
}
// Hàm gọi API lấy dữ liệu biểu đồ doanh thu
async function fetchSalesChartData() {

  // Yêu cầu backend cung cấp dữ liệu theo tháng

  let apiUrl = 'http://localhost:8080/api/dashboard/sales-over-time';
  const params = {
    period: salesTimePeriod.value
  };
  if (salesStartDate.value) {
    params.startDate = salesStartDate.value;
  }
  if (salesEndDate.value) {
    params.endDate = salesEndDate.value;
  }

  try {
    const response = await axios.get(apiUrl, { params });
    salesChartData.value = response.data.reverse(); // Đảo ngược dữ liệu để hiển thị từ đầu đến cuối

    // Sau khi có dữ liệu, gọi hàm vẽ biểu đồ
    nextTick(() => {
      setTimeout(() => {
        renderSalesChart(salesChartData.value, salesTimePeriod.value);
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
  setDefaultSalesDate();
  fetchSalesChartData();
  fetchOrdersChartData();
  fetchRecentOrders();
});

// Watcher cho các biến của Biểu đồ Doanh thu
watch([salesTimePeriod, salesStartDate, salesEndDate], () => {
  console.log("Sales chart selections changed, refetching data.");
  // Gọi hàm fetch data khi kỳ hoặc khoảng ngày thay đổi
  fetchSalesChartData();
});

// --- Các hàm vẽ biểu đồ (cần tích hợp thư viện biểu đồ và uncomment) ---

function renderSalesChart(data, period) {
  const ctx = salesChartCanvas.value;
  if (!ctx) return;
  if (ctx.chart) {
    ctx.chart.destroy();
  }

  const labels = data.map(item => item.timeLabel);
  const revenues = data.map(item => item.revenue);

  let xAxisLabel = 'Thời gian';
  if (period === 'daily') xAxisLabel = 'Ngày';
  if (period === 'weekly') xAxisLabel = 'Tuần'; // Có thể cần format lại nhãn tuần
  if (period === 'monthly') xAxisLabel = 'Tháng'; // Có thể cần format lại nhãn tháng
  if (period === 'yearly') xAxisLabel = 'Năm';

  ctx.chart = new Chart(ctx, { // Lưu instance biểu đồ vào canvas element
    type: 'line', // Loại biểu đồ
    data: {
      labels: labels, // Nhãn trục X (ví dụ: tháng)
      datasets: [{
        label: 'Doanh thu',
        data: revenues, // Dữ liệu trục Y
        borderColor: 'rgba(75, 192, 192, 1)', // Màu xanh ngọc
        backgroundColor: 'rgba(75, 192, 192, 0.2)',
        tension: 0,
        fill: true,
        pointBorderColor: '#fff',
        pointRadius: 5,
        pointHoverRadius: 7 // Không tô màu dưới đường
      }]
    },
    options: {
      responsive: true,
      maintainAspectRatio: false,
      plugins: {
        legend: {
          display: false, // Hiển thị legend
          labels: {
            color: '#aeb9e1', // Màu chữ legend
          }
        },
        tooltip: {
          callbacks: {
            label: function (context) {
              const label = context.dataset.label || '';
              if (label) {
                return label + ': ' + formatCurrency(context.raw); // Format doanh thu
              }
              return formatCurrency(context.raw);
            }
          },
          backgroundColor: '#333', // Màu nền tooltip
          titleColor: '#fff', // Màu tiêu đề
          bodyColor: '#fff', // Màu nội dung
          borderColor: '#555',
          borderWidth: 1
        }
      },
      scales: {
        y: {
          beginAtZero: true,
          ticks: {
            color: '#aeb9e1', // Màu chữ trục Y
            callback: function (value) { // Format trục Y
              return formatCurrency(value);
            }
          },
          grid: {
            color: 'rgba(174, 185, 225, 0.2)' // Màu grid
          }
        },
        x: {
          title: {
            display: true,
            text: xAxisLabel, // Set nhãn trục X
            color: '#aeb9e1'
          },
          ticks: {
            color: '#aeb9e1' // Màu chữ trục X
          },
          grid: {
            color: 'rgba(174, 185, 225, 0.2)' // Màu grid
          }
        }
      }
    }
  });
}



function renderOrdersChart(data) {

  const ctx = ordersChartCanvas.value;

  if (!ctx) {
    console.error("Canvas element for ordersChart not found via ref!");
    return
  };
  if (ctx.chart) {
    ctx.chart.destroy();
  }

  const labels = data.map(item => item.label);
  const counts = data.map(item => item.count);

  console.log("123456");
  console.log(labels, counts);

  ctx.chart = new Chart(ctx, { // Lưu instance biểu đồ
    type: 'doughnut', // Hoặc 'doughnut', 'pie'
    data: {
      labels: labels,
      datasets: [{
        label: 'Số lượng Đơn hàng',
        data: counts,
        backgroundColor: [ // Màu nền cho các cột/lát cắt
          'rgba(0,194,255)',
          'rgba(14,67,251)',
          'rgba(203,60,255)'

        ],

        borderWidth: 1
      }]
    },
    options: {
      responsive: true,
      maintainAspectRatio: false,
      plugins: {
        legend: {
          position: 'bottom',
          
          labels: {
            color: '#aeb9e1',
            usePointStyle: true,
          }
        },
        tooltip: {
          callbacks: {
            label: function (context) {
              const label = context.label || '';
              const value = context.raw || 0;
              const total = context.dataset.data.reduce((sum, val) => sum + val, 0);
              const percentage = total > 0 ? ((value / total) * 100).toFixed(1) : 0;
              return `${label}: ${value} (${percentage}%)`; // Hiển thị giá trị và phần trăm
            }
          },
          backgroundColor: '#333', // Màu nền tooltip
          titleColor: '#fff', // Màu tiêu đề tooltip
          bodyColor: '#fff', // Màu nội dung tooltip
          borderColor: '#555',
          borderWidth: 1
        }
      },
      cutout: '50%', // Tạo biểu đồ Doughnut (độ rộng lỗ ở giữa)
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

/* Tùy chỉnh riêng cho các thẻ thống kê màu */


.card-title {
  color: #aeb9e1;
  font-family: Work Sans;
  font-size: 14px;
  font-weight: 500;
  line-height: 21.98px;
  gap: 12px;
}

.card-text {
  font-size: 28px;
  font-weight: 600;
  letter-spacing: 1px;
  line-height: 32.676px;
}

/* Override màu chữ cho card trắng */
.card .card-body .card-text {
  gap: 18px !important;
}



/* Đảm bảo màu chữ trắng cho tiêu đề và nội dung trong card màu */
.card.text-white .card-title,
.card.text-white .card-text {
  color: #fff !important;
}



input[type="date"].calenda::-webkit-calendar-picker-indicator,
input[type="month"].calenda::-webkit-calendar-picker-indicator {
    filter: invert(0.8); /* Đảo màu icon để phù hợp với nền tối */
    cursor: pointer;
}
</style>
