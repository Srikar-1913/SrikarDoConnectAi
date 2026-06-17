/*
 * Author: Srikar Akula
 * Project: DoConnect
 * Description: Controller for ChatMessage APIs
 * Created Date: 17-06-2026
 */

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

// REST controller for ChatMessage operations
@RestController
@RequestMapping("/chatmessages")
@Slf4j
public class ChatMessageController {

    // Service to handle message logic
    @Autowired
    private ChatMessageService chatMessageService;

    // Create new message
    @PostMapping("/save")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ChatMessage saveMessage(@RequestBody ChatMessageDto chatMessageDto) {

        // Log action
        log.info("POST / message added successfully");

        return chatMessageService.saveMessage(chatMessageDto);
    }

    // Get all messages
    @GetMapping("/getAll")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public List<ChatMessage> getAllMessages() {

        // Log action
        log.info("GET / retrieved all messages successfully");

        return chatMessageService.getAllMessages();
    }

    // Get message by ID (admin only)
    @GetMapping("/get/{messageId}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ChatMessage getMessageById(@PathVariable Long messageId) {

        // Log action
        log.info("GET / retrieved message by id");

        return chatMessageService.getMessageById(messageId);
    }

    // Update message (admin only)
    @PutMapping("/update/{messageId}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ChatMessage updateMessage(@PathVariable Long messageId, @RequestBody ChatMessageDto chatMessageDto) {

        // Log action
        log.info("PUT / message updated successfully");

        return chatMessageService.updateMessage(messageId, chatMessageDto);
    }

    // Delete message (admin only)
    @DeleteMapping("/delete/{messageId}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public String deleteMessage(@PathVariable Long messageId) {

        // Log action
        log.info("DELETE / message deleted successfully");

        chatMessageService.deleteMessage(messageId);

        return "Message Deleted Successfully";
    }
}