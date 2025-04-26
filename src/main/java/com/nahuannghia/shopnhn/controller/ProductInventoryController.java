package com.nahuannghia.shopnhn.controller;

import com.nahuannghia.shopnhn.Response.ProductInventoryResponse;
import com.nahuannghia.shopnhn.request.ProductInventoryRequest;
import com.nahuannghia.shopnhn.service.ProductInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product-inventory")
@CrossOrigin(origins = {"http://localhost:63342", "http://127.0.0.1:5501", "http://127.0.0.1:5500"})
public class ProductInventoryController {

    @Autowired
    private ProductInventoryService productInventoryService;

    // CREATE: Thêm mới tồn kho cho sản phẩm
    @PostMapping("/add")
    public ProductInventoryResponse addInventory(@RequestBody ProductInventoryRequest request) {
        return productInventoryService.addProductInventory(request);
    }

    // READ: Lấy tất cả tồn kho
    @GetMapping("/all")
    public List<ProductInventoryResponse> getAllInventory() {
        return productInventoryService.getAllProductInventory();
    }

    // READ: Lấy tồn kho theo productId
    @GetMapping("/{productId}")
    public List<ProductInventoryResponse> getInventoryByProductId(@PathVariable Integer productId) {
        return productInventoryService.getProductInventoryById(productId);
    }

    // UPDATE: Cập nhật số lượng tồn kho
    @PutMapping("/update/{productId}/{color}/{size}")
    public ProductInventoryResponse updateInventory(@PathVariable Integer productId,
                                                    @PathVariable String color,
                                                    @PathVariable String size,
                                                    @RequestBody ProductInventoryRequest request) {
        return productInventoryService.updateProductInventory(productId, color, size, request);
    }

    // DELETE: Xóa tồn kho theo productId + color + size
    @DeleteMapping("/delete/{productId}/{color}/{size}")
    public void deleteInventory(@PathVariable Integer productId,
                                @PathVariable String color,
                                @PathVariable String size) {
        productInventoryService.deleteProductInventory(productId, color, size);
    }
}
