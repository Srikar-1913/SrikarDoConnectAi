/*
 * Author: Srikar Akula
 * Project: DoConnect
 * Description: JWT filter for authentication
 * Created Date: 17-06-2026
 */

package com.wipro.doconnect.security;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// Filter to validate JWT token for each request
@Component
public class JwtFilter extends OncePerRequestFilter {

    // Utility class for JWT operations
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                   HttpServletResponse response,
                                   FilterChain filterChain)
            throws ServletException, IOException {

        // Get request path
        String path = request.getRequestURI();

        // Skip authentication for login and register
        if (path.equals("/users/login") || path.equals("/users/register")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Get Authorization header
        String authHeader = request.getHeader("Authorization");

        // If no token, continue request
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extract token
        String token = authHeader.substring(7);

        try {
            // Extract email and role from token
            String email = jwtUtil.extractEmail(token);
            String role = jwtUtil.extractRole(token);

            // Set authentication if not already set
            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(
                                email,
                                null,
                                List.of(new SimpleGrantedAuthority("ROLE_" + role))
                        );

                SecurityContextHolder.getContext().setAuthentication(auth);
            }

        } catch (Exception e) {

            // Handle invalid or expired token
            System.out.println("JWT expired or invalid: " + e.getMessage());
        }

        // Continue request processing
        filterChain.doFilter(request, response);
    }
}