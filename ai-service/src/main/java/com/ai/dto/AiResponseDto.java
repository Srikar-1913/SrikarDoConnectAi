package com.ai.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AiResponseDto {
	
	private Integer id;
	
	private String keyword;
	
	private String answer;

}
