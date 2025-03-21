package com.nahuannghia.shopnhn.controller;

import com.nahuannghia.shopnhn.model.Cart;
import com.nahuannghia.shopnhn.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;
    @PostMapping
    public Cart addCart(@RequestBody Cart cart){
        return cartService.addCart(cart);
    }
    @GetMapping
    public List<Cart> getAllCarts(){
        return cartService.getAllCarts();
    }
    @GetMapping("/{cartId}")
    public Cart getCart(@PathVariable Long cartId){
        return cartService.getCart(cartId);
    }
    @PutMapping("/{cartId}")
    public Cart updateCart(@PathVariable Long cartId, @RequestBody Cart cart){
        return cartService.updateCart(cartId, cart);
    }
    @DeleteMapping("/{cartId}")
    public void deleteCart(@PathVariable Long cartId){
        cartService.deleteCart(cartId);
    }
}
