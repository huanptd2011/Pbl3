package com.nahuannghia.shopnhn.controller;

import com.nahuannghia.shopnhn.Response.ProductImageResponse;
import com.nahuannghia.shopnhn.request.ProductImageRequest;
import com.nahuannghia.shopnhn.service.ProductImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/product-images")
@CrossOrigin(origins =  {"http://localhost:63342", "http://127.0.0.1:5501", "http://127.0.0.1:5500"})
public class ProductImageController {

    @Autowired
    private ProductImageService productImageService;

    // Thêm ảnh cho sản phẩm
    @PostMapping("/add/{productId}")
    public ProductImageResponse addProductImage(@PathVariable Integer productId,
                                                @RequestBody ProductImageRequest request) {
        return productImageService.addProductImage(productId, request);
    }

    // Lấy tất cả ảnh (hiếm khi cần)
    @GetMapping("/all")
    public List<ProductImageResponse> getAllImages() {
        return productImageService.getAllProductImages();
    }

    // Lấy ảnh theo ID sản phẩm
    @GetMapping("/{productId}")
    public List<ProductImageResponse> getImagesByProductId(@PathVariable Integer productId) {
        return productImageService.getImagesByProductId(productId);
    }

    // Xóa ảnh theo URL
    @DeleteMapping("/delete")
    public void deleteImage(@RequestParam Integer productId, @RequestParam String imageUrl) {
        productImageService.deleteProductImage(productId, imageUrl);
    }
}
