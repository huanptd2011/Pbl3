package com.nahuannghia.shopnhn.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.nahuannghia.shopnhn.Response.ProductImageResponse;
import com.nahuannghia.shopnhn.Response.ProductInventoryResponse;
import com.nahuannghia.shopnhn.Response.ProductResponse;
import com.nahuannghia.shopnhn.model.Product;
import com.nahuannghia.shopnhn.model.ProductImage;
import com.nahuannghia.shopnhn.model.ProductInventory;
import com.nahuannghia.shopnhn.repository.ProductImageRepository;
import com.nahuannghia.shopnhn.repository.ProductInventoryRepository;
import com.nahuannghia.shopnhn.repository.ProductRepository;
import com.nahuannghia.shopnhn.request.ProductRequest;

import jakarta.transaction.Transactional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductInventoryRepository productInventoryRepository;
    private final ProductImageRepository productImageRepository;
    private final ProductImageService productImageService;
    private final ProductInventoryService productInventoryService;

    public ProductService(ProductRepository productRepository,
            ProductInventoryRepository productInventoryRepository,
            ProductImageRepository productImageRepository,
            ProductImageService productImageService,
            ProductInventoryService productInventoryService) {
        this.productRepository = productRepository;
        this.productInventoryRepository = productInventoryRepository;
        this.productImageRepository = productImageRepository;
        this.productImageService = productImageService;
        this.productInventoryService = productInventoryService;
    }

    // Create new product
    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product = new Product();
        product.setProductName(productRequest.getProductName());
        product.setProductDescription(productRequest.getProductDescription());
        product.setBrand(productRequest.getBrand());
        product.setPrice(productRequest.getPrice());
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());
        Product savedProduct = productRepository.save(product);

        if (productRequest.getSizeColorList() != null) {
            productRequest.getSizeColorList().forEach(inventory -> {
                ProductInventory productInventory = new ProductInventory(
                        savedProduct,
                        inventory.getColor(),
                        inventory.getSize(),
                        inventory.getQuantity());
                productInventoryRepository.save(productInventory);
            });
        }

        if (productRequest.getImageList() != null) {
            productRequest.getImageList().forEach(image -> {
                ProductImage productImage = new ProductImage(
                        savedProduct,
                        image.getImageUrl()
                );
                productImageRepository.save(productImage);
            });
        }

        List<ProductInventoryResponse> inventoryList = productInventoryService.getProductInventoryById(savedProduct.getProductId());
        List<ProductImageResponse> imageList = productImageService.getImagesByProductId(savedProduct.getProductId());

        return new ProductResponse(
                savedProduct.getProductId(),
                savedProduct.getProductName(),
                savedProduct.getProductDescription(),
                savedProduct.getBrand(),
                savedProduct.getPrice(),
                savedProduct.getCreatedAt(),
                savedProduct.getUpdatedAt(),
                inventoryList,
                imageList
        );
    }

    public ProductResponse getProductById(Integer productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        List<ProductInventoryResponse> inventoryList = productInventoryRepository.findByProductProductId(productId)
                .stream()
                .map(inventory -> new ProductInventoryResponse(inventory.getColor(), inventory.getSize(), inventory.getQuantity()))
                .collect(Collectors.toList());

        List<ProductImageResponse> imageList = productImageRepository.findByProductProductId(productId)
                .stream()
                .map(image -> new ProductImageResponse(image.getImageUrl()))
                .collect(Collectors.toList());

        return new ProductResponse(
                product.getProductId(),
                product.getProductName(),
                product.getProductDescription(),
                product.getBrand(),
                product.getPrice(),
                product.getCreatedAt(),
                product.getUpdatedAt(),
                inventoryList,
                imageList
        );
    }

    // Get all products
    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();

        return products.stream().map(product -> {
            List<ProductInventoryResponse> inventoryList = productInventoryService.getProductInventoryById(product.getProductId());
            List<ProductImageResponse> imageList = productImageService.getImagesByProductId(product.getProductId());
            return new ProductResponse(
                    product.getProductId(),
                    product.getProductName(),
                    product.getProductDescription(),
                    product.getBrand(),
                    product.getPrice(),
                    product.getCreatedAt(),
                    product.getUpdatedAt(),
                    inventoryList,
                    imageList
            );
        }).collect(Collectors.toList());
    }

    @Transactional
    public ProductResponse updateProduct(Integer productId, ProductRequest productRequest) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setProductName(productRequest.getProductName());
        product.setProductDescription(productRequest.getProductDescription());
        product.setBrand(productRequest.getBrand());
        product.setPrice(productRequest.getPrice());
        productRepository.save(product);
        Product product1 = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Update or create product inventory
        if (productRequest.getSizeColorList() != null) {
            productRequest.getSizeColorList().forEach(inventory -> {
                // Check if inventory exists for this product, size, and color
                ProductInventory existingInventory = productInventoryRepository
                        .findByProductProductIdAndProductInventoryId_SizeAndProductInventoryId_Color(
                                product1.getProductId(), inventory.getSize(), inventory.getColor())
                        .orElse(null);

                if (existingInventory != null) {
                    // Update existing inventory
                    existingInventory.setQuantity(inventory.getQuantity());
                    productInventoryRepository.save(existingInventory);
                } else {
                    // Create new inventory
                    ProductInventory productInventory = new ProductInventory(
                            product1,
                            inventory.getColor(),
                            inventory.getSize(),
                            inventory.getQuantity());
                    productInventoryRepository.save(productInventory);
                }
            });
        }

        // Update or create product images
        if (productRequest.getImageList() != null) {
            productRequest.getImageList().forEach(image -> {
                // Check if image exists for this product and URL
                ProductImage existingImage = productImageRepository
                        .findByProductProductIdAndImageUrl(
                                product1.getProductId(), image.getImageUrl())
                        .orElse(null);

                if (existingImage == null) {
                    // Create new image if it doesn't exist
                    ProductImage productImage = new ProductImage(
                            product1,
                            image.getImageUrl()
                    );
                    productImageRepository.save(productImage);
                }
                // If image exists, no update needed unless additional fields are involved
            });
        }

        List<ProductInventoryResponse> inventoryList = productInventoryService.getProductInventoryById(product1.getProductId());
        List<ProductImageResponse> imageList = productImageService.getImagesByProductId(product1.getProductId());

        return new ProductResponse(
                product1.getProductId(),
                product1.getProductName(),
                product1.getProductDescription(),
                product1.getBrand(),
                product1.getPrice(),
                product1.getCreatedAt(),
                product1.getUpdatedAt(),
                inventoryList,
                imageList
        );
    }

    @Transactional
    public void deleteProduct(Integer productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        productInventoryRepository.deleteByProductProductId(productId);
        productImageRepository.deleteByProductProductId(productId);

        productRepository.delete(product);
    }

    public List<ProductResponse> searchProducts(String keyword) {
        List<Product> products = productRepository.searchByNameOrBrand(keyword);

        return products.stream().map(product -> {
            List<ProductInventoryResponse> inventoryList = productInventoryService.getProductInventoryById(product.getProductId());
            List<ProductImageResponse> imageList = productImageService.getImagesByProductId(product.getProductId());
            return new ProductResponse(
                    product.getProductId(),
                    product.getProductName(),
                    product.getProductDescription(),
                    product.getBrand(),
                    product.getPrice(),
                    product.getCreatedAt(),
                    product.getUpdatedAt(),
                    inventoryList,
                    imageList
            );
        }).collect(Collectors.toList());
    }

    public Page<ProductResponse> searchProductsWithPagination(String keyword, int page, int size) {
        List<ProductResponse> allProducts = searchProducts(keyword);

        int total = allProducts.size();
        int fromIndex = page * size;
        int toIndex = Math.min(fromIndex + size, total);

        List<ProductResponse> paginatedList;

        if (fromIndex >= total) {
            paginatedList = new ArrayList<>();
        } else {
            paginatedList = allProducts.subList(fromIndex, toIndex);
        }

        Pageable pageable = PageRequest.of(page, size);
        return new PageImpl<>(paginatedList, pageable, total);
    }

    public List<ProductResponse> getNewProduct(){
        List<Product> products = productRepository.findTop4ByOrderByCreatedDateDesc();
        return products.stream().map(product -> {
            List<ProductInventoryResponse> inventoryList = productInventoryService.getProductInventoryById(product.getProductId());
            List<ProductImageResponse> imageList = productImageService.getImagesByProductId(product.getProductId());
            return new ProductResponse(
                    product.getProductId(),
                    product.getProductName(),
                    product.getProductDescription(),
                    product.getBrand(),
                    product.getPrice(),
                    product.getCreatedAt(),
                    product.getUpdatedAt(),
                    inventoryList,
                    imageList
            );
        }).collect(Collectors.toList());
    }
}
