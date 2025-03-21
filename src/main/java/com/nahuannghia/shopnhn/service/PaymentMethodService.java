package com.nahuannghia.shopnhn.service;

import com.nahuannghia.shopnhn.model.PaymentMethod;
import com.nahuannghia.shopnhn.repository.PaymentMethodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentMethodService {
    @Autowired
    private PaymentMethodRepository paymentMethodRepository;
    public PaymentMethod addPaymentMethod(PaymentMethod paymentMethod){
        try{
            return paymentMethodRepository.save(paymentMethod);
        } catch (Exception e){
            throw new RuntimeException("Error adding");
        }
    }
    public List<PaymentMethod> getAllPaymentMethods(){
        List<PaymentMethod> list = paymentMethodRepository.findAll();
        if(list.isEmpty())
            throw new RuntimeException("No payment method");
        return list;
    }
    public PaymentMethod getPaymentMethod(Long paymentMethodId){
        return paymentMethodRepository.findById(paymentMethodId).orElseThrow(() ->
                new RuntimeException("Not found"));
    }
    public PaymentMethod updatePaymentMethod(Long paymentMethodId, PaymentMethod paymentMethod){
        PaymentMethod existing = getPaymentMethod(paymentMethodId);
        existing.setPaymentMethodName(paymentMethod.getPaymentMethodName());
        try{
            return paymentMethodRepository.save(existing);
        } catch (Exception e){
            throw new RuntimeException("Error updating");
        }
    }
    public void deletePaymentMethod(Long paymentMethodId){
        PaymentMethod existing = getPaymentMethod(paymentMethodId);
        try{
            paymentMethodRepository.delete(existing);
        } catch (Exception e){
            throw new RuntimeException("Error deleting");
        }
    }
}
