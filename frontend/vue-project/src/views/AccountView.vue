<template>
  <div class="profile-container">
    <h1>Thông Tin Cá Nhân</h1>

    <div class="profile-content">
      <!-- Phần avatar -->
      <div class="avatar-section">
        <div class="avatar-wrapper">
          <img :src="user.avatar || defaultAvatar" alt="Avatar" class="avatar">
          <input
            type="file"
            id="avatar-upload"
            accept="image/*"
            @change="handleAvatarChange"
            class="avatar-upload"
          >
          <label for="avatar-upload" class="avatar-upload-label">Đổi ảnh</label>
        </div>
      </div>

      <!-- Form thông tin -->
      <div class="info-section">
        <form @submit.prevent="saveProfile">
          <div class="form-group">
            <label for="username">Tên đăng nhập</label>
            <input
              type="text"
              id="username"
              v-model="user.username"
              disabled
            >
          </div>

          <div class="form-group">
            <label for="fullName">Họ và tên</label>
            <input
              type="text"
              id="fullname"
              v-model="user.fullName"
              required
            >
          </div>

          <div class="form-group">
            <label for="email">Email</label>
            <input
              type="email"
              id="email"
              v-model="user.email"
              disabled
            >
          </div>

          <div class="form-group">
            <label for="dob">Ngày sinh</label>
            <input
              type="date"
              id="dob"
              v-model="user.dob"
              :max="maxDate"
              required
            >
          </div>

          <div class="form-group">
            <label for="phone">Số điện thoại</label>
            <input
              type="tel"
              id="phone"
              v-model="user.phone"
              required
            >
          </div>

          <div class="form-group">
            <label for="address">Địa chỉ</label>
            <textarea
              id="address"
              v-model="user.address"
              rows="3"
              required
            ></textarea>
          </div>

          <button type="submit" class="save-btn">Lưu thông tin</button>
        </form>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useUserStore } from '@/stores/user'
import axios from 'axios'
import defaultAvatar from '@/assets/default-avatar.jpg'

const userStore = useUserStore();
const error = ref(null)
const isLoading = ref(false)

const user = ref({
    username: userStore.user.username,
    fullName: userStore.user.fullName,
    email: userStore.user.email,
    phone: userStore.user.phone,
    address: userStore.user.address,
    avatar: userStore.user.avatar || defaultAvatar,
    dob: userStore.user.dob || null  
})


const handleAvatarChange = (event) => {
  const file = event.target.files[0]
  if (file) {
    const reader = new FileReader()
    reader.onload = (e) => {
      user.value.avatar = e.target.result
    }
    reader.readAsDataURL(file)
  }
}



const saveProfile = async () => {
  error.value = null
  isLoading.value = true

  try {
    // Chuẩn hóa ngày sinh về dạng YYYY-MM-DD
    const formattedDob = user.value.dob ? user.value.dob.slice(0, 10) : null

    const payload = {
      fullName: user.value.fullName,
      phone: user.value.phone,
      address: user.value.address,
      avatar: user.value.avatar,
      dob: formattedDob,
    }

    const response = await axios.put('/api/user/profile', payload, {
      headers: {
        Authorization: `Bearer ${userStore.user.token}`
      }
    })

    // Cập nhật lại store nếu cần
    userStore.setUser({
      ...userStore.user,
      ...payload
    })

    alert('Cập nhật thông tin thành công!')
  } catch (err) {
    console.error('Lỗi khi cập nhật:', err)
    error.value = 'Đã xảy ra lỗi khi cập nhật thông tin người dùng.'
  } finally {
    isLoading.value = false
  }
}

</script>

<style scoped>
.profile-container {
  max-width: 1000px;
  margin: 0 auto;
  padding: 20px;
}

.profile-content {
  display: flex;
  gap: 40px;
  margin-top: 30px;
}

.avatar-section {
  flex: 1;
  max-width: 300px;
}

.info-section {
  flex: 2;
}

.avatar-wrapper {
  position: relative;
  width: 200px;
  height: 200px;
  margin: 0 auto;
}

.avatar {
  width: 100%;
  height: 100%;
  border-radius: 50%;
  object-fit: cover;
  border: 3px solid #42b983;
}

.avatar-upload {
  display: none;
}

.avatar-upload-label {
  display: block;
  margin-top: 10px;
  text-align: center;
  background: #42b983;
  color: white;
  padding: 8px 15px;
  border-radius: 4px;
  cursor: pointer;
  transition: background 0.3s;
}

.avatar-upload-label:hover {
  background: #3aa876;
}

.form-group {
  margin-bottom: 20px;
}

label {
  display: block;
  margin-bottom: 5px;
  font-weight: bold;
  color: #333;
}

input, textarea {
  width: 100%;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-size: 16px;
}

textarea {
  resize: vertical;
}

.save-btn {
  background: #42b983;
  color: white;
  border: none;
  padding: 12px 25px;
  border-radius: 4px;
  font-size: 16px;
  cursor: pointer;
  transition: background 0.3s;
}

.save-btn:hover {
  background: #3aa876;
}

@media (max-width: 768px) {
  .profile-content {
    flex-direction: column;
  }

  .avatar-section {
    max-width: 100%;
    margin-bottom: 20px;
  }
}
input[type="date"] {
  padding: 8px;
  border: 1px solid #ddd;
  border-radius: 4px;
}

</style>
