<template>
  <div class="admin-users-container container mt-4">
    <h1 class="mb-4 text-center text-primary">Quản lý Người dùng</h1>

    <div class="row mb-4 align-items-center">
      <div class="col-md-6">
        <input type="text" class="form-control border-0"
          placeholder="Tìm kiếm theo tên đăng nhập, họ tên hoặc email..." v-model="searchKeyword"
          @keyup.enter="!watchEnabled ? handleSearch() : null">
        <button v-if="!watchEnabled" class="btn btn-success btn-sm bg-success bor-success" type="button" @click="handleSearch">
          <i class="fas fa-search me-1"></i> Tìm kiếm
        </button>
      </div>
    </div>

    <div class="card shadow-sm rounded">
      <div class="card-header bg-light text-dark fw-bold">
        Danh sách Người dùng
      </div>
      <div class="card-body">
        <div v-if="loadingUsers" class="text-center py-4">
          <div class="spinner-border text-primary" role="status">
            <span class="visually-hidden">Đang tải...</span>
          </div>
          <p class="mt-2">Đang tải danh sách người dùng...</p>
        </div>

        <div v-else-if="paginatedUsers.length === 0 && !loadingUsers" class="text-center py-4">
          <p class="text-muted cl-note">Không tìm thấy người dùng nào.</p>
        </div>

        <div v-else>
          <div class="table-responsive">
            <table class="table table-striped table-hover table-bordered">
              <thead>
                <tr class="bg-light">
                  <th>Tên đăng nhập</th>
                  <th>Họ tên</th>
                  <th>Email</th>
                  <th>Trạng thái</th>
                  <th>Ngày tạo</th>
                  <th>Hành động</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="user in paginatedUsers" :key="user.userId">
                  <td>{{ user.username }}</td>
                  <td>{{ user.fullName || 'Chưa cập nhật' }}</td>
                  <td>{{ user.email }}</td>
                  <td>
                    <span
                      :class="{ 'badge bg-success': user.status, 'badge bg-danger': !user.status }">
                      {{ user.status ? 'Hoạt động' : 'Khóa' }}
                    </span>
                  </td>
                  <td>{{ formatDate(user.createdDate) }}</td>
                  <td>
                    <i class="fas fa-edit text-warning me-2 icon"
                      @click="viewUserDetail(user.userId)"
                      title="Xem chi tiết"></i>

                    <button v-if="user.status"
                      class="btn btn-sm btn-warning rounded-pill"
                      :disabled="isSubmitting"
                      @click="toggleUserStatus(user.userId, false)">
                      <i class="fas fa-lock me-1"></i> Khóa
                    </button>
                    <button v-else
                      class="btn btn-sm btn-success rounded-pill"
                      :disabled="isSubmitting"
                      @click="toggleUserStatus(user.userId, true)">
                      <i class="fas fa-unlock me-1"></i> Kích hoạt
                    </button>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>

          <nav aria-label="Page navigation mt-0">
            <ul class="pagination justify-content-center">
              <li class="page-item" :class="{ 'disabled': currentPage === 1 }">
                <a class="page-link" href="#" @click.prevent="changePage(currentPage - 1)">Previous</a>
              </li>
              <li class="page-item" v-for="page in totalPages" :key="page"
                :class="{ 'active': page === currentPage }">
                <a class="page-link" href="#" @click.prevent="changePage(page)">{{ page }}</a>
              </li>
              <li class="page-item" :class="{ 'disabled': currentPage === totalPages }">
                <a class="page-link" href="#" @click.prevent="changePage(currentPage + 1)">Next</a>
              </li>
            </ul>
          </nav>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, watch, computed } from 'vue';
import axios from 'axios';
import { useRouter } from 'vue-router';
const router = useRouter();

const watchEnabled = true;

const users = ref([]);
const loadingUsers = ref(true);
const selectedUser = ref(null);

const currentPage = ref(1);
const pageSize = ref(10);
const searchKeyword = ref('');

let searchTimer = null;

const paginatedUsers = computed(() => {
  const start = (currentPage.value - 1) * pageSize.value;
  const end = start + pageSize.value;
  return users.value.slice(start, end);
});

const totalPages = computed(() => {
  return Math.ceil(users.value.length / pageSize.value) || 1;
});

function changePage(page) {
  if (page >= 1 && page <= totalPages.value) {
    currentPage.value = page;
  }
}

async function fetchUsers() {
  loadingUsers.value = true;
  try {
    const response = await axios.get('http://localhost:8080/api/users/all-customer');
    const allUsers = response.data;

    const keyword = searchKeyword.value.toLowerCase();
    const filteredUsers = allUsers.filter(user =>
      user.username.toLowerCase().includes(keyword) ||
      user.email.toLowerCase().includes(keyword) ||
      (user.fullName && user.fullName.toLowerCase().includes(keyword))
    );

    users.value = filteredUsers;
    currentPage.value = 1;
  } catch (err) {
    console.error('Fetch users error:', err);
  } finally {
    loadingUsers.value = false;
  }
}

function viewUserDetail(userId) {
  router.push(`/admin/user/update/${userId}`);
}

const isSubmitting = ref(false);

async function toggleUserStatus(userId, newStatus) {
  if (isSubmitting.value) return;
  isSubmitting.value = true;

  try {
    // Gọi API với query param ?status=...
    const response = await axios.put(
      `http://localhost:8080/api/users/${userId}/status?status=${newStatus}`
    );

    if (response.status === 200) {
      const userIndex = users.value.findIndex(user => user.userId === userId);
      if (userIndex !== -1) {
        users.value[userIndex].status = newStatus;
      }
      if (selectedUser.value && selectedUser.value.userId === userId) {
        selectedUser.value.status = newStatus;
      }
      alert('Trạng thái người dùng đã được cập nhật thành công!');
      console.log(`User ${userId} status updated to ${newStatus ? 'active' : 'inactive'}`);
    }
  } catch (err) {
    console.error('Lỗi cập nhật trạng thái người dùng:', err);
    alert('Có lỗi xảy ra khi cập nhật trạng thái người dùng.');
  } finally {
    isSubmitting.value = false;
  }
}

function handleSearch() {
  if (!watchEnabled) {
    fetchUsers();
  }
}

function formatDate(dateString) {
  if (!dateString) return '';
  try {
    const date = new Date(dateString.replace(' ', 'T'));
    if (isNaN(date.getTime())) {
      return dateString;
    }
    const options = {
      year: 'numeric',
      month: '2-digit',
      day: '2-digit',
      hour: '2-digit',
      minute: '2-digit'
    };
    return date.toLocaleDateString('vi-VN', options);
  } catch (e) {
    console.error("Error formatting date:", e);
    return dateString;
  }
}

onMounted(() => {
  fetchUsers();

  if (watchEnabled) {
    watch(searchKeyword, () => {
      if (searchTimer) {
        clearTimeout(searchTimer);
      }
      searchTimer = setTimeout(() => {
        fetchUsers();
      }, 300);
    });
  }
});
</script>


<style scoped>
/* Tùy chỉnh CSS cho trang quản lý người dùng */
.admin-users-container {
    /* Thêm padding hoặc margin */
}

/* Tùy chỉnh màu tiêu đề */
.text-primary {
    color: #007bff !important;
}

/* Style cho badge trạng thái */
.badge {
    padding: 0.35em 0.65em;
    font-size: 0.75em;
    font-weight: 700;
    line-height: 1;
    text-align: center;
    white-space: nowrap;
    vertical-align: baseline;
    border-radius: 0.25rem;
}

.badge.bg-success {
    background-color: #28a745 !important;
    color: #fff;
}

.badge.bg-danger {
    background-color: #dc3545 !important;
    color: #fff;
}

.badge.bg-info {
    background-color: #17a2b8 !important;
    color: #fff;
}

/* Tùy chỉnh khoảng cách giữa các nút hành động */
.btn-sm {
    padding: .25rem .5rem;
    font-size: .875rem;
    line-height: 1.5;
    border-radius: .2rem;
}

.me-2 {
    margin-right: .5rem !important;
}

/* Thêm style cho input group và card */
.shadow-sm {
    box-shadow: 0 .125rem .25rem rgba(0, 0, 0, .075) !important;
}

.rounded {
    border-radius: .25rem !important;
}

.input-group .form-control.border-0 {
    border: none !important;
    box-shadow: none !important;
}

.input-group .btn {
    border-radius: 0 .25rem .25rem 0 !important;
}

.input-group.rounded .form-control {
    border-top-left-radius: .25rem !important;
    border-bottom-left-radius: .25rem !important;
}

.input-group.rounded .btn {
    border-top-right-radius: .25rem !important;
    border-bottom-right-radius: .25rem !important;
}

/* Style cho bảng */
.table {
    margin-bottom: 0;
}

.table-hover tbody tr:hover {
    background-color: rgba(0, 123, 255, 0.075);
}

/* Style cho header bảng */
.table thead th {
    vertical-align: bottom;
    border-bottom: 2px solid #dee2e6;
}

/* Style cho phân trang */
.pagination .page-link {
    color: #28a745;
}

.pagination .page-item.active .page-link {
    background-color: #28a745;
    border-color: #28a745;
    color: #fff;
}

.pagination .page-item.disabled .page-link {
    color: #6c757d;
    pointer-events: none;
    background-color: #fff;
    border-color: #dee2e6;
}

.pagination .page-link:hover {
    color: #1e7e34;
    background-color: #e9ecef;
    border-color: #dee2e6;
}

/* Style cho nút hành động bo tròn */
.rounded-pill {
    border-radius: 50rem !important;
}

/* Thêm style cho icon và nút */
.icon {
    cursor: pointer;
}

.btn i, .icon {
    margin-right: 5px;
}

.bg-success {
    background-color: #28a745 !important;
}

.bor-success {
    border-color: #28a745 !important;
}

/* Style cho modal */
.modal-body .form-label {
    color: #495057;
    margin-bottom: 0.25rem;
}

.modal-body .form-control-plaintext {
    padding: 0.375rem 0;
    margin-bottom: 0;
    font-size: 0.875rem;
    line-height: 1.5;
    color: #212529;
    background-color: transparent;
    border: solid transparent;
    border-width: 1px 0;
}

/* Responsive cho bảng với nhiều cột */
@media (max-width: 768px) {
    .table-responsive {
        font-size: 0.875rem;
    }

    .table th,
    .table td {
        padding: 0.5rem;
    }
}
</style>
