package com.nahuannghia.shopnhn.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import com.nahuannghia.shopnhn.model.ProductCategory;
import com.nahuannghia.shopnhn.model.ProductImage;
import com.nahuannghia.shopnhn.model.ProductInventory;
import com.nahuannghia.shopnhn.model.ProductInventoryId;
import com.nahuannghia.shopnhn.repository.ProductCategoryRepository;
import com.nahuannghia.shopnhn.repository.ProductImageRepository;
import com.nahuannghia.shopnhn.repository.ProductInventoryRepository;
import com.nahuannghia.shopnhn.repository.ProductRepository;
import com.nahuannghia.shopnhn.request.ProductImageRequest;
import com.nahuannghia.shopnhn.request.ProductInventoryRequest;
import com.nahuannghia.shopnhn.request.ProductRequest;

import jakarta.transaction.Transactional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductInventoryRepository productInventoryRepository;
    private final ProductImageRepository productImageRepository;
    private final ProductImageService productImageService;
    private final ProductInventoryService productInventoryService;
    private final ProductCategoryRepository productCategoryRepository;

    //
    // Create new product
    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product = new Product();
        product.setProductName(productRequest.getProductName());
        product.setProductDescription(productRequest.getProductDescription());
        product.setBrand(productRequest.getBrand());
        product.setPrice(productRequest.getPrice());
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());
        product.setProductCategory(new ProductCategory(productRequest.getCategoryId(), productRequest.getProductName()));
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
                        image.getImageUrl());
                productImageRepository.save(productImage);
            });
        }
        // Set category
        if (productRequest.getCategory() != null && productRequest.getCategory().getCategoryId() != null) {
            Integer categoryId = productRequest.getCategory().getCategoryId();
            ProductCategory category = productCategoryRepository.findById(categoryId)
                    .orElseThrow(() -> new RuntimeException("Category not found with id: " + categoryId));
            product.setProductCategory(category);
        }

        List<ProductInventoryResponse> inventoryList = productInventoryService
                .getProductInventoryById(savedProduct.getProductId());
        List<ProductImageResponse> imageList = productImageService.getImagesByProductId(savedProduct.getProductId());
        Long totalInventory = inventoryList.stream()
                .filter(ProductInventoryResponse::getIsActive) // Lọc chỉ những phần tử isActive = true
                .mapToLong(ProductInventory -> ProductInventory.getQuantity() != null ? ProductInventory.getQuantity() : 0) // Tránh NullPointerException nếu quantity null
                .sum();

        ProductCategory category = productCategoryRepository.findCategoryByProductId(product.getProductId());
        return new ProductResponse(
                savedProduct.getProductId(),
                savedProduct.getProductName(),
                savedProduct.getProductDescription(),
                savedProduct.getBrand(),
                savedProduct.getPrice(),
                totalInventory,
                savedProduct.getStatus(),
                savedProduct.getCreatedAt(),
                savedProduct.getUpdatedAt(),
                inventoryList,
                imageList, category);
    }

    public ProductService(ProductRepository productRepository, ProductInventoryRepository productInventoryRepository,
            ProductImageRepository productImageRepository, ProductImageService productImageService,
            ProductInventoryService productInventoryService, ProductCategoryRepository productCategoryRepository) {
        this.productRepository = productRepository;
        this.productInventoryRepository = productInventoryRepository;
        this.productImageRepository = productImageRepository;
        this.productImageService = productImageService;
        this.productInventoryService = productInventoryService;
        this.productCategoryRepository = productCategoryRepository;
    }

    public ProductResponse getProductById(Integer productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        List<ProductInventoryResponse> inventoryList = productInventoryService
                .getProductInventoryById(product.getProductId());
        List<ProductImageResponse> imageList = productImageService.getImagesByProductId(product.getProductId());
        Long totalInventory = inventoryList.stream()
                .filter(ProductInventoryResponse::getIsActive) // Lọc chỉ những phần tử isActive = true
                .mapToLong(ProductInventory -> ProductInventory.getQuantity() != null ? ProductInventory.getQuantity() : 0) // Tránh NullPointerException nếu quantity null
                .sum();

        if (!inventoryList.isEmpty()) {
            System.out.println("Djiasudhfusdnd");
        }
        ProductCategory category = productCategoryRepository.findCategoryByProductId(product.getProductId());
        return new ProductResponse(
                product.getProductId(),
                product.getProductName(),
                product.getProductDescription(),
                product.getBrand(),
                product.getPrice(),
                totalInventory,
                product.getStatus(),
                product.getCreatedAt(),
                product.getUpdatedAt(),
                inventoryList,
                imageList,
                category);
    }

    // Get all products
    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();

        return products.stream().map(product -> {
            List<ProductInventoryResponse> inventoryList = productInventoryService
                    .getProductInventoryById(product.getProductId());
            List<ProductImageResponse> imageList = productImageService.getImagesByProductId(product.getProductId());
            Long totalInventory = inventoryList.stream()
                    .mapToLong(ProductInventoryResponse::getQuantity)
                    .sum();
            ProductCategory category = productCategoryRepository.findCategoryByProductId(product.getProductId());

            return new ProductResponse(
                    product.getProductId(),
                    product.getProductName(),
                    product.getProductDescription(),
                    product.getBrand(),
                    product.getPrice(),
                    totalInventory,
                    product.getStatus(),
                    product.getCreatedAt(),
                    product.getUpdatedAt(),
                    inventoryList,
                    imageList,
                    category);
        }).collect(Collectors.toList());
    }

    @Transactional
    public ProductResponse updateProduct(Integer productId, ProductRequest productRequest) {
        // 1. Tìm sản phẩm
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // 2. Cập nhật thông tin sản phẩm
        product.setProductName(productRequest.getProductName());
        product.setProductDescription(productRequest.getProductDescription());
        product.setBrand(productRequest.getBrand());
        product.setStatus(productRequest.getIsActive());
        product.setPrice(productRequest.getPrice());
        productRepository.save(product);

        // 3. Xử lý danh sách biến thể (inventory)
        Map<ProductInventoryId, ProductInventoryRequest> requestMap = new HashMap<>();
        if (productRequest.getSizeColorList() != null) {
            for (ProductInventoryRequest inventory : productRequest.getSizeColorList()) {
                if (inventory.getColor() == null || inventory.getSize() == null) {
                    continue;
                }

                ProductInventoryId inventoryId = new ProductInventoryId(
                        product.getProductId(), inventory.getColor(), inventory.getSize());
                requestMap.put(inventoryId, inventory);
            }

            List<ProductInventory> existingInventories = productInventoryRepository.findByProductProductId(productId);
            // Cập nhật hoặc vô hiệu hóa
            for (ProductInventory existingInventory : existingInventories) {
                ProductInventoryId id = existingInventory.getProductInventoryId();
                if (requestMap.containsKey(id)) {
                    ProductInventoryRequest req = requestMap.get(id);
                    existingInventory.setQuantity(req.getQuantity());
                    existingInventory.setIsActive(req.getIsActive() != null ? req.getIsActive() : true);
                    productInventoryRepository.save(existingInventory);
                    requestMap.remove(id);
                } else {
                    existingInventory.setIsActive(false);
                    productInventoryRepository.save(existingInventory);
                }
            }

            // Thêm mới các inventory chưa tồn tại
            for (Map.Entry<ProductInventoryId, ProductInventoryRequest> entry : requestMap.entrySet()) {
                ProductInventoryRequest req = entry.getValue();
                ProductInventory newInventory = new ProductInventory(
                        product, req.getColor(), req.getSize(), req.getQuantity());
                newInventory.setIsActive(req.getIsActive() != null ? req.getIsActive() : true);
                productInventoryRepository.save(newInventory);
            }
        }

        // 4. Cập nhật ảnh sản phẩm
        if (productRequest.getImageList() != null) {
            List<String> newImageUrls = productRequest.getImageList().stream()
                    .map(ProductImageRequest::getImageUrl)
                    .collect(Collectors.toList());

            // Tạo ảnh mới nếu chưa có
            for (String imageUrl : newImageUrls) {
                productImageRepository.findByProductProductIdAndImageUrl(productId, imageUrl)
                        .orElseGet(() -> productImageRepository.save(new ProductImage(product, imageUrl)));
            }

            // Xoá ảnh không còn
            List<ProductImage> existingImages = productImageRepository.findByProductProductId(productId);
            for (ProductImage image : existingImages) {
                if (!newImageUrls.contains(image.getImageUrl())) {
                    productImageRepository.delete(image);
                }
            }
        }

        // 5. Lấy lại dữ liệu để trả về
        List<ProductInventory> inventoryList = productInventoryRepository.findByProductProductId(productId);
        List<ProductImage> imageList = productImageRepository.findByProductProductId(productId);
        ProductCategory category = productCategoryRepository.findCategoryByProductId(productId);
        if (productRequest.getCategoryId() != null) {
        ProductCategory newCategory = productCategoryRepository.findById(productRequest.getCategoryId())
            .orElseThrow(() -> new RuntimeException("Category not found"));

         product.setProductCategory(newCategory);
        productRepository.save(product);
    
        category = newCategory;  // cập nhật lại biến category để trả về
}
        
        int totalInventory = inventoryList.stream()
                .filter(ProductInventory::getIsActive)
                .mapToInt(ProductInventory::getQuantity)
                .sum();

        List<ProductInventoryResponse> inventoryResponseList = inventoryList.stream()
                .map(inv -> new ProductInventoryResponse(
                inv.getProductInventoryId().getSize(),
                inv.getProductInventoryId().getColor(),
                inv.getQuantity(),
                inv.getIsActive()))
                .collect(Collectors.toList());

        List<ProductImageResponse> imageResponseList = imageList.stream()
                .map(img -> new ProductImageResponse(img.getImageUrl()))
                .collect(Collectors.toList());

        return new ProductResponse(
                product.getProductId(),
                product.getProductName(),
                product.getProductDescription(),
                product.getBrand(),
                product.getPrice(),
                totalInventory,
                product.getStatus(),
                product.getCreatedAt(),
                product.getUpdatedAt(),
                inventoryResponseList,
                imageResponseList,
                category
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
            List<ProductInventoryResponse> inventoryList = productInventoryService
                    .getProductInventoryById(product.getProductId());
            List<ProductImageResponse> imageList = productImageService.getImagesByProductId(product.getProductId());
            Long totalInventory = inventoryList.stream()
                    .mapToLong(ProductInventoryResponse::getQuantity)
                    .sum();
            ProductCategory category = productCategoryRepository.findCategoryByProductId(product.getProductId());
            return new ProductResponse(
                    product.getProductId(),
                    product.getProductName(),
                    product.getProductDescription(),
                    product.getBrand(),
                    product.getPrice(),
                    totalInventory,
                    product.getStatus(),
                    product.getCreatedAt(),
                    product.getUpdatedAt(),
                    inventoryList,
                    imageList,
                    category);
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

    public List<ProductResponse> getNewProduct() {
        List<Product> products = productRepository.findTop4ByOrderByCreatedDateDesc();
        return products.stream().map(product -> {
            List<ProductInventoryResponse> inventoryList = productInventoryService
                    .getProductInventoryById(product.getProductId());
            List<ProductImageResponse> imageList = productImageService.getImagesByProductId(product.getProductId());
            Long totalInventory = inventoryList.stream()
                    .mapToLong(ProductInventoryResponse::getQuantity)
                    .sum();
            ProductCategory category = productCategoryRepository.findCategoryByProductId(product.getProductId());
            return new ProductResponse(
                    product.getProductId(),
                    product.getProductName(),
                    product.getProductDescription(),
                    product.getBrand(),
                    product.getPrice(),
                    totalInventory,
                    product.getStatus(),
                    product.getCreatedAt(),
                    product.getUpdatedAt(),
                    inventoryList,
                    imageList, category);
        }).collect(Collectors.toList());
    }

    public List<ProductResponse> getTop4BestSellingProducts() {
        try {
            List<Product> products = productRepository.findTop4BestSellingProducts();
            return products.stream().map(product -> {
                List<ProductInventoryResponse> inventoryList = productInventoryService.getProductInventoryById(product.getProductId());
                List<ProductImageResponse> imageList = productImageService.getImagesByProductId(product.getProductId());
                Long totalInventory = inventoryList.stream()
                        .mapToLong(ProductInventoryResponse::getQuantity)
                        .sum();
                ProductCategory category = productCategoryRepository.findCategoryByProductId(product.getProductId());

                return new ProductResponse(
                        product.getProductId(),
                        product.getProductName(),
                        product.getProductDescription(),
                        product.getBrand(),
                        product.getPrice(),
                        totalInventory,
                        product.getStatus(),
                        product.getCreatedAt(),
                        product.getUpdatedAt(),
                        inventoryList,
                        imageList,
                        category
                );
            }).collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<ProductResponse> getProductsByCategory(String categoryName) {
        List<ProductResponse> responses = productRepository.searchByProductCategory(categoryName);

        for (ProductResponse response : responses) {
            Integer productId = response.getProductId();

            // Lấy size và màu (inventory)
            List<ProductInventoryResponse> inventoryList = productInventoryService.getProductInventoryById(productId);

            // Lấy danh sách hình ảnh
            List<ProductImageResponse> imageList = productImageService.getImagesByProductId(productId);

            // Gán vào response
            response.setSizeColorList(inventoryList);
            response.setImageList(imageList);
        }

        return responses;
    }
}
