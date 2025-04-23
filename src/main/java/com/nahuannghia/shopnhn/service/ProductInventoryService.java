package com.nahuannghia.shopnhn.service;

import com.nahuannghia.shopnhn.request.ProductInventoryRequest;
import com.nahuannghia.shopnhn.Response.ProductInventoryResponse;
import com.nahuannghia.shopnhn.model.Product;
import com.nahuannghia.shopnhn.model.ProductInventory;
import com.nahuannghia.shopnhn.repository.ProductInventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductInventoryService {

    @Autowired
    private ProductInventoryRepository productInventoryRepository;

    // CREATE: Thêm mới thông tin tồn kho
    public ProductInventoryResponse addProductInventory(ProductInventoryRequest request) {
        if (request.getColor() == null || request.getSize() == null || request.getQuantity() == null) {
            throw new IllegalArgumentException("Color, size, and quantity must not be null");
        }
        Optional<ProductInventory> productInventory = productInventoryRepository.findByProductInventoryId_ProductId(request.getProductId());
     
        
        if(productInventory.isEmpty()){
            throw new RuntimeException("");
        }
        Product product = productInventory.get().getProduct();
        ProductInventory productInventory1 = new ProductInventory(
                product,
                request.getColor(),
                request.getSize(),
                request.getQuantity());
        productInventoryRepository.save(productInventory1);
        return new ProductInventoryResponse(productInventory1.getColor(),productInventory1.getSize(), productInventory1.getQuantity());
    }

    // READ: Lấy tất cả thông tin tồn kho
    public List<ProductInventoryResponse> getAllProductInventory() {
        List<ProductInventory> productInventoryList = productInventoryRepository.findAll();

        return productInventoryList.stream().map(inventory -> new ProductInventoryResponse(
                        inventory.getColor(),
                        inventory.getSize(),
                        inventory.getQuantity()))
                .collect(Collectors.toList());
    }

    // READ
    public List<ProductInventoryResponse> getProductInventoryById(Integer productId) {
        List<ProductInventory> productInventory = productInventoryRepository.findByProductProductId(productId);

        return productInventory.stream()
                .map(inventory -> new ProductInventoryResponse(inventory.getColor(), inventory.getSize(), inventory.getQuantity())).collect(Collectors.toList());
    }

    // UPDATE
    @Transactional
    public ProductInventoryResponse updateProductInventory(Integer productId, String color, String size, ProductInventoryRequest request) {
        ProductInventory productInventory = productInventoryRepository
                .findByProductInventoryId_ProductIdAndProductInventoryId_ColorAndProductInventoryId_Size(productId, color, size)
                .orElseThrow(() -> new RuntimeException("Product inventory not found"));

        productInventory.setQuantity(request.getQuantity());
        productInventoryRepository.save(productInventory);

        return new ProductInventoryResponse(productInventory.getColor(), productInventory.getSize(), productInventory.getQuantity());
    }

    // DELETE
    @Transactional
    public void deleteProductInventory(Integer productId, String color, String size) {
        ProductInventory productInventory = productInventoryRepository
                .findByProductInventoryId_ProductIdAndProductInventoryId_ColorAndProductInventoryId_Size(productId, color, size)
                .orElseThrow(() -> new RuntimeException("Product inventory not found"));

        productInventoryRepository.delete(productInventory);
    }
}
