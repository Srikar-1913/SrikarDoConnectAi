/*
 * Author: Srikar Akula
 * Project: DoConnect
 * Description: Entity class for impressions (like/dislike)
 * Created Date: 17-06-2026
 */

package com.wipro.doconnect.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// Lombok annotations for constructors, getters, setters
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter

// Entity mapped for impression data
@Entity
public class Impression {

    // Primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long impressionId;

    // Type of impression (LIKE / DISLIKE)
    @Enumerated(EnumType.STRING)
    private ImpressionType type;

    // Many impressions belong to one user
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Many impressions belong to one answer
    @ManyToOne
    @JoinColumn(name = "answer_id")
    @ToString.Exclude
    private Answer answer;
}
