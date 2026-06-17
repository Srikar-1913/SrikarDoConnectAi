/*
 * Author: Srikar Akula
 * Project: DoConnect
 * Description: Entity class for Answer table
 * Created Date: 17-06-2026
 */

package com.wipro.doconnect.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// Lombok annotations for boilerplate code
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString

// Entity mapped to answers table
@Entity
@Table(name = "answers")
public class Answer {

    // Primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long answerId;

    // Answer content
    private String content;

    // Created time
    private LocalDateTime createdAt;

    // Many answers belong to one user
    @ManyToOne
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private User user;

    // Many answers belong to one question
    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    // One answer has many impressions (likes/dislikes)
    @OneToMany(mappedBy = "answer", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Impression> impressions;
}