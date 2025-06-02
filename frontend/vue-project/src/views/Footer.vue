<template>
  <footer class="footer">
    <div class="footer-content">
      <div class="social-icons">
        <a href="#" aria-label="Facebook"><i class="fab fa-facebook-f"></i></a>
        <a href="#" aria-label="Twitter"><i class="fab fa-twitter"></i></a>
        <a href="#" aria-label="Instagram"><i class="fab fa-instagram"></i></a>
      </div>
      <nav class="footer-nav">
        <ul>
          <li><a href="#dc">123 Đường thời trang, Quận 1, TP. Hồ CHí Minh</a></li>
          <li><a href="#hotline">0909 123 456</a></li>
          <li><a href="#email">contact@nhnshoes.vn</a></li>
        </ul>
      </nav>
      <router-link to="/about" class="contact-button">Về chúng tôi</router-link>
      <hr class="footer-divider">
      <p class="copyright">&copy; 2021 All Rights Reserved</p>
    </div>
  </footer>
</template>

<script>
export default {
  name: 'AppFooter'
}
</script>

<style scoped>
/* Import Font Awesome for social icons (You might also import this globally in your main.js or App.vue) */
/* @import url('https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css'); */

.footer {
  position: relative;
    background-color: #000; /* Màu nền đen như trong hình */
    color: #fff; /* Màu chữ trắng */
    padding: 20px 20px 0 20px; /* Khoảng cách đệm trên dưới và hai bên */
    text-align: center; /* Căn giữa nội dung */
    font-family: Arial, sans-serif; /* Font chữ */
    width: 100%;
    box-sizing: border-box; /* Đảm bảo padding và border không làm tăng kích thước */
}

.footer-content {
    max-width: 1200px; /* Chiều rộng tối đa của nội dung, căn giữa */
    margin: 0 auto;
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 10px; /* Khoảng cách giữa các phần tử chính */
}

.social-icons {
    display: flex;
    gap: 20px; /* Khoảng cách giữa các icon */
    margin-bottom: 5px; /* Khoảng cách dưới social icons */
}

.social-icons a {
    color: #fff;
    font-size: 24px; /* Kích thước icon */
    transition: color 0.3s ease; /* Hiệu ứng chuyển màu khi hover */
}

.social-icons a:hover {
    color: #bbb; /* Màu khi hover */
}

.footer-nav ul {
    list-style: none; /* Bỏ dấu chấm của list */
    padding: 0;
    margin: 0;
    display: flex; /* Hiển thị các mục menu trên một hàng */
    gap: 30px; /* Khoảng cách giữa các mục menu */
    flex-wrap: wrap; /* Cho phép xuống dòng nếu màn hình nhỏ */
    justify-content: center;
   
}

.footer-nav a {
    color: #fff;
    text-decoration: none; /* Bỏ gạch chân link */
    font-size: 16px;
    transition: color 0.3s ease;
}

.footer-nav a:hover {
    color: #bbb;
}

.contact-button {
    background-color: transparent; /* Nền trong suốt */
    color: #fff;
    border: 1px solid #fff; /* Viền trắng */
    padding: 10px 20px;
    border-radius: 25px; /* Bo góc nhẹ */
    text-decoration: none;
    font-size: 16px;
    transition: background-color 0.3s ease, color 0.3s ease;
    margin-bottom: 20px; /* Khoảng cách dưới button */
    position:absolute;
    right: 160px;
    bottom:50px;
}

.contact-button:hover {
    background-color: #fff; /* Nền trắng khi hover */
    color: #000; /* Chữ đen khi hover */
}

.footer-divider {
    border: none;
    border-top: 1px solid #eee2e2; /* Đường kẻ mỏng, màu xám đậm */
    width: 100%; /* Chiều rộng của đường kẻ */
    margin: 10px 0; /* Khoảng cách trên dưới của đường kẻ */
}

.copyright {
    font-size: 14px;
    color: #888; /* Màu chữ bản quyền hơi nhạt */
   
}

/* Responsive adjustments */
@media (max-width: 768px) {
    .footer-nav ul {
        flex-direction: column; /* Xếp các mục menu theo cột trên màn hình nhỏ */
        gap: 10px;
    }

    .footer-content {
        gap: 15px; /* Giảm khoảng cách tổng thể */
    }

    .social-icons {
        margin-bottom: 15px;
    }

    .contact-button {
        margin-bottom: 15px;
    }
}
</style>