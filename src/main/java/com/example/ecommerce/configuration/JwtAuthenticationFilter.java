package com.example.ecommerce.configuration;

import com.example.ecommerce.service.user.UserService;
import com.example.ecommerce.utils.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component // This is a Spring stereotype annotation. It serves as a specialization of @Component, allowing for implementation classes to be autodetected through classpath scanning.
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil; //contain JWT functions to generate and extract token

    private final UserService userService; //contain userDetailsService function to load user by username

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request, // HTTP request used to extract header
            @NonNull HttpServletResponse response, // HTTP response used to extract header
            @NonNull FilterChain filterChain // FilterChain used to continue to the next filter
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization"); //get Authorization header from request
        final String token; //JWT token
        final String email; //user email

        if (StringUtils.isEmpty(authHeader) || !StringUtils.startsWith(authHeader, "Bearer ")) { //if Authorization header is empty or does not start with "Bearer "
            filterChain.doFilter(request, response); //continue to the next filter
            return;
        }

        token = authHeader.substring(7); //get JWT token from Authorization header
        email = jwtUtil.extractUsername(token); //extract user email from JWT token

        if (StringUtils.isNoneEmpty(email) && SecurityContextHolder.getContext().getAuthentication() == null) { //if user email is not empty and there is no authentication in SecurityContextHolder
            //security context is used to store the details of the currently authenticated user
            UserDetails userDetails = userService.userDetailsService().loadUserByUsername(email); //get user details by user email
            if(jwtUtil.isTokenExpired(token)) { //if JWT token is valid
                SecurityContext context = SecurityContextHolder.createEmptyContext(); //create empty security context to hold authentication details
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()); //create authentication token
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); //set authentication token details from request header
                context.setAuthentication(usernamePasswordAuthenticationToken); //set authentication in security context to authenticate user
                SecurityContextHolder.setContext(context); //set security context in SecurityContextHolder
            }
        }

        filterChain.doFilter(request, response); //continue to the next filter inorder to process the request

    }
}
