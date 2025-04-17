<template>
    <div class="container mt-4">
        <div class="row">
            <!-- Slide -->
            <div class="col-12 mb-5">
                <div id="slide" class="carousel slide" data-bs-ride="carousel">
                    <!-- Indicators -->
                    <div class="carousel-indicators">
                        <button type="button" data-bs-target="#slide" data-bs-slide-to="0" class="active"></button>
                        <button type="button" data-bs-target="#slide" data-bs-slide-to="1"></button>
                        <button type="button" data-bs-target="#slide" data-bs-slide-to="2"></button>
                    </div>

                    <!-- Slides -->
                    <div class="carousel-inner">
                        <div class="carousel-item active">
                            <img src="../assets/img/001_002-su24-air-group-1200x630.jpeg" class="d-block w-100"
                                alt="Los Angeles" />
                            <div class="carousel-caption">
                                <h3>Los Angeles</h3>
                                <p>We had such a great time in LA!</p>
                            </div>
                        </div>
                        <div class="carousel-item">
                            <img src="../assets/img/footlocker-sale-dames-sneakers-2.webp" class="d-block w-100"
                                alt="Chicago" />
                            <div class="carousel-caption">
                                <h3>Chicago</h3>
                                <p>Thank you, Chicago!</p>
                            </div>
                        </div>
                        <div class="carousel-item">
                            <img src="../assets/img/sallyssneakers-am1-1-tsw_6ec6bf362f774257a08e51d71dd5a78b_1024x1024-1-png-1723110914-08082024165514.webp"
                                class="d-block w-100" alt="New York" />
                            <div class="carousel-caption">
                                <h3>New York</h3>
                                <p>We love the Big Apple!</p>
                            </div>
                        </div>
                    </div>

                    <!-- Controls -->
                    <button class="carousel-control-prev" type="button" data-bs-target="#slide" data-bs-slide="prev">
                        <span class="carousel-control-prev-icon"></span>
                    </button>
                    <button class="carousel-control-next" type="button" data-bs-target="#slide" data-bs-slide="next">
                        <span class="carousel-control-next-icon"></span>
                    </button>
                </div>
            </div>

            <!-- Collections -->
            <div class="col-12">
                <h3 class="text-center mb-4">COLLECTIONS</h3>
                <div class="row g-4">
                    <div class="col-md-4" v-for="collection in collections" :key="collection.id">
                        <div class="card mb-4 shadow-sm">
                            <img :src="collection.image" class="card-img-top" :alt="collection.name" />
                            <div class="card-body text-center">
                                <h5 class="card-title">{{ collection.name }}</h5>
                                <router-link :to="collection.route" class=" shopnow btn-theme mt-2 w-100">
                                    Shop now
                                </router-link>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- best sale -->
            <div class="col-md-3" v-for="product in bestSellers" :key="product.id">
                <productCard :product="product" />
            </div>

        </div>
    </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import axios from "axios";
import productCard from "@/components/ProductCard.vue";

const collections = ref([]);
const bestSellers = ref([]);



const fetchData = async (url, targetRef) => {
    try {
        const res = await axios.get(url);
        targetRef.value = res.data;
    } catch (error) {
        console.log("Lỗi fetch collection:", error);
    }
};

onMounted(() => {
    fetchData("http://localhost:3000/collections", collections);
    fetchData("http://localhost:3000/bestsellers", bestSellers);
});
</script>

<style scoped>
.carousel-inner {
    height: 500px;
    overflow: hidden;
}

.card-img-top {
    height: 220px;
    object-fit: cover;
}

.shopnow {
    display: block;
    text-decoration: none;

}

.card {
    overflow: hidden;
    transition: all 0.5s ease-in-out;
}

.card:hover img {
    transform: scale(1.1);
    transition: all 0.5s ease-in-out;

}
</style>