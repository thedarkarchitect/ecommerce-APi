package com.example.ecommerce.service.cart;


import com.example.ecommerce.models.Cart;

import java.math.BigDecimal;

public interface CartService {
    Cart getCart(Long userId);
    void clearCart(Long cartId);

    BigDecimal getTotalPrice(Long cartId);

    Long initializeNewCart();
    Cart getCartByUserId(Long cartId);
}
