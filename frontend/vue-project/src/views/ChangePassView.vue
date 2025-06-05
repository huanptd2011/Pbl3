<template>
  <div class="change-password">
    <h2 class="title"><strong>Đổi Mật Khẩu</strong></h2>
    
    <form @submit.prevent="handleSubmit" class="form">
      <!-- Current Password -->
      <div class="form-group">
        <label for="currentPassword" class="label">Mật khẩu hiện tại</label>
        <div class="input-wrapper">
          <input
            :type="showCurrentPassword ? 'text' : 'password'"
            id="currentPassword"
            v-model="form.currentPassword"
            :class="['input', { 'is-error': errors.currentPassword }]"
            @input="clearError('currentPassword')"
          />
          <button
            type="button"
            class="toggle-password"
            @click="toggleShow('currentPassword')"
            :aria-label="showCurrentPassword ? 'Hide password' : 'Show password'"
          >
            <i :class="showCurrentPassword ? 'far fa-eye-slash' : 'far fa-eye'"></i>
          </button>
        </div>
        <div v-if="errors.currentPassword" class="error-message">
          {{ errors.currentPassword }}
        </div>
      </div>

      <!-- New Password -->
      <div class="form-group">
        <label for="newPassword" class="label">Mật khẩu mới</label>
        <div class="input-wrapper">
          <input
            :type="showNewPassword ? 'text' : 'password'"
            id="newPassword"
            v-model="form.newPassword"
            :class="['input', { 'is-error': errors.newPassword }]"
            @input="clearError('newPassword')"
          />
          <button
            type="button"
            class="toggle-password"
            @click="toggleShow('newPassword')"
            :aria-label="showNewPassword ? 'Hide password' : 'Show password'"
          >
            <i :class="showNewPassword ? 'far fa-eye-slash' : 'far fa-eye'"></i>
          </button>
        </div>
        <div v-if="errors.newPassword" class="error-message">
          {{ errors.newPassword }}
        </div>
      </div>

      <!-- Confirm Password -->
      <div class="form-group">
        <label for="confirmPassword" class="label">Xác nhận mật khẩu</label>
        <div class="input-wrapper">
          <input
            :type="showConfirmPassword ? 'text' : 'password'"
            id="confirmPassword"
            v-model="form.confirmPassword"
            :class="['input', { 'is-error': errors.confirmPassword }]"
            @input="clearError('confirmPassword')"
          />
          <button
            type="button"
            class="toggle-password"
            @click="toggleShow('confirmPassword')"
            :aria-label="showConfirmPassword ? 'Hide password' : 'Show password'"
          >
            <i :class="showConfirmPassword ? 'far fa-eye-slash' : 'far fa-eye'"></i>
          </button>
        </div>
        <div v-if="errors.confirmPassword" class="error-message">
          {{ errors.confirmPassword }}
        </div>
      </div>

      <!-- Form Actions -->
      <div class="form-actions">
        <button
          type="submit"
          class="submit-button"
          :disabled="isSubmitting"
        >
          <span v-if="isSubmitting">
            <i class="fas fa-spinner fa-spin"></i> Đang xử lý...
          </span>
          <span v-else>Đổi mật khẩu</span>
        </button>
        <button
          type="button"
          class="reset-button"
          @click="resetForm"
          :disabled="isSubmitting"
        >
          Nhập lại
        </button>
      </div>
    </form>

    <!-- Success Message -->
    <div v-if="successMessage" class="success-message">
      <i class="fas fa-check-circle"></i> {{ successMessage }}
    </div>
  </div>
</template>

<script>
import { ref, reactive } from 'vue';
import { useUserStore } from '@/stores/user';
import axios from 'axios';

export default {
  name: 'ChangePassword',
  setup() {
    const userStore = useUserStore();
    
    const form = reactive({
      currentPassword: '',
      newPassword: '',
      confirmPassword: ''
    });
    
    const errors = reactive({
      currentPassword: '',
      newPassword: '',
      confirmPassword: ''
    });
    
    const showCurrentPassword = ref(false);
    const showNewPassword = ref(false);
    const showConfirmPassword = ref(false);
    const isSubmitting = ref(false);
    const successMessage = ref('');
    
    const toggleShow = (field) => {
      switch(field) {
        case 'currentPassword':
          showCurrentPassword.value = !showCurrentPassword.value;
          break;
        case 'newPassword':
          showNewPassword.value = !showNewPassword.value;
          break;
        case 'confirmPassword':
          showConfirmPassword.value = !showConfirmPassword.value;
          break;
      }
    };
    
    const clearError = (field) => {
      errors[field] = '';
    };
    
    const validateForm = () => {
      let isValid = true;
      
      // Clear previous errors
      Object.keys(errors).forEach(key => errors[key] = '');
      
      // Current password validation
      if (!form.currentPassword.trim()) {
        errors.currentPassword = 'Vui lòng nhập mật khẩu hiện tại';
        isValid = false;
      } 
      
      // New password validation
      if (!form.newPassword.trim()) {
        errors.newPassword = 'Vui lòng nhập mật khẩu mới';
        isValid = false;
      } else if (form.newPassword.length < 8) {
        errors.newPassword = 'Mật khẩu phải có ít nhất 8 ký tự';
        isValid = false;
      }
      
      // Confirm password validation
      if (!form.confirmPassword.trim()) {
        errors.confirmPassword = 'Vui lòng xác nhận mật khẩu';
        isValid = false;
      } else if (form.newPassword !== form.confirmPassword) {
        errors.confirmPassword = 'Mật khẩu xác nhận không khớp';
        isValid = false;
      }
      
      return isValid;
    };
    
    const resetForm = () => {
      form.currentPassword = '';
      form.newPassword = '';
      form.confirmPassword = '';
      Object.keys(errors).forEach(key => errors[key] = '');
      successMessage.value = '';
    };
    
    const handleSubmit = async () => {
      if (!validateForm()) return;
      
      isSubmitting.value = true;
      successMessage.value = '';
      
      try {
        const response = await axios.put(
          `http://localhost:8080/api/users/${userStore.user.userId}/change-password`,
          {
            userId: userStore.user.userId,
            currentPassword: form.currentPassword,
            newPassword: form.newPassword,
            confirmNewPassword: form.confirmPassword
          }
        );

        if (response.status === 400) {
          return errors.currentPassword = 'Mật khẩu hiện tại không đúng';
        }
        
        successMessage.value = 'Đổi mật khẩu thành công!';
        resetForm();
      } catch (error) {
        console.error('Change password error:', error);
        
        if (error.response) {
          const { status, data } = error.response;
          
          if (status === 401) {
            errors.currentPassword = 'Mật khẩu hiện tại không đúng';
          } else if (status === 400 && data.errors) {
            // Handle server-side validation errors
            if (data.errors.currentPassword) {
              errors.currentPassword = data.errors.currentPassword;
            }
            if (data.errors.newPassword) {
              errors.newPassword = data.errors.newPassword;
            }
            if (data.errors.confirmNewPassword) {
              errors.confirmPassword = data.errors.confirmNewPassword;
            }
          } else {
            alert('Có lỗi xảy ra khi đổi mật khẩu. Vui lòng thử lại sau.');
          }
        } else {
          alert('Không thể kết nối đến máy chủ. Vui lòng kiểm tra kết nối mạng.');
        }
      } finally {
        isSubmitting.value = false;
      }
    };
    
    return {
      form,
      errors,
      showCurrentPassword,
      showNewPassword,
      showConfirmPassword,
      isSubmitting,
      successMessage,
      toggleShow,
      clearError,
      resetForm,
      handleSubmit
    };
  }
};
</script>

<style scoped>
.change-password {
  max-width: 500px;
  margin: 50px auto;
  padding: 2rem;
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 10px #23547a77;
}

.title {
  text-align: center;
  color: #2c3e50;
  margin-bottom: 1.5rem;
}

.form {
  display: flex;
  flex-direction: column;
  gap: 1.25rem;
}

.form-group {
  display: flex;
  flex-direction: column;
}

.label {
  font-weight: 500;
  color: #666;
}

.input-wrapper {
  position: relative;
}

.input {
  width: 100%;
    border-radius: 0;
  border-bottom: 1px solid #ced4da;
  border-top: none;
  border-left: none;
  border-right: none;
  padding: 0.5rem;
  background-color: #fff !important;
}

.input:focus {
  outline: none;
   border-color: #000;
  box-shadow: none;
}

.input.is-error {
  border-color: #f56565;
}

.toggle-password {
  position: absolute;
  right: 0.75rem;
  top: 50%;
  transform: translateY(-50%);
  background: none;
  border: none;
  color: #718096;
  cursor: pointer;
  padding: 0.25rem;
}

.error-message {
  color: #f56565;
  font-size: 0.875rem;
  margin-top: 0.25rem;
}

.form-actions {
  display: flex;
  gap: 1rem;
  margin-top: 1rem;
}

.submit-button,
.reset-button {
  flex: 1;
  padding: 0.75rem;
  border: none;
  border-radius: 6px;
  font-size: 1rem;
  font-weight: 500;
  cursor: pointer;
  transition: background-color 0.2s;
}

.submit-button {
  background-color: var(--submit-button);
  color: white;
}

.submit-button:hover:not(:disabled) {
  background-color: var(--submit-button-hover);
}

.submit-button:disabled {
  background-color: #a0aec0;
  cursor: not-allowed;
}

.reset-button {
  background-color: #e2e8f0;
  color: #4a5568;
}

.reset-button:hover:not(:disabled) {
  background-color: #cbd5e0;
}

.success-message {
  margin-top: 1.5rem;
  padding: 0.75rem 1rem;
  background-color: #f0fff4;
  color: #38a169;
  border-radius: 6px;
  display: flex;
  align-items: center;
  gap: 0.5rem;
}

.fa-spinner {
  margin-right: 0.5rem;
}
</style>