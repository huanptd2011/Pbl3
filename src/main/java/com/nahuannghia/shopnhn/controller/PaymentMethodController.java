package com.nahuannghia.shopnhn.controller;

import com.nahuannghia.shopnhn.request.PaymentMethodRequest;
import com.nahuannghia.shopnhn.Response.PaymentMethodResponse;
import com.nahuannghia.shopnhn.service.PaymentMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payment-methods")
public class PaymentMethodController {

    @Autowired
    private PaymentMethodService paymentMethodService;

    @PostMapping("/add")
    public PaymentMethodResponse create(@RequestBody PaymentMethodRequest request) {
        return paymentMethodService.createPaymentMethod(request);
    }

    @GetMapping
    public List<PaymentMethodResponse> getAll() {
        return paymentMethodService.getAllPaymentMethods();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        paymentMethodService.safeDelete(id);
    }
    @PutMapping("/{id}")
    public PaymentMethodResponse update(@PathVariable Integer id, @RequestBody PaymentMethodRequest request) {
        return paymentMethodService.updatePaymentMethod(id, request);
    }
    @GetMapping("/search")
    public List<PaymentMethodResponse> search(@RequestParam String name) {
        return paymentMethodService.searchByName(name);
    }
}
