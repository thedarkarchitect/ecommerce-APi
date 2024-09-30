package com.example.ecommerce.service.auth;

import com.example.ecommerce.dto.SignupRequest;
import com.example.ecommerce.dto.UserDTO;
import com.example.ecommerce.enums.Roles;
import com.example.ecommerce.models.User;
import com.example.ecommerce.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    //create default admin account
    @PostConstruct
    public void createAdminAccount() {
        Optional<User> optionalAdmin = userRepository.findByUserRole(Roles.ADMIN);

        if(optionalAdmin.isEmpty()) {
            User admin = new User();
            admin.setFirstName("Reynold");
            admin.setLastName("Diaz");
            admin.setEmail("admin@test.com");
            admin.setRole(Roles.ADMIN);
            admin.setPassword("admin");
            userRepository.save(admin);
            System.out.println("Admin account created successfully");
        } else {
            System.out.println("Admin account already exists!");
        }
    }

    @Override
    public Boolean hasUserWithEmail(String email) {
        return userRepository.findFirstByEmail(email).isPresent();
    }

    @Override
    public UserDTO signup(SignupRequest signupRequest) {
        User user = new User();
        user.setFirstName(signupRequest.getFirstName());
        user.setLastName(signupRequest.getLastName());
        user.setEmail(signupRequest.getEmail());
        user.setPassword(signupRequest.getPassword());
        return convertToUserDTO(userRepository.save(user));
    }

    @Override
    public UserDTO convertToUserDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }
}
