package com.example.ecommerce.service.cart;

import com.example.ecommerce.models.CartItem;

public interface CartItemService { // Service for CartItem entity
    void addItemToCart(Long cartId, Long productId, int quantity); // Method to add an item to the cart
    void removeItemFromCart(Long cartId, Long productId); // Method to remove an item from the cart
    void updateItemQuantity(Long cartId, Long productId, int quantity); // Method to update item quantity

    CartItem getCartItem(Long cartId, Long productId); // Method to get a cart item by id
}
