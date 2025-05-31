<template>
  <div class="add-product-container">
    <div class="card">
      <div class="card-header d-flex justify-content-between align-items-center">
        <span>Thêm Sản phẩm Mới</span>
        <button class="btn bg-main bor-main btn-sm fixed-onright" @click="goBack">
           <i class="fas fa-arrow-left me-1"></i>
           Quay lại
        </button>
      </div>
      <div class="card-body">
        <form @submit.prevent="handleSubmit">

          <div class="mb-3">
            <label for="productName" class="form-label">Tên Sản phẩm <span class="text-danger">*</span></label>
            <input type="text" class="form-control calenda" id="productName" v-model="product.productName" required>
          </div>

          <div class="mb-3">
            <label for="productDescription" class="form-label">Mô tả Sản phẩm</label>
            <textarea class="form-control calenda" id="productDescription" v-model="product.productDescription" rows="3"></textarea>
          </div>

           <div class="mb-3">
            <label for="brand" class="form-label">Thương hiệu</label>
            <input type="text" class="form-control calenda" id="brand" v-model="product.brand">
          </div>

          <div class="mb-3">
            <label for="price" class="form-label">Giá <span class="text-danger">*</span></label>
            <input type="number" class="form-control calenda" id="price" v-model="product.price" required min="0">
          </div>


           <div class="mb-3">
            <label for="totalInventory" class="form-label">Tổng số lượng tồn</label>
            <input type="number" class="form-control calenda" id="totalInventory" v-model="product.totalInventory" min="0">

             <small class="form-text cl-note">Tổng số lượng tồn có thể được tự động tính từ các biến thể.</small>

          </div>


          <div class="mb-3">
            <label for="category" class="form-label">Danh mục <span class="text-danger">*</span></label>
            <select v-model="selectedCategoryId" class="form-select calenda" id="category" required>
              <option value="">-- Chọn danh mục --</option>
              <option v-for="category in categories" :key="category.categoryId" :value="category.categoryId">
                {{ category.categoryName }}
              </option>
            </select>
          </div>



          <div class="mb-3 form-check form-switch">
             <input class="form-check-input" type="checkbox" id="isActive" v-model="product.isActive" checked> 
             <label class="form-check-label" for="isActive">Đang bán</label>
          </div>

           <div class="mb-3">
             <label for="productImages" class="form-label">Hình ảnh Sản phẩm</label>
             <div v-if="product.imageList && product.imageList.length > 0" class="mb-2">
                  <span class="text-muted me-2">Ảnh hiện có:</span>
                  <div class="d-flex flex-nowrap overflow-auto py-2" style="gap: 10px;">
                    <div v-for="(image, index) in product.imageList" :key="index" 
                        class="position-relative flex-shrink-0">
                        <img :src="image.imageUrl" alt="Product Image" 
                            style="width: 100px; height: 100px; object-fit: cover; border: 1px solid #dee2e6;">
                        <button type="button" 
                                class="position-absolute btn btn-danger btn-sm p-0"
                                style="width: 20px; height: 20px; top: -5px; right: -5px;"
                                @click="deleteImage(index)"
                                title="Xóa ảnh">
                            ×
                        </button>
                    </div>
                  </div>
                </div>

              <input type="file" class="form-control calenda" id="productImages" multiple @change="handleImageUpload">

           </div>

            <div class="mb-3">
                 <label class="form-label">Biến thể (Kích thước/Màu sắc/Số lượng)</label>

                  <div v-for="(variant, index) in product.sizeColorList" :key="index" class="row g-2 mb-2 align-items-center">
                     <div class="col">
                         <input type="text" class="form-control form-control-sm calenda cl-note" v-model="variant.color" placeholder="Màu sắc">

                     </div>
                      <div class="col">
                         <input type="text" class="form-control form-control-sm calenda" v-model="variant.size" placeholder="Kích thước">
                     </div>
                       <div class="col">
                         <input type="number" class="form-control form-control-sm calenda" v-model="variant.quantity" placeholder="Số lượng" min="0">
                     </div>
                       <div class="col-auto">
                          <button type="button" class="btn btn-danger btn-sm" @click="removeVariant(index)">
                             <i class="fas fa-times"></i>
                          </button>
                       </div>
                 </div>
                  <button type="button" class="btn btn-outline-secondary btn-sm" @click="addVariant">
                     <i class="fas fa-plus me-1"></i>
                     Thêm biến thể
                  </button>
            </div>


          <button type="submit" class="btn btn-primary" :disabled="isSubmitting" style="background-color: #8b5cf6; border-color: #8b5cf6;">
            <span v-if="isSubmitting" class="spinner-border spinner-border-sm me-2" role="status" aria-hidden="true"></span>
            {{ isSubmitting ? 'Đang thêm...' : 'Thêm Sản phẩm' }}
          </button>
        </form>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import { useRouter } from 'vue-router';

const router = useRouter();
const selectedCategoryId = ref('');

// --- State lưu dữ liệu form sản phẩm mới ---
const product = ref({
  productName: '',
  productDescription: '',
  brand: '',
  price: 0,
  totalInventory: 0, // Có thể được tính tự động
  category: {
    categoryId: null, // Sẽ được cập nhật khi chọn danh mục
    categoryName: '' // Có thể để trống hoặc lấy từ danh mục đã chọn
  }, // Lưu ID danh mục được chọn
  isActive: true,   // Mặc định là true
  imageList: [], // Sẽ xử lý upload file
  sizeColorList: [], // Mảng các biến thể size/color/quantity
});

const isSubmitting = ref(false); // Trạng thái khi đang submit form

// --- State lưu danh sách danh mục (tái sử dụng logic từ ProductManagement) ---
const categories = ref([]);
async function fetchCategories() {
    try {
        const response = await axios.get('http://localhost:8080/api/product-categories');
        categories.value = response.data;
    } catch (error) {
        console.error('Error fetching categories:', error);
        categories.value = [];
        // Xử lý lỗi fetch danh mục
    }
}
// --- End State và Hàm fetch danh mục ---


// --- Hàm xử lý Upload Hình ảnh (Placeholder) ---
async function handleImageUpload(event) {
  const files = event.target.files;
  for (let i = 0; i < files.length; i++) {
    const file = files[i];

    if (!file) return;

    const formData = new FormData();
    formData.append("file", file);

    try {
      const response = await fetch("http://localhost:8080/api/upload-image", {
        method: "POST",
        body: formData,
      });

      if (!response.ok) {
        throw new Error("Upload failed");
      }

      const data = await response.json();
      const imageUrl = data.url;
      product.value.imageList.push({ imageUrl, file }); // Lưu URL tạm thời và file gốc
  
    } catch (error) {
      console.error("Error uploading image:", error);
    }
  }
  console.log('Uploaded images:', product.value.imageList);
  event.target.value = ''; // Cho phép chọn lại cùng file
}
// --- Hàm xử lý Xóa Hình ảnh ---
async function deleteImage(index) {
    if (!confirm('Bạn có chắc chắn muốn xóa ảnh này?')) {
        return;
    }
    try {  
        // Xóa ảnh khỏi danh sách hiển thị
        product.value.imageList.splice(index, 1);
    } catch (error) {
        console.error('Error deleting image:', error);
        alert('Có lỗi xảy ra khi xóa ảnh');
    }
}

// --- Hàm xử lý Biến thể (Size/Color/Quantity) ---
function addVariant() {
  // Thêm một đối tượng biến thể mới vào mảng
  product.value.sizeColorList.push({ color: '', size: '', quantity: 0 });
}

function removeVariant(index) {
  // Xóa đối tượng biến thể tại index được chỉ định
  product.value.sizeColorList.splice(index, 1);
}


// --- Hàm xử lý Submit Form ---
async function handleSubmit() {
  if (isSubmitting.value) return;

  if (!product.value.productName || !product.value.price || selectedCategoryId.value === null) {
    alert('Vui lòng điền đầy đủ các trường bắt buộc (Tên, Giá, Danh mục).');
    return;
  }

  isSubmitting.value = true; 

  try {    
    const selectedCategory = categories.value.find(c => c.categoryId === parseInt(selectedCategoryId.value));
    console.log('Selected Category:', selectedCategory);

    const productData = {
        productName: product.value.productName,
        productDescription: product.value.productDescription,
        brand: product.value.brand,
        price: product.value.price,
        isActive: product.value.isActive,
        category: {
            categoryId: parseInt(selectedCategory.categoryId),
            categoryName: selectedCategory.categoryName 
        },
        sizeColorList: product.value.sizeColorList.map(v => ({ 
            color: v.color,
            size: v.size,
            quantity: v.quantity ? parseInt(v.quantity) : 0 
        })),
        imageList: product.value.imageList
    };
    console.log('Product Data to Submit:', productData.category);

    const response = await axios.post('http://localhost:8080/api/products/add', productData);

    console.log('Product added successfully:', response.data);

    alert('Sản phẩm đã được thêm thành công!');

    router.push('/admin/products');

  } catch (error) {
    console.error('Error adding product:', error);
    alert('Có lỗi xảy ra khi thêm sản phẩm.');
  } finally {
    isSubmitting.value = false;
  }
}

// Hàm quay lại trang trước (ví dụ: trang danh sách sản phẩm)
function goBack() {
  router.back(); // Quay lại trang trước đó
   // Hoặc router.push('/admin/products'); nếu luôn muốn quay về trang danh sách
}


// Fetch danh sách danh mục khi component được mount
onMounted(() => {
  fetchCategories();
});

</script>

<style scoped>
/* Sử dụng lại các style chung cho card từ dashboard nếu muốn */
.card {
  background-color: #0b1739;
  border-radius: 12px;
  box-shadow: #0105114d 0px 8px 28px 0px;
  color: #fff;
  /* ... các style khác của card */
}

.card-header {
     background-color: #0b1739;
     border-bottom: 1px solid #1e293b;
     color: #aeb9e1; /* Màu chữ header */
     /* ... */
}

.card-body {
}

/* Style cho input, select, textarea */
.form-control.calenda,
.form-select.calenda {
  background-color: #161e39;
  color: #9FB9E1;
  border-color: #435168; /* Màu viền */
}

/* Style cho label */
.form-label {
    color: #aeb9e1;
}

/* Style cho checkbox/switch */
.form-check-input:checked {
    background-color: #8b5cf6; /* Màu khi checked */
    border-color: #8b5cf6;
}

.form-check-label {
    color: #aeb9e1;
}

/* Style cho nút submit */
.btn-primary {
   background-color: #8b5cf6;
   border-color: #8b5cf6;
}
.btn-primary:hover {
     background-color: #7c3aed;
     border-color: #7c3aed;
}

/* Style cho nút quay lại */
.btn-secondary {
   background-color: #555;
   border-color: #555;
   color: #fff;
}


.fixed-onright{
    position: fixed;
    right: 45px;
    top: 30px;
}

/* Style cho nút xóa biến thể */
.btn-danger {
    /* Màu đỏ mặc định hoặc tùy chỉnh */
}

/* Style cho nút thêm biến thể */
.btn-outline-secondary {
    color: #aeb9e1;
    border-color: #aeb9e1;
}
.btn-outline-secondary:hover {
    background-color: rgba(174, 185, 225, 0.1);
    color: #fff;
}


.position-relative {
    position: relative;
}
.position-absolute {
    position: absolute;
}
.top-0 {
    top: 0;
}
.end-0 {
    right: 0;
}

</style>