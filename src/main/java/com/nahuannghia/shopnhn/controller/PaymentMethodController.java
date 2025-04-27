package com.nahuannghia.shopnhn.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nahuannghia.shopnhn.Response.PaymentMethodResponse;
import com.nahuannghia.shopnhn.request.PaymentMethodRequest;
import com.nahuannghia.shopnhn.service.PaymentMethodService;

@RestController
@RequestMapping("/api/payment-methods")
@CrossOrigin(origins = {"http://localhost:63342", "http://127.0.0.1:5501", "http://127.0.0.1:5500","http://localhost:5173/"})
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
