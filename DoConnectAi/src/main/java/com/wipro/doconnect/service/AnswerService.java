/*
 * Author: Srikar Akula
 * Project: DoConnect
 * Description: Service interface for Answer operations
 * Created Date: 17-06-2026
 */

package com.wipro.doconnect.service;

import java.util.List;

import com.wipro.doconnect.dto.AnswerDto;
import com.wipro.doconnect.entity.Answer;

// Service interface for Answer operations
public interface AnswerService {

    // Save new answer
    Answer saveAnswer(AnswerDto answerDto);

    // Get all answers
    List<Answer> getAllAnswers();

    // Get answer by ID
    Answer getAnswerById(Long answerId);

    // Update answer details
    Answer updateAnswer(Long answerId,
                        AnswerDto answerDto);

    // Delete answer by ID
    void deleteAnswer(Long answerId);
}