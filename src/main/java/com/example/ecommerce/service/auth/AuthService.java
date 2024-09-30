package com.example.ecommerce.service.auth;

import com.example.ecommerce.dto.SignupRequest;
import com.example.ecommerce.dto.UserDTO;
import com.example.ecommerce.models.User;

public interface AuthService {
    Boolean hasUserWithEmail(String email);

    UserDTO signup(SignupRequest signupRequest);
    UserDTO convertToUserDTO(User user);
}
