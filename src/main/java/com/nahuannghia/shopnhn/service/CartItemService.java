package com.nahuannghia.shopnhn.service;

import com.nahuannghia.shopnhn.model.CartItem;
import com.nahuannghia.shopnhn.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartItemService {
    @Autowired
    private CartItemRepository cartItemRepository;
    public CartItem addCartItem(CartItem cartItem){
        try {
            return cartItemRepository.save(cartItem);
        } catch (Exception e){
            throw new RuntimeException("Error adding");
        }
    }
    public List<CartItem> getAllCartItem(){
        List<CartItem> list = cartItemRepository.findAll();
        if(list.isEmpty())
            throw new RuntimeException("No cart item");
        return list;
    }
    public CartItem getCartItem(Long cartItemId){
        return cartItemRepository.findById(cartItemId).orElseThrow(()->
                new RuntimeException("Not found"));
    }
    public CartItem updateCartItem(Long cartItemId, CartItem cartItem){
        CartItem existing = getCartItem(cartItemId);
        existing.setCart(cartItem.getCart());
        existing.setQuantity(cartItem.getQuantity());
        existing.setPrice(cartItem.getPrice());
        try{
            return cartItemRepository.save(existing);
        }catch (Exception e){
            throw new RuntimeException("Error updating");
        }
    }
    public void deleteCartItem(Long cartItemId){
        CartItem existing = getCartItem(cartItemId);
        try{
            cartItemRepository.delete(existing);
        } catch (Exception e){
            throw new RuntimeException("Error deleting");
        }
    }
}
