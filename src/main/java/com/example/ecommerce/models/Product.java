package com.example.ecommerce.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data // Lombok annotation to generate getters and setters
@AllArgsConstructor // Lombok annotation to generate all-args constructor
@NoArgsConstructor // Lombok annotation to generate no-args constructor
@Entity // JPA annotation to make this class ready for storage in a JPA-based data store
public class Product {
    @Id // JPA annotation to mark this field as the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // JPA annotation to auto-generate the primary key
    private Long id;
    private String name;
    private String brand;
    private String description;
    private BigDecimal price;
    private int inventory;

    @ManyToOne(cascade = CascadeType.ALL) // JPA annotation to specify the many side of a many-to-one relationship
    @JoinColumn(name = "category_id") // JPA annotation to specify the column name for the foreign key
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true) // JPA annotation to specify the one side of a one-to-many relationship and cascade operations with orphan removal true meaning that if an Image is removed from the images list, it will be deleted from the database
    private List<Image> images;

    public Product(String name, String brand, BigDecimal price, String description, int inventory, Category category) { // Constructor with parameters
        //even though RequiredArgsConstructor is used, we still need to define a constructor with parameters to create a new product in the ProductService class implementation
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.description = description;
        this.inventory = inventory;
        this.category = category;
    }
}
