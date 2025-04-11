package com.nahuannghia.shopnhn.service;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductInventoryRepository productInventoryRepository;
    private final ProductImageRepository productImageRepository;
    private final ProductImageService productImageService;
    private final ProductInventoryService productInventoryService;

    @Autowired
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

        product = productRepository.save(product);
        Product product1 = product;

        if (productRequest.getSizeColorList() != null) {
            productRequest.getSizeColorList().forEach(inventory -> {
                ProductInventory productInventory = new ProductInventory(
                        product1,
                        inventory.getColor(),
                        inventory.getSize(),
                        inventory.getQuantity());
                productInventoryRepository.save(productInventory);
            });
        }

        if (productRequest.getImageList() != null) {
            productRequest.getImageList().forEach(image -> {
                ProductImage productImage = new ProductImage(
                        product1,
                        image.getImageUrl()
                        );
                productImageRepository.save(productImage);
            });
        }

        return new ProductResponse(
                product.getProductId(),
                product.getProductName(),
                product.getProductDescription(),
                product.getBrand(),
                product.getPrice(),
                product.getCreatedAt(),
                product.getUpdatedAt(),
                productRequest.getSizeColorList(),
                productRequest.getImageList()
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

        product = productRepository.save(product);
        Product product1 = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        productInventoryRepository.deleteByProductProductId(productId);
        if (productRequest.getSizeColorList() != null) {
            productRequest.getSizeColorList().forEach(inventory -> {
                ProductInventory productInventory = new ProductInventory(
                        product1,
                        inventory.getColor(),
                        inventory.getSize(),
                        inventory.getQuantity());
                productInventoryRepository.save(productInventory);
            });
        }

        productImageRepository.deleteByProductProductId(productId);
        if (productRequest.getImageList() != null) {
            productRequest.getImageList().forEach(image -> {
                ProductImage productImage = new ProductImage(
                        product1,
                        image.getImageUrl()
                );
                productImageRepository.save(productImage);
            });
        }

        return new ProductResponse(
                product.getProductId(),
                product.getProductName(),
                product.getProductDescription(),
                product.getBrand(),
                product.getPrice(),
                product.getCreatedAt(),
                product.getUpdatedAt(),
                productRequest.getSizeColorList(),
                productRequest.getImageList()
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
    public List<ProductResponse> searchProductsWithPagination(String keyword, int page, int size) {
        List<ProductResponse> products = searchProducts(keyword);
        return paginateList(products, page, size);
    }
    public List<ProductResponse> paginateList(List<ProductResponse> list, int page, int size) {
        int fromIndex = page * size;
        int toIndex = Math.min(fromIndex + size, list.size());

        if (fromIndex >= list.size()) {
            return new ArrayList<>();
        }

        return list.subList(fromIndex, toIndex);
    }
}
