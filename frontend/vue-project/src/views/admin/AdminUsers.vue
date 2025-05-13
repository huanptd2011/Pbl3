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
                    <p class="mt-2 text-muted">Đang tải danh sách người dùng...</p>
                </div>
                <div v-else-if="users.length === 0 && !loadingUsers" class="text-center py-4">
                    <p class="text-muted">Không có người dùng nào trong hệ thống.</p> 
                </div>
                <div v-else>
                    <div class="table-responsive"> 
                        <table class="table table-striped table-hover table-bordered"> 
                            <thead>
                                <tr class="bg-light"> 
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
                                        <span
                                            :class="{ 'badge bg-success': user.isActive, 'badge bg-danger': !user.isActive }">
                                            {{ user.isActive ? 'Hoạt động' : 'Khóa' }}
                                        </span>
                                    </td>
                                    <td>{{ formatDate(user.createdAt) }}</td>
                                    <td>
                                        
                                        <button class="btn btn-sm btn-outline-warning me-2 rounded-pill"> 
                                            <i class="bi bi-pencil-square"></i> Sửa
                                        </button>
                                       
                                        <button class="btn btn-sm btn-outline-danger rounded-pill"> 
                                            <i class="bi bi-trash"></i> Xóa
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

