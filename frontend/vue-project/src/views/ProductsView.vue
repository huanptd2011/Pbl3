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
                  <h5>Bộ lọc</h5>
                  <h6>Mức giá</h6>
                  <div class="form-check">
                      <input class="form-check-input" type="radio" v-model="priceFilter" value="0-1000000" id="price1">
                      <label class="form-check-label" for="price1">
                          Dưới 1 triệu
                      </label>
                  </div>
                  <div class="form-check">
                      <input class="form-check-input" type="radio" v-model="priceFilter" value="1000000-2000000" id="price2">
                      <label class="form-check-label" for="price2">
                          1 - 2 triệu
                      </label>
                  </div>
                  <div class="form-check">
                      <input class="form-check-input" type="radio" v-model="priceFilter" value="2000000-3000000" id="price3">
                      <label class="form-check-label" for="price3">
                          2 - 3 triệu
                      </label>
                  </div>
                  <div class="form-check">
                      <input class="form-check-input" type="radio" v-model="priceFilter" value="3000000-999999999" id="price4">
                      <label class="form-check-label" for="price4">
                          Trên 3 triệu
                      </label>
                  </div>
                  <div class="form-check">
                      <input class="form-check-input" type="radio" v-model="priceFilter" value="" id="priceAll" checked>
                      <label class="form-check-label" for="priceAll">
                          Tất cả
                      </label>
                  </div>

                  <h6 class="mt-3">Thương hiệu</h6>
                  <div class="form-check" v-for="brand in availableBrands" :key="brand">
                      <input class="form-check-input" type="checkbox" v-model="brandFilter" :value="brand" :id="'brand-' + brand">
                      <label class="form-check-label" :for="'brand-' + brand">
                          {{ brand }}
                      </label>
                  </div>

                  <button class="btn btn-sm btn-outline-secondary mt-3" @click="resetFilters">Đặt lại bộ lọc</button>
              </div>
          </div>

          <div class="col-md-9">
              <div class="d-flex justify-content-between align-items-center mb-4">
                  <div class="sort-options">
                      <span class="me-2">Sắp xếp theo</span>
                      <button class="btn btn-outline-secondary btn-sm me-2"
                          :class="{ 'active': sortOption === 'relevant' }"
                          @click="changeSortOption('relevant')">
                          Liên quan
                      </button>
                      <button class="btn btn-outline-secondary btn-sm me-2"
                          :class="{ 'active': sortOption === 'newest' }"
                          @click="changeSortOption('newest')">
                          Mới nhất
                      </button>
                      <button class="btn btn-outline-secondary btn-sm me-2"
                          :class="{ 'active': sortOption === 'popular' }"
                          @click="changeSortOption('popular')">
                          Bán chạy
                      </button>
                      <button class="btn btn-outline-secondary btn-sm"
                          :class="{ 'active': sortOption === 'price' }"
                          @click="changeSortOption('price')">
                          Giá <i class="bi" :class="priceSortIcon"></i>
                      </button>
                  </div>
                  <div class="page-info">
                      {{ currentPage + 1 }}/{{ totalPages }}
                  </div>
              </div>

              <div class="row row-cols-1 row-cols-md-3 g-4">
                  <div class="col" v-for="product in filteredProducts" :key="product.productId">
                      <div class="card h-100 product-card" @click="goToProductDetail(product.productId)">
                          <img :src="product?.imageList?.[0]?.imageUrl || 'fallback-image.png'" class="card-img-top" alt="Product Image">
                          <div class="card-body">
                              <h6 class="card-title">{{ product.productName }}</h6>
                              <p class="card-text fw-bold">{{ formatPrice(product.price) }}</p>
                              <p class="card-text text-muted small">{{ product.brand }}</p>
                          </div>
                          <div class="card-footer bg-transparent" v-if="product.comingSoon">
                              <small class="text-muted">Coming soon</small>
                          </div>
                      </div>
                  </div>
              </div>

              <div class="col-12 text-center mt-4" v-if="filteredProducts.length === 0 && searchKeyword.trim()">
                  <p>Không tìm thấy sản phẩm nào với từ khóa "{{ searchKeyword }}"</p>
              </div>
              <div class="col-12 text-center mt-4"
                  v-if="filteredProducts.length === 0 && !searchKeyword.trim() && !isLoading">
                  <p>Không tìm thấy sản phẩm nào phù hợp với bộ lọc.</p>
              </div>
              <div class="col-12 text-center mt-4" v-if="isLoading">
                  <p>Đang tải sản phẩm...</p>
              </div>

              <nav aria-label="Page navigation" v-if="totalPages > 1" class="mt-4">
                  <ul class="pagination justify-content-center">
                      <li class="page-item" :class="{ 'disabled': currentPage === 0 }">
                          <button class="page-link" @click="prevPage">Trước</button>
                      </li>
                      <li class="page-item" v-for="page in visiblePages" :key="page"
                          :class="{ 'active': page === currentPage + 1 }">
                          <button class="page-link" @click="goToPage(page - 1)">{{ page }}</button>
                      </li>
                      <li class="page-item" :class="{ 'disabled': currentPage >= totalPages - 1 }">
                          <button class="page-link" @click="nextPage">Sau</button>
                      </li>
                  </ul>
              </nav>
          </div>
      </div>
  </div>
</template>

<script setup>
import { ref, computed, watch, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router'
//import ProductCard from '../components/productCard.vue';

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
const sortOption = ref('relevant');
const priceSortOrder = ref('asc');
const priceFilter = ref('');
const brandFilter = ref([]);
const availableBrands = ref([]);

// Computed properties
const priceSortIcon = computed(() => {
    return priceSortOrder.value === 'asc' ? 'bi-arrow-up' : 'bi-arrow-down';
});

const visiblePages = computed(() => {
    const pages = [];
    const startPage = Math.max(1, currentPage.value - 1);
    const endPage = Math.min(totalPages.value, currentPage.value + 3);

    for (let i = startPage; i <= endPage; i++) {
        pages.push(i);
    }
    return pages;
});

const filteredProducts = computed(() => {
    let filtered = [...products.value];

    // Apply price filter
    if (priceFilter.value) {
        const [min, max] = priceFilter.value.split('-').map(Number);
        filtered = filtered.filter(product => {
            const price = product.price || 0;
            return price >= min && price <= max;
        });
    }

    // Apply brand filter
    if (brandFilter.value.length > 0) {
        filtered = filtered.filter(product =>
            brandFilter.value.includes(product.brand)
        );
    }

    // Apply sorting
    return sortProducts(filtered);
});

// Format price with Vietnamese đồng
const formatPrice = (price) => {
    if (!price) return 'N/A';
    return new Intl.NumberFormat('vi-VN').format(price) + 'đ';
};

// Token for authentication
const token = localStorage.getItem("authToken");

// Load products from API
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

        // Extract available brands
        extractBrands();

    } catch (error) {
        console.error('Error loading products:', error);
        products.value = [];
        totalElements.value = 0;
        totalPages.value = 1;
    } finally {
        isLoading.value = false;
    }
};

// Extract unique brands from products
const extractBrands = () => {
    const brands = new Set();
    products.value.forEach(product => {
        if (product.brand) {
            brands.add(product.brand);
        }
    });
    availableBrands.value = Array.from(brands).sort();
};

// Sort products based on selected option
const sortProducts = (productsToSort) => {
    let sorted = [...productsToSort];

    switch (sortOption.value) {
        case 'newest':
            sorted.sort((a, b) => new Date(b.createdAt || 0) - new Date(a.createdAt || 0));
            break;
        case 'popular':
            sorted.sort((a, b) => (b.salesCount || 0) - (a.salesCount || 0));
            break;
        case 'price':
            sorted.sort((a, b) => {
                const priceA = a.price || 0;
                const priceB = b.price || 0;
                return priceSortOrder.value === 'asc' ? priceA - priceB : priceB - priceA;
            });
            break;
        case 'relevant':
        default:
            // Default sorting - might be relevance score or no sorting
            break;
    }

    return sorted;
};

// Change sort option
const changeSortOption = (option) => {
    if (option === 'price') {
        if (sortOption.value === 'price') {
            // Toggle price sort order if price is already selected
            priceSortOrder.value = priceSortOrder.value === 'asc' ? 'desc' : 'asc';
        } else {
            // Default to ascending when first selecting price
            priceSortOrder.value = 'asc';
        }
    }
    sortOption.value = option;
};

// Reset all filters
const resetFilters = () => {
    priceFilter.value = '';
    brandFilter.value = [];
};

// Navigation methods
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

const goToPage = (page) => {
    if (page >= 0 && page < totalPages.value) {
        currentPage.value = page;
        updateRouteAndLoadProducts();
    }
};

const updateRouteAndLoadProducts = () => {
    const query = { ...route.query };
    query.page = currentPage.value > 0 ? currentPage.value : undefined;
    router.push({ query });
    loadProducts();
};

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

// Watch for filter changes
watch([priceFilter, brandFilter], () => {
    currentPage.value = 0; // Reset to first page when filters change
});

// Navigation to product detail
const goToProductDetail = (productId) => {
    router.push({
        name: 'ProductDetail',
        params: { productId }
    });
};

// Initial load on component mount
onMounted(() => {
    loadProducts();
});
</script>

<style scoped>
.sort-options .btn.active {
    background-color: #0d6efd;
    color: white;
}

.card-img-top {
    width: 100%;
    height: 200px;
    object-fit: cover;
}

.product-card {
    cursor: pointer;
    transition: transform 0.2s;
    border: 1px solid #eee;
}

.product-card:hover {
    transform: translateY(-5px);
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.page-info {
    font-size: 0.9rem;
    color: #666;
}

.coming-soon-badge {
    position: absolute;
    top: 10px;
    right: 10px;
    background-color: rgba(0, 0, 0, 0.7);
    color: white;
    padding: 3px 8px;
    border-radius: 4px;
    font-size: 0.8rem;
}
</style>
