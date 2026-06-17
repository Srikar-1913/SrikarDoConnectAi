/*
 * Author: Srikar Akula
 * Project: DoConnect
 * Description: Service interface for question operations
 * Created Date: 17-06-2026
 */

package com.wipro.doconnect.service;

import java.util.List;

import com.wipro.doconnect.dto.QuestionDto;
import com.wipro.doconnect.entity.Question;

// Service interface for Question operations
public interface QuestionService {

    // Save new question
    Question saveQuestion(QuestionDto questionDto);

    // Get all questions
    List<Question> getAllQuestions();

    // Get question by ID
    Question getQuestionById(Long questionId);

    // Update question details
    Question updateQuestion(Long questionId,
                            QuestionDto questionDto);

    // Delete question by ID
    void deleteQuestion(Long questionId);
}
