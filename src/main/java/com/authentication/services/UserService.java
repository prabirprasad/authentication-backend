package com.authentication.services;

import com.authentication.dtos.UserDto;

public interface UserService {

    UserDto createUser(UserDto userDto);

    UserDto updateUser(UserDto userDto, String userId);

    void deleteUser(UserDto userDto);

    UserDto getUserByUsername(String username);

    UserDto getByUserId(String userId);

    Iterable<UserDto> getAllUsers();
}
