package com.nahuannghia.shopnhn.controller;

import com.nahuannghia.shopnhn.model.ProductCategory;
import com.nahuannghia.shopnhn.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class ProductCategoryController {
    @Autowired
    private ProductCategoryService productCategoryService;
    @PostMapping
    public ProductCategory addProductCategory(@RequestBody ProductCategory productCategory){
        return productCategoryService.addProductCategory(productCategory);
    }
    @GetMapping
    public List<ProductCategory> getAllProductCategory(){
        return productCategoryService.getAllProductCategory();
    }
    @GetMapping("/{categoryId}")
    public ProductCategory getProductCategory(@PathVariable Long categoryId){
        return productCategoryService.getProductCategory(categoryId);
    }
    @PutMapping("/{categoryId}")
    public ProductCategory updateProductCategory(@PathVariable Long categoryId, @RequestBody ProductCategory productCategory){
        return productCategoryService.updateProductCategory(categoryId, productCategory);
    }
    @DeleteMapping("/{categoryId}")
    public void deleteProductCategory(@PathVariable Long categoryId){
        productCategoryService.deleteProductCategory(categoryId);
    }
}
