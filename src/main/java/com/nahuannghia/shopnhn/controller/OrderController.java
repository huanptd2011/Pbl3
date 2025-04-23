package com.nahuannghia.shopnhn.controller;

import com.nahuannghia.shopnhn.request.OrderRequest;
import com.nahuannghia.shopnhn.Response.OrderResponse;
import com.nahuannghia.shopnhn.exeption.OrderNotFoundException;
import com.nahuannghia.shopnhn.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/search")
    public Page<OrderResponse> searchOrders(
            @RequestParam(required = false, defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return orderService.searchOrdersWithPagination(keyword, page, size);
    }

    @PostMapping
    public OrderResponse createOrder(@RequestBody OrderRequest request) {
        return orderService.createOrder(request);
    }

    @GetMapping
    public List<OrderResponse> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{orderId}")
    public OrderResponse getOrderById(@PathVariable String orderId) {
        return orderService.getOrderById(orderId);
    }

    @PutMapping("/{orderId}/status")
    public OrderResponse updateOrderStatus(@PathVariable String orderId, @RequestParam String status) {
        return orderService.updateOrderStatus(orderId, status);
    }

@DeleteMapping("/{orderId}")
    public ResponseEntity<?> deleteOrder(
        @PathVariable String orderId // Nhận orderId từ URL
    ) {
        try {
            orderService.deleteOrder(orderId);
            return ResponseEntity.ok().build();
        } catch (OrderNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
    
}