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

@Service
@Slf4j
public class ImpressionServiceImpl implements ImpressionService {

	@Autowired
	private ImpressionRepository impressionRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AnswerRepository answerRepository;

	@Override
	public ImpressionDto saveImpression(ImpressionDto dto) {

	    log.info("Saving impression of type: {} for answerId: {}", 
	        dto.getType(), dto.getAnswerId());

	    Impression impression = new Impression();

	    impression.setType(dto.getType());

	    Answer answer = answerRepository.findById(dto.getAnswerId())
	            .orElseThrow(() -> 
	                new AnswerNotFoundException("Answer not found"));

	    impression.setAnswer(answer);

	    String email = SecurityContextHolder.getContext()
	            .getAuthentication()
	            .getName();

	    User user = userRepository.findByEmail(email);

	    if (user == null) {
	        throw new UserNotFoundException("User not found");
	    }

	    impression.setUser(user);

	    Impression saved = impressionRepository.save(impression);

	    dto.setImpressionId(saved.getImpressionId());

	    return dto;
	}
	
	@Override
	public Map<String, Long> getCountByAnswerId(Long answerId) {

	    Long likes = impressionRepository
	        .countByAnswer_AnswerIdAndType(answerId, ImpressionType.LIKE);

	    Long dislikes = impressionRepository
	        .countByAnswer_AnswerIdAndType(answerId, ImpressionType.DISLIKE);

	    Map<String, Long> result = new HashMap<>();
	    result.put("likes", likes);
	    result.put("dislikes", dislikes);

	    return result;
	}
}