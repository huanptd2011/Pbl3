package com.nahuannghia.shopnhn.service;

import com.nahuannghia.shopnhn.model.ProductCategory;
import com.nahuannghia.shopnhn.repository.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductCategoryService {
    @Autowired
    private ProductCategoryRepository productCategoryRepository;
    public ProductCategory addProductCategory(ProductCategory productCategory){
        try {
            return productCategoryRepository.save(productCategory);
        } catch (Exception e){
            throw new RuntimeException("Error adding");
        }
    }
    public List<ProductCategory> getAllProductCategory(){
        List<ProductCategory> list = productCategoryRepository.findAll();
        if(list.isEmpty())
            throw new RuntimeException("No product category");
        return list;
    }
    public ProductCategory getProductCategory(Long categoryId){
        return productCategoryRepository.findById(categoryId).orElseThrow(()->
                new RuntimeException("Not found"));
    }
    public ProductCategory updateProductCategory(Long categoryId, ProductCategory productCategory){
        ProductCategory existing = getProductCategory(categoryId);
        existing.setCategoryName(productCategory.getCategoryName());
        try {
             return productCategoryRepository.save(existing);
        } catch (Exception e){
            throw new RuntimeException("Error updating");
        }
    }
    public void deleteProductCategory(Long categoryId){
        ProductCategory existing = getProductCategory(categoryId);
        try{
            productCategoryRepository.delete(existing);
        } catch (Exception e){
            throw new RuntimeException("Error deleting");
        }
    }
}
