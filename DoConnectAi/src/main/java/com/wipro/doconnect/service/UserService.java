/*
 * Author: Srikar Akula
 * Project: DoConnect
 * Description: Service interface for user operations
 * Created Date: 17-06-2026
 */

package com.wipro.doconnect.service;

import java.util.List;

import com.wipro.doconnect.dto.UserDto;
import com.wipro.doconnect.entity.User;

// Service interface for User operations
public interface UserService {

    // Register new user
    UserDto addUser(UserDto userDto);

    // Get user by ID
    UserDto getUserById(Long userId);
    
    // Login user with email and password
    User login(String email, String password);

    // Get all users
    List<UserDto> getAllUsers();

    // Update user details
    UserDto updateUser(Long userId, UserDto userDto);

    // Delete user by ID
    String deleteUser(Long userId);
}
