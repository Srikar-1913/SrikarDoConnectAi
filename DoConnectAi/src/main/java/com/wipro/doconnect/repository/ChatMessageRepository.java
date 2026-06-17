/*
 * Author: Srikar Akula
 * Project: DoConnect
 * Description: Repository for chat messages
 * Created Date: 17-06-2026
 */

package com.wipro.doconnect.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wipro.doconnect.entity.Answer;
import com.wipro.doconnect.entity.ChatMessage;

// Repository interface for ChatMessage entity
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long>{

    // Delete all messages related to a specific answer
    void deleteByAnswer(Answer answer);
}