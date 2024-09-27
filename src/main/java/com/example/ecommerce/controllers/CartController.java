package com.example.ecommerce.controllers;


import com.example.ecommerce.models.Cart;
import com.example.ecommerce.response.ApiResponse;
import com.example.ecommerce.service.cart.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/carts") // Base URL mapping for the controller
public class CartController { // Controller for Cart entity
    private final CartService cartService; // Service for Cart entity

    @GetMapping("/{cartId}/my-cart") // URL mapping for the method
    public ResponseEntity<ApiResponse> getCart(@PathVariable Long cartId) { // Method to get a cart by id
        try {
            Cart cart = cartService.getCart(cartId); // Get cart by id
            return ResponseEntity.ok(new ApiResponse("Success", cart));
        } catch (RuntimeException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/{cartId}/clear")
    public ResponseEntity<ApiResponse> clearCart( @PathVariable Long cartId) {
        try {
            cartService.clearCart(cartId);
            return ResponseEntity.ok(new ApiResponse("Clear Cart Success!", null));
        } catch (RuntimeException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/{cartId}/cart/total-price")
    public ResponseEntity<ApiResponse> getTotalAmount( @PathVariable Long cartId) {
        try {
            BigDecimal totalPrice = cartService.getTotalPrice(cartId); // Get total price of the cart
            return ResponseEntity.ok(new ApiResponse("Total Price", totalPrice));
        } catch (RuntimeException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
