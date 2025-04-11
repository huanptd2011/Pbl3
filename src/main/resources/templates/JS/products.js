

// Khởi tạo trang
//document.addEventListener('DOMContentLoaded', init);

// DOM Elements
const productsContainer = document.getElementById('products-container');
const paginationContainer = document.getElementById('pagination');
const modal = document.getElementById('productModal');
const closeModal = document.querySelector('.close');
const productDetailContent = document.getElementById('product-detail-content');
const applyFiltersBtn = document.getElementById('apply-filters');
const sortBySelect = document.getElementById('sort-by');

let products = [];
// Variables
let currentPage = 1;
const productsPerPage = 12;
let filteredProducts = [...products];

// Initialize the page
function init() {
  displayProducts();
  setupPagination();
  setupEventListeners();
}

const apiUrl = 'http://localhost:8080/ShopNHN/products';
// Hàm fetch sản phẩm từ API
async function fetchProducts() {
    try {
        const response = await fetch(apiUrl); // Thay URL bằng endpoint API của bạn
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        const data = await response.json();
        return data; // Trả về danh sách sản phẩm
    } catch (error) {
        console.error('Lỗi khi fetch sản phẩm:', error);
        return []; // Trả về mảng rỗng nếu có lỗi
    }
}



// Sử dụng hàm fetchProducts để hiển thị sản phẩm
async function init() {
    products = await fetchProducts(); // Lấy dữ liệu sản phẩm từ API
    if (products.length > 0) {
        filteredProducts = products; // Gán dữ liệu vào biến filteredProducts
        displayProducts(); // Hiển thị sản phẩm
        setupPagination(); // Thiết lập phân trang
    } else {
        productsContainer.innerHTML = '<p>Không có sản phẩm nào để hiển thị.</p>';
    }
    setupEventListeners(); // Thiết lập các sự kiện
}
// Display products for current page
//function displayProducts() {
//  productsContainer.innerHTML = '';
//
//  const startIndex = (currentPage - 1) * productsPerPage;
//  const endIndex = startIndex + productsPerPage;
//  const productsToDisplay = filteredProducts.slice(startIndex, endIndex);
//
//  if (productsToDisplay.length === 0) {
//    productsContainer.innerHTML = '<p style="grid-column: 1 / -1; text-align: center;">Không tìm thấy sản phẩm phù hợp</p>';
//    return;
//  }
//
//  productsToDisplay.forEach(product => {
//    const discount = Math.round((1 - product.price / product.originalPrice) * 100);
//
//    const productCard = document.createElement('div');
//    productCard.classList.add('product');
////    productCard.className = 'product-card';
//    productCard.innerHTML = `
//       <div class="product-card">
//      <img src="${product.imageUrlId}" alt="${product.productName}" class="product-image">
//      <div class="product-info">
//        <div class="product-brand">Nike</div>
//        <div class="product-name">${product.productName}</div>
//        <div class="product-price">
//          <span class="current-price">${formatPrice(product.price)}</span>
////          <span class="original-price">${formatPrice(product.originalPrice)}</span>
////          <span class="discount">-${discount}%</span/>
//        </div>
//        <button class="add-to-cart">Thêm vào giỏ hàng</button>
//      </div>
//      </div>
//    `;
//
//    productCard.addEventListener('click', () => showProductDetail(product));
//    productsContainer.appendChild(productCard);
//  });
//}


function displayProducts() {
  productsContainer.innerHTML = '';

  const startIndex = (currentPage - 1) * productsPerPage;
  const endIndex = startIndex + productsPerPage;
  const productsToDisplay = filteredProducts.slice(startIndex, endIndex);

  if (productsToDisplay.length === 0) {
    productsContainer.innerHTML = '<p style="grid-column: 1 / -1; text-align: center;">Không tìm thấy sản phẩm phù hợp</p>';
    return;
  }

  productsToDisplay.forEach(product => {
    const productCard = document.createElement('div');
    productCard.classList.add('product-card');
    productCard.innerHTML = `
      <img src="${product.imageList[1].imageUrl}" alt="${product.productName}" class="product-image">
      <div class="product-info">
        <div class="product-brand">${product.brand}</div>
        <div class="product-name">${product.productName}</div>
        <div class="product-price">
          <span class="current-price">${formatPrice(product.price)}</span>
        </div>
      </div>
    `;

    productCard.addEventListener('click', () => showProductDetail(product));
    productsContainer.appendChild(productCard);
  });
}

// Format price with VND
function formatPrice(price) {
  return new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(price);
}

// Setup pagination
function setupPagination() {
  const pageCount = Math.ceil(filteredProducts.length / productsPerPage);

  paginationContainer.innerHTML = '';

  if (pageCount <= 1) return;

  // Previous button
  if (currentPage > 1) {
    const prevLink = document.createElement('a');
    prevLink.href = '#';
    prevLink.innerHTML = '&laquo;';
    prevLink.addEventListener('click', (e) => {
      e.preventDefault();
      currentPage--;
      displayProducts();
      setupPagination();
      window.scrollTo({ top: 0, behavior: 'smooth' });
    });
    paginationContainer.appendChild(prevLink);
  }

  // Page numbers
  for (let i = 1; i <= pageCount; i++) {
    const pageLink = document.createElement('a');
    pageLink.href = '#';
    pageLink.textContent = i;
    if (i === currentPage) {
      pageLink.className = 'active';
    }
    pageLink.addEventListener('click', (e) => {
      e.preventDefault();
      currentPage = i;
      displayProducts();
      setupPagination();
      window.scrollTo({ top: 0, behavior: 'smooth' });
    });
    paginationContainer.appendChild(pageLink);
  }

  // Next button
  if (currentPage < pageCount) {
    const nextLink = document.createElement('a');
    nextLink.href = '#';
    nextLink.innerHTML = '&raquo;';
    nextLink.addEventListener('click', (e) => {
      e.preventDefault();
      currentPage++;
      displayProducts();
      setupPagination();
      window.scrollTo({ top: 0, behavior: 'smooth' });
    });
    paginationContainer.appendChild(nextLink);
  }
}


// Show product detail modal
function showProductDetail(product) {
//    const discount = Math.round((1 - product.price / product.originalPrice) * 100);

    productDetailContent.innerHTML = `
        <div class="product-detail-images">
            <img src="${product.imageList[0].imageUrl}" alt="${product.productName}" class="main-image" id="main-image">
            <div class="thumbnail-container">
                ${product.imageList.map((imageUrl, index) => `
                    <img src="${imageUrl}" alt="${product.productName}" class="thumbnail" data-index="${index}">
                `).join('')}
            </div>
        </div>
        <div class="product-detail-info">
            <div class="product-detail-brand">${product.brand}</div>
            <h1 class="product-detail-name">${product.ProductName}</h1>
            <div class="product-detail-price">
                <span class="product-detail-current">${formatPrice(product.price)}</span>
//                <span class="product-detail-original">${formatPrice(product.originalPrice)}</span>
//                <span class="product-detail-discount">-${discount}%</span>
            </div>
            <div class="product-detail-description">
                <p>${product.productDescription}</p>
            </div>
            <div class="product-detail-options">
                <div class="option-title">Kích cỡ:</div>
//                <div class="detail-size-options">
//                    ${product.sizes.map(size => `
//                        <div class="detail-size-option">${size}</div>
//                    `).join('')}
//                </div>
            </div>
            <div class="product-detail-options">
                <div class="option-title">Màu sắc:</div>
//                <div class="detail-color-options">
//                    ${product.colors.map(color => `
//                        <div class="detail-color-option" style="background: ${color};"></div>
//                    `).join('')}
//                </div>
            </div>
            <div class="product-detail-buttons">
                <button class="add-to-cart-detail">Thêm vào giỏ hàng</button>
                <button class="buy-now-btn">Mua sản phẩm</button>
            </div>
        </div>
    `;

    // Setup thumbnail click events
    const thumbnails = document.querySelectorAll('.thumbnail');
    const mainImage = document.getElementById('main-image');

    thumbnails.forEach(thumb => {
        thumb.addEventListener('click', () => {
            mainImage.src = thumb.src;
        });
    });

    // Setup size selection
    const sizeOptions = document.querySelectorAll('.detail-size-option');
    sizeOptions.forEach(option => {
        option.addEventListener('click', () => {
            sizeOptions.forEach(opt => opt.classList.remove('selected'));
            option.classList.add('selected');
        });
    });

    // Setup color selection
    const colorOptions = document.querySelectorAll('.detail-color-option');
    colorOptions.forEach(option => {
        option.addEventListener('click', () => {
            colorOptions.forEach(opt => opt.classList.remove('selected'));
            option.classList.add('selected');
        });
    });

    // Handle "Mua sản phẩm" button click
    const buyNowBtn = document.querySelector('.buy-now-btn');
    buyNowBtn.addEventListener('click', () => {
        alert(`Bạn đã chọn mua sản phẩm: ${product.productName}`);
        // Thêm logic xử lý mua sản phẩm tại đây
    });

    // Show the modal
    modal.style.display = 'block';
}

// Filter products based on selected filters
function filterProducts() {
  const selectedBrands = Array.from(document.querySelectorAll('input[name="brand"]:checked')).map(el => el.value);
  const selectedPrice = document.querySelector('input[name="price"]:checked').value;
  const minPrice = document.querySelector('.price-range input[placeholder="Từ"]').value;
  const maxPrice = document.querySelector('.price-range input[placeholder="Đến"]').value;

  filteredProducts = products.filter(product => {
    // Filter by brand
//    if (selectedBrands.length > 0 && !selectedBrands.includes(product.brand.toLowerCase())) {
//      return false;
//    }

    // Filter by price range
    if (selectedPrice !== 'all') {
      switch (selectedPrice) {
        case 'under1m':
          if (product.price >= 1000000) return false;
          break;
        case '1m-2m':
          if (product.price < 1000000 || product.price > 2000000) return false;
          break;
        case '2m-3m':
          if (product.price < 2000000 || product.price > 3000000) return false;
          break;
        case 'over3m':
          if (product.price <= 3000000) return false;
          break;
      }
    }

    // Filter by custom price range
    if (minPrice && product.price < parseInt(minPrice)) return false;
    if (maxPrice && product.price > parseInt(maxPrice)) return false;

    return true;
  });

  // Sort products
  sortProducts();

  // Reset to first page
  currentPage = 1;

  // Update display
  displayProducts();
  setupPagination();
}

// Sort products based on selected option
function sortProducts() {
  const sortBy = sortBySelect.value;

  switch (sortBy) {
    case 'price-asc':
      filteredProducts.sort((a, b) => a.price - b.price);
      break;
    case 'price-desc':
      filteredProducts.sort((a, b) => b.price - a.price);
      break;
    case 'popular':
      // In a real app, this would sort by popularity (sales, views, etc.)
      filteredProducts.sort(() => Math.random() - 0.5);
      break;
    case 'newest':
      // Assuming newer products have higher IDs
      filteredProducts.sort((a, b) => b.id - a.id);
      break;
    default:
      // Default sorting (original order)
      filteredProducts = [...products].filter(p => filteredProducts.some(fp => fp.id === p.id));
      break;
  }
}

// Setup event listeners
function setupEventListeners() {
  // Apply filters button
  applyFiltersBtn.addEventListener('click', filterProducts);

  // Sort select
  sortBySelect.addEventListener('change', () => {
    sortProducts();
    currentPage = 1;
    displayProducts();
    setupPagination();
  });

  // Close modal
  closeModal.addEventListener('click', () => {
    modal.style.display = 'none';
  });

  // Close modal when clicking outside
  window.addEventListener('click', (e) => {
    if (e.target === modal) {
      modal.style.display = 'none';
    }
  });
}

// Đóng modal khi nhấp vào nút "close"
closeModal.addEventListener('click', () => {
    modal.style.display = 'none';
});

// Đóng modal khi nhấp bên ngoài modal
window.addEventListener('click', (e) => {
    if (e.target === modal) {
        modal.style.display = 'none';
    }
});

// Initialize the page
document.addEventListener('DOMContentLoaded', init);