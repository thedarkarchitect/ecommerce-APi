package com.example.ecommerce.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Blob;

@Data // Lombok annotation to generate getters and setters
@AllArgsConstructor // Lombok annotation to generate all-args constructor
@NoArgsConstructor // Lombok annotation to generate no-args constructor
@Entity // JPA annotation to make this class ready for storage in a JPA-based data store
public class Image {
    @Id // JPA annotation to mark this field as the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // JPA annotation to auto-generate the primary key
    private Long id;
    private String fileName;
    private String fileType;

    @Lob // JPA annotation to store large binary data
    private Blob image;
    private String downloadUrl;

    @ManyToOne // JPA annotation to specify the many side of a many-to-one relationship
    @JoinColumn(name = "product_id") // JPA annotation to specify the column name for the foreign key
    private Product product;
}
