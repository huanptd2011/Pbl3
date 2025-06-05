<template>
  <div class="home-page">

    <!-- Hero Banner -->
    <section class="hero-banner ">

      <div id="heroCarousel" class="carousel slide" data-bs-ride="carousel">
        <div class="carousel-inner">
          <div class="carousel-item  banner-image"
            style="background-image: url('https://mir-s3-cdn-cf.behance.net/project_modules/1400/c9e8ea143948393.6283c8632efeb.jpg'); background-position: 100% 45.6%; background-size: 100%;">
          </div>
          <div class="carousel-item  banner-image active"
            style="background-image: url('https://mir-s3-cdn-cf.behance.net/project_modules/1400/c9e8ea143948393.6283c8632efeb.jpg'); background-position: 100% 84.6% ; background-size: 105%;">
          </div>
          <div class="carousel-item  banner-image"
            style="background-image: url('https://mir-s3-cdn-cf.behance.net/project_modules/1400/c9e8ea143948393.6283c8632efeb.jpg'); background-position: 100% 4.9% ;background-size: 103%">
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


    <section>
      <div class="container">
        <div class="text-center py-5  ">
          <h2 class=" vollkorn-title ">Sản phẩm nổi bật</h2>
          <span class=" roboto-thin">Sự kết hợp hoàn hảo giữa công nghệ và thẩm mỹ.
            <br>
            Không chỉ đẹp mắt, mà còn vượt trội trong hiệu năng.</span>
          <!-- <router-link to="/products" class="btn btn-outline-dark">Xem tất cả</router-link> -->
        </div>
      </div>
    </section>

    <!-- New Arrivals -->
    <section class="new-arrivals pb-5 pt-5  bg_main">
      <div class="container">
        
        <div class="d-flex justify-content-between align-items-center mb-4 ">
          <h2 class="section-title">Sản phẩm bán chạy</h2>
          <router-link to="/products" class="btn btn-outline-dark">Xem tất cả</router-link>
        </div>
        <div v-if="isLoading" class="text-center text-muted">Đang tải sản phẩm...</div>
        <div v-else class="row g-4">
          <div class="col-md-3" v-for="product in bestSale" :key="product.productId">
            <ProductCard :product="product" />
          </div>
        </div>
      </div>
    </section>

    <!-- Recently Updated -->
    <section class="best-sellers mb-5 pt-5">
      <div class="container">
        <div class="d-flex justify-content-between align-items-center mb-4">
          <h2 class="section-title">Cập nhật gần đây</h2>
          <router-link to="/products" class="btn btn-outline-dark">Xem tất cả</router-link>
        </div>
        <div v-if="isLoading" class="text-center text-muted">Đang tải sản phẩm...</div>
        <div v-else class="row g-4">
          <div class="col-md-3" v-for="product in newArrivals" :key="product.productId">
            <ProductCard :product="product" />
          </div>
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
const bestSale = ref([])
const isLoading = ref(true)

const fetchData = async () => {
  try {
    const responseNew = await axios.get('http://localhost:8080/api/products/new')
    const newProducts = responseNew.data
    const responseSelling = await axios.get('http://localhost:8080/api/products/best-selling')
    const sellingProducts = responseSelling.data

    // san pham moi
    newArrivals.value = [...newProducts]
    //San pham ban chay
    bestSale.value = [...sellingProducts]

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

.carousel-inner {
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
  width: 300px;
  height: 3px;
  background-color: #d32f2f;
}

.promo-banner {
  background: #fff;
  background-size: cover;
  background-position: center;
}

.promo-content {
  background-color: rgba(211, 47, 47, 0.9);
}

.carousel-item.banner-image {
  height: 500px;
  /* Set a specific height for your banner */
  width: 100%;
  /* Ensure it takes full width */
  background-size: cover;
  /* Scales the background image to cover the entire container */
  background-position: center 40%;
  /* Centers horizontally, anchors to the top vertically */
  background-repeat: no-repeat;
  /* Prevents the image from repeating */
}
.bg_main{
  background-color: #f5f5f5;
}
</style>
