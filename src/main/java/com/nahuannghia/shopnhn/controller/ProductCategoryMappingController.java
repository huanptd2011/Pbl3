package com.nahuannghia.shopnhn.controller;

import com.nahuannghia.shopnhn.model.ProductCategoryMapping;
import com.nahuannghia.shopnhn.model.ProductCategoryMappingKey;
import com.nahuannghia.shopnhn.service.ProductCategoryMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("category-mapping")
public class ProductCategoryMappingController {
    @Autowired
    private ProductCategoryMappingService productCategoryMappingService;
    @PostMapping
    public ProductCategoryMapping addProCategoryMapping(@RequestBody ProductCategoryMapping productCategoryMapping){
        return productCategoryMappingService.addProCategoryMapping(productCategoryMapping);
    }
    @GetMapping
    public List<ProductCategoryMapping> getAllProCategory(){
        return productCategoryMappingService.getAllProCategoryMapping();
    }
    @GetMapping("/{productId}/{categoryId}")
    public ProductCategoryMapping getProCategoryMapping(@PathVariable String productId, @PathVariable int categoryId){
        return productCategoryMappingService
                .getProCategoryMapping(new ProductCategoryMappingKey(productId, categoryId));
    }
    @PutMapping("/{productId}/{categoryId}")
    public ProductCategoryMapping updateProCategoryMapping(@PathVariable String productId,
                                                           @PathVariable int categoryId,
                                                           @RequestBody ProductCategoryMapping productCategoryMapping){
        return productCategoryMappingService.updateProductCategoryMapping(
                new ProductCategoryMappingKey(productId, categoryId),
                productCategoryMapping);
    }
    @DeleteMapping("/{productId}/{categoryId}")
    public void deleteProCategoryMapping(@PathVariable String productId, @PathVariable int categoryId){
        productCategoryMappingService.deleteProCategoryMapping(new ProductCategoryMappingKey(productId, categoryId));
    }
}
