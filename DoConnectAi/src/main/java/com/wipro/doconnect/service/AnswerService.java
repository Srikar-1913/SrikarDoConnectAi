package com.wipro.doconnect.service;

import java.util.List;


import com.wipro.doconnect.dto.AnswerDto;
import com.wipro.doconnect.entity.Answer;

public interface AnswerService {

    Answer saveAnswer(AnswerDto answerDto);

    List<Answer> getAllAnswers();

    Answer getAnswerById(Long answerId);

    Answer updateAnswer(Long answerId,
                        AnswerDto answerDto);

    void deleteAnswer(Long answerId);
}
