/*
 * Author: Srikar Akula
 * Project: DoConnect
 * Description: Security configuration for authentication and authorization
 * Created Date: 17-06-2026
 */

package com.wipro.doconnect.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// Configuration class for Spring Security
@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    // JWT filter for token validation
    @Autowired
    private JwtFilter jwtFilter;

    // Configure security rules
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            // Disable CSRF
            .csrf(csrf -> csrf.disable())

            // Enable CORS
            .cors(cors -> {})

            .authorizeHttpRequests(auth -> auth

                // Allow preflight requests
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                // Public APIs (no login required)
                .requestMatchers("/users/register", "/users/login").permitAll()

                // Admin-only API
                .requestMatchers("/users/all").hasAuthority("ROLE_ADMIN")

                // Authenticated users only
                .requestMatchers("/answers/**").authenticated()
                .requestMatchers("/questions/**").authenticated()

                // All other requests require authentication
                .anyRequest().authenticated()
            )

            // Disable default login methods
            .formLogin(form -> form.disable())
            .httpBasic(basic -> basic.disable())

            // Add JWT filter before authentication filter
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // Password encoder bean
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}