package com.ai.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ai.dto.AiRequestDto;
import com.ai.service.AiService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/ai")
@RequiredArgsConstructor
@CrossOrigin
public class AiController {

    private final AiService aiService;

    @PostMapping("/generate")
    public String generateAnswer(@RequestBody AiRequestDto request) {

        return aiService.generateAnswer(
                request.getTitle(),
                request.getDescription()
        );
    }
}