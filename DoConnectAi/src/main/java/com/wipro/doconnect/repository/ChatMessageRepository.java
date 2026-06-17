package com.wipro.doconnect.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wipro.doconnect.entity.Answer;
import com.wipro.doconnect.entity.ChatMessage;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long>{

	void deleteByAnswer(Answer answer);

}
