<template>
  <div class="container-fluid vh-100 p-5">
    <div class="row border">
      <div
        class="col-md-6 d-none d-md-flex align-items-center justify-content-center"
        style="background-image: url(https://i.pinimg.com/originals/d5/53/fd/d553fd8172563455f80fe22edb33f4b6.jpg);"
      >
        <img
          src="https://i.pinimg.com/originals/d5/53/fd/d553fd8172563455f80fe22edb33f4b6.jpg"
          alt="Reset Password"
          class="img-fluid w-75"
        />
      </div>

      <div class="col-md-6 d-flex align-items-center justify-content-center">
        <div class="w-75">
          <a href="/" class="text-end mb-4 d-block">
            <img src="../assets/img/logoUser.png" alt="Sneakers Logo" style="height: 40px" />
          </a>

          <h2 class="mb-4 fw-bold">Quên mật khẩu</h2>

          <form @submit.prevent="submitEmail">
            <div class="mb-3">
              <label for="email" class="form-label">Nhập email để đặt lại mật khẩu</label>
              <input
                type="email"
                v-model="email"
                class="form-control"
                id="email"
                required
              />
            </div>

            <div class="d-grid mb-3">
              <button type="submit" class="btn btn-dark py-2" :disabled="isLoading">
                <span
                  v-if="isLoading"
                  class="spinner-border spinner-border-sm"
                  role="status"
                  aria-hidden="true"
                ></span>
                <span v-else>Gửi email đặt lại mật khẩu</span>
              </button>
            </div>

            <div v-if="message" class="alert alert-info p-2" role="alert">
              {{ message }}
            </div>
          </form>

          <div class="text-start mt-3">
            <small><router-link to="/">Quay lại đăng nhập</router-link></small>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import axios from 'axios'

const email = ref('')
const message = ref('')
const isLoading = ref(false)

const submitEmail = async () => {
  if (!email.value) {
    alert('Vui lòng nhập email hợp lệ')
    return
  }

  isLoading.value = true
  message.value = ''

  try {
    const res = await axios.post('http://localhost:8080/api/users/forgot-password', null, {
      params: { email: email.value }
    })
    message.value = res.data.message || 'Đã gửi email đặt lại mật khẩu thành công!'
  } catch (err) {
    message.value = err.response?.data?.message || 'Lỗi khi gửi email. Vui lòng thử lại.'
  } finally {
    isLoading.value = false
  }
}
</script>

<style scoped>
.form-label {
  font-size: 0.9rem;
  color: #666;
}

.form-control {
  border-radius: 0;
  border-bottom: 1px solid #ced4da;
  border-top: none;
  border-left: none;
  border-right: none;
  padding: 0.75rem;
  background-color: #fff !important;
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

.spinner-border {
  margin-right: 5px;
}

.border {
  width: 90%;
  position: relative;
  margin: auto;
  top: 40px;
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

/* Responsive hình ảnh cho mobile */
@media (max-width: 767.98px) {
  .img-fluid {
    display: none;
  }
}
</style>
