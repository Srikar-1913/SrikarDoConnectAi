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

import com.wipro.doconnect.dto.ChatMessageDto;
import com.wipro.doconnect.entity.Answer;
import com.wipro.doconnect.entity.ChatMessage;
import com.wipro.doconnect.entity.User;
import com.wipro.doconnect.exceptions.ChatMessageNotFoundException;
import com.wipro.doconnect.repository.AnswerRepository;
import com.wipro.doconnect.repository.ChatMessageRepository;
import com.wipro.doconnect.repository.UserRepository;
import com.wipro.doconnect.service.ChatMessageServiceImpl;

class ChatMessageServiceImplTest {

    @InjectMocks
    private ChatMessageServiceImpl chatService;

    @Mock
    private ChatMessageRepository chatMessageRepository;

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
    private ChatMessage message;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setUserId(1L);
        user.setEmail("test@gmail.com");

        answer = new Answer();
        answer.setAnswerId(1L);

        message = new ChatMessage();
        message.setMessageId(1L);
        message.setMessage("Hello");
        message.setUser(user);
        message.setAnswer(answer);

        // Mock SecurityContextHolder
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("test@gmail.com");
    }

    @Test
    void testSaveMessage() {

        ChatMessageDto dto = new ChatMessageDto();
        dto.setMessage("Hello");
        dto.setAnswerId(1L);

        when(userRepository.findByEmail("test@gmail.com")).thenReturn(user);
        when(answerRepository.findById(1L)).thenReturn(Optional.of(answer));
        when(chatMessageRepository.save(any(ChatMessage.class))).thenReturn(message);

        ChatMessage saved = chatService.saveMessage(dto);

        assertNotNull(saved);
        assertEquals("Hello", saved.getMessage());
    }

    @Test
    void testSaveMessage_UserNotFound() {

        ChatMessageDto dto = new ChatMessageDto();
        dto.setMessage("Hello");
        dto.setAnswerId(1L);

        when(userRepository.findByEmail("test@gmail.com")).thenReturn(null);

        assertThrows(RuntimeException.class, () -> {
            chatService.saveMessage(dto);
        });
    }

    @Test
    void testGetAllMessages() {

        when(chatMessageRepository.findAll()).thenReturn(List.of(message));

        List<ChatMessage> list = chatService.getAllMessages();

        assertEquals(1, list.size());
    }

    @Test
    void testGetMessageById() {

        when(chatMessageRepository.findById(1L)).thenReturn(Optional.of(message));

        ChatMessage result = chatService.getMessageById(1L);

        assertEquals("Hello", result.getMessage());
    }

    @Test
    void testGetMessageById_NotFound() {

        when(chatMessageRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ChatMessageNotFoundException.class, () -> {
            chatService.getMessageById(1L);
        });
    }

    @Test
    void testUpdateMessage() {

        ChatMessageDto dto = new ChatMessageDto();
        dto.setMessage("Updated Message");

        when(chatMessageRepository.findById(1L)).thenReturn(Optional.of(message));
        when(chatMessageRepository.save(any(ChatMessage.class))).thenReturn(message);

        ChatMessage updated = chatService.updateMessage(1L, dto);

        assertEquals("Updated Message", updated.getMessage());
    }

    @Test
    void testDeleteMessage() {

        when(chatMessageRepository.findById(1L)).thenReturn(Optional.of(message));

        chatService.deleteMessage(1L);

        verify(chatMessageRepository, times(1)).delete(message);
    }
}

