package com.nahuannghia.shopnhn.service;

import com.nahuannghia.shopnhn.model.Cart;
import com.nahuannghia.shopnhn.model.Customer;
import com.nahuannghia.shopnhn.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;
    public Cart addCart(Cart cart){
        try {
            return cartRepository.save(cart);
        } catch (Exception e){
            throw new RuntimeException("Error adding");
        }
    }
    public List<Cart> getAllCarts(){
        List<Cart> list = cartRepository.findAll();
        if(list.isEmpty())
            throw new RuntimeException("No cart");
        return list;
    }
    public Cart getCart(Long cartId){
        return cartRepository.findById(cartId).orElseThrow(()->
                new RuntimeException("Not found"));
    }
    public Cart updateCart(Long cartId, Cart cart){
        Cart existing = getCart(cartId);
        existing.setCreatedDate(cart.getCreatedDate());
        existing.setStatus(cart.getStatus());
        try {
            return cartRepository.save(existing);
        } catch (Exception e){
            throw new RuntimeException("Error updating");
        }
    }
    public void deleteCart(Long cartId){
        Cart existing = getCart(cartId);
        try{
            cartRepository.delete(existing);
        } catch (Exception e){
            throw new RuntimeException("Error deleting");
        }
    }
}
