<template>
    <div class="admin-layout d-flex fixed">
      <div class="sidebar  text-white p-3">
        <router-link to="/profile" class="nav-link text-white text-center">
                {{ userStore.user.username }}
        </router-link>
        <ul class="nav flex-column">
          <li class="nav-item">
            <router-link to="/admin" class="nav-link text-cl">
              <div class="d-flex align-items-center">
                <div class="d-flex  me-3">
                  <i class="fas fa-tachometer-alt me-1 "></i>
                </div>
                <span class="d-none d-md-inline">Dashboard</span>
              </div>
            </router-link>
          </li>
          <li class="nav-item">
            <router-link to="/admin/users" class="nav-link text-cl">
              <div class="d-flex align-items-center">
                <div class="d-flex  me-3">
                  <i class="fas fa-users"></i>
                </div>
                <span class="d-none d-md-inline">Quản lý Người dùng</span>
              </div>
            </router-link>
          </li>
          <li class="nav-item">
            <router-link to="/admin/products" class="nav-link text-cl">
              <div class="d-flex align-items-center">
                <div class="d-flex  me-3">
                  <i class="fas fa-box me-1"></i>
                </div>
                <span class="d-none d-md-inline">Quản lý Sản phẩm</span>
              </div>
            </router-link>
          </li>
           <li class="nav-item">
            <router-link to="/admin/orders" class="nav-link text-cl">
              <div class="d-flex align-items-center">
                <div class="d-flex  me-3">
                  <i class="fas fa-shopping-cart"></i>
                </div>
                <span class="d-none d-md-inline">Quản lý Đơn hàng</span>
              </div>
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
    router.push('/'); // Chuyển hướng về trang đăng nhập sau khi logout
  };

  </script>

  <style scoped>
  .admin-layout {
    font-family: "Mona Sans", sans-serif;
    min-height: 100vh;
    background-color: #080F27
    ;
  }
 .fixed {
    position: fixed; /* Đặt layout ở vị trí cố định */
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    display: flex; /* Sử dụng flexbox để căn chỉnh sidebar và nội dung chính */
  }
  .sidebar {
    width: 250px; /* Chiều rộng cố định của sidebar */
    flex-shrink: 0; /* Ngăn sidebar co lại */
    display: flex; /* Sử dụng flexbox để căn chỉnh nội dung bên trong sidebar */
    flex-direction: column; /* Sắp xếp các mục theo cột */
    background-color: #081028;
    border-right: 0.1px solid #2e3454;
  }

  .main-content {
    overflow-y: auto; /* Thêm scrollbar nếu nội dung quá dài */
  }

  /* Tùy chỉnh style cho các link trong sidebar */
  .sidebar .nav-link {
    padding: 10px 15px;
    border-radius: 5px;
    margin-bottom: 5px;
    transition: all 0.3s ease; 
  }

  .sidebar .nav-link:hover {
    background-color: rgba(129, 126, 126, 0.1); /* Hiệu ứng hover */
    color: #3B4360
  }

  .sidebar .nav-link.router-link-exact-active {
    color: #CB3CFF !important;
  }

  /* Style cho nút đăng xuất */
  .sidebar .mt-auto {
      margin-top: auto; /* Đẩy nút đăng xuất xuống cuối sidebar */
      padding-top: 20px; /* Khoảng cách trên nút */
      border-top: 1px solid rgba(255, 255, 255, 0.1); /* Đường kẻ phân cách */
  }
  </style>
