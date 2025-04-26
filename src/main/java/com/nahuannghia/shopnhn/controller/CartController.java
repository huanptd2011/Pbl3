package com.nahuannghia.shopnhn.controller;

import com.nahuannghia.shopnhn.Response.CartResponse;
import com.nahuannghia.shopnhn.model.Cart;
import com.nahuannghia.shopnhn.request.CartRequest;
import com.nahuannghia.shopnhn.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/carts")
@CrossOrigin
public class CartController {

    @Autowired
    private CartService cartService;

    // POST /carts/create?customerId=1
    @PostMapping("/create")
    public CartResponse createCart(@RequestBody CartRequest cartRequest) {
        return cartService.createCart(cartRequest);
    }

    // GET /carts/{cartId}
    @GetMapping("/{cartId}")
    public CartResponse getCartById(@PathVariable Integer cartId) {
        return cartService.getCartById(cartId);
    }

    // GET /carts/customer/{customerId}
    @GetMapping("/customer/{customerId}")
    public List<CartResponse> getCartsByCustomer(@PathVariable Integer customerId) {
        return cartService.getCartsByCustomerId(customerId);
    }

    // PUT /carts/{cartId}/status?status=Đã thanh toán
    @PutMapping("/{cartId}/status")
    public CartResponse updateCartStatus(@PathVariable Integer cartId, @RequestParam String status) {
        return cartService.updateCartStatus(cartId, status);
    }

    // DELETE /carts/{cartId}
    @DeleteMapping("/{cartId}")
    public void deleteCart(@PathVariable Integer cartId) {
        cartService.deleteCart(cartId);
    }
}
