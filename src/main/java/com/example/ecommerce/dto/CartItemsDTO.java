package com.example.ecommerce.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartItemsDTO {
    private Long itemId;
    private Integer quantity;
    private BigDecimal unitPrice;
    private ProductDTO product;
}
