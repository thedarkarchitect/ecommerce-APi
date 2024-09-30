package com.example.ecommerce.repository;

import com.example.ecommerce.dto.UserDTO;
import com.example.ecommerce.enums.Roles;
import com.example.ecommerce.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<UserDTO> findFirstByEmail(String email);

    Optional<User> findByUserRole(Roles userRole);
}
