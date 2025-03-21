package com.nahuannghia.shopnhn.controller;

import com.nahuannghia.shopnhn.model.PaymentMethod;
import com.nahuannghia.shopnhn.service.PaymentMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payment-method")
public class PaymentMethodController {
    @Autowired
    private PaymentMethodService paymentMethodService;
    @PostMapping
    public PaymentMethod addPaymentMethod(@RequestBody PaymentMethod paymentMethod){
        return paymentMethodService.addPaymentMethod(paymentMethod);
    }
    @GetMapping
    public List<PaymentMethod> getAllPaymentMethods(){
        return paymentMethodService.getAllPaymentMethods();
    }
    @GetMapping("/{paymentId}")
    public PaymentMethod getPaymentMethod(@PathVariable Long paymentId){
        return paymentMethodService.getPaymentMethod(paymentId);
    }
    @PutMapping("/{paymentId}")
    public PaymentMethod updatePaymentMethod(@PathVariable Long paymentId, @RequestBody PaymentMethod paymentMethod){
        return paymentMethodService.updatePaymentMethod(paymentId, paymentMethod);
    }
    @DeleteMapping("/{paymentId}")
    public void deletePaymentMethod(@PathVariable Long paymentId){
        paymentMethodService.deletePaymentMethod(paymentId);
    }
}
