package com.wipro.doconnect.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString

public class AnswerDto {

	private Long answerId;

	@NotBlank(message = "Answer cannot be empty")
	@Size(min = 5, message = "Answer should contain minimum 5 characters")
	private String content;

	private Long userId; 

	private Long questionId;

	private LocalDateTime createdAt;

}
