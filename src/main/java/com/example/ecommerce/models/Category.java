package com.example.ecommerce.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data // Lombok annotation to generate getters and setters
@AllArgsConstructor // Lombok annotation to generate all-args constructor
@NoArgsConstructor // Lombok annotation to generate no-args constructor
@Entity // JPA annotation to make this class ready for storage in a JPA-based data store
public class Category {
    @Id // JPA annotation to mark this field as the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // JPA annotation to auto-generate the primary key
    private Long id;
    private String name;
    @JsonIgnore // Jackson annotation to ignore this field when returning JSON response because it may cause an infinite loop when serializing
    @OneToMany(mappedBy = "category") // JPA annotation to specify the one side of a one-to-many relationship
    private List<Product> products;

    public Category(String name) { // Constructor with name parameter to create a new Category object when only the name is known
        // Assign the name parameter to the name field
        this.name = name;
    }
}
