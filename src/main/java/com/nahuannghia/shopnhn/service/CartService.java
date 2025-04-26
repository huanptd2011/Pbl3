package com.nahuannghia.shopnhn.service;

import com.nahuannghia.shopnhn.Response.CartItemResponse;
import com.nahuannghia.shopnhn.Response.CartResponse;
import com.nahuannghia.shopnhn.model.Cart;
import com.nahuannghia.shopnhn.model.User;
import com.nahuannghia.shopnhn.repository.CartRepository;
import com.nahuannghia.shopnhn.repository.UserRepository;
import com.nahuannghia.shopnhn.request.CartRequest;
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
    private CartItemService cartItemService;
    @Autowired
    private UserRepository userRepository;

    public CartResponse createCart(CartRequest cartRequest) {
        User user = userRepository.findById(cartRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khách hàng"));

        Cart cart = new Cart();
        cart.setUser(user);
        cart.setCreatedAt(LocalDateTime.now());
        try {
            cart.setCartStatus("Đang mua");
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        try{
            cartRepository.save(cart);
        } catch (Exception e){
            System.out.println("LOg" + e.getMessage());
        }


        return toResponse(cart);
    }

    public CartResponse getCartById(Integer cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy giỏ hàng"));
        return toResponse(cart);
    }

    public List<CartResponse> getCartsByCustomerId(Integer userId) {
        List<Cart> carts = cartRepository.findByUser_UserId(userId);
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
        List<CartItemResponse> list = cartItemService.getItemsByCartId(cart.getCartId());
        return new CartResponse(
                cart.getCartId(),
                cart.getUser().getUserId(),
                cart.getCreatedAt(),
                cart.getCartStatus(),
                list
        );
    }
}
