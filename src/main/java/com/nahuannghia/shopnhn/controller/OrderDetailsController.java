package com.nahuannghia.shopnhn.controller;

import com.nahuannghia.shopnhn.model.OrderDetails;
import com.nahuannghia.shopnhn.model.OrderDetailsKey;
import com.nahuannghia.shopnhn.service.OrderDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order-details")
public class OrderDetailsController {
    @Autowired
    private OrderDetailsService orderDetailsService;
    @PostMapping
    public OrderDetails addOrderDetails(@RequestBody OrderDetails orderDetails){
        return orderDetailsService.addOrderDetails(orderDetails);
    }
    @GetMapping
    public List<OrderDetails> getAllOrderDetails(){
        return orderDetailsService.getAllOrderDetails();
    }
    @GetMapping("/{orderId}/{productId}")
    public OrderDetails getOrderDetails(@PathVariable String orderId, @PathVariable String productId) {
        return orderDetailsService.getOrderDetails(new OrderDetailsKey(orderId, productId));
    }
    @PutMapping("/{orderId}/{productId}")
    public OrderDetails updateOrderDetails(@PathVariable String orderId,
                                           @PathVariable String productId,
                                           @RequestBody OrderDetails orderDetails){
        return orderDetailsService.updateOrderDetails(new OrderDetailsKey(orderId, productId), orderDetails);
    }
    @DeleteMapping("/{orderId}/{productId}")
    public void deleteOrderDetails(@PathVariable String orderId, @PathVariable String productId){
        orderDetailsService.deleteOrderDetails(new OrderDetailsKey(orderId, productId));
    }
}
