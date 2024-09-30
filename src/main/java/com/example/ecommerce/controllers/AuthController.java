package com.example.ecommerce.controllers;

import com.example.ecommerce.dto.AuthenticationRequest;
import com.example.ecommerce.dto.AuthenticationResponse;
import com.example.ecommerce.dto.SignupRequest;
import com.example.ecommerce.dto.UserDTO;
import com.example.ecommerce.enums.Roles;
import com.example.ecommerce.repository.UserRepository;
import com.example.ecommerce.service.auth.AuthService;
import com.example.ecommerce.service.user.UserService;
import com.example.ecommerce.service.user.UserServiceImpl;
import com.example.ecommerce.utils.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("${api.prefix}/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserRepository userRepository;
    private final JWTUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest signupRequest) {
        if(authService.hasUserWithEmail(signupRequest.getEmail())) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("User already exists");
        }

        UserDTO userDto = authService.signup(signupRequest);
        if(userDto == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest authenticationRequest) {
            Optional<UserDTO> user = userRepository.findFirstByEmail(authenticationRequest.getEmail())
            if(user.isPresent()) {
                Long userId = user.get().getId();
                Roles roles = Roles.valueOf(user.get().getRole());
                UserDetails details = userService.userDetailsService().loadUserByUsername(user.get().getEmail());

                String refreshToken = jwtUtil.generateRefreshToken(details, userId, roles);
                String accessToken = jwtUtil.generateAccessToken(details, userId, roles);

                if(refreshToken != null && accessToken != null) {
                    return ResponseEntity.status(HttpStatus.OK).body(new AuthenticationResponse());
                } else {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
                }
            }

    }

}



