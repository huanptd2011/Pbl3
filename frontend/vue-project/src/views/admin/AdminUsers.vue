<!-- 

<template>
  <div class="admin-users-container container mt-4">
    <h1 class="mb-4 text-center text-primary">Quản lý Người dùng</h1>

    <div class="row mb-4 align-items-center">
      <div class="col-md-6">
        <div class="input-group shadow-sm rounded-pill overflow-hidden"> {/* Thêm rounded-pill và overflow-hidden */}
          <input
            type="text"
            class="form-control border-0 ps-3" {/* Thêm ps-3 (padding-left) */}
            placeholder="Tìm kiếm theo tên đăng nhập hoặc email..."
            v-model="searchKeyword"
            {{!watchEnabled ? '@keyup.enter="handleSearch"' : ''}} {/* Giữ keyup.enter chỉ khi watch không bật */}
          >
          {{!watchEnabled ? `
          <button class="btn btn-primary" type="button" @click="handleSearch">
            <i class="bi bi-search"></i> Tìm kiếm
          </button>
          ` : ''}} {/* Giữ nút tìm kiếm chỉ khi watch không bật */}
        </div>
      </div>
      <div class="col-md-6 text-end">
        <button class="btn btn-success shadow-sm rounded-pill"> {/* Nút bo tròn */}
           <i class="bi bi-plus-circle"></i> Thêm Người dùng Mới
        </button>
      </div>
    </div>

    <div class="card shadow-sm rounded"> {/* Thẻ card với đổ bóng nhẹ và bo tròn */}
      <div class="card-header bg-light text-dark fw-bold"> {/* Header card sáng màu và chữ đậm */}
        Danh sách Người dùng
      </div>
      <div class="card-body">
        <div v-if="loadingUsers" class="text-center py-4">
          <div class="spinner-border text-primary" role="status">
            <span class="visually-hidden">Đang tải...</span>
          </div>
          <p class="mt-2 text-muted">Đang tải danh sách người dùng...</p> {/* Chữ màu xám */}
        </div>
        <div v-else-if="users.length === 0 && !loadingUsers" class="text-center py-4">
          <p class="text-muted">Không có người dùng nào trong hệ thống.</p> {/* Chữ màu xám */}
        </div>
        <div v-else>
          <div class="table-responsive"> {/* Bọc bảng trong div responsive */}
            <table class="table table-striped table-hover table-bordered"> {/* Thêm table-hover để có hiệu ứng rê chuột */}
              <thead>
                <tr class="bg-light"> {/* Thêm màu nền cho header bảng */}
                  <th>ID</th>
                  <th>Tên đăng nhập</th>
                  <th>Email</th>
                  <th>Vai trò</th>
                  <th>Trạng thái</th>
                  <th>Ngày tạo</th>
                  <th>Hành động</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="user in users" :key="user.userId">
                  <td>{{ user.userId }}</td>
                  <td>{{ user.username }}</td>
                  <td>{{ user.email }}</td>
                  <td>{{ user.role }}</td>
                  <td>
                    <span :class="{'badge bg-success': user.isActive, 'badge bg-danger': !user.isActive}">
                      {{ user.isActive ? 'Hoạt động' : 'Khóa' }}
                    </span>
                  </td>
                  <td>{{ formatDate(user.createdAt) }}</td>
                  <td>
                    {/* Nút Sửa */}
                    <button class="btn btn-sm btn-outline-warning me-2 rounded-pill"> {/* Nút viền vàng bo tròn */}
                       <i class="bi bi-pencil-square"></i> Sửa
                    </button>
                    {/* Nút Xóa */}
                    <button class="btn btn-sm btn-outline-danger rounded-pill"> {/* Nút viền đỏ bo tròn */}
                       <i class="bi bi-trash"></i> Xóa
                    </button>
                  </td>
                </tr>
              </tbody>
            </table>
          </div> {/* Kết thúc div responsive */}

          <div class="d-flex justify-content-center mt-3">
              <nav aria-label="Page navigation example">
                <ul class="pagination shadow-sm rounded"> {/* Thêm shadow và bo tròn cho phân trang */}
                  {/* Logic phân trang thực tế sẽ được thêm vào đây */}
                   <li class="page-item disabled"><a class="page-link" href="#">Trước</a></li>
                   <li class="page-item active"><a class="page-link" href="#">1</a></li>
                   <li class="page-item disabled"><a class="page-link" href="#">Sau</a></li>
                </ul>
              </nav>
          </div>
        </div>
      </div>
    </div>

  </div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue'; // Import watch
import axios from 'axios'; // Uncomment để sử dụng Axios
import { useUserStore } from '@/stores/user'; // Uncomment để lấy token

// Cờ bật/tắt watch (chỉ dùng để minh họa)
const watchEnabled = true; // Đặt true để bật tìm kiếm tức thời

// Định nghĩa biến state cho danh sách người dùng và trạng thái loading
const users = ref([]);
const loadingUsers = ref(true);

// Định nghĩa biến state cho từ khóa tìm kiếm
const searchKeyword = ref('');

// Biến để lưu trữ timer cho debounce
let searchTimer = null;

// --- DỮ LIỆU GIẢ (MOCK DATA) ---
// Đã xóa dữ liệu giả ở đây, sẽ lấy từ API
// const mockUsers = [ ... ];
// ---------------------------------

// Hàm gọi API lấy danh sách người dùng
async function fetchUsers() {
  loadingUsers.value = true; // Bắt đầu loading
  try {
    // Gọi API backend để lấy danh sách người dùng
    // Sử dụng endpoint đã thống nhất: GET /api/admin/users
    // Thêm header Authorization với token để xác thực
    const response = await axios.get('http://localhost:8080/api/admin/users', {
      headers: {
        Authorization: `Bearer ${userStore.token}` // Gửi token từ store
      },
      params: {
        // Thêm tham số keyword nếu searchKeyword có giá trị
        keyword: searchKeyword.value || undefined, // Gửi undefined nếu rỗng để backend bỏ qua tham số
        // Thêm params cho phân trang, sắp xếp ở đây sau
        // page: 0, // Ví dụ: trang đầu tiên
        // size: 10, // Ví dụ: 10 người dùng mỗi trang
        // sortBy: 'createdAt',
        // sortDir: 'desc'
      }
    });

    // Giả định backend trả về dữ liệu người dùng trong response.data.content (theo cấu trúc phân trang)
    // Nếu backend trả về trực tiếp mảng người dùng trong response.data, hãy sửa lại dòng dưới
    users.value = response.data.content;

  } catch (error) {
    console.error('Error fetching users:', error);
    // Xử lý lỗi (ví dụ: hiển thị thông báo lỗi cho người dùng)
    // Có thể kiểm tra error.response để xem chi tiết lỗi từ backend
  } finally {
    loadingUsers.value = false; // Kết thúc loading dù thành công hay thất bại
  }
}

// Hàm xử lý khi nhấn nút Tìm kiếm hoặc Enter trong input (chỉ dùng khi watchEnabled = false)
function handleSearch() {
  if (!watchEnabled) {
     fetchUsers();
  }
}


// Hàm định dạng ngày tháng (tái sử dụng từ Dashboard)
function formatDate(dateString) {
    if (!dateString) return '';
    const options = { year: 'numeric', month: 'numeric', day: 'numeric', hour: 'numeric', minute: 'numeric' };
     try {
        const date = new Date(dateString);
        if (isNaN(date.getTime())) {
            return dateString;
        }
         return date.toLocaleDateString('vi-VN', options);
    } catch (e) {
        console.error("Error formatting date:", e);
        return dateString;
    }
}


// Lifecycle hook: Chạy khi component được mount
onMounted(() => {
  console.log('Admin Users View mounted');
  fetchUsers(); // Gọi hàm fetch users (gọi API thực tế) khi component load lần đầu

  // --- Watch searchKeyword để tìm kiếm tức thời ---
  if (watchEnabled) {
      watch(searchKeyword, (newValue, oldValue) => {
          // Clear timer cũ nếu có
          if (searchTimer) {
              clearTimeout(searchTimer);
          }
          // Đặt timer mới để gọi fetchUsers sau 300ms
          searchTimer = setTimeout(() => {
              fetchUsers();
          }, 300); // Debounce 300ms
      });
  }
  // ---------------------------------------------
});

</script>

<style scoped>
/* Tùy chỉnh CSS cho trang quản lý người dùng */
.admin-users-container {
  /* Thêm padding hoặc margin */
}

/* Tùy chỉnh màu tiêu đề */
.text-primary {
    color: #007bff !important; /* Màu xanh dương của Bootstrap */
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
    box-shadow: 0 .125rem .25rem rgba(0,0,0,.075)!important;
}

.rounded {
    border-radius: .25rem!important;
}

.input-group .form-control.border-0 {
    border: none !important;
    box-shadow: none !important; /* Loại bỏ shadow mặc định của input */
}

.input-group .btn {
    border-radius: 0 .25rem .25rem 0 !important; /* Bo tròn chỉ góc phải */
}

.input-group.rounded .form-control {
     border-top-left-radius: .25rem!important;
     border-bottom-left-radius: .25rem!important;
}

.input-group.rounded .btn {
     border-top-right-radius: .25rem!important;
     border-bottom-right-radius: .25rem!important;
}


/* Style cho bảng */
.table {
    margin-bottom: 0; /* Loại bỏ margin dưới cùng mặc định của bảng */
}

.table-hover tbody tr:hover {
    background-color: rgba(0, 123, 255, 0.075); /* Hiệu ứng hover nhẹ nhàng */
}

/* Style cho header bảng */
.table thead th {
    vertical-align: bottom;
    border-bottom: 2px solid #dee2e6;
}


/* Style cho phân trang */
.pagination .page-link {
    color: #007bff;
}

.pagination .page-item.active .page-link {
    background-color: #007bff;
    border-color: #007bff;
    color: #fff;
}

.pagination .page-link:hover {
    color: #0056b3;
}

/* Style cho nút hành động bo tròn */
.rounded-pill {
    border-radius: 50rem !important;
}

/* Thêm icon cho nút */
.btn i {
    margin-right: 5px;
}
</style>

   -->
<template>
    <div class="admin-users-container container mt-4">
        <h1 class="mb-4 text-center text-primary">Quản lý Người dùng</h1>

        <div class="row mb-4 align-items-center">
            <div class="col-md-6">
                <div class="input-group shadow-sm rounded">
                    <input type="text" class="form-control border-0"
                        placeholder="Tìm kiếm theo tên đăng nhập hoặc email..." v-model="searchKeyword"
                        @keyup.enter="!watchEnabled ? handleSearch() : null">
                    <button v-if="!watchEnabled" class="btn btn-primary" type="button" @click="handleSearch">
                        <i class="bi bi-search"></i> Tìm kiếm
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
                    <div v-else-if="users.length === 0 && !loadingUsers" class="text-center py-4">
                        <p class="text-muted">Không có người dùng nào trong hệ thống.</p>
                    </div>
                    <div v-else>
                        <div class="table-responsive"> 
                            <table class="table table-striped table-hover table-bordered"> 
                                <thead>
                                    <tr class="bg-light">
                                        <!-- <th>ID</th> -->
                                        <th>Tên đăng nhập</th>
                                        <th>Email</th>
                                        <th>Vai trò</th>
                                        <th>Trạng thái</th>
                                        <th>Ngày tạo</th>
                                        <th>Hành động</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr v-for="user in users" :key="user.userId">
                                        <!-- <td>{{ user.userId }}</td> -->
                                        <td>{{ user.username }}</td>
                                        <td>{{ user.email }}</td>
                                        <td>{{ user.role }}</td>
                                        <td>
                                            <span
                                                :class="{ 'badge bg-success': user.isActive, 'badge bg-danger': !user.isActive }">
                                                {{ user.isActive ? 'Hoạt động' : 'Khóa' }}
                                            </span>
                                        </td>
                                        <td>{{ formatDate(user.createdAt) }}</td>
                                        <td>
                                            <button v-if="user.isActive" class="btn btn-sm btn-warning me-2 rounded-pill"> 
                                                <i class="bi bi-pencil-square"></i> Khóa
                                            </button>
                                            <button v-else class="btn btn-sm btn-danger rounded-pill"> 
                                                <i class="bi bi-trash"></i> Kích hoat 
                                            </button>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </div> 

                        <div class="d-flex justify-content-center mt-3">
                            <nav aria-label="Page navigation example">
                                <ul class="pagination shadow-sm rounded">
                                    <li class="page-item disabled"><a class="page-link" href="#">Trước</a></li>
                                    <li class="page-item active"><a class="page-link" href="#">1</a></li>

                                    <li class="page-item disabled"><a class="page-link" href="#">2</a></li>
                                    <li class="page-item disabled"><a class="page-link" href="#">3</a></li>
                                    <li class="page-item disabled"><a class="page-link" href="#">Sau</a></li>
                                </ul>
                            </nav>
                        </div>
                    </div>
                </div>
            </div>

        </div>
    </div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue'; // Import watch
// import axios from 'axios'; // Không dùng Axios khi dùng dữ liệu giả
// import { useUserStore } from '@/stores/user'; // Không cần thiết khi dùng dữ liệu giả

// Cờ bật/tắt watch (chỉ dùng để minh họa)
const watchEnabled = true; // Đặt true để bật tìm kiếm tức thời

// Định nghĩa biến state cho danh sách người dùng và trạng thái loading
const users = ref([]);
const loadingUsers = ref(true);

// Định nghĩa biến state cho từ khóa tìm kiếm
const searchKeyword = ref('');

// Biến để lưu trữ timer cho debounce
let searchTimer = null;

// --- DỮ LIỆU GIẢ (MOCK DATA) ---
const mockUsers = [
    { userId: 1, username: 'admin', email: 'admin@example.com', role: 'ADMIN', createdAt: '2023-01-15T10:00:00Z', updatedAt: '2023-10-27T14:30:00Z', isActive: true },
    { userId: 2, username: 'nguyenvana', email: 'nguyenvana@example.com', role: 'CUSTOMER', createdAt: '2023-02-20T11:00:00Z', updatedAt: '2023-02-20T11:00:00Z', isActive: true },
    { userId: 3, username: 'tranb', email: 'tranb@example.com', role: 'CUSTOMER', createdAt: '2023-03-10T15:00:00Z', updatedAt: '2023-10-25T09:00:00Z', isActive: false },
    { userId: 4, username: 'levanc', email: 'levanc@example.com', role: 'CUSTOMER', createdAt: '2023-04-01T08:00:00Z', updatedAt: '2023-04-01T08:00:00Z', isActive: true },
    { userId: 5, username: 'phamthid', email: 'phamthid@example.com', role: 'CUSTOMER', createdAt: '2023-05-05T12:00:00Z', updatedAt: '2023-05-05T12:00:00Z', isActive: true },
    { userId: 6, username: 'hoange', email: 'hoange@example.com', role: 'CUSTOMER', createdAt: '2023-06-10T14:00:00Z', updatedAt: '2023-06-10T14:00:00Z', isActive: true },
    { userId: 7, username: 'nguyenf', email: 'nguyenf@example.com', role: 'CUSTOMER', createdAt: '2023-07-01T09:00:00Z', updatedAt: '2023-07-01T09:00:00Z', isActive: true },
    { userId: 8, username: 'vantg', email: 'vantg@example.com', role: 'CUSTOMER', createdAt: '2023-08-18T16:00:00Z', updatedAt: '2023-08-18T16:00:00Z', isActive: false },
    { userId: 9, username: 'thih', email: 'thih@example.com', role: 'CUSTOMER', createdAt: '2023-09-22T10:00:00Z', updatedAt: '2023-09-22T10:00:00Z', isActive: true },
    { userId: 10, username: 'leik', email: 'leik@example.com', role: 'CUSTOMER', createdAt: '2023-10-05T11:00:00Z', updatedAt: '2023-10-05T11:00:00Z', isActive: true },
    { userId: 11, username: 'phaml', email: 'phaml@example.com', role: 'CUSTOMER', createdAt: '2023-10-10T13:00:00Z', updatedAt: '2023-10-10T13:00:00Z', isActive: true },
    { userId: 12, username: 'hoangm', email: 'hoangm@example.com', role: 'CUSTOMER', createdAt: '2023-10-15T15:00:00Z', updatedAt: '2023-10-15T15:00:00Z', isActive: true },
    { userId: 13, username: 'nguyenn', email: 'nguyenn@example.com', role: 'CUSTOMER', createdAt: '2023-10-20T17:00:00Z', updatedAt: '2023-10-20T17:00:00Z', isActive: false },
    { userId: 14, username: 'vanto', email: 'vanto@example.com', role: 'CUSTOMER', createdAt: '2023-10-25T09:00:00Z', updatedAt: '2023-10-25T09:00:00Z', isActive: true },
    { userId: 15, username: 'thip', email: 'thip@example.com', role: 'CUSTOMER', createdAt: '2023-10-26T10:00:00Z', updatedAt: '2023-10-26T10:00:00Z', isActive: true },
    { userId: 16, username: 'leq', email: 'leq@example.com', role: 'CUSTOMER', createdAt: '2023-10-27T11:00:00Z', updatedAt: '2023-10-27T11:00:00Z', isActive: true },
    { userId: 17, username: 'phamr', email: 'phamr@example.com', role: 'CUSTOMER', createdAt: '2023-10-28T12:00:00Z', updatedAt: '2023-10-28T12:00:00Z', isActive: true },
    { userId: 18, username: 'hoangs', email: 'hoangs@example.com', role: 'CUSTOMER', createdAt: '2023-10-29T13:00:00Z', updatedAt: '2023-10-29T13:00:00Z', isActive: true },
    { userId: 19, username: 'nguyent', email: 'nguyent@example.com', role: 'CUSTOMER', createdAt: '2023-10-30T14:00:00Z', updatedAt: '2023-10-30T14:00:00Z', isActive: false },
    { userId: 20, username: 'vantu', email: 'vantu@example.com', role: 'CUSTOMER', createdAt: '2023-10-31T15:00:00Z', updatedAt: '2023-10-31T15:00:00Z', isActive: true },
    { userId: 21, username: 'thiv', email: 'thiv@example.com', role: 'CUSTOMER', createdAt: '2023-11-01T16:00:00Z', updatedAt: '2023-11-01T16:00:00Z', isActive: true },
    { userId: 22, username: 'lew', email: 'lew@example.com', role: 'CUSTOMER', createdAt: '2023-11-02T17:00:00Z', updatedAt: '2023-11-02T17:00:00Z', isActive: true },
    { userId: 23, username: 'phamx', email: 'phamx@example.com', role: 'CUSTOMER', createdAt: '2023-11-03T18:00:00Z', updatedAt: '2023-11-03T18:00:00Z', isActive: true },
    { userId: 24, username: 'hoangy', email: 'hoangy@example.com', role: 'CUSTOMER', createdAt: '2023-11-04T19:00:00Z', updatedAt: '2023-11-04T19:00:00Z', isActive: true },
    { userId: 25, username: 'nguyenz', email: 'nguyenz@example.com', role: 'CUSTOMER', createdAt: '2023-11-05T20:00:00Z', updatedAt: '2023-11-05T20:00:00Z', isActive: false },
];
// ---------------------------------

// Hàm mô phỏng gọi API lấy danh sách người dùng
async function fetchUsers() {
    loadingUsers.value = true; // Bắt đầu loading
    // Mô phỏng độ trễ mạng
    await new Promise(resolve => setTimeout(resolve, 500)); // Giả lập 0.5 giây tải data

    // Lọc dữ liệu giả dựa trên searchKeyword
    const keyword = searchKeyword.value.toLowerCase();
    const filteredUsers = mockUsers.filter(user =>
        user.username.toLowerCase().includes(keyword) ||
        user.email.toLowerCase().includes(keyword)
    );

    // *** Lưu ý: Với dữ liệu giả, chúng ta không có phân trang thực tế.
    // Logic này chỉ hiển thị toàn bộ dữ liệu đã lọc.
    // Khi tích hợp API thật, bạn sẽ xử lý response.data.content và thông tin phân trang.

    users.value = filteredUsers; // Gán dữ liệu đã lọc
    loadingUsers.value = false; // Kết thúc loading
}

// Hàm xử lý khi nhấn nút Tìm kiếm hoặc Enter trong input (chỉ dùng khi watchEnabled = false)
function handleSearch() {
    if (!watchEnabled) {
        fetchUsers();
    }
}


// Hàm định dạng ngày tháng (tái sử dụng từ Dashboard)
function formatDate(dateString) {
    if (!dateString) return '';
    const options = { year: 'numeric', month: 'numeric', day: 'numeric', hour: 'numeric', minute: 'numeric' };
    try {
        const date = new Date(dateString);
        if (isNaN(date.getTime())) {
            return dateString;
        }
        return date.toLocaleDateString('vi-VN', options);
    } catch (e) {
        console.error("Error formatting date:", e);
        return dateString;
    }
}


// Lifecycle hook: Chạy khi component được mount
onMounted(() => {
    console.log('Admin Users View mounted');
    fetchUsers(); // Gọi hàm fetch users (sử dụng dữ liệu giả) khi component load lần đầu

    // --- Watch searchKeyword để tìm kiếm tức thời ---
    if (watchEnabled) {
        watch(searchKeyword, () => {
            // Clear timer cũ nếu có
            if (searchTimer) {
                clearTimeout(searchTimer);
            }
            // Đặt timer mới để gọi fetchUsers sau 300ms
            searchTimer = setTimeout(() => {
                fetchUsers();
            }, 300); // Debounce 300ms
        });
    }
    // ---------------------------------------------
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
    /* Màu xanh dương của Bootstrap */
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
    /* Loại bỏ shadow mặc định của input */
}

.input-group .btn {
    border-radius: 0 .25rem .25rem 0 !important;
    /* Bo tròn chỉ góc phải */
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
    /* Loại bỏ margin dưới cùng mặc định của bảng */
}

.table-hover tbody tr:hover {
    background-color: rgba(0, 123, 255, 0.075);
    /* Hiệu ứng hover nhẹ nhàng */
}

/* Style cho header bảng */
.table thead th {
    vertical-align: bottom;
    border-bottom: 2px solid #dee2e6;
}


/* Style cho phân trang */
.pagination .page-link {
    color: #007bff;
}

.pagination .page-item.active .page-link {
    background-color: #007bff;
    border-color: #007bff;
    color: #fff;
}

.pagination .page-link:hover {
    color: #0056b3;
}

/* Style cho nút hành động bo tròn */
.rounded-pill {
    border-radius: 50rem !important;
}

/* Thêm icon cho nút */
.btn i {
    margin-right: 5px;
}
</style>