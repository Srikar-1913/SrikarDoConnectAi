package com.wipro.doconnect.service;

import java.util.List;


import com.wipro.doconnect.dto.QuestionDto;
import com.wipro.doconnect.entity.Question;

public interface QuestionService {

    Question saveQuestion(QuestionDto questionDto);

    List<Question> getAllQuestions();

    Question getQuestionById(Long questionId);

    Question updateQuestion(Long questionId,
                            QuestionDto questionDto);

    void deleteQuestion(Long questionId);
}
