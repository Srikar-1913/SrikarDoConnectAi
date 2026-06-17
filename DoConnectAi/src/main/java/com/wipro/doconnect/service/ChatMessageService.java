/*
 * Author: Srikar Akula
 * Project: DoConnect
 * Description: Service interface for chat message operations
 * Created Date: 17-06-2026
 */

package com.wipro.doconnect.service;

import java.util.List;

import com.wipro.doconnect.dto.ChatMessageDto;
import com.wipro.doconnect.entity.ChatMessage;

// Service interface for ChatMessage operations
public interface ChatMessageService {

    // Save new message
    ChatMessage saveMessage(ChatMessageDto chatMessageDto);
    
    // Get all messages
    List<ChatMessage> getAllMessages();
    
    // Get message by ID
    ChatMessage getMessageById(Long messageId);
    
    // Update message
    ChatMessage updateMessage(Long messageid, ChatMessageDto chatMessageDto);
    
    // Delete message by ID
    void deleteMessage(Long messageId);
}