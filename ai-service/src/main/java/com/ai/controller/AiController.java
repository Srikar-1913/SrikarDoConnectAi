/*
 * Author: Srikar Akula
 * Project: DoConnect
 * Description: Controller to handle AI requests
 * Created Date: 17-06-2026
 */

package com.ai.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ai.dto.AiRequestDto;
import com.ai.service.AiService;

import lombok.RequiredArgsConstructor;

// REST controller for AI operations
@RestController
@RequestMapping("/ai")
@RequiredArgsConstructor
@CrossOrigin
public class AiController {

    // Service to process AI logic
    private final AiService aiService;

    // Generate answer based on request data
    @PostMapping("/generate")
    public String generateAnswer(@RequestBody AiRequestDto request) {

        // Call service with title and description
        return aiService.generateAnswer(
                request.getTitle(),
                request.getDescription()
        );
    }
}