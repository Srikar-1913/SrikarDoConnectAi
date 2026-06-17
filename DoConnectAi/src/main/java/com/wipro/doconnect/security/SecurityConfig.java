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

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())
            .cors(cors -> {})

            .authorizeHttpRequests(auth -> auth

                // ✅ Allow frontend pre-flight calls
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                // ✅ Public APIs
                .requestMatchers("/users/register", "/users/login").permitAll()

                // ✅ Admin only
                .requestMatchers("/users/all").hasAuthority("ROLE_ADMIN")

                // ✅ IMPORTANT: allow answers API for authenticated users
                .requestMatchers("/answers/**").authenticated()

                // ✅ Same for questions API
                .requestMatchers("/questions/**").authenticated()

                // ✅ Everything else
                .anyRequest().authenticated()
            )

            // ✅ disable default login page
            .formLogin(form -> form.disable())
            .httpBasic(basic -> basic.disable())

            // ✅ add JWT filter
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}