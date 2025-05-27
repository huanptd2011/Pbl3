package com.nahuannghia.shopnhn.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.nahuannghia.shopnhn.model.*;
import com.nahuannghia.shopnhn.repository.*;
import com.nahuannghia.shopnhn.request.OrderDetailRequest;
import com.nahuannghia.shopnhn.request.ProductInventoryRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nahuannghia.shopnhn.Response.OrderDetailResponse;
import com.nahuannghia.shopnhn.Response.OrderResponse;
import com.nahuannghia.shopnhn.Response.PaymentMethodResponse;
import com.nahuannghia.shopnhn.request.OrderRequest;

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
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductInventoryRepository productInventoryRepository;

    @Autowired
    private ProductInventoryService productInventoryService;


    public OrderResponse createOrder(OrderRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        PaymentMethod paymentMethod = paymentMethodRepository.findById(request.getPaymentMethodId())
                .orElseThrow(() -> new RuntimeException("Payment method not found"));

        // 1. Tạo đơn hàng chính
        Order order = new Order();
        order.setUser(user);
        order.setPaymentMethod(paymentMethod);
        order.setOrderDate(LocalDateTime.now());
        order.setTotalPrice(request.getTotalPrice());
        order.setOrderState("Chờ xác nhận");
        order.setNote(request.getNote());
        order.setPhone(request.getPhone());
        order.setAddress(request.getAddress());

        Order savedOrder = orderRepository.save(order);

        // 2. Lưu chi tiết đơn hàng
        for (OrderDetailRequest detailRequest : request.getListOrderDetail()) {
            Product product = productRepository.findById(detailRequest.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found: " + detailRequest.getProductId()));

            //Giam so luong ngay khi dat hang
            var productInventory = productInventoryRepository.findByProductInventoryId_ProductIdAndProductInventoryId_ColorAndProductInventoryId_Size(product.getProductId(), detailRequest.getColor(), detailRequest.getSize());
            productInventoryService.updateProductInventory(product.getProductId(), detailRequest.getColor(), detailRequest.getSize(),
                    new ProductInventoryRequest(product.getProductId(),
                            detailRequest.getColor(),
                            detailRequest.getSize(), productInventory.get().getQuantity() - detailRequest.getQuantity()));

            OrderDetailId orderDetailId = new OrderDetailId(
                    order.getOrderId(),
                    product.getProductId(),
                    detailRequest.getColor(),
                    detailRequest.getSize()
            );

            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderDetailId(orderDetailId);
            orderDetail.setOrder(savedOrder);
            orderDetail.setProduct(product);
            orderDetail.setQuantity(detailRequest.getQuantity());
            orderDetail.setPrice(detailRequest.getPrice());
            orderDetail.setColor(detailRequest.getColor());
            orderDetail.setPrice(detailRequest.getPrice());

            orderDetailRepository.save(orderDetail);
        }

        // 3. Trả về response
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
        if(Objects.equals(newStatus, "cancel")){
            order.setOrderState("Đã hủy");
        }
        if(Objects.equals(newStatus, "complete")){
            order.setOrderState("Đã giao");
        }
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

        // Khởi tạo đối tượng OrderResponse trước
        OrderResponse response = new OrderResponse(
                order.getOrderId(),
                order.getUser().getUserId(),
                paymentMethodResponse,
                order.getOrderDate(),
                order.getTotalPrice(),
                order.getOrderState(),
                order.getNote(),
                order.getPhone(),
                order.getAddress(),
                list
        );

        // Gán orderDetails sau khi đã có object
        response.setOrderDetails(list);

        return response;
    }

    public List<OrderResponse> getAllOrderByUserId(Integer userId){
        try{
            List<Order> list = orderRepository.getAllOrderByUserId(userId);
            return list.stream()
                    .map(this::mapToResponse)
                    .collect(Collectors.toList());
        } catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    public Map<String, List<OrderResponse>> getOrdersGroupedByStatus(Integer userId, String orderState ) {
        List<OrderResponse> orders;
        if (orderState != null && !orderState.isEmpty()) {
            orders = orderRepository.findOrdersByUserIdAndOrderState(userId, orderState); // Specific state
        } else {
            orders = orderRepository.findOrdersByUserId(userId); // All states
        }

        for (OrderResponse order : orders) {
            List<OrderDetailResponse> details = orderDetailRepository.findOrderDetailsByOrderId(order.getOrderId());
            order.setOrderDetails(details);
        }

        return orders.stream()
                     .collect(Collectors.groupingBy(OrderResponse::getOrderState));
    }
}
