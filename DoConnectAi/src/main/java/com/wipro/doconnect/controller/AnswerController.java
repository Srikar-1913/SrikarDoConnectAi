/*
 * Author: Srikar Akula
 * Project: DoConnect
 * Description: Controller for Answer APIs
 * Created Date: 17-06-2026
 */

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

// REST controller for Answer operations
@RestController
@RequestMapping("/answers")
@Slf4j
public class AnswerController {

    // Service to handle answer logic
    @Autowired
    private AnswerService answerService;

    // Create new answer
    @PostMapping("/save")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public Answer saveAnswer(@RequestBody AnswerDto answerDto) {

        // Log action
        log.info("POST / answer added successfully");

        return answerService.saveAnswer(answerDto);
    }

    // Get all answers
    @GetMapping("/getAll")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public List<Answer> getAllAnswers() {

        // Log action
        log.info("GET / retrieved all answers successfully");

        return answerService.getAllAnswers();
    }

    // Get answer by ID
    @GetMapping("/get/{answerId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public Answer getAnswerById(@PathVariable Long answerId) {

        // Log action
        log.info("GET / retrieved answer by id");

        return answerService.getAnswerById(answerId);
    }

    // Update answer (admin only)
    @PutMapping("/update/{answerId}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public Answer updateAnswer(@PathVariable Long answerId, @RequestBody AnswerDto answerDto) {

        // Log action
        log.info("PUT / answer updated successfully");

        return answerService.updateAnswer(answerId, answerDto);
    }

    // Delete answer (admin only)
    @DeleteMapping("/delete/{answerId}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public String deleteAnswer(@PathVariable Long answerId) {

        // Log action
        log.info("DELETE / answer deleted successfully");

        answerService.deleteAnswer(answerId);

        return "Answer Deleted Successfully";
    }
}