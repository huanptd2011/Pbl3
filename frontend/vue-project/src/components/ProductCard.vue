<template>
    <div class="card mb-4 shadow-sm product-card">
        <img :src="product.image" class="card-img-top" :alt="product.name" />
        <div class="card-body text-center">
            <h5 class="card-title">{{ product.name }}</h5>
            <p v-if="product.price" class="card-text text-danger fw-bold">{{ product.price }}₫</p>
            <router-link :to="'/product/' + product.id" class="shopnow btn-theme mt-2 w-100">
                View Product
            </router-link>
        </div>
    </div>
</template>

<script setup>
defineProps({
    product: Object
});
</script>

<style scoped>
.product-card {
    overflow: hidden;
    transition: all 0.5s ease-in-out;
}

.product-card:hover img {
    transform: scale(1.1);
    transition: all 0.5s ease-in-out;
}
</style>