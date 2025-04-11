package com.nahuannghia.shopnhn.service;

import com.nahuannghia.shopnhn.model.PaymentMethod;
import com.nahuannghia.shopnhn.repository.OrderRepository;
import com.nahuannghia.shopnhn.repository.PaymentMethodRepository;
import com.nahuannghia.shopnhn.request.PaymentMethodRequest;
import com.nahuannghia.shopnhn.Response.PaymentMethodResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentMethodService {

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    public PaymentMethodResponse createPaymentMethod(PaymentMethodRequest request) {
        PaymentMethod method = new PaymentMethod();
        method.setPaymentMethodName(request.getPaymentMethodName());
        PaymentMethod saved = paymentMethodRepository.save(method);
        return mapToResponse(saved);
    }

    public List<PaymentMethodResponse> getAllPaymentMethods() {
        return paymentMethodRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

//    public void deletePaymentMethod(Integer id) {
//        paymentMethodRepository.deleteById(id);
//    }

    private PaymentMethodResponse mapToResponse(PaymentMethod method) {
        return new PaymentMethodResponse(method.getPaymentMethodId(), method.getPaymentMethodName());
    }

    public PaymentMethodResponse updatePaymentMethod(Integer id, PaymentMethodRequest request) {
        PaymentMethod method = paymentMethodRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy phương thức"));

        method.setPaymentMethodName(request.getPaymentMethodName());
        return mapToResponse(paymentMethodRepository.save(method));
    }

    public List<PaymentMethodResponse> searchByName(String keyword) {
        return paymentMethodRepository.findByPaymentMethodNameContainingIgnoreCase(keyword)
                .stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Autowired
    private OrderRepository orderRepository;

    public void safeDelete(Integer id) {
        boolean isUsed = orderRepository.existsByPaymentMethod_PaymentMethodId(id);
        if (isUsed) {
            throw new RuntimeException("Phương thức thanh toán đang được sử dụng, không thể xóa.");
        }
        paymentMethodRepository.deleteById(id);
    }

}
