package com.wipro.doconnect.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.doconnect.dto.ChatMessageDto;
import com.wipro.doconnect.entity.ChatMessage;
import com.wipro.doconnect.service.ChatMessageService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/chatmessages")
@Slf4j
public class ChatMessageController {

	@Autowired
	private ChatMessageService chatMessageService;

	@PostMapping("/save")
	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	public ChatMessage saveMessage(@RequestBody ChatMessageDto chatMessageDto) {
		log.info("POST / message added successfully");

		return chatMessageService.saveMessage(chatMessageDto);
	}

	@GetMapping("/getAll")
	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	public List<ChatMessage> getAllMessages() {
		log.info("GET / retrived all messages successfully");

		return chatMessageService.getAllMessages();
	}

	@GetMapping("/get/{messageId}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ChatMessage getMessageById(@PathVariable Long messageId) {
		log.info("GET / retrived message by id");

		return chatMessageService.getMessageById(messageId);
	}

	@PutMapping("/update/{messageId}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ChatMessage updateMessage(@PathVariable Long messageId, @RequestBody ChatMessageDto chatMessageDto) {
		log.info("PUT / message updated successfully");

		return chatMessageService.updateMessage(messageId, chatMessageDto);
	}

	@DeleteMapping("/delete/{messageId}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public String deleteMessage(@PathVariable Long messageId) {
		log.info("POST / message deleted successfully");

		chatMessageService.deleteMessage(messageId);

		return "Message Deleted Successfully";
	}
}
