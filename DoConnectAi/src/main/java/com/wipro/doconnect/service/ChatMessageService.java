package com.wipro.doconnect.service;

import java.util.List;

import com.wipro.doconnect.dto.ChatMessageDto;
import com.wipro.doconnect.entity.ChatMessage;

public interface ChatMessageService {
	ChatMessage saveMessage(ChatMessageDto chatMessageDto);
	
	List<ChatMessage> getAllMessages();
	
	ChatMessage getMessageById(Long messageId);
	
	ChatMessage updateMessage(Long messageid, ChatMessageDto chatMessageDto);
	
	void deleteMessage(Long messageId);
}
