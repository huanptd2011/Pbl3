document.addEventListener('DOMContentLoaded', function() {
    // DOM Elements
    const pageTitle = document.querySelector('.header-left h2');
    const dashboardContent = document.getElementById('dashboard-content');
    const addProductContent = document.getElementById('add-product-content');
    const listProductContent = document.getElementById('list-product-content');
    const listOrderContent = document.getElementById('list-order-content');
    const confirmOrderContent = document.getElementById('confirm-order-content');
    const updateOrderContent = document.getElementById('update-order-content');
    const deleteOrderContent = document.getElementById('delete-order-content');
    const listCustomerContent = document.getElementById('list-customer-content');
    const searchCustomerContent = document.getElementById('search-customer-content');
    const lockCustomerContent = document.getElementById('lock-customer-content');
    const revenueStatsContent = document.getElementById('revenue-stats-content');
    const orderStatsContent = document.getElementById('order-stats-content');
    const topProductsContent = document.getElementById('top-products-content');
    const loyalCustomersContent = document.getElementById('loyal-customers-content');

    // Select sidebar links
    const dashboardLink = document.getElementById('dashboard-link');
    const addProductLink = document.getElementById('add-product-link');
    const listProductLink = document.getElementById('list-product-link');
    const listOrderLink = document.getElementById('list-order-link');
    const confirmOrderLink = document.getElementById('confirm-order-link');
    const updateOrderLink = document.getElementById('update-order-link');
    const deleteOrderLink = document.getElementById('delete-order-link');
    const listCustomerLink = document.getElementById('list-customer-link');
    const searchCustomerLink = document.getElementById('search-customer-link');
    const lockCustomerLink = document.getElementById('lock-customer-link');
    const revenueStatsLink = document.getElementById('revenue-stats-link');
    const orderStatsLink = document.getElementById('order-stats-link');
    const topProductsLink = document.getElementById('top-products-link');
    const loyalCustomersLink = document.getElementById('loyal-customers-link');

    // Function to hide all sections
    function hideAllSections() {
        dashboardContent.style.display = 'none';
        addProductContent.style.display = 'none';
        listProductContent.style.display = 'none';
        listOrderContent.style.display = 'none';
        confirmOrderContent.style.display = 'none';
        updateOrderContent.style.display = 'none';
        deleteOrderContent.style.display = 'none';
        listCustomerContent.style.display = 'none';
        searchCustomerContent.style.display = 'none';
        lockCustomerContent.style.display = 'none';
        revenueStatsContent.style.display = 'none';
        orderStatsContent.style.display = 'none';
        topProductsContent.style.display = 'none';
        loyalCustomersContent.style.display = 'none';
    }

    // Function to update active menu
    function updateActiveMenu(activeItem) {
        document.querySelectorAll('.sidebar-menu a').forEach(link => {
            link.classList.remove('active');
        });
        if (activeItem) {
            activeItem.classList.add('active');
            // Also set active class on parent menu if it's a submenu item
            const parentMenu = activeItem.closest('.has-dropdown')?.querySelector('a');
            if (parentMenu) {
                parentMenu.classList.add('active');
            }
        }
    }

    // Handle Dashboard click
    if (dashboardLink) {
        dashboardLink.addEventListener('click', function(e) {
            e.preventDefault();
            hideAllSections();
            pageTitle.textContent = 'Dashboard';
            dashboardContent.style.display = 'block';
            updateActiveMenu(this);
        });
    }

    // Handle Add Product click
    if (addProductLink) {
        addProductLink.addEventListener('click', function(e) {
            e.preventDefault();
            hideAllSections();
            pageTitle.textContent = 'Thêm sản phẩm mới';
            addProductContent.style.display = 'block';
            updateActiveMenu(this);
        });
    }

    // Handle List Product click
    if (listProductLink) {
        listProductLink.addEventListener('click', function(e) {
            e.preventDefault();
            hideAllSections();
            pageTitle.textContent = 'Danh sách sản phẩm';
            listProductContent.style.display = 'block';
            updateActiveMenu(this);
        });
    }

    // Handle List Order click
    if (listOrderLink) {
        listOrderLink.addEventListener('click', function(e) {
            e.preventDefault();
            hideAllSections();
            pageTitle.textContent = 'Danh sách đơn hàng';
            listOrderContent.style.display = 'block';
            updateActiveMenu(this);
        });
    }

    // Handle Confirm Order click
    if (confirmOrderLink) {
        confirmOrderLink.addEventListener('click', function(e) {
            e.preventDefault();
            hideAllSections();
            pageTitle.textContent = 'Xác nhận đơn hàng';
            confirmOrderContent.style.display = 'block';
            updateActiveMenu(this);
        });
    }

    // Handle Update Order click
    if (updateOrderLink) {
        updateOrderLink.addEventListener('click', function(e) {
            e.preventDefault();
            hideAllSections();
            pageTitle.textContent = 'Cập nhật đơn hàng';
            updateOrderContent.style.display = 'block';
            updateActiveMenu(this);
        });
    }

    // Handle Delete Order click
    if (deleteOrderLink) {
        deleteOrderLink.addEventListener('click', function(e) {
            e.preventDefault();
            hideAllSections();
            pageTitle.textContent = 'Xóa đơn hàng';
            deleteOrderContent.style.display = 'block';
            updateActiveMenu(this);
        });
    }

    // Handle Customer List click
    if (listCustomerLink) {
        listCustomerLink.addEventListener('click', function(e) {
            e.preventDefault();
            hideAllSections();
            pageTitle.textContent = 'Danh sách khách hàng';
            listCustomerContent.style.display = 'block';
            updateActiveMenu(this);
        });
    }

    // Handle Search Customer click
    if (searchCustomerLink) {
        searchCustomerLink.addEventListener('click', function(e) {
            e.preventDefault();
            hideAllSections();
            pageTitle.textContent = 'Tìm kiếm khách hàng';
            searchCustomerContent.style.display = 'block';
            updateActiveMenu(this);
        });
    }

    // Handle Lock Customer click
    if (lockCustomerLink) {
        lockCustomerLink.addEventListener('click', function(e) {
            e.preventDefault();
            hideAllSections();
            pageTitle.textContent = 'Khóa tài khoản khách hàng';
            lockCustomerContent.style.display = 'block';
            updateActiveMenu(this);
        });
    }

    // Handle Revenue Stats click
    if (revenueStatsLink) {
        revenueStatsLink.addEventListener('click', function(e) {
            e.preventDefault();
            hideAllSections();
            pageTitle.textContent = 'Thống kê doanh thu';
            revenueStatsContent.style.display = 'block';
            updateActiveMenu(this);
        });
    }

    // Handle Order Stats click
    if (orderStatsLink) {
        orderStatsLink.addEventListener('click', function(e) {
            e.preventDefault();
            hideAllSections();
            pageTitle.textContent = 'Thống kê đơn hàng';
            orderStatsContent.style.display = 'block';
            updateActiveMenu(this);
        });
    }

    // Handle Top Products click
    if (topProductsLink) {
        topProductsLink.addEventListener('click', function(e) {
            e.preventDefault();
            hideAllSections();
            pageTitle.textContent = 'Top sản phẩm bán chạy';
            topProductsContent.style.display = 'block';
            updateActiveMenu(this);
        });
    }

    // Handle Loyal Customers click
    if (loyalCustomersLink) {
        loyalCustomersLink.addEventListener('click', function(e) {
            e.preventDefault();
            hideAllSections();
            pageTitle.textContent = 'Khách hàng thân thiết';
            loyalCustomersContent.style.display = 'block';
            updateActiveMenu(this);
        });
    }

    // Handle dropdown menu
    const dropdownToggles = document.querySelectorAll('.has-dropdown > a');
    dropdownToggles.forEach(toggle => {
        toggle.addEventListener('click', function(e) {
            if (window.innerWidth > 768) { // Only apply for desktop
                e.preventDefault();
                const submenu = this.nextElementSibling;
                submenu.style.display = submenu.style.display === 'block' ? 'none' : 'block';
            }
        });
    });
});