package com.nahuannghia.shopnhn.controller;

import com.nahuannghia.shopnhn.model.Product;
import com.nahuannghia.shopnhn.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    ProductService productService;
    @PostMapping
    public Product addProduct(@RequestBody @Valid Product product){
        return productService.addProduct(product);
    }
    @GetMapping("/{productId}")
    public Product getProduct(@PathVariable String productId){
        return productService.getProduct(productId);
    }
    @GetMapping
    public List<Product> getAllProducts(){
        return productService.getAllProducts();
    }
    @PutMapping("/{productId}")
    public Product updateProduct(@PathVariable String productId ,@RequestBody Product product){
        return productService.updateProduct(productId, product);
    }
    @DeleteMapping("/{productId}")
    public void deleteProduct(@PathVariable String productId){
        productService.deleteProduct(productId);
    }
}
