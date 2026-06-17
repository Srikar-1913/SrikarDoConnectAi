/*
 * Author: Srikar Akula
 * Project: DoConnect
 * Description: Implementation of User service
 * Created Date: 17-06-2026
 */

package com.wipro.doconnect.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.wipro.doconnect.dto.UserDto;
import com.wipro.doconnect.entity.Role;
import com.wipro.doconnect.entity.User;
import com.wipro.doconnect.exceptions.UserNotFoundException;
import com.wipro.doconnect.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

// Service implementation for user operations
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    // Repository for users
    @Autowired
    private UserRepository userRepository;

    // Password encoder for security
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // Register User
    @Override
    public UserDto addUser(UserDto userDto) {

        // Log registration
        log.info("Registering user with email: {}", userDto.getEmail());

        // Check if email already exists
        if (userRepository.findByEmail(userDto.getEmail()) != null) {
            throw new RuntimeException("User already exists");
        }

        User user = new User();

        // Set user details
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());

        // Encode password
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        // Set role (default USER)
        if (userDto.getRole() == null) {
            user.setRole(Role.USER);
        } else {
            user.setRole(userDto.getRole());
        }

        user.setCreatedAt(LocalDateTime.now());

        // Save user
        User savedUser = userRepository.save(user);

        // Set generated ID in DTO
        userDto.setUserId(savedUser.getUserId());

        return userDto;
    }

    // Login
    @Override
    public User login(String email, String password) {

        // Log login attempt
        log.info("User login attempt with email: {}", email);

        // Find user by email
        User user = userRepository.findByEmail(email);

        // Check user exists
        if (user == null) {
            throw new UserNotFoundException("User not found");
        }

        // Check password
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return user;
    }

    @Override
    public UserDto getUserById(Long userId) {

        // Get user by ID
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        // Convert to DTO
        return convertToDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {

        // Get all users and convert to DTO list
        return userRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto updateUser(Long userId, UserDto userDto) {

        // Log update action
        log.info("Updating user with id: {}", userId);

        // Get existing user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        // Update details
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());

        // Encode and update password
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        user.setRole(userDto.getRole());
    
        // Save updated user
        User updatedUser = userRepository.save(user);

        // Convert to DTO
        return convertToDto(updatedUser);
    }

    @Override
    public String deleteUser(Long userId) {

        // Log delete action
        log.info("Deleting user with id: {}", userId);

        // Get user by ID
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        // Delete user
        userRepository.delete(user);

        return "User deleted successfully";
    }

    // Convert User entity to DTO
    private UserDto convertToDto(User user) {

        UserDto dto = new UserDto();

        // Map fields
        dto.setUserId(user.getUserId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPassword(user.getPassword());
        dto.setRole(user.getRole());

        return dto;
    }
}