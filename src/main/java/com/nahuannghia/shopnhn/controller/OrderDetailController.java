package com.nahuannghia.shopnhn.controller;

import com.nahuannghia.shopnhn.model.OrderDetail;
import com.nahuannghia.shopnhn.model.OrderDetailId;
import com.nahuannghia.shopnhn.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order-details")
public class OrderDetailController {

    @Autowired
    private OrderDetailService orderDetailService;

    @GetMapping
    public List<OrderDetail> getAll() {
        return orderDetailService.getAll();
    }

    @GetMapping("/{orderId}/{productId}/{color}/{size}")
    public OrderDetail getById(@PathVariable Integer orderId,
                               @PathVariable Integer productId,
                               @PathVariable String color,
                               @PathVariable String size) {
        return orderDetailService.getById(new OrderDetailId(orderId, productId, color, size));
    }


    @PostMapping("/add")
    public OrderDetail create(@RequestBody OrderDetail orderDetail) {
        return orderDetailService.create(orderDetail);
    }

    @PutMapping
    public OrderDetail update(@RequestBody OrderDetail orderDetail) {
        return orderDetailService.update(orderDetail);
    }

    @DeleteMapping("/{orderId}/{productId}/{color}/{size}")
    public void delete(@PathVariable Integer orderId,
                       @PathVariable Integer productId,
                       @PathVariable String color,
                       @PathVariable String size) {
        orderDetailService.delete(new OrderDetailId(orderId, productId, color, size));
    }
}
