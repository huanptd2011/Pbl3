document.addEventListener('DOMContentLoaded', function() {
    // DOM Elements
    const uploadArea = document.getElementById('uploadArea');
    const imageUpload = document.getElementById('imageUpload');
    const imagePreview = document.getElementById('imagePreview');
    const addVariantBtn = document.getElementById('addVariantBtn');
    const inventoryBody = document.getElementById('inventoryBody');
    const variantTemplate = document.getElementById('variantTemplate');
    const productForm = document.getElementById('productForm');
    
    // Xử lý upload hình ảnh
    uploadArea.addEventListener('click', () => imageUpload.click());
    
    uploadArea.addEventListener('dragover', (e) => {
        e.preventDefault();
        uploadArea.style.borderColor = '#3498db';
        uploadArea.style.backgroundColor = '#f8fafc';
    });
    
    uploadArea.addEventListener('dragleave', () => {
        uploadArea.style.borderColor = '#ccc';
        uploadArea.style.backgroundColor = 'transparent';
    });
    
    uploadArea.addEventListener('drop', (e) => {
        e.preventDefault();
        uploadArea.style.borderColor = '#ccc';
        uploadArea.style.backgroundColor = 'transparent';
        
        if (e.dataTransfer.files.length) {
            imageUpload.files = e.dataTransfer.files;
            handleImageUpload();
        }
    });
    
    imageUpload.addEventListener('change', handleImageUpload);
    
    function handleImageUpload() {
        const files = imageUpload.files;
        if (!files || files.length === 0) return;
        
        for (let i = 0; i < files.length; i++) {
            const file = files[i];
            if (!file.type.match('image.*')) continue;
            
            const reader = new FileReader();
            reader.onload = function(e) {
                const imgContainer = document.createElement('div');
                imgContainer.className = 'image-preview-item';
                
                const img = document.createElement('img');
                img.src = e.target.result;
                
                const removeBtn = document.createElement('button');
                removeBtn.className = 'remove-img';
                removeBtn.innerHTML = '<i class="fas fa-times"></i>';
                removeBtn.addEventListener('click', () => imgContainer.remove());
                
                imgContainer.appendChild(img);
                imgContainer.appendChild(removeBtn);
                imagePreview.appendChild(imgContainer);
            }
            reader.readAsDataURL(file);
        }
    }
    
    // Thêm biến thể sản phẩm
    addVariantBtn.addEventListener('click', addVariantRow);
    
    function addVariantRow() {
        const row = variantTemplate.content.cloneNode(true);
        const removeBtn = row.querySelector('.btn-remove');
        removeBtn.addEventListener('click', () => removeBtn.closest('tr').remove());
        inventoryBody.appendChild(row);
    }
    
    // Xử lý submit form
    productForm.addEventListener('submit', function(e) {
        e.preventDefault();
        
        // Kiểm tra dữ liệu
        const productName = document.getElementById('productName').value;
        const price = document.getElementById('price').value;
        
        if (!productName || !price) {
            alert('Vui lòng nhập tên sản phẩm và giá bán');
            return;
        }
        
        // Kiểm tra có ít nhất 1 hình ảnh
//        if (imagePreview.children.length === 0) {
//            alert('Vui lòng thêm ít nhất 1 hình ảnh sản phẩm');
//            return;
//        }
        
        // Kiểm tra có ít nhất 1 biến thể
        if (inventoryBody.children.length === 0) {
            alert('Vui lòng thêm ít nhất 1 biến thể sản phẩm');
            return;
        }
        
        // Hiển thị thông báo thành công
        // alert('Sản phẩm đã được lưu thành công!');
        
        // Trong thực tế, bạn sẽ gửi dữ liệu đến server ở đây
        // console.log('Dữ liệu sản phẩm:', collectProductData());
        const data = collectProductData();

<<<<<<< HEAD
        fetch('http://localhost:8080/products/add', {
=======
        fetch('http://localhost:8080/ShopNHN/products/add', {
>>>>>>> a72de685ed457ecb97c852db83f5d59ebfecb8e8
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                productName: data.name,
                productDescription: data.description,
                brand: data.brand,
                price: parseFloat(data.price),
                sizeColorList: data.variants,
                imageList: data.images.map(url => ({
                    imageUrl: url
                }))
            })
        })
        .then(response => {
            if (!response.ok) throw new Error('Lỗi khi thêm sản phẩm');
            return response.json();
        })
        .then(result => {
            alert('Sản phẩm đã được thêm thành công!');
            console.log(result);
        })
        .catch(error => {
            console.error('Lỗi:', error);
            alert('Có lỗi xảy ra khi gửi sản phẩm lên server.');
        });

    });
    
    // Thêm 1 dòng biến thể mặc định khi tải trang
    addVariantRow();
    
    // Hàm thu thập dữ liệu sản phẩm
    function collectProductData() {
        const productData = {
            name: document.getElementById('productName').value,
            description: document.getElementById('productDescription').value,
            category: document.getElementById('category').value,
            brand: document.getElementById('brand').value,
            price: document.getElementById('price').value,
            variants: [],
            images: []
        };
        
        // Thu thập biến thể
        const variantRows = inventoryBody.querySelectorAll('tr');
        variantRows.forEach(row => {
            productData.variants.push({
                color: row.querySelector('.color-input').value,
                size: row.querySelector('.size-input').value,
                quantity: row.querySelector('.quantity-input').value
            });
        });
        
        // Thu thập hình ảnh (trong thực tế sẽ là URL từ server)
        const imageItems = imagePreview.querySelectorAll('.image-preview-item img');
        imageItems.forEach(img => {
            productData.images.push(img.src);
        });
        
        return productData;
    }
});