<template>
  <div class="container mt-4">
      <h1>{{ categoryName || 'Sản phẩm' }}</h1>

      <nav aria-label="breadcrumb">
          <ol class="breadcrumb">
              <li class="breadcrumb-item"><router-link to="/">Trang chủ</router-link></li>
              <li class="breadcrumb-item" v-if="categoryName">
                  <router-link to="/products">Sản phẩm</router-link>
              </li>
              <li class="breadcrumb-item active" aria-current="page">
                  {{ categoryName || 'Sản phẩm' }}
              </li>
          </ol>
      </nav>

      <div class="row">
          <div class="col-md-3">
              <div class="mb-4">
                  <h5>Bộ lọc</h5>

                  <!-- Category Filter (if multiple categories available) -->
                  <div v-if="availableCategories.length > 1" class="mb-3">
                      <h6>Danh mục</h6>
                      <div class="form-check" v-for="category in availableCategories" :key="category.categoryId">
                          <input class="form-check-input" type="radio"
                              v-model="selectedCategoryId"
                              :value="category.categoryId"
                              :id="'category-' + category.categoryId">
                          <label class="form-check-label" :for="'category-' + category.categoryId">
                              {{ category.categoryName }}
                          </label>
                      </div>
                      <div class="form-check">
                          <input class="form-check-input" type="radio"
                              v-model="selectedCategoryId"
                              value=""
                              id="categoryAll">
                          <label class="form-check-label" for="categoryAll">
                              Tất cả danh mục
                          </label>
                      </div>
                  </div>

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
                      <input class="form-check-input" type="radio" v-model="priceFilter" value="" id="priceAll">
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
                      {{ currentPage + 1 }}/{{ totalFilteredPages }} ({{ filteredProducts.length }} sản phẩm)
                  </div>
              </div>

              <div class="row row-cols-1 row-cols-md-3 g-4">
                  <div class="col" v-for="product in paginatedProducts" :key="product.productId">
                      <div class="card h-100 product-card" @click="goToProductDetail(product.productId)">
                        <div class="product-image-container">
                            <img :src="product?.imageList?.[0]?.imageUrl || 'fallback-image.png'" class="product-image" alt="Hình ảnh sản phẩm">
                        </div>

                          <div class="card-body">
                              <h6 class="card-title">{{ product.productName }}</h6>
                              <p class="card-text fw-bold">{{ formatPrice(product.price) }}</p>
                              <p class="card-text text-muted-product small">{{ product.brand }}</p>
                              <p class="card-text text-muted small" v-if="product.category">
                                  <i class="bi bi-tag"></i> {{ product.category.categoryName }}
                              </p>
                          </div>
                          <div class="card-footer bg-transparent" v-if="product.comingSoon">
                              <small class="text-muted">Sắp ra mắt</small>
                          </div>
                      </div>
                  </div>
              </div>

              <div class="col-12 text-center mt-4" v-if="filteredProducts.length === 0 && searchKeyword.trim()">
                  <p>Không tìm thấy sản phẩm nào với từ khóa "{{ searchKeyword }}"</p>
              </div>
              <div class="col-12 text-center mt-4"
                  v-if="filteredProducts.length === 0 && !searchKeyword.trim() && !isLoading">
                  <p>Không tìm thấy sản phẩm nào phù hợp với bộ lọc trong danh mục này.</p>
              </div>
              <div class="col-12 text-center mt-4" v-if="isLoading">
                  <p>Đang tải sản phẩm...</p>
              </div>

              <!-- Custom Pagination for filtered products -->
              <nav aria-label="Điều hướng trang" v-if="totalFilteredPages > 1" class="mt-4">
                  <ul class="pagination justify-content-center">
                      <li class="page-item" :class="{ 'disabled': currentPage === 0 }">
                          <button class="page-link" @click="prevPage">Trước</button>
                      </li>
                      <li class="page-item" v-for="page in visiblePages" :key="page"
                          :class="{ 'active': page === currentPage + 1 }">
                          <button class="page-link" @click="goToPage(page - 1)">{{ page }}</button>
                      </li>
                      <li class="page-item" :class="{ 'disabled': currentPage >= totalFilteredPages - 1 }">
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
import { useRoute, useRouter } from 'vue-router';

// Props để nhận categoryId từ parent component hoặc route
const props = defineProps({
    categoryId: {
        type: [String, Number],
        default: null
    }
});

// Constants
// const defaultPage = 0;
const defaultSize = 9;

// Reactive state
const searchKeyword = ref('');
const products = ref([]);
const currentPage = ref(0);
const pageSize = ref(defaultSize);
// const totalPages = ref(1);
const totalElements = ref(0);
const route = useRoute();
const router = useRouter();
const isLoading = ref(false);
const sortOption = ref('relevant');
const priceSortOrder = ref('asc');
const priceFilter = ref('');
const brandFilter = ref([]);
const availableBrands = ref([]);
const availableCategories = ref([]);
const selectedCategoryId = ref('');
const categoryName = ref('');

// Computed properties
const priceSortIcon = computed(() => {
    return priceSortOrder.value === 'asc' ? 'bi-arrow-up' : 'bi-arrow-down';
});

const filteredProducts = computed(() => {
    let filtered = [...products.value];

    // Apply category filter first
    const categoryIdToFilter = selectedCategoryId.value !== undefined && selectedCategoryId.value !== null
    ? selectedCategoryId.value
    : (props.categoryId || route.params.categoryId);

if (categoryIdToFilter) {
    filtered = filtered.filter(product => {
        return product.category && product.category.categoryId == categoryIdToFilter;
    });
    }

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

    // Apply keyword search
    if (searchKeyword.value.trim()) {
        const keyword = searchKeyword.value.toLowerCase().trim();
        filtered = filtered.filter(product =>
            product.productName.toLowerCase().includes(keyword) ||
            (product.brand && product.brand.toLowerCase().includes(keyword)) ||
            (product.category && product.category.categoryName.toLowerCase().includes(keyword))
        );
    }

    // Apply sorting
    return sortProducts(filtered);
});

// Pagination for filtered products
const totalFilteredPages = computed(() => {
    return Math.ceil(filteredProducts.value.length / pageSize.value);
});

const paginatedProducts = computed(() => {
    const start = currentPage.value * pageSize.value;
    const end = start + pageSize.value;
    return filteredProducts.value.slice(start, end);
});

const visiblePages = computed(() => {
    const pages = [];
    const startPage = Math.max(1, currentPage.value - 1);
    const endPage = Math.min(totalFilteredPages.value, currentPage.value + 3);

    for (let i = startPage; i <= endPage; i++) {
        pages.push(i);
    }
    return pages;
});

// Format price with Vietnamese đồng
const formatPrice = (price) => {
    if (!price) return 'N/A';
    return new Intl.NumberFormat('vi-VN').format(price) + 'đ';
};

// Token for authentication
const token = localStorage.getItem("authToken");

// Load all products from API (không phân trang từ server)
const loadProducts = async () => {
    isLoading.value = true;

    try {
        // Load tất cả products mà không phân trang
        const response = await fetch(`http://localhost:8080/api/products/search?size=1000`, {
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

        // Extract available brands and categories
        extractBrandsAndCategories();

        // Set category name if categoryId is provided
        setCategoryName();

    } catch (error) {
        console.error('Error loading products:', error);
        products.value = [];
        totalElements.value = 0;
    } finally {
        isLoading.value = false;
    }
};

// Extract unique brands and categories from products
const extractBrandsAndCategories = () => {
    const brands = new Set();
    const categories = new Map();

    products.value.forEach(product => {
        // Thêm brand nếu có
        if (product?.brand) {
            brands.add(product.brand);
        }

        // Thêm category nếu có
        const category = product?.category;
        if (category?.categoryId) {
            categories.set(category.categoryId, category);
        }
    });

    // Gán danh sách brand và category đã sắp xếp
    availableBrands.value = Array.from(brands).sort();
    availableCategories.value = Array.from(categories.values()).sort((a, b) =>
        a.categoryName.localeCompare(b.categoryName)
    );
};

const setCategoryName = () => {
    const categoryIdToFind = selectedCategoryId.value;

    if (categoryIdToFind) {
        const category = availableCategories.value.find(cat => cat.categoryId == categoryIdToFind);
        categoryName.value = category ? category.categoryName : 'Danh mục';
    } else {
        // Nếu chọn "Tất cả danh mục"
        categoryName.value = 'Tất cả danh mục';
    }
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
    // Reset to first page when sorting changes
    currentPage.value = 0;
};

// Reset all filters
const resetFilters = () => {
    priceFilter.value = '';
    brandFilter.value = [];
    selectedCategoryId.value = props.categoryId || route.params.categoryId || '';
    currentPage.value = 0;
};

// Navigation methods
const prevPage = () => {
    if (currentPage.value > 0) {
        currentPage.value--;
    }
};

const nextPage = () => {
    if (currentPage.value < totalFilteredPages.value - 1) {
        currentPage.value++;
    }
};

const goToPage = (page) => {
    if (page >= 0 && page < totalFilteredPages.value) {
        currentPage.value = page;
    }
};

// Watch for route query changes
watch(() => route.query, (newQuery) => {
    if (newQuery.q) {
        searchKeyword.value = newQuery.q;
    } else {
        searchKeyword.value = '';
    }
    currentPage.value = 0; // Reset to first page when search changes
}, { immediate: true });

// Watch for route params changes (categoryId)
watch(() => route.params.categoryId, (newCategoryId) => {
    selectedCategoryId.value = newCategoryId || props.categoryId || '';
    setCategoryName();
    currentPage.value = 0; // Reset to first page when category changes
}, { immediate: true });

// Watch for filter changes
watch([priceFilter, brandFilter, selectedCategoryId], () => {
    currentPage.value = 0; // Reset to first page when filters change
});
watch(selectedCategoryId, () => {
    setCategoryName();
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
    background-color: #32445e;
    color: white;
}

.form-check-label {
    color: #131418;
}

.form-check-input:checked {
    background-color: #19181b;
    border-color: #1f1e22;
}

.page-info {
    font-size: 0.9rem;
    color: #666;
}

.active>.page-link, .page-link.active{
    background-color: #32445e;
    border: none;
}

.page-link:focus{
    color:#cfd4db
}

.page-link {
    color: #131418;
}

.product-image-container {
    width: 100%;
    height: 180px;
    overflow: hidden;
    background-color: #ffffff;
    display: flex;
    justify-content: center;
    align-items: center;
    border-bottom: 1px solid #eee;
    padding: 10px 0;
}

.product-image {
    width: 100%;
    height: 100%;
    object-fit: cover;
    margin: 10px;
    border: #dfdada solid 1px;
    border-top-left-radius: 12px;
    border-top-right-radius: 12px;
}

.product-card {
    cursor: pointer;
    transition: transform 0.2s, box-shadow 0.2s;
}

.product-card:hover {
    transform: translateY(-2px);
    box-shadow: 0 4px 12px rgba(0,0,0,0.1);
}

.text-muted-product {
    color: #6c757d !important;
}
</style>
