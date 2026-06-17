/*
 * Author: Srikar Akula
 * Project: DoConnect
 * Description: DTO class for chat messages
 * Created Date: 17-06-2026
 */

package com.wipro.doconnect.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// Lombok annotations for constructors, getters, setters
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString

// DTO used to transfer chat message data
public class ChatMessageDto {

    // Unique ID of message
    private Long messageId;

    // Message content with validation
    @NotBlank(message = "Message cannot be empty")
    private String message;

    // Related answer ID (required)
    @NotNull(message = "AnswerId is required")
    private Long answerId;

    // Time when message was sent
    private LocalDateTime sentAt;
}