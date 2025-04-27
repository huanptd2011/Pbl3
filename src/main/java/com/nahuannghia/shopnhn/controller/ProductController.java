package com.nahuannghia.shopnhn.controller;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nahuannghia.shopnhn.Response.ProductResponse;
import com.nahuannghia.shopnhn.request.ProductRequest;
import com.nahuannghia.shopnhn.service.ProductService;

@RestController
@CrossOrigin(origins = {"http://localhost:63342", "http://127.0.0.1:5501", "http://127.0.0.1:5500","http://localhost:5173/"})
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/add")
    public ProductResponse createProduct(@RequestBody ProductRequest productRequest) {
        return productService.createProduct(productRequest);
    }

    //ham tim theo thu khoa nam voi brand
//    @GetMapping("/search")
//    public List<ProductResponse> searchProducts(@RequestParam("keyword") String keyword) {
//        return productService.searchProducts(keyword);
//    }

    //phan trang san pham
    @GetMapping("/search")
    public Page<ProductResponse> searchProducts(
            @RequestParam(required = false, defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return productService.searchProductsWithPagination(keyword, page, size);
    }
    @GetMapping("/{productId}")
    public ProductResponse getProductById(@PathVariable Integer productId) {
        return productService.getProductById(productId);
    }

    @GetMapping
    public List<ProductResponse> getAllProducts() {
                return productService.getAllProducts();
    }

    // @GetMapping("/new")
    // public List<ProductResponse> getNewProduct(){
    //     return productService.getNewProduct();
    // }

    @PutMapping("/edit/{productId}")
    public ProductResponse updateProduct(@PathVariable Integer productId, @RequestBody ProductRequest productRequest) {
        return productService.updateProduct(productId, productRequest);
    }

    @DeleteMapping("/delete/{productId}")
    public void deleteProduct(@PathVariable Integer productId) {
        productService.deleteProduct(productId);
    }
}
