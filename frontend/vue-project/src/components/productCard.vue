<template>
  <div class="card h-100 product-card ">
    <div class="product-image-container">
        <img :src="product?.imageList[0].imageUrl || 'fallback-image.png'" alt="Product Image  " class=" product-image" />
    </div>
    
    <div class="card-body d-flex flex-column">
      <h5 class="card-title">{{ product?.productName }}</h5>
     <!-- <p class="card-text text-muted mb-2">{{ product?.category }}</p> -->
      <p class="card-text fw-bold text-danger mb-3">{{ formatPrice(product?.price) }}₫</p>
      <router-link
        :to="`/products/${product?.productId}`"
        class="btn btn-more mt-auto"
      >
        Xem chi tiết
      </router-link>
    </div>
  </div>
</template>

<script setup>
defineProps({
  product: {
    type: Object,
    required: true
  }
})

const formatPrice = (price) => {
  if (!price) return '0'
  return price.toLocaleString('vi-VN')
}
</script>

<style scoped>
 /* img {
  height: 200px;
  object-fit: cover;
  width: 100%;

}
.btn-more{
  
  color: #212529;
  border: 1px solid #212529;
  transition: background-color 0.3s ease;
}
.card-product{
  border: none;
  transition: transform 0.2s ease;
  background-color: #212529 ;
}

.product-card {
    background-color: #fff; 
    /* border-radius: 10px; 
    box-shadow: 0 4px 10px rgba(0, 0, 0, 0.08); 
    overflow: hidden; 
    width: 280px; 
    transition: transform 0.2s ease-in-out, box-shadow 0.2s ease-in-out; 
    display: flex;
    flex-direction: column;
    cursor: pointer;
} */
/* 
.product-image-container {
    width: 100%;
    height: 180px;
    overflow: hidden;
    background-color: #f7f7f7; 
    display: flex;
    justify-content: center;
    align-items: center;
    border-bottom: 1px solid #eee;
}

.product-image {
    width: 100%;
    height: 100%;
    object-fit: cover;
    padding: 10px; 
}  */

  .product-card {
    /* Overwrite Bootstrap .card defaults for image */
    border: none; /* Bỏ border mặc định của Bootstrap card */
    border-radius: 10px; /* Bo góc nhẹ */
    box-shadow: 0 4px 10px rgba(0, 0, 0, 0.08); /* Đổ bóng nhẹ nhàng */
    overflow: hidden; /* Đảm bảo nội dung bên trong không tràn ra ngoài các góc bo */
    transition: transform 0.2s ease-in-out, box-shadow 0.2s ease-in-out; /* Hiệu ứng khi hover */
    cursor: pointer; /* Biểu tượng con trỏ khi di chuột qua */
    background-color: #fff; /* Nền trắng */
    display: flex; /* Dùng flexbox để sắp xếp nội dung */
    flex-direction: column; /* Sắp xếp theo chiều dọc */
}

.product-card:hover {
    transform: translateY(-5px); /* Nâng card lên một chút khi hover */
    box-shadow: 0 8px 16px rgba(0, 0, 0, 0.15); /* Đổ bóng đậm hơn khi hover */
}

/* --- Phần chứa ảnh sản phẩm --- */
.product-image-container {
    width: 100%;
    height: 180px; /* Chiều cao cố định cho vùng ảnh */
    overflow: hidden;
    background-color: #ffffff; /* Nền nhẹ cho vùng ảnh */
    display: flex; /* Dùng flexbox để căn giữa ảnh */
    justify-content: center;
    align-items: center;
    border-bottom: 0px solid #eee; /* Đường kẻ dưới ảnh */
    padding: 10px 0 0 0;
}

.product-image {
    width: 100%;
    height: 100%;
    object-fit: cover; /* Đảm bảo ảnh hiển thị đầy đủ mà không bị cắt */
    margin: 10px; /* Khoảng cách giữa ảnh và viền container */
    /* Remove any default Bootstrap `card-img-top` rounded corners if they conflict */
    border: #dfdada solid 1px; /* Thêm viền xung quanh ảnh */
    border-top-left-radius: 12px;
    border-top-right-radius: 12px;
}

/* --- Phần nội dung Card Body (Tên, giá, nút) --- */
.card-body {
    padding: 15px; /* Điều chỉnh padding cho body */
    text-align: left; /* Căn lề trái cho thông tin */
    flex-grow: 1; /* Cho phép card-body chiếm hết không gian còn lại */
}

.card-title {
    font-size: 1.1em; /* Kích thước tên sản phẩm */
    color: #333; /* Màu tên sản phẩm */
    margin-top: 0;
    margin-bottom: 8px; /* Khoảng cách dưới tên */
    font-weight: 600; /* Đậm hơn một chút */
    line-height: 1.3;
}

.card-text.fw-bold.text-danger { /* Style cho giá */
    font-size: 1.15em; /* Kích thước giá nổi bật */
    color: #e44d26 !important; /* Màu đỏ nổi bật cho giá, dùng !important nếu Bootstrap ghi đè */
    font-weight: bold;
    margin-bottom: 15px !important; /* Tăng khoảng cách dưới giá */
}

/* --- Nút "Xem chi tiết" --- */
.btn-more {
    /* Overwrite Bootstrap .btn defaults */
    width: 100%; /* Nút chiếm toàn bộ chiều rộng có sẵn */
    padding: 10px 15px;
    background-color: #fff; /* Nền trắng */
    color: #333; /* Chữ đen */
    border: 1px solid #ccc; /* Viền xám */
    border-radius: 8px; /* Bo góc nút */
    font-size: 1em;
    cursor: pointer;
    transition: background-color 0.3s ease, border-color 0.3s ease, color 0.3s ease;
    text-decoration: none; /* Bỏ gạch chân cho router-link */
    display: block; /* Đảm bảo nút chiếm toàn bộ chiều rộng */
    text-align: center; /* Căn giữa chữ trong nút */
}

.btn-more:hover {
    background-color: #f0f0f0; /* Nền xám nhẹ khi hover */
    border-color: #a0a0a0; /* Viền đậm hơn khi hover */
    color: #000;
}
</style>
