package com.example.ecommerce.repository;

import com.example.ecommerce.models.CartItem;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> { // Repository for CartItem entity
    void deleteAllByCartId(Long cartId);    // Delete all cart items by cart id
}
