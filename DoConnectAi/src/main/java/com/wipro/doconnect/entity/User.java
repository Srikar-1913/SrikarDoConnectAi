/*
 * Author: Srikar Akula
 * Project: DoConnect
 * Description: Entity class for user information
 * Created Date: 17-06-2026
 */

package com.wipro.doconnect.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// Lombok annotations for constructors, getters, setters
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString

// Entity mapped to users table
@Entity
@Table(name = "users")
public class User {

    // Primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    // User name (cannot be null)
    @Column(nullable = false)
    private String name;

    // User email
    private String email;

    // User password (cannot be null)
    @Column(nullable = false)
    private String password;

    // User role (ADMIN / USER)
    @Enumerated(EnumType.STRING)
    private Role role;

    // Account creation time
    private LocalDateTime createdAt;
}