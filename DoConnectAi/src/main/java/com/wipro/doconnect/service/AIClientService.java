package com.wipro.doconnect.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AIClientService {

    private final RestTemplate restTemplate = new RestTemplate();

    public String getAIResponse(String title, String description) {

        String url = "http://localhost:8081/ai/generate";

        Map<String, String> request = new HashMap<>();
        request.put("title", title);
        request.put("description", description);

        ResponseEntity<String> response =
                restTemplate.postForEntity(url, request, String.class);

        return response.getBody();
    }
}
