<template>
    <div class="container mt-4">
        <h1>Sản phẩm</h1>

        <nav aria-label="breadcrumb">
            <ol class="breadcrumb">
                <li class="breadcrumb-item"><router-link to="/">Trang chủ</router-link></li>
                <li class="breadcrumb-item active" aria-current="page">Sản phẩm</li>
            </ol>
        </nav>

        <div class="row">
            <div class="col-md-3">
                <div class="mb-4">
                    <h5>Tìm kiếm</h5>
                    <div class="input-group rounded-pill">
                        <span class="input-group-text bg-transparent border-0">
                            <i class="bi bi-search"></i>
                        </span>
                        <input type="text" class="form-control border-0 rounded-pill" placeholder="Tìm kiếm sản phẩm..."
                            v-model="searchQuery" @input="handlePageSearchInput" @keyup.enter="handlePageSearchEnter">
                    </div>
                </div>


                <div class="mb-4">
                    <h5>Bộ lọc</h5>
                    <h6>Mức giá</h6>
                    <div class="form-check">
                        <input class="form-check-input" type="checkbox" value="" id="price1">
                        <label class="form-check-label" for="price1">
                            Dưới 1 triệu
                        </label>
                    </div>
                    <div class="form-check">
                        <input class="form-check-input" type="checkbox" value="" id="price2">
                        <label class="form-check-label" for="price2">
                            1 - 2 triệu
                        </label>
                    </div>
                    <div class="form-check">
                        <input class="form-check-input" type="checkbox" value="" id="price3">
                        <label class="form-check-label" for="price3">
                            2 - 3 triệu
                        </label>
                    </div>
                    <div class="form-check">
                        <input class="form-check-input" type="checkbox" value="" id="price4">
                        <label class="form-check-label" for="price4">
                            Trên 3 triệu
                        </label>
                    </div>
                    <h6 class="mt-3">Thương hiệu</h6>
                    <div class="form-check">
                        <input class="form-check-input" type="checkbox" value="" id="brand1">
                        <label class="form-check-label" for="brand1">
                            Nike
                        </label>
                    </div>
                    <div class="form-check">
                        <input class="form-check-input" type="checkbox" value="" id="brand2">
                        <label class="form-check-label" for="brand2">
                            Adidas
                        </label>
                    </div>
                </div>

                <div class="mb-4">
                    <h5>Sắp xếp theo</h5>
                    <select class="form-select">
                        <option selected>Sản phẩm bán chạy</option>
                        <option value="1">Sản phẩm mới nhất</option>
                        <option value="2">Giá: Tăng dần</option>
                        <option value="3">Giá: Giảm dần</option>
                    </select>
                </div>
            </div>

            <div class="col-md-9">
                <div class="row row-cols-1 row-cols-md-3 g-4">
                    <div class="col" v-for="product in filteredProducts" :key="product.id">
                        <div class="card h-100">
                            <img :src="product.imageList[0].imageUrl" class="card-img-top" alt="Product Image">
                            <div class="card-body">
                                <h6 class="card-title">{{ product.ProductName }}</h6>
                                <div class="text-warning mb-1">
                                    <i class="bi bi-star-fill"></i>
                                    <i class="bi bi-star-fill"></i>
                                    <i class="bi bi-star-fill"></i>
                                    <i class="bi bi-star-fill"></i>
                                    <i class="bi bi-star-half"></i>
                                </div>
                                <p class="card-text fw-bold">{{ product.price }}</p>
                                <a href="#" class="btn btn-primary btn-sm">Xem chi tiết</a>
                                <a href="#" class="btn btn-outline-secondary btn-sm">Thêm vào giỏ</a>
                            </div>
                        </div>
                    </div>

                    <div class="col-12 text-center mt-4" v-if="filteredProducts.length === 0 && searchQuery.trim()">
                        <p>Không tìm thấy sản phẩm nào với từ khóa "{{ searchQuery }}"</p>
                    </div>
                    <div class="col-12 text-center mt-4"
                        v-if="!filteredProducts.length && !searchQuery.trim() && !isLoading">
                        <p>Chưa có sản phẩm nào.</p>
                    </div>
                    <div class="col-12 text-center mt-4" v-if="isLoading">
                        <p>Đang tải sản phẩm...</p>
                    </div>
                </div>

                <nav aria-label="Page navigation example" class="mt-4">
                    <ul class="pagination justify-content-center">
                        <li class="page-item disabled">
                            <a class="page-link">Previous</a>
                        </li>
                        <li class="page-item"><a class="page-link" href="#">1</a></li>
                        <li class="page-item"><a class="page-link" href="#">2</a></li>
                        <li class="page-item"><a class="page-link" href="#">3</a></li>
                        <li class="page-item">
                            <a class="page-link" href="#">Next</a>
                        </li>
                    </ul>
                </nav>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref, computed, watch, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';

const searchQuery = ref(''); // Biến cho input search trên trang SP
const allProducts = ref([]); // Danh sách TẤT CẢ sản phẩm
const route = useRoute();
const router = useRouter();
const isLoading = ref(false); // Biến trạng thái tải dữ liệu

// Hàm fetch dữ liệu sản phẩm (simulated fetch)
const fetchProducts = async () => {
    isLoading.value = true;
    // Thay thế bằng logic fetch API thực tế của bạn
    const response = await fetch('http://localhost:8080/api/products');
    allProducts.value = await response.json();

    isLoading.value = false;
    // Sau khi fetch xong, áp dụng lọc lần đầu (nếu có query param)
    applyFilterFromUrl();
};


// Computed property để lọc sản phẩm hiển thị
const filteredProducts = computed(() => {
    // Nếu đang tải hoặc không có sản phẩm gốc, trả về mảng rỗng hoặc rỗng
    if (isLoading.value || !allProducts.value) {
        return [];
    }

    if (!searchQuery.value.trim()) {
        return allProducts.value; // Hiển thị tất cả nếu input search trống
    }
    const lowerCaseQuery = searchQuery.value.toLowerCase();
    return allProducts.value.filter(product => {
        // Lọc dựa trên tên sản phẩm
        return product.name.toLowerCase().includes(lowerCaseQuery);
        // Bạn có thể thêm các trường khác ở đây nếu muốn tìm kiếm trong mô tả, thương hiệu, v.v.
        // || (product.description && product.description.toLowerCase().includes(lowerCaseQuery))
        // || (product.brand && product.brand.toLowerCase().includes(lowerCaseQuery));
    });
});

// Hàm áp dụng lọc dựa trên query parameter trên URL
const applyFilterFromUrl = () => {
    if (route.query.q) {
        // Cập nhật input search trên trang SP với giá trị từ URL
        searchQuery.value = route.query.q;
        // Computed property filteredProducts sẽ tự động lọc lại
    } else {
        // Nếu không có query param 'q' trên URL, đảm bảo input search trên trang SP trống
        searchQuery.value = '';
        // Computed property filteredProducts sẽ tự động hiển thị tất cả
    }
};


// Xử lý khi gõ bất kỳ ký tự nào vào input search trên trang SP (tùy chọn: lọc tức thời)
// Nếu muốn lọc chỉ khi nhấn Enter thì bỏ đoạn watcher này
watch(searchQuery, (newVal) => {
    // Cập nhật URL với từ khóa mới mỗi khi searchQuery thay đổi
    router.push({ query: { ...route.query, q: newVal.trim() } });
    // Logic lọc đã nằm trong computed property filteredProducts
});


// Theo dõi sự thay đổi của query parameter 'q' trên URL
// Điều này quan trọng khi người dùng search từ Header lần nữa trong khi đang ở trang sản phẩm
watch(() => route.query.q, (newQ, oldQ) => {
    // Chỉ xử lý nếu query 'q' thực sự thay đổi
    if (newQ !== oldQ && newQ !== searchQuery.value) {
        // Cập nhật searchQuery dựa trên query param mới từ URL
        // Watcher của searchQuery ở trên sẽ lo việc cập nhật URL và lọc lại
        searchQuery.value = newQ || ''; // Sử dụng '' nếu newQ là undefined (khi xóa query param)
    } else if (!newQ && oldQ && searchQuery.value) {
        // Xử lý trường hợp query 'q' bị xóa khỏi URL (ví dụ: người dùng xóa bằng tay)
        searchQuery.value = '';
    }
});


// Khi component được mount (trang được tải lần đầu)
onMounted(() => {
    fetchProducts(); // Lấy dữ liệu sản phẩm
    // applyFilterFromUrl() sẽ được gọi sau khi fetchProducts hoàn tất
});

// Hàm xử lý khi người dùng nhấn Enter trong input search trên trang SP
const handlePageSearchEnter = () => {
    // Logic cập nhật URL và lọc đã được xử lý bởi watcher của searchQuery
    // Không cần làm gì thêm ở đây nếu watcher đã hoạt động
};

// Hàm xử lý khi người dùng gõ vào input search trên trang SP
const handlePageSearchInput = () => {
    // Logic cập nhật URL và lọc đã được xử lý bởi watcher của searchQuery
    // Nếu bạn chỉ muốn lọc khi nhấn Enter, hãy bỏ watcher của searchQuery
    // và chỉ gọi router.push/applyFilterFromUrl trong handlePageSearchEnter
};


</script>

<style scoped>
/* CSS tùy chỉnh cho trang sản phẩm nếu cần */
.rounded-pill {
    border-radius: 50rem !important;
}

.card-img-top {
    width: 100%;
    height: 150px;
    /* Điều chỉnh chiều cao ảnh sản phẩm */
    object-fit: cover;
    /* Giữ tỷ lệ ảnh */
}
</style>