package com.wipro.doconnect.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.doconnect.dto.UserDto;
import com.wipro.doconnect.entity.User;
import com.wipro.doconnect.security.JwtUtil;
import com.wipro.doconnect.service.UserServiceImpl;

import lombok.extern.slf4j.Slf4j;

@CrossOrigin
@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

	@Autowired
	private UserServiceImpl userService;

	@Autowired
	private JwtUtil jwtUtil;

	// REGISTER
	@PostMapping("/register")
	public UserDto register(@RequestBody UserDto userDto) {
		log.info("POST / user added successfully");
		return userService.addUser(userDto);
	}

	// LOGIN → JWT TOKEN
	@PostMapping("/login")
	public Map<String, String> login(@RequestBody UserDto dto) {
		log.info("POST / user logged in successfully");

		User user = userService.login(dto.getEmail(), dto.getPassword());

		String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());

		Map<String, String> response = new HashMap<>();
		response.put("token", token);
		response.put("role", user.getRole().name());
		response.put("email", user.getEmail());

		return response;
	}

	// ADMIN ONLY
	@GetMapping("/all")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<UserDto> getAllUsers() {
		log.info("GET / retrived all users successfully");
		
		return userService.getAllUsers();
	}

	// AUTHENTICATED USERS
	@GetMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	public UserDto getUser(@PathVariable Long id) {
		log.info("GET / retrived user by id");
		
		return userService.getUserById(id);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	public UserDto updateUser(@PathVariable Long id, @RequestBody UserDto dto) {
		log.info("PUT / user updated successfully");
		
		return userService.updateUser(id, dto);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public String deleteUser(@PathVariable Long id) {
		log.info("POST / user deleted successfully");
		
		return userService.deleteUser(id);
	}
}