package com.wipro.doconnect.testservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
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

import com.wipro.doconnect.dto.AnswerDto;
import com.wipro.doconnect.entity.Answer;
import com.wipro.doconnect.entity.Question;
import com.wipro.doconnect.entity.User;
import com.wipro.doconnect.exceptions.AnswerNotFoundException;
import com.wipro.doconnect.exceptions.QuestionNotFoundException;
import com.wipro.doconnect.repository.AnswerRepository;
import com.wipro.doconnect.repository.QuestionRepository;
import com.wipro.doconnect.repository.UserRepository;
import com.wipro.doconnect.service.AnswerServiceImpl;

class AnswerServiceImplTest {

    @InjectMocks
    private AnswerServiceImpl answerService;

    @Mock
    private AnswerRepository answerRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private QuestionRepository questionRepository;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    private Answer answer;
    private User user;
    private Question question;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setUserId(1L);
        user.setName("Srikar");
        user.setEmail("test@gmail.com");

        question = new Question();
        question.setQuestionId(1L);

        answer = new Answer();
        answer.setAnswerId(1L);
        answer.setContent("Test Answer");
        answer.setUser(user);
        answer.setQuestion(question);

        // ✅ Mock Security
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("test@gmail.com");
    }

    @Test
    void testSaveAnswer() {

        AnswerDto dto = new AnswerDto();
        dto.setContent("Test Answer");
        dto.setQuestionId(1L);

        when(questionRepository.findById(1L)).thenReturn(Optional.of(question));
        when(userRepository.findByEmail("test@gmail.com")).thenReturn(user);
        when(answerRepository.save(any(Answer.class))).thenReturn(answer);

        Answer result = answerService.saveAnswer(dto);

        assertNotNull(result);
        assertEquals("Test Answer", result.getContent());

        // called twice (your code saves twice)
        verify(answerRepository, times(2)).save(any(Answer.class));
    }

    @Test
    void testSaveAnswer_QuestionNotFound() {

        AnswerDto dto = new AnswerDto();
        dto.setContent("Test");
        dto.setQuestionId(1L);

        when(questionRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(QuestionNotFoundException.class, () -> {
            answerService.saveAnswer(dto);
        });
    }

    @Test
    void testGetAllAnswers() {

        when(answerRepository.findAll()).thenReturn(List.of(answer));

        List<Answer> list = answerService.getAllAnswers();

        assertEquals(1, list.size());
    }

    @Test
    void testGetAnswerById() {

        when(answerRepository.findById(1L)).thenReturn(Optional.of(answer));

        Answer result = answerService.getAnswerById(1L);

        assertEquals("Test Answer", result.getContent());
    }

    @Test
    void testGetAnswerById_NotFound() {

        when(answerRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(AnswerNotFoundException.class, () -> {
            answerService.getAnswerById(1L);
        });
    }

    @Test
    void testUpdateAnswer() {

        AnswerDto dto = new AnswerDto();
        dto.setContent("Updated Answer");

        when(answerRepository.findById(1L)).thenReturn(Optional.of(answer));
        when(answerRepository.save(any(Answer.class))).thenReturn(answer);

        Answer updated = answerService.updateAnswer(1L, dto);

        assertEquals("Updated Answer", updated.getContent());
    }

    @Test
    void testDeleteAnswer() {

        when(answerRepository.findById(1L)).thenReturn(Optional.of(answer));

        answerService.deleteAnswer(1L);

        verify(answerRepository, times(1)).delete(answer);
    }
}
