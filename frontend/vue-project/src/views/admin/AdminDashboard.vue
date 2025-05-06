<template>
    <div class="admin-dashboard-container container mt-4">
      <h1 class="mb-4 text-center">Chào mừng đến với Trang Quản trị</h1>
  
      <div class="row">
        <div class="col-md-6 col-lg-4 mb-4">
          <div class="card">
            <div class="card-body text-center">
              <h5 class="card-title">Quản lý Người dùng</h5>
              <p class="card-text">Xem, thêm, sửa, xóa thông tin người dùng.</p>
              <router-link to="/admin/users" class="btn btn-primary">Đi đến</router-link>
            </div>
          </div>
        </div>
  
        <div class="col-md-6 col-lg-4 mb-4">
          <div class="card">
            <div class="card-body text-center">
              <h5 class="card-title">Quản lý Sản phẩm</h5>
              <p class="card-text">Xem, thêm, sửa, xóa sản phẩm.</p>
              <router-link to="/admin/products" class="btn btn-primary">Đi đến</router-link>
            </div>
          </div>
        </div>
  
        <div class="col-md-6 col-lg-4 mb-4">
          <div class="card">
            <div class="card-body text-center">
              <h5 class="card-title">Quản lý Đơn hàng</h5>
              <p class="card-text">Xem, cập nhật trạng thái đơn hàng.</p>
              <router-link to="/admin/orders" class="btn btn-primary">Đi đến</router-link>
            </div>
          </div>
        </div>
  
        </div>
    </div>
  </template>
  
  <script setup>
  // Import Pinia store nếu bạn muốn hiển thị thông tin admin trên dashboard
  import { useUserStore } from '@/stores/user';
  import { onMounted } from 'vue'; // Import onMounted hook
  
  const userStore = useUserStore();
  
  // Lifecycle hook chạy sau khi component được gắn vào DOM
  onMounted(() => {
    console.log('Admin Dashboard mounted');
    console.log('Current user role:', userStore.role);
    // Bạn có thể thêm logic tải dữ liệu tổng quan cho dashboard ở đây
  });
  
  </script>
  
  <style scoped>
  /* Tùy chỉnh CSS cho dashboard admin */
  .admin-dashboard-container {
    /* Thêm padding hoặc margin nếu cần */
  }
  
  .card {
    border: 1px solid #e0e0e0;
    border-radius: 8px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  }
  
  .card-title {
    font-size: 1.25rem;
    font-weight: bold;
    margin-bottom: 10px;
  }
  
  .card-text {
    color: #555;
  }
  
  .btn-primary {
    background-color: #007bff;
    border-color: #007bff;
  }
  
  .btn-primary:hover {
    background-color: #0056b3;
    border-color: #004085;
  }
  </style>
  