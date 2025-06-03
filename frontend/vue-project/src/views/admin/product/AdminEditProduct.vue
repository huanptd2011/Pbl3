<template>
  <div class="edit-product-container">
    <div class="card">
      <div class="card-header d-flex justify-content-between align-items-center">
        <span>Sửa Sản phẩm</span>
        <button class="btn btn-secondary btn-sm fixed-onright" @click="goBack">
           <i class="fas fa-arrow-left me-1"></i>
           Quay lại
        </button>
      </div>
      <div class="card-body">
        <div v-if="loadingProduct" class="text-center text-muted">Đang tải thông tin sản phẩm...</div>
        <div v-else-if="!product || !product.productId" class="text-center text-danger">Không tìm thấy thông tin sản phẩm.</div>
        <form v-else @submit.prevent="handleSubmit">


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
            <input type="number" class="form-control calenda" id="totalInventory" v-model="product.totalInventory" min="0" readonly>
             <small class="form-text text-muted">Tổng số lượng tồn có thể được tự động tính từ các biến thể.</small>
          </div>


          <div class="mb-3">
            <label for="category" class="form-label">Danh mục <span class="text-danger">*</span></label>
            <select class="form-select calenda" id="category" v-model="product.categoryId" required>
              <option v-for="category in categories" :key="category.categoryId" :value="category.categoryId">
                  {{ category.categoryName }}
              </option>
            </select>
          </div>



          <div class="mb-3 form-check form-switch">
             <input class="form-check-input" type="checkbox" id="isActive" :checked="product.isActive"   @change="changeActiveProduct(product)">
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

                  <div v-for="(variant, index) in product.sizeColorList.filter(v => v.isActive !== false)" :key="index" class="row g-2 mb-2 align-items-center">
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
            {{ isSubmitting ? 'Đang lưu...' : 'Lưu Thay đổi' }}
          </button>
        </form>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import { useRouter, useRoute } from 'vue-router'; // Import useRoute để lấy params từ route

const router = useRouter();
const route = useRoute(); // Sử dụng useRoute để truy cập route hiện tại

// --- State lưu dữ liệu form sản phẩm cần sửa ---
// Khởi tạo với cấu trúc dự kiến từ API response
const product = ref({
  productId: null,
  productName: '',
  productDescription: '',
  brand: '',
  price: 0,
  totalInventory: 0,
  category: null, // object category
  categoryId: '', // chỉ dùng để binding vào <select>
  isActive: true,
  sizeColorList: [],
  imageList: [],
});

const listImageDelete = ref([]);
const listImageAdd = ref([]);
const loadingProduct = ref(true); // Trạng thái tải dữ liệu sản phẩm hiện tại
const isSubmitting = ref(false); // Trạng thái khi đang submit form lưu

// --- State lưu danh sách danh mục (tái sử dụng) ---
const categories = ref([]);
async function fetchCategories() { /* ... (code hàm fetchCategories giữ nguyên) ... */
    try {
        const response = await axios.get('http://localhost:8080/api/product-categories');
        categories.value = response.data;
        console.log('Fetched categories:', categories.value);
    } catch (error) {
        console.error('Error fetching categories:', error);
        categories.value = [];
    }
}


// --- Hàm fetch dữ liệu Sản phẩm cần sửa ---
async function fetchProductData(productId) {
    loadingProduct.value = true; // Bắt đầu tải dữ liệu sản phẩm

    try {
        // GỌI API backend để lấy thông tin sản phẩm theo ID
        const response = await axios.get(`http://localhost:8080/api/products/${productId}`); // Lưu ý lỗi chính tả: producs -> products?
        // Giả định API trả về object ProductResponse

        // Gán dữ liệu fetch được vào state product
        // Đảm bảo tên trường khớp với API response
        product.value = response.data;

         // Chuyển đổi các trường số về đúng kiểu nếu cần (API có thể trả về string)
         if (product.value.price) product.value.price = parseFloat(product.value.price);
         if (product.value.totalInventory) product.value.totalInventory = parseInt(product.value.totalInventory);
         if (product.value.sizeColorList && Array.isArray(product.value.sizeColorList)) {
             product.value.sizeColorList = product.value.sizeColorList.map(variant => ({
                  ...variant,
                  quantity: variant.quantity ? parseInt(variant.quantity) : 0
             }));
         }
         // Gán categoryId để dùng cho v-model trong select
        product.value.categoryId = product.value.category?.categoryId || '';

        // (Tùy chọn) Đồng bộ tên danh mục nếu cần
          if (product.value.categoryId) {
              const category = categories.value.find(c => c.categoryId === product.value.categoryId);
              if (category) {
                  product.value.category.categoryName = category.categoryName;
              } else {
                  console.warn(`Category ID ${product.value.categoryId} not found in categories list.`);
              }
          }
        console.log('Fetched product data for editing:', product.value);

    } catch (error) {
        console.error(`Error fetching product data for ID ${productId}:`, error);
        // Xử lý lỗi: có thể điều hướng về trang 404 hoặc hiển thị thông báo lỗi
         alert('Không thể tải thông tin sản phẩm.');
         // router.push('/admin/products'); // Ví dụ: điều hướng về trang danh sách nếu không tìm thấy
    } finally {
        loadingProduct.value = false; // Kết thúc tải dữ liệu sản phẩm
    }
}

// --- Hàm xử lý Xóa ảnh ---
async function deleteImage(index) {
    if (!confirm('Bạn có chắc chắn muốn xóa ảnh này?')) {
        return;
    }

    try {
        const image = product.value.imageList[index];

        if (image.isNew) {
          // Ảnh mới upload chưa lưu DB => xóa khỏi listImageAdd
          const idx = listImageAdd.value.indexOf(image.imageUrl);
          if (idx !== -1) listImageAdd.value.splice(idx, 1);
        } else {
          // Ảnh đã lưu DB => thêm vào listImageDelete để gửi xuống backend
          if (!listImageDelete.value.includes(image.imageUrl)) {
            listImageDelete.value.push(image.imageUrl);
          }
        }
        product.value.imageList.splice(index, 1);
    } catch (error) {
        console.error('Error deleting image:', error);
        alert('Có lỗi xảy ra khi xóa ảnh');
    }
}

// --- Hàm xử lý Upload Hình ảnh (Placeholder) ---
// Logic có thể khác so với add (ví dụ: hiển thị ảnh cũ, xóa ảnh cũ, thêm ảnh mới)
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
      listImageAdd.value.push(imageUrl); // Lưu URL ảnh mới vào danh sách
      product.value.imageList.push({ imageUrl, file, isNew: true }); // Lưu URL tạm thời và file gốc
  
    } catch (error) {
      console.error("Error uploading image:", error);
    }
  }
  event.target.value = ''; // Cho phép chọn lại cùng file
}


// --- Hàm xử lý Biến thể (Size/Color/Quantity) ---
// Các hàm này tương tự AddProduct.vue
function changeActiveProduct() {
  product.value.isActive = !product.value.isActive;
  console.log('Product active status changed to:', product.value.isActive);
}
function addVariant() {
  product.value.sizeColorList.push({ color: '', size: '', quantity: 0 });
}

function removeVariant(index) {
  const variant = product.value.sizeColorList[index];
  if (variant) {
    variant.isActive = false; // "Xóa mềm"
    console.log('Marked variant inactive at index:', index);
  }
}


// --- Hàm xử lý Submit Form (Lưu) ---
async function handleSubmit() {
  if (isSubmitting.value) return;

  // Basic validation
   if (!product.value.productName || !product.value.price || product.value.categoryId === null || product.value.categoryId === '') {
    alert('Vui lòng điền đầy đủ các trường bắt buộc (Tên, Giá, Danh mục).');
    return;
  }

  isSubmitting.value = true; // Bắt đầu submit

  try {
    if(listImageDelete.value !== null) {
        for (const urlImage of listImageDelete.value) {
            // Gọi API xóa ảnh nếu cần
            await axios.delete(`http://localhost:8080/api/product-images/delete/${product.value.productId}`, {
              params: {
                imageUrl: urlImage
              }
            });
        }
        console.log('Deleted images:', listImageDelete.value);
    }
    if(listImageAdd.value !== null) {
        for (const urlImage of listImageAdd.value) {
            // Gọi API thêm ảnh nếu cần
            await axios.post(`http://localhost:8080/api/product-images/add/${product.value.productId}`, 
            { 
                productId: product.value.productId,
                imageUrl: urlImage
            });
        }
    }
    const productData = {
        // Không gửi productId trong body nếu API chỉ lấy từ PathVariable
        productName: product.value.productName,
        productDescription: product.value.productDescription,
        brand: product.value.brand,
        price: product.value.price ? parseFloat(product.value.price) : 0,
        totalInventory: product.value.totalInventory ? parseInt(product.value.totalInventory) : 0,
        isActive: product.value.isActive,
        category: {
            categoryId: product.value.categoryId, // Chỉ gửi ID danh mục
            categoryName: categories.value.find(c => c.categoryId === product.value.categoryId)?.categoryName || ''
        },
        sizeColorList: product.value.sizeColorList.map(v => ({
            color: v.color,
            size: v.size,
            quantity: v.quantity ? parseInt(v.quantity) : 0,
            isActive: v.isActive !== false // Chỉ gửi nếu isActive là true hoặc không có trường này
        })),
    };


    // GỌI API backend để CẬP NHẬT sản phẩm (PUT request)
    // URL cần có productId
    const response = await axios.put(`http://localhost:8080/api/products/edit/${product.value.productId}`, productData);

    console.log('Product updated successfully:', response.data);

    // Hiển thị thông báo thành công
    alert('Sản phẩm đã được cập nhật thành công!');

    // Điều hướng về trang danh sách sản phẩm sau khi lưu thành công
    router.push('/admin/products');

  } catch (error) {
    console.error('Error updating product:', error);
     // Xử lý lỗi từ API
    alert('Có lỗi xảy ra khi cập nhật sản phẩm.');
  } finally {
    isSubmitting.value = false; // Kết thúc submit
  }
}

// Hàm quay lại trang trước
function goBack() {
  router.back();
}

// --- Lấy ID sản phẩm từ route và fetch dữ liệu khi component mount ---
onMounted(() => {

  // Lấy ID sản phẩm từ tham số route
  // Đảm bảo tên tham số khớp với cấu hình router (:id)
  const productId = route.params.id;

  if (productId) {
    // Lưu ID sản phẩm vào state product để dùng khi submit
    product.value.productId = productId;
    // Fetch dữ liệu sản phẩm dựa trên ID
    fetchProductData(productId);
    // Fetch danh sách danh mục
    fetchCategories();
  } else {
    console.error("Product ID is missing from route parameters.");
     alert("Không tìm thấy ID sản phẩm để sửa.");
     // Điều hướng về trang danh sách nếu không có ID
     router.push('/admin/products');
  }
});




</script>

<style scoped>
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

.fixed-onright{
    position: fixed;
    right: 55px;
    top: 30px;
}


</style>
