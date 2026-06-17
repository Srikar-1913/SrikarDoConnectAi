/*
 * Author: Srikar Akula
 * Project: DoConnect
 * Description: Controller to handle AI requests
 * Created Date: 17-06-2026
 */

package com.wipro.doconnect.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.doconnect.service.AIClientService;

// REST controller for AI-related APIs
@RestController
@RequestMapping("/ai")
@CrossOrigin
public class AiController {

    // Service to call AI microservice
    @Autowired
    private AIClientService aiClientService;

    // Generate AI answer using title and description
    @PostMapping("/generate")
    public String generateAnswer(@RequestBody Map<String, String> request) {

        // Call service and return AI response
        return aiClientService.getAIResponse(
                request.get("title"),
                request.get("description")
        );
    }


    // Old Gemini service code (commented)
    /*
     * @Autowired private GeminiService geminiService;
     * 
     * @PostMapping("/answer") public String getAnswer(@RequestBody QuestionDto dto)
     * throws Exception{
     * 
     * return geminiService.generateAnswer(dto); }
     */
}