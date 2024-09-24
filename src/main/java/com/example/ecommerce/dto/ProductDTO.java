package com.example.ecommerce.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProductDTO {
    private Long id;
    private String name;
    private String brand;
    private String description;
    private String price;
    private int inventory;
    private Long categoryId;
    private List<ImageDTO> imageUrls;
}

