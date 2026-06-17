package com.wipro.doconnect.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.doconnect.dto.AnswerDto;
import com.wipro.doconnect.entity.Answer;
import com.wipro.doconnect.service.AnswerService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/answers")
@Slf4j
public class AnswerController {

	@Autowired
	private AnswerService answerService;

	@PostMapping("/save")
	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	public Answer saveAnswer(@RequestBody AnswerDto answerDto) {
		log.info("POST / answer added successfully");

		return answerService.saveAnswer(answerDto);
	}

	@GetMapping("/getAll")
	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	public List<Answer> getAllAnswers() {
		log.info("GET / retrived all answers successfully");

		return answerService.getAllAnswers();
	}

	@GetMapping("/get/{answerId}")
	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	public Answer getAnswerById(@PathVariable Long answerId) {
		log.info("GET / retrived answer by id");

		return answerService.getAnswerById(answerId);
	}

	@PutMapping("/update/{answerId}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public Answer updateAnswer(@PathVariable Long answerId, @RequestBody AnswerDto answerDto) {
		log.info("PUT / answer updated successfully");

		return answerService.updateAnswer(answerId, answerDto);
	}

	@DeleteMapping("/delete/{answerId}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public String deleteAnswer(@PathVariable Long answerId) {
		log.info("DELETE / answer deleted successfully");

		answerService.deleteAnswer(answerId);

		return "Answer Deleted Successfully";
	}
}
