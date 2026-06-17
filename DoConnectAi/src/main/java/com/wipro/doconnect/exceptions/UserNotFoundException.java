/*
 * Author: Srikar Akula
 * Project: DoConnect
 * Description: Exception for user not found
 * Created Date: 17-06-2026
 */

package com.wipro.doconnect.exceptions;

// Custom exception when user is not found
public class UserNotFoundException extends RuntimeException {

    // Constructor with error message
    public UserNotFoundException(String message) {
        super(message);
    }
}
