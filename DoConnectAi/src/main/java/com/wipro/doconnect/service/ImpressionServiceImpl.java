/*
 * Author: Srikar Akula
 * Project: DoConnect
 * Description: Implementation of Impression service
 * Created Date: 17-06-2026
 */

package com.wipro.doconnect.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.wipro.doconnect.dto.ImpressionDto;
import com.wipro.doconnect.entity.Answer;
import com.wipro.doconnect.entity.Impression;
import com.wipro.doconnect.entity.ImpressionType;
import com.wipro.doconnect.entity.User;
import com.wipro.doconnect.exceptions.AnswerNotFoundException;
import com.wipro.doconnect.exceptions.UserNotFoundException;
import com.wipro.doconnect.repository.AnswerRepository;
import com.wipro.doconnect.repository.ImpressionRepository;
import com.wipro.doconnect.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

// Service implementation for impression (like/dislike)
@Service
@Slf4j
public class ImpressionServiceImpl implements ImpressionService {

    // Repository for impressions
    @Autowired
    private ImpressionRepository impressionRepository;

    // Repository for users
    @Autowired
    private UserRepository userRepository;

    // Repository for answers
    @Autowired
    private AnswerRepository answerRepository;

    @Override
    public ImpressionDto saveImpression(ImpressionDto dto) {

        // Log save action
        log.info("Saving impression of type: {} for answerId: {}", 
            dto.getType(), dto.getAnswerId());

        Impression impression = new Impression();

        // Set impression type
        impression.setType(dto.getType());

        // Get answer by ID
        Answer answer = answerRepository.findById(dto.getAnswerId())
                .orElseThrow(() -> 
                    new AnswerNotFoundException("Answer not found"));

        impression.setAnswer(answer);

        // Get logged-in user
        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email);

        // Check user exists
        if (user == null) {
            throw new UserNotFoundException("User not found");
        }

        impression.setUser(user);

        // Save impression
        Impression saved = impressionRepository.save(impression);

        // Set generated ID to DTO
        dto.setImpressionId(saved.getImpressionId());

        return dto;
    }
    
    @Override
    public Map<String, Long> getCountByAnswerId(Long answerId) {

        // Count likes
        Long likes = impressionRepository
            .countByAnswer_AnswerIdAndType(answerId, ImpressionType.LIKE);

        // Count dislikes
        Long dislikes = impressionRepository
            .countByAnswer_AnswerIdAndType(answerId, ImpressionType.DISLIKE);

        // Prepare result map
        Map<String, Long> result = new HashMap<>();
        result.put("likes", likes);
        result.put("dislikes", dislikes);

        return result;
    }
}
