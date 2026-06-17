/*
 * Author: Srikar Akula
 * Project: DoConnect
 * Description: Exception for chat message not found
 * Created Date: 17-06-2026
 */

package com.wipro.doconnect.exceptions;

// Custom exception when chat message is not found
public class ChatMessageNotFoundException extends RuntimeException{
    
    // Constructor with error message
    public ChatMessageNotFoundException(String message) {
        super(message);
    }
}