/*
 * Author: Srikar Akula
 * Project: DoConnect
 * Description: Entity class for AI response data
 * Created Date: 17-06-2026
 */

package com.ai.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Lombok annotations for getters, setters and constructors
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

// Entity mapped to database table
@Entity
public class AiResponse {

    // Primary key with auto generation
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    // Input keyword
    public String keyword;

    // AI generated answer
    public String answer;
}
