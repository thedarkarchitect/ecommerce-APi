package com.example.ecommerce.configuration;

import com.example.ecommerce.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration // Annotation to declare that it is a Configuration class used to define configurations for the application
@EnableWebSecurity // Annotation to enable Spring Security
@EnableMethodSecurity // Annotation to enable method-level security
@RequiredArgsConstructor
public class WebSecurityConfiguration {

    private final UserService userService; //dependency injection

    private final JwtAuthenticationFilter jwtAuthenticationFilter; //dependency injection

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable) //this line disables the crsf protection cause the application is stateless because it is using JWT
                .authorizeHttpRequests(request -> request.requestMatchers("/api/v1/auth/**").permitAll() //this allows access to all endpoints that start with /api/auth typically used for login and registration or password reset and requires authentication for all other requests
                        .anyRequest().authenticated()) //this requires authentication for all other requests
                .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //this configures how sessions are managed and typically RESTFUL services where each request should be stateless, often in conjunction with JWT
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build(); //this builds the securityfilterchain object
    }

    private AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(); //this retrieves data from the db
        provider.setUserDetailsService(userService.userDetailsService()); //using the userDetailsService service interacts with the db to retrieve user details and provider loads user-specific data
        provider.setPasswordEncoder(passwordEncoder()); //the DaoAuthenticationProvider uses the passwordEncoder method encode and validate passwords by creating an instance of PasswordEncorder
        return provider; //this configures and returns a DaoAuthenticationProvider Bean
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    //AuthenticationManager is a crucial part of Spring Security, it responsible for processing authentication requests
    //By using this Bean to expose the Spring Context, making it available for DI in other parts of the application
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
