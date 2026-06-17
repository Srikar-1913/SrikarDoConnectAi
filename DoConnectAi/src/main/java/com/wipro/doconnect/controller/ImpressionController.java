/*
 * Author: Srikar Akula
 * Project: DoConnect
 * Description: Controller for impression APIs
 * Created Date: 17-06-2026
 */

package com.wipro.doconnect.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.doconnect.dto.ImpressionDto;
import com.wipro.doconnect.service.ImpressionService;

import lombok.extern.slf4j.Slf4j;

// REST controller for impression (like/dislike)
@RestController
@RequestMapping("/impressions")
@Slf4j
public class ImpressionController {

    // Service to handle impression logic
    @Autowired
    private ImpressionService impressionService;

    // Save like or dislike
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ImpressionDto saveImpression(@RequestBody ImpressionDto dto) {

        // Log action
        log.info("POST / impression added successfully");
        
        return impressionService.saveImpression(dto);
    }
    
    // Get like and dislike count for an answer
    @GetMapping("/count/{answerId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public Map<String, Long> getCount(@PathVariable Long answerId) {

        // Log action
        log.info("GET / count for answerId: {}", answerId);

        return impressionService.getCountByAnswerId(answerId);
    }
}