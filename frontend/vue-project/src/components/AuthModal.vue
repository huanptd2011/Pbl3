<template>
  <div class="dropdown">
    <div class="dropdown-content">
      <div class="dropdown-item">Tài Khoản Của Tôi</div>
      <div class="dropdown-item">Đơn Mua</div>
      <div class="dropdown-item" @click="handleLogout">Đăng Xuất</div>
    </div>
  </div>
</template>



<script setup>
import { useRouter } from 'vue-router';
import { useUserStore } from '@/stores/user';

const router = useRouter();
const userStore = useUserStore();

async function handleLogout() {
  try {
    const response = await fetch('http://localhost:8080/api/users/log-out', {
      method: 'POST',
      credentials: 'include',
    });

    if (response.ok) {
      localStorage.removeItem('token');
      userStore.logout(); // <-- đúng hàm logout
      router.push('/');   // <-- dùng router từ useRouter
    } else {
      console.error('Logout failed');
    }
  } catch (error) {
    console.error('Error during logout:', error);
  }
}
</script>

<style scoped>
.dropdown {
  position: absolute;
  top: 60px;
  right: 20px;
  z-index: 1000;
}

.dropdown-content {
  background: white;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.15);
  min-width: 150px;
  padding: 0.5rem 0;
}

.dropdown-item {
  padding: 0.75rem 1rem;
  font-size: 14px;
  color: #333;
  cursor: pointer;
  text-align: left;
}

.dropdown-item:hover {
  background-color: #f5f5f5;
}
</style>
