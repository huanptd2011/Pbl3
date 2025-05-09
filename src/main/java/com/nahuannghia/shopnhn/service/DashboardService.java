package com.nahuannghia.shopnhn.service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nahuannghia.shopnhn.Response.DashboardSummaryResponse;
import com.nahuannghia.shopnhn.Response.OrderDataResponse;
import com.nahuannghia.shopnhn.Response.OrderStateCount;
import com.nahuannghia.shopnhn.Response.RecentOrderResponse;
import com.nahuannghia.shopnhn.Response.SalesDataResponse;
import com.nahuannghia.shopnhn.Response.TopSellingProductResponse;
import com.nahuannghia.shopnhn.model.Order;
import com.nahuannghia.shopnhn.repository.OrderRepository;
import com.nahuannghia.shopnhn.repository.ProductRepository;
import com.nahuannghia.shopnhn.repository.UserRepository;

@Service
public class DashboardService {

    private static final int LOW_STOCK_THRESHOLD = 10;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Get dashboard summary metrics
     * @return DashboardSummaryResponse with key metrics
     */
    public DashboardSummaryResponse getDashboardSummary() {
        // Get total revenue
        BigDecimal totalRevenue = orderRepository.sumTotalRevenue();
        
        // Get total orders
        long totalOrders = orderRepository.count();
        
        // Get total users
        long totalUsers = userRepository.count();
        
        // Get low stock products count
        long lowStockProducts = productRepository.countLowStockProducts(LOW_STOCK_THRESHOLD);
        
        // Get pending orders count
        long pendingOrders = orderRepository.countByOrderState("Chờ xác nhận");
        
        // Calculate revenue for current month
        LocalDateTime startOfMonth = LocalDate.now().withDayOfMonth(1).atStartOfDay();
        BigDecimal monthlyRevenue = orderRepository.sumTotalRevenueFromDate(startOfMonth);
        
        return new DashboardSummaryResponse(
            totalRevenue, 
            totalOrders, 
            totalUsers, 
            lowStockProducts,
            pendingOrders,
            monthlyRevenue
        );
    }


    public List<SalesDataResponse> getSalesOverTime(String period, LocalDate startDate, LocalDate endDate) {
        List<SalesDataResponse> salesData = new ArrayList<>();
        
        // Set default dates if not provided
        LocalDateTime start = startDate != null ? startDate.atStartOfDay() : 
                             LocalDate.now().minusMonths(1).atStartOfDay();
        LocalDateTime end = endDate != null ? endDate.atTime(23, 59, 59) : 
                           LocalDate.now().atTime(23, 59, 59);
        
        DateTimeFormatter formatter;
        Map<String, BigDecimal> aggregatedData = new HashMap<>();
        
        switch (period.toLowerCase()) {
            case "daily":
                formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                orderRepository.findOrdersBetweenDates(start, end).forEach(order -> {
                    String dateKey = order.getOrderDate().format(formatter);
                    aggregatedData.merge(dateKey, order.getTotalPrice(), BigDecimal::add);
                });
                break;
                
            case "weekly":
                orderRepository.findOrdersBetweenDates(start, end).forEach(order -> {
                    LocalDate orderDate = order.getOrderDate().toLocalDate();
                    String weekKey = orderDate.getYear() + "-W" + orderDate.get(java.time.temporal.WeekFields.ISO.weekOfWeekBasedYear());
                    aggregatedData.merge(weekKey, order.getTotalPrice(), BigDecimal::add);
                });
                break;
                
            case "monthly":
                formatter = DateTimeFormatter.ofPattern("yyyy-MM");
                orderRepository.findOrdersBetweenDates(start, end).forEach(order -> {
                    String monthKey = order.getOrderDate().format(formatter);
                    aggregatedData.merge(monthKey, order.getTotalPrice(), BigDecimal::add);
                });
                break;
                
            case "yearly":
                formatter = DateTimeFormatter.ofPattern("yyyy");
                orderRepository.findOrdersBetweenDates(start, end).forEach(order -> {
                    String yearKey = order.getOrderDate().format(formatter);
                    aggregatedData.merge(yearKey, order.getTotalPrice(), BigDecimal::add);
                });
                break;
                
            default:
                // Default to daily if invalid period is provided
                formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                orderRepository.findOrdersBetweenDates(start, end).forEach(order -> {
                    String dateKey = order.getOrderDate().format(formatter);
                    aggregatedData.merge(dateKey, order.getTotalPrice(), BigDecimal::add);
                });
        }
        
        // Convert aggregated data to Responses
        aggregatedData.forEach((timeLabel, revenue) -> {
            salesData.add(new SalesDataResponse(timeLabel, revenue));
        });
        
        return salesData;
    }

    public List<OrderDataResponse> getOrdersData(String type, String period) {
        List<OrderDataResponse> orderData = new ArrayList<>();
        
        if ("by-status".equalsIgnoreCase(type)) {
            // Get orders grouped by status
            List<OrderStateCount> results = orderRepository.countByOrderState();
            Map<String, Long> ordersByStatus = results.stream()
            .collect(Collectors.toMap(OrderStateCount::getState, OrderStateCount::getCount));

            
            ordersByStatus.forEach((status, count) -> {
                orderData.add(new OrderDataResponse(status, count));
            });
        } else if ("over-time".equalsIgnoreCase(type)) {
            // Get orders over time based on period
            LocalDateTime start;
            LocalDateTime end = LocalDateTime.now();
            DateTimeFormatter formatter;
            
            switch (period.toLowerCase()) {
                case "weekly":
                    start = LocalDateTime.now().minusWeeks(4);
                    formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    break;
                case "monthly":
                    start = LocalDateTime.now().minusMonths(6);
                    formatter = DateTimeFormatter.ofPattern("yyyy-MM");
                    break;
                case "yearly":
                    start = LocalDateTime.now().minusYears(1);
                    formatter = DateTimeFormatter.ofPattern("yyyy");
                    break;
                default:
                    // Default to daily for last 30 days
                    start = LocalDateTime.now().minusDays(30);
                    formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            }
            
            Map<String, Long> ordersOverTime = new HashMap<>();
            orderRepository.findOrdersBetweenDates(start, end).forEach(order -> {
                String timeKey = order.getOrderDate().format(formatter);
                ordersOverTime.merge(timeKey, 1L, Long::sum);
            });
            
            ordersOverTime.forEach((timeLabel, count) -> {
                orderData.add(new OrderDataResponse(timeLabel, count));
            });
        }
        
        return orderData;
    }


    public List<TopSellingProductResponse> getTopSellingProducts(int limit, String period) {
        LocalDateTime startDate;
        LocalDateTime endDate = LocalDateTime.now();
    
        switch (period.toLowerCase()) {
            case "last-7-days":
                startDate = endDate.minusDays(7);
                break;
            case "last-30-days":
                startDate = endDate.minusDays(30);
                break;
            case "last-year":
                startDate = endDate.minusYears(1);
                break;
            default:
                startDate = LocalDateTime.MIN;
        }
    
        List<Object[]> allResults = orderRepository.findTopSellingProducts(startDate, endDate);
    
        // Giới hạn số lượng kết quả
        List<Object[]> limitedResults = allResults.size() > limit
                ? allResults.subList(0, limit)
                : allResults;
    
        List<TopSellingProductResponse> response = new ArrayList<>();
        for (Object[] row : limitedResults) {
            response.add(new TopSellingProductResponse(
                    (Integer) row[0],
                    (String) row[1],
                    ((Number) row[2]).longValue(),
                    (BigDecimal) row[3]
            ));
        }
    
        return response;
    }
    
    

    public List<RecentOrderResponse> getRecentOrders(int limit) {
        List<Order> recentOrders = orderRepository.findTopByOrderByOrderDateDesc();
        recentOrders = recentOrders.size() > limit
                ? recentOrders.subList(0, limit)
                : recentOrders;
        
        return recentOrders.stream()
            .map(order -> new RecentOrderResponse(
                order.getOrderId(),
                order.getUser().getUsername(),
                order.getOrderDate(),
                order.getTotalPrice(),
                order.getOrderState())
            )
            .collect(Collectors.toList());
    }
}