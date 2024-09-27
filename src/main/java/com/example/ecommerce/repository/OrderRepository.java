package com.example.ecommerce.repository;

import com.example.ecommerce.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> { // Repository for Order entity
    List<Order> findByUserId(Long userId); // Find all orders by user id
}
