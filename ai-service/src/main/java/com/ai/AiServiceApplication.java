/*
 * Author: Srikar Akula
 * Project: DoConnect AI Service
 * Description: Main class to start AI service application
 * Created Date: 17-06-2026
 */

package com.ai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Main class to run the AI service
@SpringBootApplication
public class AiServiceApplication {

    // Entry point of the application
    public static void main(String[] args) {

        // Starts Spring Boot application
        SpringApplication.run(AiServiceApplication.class, args);
    }
}