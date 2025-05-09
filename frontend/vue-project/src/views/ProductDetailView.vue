<template>
    <div class="container py-5" v-if="product">
        <div class="row">
            <!-- Hình ảnh sản phẩm (carousel) -->
            <div class="col-md-6 mb-4">
                <div id="productImagesCarousel" class="carousel slide" data-bs-ride="carousel">
                    <div class="carousel-inner">
                        <div v-for="(img, index) in product.imageList" :key="index"
                            :class="['carousel-item', { active: index === 0 }]">
                            <img :src="img?.imageUrl || 'fallback-image.png'" class="d-block w-100 rounded" alt="Product Image" />
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
                <p class="text-muted">Thương hiệu: {{ product.brand }}</p>
                <h4 class="text-danger fw-bold">{{ formatPrice(product.price) }}₫</h4>
                <p class="mt-3">{{ product.productDescription }}</p>

                <!-- Lựa chọn màu sắc -->
                <div class="mb-3" v-if="availableColors.length > 0 ">
                    <label class="form-label fw-semibold">Chọn màu:</label>
                    <div class="d-flex gap-2 flex-wrap">
                        <button v-for="color in availableColors" :key="color" class="btn"
                            :class="color === selectedColor ? 'btn-dark' : 'btn-outline-secondary'"
                            @click="selectColor(color)">
                            {{ color }}
                        </button>
                    </div>
                </div>

                <!-- Lựa chọn size -->
                <div class="mb-3" v-if="selectedColor && availableSizes.length > 0">
                    <label class="form-label fw-semibold">Chọn size:</label>
                    <div class="d-flex gap-2 flex-wrap">
                        <button v-for="size in availableSizes" :key="size" class="btn"
                            :class="size === selectedSize ? 'btn-dark' : 'btn-outline-secondary'"
                            @click="selectSize(size)">
                            {{ size }}
                        </button>
                    </div>
                </div>


                <!-- Tồn kho -->
                <div class="mb-3" v-if="selectedColor && selectedSize">
                    <p>
                        <span class="fw-semibold">Số lượng còn lại:</span>
                        {{ getQuantity(selectedColor, selectedSize) }}
                    </p>
                </div>

                <div>
            <button
              class="btn btn-primary mt-3"
              :disabled="!selectedColor || !selectedSize || getQuantity(selectedColor, selectedSize) <= 0"
              @click="handleAddToCart">
              Thêm vào giỏ hàng
            </button>

             <button
              class="btn btn-success mt-3 ms-2"
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
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import axios from 'axios'
import { useCartStore } from '@/stores/cartStore'
import { useUserStore } from '@/stores/user';

const route = useRoute()
const router = useRouter(); // <-- Sử dụng useRouter

const productId = route.params.productId


const product = ref({ sizeColorList: [] });
const selectedColor = ref(null)
const selectedSize = ref(null)
const quantityToAdd = ref(1); // <-- Thêm state cho số lượng muốn thêm (mặc định là 1)

const cartStore = useCartStore(); // <-- Khởi tạo store



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
        quantity: item.quantity
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
  const colors = product.value.sizeColorList.map(item => item.color);
  return [...new Set(colors)]; // Loại bỏ màu trùng lặp
});

const availableSizes = computed(() => {
  if (!product.value?.sizeColorList || !selectedColor.value) return [];
  return product.value.sizeColorList
    .filter(item => item.color === selectedColor.value)
    .map(item => item.size);
});

const getQuantity = (color, size) => {
    const item = product.value?.sizeColorList.find(
        sc => sc.color === color && sc.size === size
    )
    return item?.quantity ?? 0
}

const formatPrice = (price) => {
    return price?.toLocaleString('vi-VN') ?? ''
}

const selectColor = (color) => {
    selectedColor.value = color
    selectedSize.value = null // reset size khi đổi màu
}

const selectSize = (size) => {
    selectedSize.value = size
}

//Thêm vào giỏ hàng
const handleAddToCart = () => {

  if (!product.value || !selectedColor.value || !selectedSize.value || getQuantity(selectedColor.value, selectedSize.value) <= 0) {
    alert('Vui lòng chọn màu, size và đảm bảo còn hàng.');
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
        name: product.value.productName,
        imageUrl: product.value.imageList && product.value.imageList.length > 0 ? product.value.imageList[0].imageUrl : 'placeholder.jpg',
        price: product.value.price,
        color: selectedColor.value,
        size: selectedSize.value,
        quantity: quantityToAdd.value, // Mua ngay thường là 1, nhưng có thể dùng quantityToAdd nếu có input số lượng
        brand: product.value.brand
    };

    // <-- Gọi action 'addItem' của cart store để thêm sản phẩm vào giỏ hàng (hoặc cập nhật số lượng nếu đã có)
    cartStore.addItem(itemToBuy);

    // <-- Điều hướng người dùng đến trang thanh toán (/checkout)
    router.push({ name: 'Checkout' }); // Sử dụng tên route 'Checkout' như đã định nghĩa trong router/index.js
    // Hoặc bạn có thể dùng: router.push('/checkout');

    // Lưu ý: Mua ngay thường không reset lựa chọn sau khi thêm vì người dùng đã chuyển trang
};


</script>

<style scoped>
.carousel-inner img {
    height: 450px;
    object-fit: cover;
}
</style>
