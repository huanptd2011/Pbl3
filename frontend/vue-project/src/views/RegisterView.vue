<template>
  <div class="container-fluid vh-100">
    <div class="row h-100">
      <!-- Hình ảnh bên trái -->
      <div class="col-md-6 d-none d-md-flex align-items-center justify-content-center bg-light">
        <div class="text-center">
          <h1 class="display-4 text-muted">TUXEDO</h1>
          <h3 class="text-muted">LOAFER PATENT</h3>
          <img src="@/assets/img/images.jpeg" alt="Tuxedo Shoes" class="img-fluid w-75 mt-4">
        </div>
      </div>

      <!-- Form bên phải -->
      <div class="col-md-6 d-flex align-items-center justify-content-center">
        <div class="w-75">
          <div class="text-end mb-4">
            <img src="@/assets/logo.jpeg" alt="Logo" style="height: 40px;">
          </div>

          <h2 class="mb-4 fw-bold">Tạo tài khoản</h2>

          <form @submit.prevent="submitForm">
            <div class="row mb-3">
              <div class="col">
                <input type="text" v-model="form.lastName" class="form-control" placeholder="Họ" required>
              </div>
              <div class="col">
                <input type="text" v-model="form.firstName" class="form-control" placeholder="Tên" required>
              </div>
            </div>

            <div class="mb-3">
              <input type="email" v-model="form.email" class="form-control" placeholder="Email" required>
            </div>

            <div class="mb-3">
              <input type="tel" v-model="form.phone" class="form-control" placeholder="Số điện thoại" required>
            </div>

            <div class="row mb-4">
              <div class="col">
                <input type="password" v-model="form.password" class="form-control" placeholder="Mật khẩu" required>
              </div>
              <div class="col">
                <input type="password" v-model="form.confirmPassword" class="form-control" placeholder="Xác nhận mật khẩu" required>
              </div>
            </div>

            <div class="d-grid mb-3">
              <button type="submit" class="btn btn-dark py-2">Tạo tài khoản</button>
            </div>

            <div class="text-center">
              <small>Đã có tài khoản? <router-link to="/login">Đăng nhập</router-link></small>
            </div>
          </form>

          <div class="text-end mt-4">
            <small><a href="#">Điều khoản & Điều kiện của NHN</a></small>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
export default {
  name: "UserLogin",
  data() {
    return {
      form: {
        lastName: '',
        firstName: '',
        email: '',
        phone: '',
        password: '',
        confirmPassword: '',
      }
    };
  },
  methods: {
    async submitForm() {
      if (!/^\S+@\S+\.\S+$/.test(this.form.email)) {
        alert("Email không hợp lệ!");
        return;
      }

      if (!/^[0-9]{9,11}$/.test(this.form.phone)) {
        alert("Số điện thoại không hợp lệ!");
        return;
      }

      if (this.form.password !== this.form.confirmPassword) {
        alert("Mật khẩu không khớp!");
        return;
      }

      try {
            const response = await axios.post('http://localhost:8080/api/users/create', {
              email: this.form.email,
              password: this.form.password,
              confirmPassword: this.form.confirmPassword,
              firstName: this.form.firstName,
              lastName: this.form.lastName,
              phone: this.form.phone
            });
            console.log(response)
            alert("Đăng ký thành công!");
          } catch (error) {
              if (error.response) {
                console.error("Máy chủ phản hồi với:", error.response.status, error.response.data);
                alert(`Lỗi: ${error.response.data.message || 'Đăng ký thất bại'}`);
              } else if (error.request) {
                console.error("Không nhận được phản hồi:", error.request);
                alert("Không có phản hồi từ máy chủ - kiểm tra kết nối mạng");
              } else {
                console.error("Lỗi thiết lập request:", error.message);
                alert("Lỗi request: " + error.message);
              }
            }
    }
  }
};
</script>

<style scoped>
/* Responsive hình ảnh cho mobile */
@media (max-width: 767.98px) {
  .img-fluid {
    display: none;
  }
}

/* Tùy chỉnh thêm để giống hình ảnh */
h1.display-4 {
  font-size: 4rem;
  font-weight: 300;
}

h3 {
  font-size: 1.5rem;
  font-weight: 300;
}

.form-control {
  border: none;
  border-bottom: 1px solid #ced4da;
  border-radius: 0;
}

.form-control:focus {
  box-shadow: none;
  border-bottom: 1px solid #000;
}

.btn-dark {
  border-radius: 0;
}
</style>
