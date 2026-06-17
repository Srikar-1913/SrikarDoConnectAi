/*
 * Author: Srikar Akula
 * Project: DoConnect
 * Description: DTO class for user data
 * Created Date: 17-06-2026
 */

package com.wipro.doconnect.dto;

import java.time.LocalDateTime;

import com.wipro.doconnect.entity.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

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

// DTO used to transfer user data
public class UserDto {
    
    // Unique ID of user
    private Long userId;
    
    // User name (required)
    @NotBlank(message = "Name is required")
    private String name;
    
    // User email with validation
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;
    
    // User password with minimum length
    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must contain minimum 6 characters")
    private String password;
    
    // User role (required)
    @NotNull(message = "Role is required")
    private Role role;
    
    // Account creation time
    private LocalDateTime createdAt;
}
