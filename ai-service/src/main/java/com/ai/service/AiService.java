/*
 * Author: Srikar Akula
 * Project: DoConnect
 * Description: Service class to generate AI answers
 * Created Date: 17-06-2026
 */

package com.ai.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ai.entity.AiResponse;
import com.ai.repo.AiResponseRepository;

import lombok.RequiredArgsConstructor;

// Service class to handle AI logic
@Service
@RequiredArgsConstructor
public class AiService {

    // Repository to access AI data
    private final AiResponseRepository repository;

    // Generate answer based on title and description
    public String generateAnswer(String title, String description) {

        // Combine input and convert to lowercase
        String input = (title + " " + description).toLowerCase();

        // Get all stored responses
        List<AiResponse> allResponses = repository.findAll();

        // Check if any keyword matches
        for (AiResponse ai : allResponses) {

            if (input.contains(ai.getKeyword().toLowerCase())) {

                // Return formatted answer
                return formatResponse(ai.getAnswer()); 
            }
        }

        // Default response if no match found
        return formatResponse(
                "I couldn't find an exact answer, please check your logic or error message."
        );
    }

    // Format the response with prefix and message
    private String formatResponse(String answer) {
        return "🤖 AI Suggestion:\n\n"
                + answer
                + "\n\nLet me know if you need more help!";
    }
}