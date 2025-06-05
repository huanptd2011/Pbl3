<template>
  <div class="card">
    <div class="profile-container">
      <div class="profile-content">
        <!-- Phần avatar -->
        <div class="avatar-section">
          <div class="avatar-wrapper">
            <img :src="user.avatar || defaultAvatar" alt="Avatar" class="avatar" />
            <input type="file" id="avatar-upload" accept="image/*" @change="handleAvatarChange" class="avatar-upload" />
            <label for="avatar-upload" class="avatar-upload-label">
              <i class="fas fa-pen"></i>
            </label>
          </div>
        </div>

        <!-- Form thông tin -->
        <div class="card-body info-section">
          <form @submit.prevent="saveProfile">
            <!-- Các trường thông tin -->
            <div class="form-group">
              <label for="username" class="form-label">Tên đăng nhập</label>
              <input type="text" class="form-control calenda" id="username" v-model="user.username" disabled />
            </div>

            <div class="form-group">
              <label for="fullName" class="form-label">Họ và tên</label>
              <input type="text" class="form-control calenda" id="fullname" v-model="user.fullName" required />
            </div>

            <div class="form-group">
              <label for="email" class="form-label">Email</label>
              <input type="email" class="form-control calenda" id="email" v-model="user.email" disabled />
            </div>

            <div class="form-group">
              <label for="dob" class="form-label">Ngày sinh</label>
              <input type="date" class="form-control calenda" id="dob" v-model="user.dob" :max="maxDate" required />
            </div>

            <!-- <div class="form-group">
              <label for="gender" class="form-label">Giới tính</label>
              <select class="form-control" v-model="user.gender" required>
                <option value="Nam">Nam</option>
                <option value="Nữ">Nữ</option>

              </select>
            </div> -->

            <div class="form-group">
              <label for="phone" class="form-label">Số điện thoại</label>
              <input type="tel" class="form-control calenda" id="phone" v-model="user.phone" required />
            </div>

            <div class="form-group">
              <label for="address" class="form-label">Địa chỉ</label>
              <textarea id="address" class="form-control calenda" v-model="user.address" rows="3" required></textarea>
            </div>

            <div class="button-group">
              <button type="submit" class="btn btn-primary" :disabled="isLoading">
                {{ isLoading ? 'Đang lưu...' : 'Lưu thông tin' }}
              </button>
              <button type="button" class="btn btn-secondary" @click="showChangePassword = true">
                Đổi mật khẩu
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>

    <!-- Modal đổi mật khẩu -->
    <div v-if="showChangePassword" class="modal-overlay" @click="closeModal">
      <div class="modal-content" @click.stop>
        <div class="modal-header">
          <h5 class="modal-title">Đổi mật khẩu</h5>
          <button type="button" class="close-btn" @click="closeModal">×</button>
        </div>

        <form @submit.prevent="changePassword" class="password-form">
          <!-- Mật khẩu hiện tại -->
          <div class="form-group">
            <label class="form-label">Mật khẩu hiện tại</label>
            <div class="password-input-wrapper">
              <input
                :type="showCurrentPassword ? 'text' : 'password'"
                v-model="passwordForm.currentPassword"
                class="form-control password-input"
                :class="{ 'error': passwordErrors.currentPassword }"
                @input="clearPasswordError('currentPassword')"
                required
              />
              <button
                type="button"
                class="password-toggle"
                @click="togglePasswordVisibility('currentPassword')"
              >
                <i :class="showCurrentPassword ? 'fas fa-eye-slash' : 'fas fa-eye'"></i>
              </button>
            </div>
            <span v-if="passwordErrors.currentPassword" class="error-message">
              {{ passwordErrors.currentPassword }}
            </span>
          </div>

          <!-- Mật khẩu mới -->
          <div class="form-group">
            <label class="form-label">Mật khẩu mới</label>
            <div class="password-input-wrapper">
              <input
                :type="showNewPassword ? 'text' : 'password'"
                v-model="passwordForm.newPassword"
                class="form-control password-input"
                :class="{ 'error': passwordErrors.newPassword }"
                @input="clearPasswordError('newPassword')"
                required
              />
              <button
                type="button"
                class="password-toggle"
                @click="togglePasswordVisibility('newPassword')"
              >
                <i :class="showNewPassword ? 'fas fa-eye-slash' : 'fas fa-eye'"></i>
              </button>
            </div>
            <span v-if="passwordErrors.newPassword" class="error-message">
              {{ passwordErrors.newPassword }}
            </span>
          </div>

          <!-- Xác nhận mật khẩu -->
          <div class="form-group">
            <label class="form-label">Xác nhận mật khẩu</label>
            <div class="password-input-wrapper">
              <input
                :type="showConfirmPassword ? 'text' : 'password'"
                v-model="passwordForm.confirmPassword"
                class="form-control password-input"
                :class="{ 'error': passwordErrors.confirmPassword }"
                @input="clearPasswordError('confirmPassword')"
                required
              />
              <button
                type="button"
                class="password-toggle"
                @click="togglePasswordVisibility('confirmPassword')"
              >
                <i :class="showConfirmPassword ? 'fas fa-eye-slash' : 'fas fa-eye'"></i>
              </button>
            </div>
            <span v-if="passwordErrors.confirmPassword" class="error-message">
              {{ passwordErrors.confirmPassword }}
            </span>
          </div>

          <!-- Success message -->
          <div v-if="passwordSuccessMessage" class="success-message">
            {{ passwordSuccessMessage }}
          </div>

          <!-- Buttons -->
          <div class="modal-buttons">
            <button type="submit" class="btn btn-success" :disabled="isSubmittingPassword">
              {{ isSubmittingPassword ? 'Đang xử lý...' : 'Xác nhận' }}
            </button>
            <button type="button" class="btn btn-danger" @click="closeModal">
              Hủy
            </button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, computed } from 'vue'
import axios from 'axios'
import { useUserStore } from '@/stores/user'
import defaultAvatar from '@/assets/default-avatar.jpg'

const userStore = useUserStore()
const error = ref(null)
const isLoading = ref(false)

// Computed cho maxDate
const maxDate = computed(() => {
  const today = new Date()
  return today.toISOString().split('T')[0]
})

const user = reactive({
  username: userStore.user.username,
  fullName: userStore.user.fullName,
  gender: userStore.user.gender || 'Chưa cập nhật',
  email: userStore.user.email,
  phone: userStore.user.phone,
  address: userStore.user.address,
  avatar: userStore.user.avatar || defaultAvatar,
  dob: userStore.user.dob || null
})

// Password change states
const showChangePassword = ref(false)
const showCurrentPassword = ref(false)
const showNewPassword = ref(false)
const showConfirmPassword = ref(false)
const isSubmittingPassword = ref(false)
const passwordSuccessMessage = ref('')

const passwordForm = reactive({
  currentPassword: '',
  newPassword: '',
  confirmPassword: ''
})

const passwordErrors = reactive({
  currentPassword: '',
  newPassword: '',
  confirmPassword: ''
})

// Xử lý thay đổi avatar
const handleAvatarChange = async (event) => {
  const file = event.target.files[0]
  if (!file || !file.type.startsWith('image/')) {
    alert('Vui lòng chọn một tệp ảnh hợp lệ.')
    return
  }

  const formData = new FormData()
  formData.append('file', file)

  try {
    const response = await fetch('http://localhost:8080/api/upload-image', {
      method: 'POST',
      body: formData
    })

    if (!response.ok) {
      throw new Error('Tải ảnh lên thất bại.')
    }

    const data = await response.json()
    console.log('Tải ảnh thành công:', data)

    user.avatar = data.url
    userStore.setUser({
      ...userStore.user,
      avatar: data.url
    })
  } catch (err) {
    console.error('Lỗi khi tải ảnh:', err)
    alert('Lỗi khi tải ảnh lên. Vui lòng thử lại.')
  }
}

// Lưu thông tin người dùng
const saveProfile = async () => {
  error.value = null
  isLoading.value = true

  try {
    const dobFormatted = user.dob ? new Date(user.dob).toISOString().slice(0, 19) : null

    const payload = {
      fullName: user.fullName,
      phone: user.phone,
      gender: user.gender,
      address: user.address,
      dob: dobFormatted,
      email: user.email,
      avatarUrl: user.avatar || null
    }

    console.log('Đang gửi payload:', payload)

    const response = await axios.put(
      `http://localhost:8080/api/users/${userStore.user.userId}`,
      payload
    )
    console.log('Cập nhật thành công:', response.data)

    // Cập nhật lại store
    userStore.setUser({
      ...userStore.user,
      ...payload
    })

    alert('Cập nhật thông tin thành công!')
  } catch (err) {
    console.error('Lỗi khi cập nhật thông tin:', err)
    error.value = 'Đã xảy ra lỗi khi cập nhật thông tin người dùng.'
  } finally {
    isLoading.value = false
  }
}

// Password change functions
const togglePasswordVisibility = (field) => {
  switch(field) {
    case 'currentPassword':
      showCurrentPassword.value = !showCurrentPassword.value
      break
    case 'newPassword':
      showNewPassword.value = !showNewPassword.value
      break
    case 'confirmPassword':
      showConfirmPassword.value = !showConfirmPassword.value
      break
  }
}

const clearPasswordError = (field) => {
  passwordErrors[field] = ''
  passwordSuccessMessage.value = ''
}

const validatePasswordForm = () => {
  let isValid = true

  // Clear previous errors
  Object.keys(passwordErrors).forEach(key => passwordErrors[key] = '')

  // Current password validation
  if (!passwordForm.currentPassword.trim()) {
    passwordErrors.currentPassword = 'Vui lòng nhập mật khẩu hiện tại'
    isValid = false
  }

  // New password validation
  if (!passwordForm.newPassword.trim()) {
    passwordErrors.newPassword = 'Vui lòng nhập mật khẩu mới'
    isValid = false
  } else if (passwordForm.newPassword.length < 8) {
    passwordErrors.newPassword = 'Mật khẩu phải có ít nhất 8 ký tự'
    isValid = false
  }

  // Confirm password validation
  if (!passwordForm.confirmPassword.trim()) {
    passwordErrors.confirmPassword = 'Vui lòng xác nhận mật khẩu'
    isValid = false
  } else if (passwordForm.newPassword !== passwordForm.confirmPassword) {
    passwordErrors.confirmPassword = 'Mật khẩu xác nhận không khớp'
    isValid = false
  }

  return isValid
}

const resetPasswordForm = () => {
  passwordForm.currentPassword = ''
  passwordForm.newPassword = ''
  passwordForm.confirmPassword = ''
  Object.keys(passwordErrors).forEach(key => passwordErrors[key] = '')
  passwordSuccessMessage.value = ''
  showCurrentPassword.value = false
  showNewPassword.value = false
  showConfirmPassword.value = false
}

const changePassword = async () => {
  if (!validatePasswordForm()) return

  isSubmittingPassword.value = true
  passwordSuccessMessage.value = ''

  try {
    const response = await axios.put(
      `http://localhost:8080/api/users/${userStore.user.userId}/change-password`,
      {
        userId: userStore.user.userId,
        currentPassword: passwordForm.currentPassword,
        newPassword: passwordForm.newPassword,
        confirmNewPassword: passwordForm.confirmPassword
      }
    )

    if (response.status === 400) {
      passwordErrors.currentPassword = 'Mật khẩu hiện tại không đúng'
      return
    }

    passwordSuccessMessage.value = 'Đổi mật khẩu thành công!'
    setTimeout(() => {
      closeModal()
    }, 2000)

  } catch (error) {
    console.error('Change password error:', error)

    if (error.response) {
      const { status, data } = error.response

      if (status === 401) {
        passwordErrors.currentPassword = 'Mật khẩu hiện tại không đúng'
      } else if (status === 400 && data.errors) {
        // Handle server-side validation errors
        if (data.errors.currentPassword) {
          passwordErrors.currentPassword = data.errors.currentPassword
        }
        if (data.errors.newPassword) {
          passwordErrors.newPassword = data.errors.newPassword
        }
        if (data.errors.confirmNewPassword) {
          passwordErrors.confirmPassword = data.errors.confirmNewPassword
        }
      } else {
        alert('Có lỗi xảy ra khi đổi mật khẩu. Vui lòng thử lại sau.')
      }
    } else {
      alert('Không thể kết nối đến máy chủ. Vui lòng kiểm tra kết nối mạng.')
    }
  } finally {
    isSubmittingPassword.value = false
  }
}

const closeModal = () => {
  showChangePassword.value = false
  resetPasswordForm()
}
</script>

<style scoped>
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 999;
}

.modal-content {
  background: #1a2642;
  padding: 0;
  border-radius: 12px;
  width: 450px;
  max-width: 90%;
  max-height: 90vh;
  overflow-y: auto;
  box-shadow: 0 20px 40px rgba(0, 0, 0, 0.3);
}

.modal-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 20px 24px;
  border-bottom: 1px solid #2d3748;
}

.modal-title {
  color: #fff;
  margin: 0;
  font-size: 18px;
  font-weight: 600;
}

.close-btn {
  background: none;
  border: none;
  color: #aeb9e1;
  font-size: 24px;
  cursor: pointer;
  padding: 0;
  width: 30px;
  height: 30px;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 50%;
  transition: all 0.2s;
}

.close-btn:hover {
  background: #2d3748;
  color: #fff;
}

.password-form {
  padding: 24px;
}

.password-input-wrapper {
  position: relative;
}

.password-input {
  padding-right: 45px;
}

.password-toggle {
  position: absolute;
  right: 12px;
  top: 50%;
  transform: translateY(-50%);
  background: none;
  border: none;
  color: #aeb9e1;
  cursor: pointer;
  padding: 5px;
  border-radius: 4px;
  transition: color 0.2s;
}

.password-toggle:hover {
  color: #fff;
}

.error-message {
  color: #ef4444;
  font-size: 12px;
  margin-top: 5px;
  display: block;
}

.success-message {
  color: #10b981;
  font-size: 14px;
  margin-bottom: 15px;
  padding: 10px;
  background: rgba(16, 185, 129, 0.1);
  border-radius: 6px;
  border: 1px solid rgba(16, 185, 129, 0.2);
}

.modal-buttons {
  display: flex;
  gap: 12px;
  margin-top: 20px;
}

.profile-content {
  display: flex;
  gap: 40px;
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
  border: 3px solid #aeb9e1;
}

.avatar-upload {
  display: none;
}

.avatar-upload-label {
  position: absolute;
  bottom: 10px;
  right: 10px;
  width: 40px;
  height: 40px;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: #8b5cf6;
  color: white;
  border-radius: 50%;
  cursor: pointer;
  transition: background 0.3s;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.2);
}

.avatar-upload-label:hover {
  background-color: #7c3aed;
}

.form-group {
  margin-bottom: 20px;
}

.form-label {
  color: #aeb9e1;
  display: block;
  margin-bottom: 6px;
  font-weight: 500;
}

input,
textarea {
  width: 100%;
  padding: 12px;
  border: 1px solid #2d3748;
  border-radius: 6px;
  font-size: 14px;
  background: #1a2642;
  color: #fff;
  transition: border-color 0.2s;
}

input:focus,
textarea:focus {
  outline: none;
  border-color: #8b5cf6;
}

input.error {
  border-color: #ef4444;
}

textarea {
  resize: vertical;
  min-height: 80px;
}

.button-group {
  display: flex;
  gap: 12px;
  margin-top: 24px;
}

.btn {
  padding: 12px 24px;
  border: none;
  border-radius: 6px;
  font-size: 14px;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.2s;
  display: inline-flex;
  align-items: center;
  justify-content: center;
  min-width: 120px;
}

.btn:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}

.btn-primary {
  background: #8b5cf6;
  color: white;
}

.btn-primary:hover:not(:disabled) {
  background: #7c3aed;
}

.btn-secondary {
  background: #6b7280;
  color: white;
}

.btn-secondary:hover:not(:disabled) {
  background: #4b5563;
}

.btn-success {
  background: #10b981;
  color: white;
  flex: 1;
}

.btn-success:hover:not(:disabled) {
  background: #059669;
}

.btn-danger {
  background: #ef4444;
  color: white;
  flex: 1;
}

.btn-danger:hover:not(:disabled) {
  background: #dc2626;
}

@media (max-width: 768px) {
  .profile-content {
    flex-direction: column;
  }

  .avatar-section {
    max-width: 100%;
    margin-bottom: 20px;
  }

  .button-group {
    flex-direction: column;
  }

  .modal-content {
    width: 95%;
    margin: 20px;
  }

  .modal-buttons {
    flex-direction: column;
  }
}

input[type="date"] {
  padding: 8px 12px;
}

.card {
  background-color: #0b1739;
  border-radius: 12px;
  box-shadow: #0105114d 0px 8px 28px 0px;
  color: #fff;
  display: flex;
  flex-direction: column;
  gap: 15px;
  line-height: 24px;
  padding: 18px 18px 18px 20px;
}

input[type="date"].calenda::-webkit-calendar-picker-indicator,
input[type="month"].calenda::-webkit-calendar-picker-indicator {
  filter: invert(0.8);
  cursor: pointer;
}
</style>
