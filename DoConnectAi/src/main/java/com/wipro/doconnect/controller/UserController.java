/*
 * Author: Srikar Akula
 * Project: DoConnect
 * Description: Controller for user authentication and user management
 * Created Date: 17-06-2026
 */

package com.wipro.doconnect.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.wipro.doconnect.dto.UserDto;
import com.wipro.doconnect.entity.User;
import com.wipro.doconnect.security.JwtUtil;
import com.wipro.doconnect.service.UserServiceImpl;

import lombok.extern.slf4j.Slf4j;

// REST controller for User operations
@CrossOrigin
@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

	// Service for user operations
	@Autowired
	private UserServiceImpl userService;

	// JWT utility for token generation
	@Autowired
	private JwtUtil jwtUtil;

	// Register new user
	@PostMapping("/register")
	public UserDto register(@RequestBody UserDto userDto) {

		// Log action
		log.info("POST / user added successfully");

		return userService.addUser(userDto);
	}

	// Login user and generate JWT token
	@PostMapping("/login")
	public Map<String, String> login(@RequestBody UserDto dto) {

		// Log action
		log.info("POST / user logged in successfully");

		// Authenticate user
		User user = userService.login(dto.getEmail(), dto.getPassword());

		// Generate token
		String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());

		// Prepare response
		Map<String, String> response = new HashMap<>();
		response.put("token", token);
		response.put("role", user.getRole().name());
		response.put("email", user.getEmail());

		response.put("name", user.getName());
		response.put("userId", user.getUserId().toString());

		return response;
	}

	// Get all users (admin only)
	@GetMapping("/all")
	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	public List<UserDto> getAllUsers() {

		// Log action
		log.info("GET / retrieved all users successfully");

		return userService.getAllUsers();
	}

	// Get user by ID
	@GetMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	public UserDto getUser(@PathVariable Long id) {

		// Log action
		log.info("GET / retrieved user by id");

		return userService.getUserById(id);
	}

	// Update user details
	@PutMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	public UserDto updateUser(@PathVariable Long id, @RequestBody UserDto dto) {

		// Log action
		log.info("PUT / user updated successfully");

		return userService.updateUser(id, dto);
	}

	// Delete user (admin only)
	@DeleteMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public String deleteUser(@PathVariable Long id) {

		// Log action
		log.info("DELETE / user deleted successfully");

		return userService.deleteUser(id);
	}
}