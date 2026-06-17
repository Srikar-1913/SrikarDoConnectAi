/*
 * Author: Srikar Akula
 * Project: DoConnect
 * Description: Global exception handler
 * Created Date: 17-06-2026
 */

package com.wipro.doconnect.exceptions;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// Handles all exceptions globally
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Build common response structure
    private ResponseEntity<Map<String, Object>> buildResponse(String message, HttpStatus status) {

        Map<String, Object> response = new HashMap<>();

        // Add response details
        response.put("timestamp", LocalDateTime.now());
        response.put("status", status.value());
        response.put("message", message);

        return new ResponseEntity<>(response, status);
    }

    // Handle User not found exception
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleUserNotFoundException(UserNotFoundException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    // Handle Question not found exception
    @ExceptionHandler(QuestionNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleQuestionNotFoundException(QuestionNotFoundException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    // Handle Answer not found exception
    @ExceptionHandler(AnswerNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleAnswerNotFoundException(AnswerNotFoundException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    // Handle Chat message not found exception
    @ExceptionHandler(ChatMessageNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleChatMessageNotFoundException(ChatMessageNotFoundException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    // Handle Impression not found exception
    @ExceptionHandler(ImpressionNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleImpressionNotFoundException(ImpressionNotFoundException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    // Handle all other exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleException(Exception ex) {
        return buildResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}