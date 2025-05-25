<template>
    <div class="product-management-container">
        <div class="card">
            <div class="card-header d-flex justify-content-between align-items-center">
                <span>Quản lý Sản phẩm</span>
                <div class="d-flex align-items-center">
                     <select v-model="filterCategory" class="form-select form-select-sm me-2 calenda" style="width: 150px;">
               <option value="">Tất cả Danh mục</option>
               <option v-for="category in categories" :key="category.categoryId" :value="category.categoryName" >
                {{ category.categoryName }}
            </option>
              
                </select>

           <select v-model="filterStatus" class="form-select form-select-sm me-2 calenda" style="width: 120px;">
               <option value="">Tất cả Trạng thái</option>
               <option value="active">  Đang bán</option>
               <option value="inactive">  Ngừng bán</option>
           </select>


                    <input type="text" class="form-control form-control-sm me-2 calenda"
                        placeholder="Tìm kiếm sản phẩm..." style="width: 200px;" v-model="searchKeyword"
                        @input="handleSearch">
                    <button class="btn btn-primary btn-sm bg-main bor-main" @click="goToAddProduct"
                     >
                        <i class="fas fa-plus me-1"></i>
                        Thêm Sản phẩm
                    </button>
                </div>
            </div>
            <div class="card-body">
                <div v-if="loadingProducts" class="loading-overlay">
                    <div class="spinner-border text-primary" role="status">
                        <span class="visually-hidden">Đang tải...</span>
                    </div>
                </div>
                <table class="table table-striped table-bordered custom-orders-table">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Ảnh</th>
                            <th>Tên Sản phẩm</th>
                            <th>Giá</th>
                            <th>Số lượng tồn</th>
                            <th>Danh mục</th>
                            <th>Trạng thái</th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                        <template v-if="paginatedProducts.length > 0">
                            <tr v-for="product in paginatedProducts" :key="product.id">
                                <td>{{ product.productId }}</td>
                                <td>
                                    <img :src="product.imageUrl" alt="Product Image"
                                        style="width: 50px; height: 50px; object-fit: cover;">
                                </td>
                                <td>{{ product.productName }}</td>
                                <td>{{ formatCurrency(product.price) }}</td>
                                <td>{{ product.totalInventory}}</td>
                                <td>{{ product.productCategory }}</td>
                                <td>
                                    <span
                                        :class="['status-badge', product.isActive  ? 'status-active' : 'status-inactive']">
                                        {{ product.isActive ? 'Đang bán' : 'Ngừng bán' }}
                                    </span>
                                </td>
                                <td>
                                    <i class="fas fa-edit text-info me-3 icon " 
                                        @click="editProduct(product.productId)"></i>
                                    <i class="fas fa-trash-alt text-danger icon" 
                                        @click="deleteProduct(product.productId)"></i>
                                </td>
                            </tr>
                        </template>
                        <tr v-else-if="paginatedProducts.length === 0">
                            <td colspan="8" class="text-center text-muted">Không tìm thấy sản phẩm nào.</td>
                        </tr>

                        <template v-else>
                            <tr v-for="n in pageSize" :key="'placeholder-' + n">
                                <td>...</td>
                                <td>...</td>
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
</template>

<script setup>
import { ref, onMounted, watch, computed } from 'vue';
import axios from 'axios';
import { useRouter } from 'vue-router'; // Để điều hướng sang trang thêm/sửa


const allProducts = ref([]);
const filterCategory = ref('');
const categories = ref([]); 
const filterStatus = ref('');
const loadingProducts = ref(true);
const searchKeyword = ref('');
const currentPage = ref(1);
const pageSize = 4; // Số lượng sản phẩm trên mỗi trang

const router = useRouter(); // Khởi tạo router

async function fetchCategories() {
    try {
        const response = await axios.get('http://localhost:8080/api/product-categories');
        categories.value = response.data;
        console.log('Fetched categories:', categories.value);
    } catch (error) {
        console.error('Error fetching categories:', error);
        categories.value = []; 
    }
}

async function fetchProducts() {
    loadingProducts.value = true; 


    try {
        const response = await axios.get(`http://localhost:8080/api/products`);

        allProducts.value = response.data; // Lưu tất cả sản phẩm vào allProducts

    } catch (error) {
        console.error('Error fetching products:', error);
        allProducts.value = [];
        
    } finally {
            loadingProducts.value = false;
    }
}

const filterProduct = computed(() => {
    let filteredProducts = allProducts.value;

    // Lọc theo danh mục
    if (filterCategory.value) {
        filteredProducts = filteredProducts.filter(product => product.productCategory === filterCategory.value);
    }

    // Lọc theo trạng thái
    if (filterStatus.value) {
        filteredProducts = filteredProducts.filter(product => product.isActive === (filterStatus.value === 'active'));
    }

    // Tìm kiếm theo tên sản phẩm
    if (searchKeyword.value) {
        filteredProducts = filteredProducts.filter(product =>
            product.productName.toLowerCase().includes(searchKeyword.value.toLowerCase())
        );
    }


    return filteredProducts;
});

const paginatedProducts = computed(() => {
    const startIndex = (currentPage.value - 1) * pageSize;
    return filterProduct.value.slice(startIndex, startIndex + pageSize);
});

const totalPages = computed(() => {
  return Math.ceil(filterProduct.value.length / pageSize);
});

// Hàm xử lý khi gõ vào ô search (có thể thêm debounce nếu cần)
let searchTimeout = null;
function handleSearch() {
    if (searchTimeout) {
        clearTimeout(searchTimeout);
    }
    searchTimeout = setTimeout(() => {
        currentPage.value = 1; // Reset về trang 1 khi search
     
    }, 300); // Delay 300ms sau khi gõ xong
}

// Hàm chuyển trang
function changePage(page) {
    if (page >= 1 && page <= totalPages.value) {
        currentPage.value = page;
       
    }
}

// Hàm định dạng tiền tệ (sử dụng lại từ Dashboard)
function formatCurrency(value) {
    if (value == null) return '';
    return value.toLocaleString('vi-VN', { style: 'currency', currency: 'VND' });
}

// Hàm điều hướng sang trang thêm sản phẩm (sẽ cần route '/products/add')
function goToAddProduct() {
    router.push(`/admin/product/create`);
}

// Hàm điều hướng sang trang sửa sản phẩm (sẽ cần route '/products/edit/:id')
function editProduct(productId) {
    router.push(`/admin/product/edit/${productId}`);
}

// Hàm xóa sản phẩm
async function deleteProduct(productId) {
    if (confirm(`Bạn có chắc chắn muốn xóa sản phẩm ID ${productId} không?`)) {
        try {
            await axios.delete(`http://localhost:8080/api/products/delete/${productId}`);
            console.log(`Product with ID ${productId} deleted.`);
            // Sau khi xóa thành công, fetch lại danh sách hoặc xóa khỏi mảng hiện tại
             const index = allProducts.value.findIndex(p => p.productId === productId);
      if (index !== -1) {
        // Xóa sản phẩm khỏi mảng allProducts (Vue sẽ tự reactivity)
        allProducts.value.splice(index, 1);
        if (paginatedProducts.value.length === 0 && currentPage.value > 1) {
             // Quay về trang trước đó
             currentPage.value--;
             // Computed property paginatedProducts sẽ tự cập nhật
        }
      }

        } catch (error) {
            console.error(`Error deleting product with ID ${productId}:`, error);
            alert('Có lỗi xảy ra khi xóa sản phẩm.');
        }
    }
}


// Fetch data khi component được mount lần đầu
onMounted(() => {
    fetchProducts();
    fetchCategories();
});

watch([searchKeyword, filterCategory, filterStatus], () => {
    console.log("Filter/Search criteria changed, resetting page to 1.");
    currentPage.value = 1;
    // Không cần gọi fetchProducts ở đây vì dữ liệu đã có trong allProducts
});
</script>

<style scoped>
/* Sử dụng lại các style chung cho card từ dashboard */
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
    /* Đảm bảo card header có nền phù hợp */
    background-color: #0b1739;
    /* Giống màu nền card hoặc hơi khác tùy ý */
    border-bottom: 1px solid #1e293b;
    /* Viền dưới header */
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





/* Style cho cột ảnh sản phẩm */
.custom-orders-table td img {
    border-radius: 4px;
    /* Bo góc ảnh nhỏ */
}


/* Status Badges (Sử dụng lại style từ dashboard, thêm trạng thái Active/Inactive) */
.status-badge {
    display: inline-block;
    padding: 0.55em 0.55em;
    font-size: 0.75em;
    font-weight: 400;
    line-height: 1;
    text-align: center;
    white-space: nowrap;
    vertical-align: baseline;
    border-radius: 4px;
    
    /* Màu chữ mặc định */
}

/* Màu cho trạng thái sản phẩm */
.status-badge.status-active {
    background-color: #10b9812e;
    color: #139f81;
    border: 0.2px solid  #139f81;
}

.status-badge.status-inactive {
    background-color: #e2232330;
    color: #ef4444;
    border: 0.2px solid  #ef4444;
}

/* Action Icons */
.custom-orders-table td i {
    cursor: pointer;
    margin-right: 0.5rem;
}

.custom-orders-table td i:last-child {
    margin-right: 0;
}



/* Phân trang */
.pagination {
    display: flex;
    justify-content: center;
    position: absolute;
    bottom: 0;
    left: 40%;
    /* Khoảng cách giữa bảng và phân trang */
}

.pagination .page-item .page-link {
    background-color: #161e39;
    /* Nền nút phân trang */
    border-color: #1e293b;
    /* Viền nút phân trang */
    color: #aeb9e1;
    /* Màu chữ nút phân trang */
    
}

.pagination .page-item.active .page-link {
    background-color: rgba(203, 60, 255);
    /* Nền nút active */
    color: #fff;
    /* Màu chữ nút active */
}

.pagination .page-item.disabled .page-link {
    color: #555;
    /* Màu chữ nút disabled */
    pointer-events: none;
    /* Không cho click */
    background-color: #161e39;
    border-color: #1e293b;
}

.pagination .page-item .page-link:hover:not(.disabled) {
    background-color: rgba(203, 60, 255);
    /* Màu nền khi hover */
}

.pagination .page-item .page-link:focus {
    outline: none;
    /* Bỏ viền focus */

}

.loading-overlay {
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background-color: rgba(11, 23, 57, 0.8);
    display: flex;
    justify-content: center;
    align-items: center;
    z-index: 10;
    opacity: 1;
    transition: opacity 0.3s ease-in-out;
}

.loading-table {
    opacity: 0.5;
    pointer-events: none;
}
input::placeholder {
    
    color: #aeb9e1;
}
</style>