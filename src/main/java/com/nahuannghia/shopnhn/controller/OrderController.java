package com.nahuannghia.shopnhn.controller;

import com.nahuannghia.shopnhn.model.Order;
import com.nahuannghia.shopnhn.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @PostMapping
    public Order addOrder(@RequestBody Order order){
        return orderService.addOrder(order);
    }
    @GetMapping
    public List<Order> getAllOrders(){
        return orderService.getAllOrders();
    }
    @GetMapping("/{orderId}")
    public Order getOrder(@PathVariable String orderId){
        return orderService.getOrder(orderId);
    }
    @PutMapping("/{orderId}")
    public Order updateOrder(@PathVariable String orderId, @RequestBody Order order){
        return orderService.updateOrder(orderId, order);
    }
    @DeleteMapping("/{orderId}")
    public void deleteOrder(@PathVariable String orderId){
        orderService.deleteOrder(orderId);
    }
}
