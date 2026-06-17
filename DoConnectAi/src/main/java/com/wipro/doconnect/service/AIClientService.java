/*
 * Author: Srikar Akula
 * Project: DoConnect
 * Description: Service to call AI API
 * Created Date: 17-06-2026
 */

package com.wipro.doconnect.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

// Service to communicate with AI microservice
@Service
public class AIClientService {

    // Used to make HTTP API calls
    private final RestTemplate restTemplate = new RestTemplate();

    // Send title and description to AI and get response
    public String getAIResponse(String title, String description) {

        // AI service URL
        String url = "http://localhost:8081/ai/generate";

        // Request body data
        Map<String, String> request = new HashMap<>();
        request.put("title", title);
        request.put("description", description);

        // Call AI service using POST request
        ResponseEntity<String> response =
                restTemplate.postForEntity(url, request, String.class);

        // Return AI response
        return response.getBody();
    }
}
