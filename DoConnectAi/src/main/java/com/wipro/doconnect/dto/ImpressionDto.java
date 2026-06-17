/*
 * Author: Srikar Akula
 * Project: DoConnect
 * Description: DTO class for impressions
 * Created Date: 17-06-2026
 */

package com.wipro.doconnect.dto;

import com.wipro.doconnect.entity.ImpressionType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Lombok annotations for constructors, getters, setters
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter

// DTO used to transfer impression data
public class ImpressionDto {
    
    // Unique ID of impression
    private Long impressionId;
    
    // Type of impression (LIKE / DISLIKE)
    private ImpressionType type;
    
    // ID of related answer
    private Long answerId;
}
