package com.nahuannghia.shopnhn.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nahuannghia.shopnhn.Response.OrderResponse;
import com.nahuannghia.shopnhn.request.OrderRequest;
import com.nahuannghia.shopnhn.service.OrderService;

@RestController
@RequestMapping("/api/orders")
//@CrossOrigin(origins = {"http://localhost:5173"})
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

    @DeleteMapping("/{orderId}")
    public void deleteOrder(@PathVariable Integer orderId) {
        orderService.deleteOrder(orderId);
    }



    //chưa xu li duoc tren api
    @PutMapping("/{orderId}/{status}")
    public OrderResponse updateOrderStatus(@PathVariable("orderId") Integer orderId,
                                           @PathVariable("status") String status) {
        return orderService.updateOrderStatus(orderId, status);
    }

    @GetMapping("/group-by-status")
    public ResponseEntity<Map<String, List<OrderResponse>>> getOrdersGroupedByStatus(@RequestParam Integer userId,
    @RequestParam(required = false, defaultValue = "") String orderState) {
        Map<String, List<OrderResponse>> groupedOrders = orderService.getOrdersGroupedByStatus(userId,orderState);
        return ResponseEntity.ok(groupedOrders);
    }
}
