/*
 * Author: Srikar Akula
 * Project: DoConnect
 * Description: Entity class for chat messages
 * Created Date: 17-06-2026
 */

package com.wipro.doconnect.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// Lombok annotations for constructors, getters, setters
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString

// Entity mapped to chat_messages table
@Entity
@Table(name = "chat_messages")

public class ChatMessage {

    // Primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long messageId;

    // Message content
    private String message;

    // Time when message was sent
    private LocalDateTime sentAt;

    // Many messages belong to one user
    @ManyToOne
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private User user;

    // Many messages belong to one answer
    @ManyToOne
    @JoinColumn(name = "answer_id")
    @ToString.Exclude
    private Answer answer;
}