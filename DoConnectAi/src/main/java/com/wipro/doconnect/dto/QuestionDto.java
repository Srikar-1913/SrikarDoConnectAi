/*
 * Author: Srikar Akula
 * Project: DoConnect
 * Description: DTO class for question data
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

// DTO used to transfer question data
public class QuestionDto {
    
    // Unique ID of question
    private Long questionId;
    
    // Question title (required)
    @NotBlank(message = "Title is required")
    private String title;
    
    // Question description with validation
    @NotBlank(message = "Description is required")
    @Size(min = 10, message = "Description must contain minimum 10 characters")
    private String description;
    
    // ID of user who posted the question
    private Long userId;
    
    // Time when question was created
    private LocalDateTime createdAt;
}
