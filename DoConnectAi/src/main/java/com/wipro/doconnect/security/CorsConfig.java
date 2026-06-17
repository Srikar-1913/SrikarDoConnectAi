/*
 * Author: Srikar Akula
 * Project: DoConnect
 * Description: Configuration class for CORS settings
 * Created Date: 17-06-2026
 */

package com.wipro.doconnect.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

// Configuration class for handling CORS
@Configuration
public class CorsConfig {

    // Bean to enable CORS
    @Bean
    public CorsFilter corsFilter() {

        // Create CORS configuration
        CorsConfiguration config = new CorsConfiguration();

        // Allow requests from frontend (React)
        config.addAllowedOrigin("http://localhost:3000");

        // Allow all headers
        config.addAllowedHeader("*");

        // Allow all HTTP methods (GET, POST, PUT, DELETE)
        config.addAllowedMethod("*");

        // Allow credentials (cookies, auth headers)
        config.setAllowCredentials(true);

        // Apply configuration to all endpoints
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        // Return CORS filter
        return new CorsFilter(source);
    }
}
