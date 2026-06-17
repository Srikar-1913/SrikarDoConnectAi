/*
 * Author: Srikar Akula
 * Project: DoConnect
 * Description: DTO class for AI response data
 * Created Date: 17-06-2026
 */

package com.ai.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Lombok annotations for getters, setters and constructors
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

// DTO used to send AI response data
public class AiResponseDto {
    
    // Unique ID
    private Integer id;
    
    // Keyword used for generating answer
    private String keyword;
    
    // AI generated answer
    private String answer;
}
