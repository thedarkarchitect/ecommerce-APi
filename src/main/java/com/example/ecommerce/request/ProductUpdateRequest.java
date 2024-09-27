package com.example.ecommerce.request;

import com.example.ecommerce.models.Category;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductUpdateRequest { // Request object to update a product
//    private Long id; // might not be needed
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;
    private Category category;
}
