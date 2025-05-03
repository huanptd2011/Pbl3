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
              <label for="email" class="form-label">Tên tài khoản/ Email</label>
              <input type="email" v-model="form.usernameOrEmail" class="form-control" id="usernameOrEmail" placeholder="usernameOrEmail" required>
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

<script>
import axios from 'axios';
import { useRouter } from 'vue-router';
import { useUserStore } from '@/stores/user';

export default {
  name: "LoginView",
  setup() {
    const router = useRouter();
    return { router };
  },
  data() {
    return {
      form: {
        usernameOrEmail: '',
        password: '',
      },
      isLoading: false,
    };
  },
  methods: {
    async submitForm() {
      this.isLoading = true;

      try {
        // Send POST request to the backend login endpoint
        const response = await axios.post('http://localhost:8080/api/users/log-in', {
          usernameOrEmail: this.form.usernameOrEmail,
          password: this.form.password,
        });
        // Check the response status code
        if (response.data.status === 200) {


          // Login successful
          const { token, username, role, email } = response.data;

          // Store the token (e.g., in localStorage or a Vuex/Pinia store)
          localStorage.setItem('token', token);
          localStorage.setItem('username', username);
          localStorage.setItem('role', role);
          localStorage.setItem('email', email);

          // Show success message (optional)
          // alert(response.data.message);

          // Redirect to the homepage or dashboard
          const userStore = useUserStore();
          userStore.login(); // Cập nhật trạng thái isLoggedIn
          this.router.push('/');
        } else {
          // Handle errors returned by the backend
          alert(response.data.message);
        }
      } catch (error) {
        // Handle network or unexpected errors
        if (error.response && error.response.data) {
          alert(error.response.data.message || 'Đăng nhập thất bại!');
        } else {
          alert('Có lỗi xảy ra, vui lòng thử lại sau!');
        }
      } finally {
        this.isLoading = false;
      }
    },
  },
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
