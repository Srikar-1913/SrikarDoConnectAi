package com.wipro.doconnect.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.doconnect.service.AIClientService;

@RestController
@RequestMapping("/ai")
@CrossOrigin
public class AiController {

	@Autowired
	private AIClientService aiClientService;

	@PostMapping("/generate")
	public String generateAnswer(@RequestBody Map<String, String> request) {

		return aiClientService.getAIResponse(request.get("title"), request.get("description"));
	}

	
	
	
	
	
	
	
	/*
	 * @Autowired private GeminiService geminiService;
	 * 
	 * @PostMapping("/answer") public String getAnswer(@RequestBody QuestionDto dto)
	 * throws Exception{
	 * 
	 * return geminiService.generateAnswer(dto); }
	 */

}