package com.nahuannghia.shopnhn.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nahuannghia.shopnhn.Response.OrderPaymentStateResponse;
import com.nahuannghia.shopnhn.Response.OrderResponse;
import com.nahuannghia.shopnhn.Response.OrderStateResponse;
import com.nahuannghia.shopnhn.request.OrderPaymentStateRequest;
import com.nahuannghia.shopnhn.request.OrderRequest;
import com.nahuannghia.shopnhn.request.OrderStateRequest;
import com.nahuannghia.shopnhn.service.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/add")
    public OrderResponse createOrder(@RequestBody OrderRequest request) {
        return orderService.createOrder(request);
    }

    @GetMapping
    public List<OrderResponse> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{orderId}")
    public OrderResponse getOrderById(@PathVariable("orderId") Integer orderId) {
        return orderService.getOrderById(orderId);
    }

    @GetMapping("/user/{userId}")
    public List<OrderResponse> getAllOrderByUserId(@PathVariable("userId") Integer userId){
        return orderService.getAllOrderByUserId(userId);
    }

    @PutMapping("/status")
    public ResponseEntity<OrderStateResponse> updateOrderState(@RequestBody OrderStateRequest request) {
        OrderStateResponse response = orderService.updateStateOrder(request);
        return ResponseEntity.ok(response);
    }
    @PutMapping("/payment-status")
    public ResponseEntity<OrderPaymentStateResponse> updateOrderPaymentState(@RequestBody OrderPaymentStateRequest request) {
        OrderPaymentStateResponse response = orderService.updatePaymentStateOrder(request);
        return ResponseEntity.ok(response);
    }

}