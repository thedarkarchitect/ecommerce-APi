package com.example.ecommerce.repository;

import com.example.ecommerce.models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // Spring annotation to specify that this class is a repository
public interface CartRepository extends JpaRepository<Cart, Long> { // Repository for Cart entity
    Cart findByUserId(Long userId); // Find a cart by user id
}
