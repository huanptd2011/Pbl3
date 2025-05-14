<template>
  <div class="add-product-container">
    <div class="card">
      <div class="card-header d-flex justify-content-between align-items-center">
        <span>Thêm Sản phẩm Mới</span>
        <button class="btn btn-secondary btn-sm" @click="goBack">
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
             <small class="form-text text-muted">Tổng số lượng tồn có thể được tự động tính từ các biến thể.</small>
          </div>


          <div class="mb-3">
            <label for="category" class="form-label">Danh mục <span class="text-danger">*</span></label>
            <select class="form-select calenda" id="category" v-model="product.categoryId" required> 
              <option value="">-- Chọn danh mục --</option>
              <option v-for="category in categories" :key="category.id" :value="category.id">
                  {{ category.name }}
              </option>
            </select>
          </div>



          <div class="mb-3 form-check form-switch">
             <input class="form-check-input" type="checkbox" id="isActive" v-model="product.isActive" checked> 
             <label class="form-check-label" for="isActive">Đang bán</label>
          </div>

           <div class="mb-3">
             <label for="productImages" class="form-label">Hình ảnh Sản phẩm</label>

              <input type="file" class="form-control calenda" id="productImages" multiple @change="handleImageUpload">

           </div>

            <div class="mb-3">
                 <label class="form-label">Biến thể (Kích thước/Màu sắc/Số lượng)</label>

                  <div v-for="(variant, index) in product.sizeColorList" :key="index" class="row g-2 mb-2 align-items-center">
                     <div class="col">
                         <input type="text" class="form-control form-control-sm calenda" v-model="variant.color" placeholder="Màu sắc">
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

// --- State lưu dữ liệu form sản phẩm mới ---
const product = ref({
  productName: '',
  productDescription: '',
  brand: '',
  price: 0,
  totalInventory: 0, // Có thể được tính tự động
  categoryId: null, // Lưu ID danh mục được chọn
  isActive: true,   // Mặc định là true
  // imageList: [], // Sẽ xử lý upload file
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
function handleImageUpload(event) {
  // Đây là nơi bạn sẽ xử lý file được chọn
  // Có thể dùng FileReader để hiển thị preview
  // Hoặc lưu file để gửi lên API sau này
  const files = event.target.files;
  console.log('Selected files:', files);
  // Logic xử lý file upload sẽ phức tạp hơn, cần cân nhắc gửi riêng hay gửi cùng form
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
  // Kiểm tra trạng thái submitting để tránh gửi nhiều lần
  if (isSubmitting.value) return;

  // Basic validation (có thể cải thiện sau)
  if (!product.value.productName || !product.value.price || product.value.categoryId === null || product.value.categoryId === '') {
    alert('Vui lòng điền đầy đủ các trường bắt buộc (Tên, Giá, Danh mục).');
    return;
  }

  isSubmitting.value = true; // Bắt đầu submit

  try {
    // Chuẩn bị dữ liệu gửi đi. totalInventory có thể tính lại ở BE.
    // imageList cần xử lý file upload.
    // Giả định API add nhận body là một object với cấu trúc tương tự product ref
    // (không bao gồm các trường như imageList nếu gửi riêng)

    const productData = {
        productName: product.value.productName,
        productDescription: product.value.productDescription,
        brand: product.value.brand,
        price: product.value.price,
        // totalInventory: product.value.totalInventory, // Có thể bỏ nếu BE tự tính
        isActive: product.value.isActive,
        categoryId: product.value.categoryId, // Gửi ID danh mục
        sizeColorList: product.value.sizeColorList.map(v => ({ // Format sizeColorList nếu cần
            color: v.color,
            size: v.size,
            quantity: v.quantity ? parseInt(v.quantity) : 0 // Chắc chắn quantity là số
        })),
        // imageList: ... (Xử lý file upload riêng hoặc gửi base64 nếu API nhận)
    };


    // GỌI API backend để thêm sản phẩm
    const response = await axios.post('http://localhost:8080/api/products/add', productData);

    console.log('Product added successfully:', response.data);

    // Hiển thị thông báo thành công (có thể dùng thư viện toast notification)
    alert('Sản phẩm đã được thêm thành công!');

    // Điều hướng về trang danh sách sản phẩm sau khi thêm thành công
    router.push('/admin/products'); // Đảm bảo khớp với route danh sách sản phẩm

  } catch (error) {
    console.error('Error adding product:', error);
     // Xử lý lỗi từ API (ví dụ: hiển thị thông báo lỗi cụ thể)
    alert('Có lỗi xảy ra khi thêm sản phẩm.');
  } finally {
    isSubmitting.value = false; // Kết thúc submit
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
    /* ... */
}

/* Style cho input, select, textarea */
.form-control.calenda,
.form-select.calenda {
  background-color: #161e39;
  color: #9FB9E1;
  border-color: #1e293b; /* Màu viền */
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
.btn-secondary:hover {
    background-color: #666;
    border-color: #666;
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

</style>