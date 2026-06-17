/*
 * Author: Srikar Akula
 * Project: DoConnect
 * Description: Exception for answer not found
 * Created Date: 17-06-2026
 */

package com.wipro.doconnect.exceptions;

// Custom exception when answer is not found
public class AnswerNotFoundException
        extends RuntimeException {

    // Constructor with error message
    public AnswerNotFoundException(String message) {
        super(message);
    }
}
