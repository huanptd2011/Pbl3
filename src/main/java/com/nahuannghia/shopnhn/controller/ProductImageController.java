package com.nahuannghia.shopnhn.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nahuannghia.shopnhn.Response.ProductImageResponse;
import com.nahuannghia.shopnhn.request.ProductImageRequest;
import com.nahuannghia.shopnhn.service.ProductImageService;

@RestController
@RequestMapping("api/product-images")
@CrossOrigin(origins = {"http://localhost:5173"})
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
