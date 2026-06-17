/*
 * Author: Srikar Akula
 * Project: DoConnect
 * Description: Implementation of ChatMessage service
 * Created Date: 17-06-2026
 */

package com.wipro.doconnect.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.wipro.doconnect.dto.ChatMessageDto;
import com.wipro.doconnect.entity.Answer;
import com.wipro.doconnect.entity.ChatMessage;
import com.wipro.doconnect.entity.User;
import com.wipro.doconnect.exceptions.AnswerNotFoundException;
import com.wipro.doconnect.exceptions.ChatMessageNotFoundException;
import com.wipro.doconnect.exceptions.UserNotFoundException;
import com.wipro.doconnect.repository.AnswerRepository;
import com.wipro.doconnect.repository.ChatMessageRepository;
import com.wipro.doconnect.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

// Service implementation for chat message operations
@Service
@Slf4j
public class ChatMessageServiceImpl implements ChatMessageService {

    // Repository for chat messages
    @Autowired
    private ChatMessageRepository chatMessageRepository;

    // Repository for user
    @Autowired
    private UserRepository userRepository;

    // Repository for answer
    @Autowired
    private AnswerRepository answerRepository;

    @Override
    public ChatMessage saveMessage(ChatMessageDto chatMessageDto) {

        // Log save action
        log.info("Saving chat message for answerId: {}", chatMessageDto.getAnswerId());

        ChatMessage chatMessage = new ChatMessage();

        // Set message content and time
        chatMessage.setMessage(chatMessageDto.getMessage());
        chatMessage.setSentAt(LocalDateTime.now());

        // Get logged-in user email
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        // Find user by email
        User user = userRepository.findByEmail(email);

        // Check user exists
        if (user == null) {
            throw new UserNotFoundException("User not found with email: " + email);
        }

        chatMessage.setUser(user);

        // Get answer by ID
        Answer answer = answerRepository.findById(chatMessageDto.getAnswerId()).orElseThrow(
                () -> new AnswerNotFoundException("Answer not found with id: " + chatMessageDto.getAnswerId()));

        chatMessage.setAnswer(answer);

        // Save message
        return chatMessageRepository.save(chatMessage);
    }

    @Override
    public List<ChatMessage> getAllMessages() {

        // Get all messages
        return chatMessageRepository.findAll();
    }

    @Override
    public ChatMessage getMessageById(Long messageId) {

        // Get message by ID or throw exception
        return chatMessageRepository.findById(messageId)
                .orElseThrow(() -> new ChatMessageNotFoundException("Message not found with id : " + messageId));
    }

    @Override
    public ChatMessage updateMessage(Long messageId, ChatMessageDto chatMessageDto) {

        // Log update action
        log.info("Updating chat message with id: {}", messageId);

        // Get existing message
        ChatMessage existingMessage = chatMessageRepository.findById(messageId)
                .orElseThrow(() -> new ChatMessageNotFoundException("Message not found with id : " + messageId));

        // Update message content
        existingMessage.setMessage(chatMessageDto.getMessage());

        // Save updated message
        return chatMessageRepository.save(existingMessage);
    }

    @Override
    public void deleteMessage(Long messageId) {

        // Log delete action
        log.info("Deleting chat message with id: {}", messageId);

        // Get message by ID
        ChatMessage chatMessage = chatMessageRepository.findById(messageId)
                .orElseThrow(() -> new ChatMessageNotFoundException("Message not found with id : " + messageId));

        // Delete message
        chatMessageRepository.delete(chatMessage);
    }
}
