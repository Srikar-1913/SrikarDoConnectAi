/*
 * Author: Srikar Akula
 * Project: DoConnect
 * Description: Implementation of Answer service
 * Created Date: 17-06-2026
 */

package com.wipro.doconnect.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.wipro.doconnect.dto.AnswerDto;
import com.wipro.doconnect.entity.Answer;
import com.wipro.doconnect.entity.Question;
import com.wipro.doconnect.entity.User;
import com.wipro.doconnect.exceptions.AnswerNotFoundException;
import com.wipro.doconnect.exceptions.QuestionNotFoundException;
import com.wipro.doconnect.repository.AnswerRepository;
import com.wipro.doconnect.repository.QuestionRepository;
import com.wipro.doconnect.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

// Service implementation for Answer operations
@Service
@Slf4j
public class AnswerServiceImpl implements AnswerService {

    // Repository for answer
    @Autowired
    private AnswerRepository answerRepository;

    // Repository for user
    @Autowired
    private UserRepository userRepository;

    // Repository for question
    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public Answer saveAnswer(AnswerDto answerDto) {
        
        // Log saving action
         log.info("Saving answer for questionId: {}", 
                         answerDto.getQuestionId());

        Answer answer = new Answer();

        // Set answer content and time
        answer.setContent(answerDto.getContent());
        answer.setCreatedAt(LocalDateTime.now());

        // Get question by ID
        Question question = questionRepository.findById(answerDto.getQuestionId())
                .orElseThrow(() -> new QuestionNotFoundException("Question not found"));

        answer.setQuestion(question);

        // Get logged-in user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User user = userRepository.findByEmail(email);

        answer.setUser(user);

        // Save answer
        Answer savedAnswer = answerRepository.save(answer);

        // Log success
        log.info("Answer saved with id: {} by user: {}", 
                 savedAnswer.getAnswerId(), user.getName());

        // Return saved answer
        return answerRepository.save(savedAnswer);
    }

    @Override
    public List<Answer> getAllAnswers() {

        // Get all answers
        return answerRepository.findAll();
    }

    @Override
    public Answer getAnswerById(Long answerId) {

        // Get answer by ID or throw exception
        return answerRepository.findById(answerId)
                .orElseThrow(() -> new AnswerNotFoundException("Answer not found with id : " + answerId));
    }

    @Override
    public Answer updateAnswer(Long answerId, AnswerDto answerDto) {

        // Log update action
        log.info("Updating answer with id: {}", answerId);

        // Get existing answer
        Answer answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new AnswerNotFoundException("Answer not found with id : " + answerId));

        // Update content
        answer.setContent(answerDto.getContent());

        // Save updated answer
        return answerRepository.save(answer);
    }

    @Override
    public void deleteAnswer(Long answerId) {

        // Log delete action
        log.info("Deleting answer with id: {}", answerId);

        // Get answer by ID
        Answer answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new AnswerNotFoundException("Answer not found with id : " + answerId));

        // Delete answer
        answerRepository.delete(answer);
    }
}
