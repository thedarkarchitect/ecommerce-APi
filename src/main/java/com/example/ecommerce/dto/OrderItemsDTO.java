package com.example.ecommerce.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemsDTO {
    private Long productId;
    private String productName;
    private int quantity;
    private BigDecimal price;
}
