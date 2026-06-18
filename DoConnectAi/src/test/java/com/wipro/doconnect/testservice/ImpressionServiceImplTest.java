package com.wipro.doconnect.testservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.wipro.doconnect.dto.ImpressionDto;
import com.wipro.doconnect.entity.Answer;
import com.wipro.doconnect.entity.Impression;
import com.wipro.doconnect.entity.ImpressionType;
import com.wipro.doconnect.entity.User;
import com.wipro.doconnect.exceptions.AnswerNotFoundException;
import com.wipro.doconnect.exceptions.UserNotFoundException;
import com.wipro.doconnect.repository.AnswerRepository;
import com.wipro.doconnect.repository.ImpressionRepository;
import com.wipro.doconnect.repository.UserRepository;
import com.wipro.doconnect.service.ImpressionServiceImpl;

class ImpressionServiceImplTest {

    @InjectMocks
    private ImpressionServiceImpl impressionService;

    @Mock
    private ImpressionRepository impressionRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AnswerRepository answerRepository;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    private User user;
    private Answer answer;
    private Impression impression;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setUserId(1L);
        user.setEmail("test@gmail.com");

        answer = new Answer();
        answer.setAnswerId(1L);

        impression = new Impression();
        impression.setImpressionId(1L);
        impression.setUser(user);
        impression.setAnswer(answer);
        impression.setType(ImpressionType.LIKE);

        // ✅ Mock SecurityContext
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("test@gmail.com");
    }

    @Test
    void testSaveImpression() {

        ImpressionDto dto = new ImpressionDto();
        dto.setAnswerId(1L);
        dto.setType(ImpressionType.LIKE);

        when(answerRepository.findById(1L)).thenReturn(Optional.of(answer));
        when(userRepository.findByEmail("test@gmail.com")).thenReturn(user);
        when(impressionRepository.save(any(Impression.class))).thenReturn(impression);

        ImpressionDto result = impressionService.saveImpression(dto);

        assertNotNull(result);
        assertEquals(1L, result.getImpressionId());
    }

    @Test
    void testSaveImpression_AnswerNotFound() {

        ImpressionDto dto = new ImpressionDto();
        dto.setAnswerId(1L);
        dto.setType(ImpressionType.LIKE);

        when(answerRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(AnswerNotFoundException.class, () -> {
            impressionService.saveImpression(dto);
        });
    }

    @Test
    void testSaveImpression_UserNotFound() {

        ImpressionDto dto = new ImpressionDto();
        dto.setAnswerId(1L);
        dto.setType(ImpressionType.DISLIKE);

        when(answerRepository.findById(1L)).thenReturn(Optional.of(answer));
        when(userRepository.findByEmail("test@gmail.com")).thenReturn(null);

        assertThrows(UserNotFoundException.class, () -> {
            impressionService.saveImpression(dto);
        });
    }

    @Test
    void testGetCountByAnswerId() {

        when(impressionRepository.countByAnswer_AnswerIdAndType(1L, ImpressionType.LIKE))
                .thenReturn(5L);

        when(impressionRepository.countByAnswer_AnswerIdAndType(1L, ImpressionType.DISLIKE))
                .thenReturn(2L);

        Map<String, Long> result = impressionService.getCountByAnswerId(1L);

        assertEquals(5L, result.get("likes"));
        assertEquals(2L, result.get("dislikes"));
    }
}