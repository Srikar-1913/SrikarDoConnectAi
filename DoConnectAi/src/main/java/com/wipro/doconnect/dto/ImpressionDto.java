package com.wipro.doconnect.dto;

import com.wipro.doconnect.entity.ImpressionType;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter

public class ImpressionDto {
	
	private Long impressionId;
	
	private ImpressionType type;
	
//	private Long userId;

    private Long answerId;

}
