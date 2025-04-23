package com.nahuannghia.shopnhn.service;

import java.math.BigDecimal;

import com.nahuannghia.shopnhn.Response.OrderResponse;
import com.nahuannghia.shopnhn.model.Customer;
import com.nahuannghia.shopnhn.model.Order;
import com.nahuannghia.shopnhn.model.PaymentMethod;
import com.nahuannghia.shopnhn.repository.CustomerRepository;
import com.nahuannghia.shopnhn.repository.OrderRepository;
import com.nahuannghia.shopnhn.repository.PaymentMethodRepository;
import com.nahuannghia.shopnhn.request.OrderRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.nahuannghia.shopnhn.exeption.InvalidOrderIdException;
import com.nahuannghia.shopnhn.exeption.OrderNotFoundException;

@Service
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);
    private static final List<String> VALID_STATUSES = Arrays.asList("Chờ xác nhận", "Đang xử lý", "Đã giao", "Hủy");

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    public OrderService() {
        logger.info("OrderService initialized");
    }

    @Transactional
    public OrderResponse createOrder(OrderRequest request) {
        // Validate input
        if (request == null) {
            throw new IllegalArgumentException("Order request cannot be null");
        }
        if (request.getTotalPrice().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Total price cannot be negative");
        }
        if (request.getNote() != null && request.getNote().length() > 500) {
            throw new IllegalArgumentException("Note exceeds maximum length of 500 characters");
        }

        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found with ID: " + request.getCustomerId()));

        PaymentMethod paymentMethod = paymentMethodRepository.findById(request.getPaymentMethodId())
                .orElseThrow(() -> new RuntimeException("Payment method not found with ID: " + request.getPaymentMethodId()));

        Order order = new Order();
        order.setOrderId(UUID.randomUUID().toString()); // Generate a unique String ID
        order.setCustomer(customer);
        order.setPaymentMethod(paymentMethod);
        order.setOrderDate(LocalDateTime.now());
        order.setTotalPrice(request.getTotalPrice());
        order.setOrderState("Chờ xác nhận");
        order.setNote(request.getNote());

        Order savedOrder = orderRepository.save(order);
        logger.info("Created order with ID: {}", savedOrder.getOrderId());
        return mapToResponse(savedOrder);
    }

    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public OrderResponse getOrderById(String orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));
        return mapToResponse(order);
    }

    @Transactional
    public OrderResponse updateOrderStatus(String orderId, String newStatus) {
        if (newStatus == null || !VALID_STATUSES.contains(newStatus)) {
            throw new IllegalArgumentException("Invalid status: " + newStatus + ". Valid statuses are: " + VALID_STATUSES);
        }

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));
        order.setOrderState(newStatus);
        Order updatedOrder = orderRepository.save(order);
        logger.info("Updated order ID: {} to status: {}", orderId, newStatus);
        return mapToResponse(updatedOrder);
    }
    @Transactional
    public void deleteOrder(String orderId) {
        validateOrderId(orderId); // Bước validate đầu tiên
        
        int deletedCount = orderRepository.deleteByOrderId(orderId);
        
        if (deletedCount == 0) {
            
            throw new OrderNotFoundException("Order not found with ID: " + orderId, orderId);
        }
        
    }

    private void validateOrderId(String orderId) {
        if (orderId == null || orderId.isBlank()) {
            throw new InvalidOrderIdException("Order ID cannot be empty");
        }
        
        if (orderId.equalsIgnoreCase("undefined") || orderId.equalsIgnoreCase("null")) {
            throw new InvalidOrderIdException("Invalid Order ID format");
        }
        
        if (!orderId.matches("^[a-zA-Z0-9-]{4,20}$")) {
            throw new InvalidOrderIdException("Order ID must be 8-20 alphanumeric characters");
        }
    }

    public List<OrderResponse> searchOrders(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            logger.info("No keyword provided, returning all orders");
            return orderRepository.findAll().stream()
                    .map(this::mapToResponse)
                    .collect(Collectors.toList());
        }
        List<Order> orders = orderRepository.searchByOrderId(keyword);
        logger.info("Found {} orders for keyword: {}", orders.size(), keyword);
        return orders.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public Page<OrderResponse> searchOrdersWithPagination(String keyword, int page, int size) {
        if (page < 0) {
            throw new IllegalArgumentException("Page number cannot be negative");
        }
        if (size <= 0) {
            throw new IllegalArgumentException("Page size must be greater than zero");
        }

        Pageable pageable = PageRequest.of(page, size);
        Page<Order> orderPage = orderRepository.searchByOrderId(keyword, pageable);
        logger.info("Returning paginated orders: page={}, size={}, total={}", page, size, orderPage.getTotalElements());
        return orderPage.map(this::mapToResponse);
    }

    private OrderResponse mapToResponse(Order order) {
        return new OrderResponse(
                order.getOrderId(),
                order.getCustomer().getCustomerId(),
                order.getPaymentMethod().getPaymentMethodId(),
                order.getOrderDate(),
                order.getTotalPrice(),
                order.getOrderState(),
                order.getNote()
        );
    }
}