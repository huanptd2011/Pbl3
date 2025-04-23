package com.nahuannghia.shopnhn.service;

import com.nahuannghia.shopnhn.Response.ProductImageResponse;

import com.nahuannghia.shopnhn.model.Product;
import com.nahuannghia.shopnhn.model.ProductImage;
import com.nahuannghia.shopnhn.repository.ProductImageRepository;
import com.nahuannghia.shopnhn.repository.ProductRepository;
import com.nahuannghia.shopnhn.request.ProductImageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductImageService {

    @Autowired
    private ProductImageRepository productImageRepository;

    @Autowired
    private ProductRepository productRepository;

    public ProductImageResponse addProductImage(Integer productId, ProductImageRequest productImageRequest){
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        ProductImage productImage = new ProductImage(product, productImageRequest.getImageUrl());
        productImageRepository.save(productImage);
        return new ProductImageResponse(productImage.getImageUrl());
    }

    public List<ProductImageResponse> getAllProductImages() {
        return productImageRepository.findAll()
                .stream()
                .map(image -> new ProductImageResponse(image.getImageUrl()))
                .collect(Collectors.toList());
    }

    public List<ProductImageResponse> getImagesByProductId(Integer productId) {
        return productImageRepository.findByProductProductId(productId)
                .stream()
                .map(image -> new ProductImageResponse(image.getImageUrl()))
                .collect(Collectors.toList());
    }

    public void deleteProductImage(Integer productId, String imageUrl) {
        ProductImage productImage = productImageRepository
                .findByProductProductIdAndImageUrl(productId, imageUrl)
                .orElseThrow(() -> new RuntimeException("Product image not found"));
        productImageRepository.delete(productImage);
    }

    public void deleteByProductId(Integer productId) {
        List<ProductImage> images = productImageRepository.findByProductProductId(productId);
        productImageRepository.deleteAll(images);
    }

    public void saveAllImages(Product product, List<ProductImageRequest> imageRequests) {
        List<ProductImage> productImages = imageRequests.stream()
                .map(img -> new ProductImage(product, img.getImageUrl()))
                .collect(Collectors.toList());
        productImageRepository.saveAll(productImages);
    }
}
