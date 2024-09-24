package com.example.ecommerce.repository;

import com.example.ecommerce.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // This annotation is used to indicate that the class provides the mechanism for storage, retrieval, search, update and delete operation on objects.
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByName(String name); // This method is used to find a category by its name.

    boolean existsByName(String name); // This method is used to check if a category with the given name exists in the database.
}
