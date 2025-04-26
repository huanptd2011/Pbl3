package com.nahuannghia.shopnhn.service;


import com.nahuannghia.shopnhn.Response.ProductInventoryResponse;
import com.nahuannghia.shopnhn.model.*;
import com.nahuannghia.shopnhn.repository.OrderDetailRepository;
import com.nahuannghia.shopnhn.repository.OrderRepository;
import com.nahuannghia.shopnhn.repository.ProductInventoryRepository;
import com.nahuannghia.shopnhn.repository.ProductRepository;
import com.nahuannghia.shopnhn.request.ProductInventoryRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductInventoryService productInventoryService;
    @Autowired
    private ProductInventoryRepository productInventoryRepository;

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

        var inventory = productInventoryRepository
                .findByProductInventoryId_ProductIdAndProductInventoryId_ColorAndProductInventoryId_Size(
                        product.getProductId(),
                        orderDetail.getColor(),
                        orderDetail.getSize()
                );
        if(inventory.isEmpty()){
            throw new RuntimeException("Error");
        }
        productInventoryService.updateProductInventory(
                product.getProductId(),
                orderDetail.getColor(),
                orderDetail.getSize(),
                new ProductInventoryRequest(product.getProductId(),
                                            orderDetail.getColor(),
                                            orderDetail.getSize(),
                                            inventory.get().getQuantity() - orderDetail.getQuantity())
        );

        return orderDetailRepository.save(orderDetail);
    }

    public OrderDetail update(OrderDetail orderDetail) {
        return create(orderDetail); // override nếu đã tồn tại
    }

    public void delete(OrderDetailId id) {
        orderDetailRepository.deleteById(id);
    }
}
