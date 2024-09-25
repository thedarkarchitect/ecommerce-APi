package com.example.ecommerce.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

@Data
public class CartDTO {
    private Long cartId;
    private Set<CartItemsDTO> items;
    private BigDecimal totalAmount;
}
