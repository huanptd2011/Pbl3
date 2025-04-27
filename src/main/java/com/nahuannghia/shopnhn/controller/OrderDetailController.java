package com.nahuannghia.shopnhn.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nahuannghia.shopnhn.model.OrderDetail;
import com.nahuannghia.shopnhn.model.OrderDetailId;
import com.nahuannghia.shopnhn.service.OrderDetailService;

@RestController
@RequestMapping("/api/order-details")
@CrossOrigin(origins = {"http://localhost:63342", "http://127.0.0.1:5501", "http://127.0.0.1:5500","http://localhost:5173/"})
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


    @PostMapping
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
