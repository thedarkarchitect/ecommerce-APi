package com.example.ecommerce.utils;

import com.example.ecommerce.enums.Roles;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JWTUtil {

    private static final String SECRET = System.getenv("SECRET"); // Keep the secret in a constant

    private static Key getSigningKey() {
        if (SECRET == null || SECRET.isEmpty()) { // Check if the secret is null or empty
            throw new IllegalArgumentException("No secret key.");
        }
        byte[] secretBytes = Decoders.BASE64.decode(SECRET); // Decode the secret key
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET)); // Return the secret key
    }

    private Claims extractAllClaims(String token) { // Extract all claims from the token
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey()) // Use the signing key
                .build() // Build the parser
                .parseClaimsJws(token) // Parse the token
                .getBody(); // Get the body of the token in the form of claims. claims are key-value pairs
    }

    private <T> T extractClaims(String token, Function<Claims, T> claimsResolver) { // Extract claims from the token
        final Claims claims = extractAllClaims(token); // Extract all claims from the token
        return claimsResolver.apply(claims); // Return the claims as a function of the claims resolver
    }

    public String extractUsername(String token) { // Extract the username from the token
        return extractClaims(token, Claims::getSubject); // Return the subject of the claims
    }

    private Date extractExpiration(String token) { // Extract the expiration date from the token
        return extractClaims(token, Claims::getExpiration); // Return the expiration date of the claims
    }

    public Boolean isTokenExpired(String token) { // Check if the token is expired
        return extractExpiration(token).before(new Date()); // Return true if the expiration date is before the current date
    }

    private Boolean isTokenValid(String token, UserDetails userDetails) { // Check if the token is valid
        final String email = extractUsername(token); // Extract the username from the token
        return (email.equals(userDetails.getUsername()) && !isTokenExpired(token)); // Return true if the username is the same as the user's username and the token is not expired
    }

    public String generateToken(Map<String, Object> claims, UserDetails userDetails, long expirationTimeMillis, Long userId, Roles userRole) { // Generate a token for the user
       Map<String, Object> payload = new HashMap<>(); // Create a new map of claims
        payload.put("user_id", userId); // Add the user's id to the claims
        payload.put("role", userRole); // Add the user's role to the claims

        return Jwts.builder() // Create a new token builder
               .setClaims(payload) // Set the claims of the token
               .setSubject(userDetails.getUsername()) // Set the subject of the token to the user's username
               .setIssuedAt(new Date(System.currentTimeMillis())) // Set the issued date of the token
               .setExpiration(new Date(System.currentTimeMillis() + expirationTimeMillis)) // Set the expiration date of the token
               .signWith(getSigningKey()) // Sign the token with the secret key
               .compact(); // Compact the token
    }

    public String extractRole(String token) { // Extract the role from the token
        return extractClaims(token, claims -> claims.get("role", String.class)); // Return the role from the claims
    }

    public Long extractUserId(String token) { // Extract the user id from the token
        return extractClaims(token, claims -> claims.get("user_id", Long.class)); // Return the user id from the claims
    }

    public String generateAccessToken(UserDetails userDetails, Long userId, Roles role) { // Generate an access token for the user
        // Access token valid for 5 minutes
        return generateToken(new HashMap<>(), userDetails, 1000 * 60 * 5, userId, role); // Generate a token with the user's details
    }

    public String generateRefreshToken(UserDetails userDetails, Long userId, Roles role) { // Generate a refresh token for the user
        // Refresh token valid for 3 days
        return generateToken(new HashMap<>(), userDetails, 1000 * 60 * 60 * 24 * 3, userId, role); // Generate a token with the user's details
    }

}

