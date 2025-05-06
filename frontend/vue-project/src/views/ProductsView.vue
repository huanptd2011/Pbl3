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
              <!-- <div class="mb-4">
                  <h5>Tìm kiếm</h5>
                  <div class="input-group rounded-pill">
                      <span class="input-group-text bg-transparent border-0">
                          <i class="bi bi-search"></i>
                      </span>
                      <input type="text" class="form-control border-0 rounded-pill" placeholder="Tìm kiếm sản phẩm..."
                          v-model="searchKeyword" @input="debounceSearch" @keyup.enter="handleSearchEnter"> //@input="handleSearchInput"
                  </div>
              </div> -->

              <div class="mb-4">
                  <h5>Bộ lọc</h5>
                  <h6>Mức giá</h6>
                  <div class="form-check">
                      <input class="form-check-input" type="radio" value="" id="price1">
                      <label class="form-check-label" for="price1">
                          Dưới 1 triệu
                      </label>
                  </div>
                  <div class="form-check">
                      <input class="form-check-input" type="radio" value="" id="price2">
                      <label class="form-check-label" for="price2">
                          1 - 2 triệu
                      </label>
                  </div>
                  <div class="form-check">
                      <input class="form-check-input" type="radio" value="" id="price3">
                      <label class="form-check-label" for="price3">
                          2 - 3 triệu
                      </label>
                  </div>
                  <div class="form-check">
                      <input class="form-check-input" type="radio" value="" id="price4">
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
                  <div class="col" v-for="product in products" :key="product.productId">
                      <ProductCard :product="product" />
                  </div>

                  <div class="col-12 text-center mt-4" v-if="products.length === 0 && searchKeyword.trim()">
                      <p>Không tìm thấy sản phẩm nào với từ khóa "{{ searchKeyword }}"</p>
                  </div>
                  <div class="col-12 text-center mt-4"
                      v-if="products.length === 0 && !searchKeyword.trim() && !isLoading">
                      <p>Chưa có sản phẩm nào.</p>
                  </div>
                  <div class="col-12 text-center mt-4" v-if="isLoading">
                      <p>Đang tải sản phẩm...</p>
                  </div>
              </div>

              <nav aria-label="Page navigation" v-if="totalPages > 1">
                  <ul class="pagination justify-content-center">
                      <li class="page-item" :class="{ disabled: currentPage === 0 }">
                          <button class="page-link" @click="prevPage">Trước</button>
                      </li>
                      <li class="page-item disabled">
                          <span class="page-link">Trang {{ currentPage + 1 }} / {{ totalPages }}</span>
                      </li>
                      <li class="page-item" :class="{ disabled: currentPage >= totalPages - 1 }">
                          <button class="page-link" @click="nextPage">Sau</button>
                      </li>
                  </ul>
              </nav>
          </div>
      </div>
  </div>
</template>

<script setup>
import { ref, watch, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import ProductCard from '../components/productCard.vue'

// Constants
const defaultPage = 0;
const defaultSize = 12;

// Reactive state
const searchKeyword = ref('');
const products = ref([]);
const currentPage = ref(0);
const pageSize = ref(defaultSize);
const totalPages = ref(1);
const totalElements = ref(0);
const route = useRoute();
const router = useRouter();
const isLoading = ref(false);

// Token for authentication

const token = localStorage.getItem("authToken");

// Định nghĩa hàm loadProducts trước
const loadProducts = async () => {
    isLoading.value = true;

    // Build query parameters
    let params = new URLSearchParams();
    params.append('page', currentPage.value);
    params.append('size', pageSize.value);

    if (searchKeyword.value) {
        params.append('keyword', searchKeyword.value);
    }

    try {
        const response = await fetch(`http://localhost:8080/api/products/search?${params.toString()}`, {
            headers: {
                'Authorization': token ? `Bearer ${token}` : ''
            }
        });

        if (!response.ok) {
            throw new Error('Network response was not ok');
        }

        const data = await response.json();

        // Update component state
        products.value = data.content || [];
        totalElements.value = data.totalElements || 0;
        totalPages.value = data.totalPages || 1;

        console.log(`Loaded ${products.value.length} products, page ${currentPage.value + 1}/${totalPages.value}`);

    } catch (error) {
        console.error('Error loading products:', error);
        products.value = [];
        totalElements.value = 0;
        totalPages.value = 1;
    } finally {
        isLoading.value = false;
    }
};

// Sau đó mới watch
watch(() => route.query, (newQuery) => {
    if (newQuery.page) {
        currentPage.value = parseInt(newQuery.page);
    } else {
        currentPage.value = defaultPage;
    }

    if (newQuery.size) {
        pageSize.value = parseInt(newQuery.size);
    } else {
        pageSize.value = defaultSize;
    }

    if (newQuery.q) {
        searchKeyword.value = newQuery.q;
    } else {
        searchKeyword.value = '';
    }

    loadProducts();
}, { immediate: true });

// Các hàm khác...
// Watch for route query changes
watch(() => route.query, (newQuery) => {
  if (newQuery.page) {
      currentPage.value = parseInt(newQuery.page);
  } else {
      currentPage.value = defaultPage;
  }

  if (newQuery.size) {
      pageSize.value = parseInt(newQuery.size);
  } else {
      pageSize.value = defaultSize;
  }

  if (newQuery.q) {
      searchKeyword.value = newQuery.q;
  } else {
      searchKeyword.value = '';
  }

  loadProducts();
}, { immediate: true });

// Methods
const handleSearchInput = () => {
  // Apply real-time filtering if needed
  // We'll update the URL but not trigger a search immediately
  router.push({ query: { ...route.query, q: searchKeyword.value.trim() } });
};

const handleSearchEnter = () => {
  // Reset to first page and trigger search
  currentPage.value = 0;
  loadProducts();
};

const prevPage = () => {
  if (currentPage.value > 0) {
      currentPage.value--;
      updateRouteAndLoadProducts();
  }
};

const nextPage = () => {
  if (currentPage.value < totalPages.value - 1) {
      currentPage.value++;
      updateRouteAndLoadProducts();
  }
};

const updateRouteAndLoadProducts = () => {
  // Update URL with current state
  const query = { ...route.query };

  if (currentPage.value > 0) {
      query.page = currentPage.value;
  } else {
      delete query.page;
  }

  router.push({ query });
  loadProducts();
};

// Initial load on component mount
onMounted(() => {
  loadProducts();
});
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
