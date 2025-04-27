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

import com.nahuannghia.shopnhn.Response.CartResponse;
import com.nahuannghia.shopnhn.request.CartRequest;
import com.nahuannghia.shopnhn.service.CartService;

@RestController
@RequestMapping("/api/carts")
@CrossOrigin(origins = {"http://localhost:63342", "http://127.0.0.1:5501", "http://127.0.0.1:5500","http://localhost:5173/"})
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
