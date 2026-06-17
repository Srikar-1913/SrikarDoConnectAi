/*
 * Author: Srikar Akula
 * Project: DoConnect
 * Description: Entity class for questions
 * Created Date: 17-06-2026
 */

package com.wipro.doconnect.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

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

// Entity mapped to questions table
@Entity
@Table(name = "questions")
public class Question {

	// Primary key
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long questionId;

	// Question title
	private String title;

	// Question description
	private String description;

	// Creation time
	private LocalDateTime createdAt;

	// Many questions belong to one user
	@ManyToOne
	@JoinColumn(name = "user_id")
	@ToString.Exclude
	private User user;
}