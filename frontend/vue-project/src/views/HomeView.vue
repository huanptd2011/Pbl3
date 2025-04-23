<template>
    <div class="home-page">
      <!-- Hero Banner -->
      <!-- Hero Banner as Carousel -->
<section class="hero-banner mb-5">
  <div class="container">
    <div id="heroCarousel" class="carousel slide" data-bs-ride="carousel">
      <div class="carousel-inner">
        <div class="carousel-item active">
          <img src="../assets/img/001_002-su24-air-group-1200x630.jpeg" class="d-block w-100" alt="Los Angeles" />
          <div class="carousel-caption d-none d-md-block">
            <h3>Los Angeles</h3>
            <p>We had such a great time in LA!</p>
          </div>
        </div>
        <div class="carousel-item">
          <img src="../assets/img/footlocker-sale-dames-sneakers-2.webp" class="d-block w-100" alt="Chicago" />
          <div class="carousel-caption d-none d-md-block">
            <h3>Chicago</h3>
            <p>Thank you, Chicago!</p>
          </div>
        </div>
        <div class="carousel-item">
          <img src="../assets/img/sallyssneakers-am1-1-tsw_6ec6bf362f774257a08e51d71dd5a78b_1024x1024-1-png-1723110914-08082024165514.webp"
            class="d-block w-100" alt="New York" />
          <div class="carousel-caption d-none d-md-block">
            <h3>New York</h3>
            <p>We love the Big Apple!</p>
          </div>
        </div>
      </div>

      <!-- Controls -->
      <button class="carousel-control-prev" type="button" data-bs-target="#heroCarousel" data-bs-slide="prev">
        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
      </button>
      <button class="carousel-control-next" type="button" data-bs-target="#heroCarousel" data-bs-slide="next">
        <span class="carousel-control-next-icon" aria-hidden="true"></span>
      </button>
    </div>
  </div>
</section>
<!-- hehe -->
  

  
      <!-- New Arrivals -->
      <section class="new-arrivals mb-5">
        <div class="container">
          <div class="d-flex justify-content-between align-items-center mb-4">
            <h2 class="section-title">Sản phẩm mới</h2>
            <router-link to="/products" class="btn btn-outline-dark">Xem tất cả</router-link>
          </div>
          <div class="row g-4">
            <div class="col-md-3" v-for="product in newArrivals" :key="product.id">
              <ProductCard :product="product" />
            </div>
          </div>
        </div>
      </section>
  
      <!-- Best Sellers -->
      <section class="best-sellers mb-5">
        <div class="container">
          <div class="d-flex justify-content-between align-items-center mb-4">
            <h2 class="section-title">Bán chạy nhất</h2>
            <router-link to="/products" class="btn btn-outline-dark">Xem tất cả</router-link>
          </div>
          <div class="row g-4">
            <div class="col-md-3" v-for="product in bestSellers" :key="product.id">
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
            <p class="text-white mb-4">Áp dụng cho tất cả sản phẩm từ ngày 01/06 đến 30/06</p>
            <router-link to="/products" class="btn btn-light">Mua sắm ngay</router-link>
          </div>
        </div>
      </section>
    </div>
  </template>
  
  <script setup>
  import { ref, onMounted } from 'vue';
  import axios from 'axios';
  import ProductCard from '@/components/ProductCard.vue';
  
  const featuredCollections = ref([]);
  const newArrivals = ref([]);
  const bestSellers = ref([]);
  
  const fetchData = async () => {
    try {
      const [collectionsRes, newRes, bestRes] = await Promise.all([
        axios.get('http://localhost:3000/collections'),
        axios.get('http://localhost:3000/products/new'),
        axios.get('http://localhost:3000/products/bestsellers')
      ]);
      
      featuredCollections.value = collectionsRes.data;
      newArrivals.value = newRes.data;
      bestSellers.value = bestRes.data;
    } catch (error) {
      console.error('Error fetching data:', error);
    }
  };
  
  onMounted(() => {
    fetchData();
  });
  </script>
  
  <style scoped>
  .hero-banner {
    background-color: #f8f9fa;
    padding: 4rem 0;
  }
  
  .section-title {
    font-size: 2rem;
    font-weight: 700;
    position: relative;
    display: inline-block;
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
  
  .collection-card {
    position: relative;
    overflow: hidden;
    border-radius: 8px;
    height: 300px;
  }
  
  .collection-card img {
    width: 100%;
    height: 100%;
    object-fit: cover;
    transition: transform 0.5s;
  }
  
  .collection-card:hover img {
    transform: scale(1.05);
  }
  
  .collection-overlay {
    position: absolute;
    bottom: 0;
    left: 0;
    right: 0;
    background: linear-gradient(to top, rgba(0,0,0,0.8), transparent);
    padding: 1.5rem;
    color: white;
  }
  
  .promo-banner {
    background: linear-gradient(rgba(0,0,0,0.7), rgba(0,0,0,0.7)), 
                url('@/assets/promo-bg.jpg');
    background-size: cover;
    background-position: center;
  }
  
  .promo-content {
    background-color: rgba(211, 47, 47, 0.9);
  }
  </style>