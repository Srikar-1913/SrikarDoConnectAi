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

@Service
@Slf4j
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	// Register User
	@Override
	public UserDto addUser(UserDto userDto) {
		log.info("Registering user with email: {}", userDto.getEmail());

		// check if email already exists
		if (userRepository.findByEmail(userDto.getEmail()) != null) {
			throw new RuntimeException("User already exists");
		}

		User user = new User();

		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());

		// encode password
		user.setPassword(passwordEncoder.encode(userDto.getPassword()));

		if (userDto.getRole() == null) {
			user.setRole(Role.USER);
		} else {
			user.setRole(userDto.getRole());
		}

		user.setCreatedAt(LocalDateTime.now());

		User savedUser = userRepository.save(user);

		userDto.setUserId(savedUser.getUserId());

		return userDto;
	}

	// Login
	@Override
	public User login(String email, String password) {
		log.info("User login attempt with email: {}", email);

		User user = userRepository.findByEmail(email);

		if (user == null) {
			throw new UserNotFoundException("User not found");
		}

		if (!passwordEncoder.matches(password, user.getPassword())) {
			throw new RuntimeException("Invalid credentials");
		}

		return user;
	}

	@Override
	public UserDto getUserById(Long userId) {

		User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));

		return convertToDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {

		return userRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
	}

	@Override
	public UserDto updateUser(Long userId, UserDto userDto) {
		log.info("Updating user with id: {}", userId);

		User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));

		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());

		user.setPassword(passwordEncoder.encode(userDto.getPassword()));
		user.setRole(userDto.getRole());
	

		User updatedUser = userRepository.save(user);

		return convertToDto(updatedUser);
	}

	@Override
	public String deleteUser(Long userId) {
		log.info("Deleting user with id: {}", userId);

		User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));

		userRepository.delete(user);

		return "User deleted successfully";
	}

	private UserDto convertToDto(User user) {

		UserDto dto = new UserDto();

		dto.setUserId(user.getUserId());
		dto.setName(user.getName());
		dto.setEmail(user.getEmail());
		dto.setPassword(user.getPassword());
		dto.setRole(user.getRole());

		return dto;
	}
}