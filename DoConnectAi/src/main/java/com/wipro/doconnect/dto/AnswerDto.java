/*
 * Author: Srikar Akula
 * Project: DoConnect
 * Description: DTO class for Answer data
 * Created Date: 17-06-2026
 */

package com.wipro.doconnect.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

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

// DTO used to transfer answer data
public class AnswerDto {

    // Unique ID of answer
    private Long answerId;

    // Answer content with validation
    @NotBlank(message = "Answer cannot be empty")
    @Size(min = 5, message = "Answer should contain minimum 5 characters")
    private String content;

    // ID of user who posted answer
    private Long userId; 

    // ID of related question
    private Long questionId;

    // Time when answer was created
    private LocalDateTime createdAt;
}