<template>
  <div class="container-fluid vh-100">
    <div class="row h-100">
      <div class="col-md-6 d-none d-md-flex align-items-center justify-content-center bg-light">
        <div class="text-center">
          <h1 class="mb-3 fw-bold">MATISSE</h1>
          <p class="mb-4">LOAFER PATENT</p>
          <img src="@/assets/logo.jpeg" alt="Loafer Patent" class="img-fluid w-75">
        </div>
      </div>

      <div class="col-md-6 d-flex align-items-center justify-content-center">
        <div class="w-75">
          <div class="text-end mb-4">
            <img src="@/assets/logo.jpeg" alt="Sneakers Logo" style="height: 40px;">
          </div>

          <h2 class="mb-4 fw-bold">Đăng nhập</h2>

          <form @submit.prevent="submitForm">
            <div class="mb-3">
              <label for="usernameOrEmail" class="form-label">Tên tài khoản/ Email</label>
              <input type="text" v-model="form.usernameOrEmail" class="form-control" id="usernameOrEmail" placeholder="Username/Email" required>
            </div>

            <div class="mb-4">
              <label for="password" class="form-label">Mật khẩu</label>
              <input type="password" v-model="form.password" class="form-control" id="password" placeholder="Mật khẩu" required>
            </div>

            <div class="d-grid mb-3">
              <button type="submit" class="btn btn-dark py-2" :disabled="isLoading">
                 <span v-if="isLoading" class="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span>
                 <span v-else>Đăng nhập</span>
              </button>
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
        const response = await axios.post('http://localhost:8080/api/users/log-in', {
          usernameOrEmail: this.form.usernameOrEmail,
          password: this.form.password,
        });

        const { token, username, role, email } = response.data;

        if (token && username && role && email) {
          localStorage.setItem('token', token);
          localStorage.setItem('username', username);
          localStorage.setItem('role', role);
          localStorage.setItem('email', email);

          const userStore = useUserStore();
          userStore.login({ token, username, role, email });

          if (role === 'ADMIN') {
            this.router.push('/admin');
          } else if (role === 'CUSTOMER') {
            this.router.push('/');
          } else {
            alert('Đăng nhập thành công nhưng vai trò không xác định.');
            this.router.push('/');
          }
        } else {
          alert('Đăng nhập thành công nhưng thiếu thông tin người dùng.');
          this.router.push('/login');
        }

      } catch (error) {
        console.error('Lỗi đăng nhập:', error);

        if (error.response?.data?.message) {
          alert(error.response.data.message);
        } else {
          alert('Đăng nhập thất bại! Vui lòng kiểm tra lại thông tin.');
        }
      } finally {
        this.isLoading = false;
      }
    }
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

@media (max-width: 767.98px) {
  .img-fluid {
    display: none;
  }
}

.spinner-border {
  margin-right: 5px;
}
</style>

