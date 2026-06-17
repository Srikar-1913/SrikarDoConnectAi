package com.ai.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ai.entity.AiResponse;
import com.ai.repo.AiResponseRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AiService {

    private final AiResponseRepository repository;

    public String generateAnswer(String title, String description) {

        String input = (title + " " + description).toLowerCase();

        List<AiResponse> allResponses = repository.findAll();

        for (AiResponse ai : allResponses) {

            if (input.contains(ai.getKeyword().toLowerCase())) {

                return formatResponse(ai.getAnswer()); 
            }
        }

        return formatResponse(
                "I couldn't find an exact answer, please check your logic or error message."
        );
    }

    private String formatResponse(String answer) {
        return "🤖 AI Suggestion:\n\n"
                + answer
                + "\n\nLet me know if you need more help!";
    }
}