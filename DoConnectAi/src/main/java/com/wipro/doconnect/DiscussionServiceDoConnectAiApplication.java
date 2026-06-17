/*
 * Author: Srikar Akula
 * Project: DoConnect
 * Description: Main class to start application and create RestTemplate bean
 * Created Date: 17-06-2026
 */

package com.wipro.doconnect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

// Main class to start Spring Boot application (Configuration Pattern)
@SpringBootApplication
public class DiscussionServiceDoConnectAiApplication {

    // Entry point of application
    public static void main(String[] args) {

        // Starts the Spring Boot application
        SpringApplication.run(DiscussionServiceDoConnectAiApplication.class, args);
    }
    
    // Bean for making external API calls (Singleton Pattern managed by Spring)
    @Bean
    public RestTemplate restTemplate() {

        // Spring creates and manages this object (Factory Pattern - internal)
        return new RestTemplate();
    }
}
