package com.wipro.doconnect.service;

import java.util.Map;

import com.wipro.doconnect.dto.ImpressionDto;

public interface ImpressionService {
	
	ImpressionDto saveImpression(ImpressionDto dto);
	
	Map<String, Long> getCountByAnswerId(Long answerId);
}
