package com.nahuannghia.shopnhn.controller;

import com.nahuannghia.shopnhn.model.ProductCategory;
import com.nahuannghia.shopnhn.service.ProductCategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins =  {"http://localhost:63342", "http://127.0.0.1:5501", "http://127.0.0.1:5500"})
@RequestMapping("/api/product-categories")
public class ProductCategoryController {

    private final ProductCategoryService productCategoryService;

    public ProductCategoryController(ProductCategoryService productCategoryService) {
        this.productCategoryService = productCategoryService;
    }

    // Tạo mới danh mục
    @PostMapping
    public ResponseEntity<ProductCategory> createCategory(@RequestBody ProductCategory productCategory) {
        ProductCategory createdCategory = productCategoryService.createProductCategory(productCategory);
        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }

    // Lấy tất cả danh mục
    @GetMapping
    public ResponseEntity<List<ProductCategory>> getAllCategories() {
        List<ProductCategory> categories = productCategoryService.getAllProductCategories();
        return ResponseEntity.ok(categories);
    }

    // Lấy danh mục theo ID
    @GetMapping("/{id}")
    public ResponseEntity<ProductCategory> getCategoryById(@PathVariable Integer id) {
        return productCategoryService.getProductCategoryById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Cập nhật danh mục
    @PutMapping("/{id}")
    public ResponseEntity<ProductCategory> updateCategory(
            @PathVariable Integer id, 
            @RequestBody ProductCategory categoryDetails) {
        try {
            ProductCategory updatedCategory = productCategoryService.updateProductCategory(id, categoryDetails);
            return ResponseEntity.ok(updatedCategory);
        } catch (RuntimeException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    // Xóa danh mục
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Integer id) {
        if (productCategoryService.deleteProductCategory(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Tìm kiếm theo tên
    @GetMapping("/search")
    public ResponseEntity<List<ProductCategory>> searchCategories(
            @RequestParam("name") String categoryName) {
        List<ProductCategory> result = productCategoryService.searchProductCategoriesByName(categoryName);
        return ResponseEntity.ok(result);
    }

    // Xử lý exception
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
