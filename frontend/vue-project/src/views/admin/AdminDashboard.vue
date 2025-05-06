<!-- 
<template>
    <div class="admin-dashboard-content">
      <h1 class="mb-4 text-center">Tổng quan Quản trị</h1>
  
      <div class="alert alert-info mb-4" role="alert">
        Chào mừng trở lại, <strong v-if="userStore.username">{{ userStore.username }}</strong>! Đây là bảng điều khiển của bạn.
      </div>
  
      <div class="row mb-4">
        <div class="col-md-6 col-lg-3 mb-3">
          <div class="card text-white bg-primary">
            <div class="card-body">
              <h5 class="card-title">Tổng Doanh thu</h5>
              <p class="card-text fs-3" v-if="metrics">{{ formatCurrency(metrics.totalRevenue) }}</p>
              <p class="card-text fs-3" v-else>...</p>
              <p class="card-text"><small v-if="metrics && metrics.revenueChangePercent !== undefined">+{{ metrics.revenueChangePercent }}% so với kỳ trước</small></p>
            </div>
          </div>
        </div>
  
        <div class="col-md-6 col-lg-3 mb-3">
          <div class="card text-white bg-success">
            <div class="card-body">
              <h5 class="card-title">Tổng Đơn hàng</h5>
               <p class="card-text fs-3" v-if="metrics">{{ metrics.totalOrders }}</p>
              <p class="card-text fs-3" v-else>...</p>
              <p class="card-text"><small v-if="metrics && metrics.ordersChangePercent !== undefined">+{{ metrics.ordersChangePercent }}% so với kỳ trước</small></p>
            </div>
          </div>
        </div>
  
        <div class="col-md-6 col-lg-3 mb-3">
          <div class="card text-white bg-warning">
            <div class="card-body">
              <h5 class="card-title">Tổng Người dùng</h5>
               <p class="card-text fs-3" v-if="metrics">{{ metrics.totalUsers }}</p>
               <p class="card-text fs-3" v-else>...</p>
              <p class="card-text"><small v-if="metrics && metrics.usersChangePercent !== undefined">+{{ metrics.usersChangePercent }}% so với kỳ trước</small></p>
            </div>
          </div>
        </div>
  
        <div class="col-md-6 col-lg-3 mb-3">
          <div class="card text-white bg-danger">
            <div class="card-body">
              <h5 class="card-title">Sản phẩm Tồn kho</h5>
               <p class="card-text fs-3" v-if="metrics">{{ metrics.lowStockCount }}</p>
              <p class="card-text fs-3" v-else>...</p>
              <p class="card-text"><small>Cần kiểm tra</small></p>
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
              <div v-if="!salesChartData" style="height: 300px; background-color: #f8f9fa; border: 1px dashed #ccc; display: flex; align-items: center; justify-content: center;">
                <p class="text-muted">Đang tải dữ liệu biểu đồ doanh thu...</p>
              </div>
               <canvas v-else id="salesChart" style="height: 300px;"></canvas>
              </div>
          </div>
        </div>
  
        <div class="col-md-12 col-lg-4 mb-4">
          <div class="card">
             <div class="card-header">
              Biểu đồ Đơn hàng (Ví dụ: Biểu đồ cột/tròn)
            </div>
            <div class="card-body">
               <div v-if="!ordersChartData" style="height: 300px; background-color: #f8f9fa; border: 1px dashed #ccc; display: flex; align-items: center; justify-content: center;">
                <p class="text-muted">Đang tải dữ liệu biểu đồ đơn hàng...</p>
              </div>
               <canvas v-else id="ordersChart" style="height: 300px;"></canvas>
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
                                      <td>{{ order.customerName }}</td>
                                      <td>{{ formatCurrency(order.totalAmount) }}</td>
                                      <td>{{ order.status }}</td>
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
  import { ref, onMounted } from 'vue';
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
  
  // Hàm gọi API lấy số liệu tổng quan
  async function fetchMetrics() {
    try {
      const response = await axios.get('http://localhost:8080/api/admin/dashboard/summary');
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
      const response = await axios.get('http://localhost:8080/api/admin/dashboard/sales-over-time?period=monthly');
      salesChartData.value = response.data;
      // Sau khi có dữ liệu, gọi hàm vẽ biểu đồ
      // renderSalesChart(salesChartData.value); // Uncomment khi đã tích hợp thư viện biểu đồ
    } catch (error) {
      console.error('Error fetching sales chart data:', error);
      // Xử lý lỗi
    }
  }
  
  // Hàm gọi API lấy dữ liệu biểu đồ đơn hàng
  async function fetchOrdersChartData() {
      try {
      // Yêu cầu backend cung cấp dữ liệu theo trạng thái
      const response = await axios.get('http://localhost:8080/api/admin/dashboard/orders-data?type=by-status');
      ordersChartData.value = response.data;
       // Sau khi có dữ liệu, gọi hàm vẽ biểu đồ
      // renderOrdersChart(ordersChartData.value); // Uncomment khi đã tích hợp thư viện biểu đồ
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
      const response = await axios.get('http://localhost:8080/api/admin/dashboard/recent-orders?limit=5');
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
      const ctx = document.getElementById('salesChart');
      if (!ctx) return; // Kiểm tra canvas tồn tại
      // Hủy biểu đồ cũ nếu có để tránh lỗi vẽ lại
      if (ctx.chart) {
          ctx.chart.destroy();
      }
      ctx.chart = new Chart(ctx, { // Lưu instance biểu đồ vào canvas element
          type: 'line', // Loại biểu đồ
          data: {
              labels: data.labels, // Nhãn trục X (ví dụ: tháng)
              datasets: [{
                  label: 'Doanh thu',
                  data: data.values, // Dữ liệu trục Y
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
      const ctx = document.getElementById('ordersChart');
       if (!ctx) return; // Kiểm tra canvas tồn tại
       // Hủy biểu đồ cũ nếu có
       if (ctx.chart) {
          ctx.chart.destroy();
      }
      ctx.chart = new Chart(ctx, { // Lưu instance biểu đồ
          type: 'bar', // Hoặc 'doughnut', 'pie'
          data: {
              labels: data.labels,
              datasets: [{
                  label: 'Số lượng Đơn hàng',
                  data: data.values,
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
    border: 1px solid #e0e0e0;
    border-radius: 8px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  }
  
  /* Tùy chỉnh riêng cho các thẻ thống kê màu */
  .card.bg-primary { background-color: #007bff !important; border-color: #007bff !important; }
  .card.bg-success { background-color: #28a745 !important; border-color: #28a745 !important; }
  .card.bg-warning { background-color: #ffc107 !important; border-color: #ffc107 !important; }
  .card.bg-danger { background-color: #dc3545 !important; border-color: #dc3545 !important; }
  
  .card-title {
    font-size: 1.25rem;
    font-weight: bold;
    margin-bottom: 10px;
  }
  
  .card-text {
    color: #fff; /* Màu chữ trắng cho thẻ màu */
  }
  
  /* Override màu chữ cho card trắng */
  .card .card-body .card-text:not(.fs-3) {
      color: #555; /* Màu chữ xám cho card trắng */
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
      margin-bottom: 0; /* Loại bỏ margin dưới cùng mặc định của bảng */
  }
  </style> -->


  <template>
    <div class="admin-dashboard-content">
      <h1 class="mb-4 text-center">Tổng quan Quản trị</h1>
  
      <div class="alert alert-info mb-4" role="alert">
        Chào mừng trở lại, <strong v-if="userStore.username">{{ userStore.username }}</strong>! Đây là bảng điều khiển của bạn.
      </div>
  
      <div class="row mb-4">
        <div class="col-md-6 col-lg-3 mb-3">
          <div class="card text-white bg-primary">
            <div class="card-body">
              <h5 class="card-title">Tổng Doanh thu</h5>
              <p class="card-text fs-3" v-if="metrics !== null">{{ formatCurrency(metrics.totalRevenue) }}</p>
              <p class="card-text fs-3" v-else>...</p>
              <p class="card-text"><small v-if="metrics !== null && metrics.revenueChangePercent !== undefined">+{{ metrics.revenueChangePercent }}% so với kỳ trước</small></p>
            </div>
          </div>
        </div>
  
        <div class="col-md-6 col-lg-3 mb-3">
          <div class="card text-white bg-success">
            <div class="card-body">
              <h5 class="card-title">Tổng Đơn hàng</h5>
               <p class="card-text fs-3" v-if="metrics !== null">{{ metrics.totalOrders }}</p>
              <p class="card-text fs-3" v-else>...</p>
              <p class="card-text"><small v-if="metrics !== null && metrics.ordersChangePercent !== undefined">+{{ metrics.ordersChangePercent }}% so với kỳ trước</small></p>
            </div>
          </div>
        </div>
  
        <div class="col-md-6 col-lg-3 mb-3">
          <div class="card text-white bg-warning">
            <div class="card-body">
              <h5 class="card-title">Tổng Người dùng</h5>
               <p class="card-text fs-3" v-if="metrics !== null">{{ metrics.totalUsers }}</p>
               <p class="card-text fs-3" v-else>...</p>
              <p class="card-text"><small v-if="metrics !== null && metrics.usersChangePercent !== undefined">+{{ metrics.usersChangePercent }}% so với kỳ trước</small></p>
            </div>
          </div>
        </div>
  
        <div class="col-md-6 col-lg-3 mb-3">
          <div class="card text-white bg-danger">
            <div class="card-body">
              <h5 class="card-title">Sản phẩm Tồn kho</h5>
               <p class="card-text fs-3" v-if="metrics !== null">{{ metrics.lowStockCount }}</p>
              <p class="card-text fs-3" v-else>...</p>
              <p class="card-text"><small>Cần kiểm tra</small></p>
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
              <div v-if="salesChartData === null" style="height: 300px; background-color: #f8f9fa; border: 1px dashed #ccc; display: flex; align-items: center; justify-content: center;">
                <p class="text-muted">Đang tải dữ liệu biểu đồ doanh thu...</p>
              </div>
               <canvas v-else id="salesChart" style="height: 300px;"></canvas>
              </div>
          </div>
        </div>
  
        <div class="col-md-12 col-lg-4 mb-4">
          <div class="card">
             <div class="card-header">
              Biểu đồ Đơn hàng (Ví dụ: Biểu đồ cột/tròn)
            </div>
            <div class="card-body">
               <div v-if="ordersChartData === null" style="height: 300px; background-color: #f8f9fa; border: 1px dashed #ccc; display: flex; align-items: center; justify-content: center;">
                <p class="text-muted">Đang tải dữ liệu biểu đồ đơn hàng...</p>
              </div>
               <canvas v-else id="ordersChart" style="height: 300px;"></canvas>
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
                       <div v-else-if="recentOrders.length === 0 && !loadingRecentOrders" class="text-center">Không có đơn hàng gần đây.</div>
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
                                      <td>{{ order.customerName }}</td>
                                      <td>{{ formatCurrency(order.totalAmount) }}</td>
                                      <td>{{ order.status }}</td>
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
  import { ref, onMounted, nextTick } from 'vue'; // Import nextTick
  // import axios from 'axios'; // Không dùng Axios khi dùng dữ liệu giả
  import { useUserStore } from '@/stores/user';
  // Import thư viện biểu đồ tại đây (ví dụ với Chart.js)
  import Chart from 'chart.js/auto'; // Uncomment để dùng Chart.js
  
  const userStore = useUserStore();
  
  // Định nghĩa các biến state để lưu dữ liệu từ API
  const metrics = ref(null);
  const salesChartData = ref(null);
  const ordersChartData = ref(null);
  const recentOrders = ref([]);
  const loadingRecentOrders = ref(true); // Thêm trạng thái loading cho bảng đơn hàng
  
  // --- DỮ LIỆU GIẢ (MOCK DATA) ---
  const mockMetrics = {
    totalRevenue: 255000000, // Ví dụ: 255 triệu
    revenueChangePercent: 18.5,
    totalOrders: 780,
    ordersChangePercent: 12.3,
    totalUsers: 3500,
    usersChangePercent: 7.1,
    lowStockCount: 45
  };
  
  const mockSalesChartData = {
    labels: ["Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4", "Tháng 5", "Tháng 6", "Tháng 7", "Tháng 8", "Tháng 9", "Tháng 10", "Tháng 11", "Tháng 12"],
    values: [15000000, 18000000, 20000000, 22000000, 25000000, 23000000, 26000000, 28000000, 30000000, 32000000, 35000000, 40000000] // Ví dụ: Doanh thu theo tháng
  };
  
  const mockOrdersChartData = {
    labels: ["Chờ xử lý", "Đang giao", "Đã hoàn thành", "Đã hủy"],
    values: [30, 150, 500, 100] // Ví dụ: Số lượng đơn hàng theo trạng thái
  };
  
  const mockRecentOrders = [
    { orderId: 101, customerName: "Nguyễn Văn A", totalAmount: 750000, status: "Đã hoàn thành", orderDate: "2023-10-27T11:20:00Z" },
    { orderId: 102, customerName: "Trần Thị B", totalAmount: 1200000, status: "Đang giao", orderDate: "2023-10-27T10:55:00Z" },
    { orderId: 103, customerName: "Lê Văn C", totalAmount: 500000, status: "Chờ xử lý", orderDate: "2023-10-27T10:30:00Z" },
    { orderId: 104, customerName: "Phạm Thị D", totalAmount: 950000, status: "Đã hoàn thành", orderDate: "2023-10-27T09:15:00Z" },
    { orderId: 105, customerName: "Hoàng Văn E", totalAmount: 600000, status: "Đang giao", orderDate: "2023-10-27T08:40:00Z" },
  ];
  // ---------------------------------
  
  
  // Hàm mô phỏng gọi API lấy số liệu tổng quan
  async function fetchMetrics() {
    // Mô phỏng độ trễ mạng
    await new Promise(resolve => setTimeout(resolve, 500));
    metrics.value = mockMetrics;
  }
  
  // Hàm mô phỏng gọi API lấy dữ liệu biểu đồ doanh thu
  async function fetchSalesChartData() {
     // Mô phỏng độ trễ mạng
    await new Promise(resolve => setTimeout(resolve, 700));
    salesChartData.value = mockSalesChartData;
     // Sau khi có dữ liệu và DOM được cập nhật, gọi hàm vẽ biểu đồ
     nextTick(() => {
          renderSalesChart(salesChartData.value);
     });
  }
  
  // Hàm mô phỏng gọi API lấy dữ liệu biểu đồ đơn hàng
  async function fetchOrdersChartData() {
      // Mô phỏng độ trễ mạng
     await new Promise(resolve => setTimeout(resolve, 600));
      ordersChartData.value = mockOrdersChartData;
      // Sau khi có dữ liệu và DOM được cập nhật, gọi hàm vẽ biểu đồ
      nextTick(() => {
           renderOrdersChart(ordersChartData.value);
      });
  }
  
  // Hàm mô phỏng gọi API lấy danh sách đơn hàng gần đây
  async function fetchRecentOrders() {
      loadingRecentOrders.value = true; // Bắt đầu loading
      // Mô phỏng độ trễ mạng
      await new Promise(resolve => setTimeout(resolve, 800));
      recentOrders.value = mockRecentOrders;
      loadingRecentOrders.value = false; // Kết thúc loading
  }
  
  // Hàm định dạng tiền tệ (ví dụ: thêm dấu phân cách hàng nghìn)
  function formatCurrency(value) {
      if (value == null) return '';
      // Sử dụng toLocaleString để định dạng tiền tệ
      // Đảm bảo giá trị là số trước khi định dạng
      const numberValue = typeof value === 'string' ? parseFloat(value) : value;
      if (isNaN(numberValue)) return value; // Trả về giá trị gốc nếu không phải số
  
      return numberValue.toLocaleString('vi-VN', { style: 'currency', currency: 'VND' });
  }
  
  // Hàm định dạng ngày tháng
  function formatDate(dateString) {
      if (!dateString) return '';
      // Chuyển đổi chuỗi ngày ISO 8601 sang đối tượng Date và định dạng
      const options = { year: 'numeric', month: 'numeric', day: 'numeric', hour: 'numeric', minute: 'numeric' };
      // Kiểm tra xem dateString có phải là chuỗi hợp lệ không
      try {
          const date = new Date(dateString);
          if (isNaN(date.getTime())) {
              return dateString; // Trả về chuỗi gốc nếu không phân tích được
          }
           return date.toLocaleDateString('vi-VN', options);
      } catch (e) {
          console.error("Error formatting date:", e);
          return dateString; // Trả về chuỗi gốc nếu có lỗi
      }
  }
  
  
  // Lifecycle hook: Chạy khi component được mount vào DOM
  onMounted(() => {
    console.log('Admin Dashboard View mounted');
    console.log('Current user role:', userStore.role);
  
    // Gọi các hàm fetch data (mô phỏng) khi component được mount
    fetchMetrics();
    fetchSalesChartData();
    fetchOrdersChartData();
    fetchRecentOrders();
  });
  
  // --- Các hàm vẽ biểu đồ (đã tích hợp Chart.js) ---
  
  // Hàm vẽ biểu đồ doanh thu
  function renderSalesChart(data) {
      const ctx = document.getElementById('salesChart');
      if (!ctx) {
          console.error("Canvas element for salesChart not found!");
          return; // Kiểm tra canvas tồn tại
      }
      // Hủy biểu đồ cũ nếu có để tránh lỗi vẽ lại
      if (ctx.chart) {
          ctx.chart.destroy();
      }
      ctx.chart = new Chart(ctx, { // Lưu instance biểu đồ vào canvas element
          type: 'line', // Loại biểu đồ
          data: {
              labels: data.labels, // Nhãn trục X (ví dụ: tháng)
              datasets: [{
                  label: 'Doanh thu',
                  data: data.values, // Dữ liệu trục Y
                  borderColor: 'rgba(75, 192, 192, 1)',
                  backgroundColor: 'rgba(75, 192, 192, 0.2)', // Thêm màu nền dưới đường
                  tension: 0.1,
                  fill: true // Tô màu dưới đường
              }]
          },
          options: {
               responsive: true, // Biểu đồ responsive
               maintainAspectRatio: false, // Cho phép điều chỉnh tỷ lệ khung hình
               scales: {
                  y: {
                      beginAtZero: true, // Trục Y bắt đầu từ 0
                      ticks: {
                          callback: function(value) { // Định dạng nhãn trục Y (tiền tệ)
                              return formatCurrency(value);
                          }
                      }
                  }
               },
               plugins: {
                  tooltip: { // Tùy chỉnh tooltip
                      callbacks: {
                          label: function(context) {
                              let label = context.dataset.label || '';
                              if (label) {
                                  label += ': ';
                              }
                              if (context.parsed.y !== null) {
                                  label += formatCurrency(context.parsed.y);
                              }
                              return label;
                          }
                      }
                  }
               }
          }
      });
  }
  
  
  // Hàm vẽ biểu đồ đơn hàng
  function renderOrdersChart(data) {
      const ctx = document.getElementById('ordersChart');
       if (!ctx) {
          console.error("Canvas element for ordersChart not found!");
          return; // Kiểm tra canvas tồn tại
      }
       // Hủy biểu đồ cũ nếu có
       if (ctx.chart) {
          ctx.chart.destroy();
      }
      ctx.chart = new Chart(ctx, { // Lưu instance biểu đồ
          type: 'bar', // Hoặc 'doughnut', 'pie'
          data: {
              labels: data.labels,
              datasets: [{
                  label: 'Số lượng Đơn hàng',
                  data: data.values,
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
              },
               plugins: {
                  tooltip: { // Tùy chỉnh tooltip
                      callbacks: {
                          label: function(context) {
                              let label = context.dataset.label || '';
                              if (label) {
                                  label += ': ';
                              }
                              if (context.parsed.y !== null) {
                                  label += context.parsed.y.toLocaleString(); // Định dạng số lượng
                              }
                              return label;
                          }
                      }
                  }
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
    border: 1px solid #e0e0e0;
    border-radius: 8px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  }
  
  /* Tùy chỉnh riêng cho các thẻ thống kê màu */
  .card.bg-primary { background-color: #007bff !important; border-color: #007bff !important; }
  .card.bg-success { background-color: #28a745 !important; border-color: #28a745 !important; }
  .card.bg-warning { background-color: #ffc107 !important; border-color: #ffc107 !important; }
  .card.bg-danger { background-color: #dc3545 !important; border-color: #dc3545 !important; }
  
  .card-title {
    font-size: 1.25rem;
    font-weight: bold;
    margin-bottom: 10px;
  }
  
  .card-text {
    color: #fff; /* Màu chữ trắng cho thẻ màu */
  }
  
  /* Override màu chữ cho card trắng */
  .card .card-body .card-text:not(.fs-3) {
      color: #555; /* Màu chữ xám cho card trắng */
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
      margin-bottom: 0; /* Loại bỏ margin dưới cùng mặc định của bảng */
  }
  </style>
  