<template>
    <div class="admin-layout d-flex">
      <div class="sidebar  text-white p-3">
        <h4 class="text-center mb-4">Admin Menu</h4>
        <ul class="nav flex-column">
          <li class="nav-item">
            <router-link to="/admin" class="nav-link text-white">
              Dashboard
            </router-link>
          </li>
          <li class="nav-item">
            <router-link to="/admin/users" class="nav-link text-white">
              Quản lý Người dùng
            </router-link>
          </li>
          <li class="nav-item">
            <router-link to="/admin/products" class="nav-link text-white">
              Quản lý Sản phẩm
            </router-link>
          </li>
           <li class="nav-item">
            <router-link to="/admin/orders" class="nav-link text-white">
              Quản lý Đơn hàng
            </router-link>
          </li>
          </ul>
        <div class="mt-auto">
           <button @click="logoutAdmin" class="btn btn-outline-light w-100">Đăng xuất</button>
        </div>
      </div>
  
      <div class="main-content flex-grow-1 p-4">
        <router-view></router-view>
      </div>
    </div>
  </template>
  
  <script setup>
  import { useRouter } from 'vue-router';
  import { useUserStore } from '@/stores/user'; // Import store người dùng
  
  const router = useRouter();
  const userStore = useUserStore(); // Lấy instance của store
  
  const logoutAdmin = () => {
    userStore.logout(); // Gọi action logout từ store
    router.push('/login'); // Chuyển hướng về trang đăng nhập sau khi logout
  };
  
  </script>
  
  <style scoped>
  .admin-layout {
    min-height: 100vh; 
    background-color: #080F27
    ;
  }
  
  .sidebar {
    width: 250px; /* Chiều rộng cố định của sidebar */
    flex-shrink: 0; /* Ngăn sidebar co lại */
    display: flex; /* Sử dụng flexbox để căn chỉnh nội dung bên trong sidebar */
    flex-direction: column; /* Sắp xếp các mục theo cột */
    background-color: #081028;
    border-right: 0.1px solid #dcd9d9;
  }
  
  .main-content {
    overflow-y: auto; /* Thêm scrollbar nếu nội dung quá dài */
  }
  
  /* Tùy chỉnh style cho các link trong sidebar */
  .sidebar .nav-link {
    padding: 10px 15px;
    border-radius: 5px;
    margin-bottom: 5px;
  }
  
  .sidebar .nav-link:hover {
    background-color: rgba(255, 255, 255, 0.1); /* Hiệu ứng hover */
  }
  
  .sidebar .nav-link.router-link-active {
    background-color: rgba(255, 255, 255, 0.2); /* Style cho link đang active */
    font-weight: bold;
  }
  
  /* Style cho nút đăng xuất */
  .sidebar .mt-auto {
      margin-top: auto; /* Đẩy nút đăng xuất xuống cuối sidebar */
      padding-top: 20px; /* Khoảng cách trên nút */
      border-top: 1px solid rgba(255, 255, 255, 0.1); /* Đường kẻ phân cách */
  }
  </style>
  