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

@Service
@Slf4j
public class AnswerServiceImpl implements AnswerService {

	@Autowired
	private AnswerRepository answerRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private QuestionRepository questionRepository;

	@Override
	public Answer saveAnswer(AnswerDto answerDto) {
		

		 log.info("Saving answer for questionId: {}", 
		                 answerDto.getQuestionId());


		Answer answer = new Answer();

		answer.setContent(answerDto.getContent());
		answer.setCreatedAt(LocalDateTime.now());


	    Question question = questionRepository.findById(answerDto.getQuestionId())
	            .orElseThrow(() -> new QuestionNotFoundException("Question not found"));

	    answer.setQuestion(question);

	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    String email = authentication.getName();

	    User user = userRepository.findByEmail(email);

	    answer.setUser(user);

	    Answer savedAnswer = answerRepository.save(answer);

	    log.info("Answer saved with id: {} by user: {}", 
	             savedAnswer.getAnswerId(), user.getName());


		return answerRepository.save(savedAnswer);
	}

	@Override
	public List<Answer> getAllAnswers() {
		return answerRepository.findAll();
	}

	@Override
	public Answer getAnswerById(Long answerId) {
		return answerRepository.findById(answerId)
				.orElseThrow(() -> new AnswerNotFoundException("Answer not found with id : " + answerId));
	}

	@Override
	public Answer updateAnswer(Long answerId, AnswerDto answerDto) {
		log.info("Updating answer with id: {}", answerId);

		Answer answer = answerRepository.findById(answerId)
				.orElseThrow(() -> new AnswerNotFoundException("Answer not found with id : " + answerId));

		answer.setContent(answerDto.getContent());

		return answerRepository.save(answer);
	}

	@Override
	public void deleteAnswer(Long answerId) {
		log.info("Deleting answer with id: {}", answerId);

		Answer answer = answerRepository.findById(answerId)
				.orElseThrow(() -> new AnswerNotFoundException("Answer not found with id : " + answerId));

		answerRepository.delete(answer);
	}
}