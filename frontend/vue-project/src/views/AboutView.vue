<template>
  <div class="main-container">
    <!-- Header -->
    <header class="header">
      <div class="container">
        <h1 class="header-title">NHN Shoe Shop</h1>
        <p class="header-subtitle">Chất lượng tạo nên thương hiệu</p>
      </div>
    </header>

    <!-- About Section -->
    <section class="section">
      <div class="container">
        <h2 class="section-title">Giới thiệu về NHN</h2>
        <p class="section-text">
          NHN Shoe Shop là cửa hàng chuyên cung cấp các mẫu giày thời trang, chất lượng cao với giá cả hợp lý.
          Chúng tôi cam kết mang đến cho khách hàng những sản phẩm đa dạng từ sneaker, giày thể thao đến giày công sở,
          phù hợp với mọi phong cách và nhu cầu.
        </p>
      </div>
    </section>

    <!-- Why Choose NHN Section -->
    <section class="section why-choose">
      <div class="container">
        <h2 class="section-title">Lý do chọn NHN</h2>
        <div class="reasons-list">
          <div class="reason-item">
            <h5 class="reason-title">Chất lượng đảm bảo</h5>
            <p class="reason-text">Tất cả sản phẩm đều được kiểm định kỹ lưỡng, đảm bảo độ bền và sự thoải mái khi sử dụng.</p>
          </div>
          <div class="reason-item">
            <h5 class="reason-title">Dịch vụ tận tâm</h5>
            <p class="reason-text">Đội ngũ hỗ trợ khách hàng luôn sẵn sàng giải đáp mọi thắc mắc và tư vấn nhiệt tình.</p>
          </div>
          <div class="reason-item">
            <h5 class="reason-title">Ưu đãi hấp dẫn</h5>
            <p class="reason-text">Chương trình khuyến mãi thường xuyên và chính sách đổi trả linh hoạt cho khách hàng.</p>
          </div>
        </div>
      </div>
    </section>

    <!-- Contact Section -->
    <section class="section">
      <div class="container">
        <h2 class="section-title">Liên hệ</h2>
        <ul class="contact-list">
          <li class="contact-item">Địa chỉ: 123 Đường Thời Trang, Quận 1, TP. Hồ Chí Minh</li>
          <li class="contact-item">Hotline: 0909 123 456</li>
          <li class="contact-item">Email: contact@nhnshoes.vn</li>
        </ul>
      </div>
    </section>

    <!-- Footer -->
    <footer class="footer">
      <div class="container">
        <p class="footer-text">© 2025 NHN Shoe Shop. All rights reserved.</p>
      </div>
    </footer>
  </div>
</template>

<script setup>
// Không cần logic phức tạp vì đây là trang tĩnh
</script>

<style scoped>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

.main-container {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  font-family: Arial, sans-serif;
  line-height: 1.6;
}

.container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 15px;
}

/* Header */
.header {
  background-color: #007bff;
  color: white;
  padding: 2rem 0;
}

.header-title {
  font-size: 2.5rem;
  font-weight: bold;
}

.header-subtitle {
  font-size: 1.2rem;
  margin-top: 0.5rem;
}

/* Section */
.section {
  padding: 3rem 0;
}

.section-title {
  font-size: 1.8rem;
  font-weight: 600;
  margin-bottom: 1.5rem;
}

.section-text {
  font-size: 1.1rem;
  color: #555;
  max-width: 800px;
}

/* Why Choose NHN */
.why-choose {
  background-color: #f8f9fa;
}

.reasons-list {
  display: flex;
  flex-wrap: wrap;
  gap: 1.5rem;
}

.reason-item {
  flex: 1 1 300px;
  background: white;
  padding: 1.5rem;
  border-radius: 4px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  text-align: center;
}

.reason-title {
  font-size: 1.2rem;
  font-weight: 600;
  margin-bottom: 0.5rem;
}

.reason-text {
  font-size: 1rem;
  color: #666;
}

/* Contact */
.contact-list {
  list-style: none;
}

.contact-item {
  font-size: 1.1rem;
  color: #555;
  padding: 0.5rem 0;
}

/* Footer */
.footer {
  background-color: #007bff;
  color: white;
  padding: 1.5rem 0;
  margin-top: auto;
}

.footer-text {
  text-align: center;
  font-size: 1rem;
}

/* Responsive */
@media (max-width: 767px) {
  .header-title {
    font-size: 2rem;
  }

  .section-title {
    font-size: 1.5rem;
  }

  .reason-item {
    flex: 1 1 100%;
  }

  .section-text,
  .contact-item {
    font-size: 1rem;
  }
}
</style>
