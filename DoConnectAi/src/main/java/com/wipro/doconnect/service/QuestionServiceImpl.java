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

@Service
@Slf4j
public class QuestionServiceImpl implements QuestionService {

	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired 
	private ImpressionRepository impressionRepository;
	
	@Autowired
	private ChatMessageRepository chatMessageRepository;
	
	@Autowired AnswerRepository answerRepository;

	@Override
	public Question saveQuestion(QuestionDto questionDto) {

		log.info("Saving question with title: '{}' by userId: {}", questionDto.getTitle());

		Question question = new Question();

		question.setTitle(questionDto.getTitle());
		question.setDescription(questionDto.getDescription());
		question.setCreatedAt(LocalDateTime.now());

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();

		User loggedInUser = userRepository.findByEmail(email);

		question.setUser(loggedInUser);

		return questionRepository.save(question);
	}

	@Override
	public List<Question> getAllQuestions() {
		return questionRepository.findAll();
	}

	@Override
	public Question getQuestionById(Long questionId) {
		return questionRepository.findById(questionId)
				.orElseThrow(() -> new QuestionNotFoundException("Question not found with id: " + questionId));
	}

	@Override
	public Question updateQuestion(Long questionId, QuestionDto questionDto) {
		log.info("Updating question with id: {}", questionId);

		Question question = questionRepository.findById(questionId)
				.orElseThrow(() -> new QuestionNotFoundException("Question not found with id: " + questionId));

		question.setTitle(questionDto.getTitle());
		question.setDescription(questionDto.getDescription());

		// update user also (important improvement)
		if (questionDto.getUserId() != null) {
			User user = userRepository.findById(questionDto.getUserId())
					.orElseThrow(() -> new UserNotFoundException("User not found with id: " + questionDto.getUserId()));

			question.setUser(user);
		}

		return questionRepository.save(question);
	}

	@Override
	public void deleteQuestion(Long questionId) {
		log.info("Deleting question with id: {}", questionId);

		Question question = questionRepository.findById(questionId)
				.orElseThrow(() -> new QuestionNotFoundException("Question not found with id: " + questionId));

		List<Answer> answers = answerRepository.findByQuestion(question);

		for (Answer answer : answers) {

			impressionRepository.deleteByAnswer(answer);

			chatMessageRepository.deleteByAnswer(answer);

			answerRepository.delete(answer);
		}

		questionRepository.delete(question);
	}
}