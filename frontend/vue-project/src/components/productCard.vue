<template>
    <div class="card h-100 product-card">
      <img :src="product.imageList[0].imageUrl" class="card-img-top" :alt="product.productName">
      <div class="card-body d-flex flex-column">
        <h5 class="card-title">{{ product.productName }}</h5>
        <p class="card-text text-muted mb-2">{{ product.category }}</p>
        <p class="card-text fw-bold text-danger mb-3">{{ formatPrice(product.price) }}₫</p>
        <router-link :to="`/products/${product.productId}`" class="btn btn-outline-primary mt-auto">Xem chi tiết</router-link>
        

      </div>
    </div>
  </template>
  
  <script setup>
  defineProps({
    product: Object
  })
  
  const formatPrice = (price) => {
    return price.toLocaleString('vi-VN')
  }
  </script>
  
  <style scoped>
  .product-card img {
    height: 200px;
    object-fit: cover;
  }
  </style>
  