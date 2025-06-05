<template>
  <div class="edit-user-container">
    <div class="card">
      <div class="card-header d-flex justify-content-between align-items-center">
        <span>Thông tin Người dùng</span>
        <button class="btn btn-secondary btn-sm fixed-onright" @click="goBack">
          <i class="fas fa-arrow-left me-1"></i> Quay lại
        </button>
      </div>
      <div class="card-body">
        <div v-if="loadingUser" class="text-center text-muted">Đang tải thông tin người dùng...</div>
        <div v-else-if="!user || !user.userId" class="text-center text-danger">Không tìm thấy thông tin người dùng.</div>
        <form v-else @submit.prevent="handleSubmit">
          <!-- Thông tin cơ bản -->
          <div class="mb-3" v-for="(label, field) in fields" :key="field">
            <label :for="field" class="form-label">{{ label }}</label>
            <input
              v-if="field !== 'address'"
              type="text"
              class="form-control calenda"
              :id="field"
              v-model="user[field]"
              readonly
            />
            <textarea
              v-else
              class="form-control calenda"
              :id="field"
              v-model="user[field]"
              rows="3"
              readonly
            ></textarea>
          </div>

          <!-- Ngày tạo, cập nhật -->
          <div class="mb-3">
            <label class="form-label">Ngày tạo</label>
            <input type="text" class="form-control calenda" :value="formatDate(user.createdDate)" readonly />
          </div>
          <div class="mb-3">
            <label class="form-label">Ngày cập nhật</label>
            <input type="text" class="form-control calenda" :value="formatDate(user.updatedDate)" readonly />
          </div>

          <!-- Trạng thái hoạt động -->
          <div class="mb-3 form-check form-switch">
            <input class="form-check-input" type="checkbox" id="isActive" v-model="user.status" />
            <label class="form-check-label" for="isActive">Trạng thái (Hoạt động/Khóa)</label>
          </div>

          <button type="submit" class="btn btn-primary" :disabled="isSubmitting" style="background-color: #8b5cf6; border-color: #8b5cf6;">
            <span v-if="isSubmitting" class="spinner-border spinner-border-sm me-2" role="status" aria-hidden="true"></span>
            {{ isSubmitting ? 'Đang lưu...' : 'Lưu Thay đổi' }}
          </button>
        </form>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import { useRouter, useRoute } from 'vue-router';

const router = useRouter();
const route = useRoute();

const user = ref({
  userId: null,
  username: '',
  fullName: '',
  email: '',
  phone: '',
  gender: '',
  dob: '',
  address: '',
  role: '',
  status: true,
  createdDate: '',
  updatedDate: ''
});

const fields = {
  username: 'Tên đăng nhập',
  fullName: 'Họ và tên',
  email: 'Email',
  phone: 'Số điện thoại',
  gender: 'Giới tính',
  dob: 'Ngày sinh',
  address: 'Địa chỉ',
  role: 'Vai trò'
};

const loadingUser = ref(true);
const isSubmitting = ref(false);

// Lấy dữ liệu người dùng từ server
async function fetchUserData(userId) {
  loadingUser.value = true;
  try {
    const response = await axios.get(`http://localhost:8080/api/users/${userId}`);
    user.value = response.data;
    const ttt = response.data;
  } catch (error) {
    console.error(`Lỗi khi lấy thông tin người dùng ${userId}:`, error);
    alert('Không thể tải thông tin người dùng.');
  } finally {
    loadingUser.value = false;
  }
}

// Định dạng ngày
function formatDate(dateString) {
  if (!dateString) return 'Chưa cập nhật';
    const date = new Date(dateString.replace(' ', 'T'));
    return isNaN(date.getTime())
      ? dateString
      : date.toLocaleDateString('vi-VN', { year: 'numeric', month: '2-digit', day: '2-digit', hour: '2-digit', minute: '2-digit' });

}

// Submit thay đổi trạng thái
async function handleSubmit() {
  if (isSubmitting.value) return;
  isSubmitting.value = true;
  try {
    const response = await axios.put(
      `http://localhost:8080/api/users/${user.value.userId}/status?status=${user.value.status}`
    );
    console.log('User status updated successfully:', response.data);
    alert('Trạng thái người dùng đã được cập nhật thành công!');
    router.push('/admin/users');
  } catch (error) {
    console.error('Lỗi cập nhật trạng thái:', error);
    alert('Có lỗi xảy ra khi cập nhật trạng thái người dùng.');
  } finally {
    isSubmitting.value = false;
  }
}

// Quay lại
function goBack() {
  router.back();
}

// Lifecycle
onMounted(() => {
  const userId = route.params.userId;
  if (userId) {
    user.value.id = userId;
    fetchUserData(userId);
  } else {
    alert("Không tìm thấy ID người dùng.");
    router.push('/admin/users');
  }
});
</script>


<style scoped>
.position-relative {
  position: relative;
}
.position-absolute {
  position: absolute;
}
.top-0 {
  top: 0;
}
.end-0 {
  right: 0;
}




.form-check-input {
  cursor: pointer;
}

.btn-primary {
  background-color: #8b5cf6;
  border-color: #8b5cf6;
}

.btn-primary:hover {
  background-color: #7c3aed;
  border-color: #7c3aed;
}
</style>
