package com.example.ecommerce.service.user;

import com.example.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository; //dependency injection

    @Override
    public UserDetailsService userDetailsService() { //method to get user details for Spring Security to use for authentication
        return email -> (UserDetails) userRepository.findFirstByEmail(email) //return user details by using email to get user and casting it to UserDetails to make it compatible with Spring Security
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));//throw exception if user not found
    }
}
