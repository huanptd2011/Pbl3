<template>
  <div class="container-fluid vh-100">
    <div class="row h-100">
      <!-- Hình ảnh và branding bên trái -->
      <div class="col-md-6 d-none d-md-flex align-items-center justify-content-center bg-light">
        <div class="text-center">
          <h1 class="mb-3 fw-bold">MATISSE</h1>
          <p class="mb-4">LOAFER PATENT</p>
          <img src="@/assets/logo.jpeg" alt="Loafer Patent" class="img-fluid w-75">
        </div>
      </div>

      <!-- Form bên phải -->
      <div class="col-md-6 d-flex align-items-center justify-content-center">
        <div class="w-75">
          <!-- Logo ở góc trên bên phải -->
          <div class="text-end mb-4">
            <img src="@/assets/logo.jpeg" alt="Sneakers Logo" style="height: 40px;">
          </div>

          <h2 class="mb-4 fw-bold">Đăng nhập</h2>

          <form @submit.prevent="submitForm">
            <div class="mb-3">
              <label for="text" class="form-label">Tên tài khoản/ Email</label>
              <input type="text" v-model="form.usernameOrEmail" class="form-control" id="usernameOrEmail" placeholder="Username/Email" required>
            </div>

            <div class="mb-4">
              <label for="password" class="form-label">Mật khẩu</label>
              <input type="password" v-model="form.password" class="form-control" id="password" placeholder="Mật khẩu" required>
            </div>

            <div class="d-grid mb-3">
              <button type="submit" class="btn btn-dark py-2">Đăng nhập</button>
            </div>

            <div class="text-start mb-3">
              <small><a href="#">Quên mật khẩu?</a></small>
            </div>

            <div class="text-center mb-3">
              <small>Chưa có tài khoản? <router-link to="/register">Đăng ký ngay bây giờ</router-link></small>
            </div>

            <div class="text-center">
              <small><a href="#">Điều khoản & Điều kiện của NHN</a></small>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import axios from 'axios';
import { useRouter } from 'vue-router';
import { useUserStore } from '@/stores/user';
import { useCartStore } from '@/stores/cartStore';

const router = useRouter();
const userStore = useUserStore();
const cartUser = useCartStore();

const form = ref({
  usernameOrEmail: '',
  password: '',
});

const isLoading = ref(false);

const submitForm = async () => {
  isLoading.value = true;
  try {
    const response = await axios.post('http://localhost:8080/api/users/log-in', {
      usernameOrEmail: form.value.usernameOrEmail,
      password: form.value.password,
    });

    if (response.data.status === 200) {
      const { token, username, role, email, userId } = response.data;

      userStore.setUser({ username, email, role, userId, token });

      if (role === 'ADMIN') {
        router.push('/admin');
      } else if (role === 'CUSTOMER') {
        await cartUser.loadUserCart(userStore.user.userId);
        router.push('/');
      }
    } else {
      alert(response.data.message);
    }
  } catch (error) {
    console.error('Lỗi khi đăng nhập:', error);
    if (error.response && error.response.data) {
      alert(error.response.data.message || 'Đăng nhập thất bại!');
    } else {
      alert('Có lỗi xảy ra, vui lòng thử lại sau!');
    }
  } finally {
    isLoading.value = false;
  }
};
</script>



<style scoped>
.bg-light {
  background-color: #f5f5f5;
}

h1 {
  font-size: 2.5rem;
  letter-spacing: 0.1em;
  color: #666;
}

p {
  font-size: 1.2rem;
  letter-spacing: 0.05em;
  color: #666;
}

.form-label {
  font-size: 0.9rem;
  color: #666;
}

.form-control {
  border-radius: 0;
  border: 1px solid #ced4da;
  padding: 0.75rem;
}

.form-control:focus {
  border-color: #000;
  box-shadow: none;
}

.btn-dark {
  border-radius: 0;
  background-color: #000;
  color: #fff;
  padding: 0.75rem;
}

.btn-dark:hover {
  background-color: #333;
}

a {
  color: #007bff;
  text-decoration: none;
}

a:hover {
  text-decoration: underline;
}

/* Responsive hình ảnh cho mobile */
@media (max-width: 767.98px) {
  .img-fluid {
    display: none;
  }
}
</style>
