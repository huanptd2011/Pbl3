package com.nahuannghia.shopnhn.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nahuannghia.shopnhn.Response.DashboardSummaryResponse;
import com.nahuannghia.shopnhn.Response.OrderDataResponse;
import com.nahuannghia.shopnhn.Response.RecentOrderResponse;
import com.nahuannghia.shopnhn.Response.SalesDataResponse;
import com.nahuannghia.shopnhn.Response.TopSellingProductResponse;
import com.nahuannghia.shopnhn.Response.TopUserSellerResponse;
import com.nahuannghia.shopnhn.service.DashboardService;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    /**
     * Get dashboard summary metrics
     * @return Dashboard summary with key metrics
     */
    @GetMapping("/summary")
    public ResponseEntity<DashboardSummaryResponse> getDashboardSummary() {
        DashboardSummaryResponse summary = dashboardService.getDashboardSummary();
        return ResponseEntity.ok(summary);
    }

    /**
     * Get sales data over time for charts
     * @param period Time period for aggregation (daily, weekly, monthly, yearly)
     * @param startDate Optional start date for custom range
     * @param endDate Optional end date for custom range
     * @return Sales data with time labels and revenue values
     */
    @GetMapping("/sales-over-time")
    public ResponseEntity<List<SalesDataResponse>> getSalesOverTime(
            @RequestParam(value = "period", defaultValue = "daily") String period,
            @RequestParam(value = "startDate", required = false) 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(value = "endDate", required = false) 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        
        List<SalesDataResponse> salesData = dashboardService.getSalesOverTime(period, startDate, endDate);
        return ResponseEntity.ok(salesData);
    }


    @GetMapping("/orders-data")
    public ResponseEntity<List<OrderDataResponse>> getOrdersData(
            @RequestParam(value = "type", defaultValue = "by-status") String type,
            @RequestParam(value = "period", defaultValue = "weekly") String period) {
        
        List<OrderDataResponse> orderData = dashboardService.getOrdersData(type, period);
        return ResponseEntity.ok(orderData);
    }


    @GetMapping("/top-selling-products")
    public ResponseEntity<List<TopSellingProductResponse>> getTopSellingProducts(
            @RequestParam(value = "limit", defaultValue = "5") int limit,
            @RequestParam(value = "period", defaultValue = "last-30-days") String period) {
        
        List<TopSellingProductResponse> topProducts = dashboardService.getTopSellingProducts(limit, period);
        return ResponseEntity.ok(topProducts);
    }


    @GetMapping("/recent-orders")
    public ResponseEntity<List<RecentOrderResponse>> getRecentOrders(
            @RequestParam(value = "limit", defaultValue = "10") int limit) {
        
        List<RecentOrderResponse> recentOrders = dashboardService.getRecentOrders(limit);
        return ResponseEntity.ok(recentOrders);
    }
     @GetMapping("/top-users")
    public ResponseEntity<List<TopUserSellerResponse>> getTopUserSeller() {
        List<TopUserSellerResponse> topUsers = dashboardService.getTopUserSeller();
        return ResponseEntity.ok(topUsers);
    }
}
