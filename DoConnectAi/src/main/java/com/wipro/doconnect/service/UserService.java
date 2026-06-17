package com.wipro.doconnect.service;

import java.util.List;

import com.wipro.doconnect.dto.UserDto;
import com.wipro.doconnect.entity.User;

public interface UserService {

    UserDto addUser(UserDto userDto);

    UserDto getUserById(Long userId);
    
    User login(String email, String password);

    List<UserDto> getAllUsers();

    UserDto updateUser(Long userId, UserDto userDto);

    String deleteUser(Long userId);

}