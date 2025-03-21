package com.nahuannghia.shopnhn.service;

import com.nahuannghia.shopnhn.model.OrderDetails;
import com.nahuannghia.shopnhn.model.OrderDetailsKey;
import com.nahuannghia.shopnhn.repository.OrderDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailsService {
    @Autowired
    private OrderDetailsRepository orderDetailsRepository;
    public OrderDetails addOrderDetails(OrderDetails orderDetails){
        try {
            return orderDetailsRepository.save(orderDetails);
        } catch (Exception e){
            throw new RuntimeException("Error adding");
        }
    }
    public List<OrderDetails> getAllOrderDetails(){
        List<OrderDetails> list = orderDetailsRepository.findAll();
        if(list.isEmpty())
            throw new RuntimeException("No order details");
        return list;
    }
    public OrderDetails getOrderDetails(OrderDetailsKey orderDetailsKey){
        return orderDetailsRepository.findById(orderDetailsKey).orElseThrow(()->
                new RuntimeException("Not found"));
    }
    public OrderDetails updateOrderDetails(OrderDetailsKey orderDetailsKey, OrderDetails orderDetails){
        OrderDetails existing = getOrderDetails(orderDetailsKey);
        existing.setOrder(orderDetails.getOrder());
        existing.setDiscount(orderDetails.getDiscount());
        existing.setQuantity(orderDetails.getQuantity());
        existing.setProduct(orderDetails.getProduct());
        existing.setTotalPrice(orderDetails.getTotalPrice());
        existing.setUnit(orderDetails.getUnit());
        existing.setUnitPrice(orderDetails.getUnitPrice());
        try{
            return orderDetailsRepository.save(existing);
        } catch (Exception e){
            throw new RuntimeException("Error updating");
        }
    }
    public void deleteOrderDetails(OrderDetailsKey orderDetailsKey){
        OrderDetails existing = getOrderDetails(orderDetailsKey);
        try{
            orderDetailsRepository.delete(existing);
        } catch (Exception e){
            throw new RuntimeException("Error deleting");
        }
    }
}
