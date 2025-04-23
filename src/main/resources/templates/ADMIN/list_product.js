const token = localStorage.getItem("authToken");
let currentPage = 0;
const pageSize = 8;
let totalPages = 1;
// Pagination handlers
prevPage.addEventListener('click', function() {
    if (currentPage > 0) {
        currentPage--;
        loadProducts();
    }
});

nextPage.addEventListener('click', function() {
    if (currentPage < totalPages - 1) {
        currentPage++;
        loadProducts();
    }
});

// Search handler
searchButton.addEventListener('click', function() {
    currentPage = 0;
    loadProducts();
});

// Refresh handler
refreshProductList.addEventListener('click', function() {
    currentPage = 0;
    searchKeyword.value = '';
    searchCategory.value = '';
    loadProducts();
});
function loadProducts() {
    
    const keyword = searchKeyword.value;
    const category = searchCategory.value;
    
    // Build query parameters
    let params = new URLSearchParams();
    params.append('page', currentPage);
    console.log(currentPage);
    params.append('size', pageSize);
    console.log(pageSize);

    if (keyword) params.append('keyword', keyword);
    if (category) params.append('category', category);
    fetch(`http://localhost:8080/products/search?${params.toString()}`, {
        headers: {
            'Authorization': `Bearer ${token}`
        }
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        return response.json();
    })
    .then(data => {
        // Update total products count
        totalProductsElement.textContent = data.totalElements || 0;
        const ccc = data.content;

        // Update pagination info
        totalPages = data.totalPages || 1;
        document.getElementById('pageInfo').textContent = `${currentPage + 1}/${totalPages}`;
        
        // Clear table
        productTableBody.innerHTML = '';
        
        // Add products to table
        if (data.content && data.content.length > 0) {
            data.content.forEach(product => {
                // Kiểm tra sizeColorList có tồn tại không và có dữ liệu không
                console.log(product.inventoryList);
                const stockQuantity = product.inventoryList?.reduce((total, item) => {
                    // Kiểm tra item.quantity có phải là số không
                    console.log(item.quantity);
                    return total + (typeof item.quantity === 'number' ? item.quantity : 0);
                }, 0) || 0;
        
                // Kiểm tra stockQuantity đã tính đúng chưa
                console.log(stockQuantity);
        
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td>${product.productId}</td>
                    <td>${product.productName}</td>
                    <td>${product.brand || 'N/A'}</td>
                    <td>${product.price ? product.price.toLocaleString() + 'đ' : 'N/A'}</td>
                    <td>${stockQuantity}</td>
                    <td><span class="status ${product.status === 'ACTIVE' ? 'status-active' : 'status-inactive'}">
                        ${product.status === 'ACTIVE' ? 'Đang bán' : 'Ngừng bán'}</span>
                    </td>
                    <td>
                           <button class="btn btn-edit" onclick="openEditProductModal('${product.productId}')"><i class="fas fa-edit"></i></button>
                        <button class="btn btn-delete" onclick="deleteProduct(${product.productId})"><i class="fas fa-trash-alt"></i></button>
                    </td>
                `;
                productTableBody.appendChild(row);
            });


        } else {
            const row = document.createElement('tr');
            row.innerHTML = '<td colspan="8" class="text-center">Không có sản phẩm nào</td>';
            productTableBody.appendChild(row);
        }
    })
    .catch(error => {
        console.error('Error:', error);
        productTableBody.innerHTML = '<tr><td colspan="8" class="text-center">Lỗi khi tải dữ liệu</td></tr>';
    });
}
// 
async function loadEditProductForm(productId) {
    if (!token) {
        alert("Bạn cần đăng nhập để thực hiện thao tác này!");
        return;
    }

    try {
        // Fetch product details
        const response = await fetch(`http://localhost:8080/products/${productId}`, {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });

        if (!response.ok) {
            throw new Error(`Lỗi khi tải thông tin sản phẩm: ${response.statusText}`);
        }

        const product = await response.json();

        // Populate the form with product data
        document.getElementById('productName').value = product.productName || '';
        document.getElementById('brand').value = product.brand || '';
        document.getElementById('category').value = product.categoryId || '';
        document.getElementById('price').value = product.price || '';
        document.getElementById('productDescription').value = product.description || '';

        // Show the edit form
        const pageTitle = document.querySelector('.header-left h2');
        const addProductContent = document.getElementById('add-product-content');
        const listProductContent = document.getElementById('list-product-content');
        const addProductLink = document.getElementById('add-product-link');

        // Hide all sections
        document.querySelectorAll('.content > div').forEach(section => {
            section.style.display = 'none';
        });

        // Show add product form
        pageTitle.textContent = 'Chỉnh sửa sản phẩm';
        addProductContent.style.display = 'block';

        // Update active menu
        document.querySelectorAll('.sidebar-menu a').forEach(link => {
            link.classList.remove('active');
        });
        addProductLink.classList.add('active');

        // Handle form submission
        const productForm = document.getElementById('productForm');
        productForm.onsubmit = async function(e) {
            e.preventDefault();
            await handleProductUpdate(productId);
        };

    } catch (error) {
        console.error('Error fetching product details:', error);
        alert(`Không thể tải thông tin sản phẩm: ${error.message}`);
    }
}

let currentProductId = null;

async function openEditProductModal(productId) {
  currentProductId = productId;
  const modal = document.getElementById('editProductModal');

  try {
    // Hiển thị loading
    modal.style.display = 'block';

    // Lấy dữ liệu sản phẩm và danh mục
    const [product, categories] = await Promise.all([
      fetchProduct(productId),
      fetchCategories()
    ]);

    // Điền dữ liệu vào form
    document.getElementById('editProductName').value = product.productName || '';
    document.getElementById('editBrand').value = product.brand || '';
    document.getElementById('editPrice').value = product.price || '';
    document.getElementById('editDescription').value = product.productDescription || '';

    // Điền danh mục
    const categorySelect = document.getElementById('editCategory');

    if (!categorySelect) {
      console.error("Element with ID 'editCategory' not found in the DOM.");
      return;
    }

    categorySelect.innerHTML = '<option value="">Chọn danh mục</option>';

    if (!Array.isArray(categories) || categories.length === 0) {
      console.error("Categories array is either not defined or empty.");
      return;
    }

    categories.forEach(category => {
      if (category && category.categoryId && category.categoryName) {
        const option = document.createElement('option');
        option.value = category.categoryId;
        option.textContent = category.categoryName;
        if (product && product.categoryId && category.categoryId === product.categoryId) {
          option.selected = true;
        }
        categorySelect.appendChild(option);
      } else {
        console.warn("Invalid category object:", category);
      }
    });

    // Hiển thị biến thể
    const variantsContainer = document.getElementById('variantsContainer');
    variantsContainer.innerHTML = '';
    
    if (Array.isArray(product.inventoryList) && product.inventoryList.length > 0) {
      product.inventoryList.forEach(item => {
        const div = document.createElement('div');
        div.className = 'variant-item';
        div.innerHTML = `
          <input type="text" class="variant-name" value="${item.color || ''}" placeholder="Màu sắc" />
          <input type="text" class="variant-value" value="${item.size || ''}" placeholder="Kích thước" />
          <input type="text" class="variant-value" value="${item.quantity || ''}" placeholder="Số lượng" />
          <button type="button" onclick="removeVariant(this)">Xóa</button>
        `;
        variantsContainer.appendChild(div);
      });
    }
    

  } catch (error) {
    alert(`Lỗi tải dữ liệu: ${error.message}`);
    closeEditModal();
  }
}

function closeEditModal() {
  document.getElementById('editProductModal').style.display = 'none';
  currentProductId = null;
}

async function handleProductSubmit(event) {
  event.preventDefault();

  const productData = {
    productName: document.getElementById('editProductName').value.trim(),
    brand: document.getElementById('editBrand').value.trim(),
    categoryId: document.getElementById('editCategory').value,
    price: parseFloat(document.getElementById('editPrice').value),
    productDescription: document.getElementById('editDescription').value.trim()
  };

  // Lấy thông tin các biến thể
  const variantItems = document.querySelectorAll('#variantsContainer .variant-item');

  variantItems.forEach(item => {
    const name = item.querySelector('.variant-name')?.value.trim();
    const value = item.querySelector('.variant-value')?.value.trim();
    const quantity = item.querySelector('.variant-quantity')?.value.trim();
    if (name && value && quantity) {
        productData.inventoryList.append({ name, value , quantity });
    }
  });
  // Validate
  if (!productData.productName || !productData.categoryId || isNaN(productData.price)) {
    return alert('Vui lòng điền đầy đủ thông tin bắt buộc');
  }

  const token = localStorage.getItem("authToken");
  try {
    const response = await fetch(`http://localhost:8080/products/edit/${currentProductId}`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`
      },
      body: JSON.stringify(productData)
    });

    if (!response.ok) throw new Error('Cập nhật thất bại');

    alert('Cập nhật thành công!');
    closeEditModal();
    if (typeof reloadProductList === 'function') reloadProductList();

  } catch (error) {
    alert(`Lỗi: ${error.message}`);
  }
}

function addVariant() {
    const container = document.getElementById('variantsContainer');
    const div = document.createElement('div');
    div.className = 'variant-item';
    div.innerHTML = `
      <input type="text" class="variant-name" placeholder="Màu sắc" />
      <input type="text" class="variant-value" placeholder="Kích thước" />
      <input type="text" class="variant-quantity" placeholder="Số lượng" />
      <button type="button" onclick="removeVariant(this)">Xóa</button>
    `;
    container.appendChild(div);
}

  // Hàm xóa biến thể
  function removeVariant(button) {
    const item = button.closest('.variant-item');
    const color = item.querySelector('.variant-name').value;
    const size = item.querySelector('.variant-value').value;
    if (item){ 
        if(deleteVariant(currentProductId, color, size)) {
            item.remove();
        }
    }
  }

  // Lấy thông tin các biến thể
  const variantItems = document.querySelectorAll('#variantsContainer .variant-item');
  variantItems.forEach(item => {
    const name = item.querySelector('.variant-name')?.value.trim();
    const value = item.querySelector('.variant-value')?.value.trim();
    const quantity = item.querySelector('.variant-quantity')?.value.trim();
    if (name && value && quantity) {
        productData.inventoryList.append({ name, value , quantity });
        saveVariant(productData.productId);
    }
    
  });
  
  // Hàm lưu thông tin biến thể mới (gửi request API)
  async function saveVariant(productId) {
    const variants = document.querySelectorAll('.variant-item');
    
    for (const variant of variants) {
      const color = variant.querySelector('.variant-name').value;
      const size = variant.querySelector('.variant-value').value;
      const quantity = variant.querySelector('.variant-quantity').value;
  
      if (!color || !size || !quantity) continue;
  
      const requestBody = {
        quantity: parseInt(quantity),
      };
  
      try {
        const response = await fetch(`http://localhost:8080/api/product-inventory/update/${productId}/${color}/${size}`, {
          method: 'PUT',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify(requestBody),
        });
        if (!response.ok) {
          throw new Error('Failed to update inventory');
        }
        alert('Cập nhật tồn kho thành công!');
      } catch (error) {
        console.error(error);
        alert('Có lỗi xảy ra khi cập nhật tồn kho');
      }
    }
  }
  
  async function deleteVariant(productId, color, size) {
    try {
      const response = await fetch(`http://localhost:8080/api/product-inventory/delete/${productId}/${color}/${size}`, {
        method: 'DELETE',
        headers: {
          'Content-Type': 'application/json',
        },
      });
  
      if (!response.ok) {
        const errorData = await response.json();
        console.error('Error details:', errorData);
        throw new Error('Failed to delete inventory');
      }
      alert('Xóa tồn kho thành công!');
    } catch (error) {
      console.error('Error:', error);
      alert('Có lỗi xảy ra khi xóa tồn kho');
    }
  }
  


// Hàm hỗ trợ
async function fetchProduct(productId) {
  const response = await fetch(`http://localhost:8080/products/${productId}`, {
    headers: { 'Authorization': `Bearer ${token}` }
  });
  if (!response.ok) throw new Error('Không tải được sản phẩm');
  return response.json();
}

async function fetchCategories() {
  const response = await fetch("http://localhost:8080/api/product-categories", {
    headers: {
      "Authorization": `Bearer ${token}`
    }
  });
  if (!response.ok) throw new Error('Không tải được danh mục');
  return response.json();
}

// Đóng popup khi click bên ngoài
window.onclick = function(event) {
  const modal = document.getElementById('editProductModal');
  if (event.target === modal) closeEditModal();
}

 // Delete product
 function deleteProduct(productId) {
    if (confirm('Bạn có chắc chắn muốn xóa sản phẩm này?')) {
        const token = localStorage.getItem("authToken");
        
        fetch(`http://localhost:8080/products/delete/${productId}`, {
            method: 'DELETE',
            headers: {
                'Authorization': `Bearer ${token}`
            }
        })
        .then(response => {
            if (response.ok) {
                alert('Sản phẩm đã được xóa thành công!');
                loadProducts();
            } else {
                alert('Có lỗi xảy ra khi xóa sản phẩm');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Có lỗi xảy ra khi xóa sản phẩm');
        });
    }
}