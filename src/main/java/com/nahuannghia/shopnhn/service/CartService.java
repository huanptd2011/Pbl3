package com.nahuannghia.shopnhn.service;

import com.nahuannghia.shopnhn.Response.CartResponse;
import com.nahuannghia.shopnhn.model.Cart;
import com.nahuannghia.shopnhn.model.Customer;
import com.nahuannghia.shopnhn.repository.CartRepository;
import com.nahuannghia.shopnhn.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public CartResponse createCart(Integer customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khách hàng"));

        Cart cart = new Cart();
        cart.setCustomer(customer);
        cart.setCreatedAt(LocalDateTime.now());
        cart.setCartStatus("Đang mua");

        Cart savedCart = cartRepository.save(cart);

        return toResponse(savedCart);
    }

    public CartResponse getCartById(Integer cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy giỏ hàng"));
        return toResponse(cart);
    }

    public List<CartResponse> getCartsByCustomerId(Integer customerId) {
        List<Cart> carts = cartRepository.findByCustomerCustomerId(customerId);
        return carts.stream().map(this::toResponse).collect(Collectors.toList());
    }

    public CartResponse updateCartStatus(Integer cartId, String status) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy giỏ hàng"));

        cart.setCartStatus(status);
        Cart updatedCart = cartRepository.save(cart);
        return toResponse(updatedCart);
    }

    public void deleteCart(Integer cartId) {
        if (!cartRepository.existsById(cartId)) {
            throw new RuntimeException("Giỏ hàng không tồn tại");
        }
        cartRepository.deleteById(cartId);
    }

    private CartResponse toResponse(Cart cart) {
        return new CartResponse(
                cart.getCartId(),
                cart.getCustomer().getCustomerId(),
                cart.getCreatedAt(),
                cart.getCartStatus()
        );
    }
}
