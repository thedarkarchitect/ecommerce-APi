package com.example.ecommerce.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Data // Lombok annotation to generate getters and setters
@Entity // JPA annotation to make this class ready for storage in a JPA-based data store
@AllArgsConstructor // Lombok annotation to generate all-args constructor
@NoArgsConstructor// Lombok annotation to generate no-args constructor
public class Cart {
    @Id // JPA annotation to mark this field as the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // JPA annotation to auto-generate the primary key
    private Long id; // Unique identifier for the cart
    private BigDecimal totalAmount = BigDecimal.ZERO; // Total amount of the cart

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true) // JPA annotation to specify the one side of a one-to-many relationship
    private Set<CartItem> items = new HashSet<>(); // Set of cart items in the cart

    @OneToOne // JPA annotation to specify the one side of a one-to-one relationship
    @JoinColumn(name = "user_id") // JPA annotation to specify the column name for the foreign key
    private User user; // User who owns the cart


    public void addItem(CartItem item) { // Method to add a cart item to the cart
        this.items.add(item); // Add the cart item to the items set
        item.setCart(this); // Set the cart of the cart item to this cart
        updateTotalAmount(); // Update the total amount of the cart
    }

    public void removeItem(CartItem item) { // Method to remove a cart item from the cart
        this.items.remove(item);    // Remove the cart item from the items set
        item.setCart(null); // Set the cart of the cart item to null
        updateTotalAmount(); // Update the total amount of the cart
    }

    public void updateTotalAmount() { // Method to update the total amount of the cart
        this.totalAmount = items.stream().map(item -> { // Calculate the total amount by summing the total price of each cart item
                    BigDecimal unitPrice = item.getUnitPrice(); // Get the unit price of the cart item
                    if(unitPrice == null){ // Check if the unit price is null
                        return BigDecimal.ZERO; // Return zero if the unit price is null
                    }
                    return unitPrice.multiply(new BigDecimal(item.getQuantity())); // Calculate the total price of the cart item
                }).reduce(BigDecimal.ZERO, BigDecimal::add); // Sum the total prices of all cart items
    }
}