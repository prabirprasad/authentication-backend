package com.authentication.services;

import com.authentication.dtos.UserDto;

public interface AuthService {

    UserDto registerUser(UserDto userDto);
}
