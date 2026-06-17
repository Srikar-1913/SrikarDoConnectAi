/*
 * Author: Srikar Akula
 * Project: DoConnect
 * Description: Controller for question APIs
 * Created Date: 17-06-2026
 */

package com.wipro.doconnect.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.wipro.doconnect.dto.QuestionDto;
import com.wipro.doconnect.entity.Question;
import com.wipro.doconnect.service.QuestionService;

import lombok.extern.slf4j.Slf4j;

// REST controller for Question operations
@RestController
@RequestMapping("/questions")
@Slf4j
public class QuestionController {

    // Service to handle question logic
    @Autowired
    private QuestionService questionService;

    // Create new question
    @PostMapping("/save")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public Question saveQuestion(@RequestBody QuestionDto questionDto) {

        // Log action
        log.info("POST / question added successfully");

        return questionService.saveQuestion(questionDto);
    }
 
    // Get all questions
    @GetMapping("/getAll")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public List<Question> getAllQuestions() {

        // Log action
        log.info("GET / retrieved all questions successfully");

        return questionService.getAllQuestions();
    }

    // Get question by ID (admin only)
    @GetMapping("/get/{questionId}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public Question getQuestionById(@PathVariable Long questionId) {

        // Log action
        log.info("GET / retrieved question by id");

        return questionService.getQuestionById(questionId);
    }

    // Update question (admin only)
    @PutMapping("/update/{questionId}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public Question updateQuestion(@PathVariable Long questionId, @RequestBody QuestionDto questionDto) {

        // Log action
        log.info("PUT / question updated successfully");

        return questionService.updateQuestion(questionId, questionDto);
    }

    // Delete question (admin only)
    @DeleteMapping("/delete/{questionId}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public String deleteQuestion(@PathVariable Long questionId) {

        // Log action
        log.info("DELETE / question deleted successfully");

        questionService.deleteQuestion(questionId);

        return "Question Deleted Successfully";
    }
}
