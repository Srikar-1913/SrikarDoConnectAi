package com.wipro.doconnect.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.wipro.doconnect.dto.QuestionDto;
import com.wipro.doconnect.entity.Question;
import com.wipro.doconnect.service.QuestionService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/questions")
@Slf4j
public class QuestionController {

	@Autowired
	private QuestionService questionService;

	@PostMapping("/save")
	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	public Question saveQuestion(@RequestBody QuestionDto questionDto) {
		log.info("POST / question added successfully");

		return questionService.saveQuestion(questionDto);
	}
 
	@GetMapping("/getAll")
	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	public List<Question> getAllQuestions() {
		log.info("GET / retrived all questions successfully");

		return questionService.getAllQuestions();
	}

	@GetMapping("/get/{questionId}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public Question getQuestionById(@PathVariable Long questionId) {
		log.info("GET / retrived question by id");

		return questionService.getQuestionById(questionId);
	}

	@PutMapping("/update/{questionId}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public Question updateQuestion(@PathVariable Long questionId, @RequestBody QuestionDto questionDto) {
		log.info("PUT / question updated successfully");

		return questionService.updateQuestion(questionId, questionDto);
	}

	@DeleteMapping("/delete/{questionId}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public String deleteQuestion(@PathVariable Long questionId) {
		log.info("DELETE / question deleted successfully");

		questionService.deleteQuestion(questionId);

		return "Question Deleted Successfully";
	}
}
