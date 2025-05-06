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

<script>
import axios from 'axios';
import { useRouter } from 'vue-router';
import { useUserStore } from '@/stores/user';

export default {
  name: "LoginView",
  setup() {
    const router = useRouter();
    // Lấy instance của store trong setup() nếu bạn muốn dùng nó ở đây
    // const userStore = useUserStore();
    return { router }; // Chỉ trả về router nếu bạn dùng nó trong template hoặc options API
  },
  data() {
    return {
      form: {
        usernameOrEmail: '',
        password: '',
      },
      isLoading: false, // Trạng thái loading cho button
    };
  },
  methods: {
    async submitForm() {
      this.isLoading = true; // Bắt đầu loading

      try {
        // Send POST request to the backend login endpoint
        // Axios sẽ tự động xử lý các mã trạng thái HTTP (2xx là success, 4xx/5xx là error)
        const response = await axios.post('http://localhost:8080/api/users/log-in', {
          usernameOrEmail: this.form.usernameOrEmail,
          password: this.form.password,
        });

        // Nếu code chạy đến đây, nghĩa là backend đã trả về mã trạng thái 2xx (thành công)
        // Dữ liệu người dùng cần được lấy trực tiếp từ response.data
        // Đảm bảo backend trả về các trường này trong body response khi thành công
        const { token, username, role, email } = response.data;

        // Kiểm tra xem các trường cần thiết có tồn tại không (tùy thuộc vào response backend)
        if (token && username && role && email) {
             // Lưu token và thông tin người dùng vào localStorage
            localStorage.setItem('token', token);
            localStorage.setItem('username', username);
            localStorage.setItem('role', role);
            localStorage.setItem('email', email);

            // Cập nhật trạng thái đăng nhập trong Pinia store
            const userStore = useUserStore();
            // Truyền dữ liệu người dùng vào action login của store
            userStore.login({ token, username, role, email });

            // Chuyển hướng dựa trên vai trò
            if(role === 'ADMIN') {
              this.router.push('/admin');
            } else if (role === 'CUSTOMER') {
              this.router.push('/');
            } else {
              // Xử lý trường hợp vai trò không xác định
              alert('Đăng nhập thành công nhưng vai trò không xác định.');
              this.router.push('/'); // Chuyển hướng mặc định
            }
        } else {
            // Trường hợp backend trả về 200 nhưng thiếu dữ liệu cần thiết
            alert('Đăng nhập thành công nhưng thiếu thông tin người dùng.');
             this.router.push('/login'); // Ở lại trang login hoặc chuyển hướng khác
        }


      } catch (error) {
        // Nếu code chạy vào đây, nghĩa là Axios đã bắt được lỗi (mã trạng thái 4xx/5xx hoặc lỗi mạng)
        console.error('Lỗi đăng nhập:', error); // Log lỗi chi tiết hơn

        if (error.response && error.response.data && error.response.data.message) {
          // Hiển thị thông báo lỗi từ backend nếu có
          alert(error.response.data.message);
        } else {
          // Hiển thị thông báo lỗi chung
          alert('Đăng nhập thất bại! Vui lòng kiểm tra lại thông tin.');
        }
      } finally {
        this.isLoading = false; // Kết thúc loading
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

/* Styling cho spinner */
.spinner-border {
    margin-right: 5px;
}
</style>
