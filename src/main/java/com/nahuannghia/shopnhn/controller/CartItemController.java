package com.nahuannghia.shopnhn.controller;

import com.nahuannghia.shopnhn.Response.CartItemResponse;
import com.nahuannghia.shopnhn.request.CartItemRequest;
import com.nahuannghia.shopnhn.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart-items")
@CrossOrigin(origins = {"http://localhost:63342", "http://127.0.0.1:5501", "http://127.0.0.1:5500"})
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
