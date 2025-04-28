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

import com.nahuannghia.shopnhn.Response.CartItemResponse;
import com.nahuannghia.shopnhn.request.CartItemRequest;
import com.nahuannghia.shopnhn.service.CartItemService;

@RestController
@RequestMapping("/api/cart-items")
@CrossOrigin(origins = {"http://localhost:5173"})
public class CartItemController {

    @Autowired
    private CartItemService cartItemService;

    @PostMapping("/add")
    public CartItemResponse addToCart(@RequestBody CartItemRequest request) {
        return cartItemService.addOrUpdateCartItem(request);
    }

    @GetMapping("/{cartId}")
    public List<CartItemResponse> getCartItems(@PathVariable Integer cartId) {
        return cartItemService.getItemsByCartId(cartId);
    }

    @PutMapping("/update-quantity/{cartItemId}")
    public CartItemResponse updateQuantity(@PathVariable Integer cartItemId, @RequestParam int quantity) {
        return cartItemService.updateQuantity(cartItemId, quantity);
    }

    @DeleteMapping("/delete/{cartItemId}")
    public void deleteCartItem(@PathVariable Integer cartItemId) {
        cartItemService.deleteCartItem(cartItemId);
    }

    @DeleteMapping("/clear/{cartId}")
    public void clearCart(@PathVariable Integer cartId) {
        cartItemService.clearCart(cartId);
    }
}
