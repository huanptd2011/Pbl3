<template>
  <div class="home-page">

    <!-- Hero Banner -->
    <section class="hero-banner ">

        <div id="heroCarousel" class="carousel slide" data-bs-ride="carousel">
          <div class="carousel-inner">
            <div class="carousel-item active">
              <img src="../assets/web/slide.jpg" class="d-block w-100 " alt="Khuyến mãi tại Los Angeles" />

            </div>
            <div class="carousel-item">
              <img src="../assets/web/slide.jpg" class="d-block w-100" alt="Khuyến mãi tại Chicago" />

            </div>
            <div class="carousel-item">
              <img src="../assets/web/slide.jpg" class="d-block w-100" alt="Khuyến mãi tại New York" />

            </div>
          </div>
          <button class="carousel-control-prev" type="button" data-bs-target="#heroCarousel" data-bs-slide="prev">

            <i class="fa-solid fa-caret-left rounded-pill color_d9 px-1" style="color: #000;"></i>

          </button>
          <button class="carousel-control-next" type="button" data-bs-target="#heroCarousel" data-bs-slide="next">
            <i class="fa-solid fa-caret-right rounded-pill color_d9 px-1" style="color: #000;"></i>
          </button>
        </div>
    </section>

    <!-- New Arrivals -->
    <section class="new-arrivals pb-5 bg_main">
      <div class="container">
        <div class="text-center py-5">
          <h2 class=" vollkorn-title " >Sản phẩm nổi bật</h2>
          <span class=" roboto-thin">Sự kết hợp hoàn hảo giữa công nghệ và thẩm mỹ.
            <br>
            Không chỉ đẹp mắt, mà còn vượt trội trong hiệu năng.</span>
          <!-- <router-link to="/products" class="btn btn-outline-dark">Xem tất cả</router-link> -->
        </div>
        <div v-if="isLoading" class="text-center text-muted">Đang tải sản phẩm...</div>
        <div v-else class="row g-4">
          <div class="col-md-3" v-for="product in newArrivals" :key="product.productId">
            <ProductCard :product="product" />
          </div>
        </div>
      </div>
    </section>

    <!-- Recently Updated -->
    <section class="best-sellers mb-5">
      <div class="container">
        <div class="d-flex justify-content-between align-items-center mb-4">
          <h2 class="section-title">Cập nhật gần đây</h2>
          <router-link to="/products" class="btn btn-outline-dark">Xem tất cả</router-link>
        </div>
        <div v-if="isLoading" class="text-center text-muted">Đang tải sản phẩm...</div>
        <div v-else class="row g-4">
          <div class="col-md-3" v-for="product in recentlyUpdated" :key="product.productId">
            <ProductCard :product="product" />
          </div>
        </div>
      </div>
    </section>

    <!-- Promo Banner -->
    <section class="promo-banner mb-5">
      <div class="container">
        <div class="promo-content text-center p-5 rounded">
          <h2 class="text-white mb-3">Miễn phí vận chuyển cho đơn hàng từ 500K</h2>
          <p class="text-white mb-4">Áp dụng từ ngày 01/06 đến 30/06</p>
          <router-link to="/products" class="btn btn-light">Mua sắm ngay</router-link>
        </div>
      </div>
    </section>

  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'
import ProductCard from '../components/productCard.vue'

const newArrivals = ref([])
const recentlyUpdated = ref([])
const isLoading = ref(true)

const fetchData = async () => {
  try {
    const response = await axios.get('http://localhost:8080/api/products')
    const allProducts = response.data

    // Gán dữ liệu
    newArrivals.value = [...allProducts]
      .sort((a, b) => new Date(b.createdAt) - new Date(a.createdAt))
      .slice(0, 4)

    recentlyUpdated.value = [...allProducts]
      .sort((a, b) => new Date(b.updatedAt) - new Date(a.updatedAt))
      .slice(0, 4)
  } catch (error) {
    console.error('Lỗi khi lấy dữ liệu:', error)
  } finally {
    isLoading.value = false
  }
}


onMounted(fetchData)
</script>

<style scoped>
.hero-banner {
  background-color: #f8f9fa;

}
.carousel-inner{
  height: 500px;
  overflow: hidden;
}
.section-title {
  font-size: 2rem;
  font-weight: bold;
  position: relative;
}

.section-title::after {
  content: '';
  position: absolute;
  bottom: -10px;
  left: 0;
  width: 50px;
  height: 3px;
  background-color: #d32f2f;
}

.promo-banner {
  background: linear-gradient(rgba(0,0,0,0.6), rgba(0,0,0,0.6)), url('@/assets/promo-bg.jpg');
  background-size: cover;
  background-position: center;
}

.promo-content {
  background-color: rgba(211, 47, 47, 0.9);
}
</style>
