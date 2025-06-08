<template>
  <div class="container-fluid vh-100 p-5">
    <div class="row border">
      <!-- Hình ảnh bên trái -->
      <div
        class="col-md-6 d-none d-md-flex align-items-center justify-content-center"
        style="background-image: url(https://i.pinimg.com/originals/d5/53/fd/d553fd8172563455f80fe22edb33f4b6.jpg); background-size: cover; background-position: center;"
      >
        <img
          src="https://i.pinimg.com/originals/d5/53/fd/d553fd8172563455f80fe22edb33f4b6.jpg"
          alt="Loafer Patent"
          class="img-fluid w-75"
        />
      </div>

      <!-- Form bên phải -->
      <div class="col-md-6 d-flex align-items-center justify-content-center">
        <div class="w-75">
          <a href="/" class="text-end mb-4 d-block">
            <img src="../assets/img/logoUser.png" alt="Sneakers Logo" style="height: 40px;" />
          </a>

          <h2 class="mb-4 fw-bold">Đặt lại mật khẩu mới</h2>

          <form @submit.prevent="submitResetPassword">
            <div class="mb-3">
              <input
                type="password"
                v-model="password"
                class="form-control"
                placeholder="Mật khẩu mới"
                required
                minlength="6"
              />
            </div>

            <div class="mb-3">
              <input
                type="password"
                v-model="confirmPassword"
                class="form-control"
                placeholder="Xác nhận mật khẩu"
                required
                minlength="6"
              />
            </div>

            <div v-if="error" class="alert alert-danger py-2">
              {{ error }}
            </div>

            <div v-if="successMessage" class="alert alert-success py-2">
              {{ successMessage }}
            </div>

            <div class="d-grid mb-3">
              <button type="submit" class="btn btn-dark py-2" :disabled="isLoading">
                <span v-if="isLoading" class="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span>
                <span v-else>Đặt lại mật khẩu</span>
              </button>
            </div>

            <div class="text-center">
              <router-link to="/login">Quay lại đăng nhập</router-link>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
// const ttt = this.$route.query.token;
export default {
  name: "ResetPassword",
  data() {
    return {
      password: "",
      confirmPassword: "",
      error: "",
      successMessage: "",
      isLoading: false,
      token: "",
    };
  },
  created() {
    // Lấy token từ query param ?token=...
    this.token = this.$route.query.token || "";
    if (!this.token) {
      this.error = "Token đặt lại mật khẩu không hợp lệ hoặc bị thiếu.";
    }
  },
  methods: {
    async submitResetPassword() {
      this.error = "";
      this.successMessage = "";

      if (!this.token) {
        this.error = "Token đặt lại mật khẩu không hợp lệ hoặc bị thiếu.";
        return;
      }

      if (this.password !== this.confirmPassword) {
        this.error = "Mật khẩu xác nhận không khớp.";
        return;
      }

      if (this.password.length < 6) {
        this.error = "Mật khẩu phải có ít nhất 6 ký tự.";
        return;
      }

      this.isLoading = true;

      try {
        const response = await axios.post("http://localhost:8080/api/users/reset-password", {
          token: this.token,
          password: this.password,
          confirmPassword: this.confirmPassword,
        });

        this.successMessage = response.data || "Đặt lại mật khẩu thành công! Bạn có thể đăng nhập lại.";
        // Tự động chuyển về trang đăng nhập sau 2 giây
        setTimeout(() => {
          this.$router.push("/login");
        }, 2000);
      } catch (error) {
        this.error =
          error.response?.data || "Lỗi khi đặt lại mật khẩu. Vui lòng thử lại.";
      } finally {
        this.isLoading = false;
      }
    },
  },
};
</script>

<style scoped>
/* Responsive hình ảnh cho mobile */
@media (max-width: 767.98px) {
  .img-fluid {
    display: none;
  }
}

/* Tùy chỉnh form giống mẫu */
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

.border {
  width: 90%;
  position: relative;
  margin: auto;
  top: 40px;
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}
</style>
