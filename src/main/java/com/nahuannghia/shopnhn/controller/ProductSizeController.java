package com.nahuannghia.shopnhn.controller;

import com.nahuannghia.shopnhn.model.ProductSize;
import com.nahuannghia.shopnhn.model.ProductSizeKey;
import com.nahuannghia.shopnhn.service.ProductSizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product-size")
public class ProductSizeController {
    @Autowired
    ProductSizeService productSizeService;
    @PostMapping
    public ProductSize addProductSize(@RequestBody ProductSize productSize){
        return productSizeService.addProductSize(productSize);
    }
    @GetMapping
    public List<ProductSize> getAllProductSize(){
        return productSizeService.getAllProductSize();
    }
    @GetMapping("/{productId}/{size}")
    public ProductSize getProductSize(@PathVariable String productId, @PathVariable String size){
        return productSizeService.getProductSize(new ProductSizeKey(productId, size));
    }
    @PutMapping("/{productId}/{size}")
    public ProductSize updateProductSize(@PathVariable String productId, @PathVariable String size, @RequestBody ProductSize productSize){
        ProductSizeKey productSizeKey = new ProductSizeKey(productId, size);
        return productSizeService.updateProductSize(productSize, productSizeKey);
    }
    @DeleteMapping("/{productId}/{size}")
    public void deleteProductSize(@PathVariable String productId, @PathVariable String size){
        productSizeService.deleteProductSize(new ProductSizeKey(productId, size));
    }
}
