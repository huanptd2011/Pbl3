package com.nahuannghia.shopnhn.service;

import com.nahuannghia.shopnhn.Response.OrderDetailResponse;
import com.nahuannghia.shopnhn.Response.OrderResponse;
import com.nahuannghia.shopnhn.Response.PaymentMethodResponse;
import com.nahuannghia.shopnhn.model.Order;
import com.nahuannghia.shopnhn.model.OrderDetail;
import com.nahuannghia.shopnhn.model.PaymentMethod;
import com.nahuannghia.shopnhn.model.User;
import com.nahuannghia.shopnhn.repository.OrderDetailRepository;
import com.nahuannghia.shopnhn.repository.OrderRepository;
import com.nahuannghia.shopnhn.repository.PaymentMethodRepository;
import com.nahuannghia.shopnhn.repository.UserRepository;
import com.nahuannghia.shopnhn.request.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    public OrderResponse createOrder(OrderRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        PaymentMethod paymentMethod = paymentMethodRepository.findById(request.getPaymentMethodId())
                .orElseThrow(() -> new RuntimeException("Payment method not found"));

        Order order = new Order();
        order.setUser(user);
        order.setPaymentMethod(paymentMethod);
        order.setOrderDate(LocalDateTime.now());
        order.setTotalPrice(request.getTotalPrice());
        order.setOrderState("Chờ xác nhận");
        order.setNote(request.getNote());

        Order savedOrder = orderRepository.save(order);
        return mapToResponse(savedOrder);
    }

    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public OrderResponse getOrderById(Integer orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return mapToResponse(order);
    }

    public OrderResponse updateOrderStatus(Integer orderId, String newStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setOrderState(newStatus);
        return mapToResponse(orderRepository.save(order));
    }

    public void deleteOrder(Integer orderId) {
        orderRepository.deleteById(orderId);
    }

    private OrderResponse mapToResponse(Order order) {
        PaymentMethodResponse paymentMethodResponse = new PaymentMethodResponse(
                order.getPaymentMethod().getPaymentMethodId(),
                order.getPaymentMethod().getPaymentMethodName()
        );
        List<OrderDetailResponse> list = orderDetailService.getOrderDetailByOrderId(order.getOrderId());
        return new OrderResponse(
                order.getOrderId(),
                order.getUser().getUserId(),
                paymentMethodResponse,
                order.getOrderDate(),
                order.getTotalPrice(),
                order.getOrderState(),
                order.getNote(),
                list
        );
    }
}
