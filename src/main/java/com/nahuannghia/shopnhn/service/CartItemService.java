package com.nahuannghia.shopnhn.service;

import com.nahuannghia.shopnhn.Response.CartItemResponse;
import com.nahuannghia.shopnhn.model.Cart;
import com.nahuannghia.shopnhn.model.CartItem;
import com.nahuannghia.shopnhn.model.Product;
import com.nahuannghia.shopnhn.model.ProductImage;
import com.nahuannghia.shopnhn.repository.CartItemRepository;
import com.nahuannghia.shopnhn.repository.CartRepository;
import com.nahuannghia.shopnhn.repository.ProductRepository;
import com.nahuannghia.shopnhn.request.CartItemRequest;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartItemService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;

    public CartItemService(CartRepository cartRepository, ProductRepository productRepository, CartItemRepository cartItemRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.cartItemRepository = cartItemRepository;
    }

    // Thêm hoặc cập nhật sản phẩm
    @Transactional
    public CartItemResponse addOrUpdateCartItem(CartItemRequest request) {
        Cart cart = cartRepository.findById(request.getCartId())
                .orElseThrow(() -> new RuntimeException("Cart not found"));
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Optional<CartItem> existingItem = cartItemRepository.findByCartAndProductAndColorAndSize(
                cart, product, request.getColor(), request.getSize());

        CartItem savedItem;
        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + request.getQuantity());
            savedItem = cartItemRepository.save(item);
        } else {
            CartItem newItem = new CartItem(
                    null, cart, product, request.getQuantity(), request.getPrice(), request.getColor(), request.getSize()
            );
            savedItem = cartItemRepository.save(newItem);
        }

        return mapToResponse(savedItem);
    }

    // Lấy danh sách sản phẩm trong giỏ
    public List<CartItemResponse> getItemsByCartId(Integer cartId) {
        List<CartItem> items = cartItemRepository.findByCartCartId(cartId);
        return items.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Xoá 1 item
    public void deleteCartItem(Integer cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }

    // Cập nhật số lượng
    public CartItemResponse updateQuantity(Integer cartItemId, int quantity) {
        CartItem item = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("CartItem not found"));
        item.setQuantity(quantity);
        return mapToResponse(cartItemRepository.save(item));
    }

    // Xoá toàn bộ cart
    public void clearCart(Integer cartId) {
        cartItemRepository.deleteByCartCartId(cartId);
    }


    // chuyen tu entity -> response
    private CartItemResponse mapToResponse(CartItem item) {
        String productName = item.getProduct().getProductName();
        //lay 1 anh dau
        String imageUrl = item.getProduct().getImages().stream()
                .findFirst()
                .map(ProductImage::getImageUrl)
                .orElse("");
        return new CartItemResponse(
                item.getCartItemId(),
                item.getProduct().getProductId(),
                productName,
                imageUrl,
                item.getQuantity(),
                item.getPrice(),
                item.getColor(),
                item.getSize()
        );
    }
}
