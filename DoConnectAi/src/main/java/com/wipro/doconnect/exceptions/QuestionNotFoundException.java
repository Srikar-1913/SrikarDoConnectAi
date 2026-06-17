/*
 * Author: Srikar Akula
 * Project: DoConnect
 * Description: Exception for question not found
 * Created Date: 17-06-2026
 */

package com.wipro.doconnect.exceptions;

// Custom exception when question is not found
public class QuestionNotFoundException extends RuntimeException {

    // Constructor with error message
    public QuestionNotFoundException(String message) {
        super(message);
    }
}
