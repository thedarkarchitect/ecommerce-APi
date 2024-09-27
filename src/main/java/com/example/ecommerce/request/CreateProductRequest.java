package com.example.ecommerce.request;

import com.example.ecommerce.models.Category;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateProductRequest { // Request object to create a new product
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;
    private Category category;
}
