package com.wipro.doconnect.testservice;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.wipro.doconnect.dto.UserDto;
import com.wipro.doconnect.entity.User;
import com.wipro.doconnect.repository.UserRepository;
import com.wipro.doconnect.service.UserServiceImpl;

class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setUserId(1L);
        user.setName("Srikar");
        user.setEmail("test@gmail.com");
        user.setPassword("encodedPwd");
    }
    
    @Test
    void testAddUser() {

        when(userRepository.findByEmail(anyString())).thenReturn(null);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPwd");
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDto dto = new UserDto();
        dto.setName("Srikar");
        dto.setEmail("test@gmail.com");
        dto.setPassword("123456");

        UserDto result = userService.addUser(dto);

        assertNotNull(result);
        assertEquals("test@gmail.com", result.getEmail());
    }
    
    @Test
    void testLoginSuccess() {

        when(userRepository.findByEmail("test@gmail.com")).thenReturn(user);
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);

        User result = userService.login("test@gmail.com", "123456");

        assertNotNull(result);
    }
    
    
    @Test
    void testLoginFail() {

        when(userRepository.findByEmail("test@gmail.com")).thenReturn(user);
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        assertThrows(RuntimeException.class, () -> {
            userService.login("test@gmail.com", "wrong");
        });
    }
    
    @Test
    void testGetUserById() {

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserDto result = userService.getUserById(1L);

        assertEquals("test@gmail.com", result.getEmail());
    }
    
    @Test
    void testGetAllUsers() {

        when(userRepository.findAll()).thenReturn(List.of(user));

        List<UserDto> list = userService.getAllUsers();

        assertEquals(1, list.size());
    }
    
    @Test
    void testUpdateUser() {

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(passwordEncoder.encode(anyString())).thenReturn("encoded");
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDto dto = new UserDto();
        dto.setName("Updated");
        dto.setEmail("test@gmail.com");
        dto.setPassword("newpass");

        UserDto updated = userService.updateUser(1L, dto);

        assertEquals("Updated", updated.getName());
    }
    
    @Test
    void testDeleteUser() {

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        String result = userService.deleteUser(1L);

        assertEquals("User deleted successfully", result);
    }
}
