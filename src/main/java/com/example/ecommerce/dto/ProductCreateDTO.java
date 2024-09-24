package com.example.ecommerce.dto;

import lombok.Data;

@Data
public class ProductCreateDTO {
    private String name;
    private String brand;
    private String description;
    private String price;
    private int inventory;
    private Long categoryId;
}