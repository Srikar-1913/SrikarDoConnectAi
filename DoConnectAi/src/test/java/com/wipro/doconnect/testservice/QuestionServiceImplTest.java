package com.wipro.doconnect.testservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.wipro.doconnect.dto.QuestionDto;
import com.wipro.doconnect.entity.Question;
import com.wipro.doconnect.entity.User;
import com.wipro.doconnect.repository.QuestionRepository;
import com.wipro.doconnect.repository.UserRepository;
import com.wipro.doconnect.service.QuestionServiceImpl;

class QuestionServiceImplTest {

	@InjectMocks
	private QuestionServiceImpl questionService;

	@Mock
	private QuestionRepository questionRepository;

	@Mock
	private UserRepository userRepository;

	@Mock
	private SecurityContext securityContext;

	@Mock
	private Authentication authentication;

	private User user;
	private Question question;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);

		SecurityContextHolder.setContext(securityContext);
		when(securityContext.getAuthentication()).thenReturn(authentication);
		when(authentication.getName()).thenReturn("test@gmail.com");

		user = new User();
		user.setUserId(1L);
		user.setName("Srikar");
		user.setEmail("test@gmail.com");

		question = new Question();
		question.setQuestionId(1L);
		question.setTitle("Test Title");
		question.setDescription("Test Desc");
		question.setUser(user);
	}

	@Test
	void testSaveQuestion() {

		QuestionDto dto = new QuestionDto();
		dto.setTitle("Test Title");
		dto.setDescription("Test Desc");

		when(userRepository.findByEmail("test@gmail.com")).thenReturn(user);
		when(questionRepository.save(any(Question.class))).thenReturn(question);

		Question saved = questionService.saveQuestion(dto);

		assertNotNull(saved);
		assertEquals("Test Title", saved.getTitle());
	}

	@Test
	void testGetAllQuestions() {

		when(questionRepository.findAll()).thenReturn(List.of(question));

		List<Question> list = questionService.getAllQuestions();

		assertEquals(1, list.size());
	}

	@Test
	void testGetQuestionById() {

		when(questionRepository.findById(1L)).thenReturn(Optional.of(question));

		Question result = questionService.getQuestionById(1L);

		assertEquals("Test Title", result.getTitle());
	}

	@Test
	void testUpdateQuestion() {

		QuestionDto dto = new QuestionDto();
		dto.setTitle("Updated Title");
		dto.setDescription("Updated Desc");

		when(questionRepository.findById(1L)).thenReturn(Optional.of(question));

		when(userRepository.findByEmail("test@gmail.com")).thenReturn(user);
		when(questionRepository.save(any(Question.class))).thenReturn(question);

		Question updated = questionService.updateQuestion(1L, dto);

		assertEquals("Updated Title", updated.getTitle());
	}

	@Test
	void testDeleteQuestion() {

	    when(questionRepository.findById(1L)).thenReturn(Optional.of(question));


	    QuestionServiceImpl spyService = org.mockito.Mockito.spy(questionService);

	    doNothing().when(spyService).deleteQuestion(1L);

	    spyService.deleteQuestion(1L);

	    verify(spyService, times(1)).deleteQuestion(1L);
	}

	@Test
	void testGetQuestionById_NotFound() {

		when(questionRepository.findById(1L)).thenReturn(Optional.empty());

		assertThrows(RuntimeException.class, () -> {
			questionService.getQuestionById(1L);
		});
	}
}