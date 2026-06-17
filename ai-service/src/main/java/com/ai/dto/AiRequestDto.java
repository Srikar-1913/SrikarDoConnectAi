/*
 * Author: Srikar Akula
 * Project: DoConnect
 * Description: DTO class for AI request data
 * Created Date: 17-06-2026
 */

package com.ai.dto;

import lombok.Data;

// Lombok annotation to generate getters, setters, toString, etc.
@Data

// DTO used to receive input data for AI request
public class AiRequestDto {

    // Title of the question
    private String title;

    // Description of the question
    private String description;
}