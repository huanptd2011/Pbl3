package com.nahuannghia.shopnhn.service;


import com.nahuannghia.shopnhn.Response.OrderDetailResponse;
import com.nahuannghia.shopnhn.model.Order;
import com.nahuannghia.shopnhn.model.OrderDetail;
import com.nahuannghia.shopnhn.model.OrderDetailId;
import com.nahuannghia.shopnhn.model.Product;
import com.nahuannghia.shopnhn.repository.OrderDetailRepository;
import com.nahuannghia.shopnhn.repository.OrderRepository;
import com.nahuannghia.shopnhn.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderDetailService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    public List<OrderDetail> getAll() {
        return orderDetailRepository.findAll();
    }

    public OrderDetail getById(OrderDetailId id) {
        return orderDetailRepository.findById(id).orElse(null);
    }

    public OrderDetail create(OrderDetail orderDetail) {
        Order order = orderRepository.findById(orderDetail.getOrder().getOrderId()).orElseThrow();
        Product product = productRepository.findById(orderDetail.getProduct().getProductId()).orElseThrow();
        orderDetail.setOrder(order);
        orderDetail.setProduct(product);

        return orderDetailRepository.save(orderDetail);
    }

    public OrderDetail update(OrderDetail orderDetail) {
        return create(orderDetail); // override nếu đã tồn tại
    }

    public void delete(OrderDetailId id) {
        orderDetailRepository.deleteById(id);
    }

    public List<OrderDetailResponse> getOrderDetailByOrderId(Integer orderId){
        List<OrderDetail> orderDetails = orderDetailRepository.findByOrderDetailIdOrderId(orderId);
        return orderDetails.stream().map(orderDetail -> {
            return new OrderDetailResponse(
                    orderDetail.getProduct().getProductId(),
                    orderDetail.getQuantity(),
                    orderDetail.getTotal_price(),
                    orderDetail.getColor(),
                    orderDetail.getSize()
            );
        }).collect(Collectors.toList());
    }
}
