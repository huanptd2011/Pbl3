package com.nahuannghia.shopnhn.controller;

import com.nahuannghia.shopnhn.model.CartItem;
import com.nahuannghia.shopnhn.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart-items")
public class CartItemController {
    @Autowired
    private CartItemService cartItemService;
    @PostMapping
    public CartItem addCartItem(@RequestBody CartItem cartItem){
        return cartItemService.addCartItem(cartItem);
    }
    @GetMapping
    public List<CartItem> getAllCartItem(){
        return cartItemService.getAllCartItem();
    }
    @GetMapping("/{cartItemId}")
    public CartItem getCardItem(@PathVariable Long cartItemId){
        return cartItemService.getCartItem(cartItemId);
    }
    @PutMapping("/{cartItemId}")
    public CartItem updateCartItem(@PathVariable Long cartItemId, CartItem cartItem){
        return cartItemService.updateCartItem(cartItemId, cartItem);
    }
    @DeleteMapping("/{cartItemId}")
    public void deleteCartItem(@PathVariable Long cartItemId){
        cartItemService.deleteCartItem(cartItemId);
    }
}

