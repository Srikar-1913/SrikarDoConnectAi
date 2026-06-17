/*
 * Author: Srikar Akula
 * Project: DoConnect
 * Description: Exception for impression not found
 * Created Date: 17-06-2026
 */

package com.wipro.doconnect.exceptions;

// Custom exception when impression is not found
public class ImpressionNotFoundException extends RuntimeException {

    // Constructor with error message
    public ImpressionNotFoundException(String message) {
        super(message);
    }
}