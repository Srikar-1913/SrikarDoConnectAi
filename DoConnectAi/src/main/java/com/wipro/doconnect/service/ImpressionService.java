/*
 * Author: Srikar Akula
 * Project: DoConnect
 * Description: Service interface for impression operations
 * Created Date: 17-06-2026
 */

package com.wipro.doconnect.service;

import java.util.Map;

import com.wipro.doconnect.dto.ImpressionDto;

// Service interface for impression (like/dislike)
public interface ImpressionService {
    
    // Save like or dislike for an answer
    ImpressionDto saveImpression(ImpressionDto dto);
    
    // Get count of likes and dislikes for an answer
    Map<String, Long> getCountByAnswerId(Long answerId);
}