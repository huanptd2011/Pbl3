document.addEventListener('DOMContentLoaded', function() {
    const pageTitle = document.querySelector('.header-left h2');
    const dashboardCards = document.querySelector('.cards');
    const recentOrders = document.querySelector('.content-section');
    const productFormContainer = document.querySelector('.product-form-container');
    const listFormContainer = document.querySelector('.product-list-container');
    

    const dashboardLink = document.querySelector('.sidebar-menu a[href="#"]:has(i.fa-tachometer-alt)');
    const addProductLink = document.getElementById('add-product-link');
    const listProductLink = document.getElementById('list-product-link');
    
    function hideAllSections() {
        dashboardCards.style.display = 'none';
        recentOrders.style.display = 'none';
        productFormContainer.style.display = 'none';
        listFormContainer.style.display = 'none';
    }

    
    // Hàm cập nhật menu active
    function updateActiveMenu(activeItem) {
        document.querySelectorAll('.sidebar-menu a').forEach(link => {
            link.classList.remove('active');
        });
        activeItem.classList.add('active');
    }

    // Xử lý click vào menu Dashboard
    if (dashboardLink) {
        dashboardLink.addEventListener('click', function(e) {
            e.preventDefault();
            hideAllSections();
            
            // Đổi tiêu đề
            pageTitle.textContent = 'Dashboard';
            
            // Hiển thị cards và đơn hàng
            dashboardCards.style.display = 'grid'; // hoặc 'flex' tùy CSS của bạn
            recentOrders.style.display = 'block';
            
            // Cập nhật active menu
            updateActiveMenu(this);
        });
    }
    
    // Xử lý click vào menu Thêm sản phẩm
    if (addProductLink) {
        addProductLink.addEventListener('click', function(e) {
            e.preventDefault();
            hideAllSections();
            
            // Đổi tiêu đề
            pageTitle.textContent = 'Thêm sản phẩm mới';

            // Hiển thị form thêm sản phẩm
            productFormContainer.style.display = 'block';
            
            // Cập nhật active menu
            updateActiveMenu(this.closest('.has-dropdown').querySelector('> a'));
        });
    }

    if (listProductLink) {
        listProductLink.addEventListener('click', function(e) {
            e.preventDefault();
            hideAllSections();
            
            // Đổi tiêu đề
            pageTitle.textContent = 'Danh sách sản phẩm';

            // Hiển thị form thêm sản phẩm
            listFormContainer.style.display = 'block';
            
            // Cập nhật active menu
            updateActiveMenu(this.closest('.has-dropdown').querySelector('> a'));
        });
    }
    
    
    
    // Xử lý dropdown menu
    const dropdownToggles = document.querySelectorAll('.has-dropdown > a');
    dropdownToggles.forEach(toggle => {
        toggle.addEventListener('click', function(e) {
            if (window.innerWidth > 768) { // Chỉ áp dụng cho desktop
                e.preventDefault();
                const submenu = this.nextElementSibling;
                submenu.style.display = submenu.style.display === 'block' ? 'none' : 'block';
            }
        });
    });
});
