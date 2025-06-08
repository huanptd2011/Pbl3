<template>
    <div class="container py-5" v-if="product">
        <div class="row">
            <!-- Hình ảnh sản phẩm (carousel) -->
            <div class="col-md-6 mb-4">
                <div id="productImagesCarousel" class="carousel slide" data-bs-ride="carousel">
                    <div class="carousel-inner">
                        <div v-for="(img, index) in product.imageList" :key="index"
                            :class="['carousel-item', { active: index === 0 }]">
                            <img :src="img?.imageUrl || 'fallback-image.png'" class="d-block w-100 rounded"
                                alt="Product Image" />
                        </div>
                    </div>
                    <button class="carousel-control-prev" type="button" data-bs-target="#productImagesCarousel"
                        data-bs-slide="prev">
                        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                    </button>
                    <button class="carousel-control-next" type="button" data-bs-target="#productImagesCarousel"
                        data-bs-slide="next">
                        <span class="carousel-control-next-icon" aria-hidden="true"></span>
                    </button>
                </div>
            </div>

            <!-- Thông tin sản phẩm -->
            <div class="col-md-6">
                <h2 class="mb-2">{{ product.productName }}</h2>
                <p class="text-muted-product">Thương hiệu: {{ product.brand }}</p>
                <h4 class="text-danger fw-bold">{{ formatPrice(product.price) }}₫</h4>
                <p class="mt-3">{{ product.productDescription }}</p>
      
                <div class="d-flex align-items-center mb-3">

                <!-- Lựa chọn màu sắc -->
                <div class=" me-5" v-if="availableColors.length > 0">
                    <label class="form-label-product fw-semibold">Chọn màu:</label>
                    <div class="d-flex gap-2 flex-wrap">
                        <button v-for="color in availableColors" :key="color" class="btn"
                            :class="color === selectedColor ? 'btn-dark' : 'btn-outline-secondary'"
                            @click="selectColor(color)">
                            {{ color }}
                        </button>
                    </div>
                </div>

                <!-- Lựa chọn size -->
                <div class="" v-if="selectedColor && availableSizes.length > 0">
                    <label class="form-label-product fw-semibold">Chọn size:</label>
                    <div class="d-flex gap-2 flex-wrap">
                        <button v-for="size in availableSizes" :key="size" class="btn"
                            :class="size === selectedSize ? 'btn-dark' : 'btn-outline-secondary'"
                            @click="selectSize(size)">
                            {{ size }}
                        </button>
                    </div>
                </div>
                </div>

                <!-- Tồn kho -->
                <div class="mb-3" v-if="selectedColor && selectedSize">
                    <p>
                        <span class="text">Số lượng còn lại:</span>
                        {{ getQuantity(selectedColor, selectedSize) }}
                    </p>
                </div>


                <div class="mb-3" v-if="selectedColor && selectedSize && getQuantity(selectedColor, selectedSize) > 0">
                    <label for="quantityInput" class="form-label-product fw-semibold">Số lượng:</label>
                    <div class="input-group quantity-selector">
                        <button class="btn btn-outline-secondary" type="button" @click="decrementQuantity"
                            :disabled="quantityToAdd <= 1">
                              -
                        </button>
                        <input type="number" id="quantityInput" class="form-control text-center"
                            v-model.number="quantityToAdd" min="1" :max="getQuantity(selectedColor, selectedSize)"
                            @change="validateQuantity" />
                        <button class="btn btn-outline-secondary" type="button" @click="incrementQuantity"
                            :disabled="quantityToAdd >= getQuantity(selectedColor, selectedSize)">
                        +
                        </button>
                    </div>
                </div>

                <div>
                    <button class="btn submit-button mt-3"
                        :disabled="!selectedColor || !selectedSize || getQuantity(selectedColor, selectedSize) <= 0"
                        @click="handleAddToCart">
                        Thêm vào giỏ hàng
                    </button>

                    <button class="btn btn-buynow  mt-3 ms-2"
                        :disabled="!selectedColor || !selectedSize || getQuantity(selectedColor, selectedSize) <= 0"
                        @click="handleBuyNow">
                        Mua ngay
                    </button>
                </div>
            </div>
        </div>
    </div>

    <!-- Loading -->
    <div v-else class="text-center py-5">
        <div class="spinner-border text-primary" role="status"></div>
        <p class="mt-3">Đang tải sản phẩm...</p>
    </div>


    <!-- Phần đánh giá sản phẩm - Phiên bản mới -->
    <div class="container mt-5 review-section">
        <div class="row">
            <div class="col-12">
                <h3 class="mb-4 fw-bold">Đánh giá sản phẩm</h3>

                <!-- Rating overview -->
                <div class="rating-overview p-4 mb-4 rounded-3 shadow-sm">
                    <div class="row align-items-center">
                        <div class="col-md-4 text-center mb-3 mb-md-0">
                            <div class="average-rating-big mb-2">
                                <span class="display-4 fw-bold text">{{ averageRating.toFixed(1) }}</span>
                                <span class="fs-4 text-muted">/5</span>
                            </div>
                            <div class="star-rating mb-2">
                                <span v-for="star in 5" :key="star"
                                    :class="['star', { 'filled': star <= Math.round(averageRating) }]">
                                    ★
                                </span>
                            </div>
                            <p class="text-muted mb-0">{{ reviews.length }} đánh giá</p>
                        </div>
                        <div class="col-md-8">
                            <div v-for="i in 5" :key="i" class="rating-progress mb-2">
                                <div class="d-flex align-items-center">
                                    <span class="me-2 text-nowrap">{{ 6 - i }} ★</span>
                                    <div class="progress flex-grow-1" style="height: 8px;">
                                        <div class="progress-bar bg-warning"
                                            :style="{ width: getRatingPercentage(6 - i) + '%' }"></div>
                                    </div>
                                    <span class="ms-2 text-muted small">{{ getRatingCount(6 - i) }}</span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Review form -->
                <div class="review-form   card-fix border-0 shadow-sm mb-4" v-if="userStore.isLoggedIn">
                    <div class="card-body p-4">
                        <h5 class="card-title fw-semibold mb-3">Viết đánh giá của bạn</h5>
                        <form @submit.prevent="submitReview">
                            <div class="mb-3">
                                <label class="form-label d-block mb-2" style="color: black;">Đánh giá của bạn</label>
                                <div class="rating-input">
                                    <span v-for="star in 5" :key="star" @click="setRating(star)"
                                        @mouseover="hoverRating = star" @mouseleave="hoverRating = 0" :class="['star', {
                                            'filled': star <= (hoverRating || newReview.rating),
                                            'selected': star <= newReview.rating
                                        }]">
                                        ★
                                    </span>
                                </div>
                            </div>
                            <div class="mb-3">
                                <textarea class="form-control" v-model="newReview.content"
                                    placeholder="Chia sẻ cảm nhận của bạn về sản phẩm..." rows="4" required></textarea>
                            </div>
                            <button type="submit" class="btn btn-primary px-4 py-2">
                                <i class="bi bi-send-fill me-2"></i>Gửi đánh giá
                            </button>
                        </form>
                    </div>
                </div>
                <div v-else class="card-fix border-0 shadow-sm mb-4">
                    <div class="card-body p-4 text-center">
                        <p class="mb-0">Vui lòng <a href="#" @click.prevent="redirectToLogin" class="text-primary">đăng
                                nhập</a> để viết đánh giá.</p>
                    </div>
                </div>

                <!-- Reviews list -->
                <div class="reviews-list">
                    <div v-if="reviews.length === 0" class="card-fix border-0 shadow-sm">
                        <div class="card-body p-4 text-center text-muted">
                            <i class="bi bi-chat-square-text fs-1 mb-3"></i>
                            <p class="mb-0">Sản phẩm chưa có đánh giá nào.</p>
                        </div>
                    </div>

                    <div v-for="review in visibleReviews" :key="review.reviewId"
                        class="card-fix border-0 shadow-sm mb-3">
                        <div class="card-body p-4">
                            <div class="d-flex justify-content-between align-items-start mb-2">
                                <div>
                                    <div style="display: flex;">
                                        <div class="avatar-circle me-2">
                                            <img v-if="review.user.avatar"
                                                :src="review.user.avatar || 'fallback-avatar.png'"
                                                :alt="review.user.username" @error="handleImageError"
                                                class="avatar-image">
                                        </div>
                                        <div>
                                            <h5 class="card-title mb-1 fw-semibold">{{ review.user.username }}</h5>
                                            <span class="text-muted small"><strong>{{ formatDate(review.reviewDate)
                                            }}</strong></span>
                                        </div>
                                    </div>
                                    <div class="star-rating small mb-2">
                                        <span v-for="star in 5" :key="star"
                                            :class="['star', { 'filled': star <= review.rating }]"
                                            style="font-size: 1.2em;">
                                            ★
                                        </span>
                                    </div>
                                </div>
                            </div>
                            <p class="card-text mb-0">{{ review.reviewContent }}</p>
                        </div>
                    </div>

                    <!-- Load more button -->
                    <div class="text-center mt-4" v-if="reviews.length > visibleReviews.length">
                        <button class="btn btn-outline-primary px-4 py-2" @click="loadMoreReviews">
                            <i class="bi bi-chevron-down me-2"></i>
                            Xem thêm đánh giá ({{ reviews.length - visibleReviews.length }})
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Nút quay lại đầu trang -->
    <button v-show="showBackToTop" @click="scrollToTop" class="back-to-top-btn rounded-circle shadow"
        aria-label="Quay lại đầu trang">
        <i class="fa-solid fa-arrow-up"></i>
    </button>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import axios from 'axios'
import { useCartStore } from '@/stores/cartStore'
import { useUserStore } from '@/stores/user';
import { useBuyNowStore } from '@/stores/buyNowStore';


const route = useRoute()
const router = useRouter(); // <-- Sử dụng useRouter

const productId = route.params.productId


const product = ref({ sizeColorList: [] });
const selectedColor = ref(null)
const selectedSize = ref(null)
const quantityToAdd = ref(1); // <-- Thêm state cho số lượng muốn thêm (mặc định là 1)
const errorMessage = ref('')
const cartStore = useCartStore(); // <-- Khởi tạo store
const buyNowStore = useBuyNowStore(); // <-- Khởi tạo store cho mua ngay


// function handleImageError(event) {
//   event.target.src = 'fallback-image.png';
// }

// Gọi API lấy dữ liệu sản phẩm
const fetchProduct = async () => {
    try {
        const response = await axios.get(`http://localhost:8080/api/products/${productId}`);
        const data = response.data;
        product.value = {
            ...data,
            sizeColorList: data.sizeColorList?.map(item => ({
                color: item.colour || item.color, // Handle different property names
                size: item.size,
                quantity: item.quantity,
                status: item.isActive
            })) || []
        };
    } catch (error) {
        console.error('Lỗi khi tải sản phẩm:', error);
    }
};

onMounted(fetchProduct)

onMounted(async () => {
    await fetchProduct();
    console.log("Product data:", product.value);
    console.log("Available colors:", availableColors.value);
    console.log("Available sizes when color selected:", availableSizes.value);
});
const availableColors = computed(() => {
    if (!product.value?.sizeColorList) return [];
    const colors = product.value.sizeColorList
        .filter(item => item.status) // Chỉ lấy item isActive = true
        .map(item => item.color);
    return [...new Set(colors)];
});


const availableSizes = computed(() => {
    if (!product.value?.sizeColorList || !selectedColor.value) return [];
    return product.value.sizeColorList
        .filter(item => item.status && item.color === selectedColor.value)
        .map(item => item.size);
});


const getQuantity = (color, size) => {
    const item = product.value?.sizeColorList.find(
        sc => sc.status && sc.color === color && sc.size === size
    );
    return item?.quantity ?? 0;
};

const formatPrice = (price) => {
    return price?.toLocaleString('vi-VN') ?? ''
}

const selectColor = (color) => {
    selectedColor.value = color
    selectedSize.value = null
    quantityToAdd.value = 1
  errorMessage.value = ''
}

const selectSize = (size) => {
    selectedSize.value = size
    quantityToAdd.value = 1
  errorMessage.value = ''
}


// Hàm tăng số lượng
const incrementQuantity = () => {
    const maxQuantity = getQuantity(selectedColor.value, selectedSize.value);
    if (quantityToAdd.value < maxQuantity) {
        quantityToAdd.value++;
    }
  validateQuantity()
};

// Hàm giảm số lượng
const decrementQuantity = () => {
    if (quantityToAdd.value > 1) {
        quantityToAdd.value--;
    }
  validateQuantity()
};

// Hàm kiểm tra số lượng nhập vào
const validateQuantity = () => {
  const maxQuantity = getQuantity(selectedColor.value, selectedSize.value);
  errorMessage.value = ''; // Reset thông báo lỗi

  // Đảm bảo quantityToAdd là số nguyên dương
  if (quantityToAdd.value === null || isNaN(quantityToAdd.value) || quantityToAdd.value < 1) {
    quantityToAdd.value = 1; // Đặt về 1 nếu không hợp lệ
    errorMessage.value = 'Số lượng phải lớn hơn hoặc bằng 1.';
    return;
  }

  // Kiểm tra nếu số lượng lớn hơn tồn kho
  if (quantityToAdd.value > maxQuantity) {
    errorMessage.value = `Số lượng không được vượt quá tồn kho (${maxQuantity}).`;
  }
};

//Thêm vào giỏ hàng
const handleAddToCart = () => {

    if (!product.value || !selectedColor.value || !selectedSize.value || getQuantity(selectedColor.value, selectedSize.value) <= 0) {
        alert('Vui lòng chọn màu, size và đảm bảo còn hàng.');
        return;
    }
  if (errorMessage.value) {
    alert(errorMessage.value);
    return;
  }

    //check đăng nhập
    const authStore = useUserStore();  // Lấy trạng thái đăng nhập từ store
    const isAuthenticated = authStore.isLoggedIn;

    if (!isAuthenticated) {
        // Nếu người dùng chưa đăng nhập, chuyển hướng họ đến trang đăng nhập
        alert('Vui lòng đăng nhập!');
        router.push({ name: 'Login' }); // Chuyển hướng đến trang Login
        return;
    }

    const itemToAdd = {
        productId: product.value.productId,
        name: product.value.productName,
        imageUrl: product.value.imageList && product.value.imageList.length > 0 ? product.value.imageList[0].imageUrl : 'placeholder.jpg',
        price: product.value.price,
        color: selectedColor.value,
        size: selectedSize.value,
        quantity: quantityToAdd.value,
    };
    alert('Đã thêm vào giỏ hàng');
    cartStore.addItem(itemToAdd);
};

// <-- THÊM HÀM NÀY để xử lý logic khi nhấn nút "Mua ngay"
const handleBuyNow = () => {
    // Kiểm tra điều kiện tương tự như "Thêm vào giỏ hàng"
    if (!product.value || !selectedColor.value || !selectedSize.value || getQuantity(selectedColor.value, selectedSize.value) <= 0) {
        console.warn('Vui lòng chọn màu, size và đảm bảo còn hàng.');
        alert('Vui lòng chọn màu, size và đảm bảo còn hàng.');
        return;
    }
  if (errorMessage.value) {
    alert(errorMessage.value); // Hoặc hiển thị lỗi một cách khác
    return;
  }

    //check đăng nhập
    const authStore = useUserStore();  // Lấy trạng thái đăng nhập từ store
    const isAuthenticated = authStore.isLoggedIn;

    if (!isAuthenticated) {
        // Nếu người dùng chưa đăng nhập, chuyển hướng họ đến trang đăng nhập
        alert('Vui lòng đăng nhập!');
        router.push({ name: 'Login' }); // Chuyển hướng đến trang Login
        return;
    }

    // Tạo đối tượng chi tiết sản phẩm (biến thể) để thêm vào giỏ
    const itemToBuy = {
        productId: product.value.productId,
        productName: product.value.productName,
        imageUrl: product.value.imageList && product.value.imageList.length > 0 ? product.value.imageList[0].imageUrl : 'placeholder.jpg',
        price: product.value.price,
        color: selectedColor.value,
        size: selectedSize.value,
        quantity: quantityToAdd.value, // Mua ngay thường là 1, nhưng có thể dùng quantityToAdd nếu có input số lượng
        brand: product.value.brand
    };

    buyNowStore.setBuyNowItem(itemToBuy);
    console.log('Buy Now item set:', buyNowStore.buyNowItem);

    router.push({ name: 'Checkout' });

};




// Khởi tạo các biến state
const reviews = ref([])
const visibleReviews = ref([])
const reviewsPerPage = 5
const hoverRating = ref(0)
const newReview = ref({
    rating: 0,
    content: ''
})
const userStore = useUserStore()

// Hàm lấy danh sách đánh giá từ API
const fetchReviews = async () => {
    try {
        const response = await axios.get(`http://localhost:8080/api/reviews/${productId}`)
        reviews.value = response.data
        visibleReviews.value = reviews.value.slice(0, reviewsPerPage)
    } catch (error) {
        console.error('Lỗi khi tải đánh giá:', error)
        alert('Có lỗi xảy ra khi tải đánh giá sản phẩm')
    }
}

// Hàm tải thêm đánh giá
const loadMoreReviews = () => {
    const nextPageStart = visibleReviews.value.length
    const nextPageEnd = nextPageStart + reviewsPerPage
    const nextReviews = reviews.value.slice(nextPageStart, nextPageEnd)
    visibleReviews.value = [...visibleReviews.value, ...nextReviews]
}

// Hàm gửi đánh giá mới
const submitReview = async () => {
    if (!newReview.value.rating || !newReview.value.content.trim()) {
        alert('Vui lòng chọn số sao và nhập nội dung đánh giá')
        return
    }

    try {
        const reviewData = {
            productId: productId,
            userId: userStore.user.userId,
            reviewContent: newReview.value.content.trim(),
            rating: newReview.value.rating
        }

        const response = await axios.post('http://localhost:8080/api/reviews', reviewData)

        // Thêm đánh giá mới vào đầu danh sách
        reviews.value.unshift(response.data)
        visibleReviews.value = reviews.value.slice(0, reviewsPerPage)

        // Reset form
        newReview.value = { rating: 0, content: '' }
        alert('Đánh giá của bạn đã được gửi thành công!')
    } catch (error) {
        console.error('Lỗi khi gửi đánh giá:', error)
        alert(error.response?.data?.message || 'Có lỗi xảy ra khi gửi đánh giá')
    }
}

// Hàm xử lý chọn số sao
const setRating = (rating) => {
    newReview.value.rating = rating
}

// Hàm tính điểm trung bình
const averageRating = computed(() => {
    if (reviews.value.length === 0) return 0
    const sum = reviews.value.reduce((total, review) => total + review.rating, 0)
    return sum / reviews.value.length
})

// Hàm đếm số lượng đánh giá theo số sao
const getRatingCount = (star) => {
    return reviews.value.filter(review => review.rating === star).length
}

// Hàm tính phần trăm đánh giá theo số sao
const getRatingPercentage = (star) => {
    if (reviews.value.length === 0) return 0
    return (getRatingCount(star) / reviews.value.length) * 100
}

// Hàm định dạng ngày tháng
const formatDate = (dateString) => {
    const date = new Date(dateString)
    const day = date.getDate().toString().padStart(2, '0')
    const month = (date.getMonth() + 1).toString().padStart(2, '0')
    const year = date.getFullYear()
    return `${day}/${month}/${year}`
}


// Hàm chuyển hướng đến trang đăng nhập
const redirectToLogin = () => {
    router.push({ name: 'Login', query: { redirect: route.fullPath } })
}


const showBackToTop = ref(false)

// Hàm xử lý scroll
const handleScroll = () => {
    showBackToTop.value = window.scrollY > 300
}

// Hàm cuộn lên đầu trang
const scrollToTop = () => {
    window.scrollTo({
        top: 0,
        behavior: 'smooth'
    })
}


// Gọi API khi component được mounted
onMounted(() => {
    window.addEventListener('scroll', handleScroll)
    fetchReviews()
})



</script>

<style scoped>
.carousel-inner img {
    height: 450px;
    object-fit: cover;
    border: #ddd 1px solid;
}

.form-control {
    width: 100%;
   
    border: 1px solid #7f8b94;
    border-radius: 6px;
    font-size: 1rem;
    transition: border-color 0.2s;

}

.form-control:focus {
    outline: none;
    border-color: #52626e;
    box-shadow: 0 0 0 2px rgba(105, 123, 138, 0.2);
}

/* Style cho nút submit */
.btn-primary {
    background-color: #393a41;
    border-color: #5a5c66;
}

.btn-primary:hover {
    background-color: #585968;
    border-color: #626377;
}

/* Avatar styles */
.avatar-circle {
    width: 50px;
    height: 50px;
    border-radius: 50%;
    overflow: hidden;
    position: relative;
    background-color: #f0f0f0;
    /* Màu nền fallback */
    display: flex;
    align-items: center;
    justify-content: center;
}

.avatar-image {
    width: 100%;
    height: 100%;
    object-fit: cover;
}

.review-section {
    background-color: #fafafa;
    padding: 2rem 2rem;
    border-radius: 12px;
}

.rating-overview {
    background-color: white;
    border: 1px solid #eee;
}

.average-rating-big {
    line-height: 1;
}

.star-rating {
    font-size: 1.5rem;
    color: #ccc;
    line-height: 1;
}

.star-rating.small {
    font-size: 1rem;
}

.star-rating .star.filled {
    color: #ffc107;
}

.rating-input {
    font-size: 2rem;
    color: #ddd;
    line-height: 1;
    cursor: pointer;
}

.rating-input .star {
    transition: all 0.2s;
}

.rating-input .star.filled {
    color: #ffc107;
    transform: scale(1.1);
}

.rating-input .star.selected {
    text-shadow: 0 0 4px rgba(255, 193, 7, 0.5);
}

.review-form textarea {
    min-height: 120px;
    resize: none;
}

.reviews-list .card {
    transition: transform 0.2s;
}

.reviews-list .card:hover {
    transform: translateY(-2px);
    box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
}


/* Style cho nút back to top */
.back-to-top-btn {
    position: fixed;
    bottom: 30px;
    right: 30px;
    width: 50px;
    height: 50px;
    background-color: #24262c;
    color: white;
    border: none;
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 1.2rem;
    cursor: pointer;
    z-index: 99;
    opacity: 0.8;
    transition: all 0.3s ease;
}

.back-to-top-btn:hover {
    opacity: 1;
    background-color: #303544;
    transform: translateY(-3px);
}

/* Hiệu ứng khi xuất hiện */
.back-to-top-btn {
    animation: fadeIn 0.3s;
}

.btn-buynow {
    background: white;
    color: #e63946;
    border: 1px solid #e63946;
    padding: 0.75rem;
    border-radius: 6px;
    font-size: 1rem;
    font-weight: 500;
    cursor: pointer;
    transition: background-color 0.2s;
}

.btn-buynow:hover {
    background: #e63946;
    color: white;
}

.submit-button {
    background: white;
    color: #2b2525;
    border: 1px solid #1f1b1c;
}

.text-muted {
    color: #999999 !important;
}

.text {
    color: #ff4444;
}

@keyframes fadeIn {
    from {
        opacity: 0;
        transform: translateY(20px);
    }

    to {
        opacity: 0.8;
        transform: translateY(0);
    }

}

.quantity-selector {
    width: 150px;

}

.quantity-selector .form-control {
    flex: 1;
    text-align: center;
    -moz-appearance: textfield;
    /* Firefox */
}

.quantity-selector .form-control::-webkit-outer-spin-button,
.quantity-selector .form-control::-webkit-inner-spin-button {
    -webkit-appearance: none;
    margin: 0;
}
</style>
