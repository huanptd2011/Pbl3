<template>
  <div class="admin-users-container  ">
    <div class="card ">
      <div class="card-header d-flex justify-content-between align-items-center">
        <span>
          Danh sách Người dùng
        </span>
        <div class="d-flex align-items-center">
          <input type="text" class="form-control form-control-sm calenda " style="width: 500px;"
            placeholder="Tìm kiếm theo tên đăng nhập, họ tên hoặc email..." v-model="searchKeyword"
            @keyup.enter="!watchEnabled ? handleSearch() : null">


        </div>
      </div>
      <div class="card-body">
        <div v-if="loadingUsers" class="text-center py-4">
          <div class="spinner-border text-primary" role="status">
            <span class="visually-hidden">Đang tải...</span>
          </div>
        </div>
        <table class="table table-striped table-bordered custom-orders-table">
          <thead>
            <tr>
              <th>Tên đăng nhập</th>
              <th>Họ tên</th>
              <th>Email</th>
              <th>Trạng thái</th>
              <th>Ngày tạo</th>
              <th>Hành động</th>
            </tr>
          </thead>
          <tbody>
            <template v-if="paginatedUsers.length > 0">
              <tr v-for="user in paginatedUsers" :key="user.userId">
                <td>{{ user.username }}</td>
                <td>{{ user.fullName || 'Chưa cập nhật' }}</td>
                <td>{{ user.email }}</td>
                <td>
                  <span :class="{ 'badge bg-success': user.status, 'badge bg-danger': !user.status }">
                    {{ user.status ? 'Hoạt động' : 'Khóa' }}
                  </span>
                </td>
                <td>{{ formatDate(user.createdDate) }}</td>
                <td>
                  <i class="fas fa-edit text-warning me-2 icon" @click="viewUserDetail(user.userId)"
                    title="Xem chi tiết"></i>
                    <i v-if="user.status" class="fas fa-lock icon"   :disabled="isSubmitting"
                    @click="toggleUserStatus(user.userId, false)" ></i>
                    <i class="fas fa-unlock icon" v-else  :disabled="isSubmitting"
                    @click="toggleUserStatus(user.userId, true)"></i>
                </td>
              </tr>
            </template>
            <tr v-else-if="paginatedUsers.length === 0 && !loadingOrders">
              <td colspan="7" class="text-center cl-note">Không tìm thấy người dùng nào.</td>
            </tr>

            <template v-else>
              <tr v-for="n in pageSize" :key="'placeholder-' + n">
                <td>...</td>
                <td>...</td>
                <td>...</td>
                <td>...</td>
                <td>...</td>
                <td>...</td>

              </tr>
            </template>
          </tbody>
        </table>


        <nav aria-label="Page navigation mt-0">
          <ul class="pagination justify-content-center">
            <li class="page-item" :class="{ 'disabled': currentPage === 1 }">
              <a class="page-link" href="#" @click.prevent="changePage(currentPage - 1)">Previous</a>
            </li>
            <li class="page-item" v-for="page in totalPages" :key="page" :class="{ 'active': page === currentPage }">
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
const pageSize = ref(6);
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

.card-header {
  background-color: #0b1739;
  border-bottom: 1px solid #1e293b;
}

.card-body {
  position: relative;
  min-height: 600px;
}

.custom-orders-table tbody tr {
  border-color: #1e293b;
  height: 60px;
  vertical-align: middle;
}

/* Tùy chỉnh màu tiêu đề */
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
  background-color: #10b9812e !important;
  color: #139f81;
  border: 0.2px solid #139f81;
}

.badge.bg-danger {
  background-color: #e2232330 !important;
  color: #ef4444;
  border: 0.2px solid #ef4444;
}

.badge.bg-info {
  background-color: #3b82f62e !important;
  color: #2563eb;
  border: 0.2px solid #2563eb;
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



.input-group .btn {
  border-radius: 0 .25rem .25rem 0 !important;
}

.input-group.rounded {
  border-top-left-radius: .25rem !important;
  border-bottom-left-radius: .25rem !important;
}

.input-group.rounded .btn {
  border-top-right-radius: .25rem !important;
  border-bottom-right-radius: .25rem !important;
}


.table-hover tbody tr:hover {
  background-color: rgba(0, 123, 255, 0.075);
}

input::placeholder {

  color: #aeb9e1;
}

input:focus {
  border-color: #aeb9e1;
  box-shadow: 0 0 0 0.2rem rgba(203, 60, 255, 0.25);
}

/* Phân trang */
.pagination {
  display: flex;
  justify-content: center;
  position: absolute;
  bottom: 0;
  left: 40%;
}

.pagination .page-item .page-link {
  background-color: #161e39;
  border-color: #1e293b;
  color: #aeb9e1;
}

.pagination .page-item.active .page-link {
  background-color: rgba(203, 60, 255);
  color: #fff;
}

.pagination .page-item.disabled .page-link {
  color: #555;
  pointer-events: none;
  background-color: #161e39;
  border-color: #1e293b;
}

.pagination .page-item .page-link:hover:not(.disabled) {
  background-color: rgba(203, 60, 255);
}

.pagination .page-item .page-link:focus {
  outline: none;
}


/* Style cho nút hành động bo tròn */
.rounded-pill {
  border-radius: 50rem !important;
}

/* Thêm style cho icon và nút */
.icon {
  cursor: pointer;
}

 
.icon {
  margin-right: 5px;
}

.bg-success {
  background-color: #28a745 !important;
}

.bor-success {
  border-color: #28a745 !important;
}

.btn-success{
  background-color: #28a745 !important;
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

}

.custom-orders-table tbody tr {
  border-color: #1e293b;
  height: 70px;
  vertical-align: middle;
}

.cl-note {
    color: #aeb9e1;
    font-style: italic;
}
</style>
