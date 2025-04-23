package com.nahuannghia.shopnhn.service;

import com.nahuannghia.shopnhn.model.ProductCategory;
import com.nahuannghia.shopnhn.repository.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductCategoryService {
    private final ProductCategoryRepository productCategoryRepository;

    @Autowired
    public ProductCategoryService(ProductCategoryRepository productCategoryRepository) {
        this.productCategoryRepository = productCategoryRepository;
    }

    // Tạo mới danh mục
    public ProductCategory createProductCategory(ProductCategory productCategory) {
        return productCategoryRepository.save(productCategory);
    }

    // Lấy tất cả danh mục
    public List<ProductCategory> getAllProductCategories() {
        return productCategoryRepository.findAll();
    }

    // Lấy danh mục theo ID
    public Optional<ProductCategory> getProductCategoryById(Integer categoryId) {
        return productCategoryRepository.findById(categoryId);
    }

    // Cập nhật danh mục
    public ProductCategory updateProductCategory(Integer categoryId, ProductCategory categoryDetails) {
        ProductCategory existingCategory = productCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy danh mục với ID: " + categoryId));
        
        existingCategory.setCategoryName(categoryDetails.getCategoryName());
        return productCategoryRepository.save(existingCategory);
    }

    // Xóa danh mục
    public boolean deleteProductCategory(Integer categoryId) {
        if (productCategoryRepository.existsById(categoryId)) {
            productCategoryRepository.deleteById(categoryId);
            return true;
        }
        return false;
    }

    // Tìm kiếm danh mục theo tên (custom query - cần định nghĩa trong repository)
    public List<ProductCategory> searchProductCategoriesByName(String categoryName) {
        return productCategoryRepository.findByCategoryNameContainingIgnoreCase(categoryName);
    }
}