const apiUrl = 'http://localhost:8080/ShopNHN/products/new';

const productsContainer = document.getElementById('products-container');

// Hàm để lấy dữ liệu sản phẩm từ API
async function fetchProducts() {
    try {
        // Gửi yêu cầu GET đến API
        const response = await fetch(apiUrl);

        // Kiểm tra nếu yêu cầu thành công
        if (!response.ok) {
            throw new Error('Không thể lấy dữ liệu sản phẩm');
        }

        // Chuyển dữ liệu JSON thành đối tượng
        const products = await response.json();

        // Gọi hàm để hiển thị sản phẩm
        displayProducts(products);
    } catch (error) {
        console.error('Lỗi:', error);
    }
}

function displayProducts(products) {
  productsContainer.innerHTML = '';

  products.forEach(product => {
    const productCard = document.createElement('div');
    productCard.classList.add('product-card');
    productCard.innerHTML = `
      <img src="${product.imageList[0].imageUrl}" alt="${product.productName}" class="product-image">
      <div class="product-info">
        <div class="product-brand">${product.brand}</div>
        <div class="product-name">${product.productName}</div>
        <div class="product-price">
          <span class="current-price">${formatPrice(product.price)}</span>
        </div>
      </div>
    `;

    productsContainer.appendChild(productCard);
  });
}

// Format price with VND
function formatPrice(price) {
  return new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(price);
}


window.onclick = function(event) {
    const modal = document.getElementById('productModal');
    if (event.target === modal) {
        closeModal();
    }
}

// Gọi hàm fetchProducts để lấy dữ liệu khi trang tải
window.onload = fetchProducts;


// JavaScript cho bộ lọc
  document.querySelectorAll('.color-option').forEach(option => {
      option.addEventListener('click', function() {
          document.querySelectorAll('.color-option').forEach(opt => opt.classList.remove('selected'));
          this.classList.add('selected');
      });
  });

  document.querySelectorAll('.size-option').forEach(option => {
      option.addEventListener('click', function() {
          document.querySelectorAll('.size-option').forEach(opt => opt.classList.remove('selected'));
          this.classList.add('selected');
      });
  });


  /////////
  // Banner Slider Functionality
let currentSlide = 0;
const slides = document.querySelectorAll('.banner-slide');
const dots = document.querySelectorAll('.dot');

function showSlide(n) {
    slides.forEach(slide => slide.classList.remove('active'));
    dots.forEach(dot => dot.classList.remove('active'));
    
    currentSlide = (n + slides.length) % slides.length;
    
    slides[currentSlide].classList.add('active');
    dots[currentSlide].classList.add('active');
}

function nextSlide() {
    showSlide(currentSlide + 1);
}

function prevSlide() {
    showSlide(currentSlide - 1);
}

// Auto slide change
let slideInterval = setInterval(nextSlide, 5000);

// Event listeners for controls
document.querySelector('.next-btn').addEventListener('click', () => {
    clearInterval(slideInterval);
    nextSlide();
    slideInterval = setInterval(nextSlide, 5000);
});

document.querySelector('.prev-btn').addEventListener('click', () => {
    clearInterval(slideInterval);
    prevSlide();
    slideInterval = setInterval(nextSlide, 5000);
});

// Event listeners for dots
dots.forEach((dot, index) => {
    dot.addEventListener('click', () => {
        clearInterval(slideInterval);
        showSlide(index);
        slideInterval = setInterval(nextSlide, 5000);
    });
});

// Pause on hover
document.querySelector('.banner-container').addEventListener('mouseenter', () => {
    clearInterval(slideInterval);
});

document.querySelector('.banner-container').addEventListener('mouseleave', () => {
    slideInterval = setInterval(nextSlide, 5000);
});