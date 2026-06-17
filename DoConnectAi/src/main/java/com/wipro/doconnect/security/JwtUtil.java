/*
 * Author: Srikar Akula
 * Project: DoConnect
 * Description: Utility class for JWT operations
 * Created Date: 17-06-2026
 */

package com.wipro.doconnect.security;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;

// Utility class for generating and validating JWT tokens
@Component
public class JwtUtil {

    // Secret key for signing token
    private final String SECRET = "mysecretkeymysecretkeymysecretkey";

    // Convert secret string to secure key
    private final Key key = io.jsonwebtoken.security.Keys.hmacShaKeyFor(SECRET.getBytes());

    // Generate JWT token using email and role
    public String generateToken(String email, String role) {
        return Jwts.builder()
                .setSubject(email)              // set user email
                .claim("role", role)            // add role as claim
                .setIssuedAt(new Date())        // token creation time
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hour expiry
                .signWith(key)                  // sign with secret key
                .compact();
    }

    // Extract email from token
    public String extractEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Extract role from token
    public String extractRole(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("role", String.class);
    }
}
