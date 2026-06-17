/*
 * Author: Srikar Akula
 * Project: DoConnect
 * Description: Implementation of Question service
 * Created Date: 17-06-2026
 */

package com.wipro.doconnect.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.wipro.doconnect.dto.QuestionDto;
import com.wipro.doconnect.entity.Answer;
import com.wipro.doconnect.entity.Question;
import com.wipro.doconnect.entity.User;
import com.wipro.doconnect.exceptions.QuestionNotFoundException;
import com.wipro.doconnect.exceptions.UserNotFoundException;
import com.wipro.doconnect.repository.AnswerRepository;
import com.wipro.doconnect.repository.ChatMessageRepository;
import com.wipro.doconnect.repository.ImpressionRepository;
import com.wipro.doconnect.repository.QuestionRepository;
import com.wipro.doconnect.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

// Service implementation for question operations
@Service
@Slf4j
public class QuestionServiceImpl implements QuestionService {

    // Repository for questions
    @Autowired
    private QuestionRepository questionRepository;

    // Repository for users
    @Autowired
    private UserRepository userRepository;
    
    // Repository for impressions
    @Autowired 
    private ImpressionRepository impressionRepository;
    
    // Repository for chat messages
    @Autowired
    private ChatMessageRepository chatMessageRepository;
    
    // Repository for answers
    @Autowired
    private AnswerRepository answerRepository;

    @Override
    public Question saveQuestion(QuestionDto questionDto) {

        // Log save action
        log.info("Saving question with title: '{}' by userId: {}", questionDto.getTitle());

        Question question = new Question();

        // Set question details
        question.setTitle(questionDto.getTitle());
        question.setDescription(questionDto.getDescription());
        question.setCreatedAt(LocalDateTime.now());

        // Get logged-in user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User loggedInUser = userRepository.findByEmail(email);

        question.setUser(loggedInUser);

        // Save question
        return questionRepository.save(question);
    }

    @Override
    public List<Question> getAllQuestions() {

        // Get all questions
        return questionRepository.findAll();
    }

    @Override
    public Question getQuestionById(Long questionId) {

        // Get question by ID or throw exception
        return questionRepository.findById(questionId)
                .orElseThrow(() -> new QuestionNotFoundException("Question not found with id: " + questionId));
    }

    @Override
    public Question updateQuestion(Long questionId, QuestionDto questionDto) {

        // Log update action
        log.info("Updating question with id: {}", questionId);

        // Get existing question
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new QuestionNotFoundException("Question not found with id: " + questionId));

        // Update details
        question.setTitle(questionDto.getTitle());
        question.setDescription(questionDto.getDescription());

        // Update user if provided
        if (questionDto.getUserId() != null) {
            User user = userRepository.findById(questionDto.getUserId())
                    .orElseThrow(() -> new UserNotFoundException("User not found with id: " + questionDto.getUserId()));

            question.setUser(user);
        }

        // Save updated question
        return questionRepository.save(question);
    }

    @Override
    public void deleteQuestion(Long questionId) {

        // Log delete action
        log.info("Deleting question with id: {}", questionId);

        // Get question by ID
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new QuestionNotFoundException("Question not found with id: " + questionId));

        // Get related answers
        List<Answer> answers = answerRepository.findByQuestion(question);

        // Delete related data
        for (Answer answer : answers) {

            impressionRepository.deleteByAnswer(answer);

            chatMessageRepository.deleteByAnswer(answer);

            answerRepository.delete(answer);
        }

        // Delete question
        questionRepository.delete(question);
    }
}
