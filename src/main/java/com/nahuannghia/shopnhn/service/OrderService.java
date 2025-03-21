package com.nahuannghia.shopnhn.service;

import com.nahuannghia.shopnhn.model.Order;
import com.nahuannghia.shopnhn.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    public Order addOrder(Order order){
        try {
            return orderRepository.save(order);
        } catch (Exception e){
            throw new RuntimeException("Error adding");
        }
    }
    public List<Order> getAllOrders(){
        List<Order> list = orderRepository.findAll();
        if(list.isEmpty())
            throw new RuntimeException("No order");
        return list;
    }
    public Order getOrder(String orderId){
        return orderRepository.findById(orderId).orElseThrow(()->
                new RuntimeException("Not found"));
    }
    public Order updateOrder(String orderId, Order order){
        Order existing = getOrder(orderId);
        existing.setOrderDate(order.getOrderDate());
        existing.setTotalAmount(order.getTotalAmount());
        existing.setOrderStatus(order.getOrderStatus());
        existing.setCustomer(order.getCustomer());
        existing.setNotes(order.getNotes());
        existing.setPaymentMethod(order.getPaymentMethod());
        existing.setShippingAddress(order.getShippingAddress());
        existing.setShippingCompany(order.getShippingCompany());
        try {
            return orderRepository.save(existing);
        } catch (Exception e){
            throw new RuntimeException("Error updating");
        }
    }
    public void deleteOrder(String orderId){
        Order existing = getOrder(orderId);
        try{
            orderRepository.delete(existing);
        } catch (Exception e){
            throw new RuntimeException("Error deleting");
        }
    }
}
