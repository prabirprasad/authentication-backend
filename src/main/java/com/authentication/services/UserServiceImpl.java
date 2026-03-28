package com.authentication.services;

import com.authentication.dtos.UserDto;
import com.authentication.entities.ProviderType;
import com.authentication.entities.User;
import com.authentication.exceptions.ResourceNotFound;
import com.authentication.helpers.UserHelper;
import com.authentication.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public UserDto createUser(UserDto userDto) {
        if(userDto.getUsername() == null || userDto.getUsername().isBlank()) {
            throw new IllegalArgumentException("Email is required");
        }

        if(userRepository.existsByUsername(userDto.getUsername())) {
            throw new IllegalArgumentException("Username is already in use");
        }

        User user = modelMapper.map(userDto, User.class);
        user.setProviderType(userDto.getProviderType() != null ? userDto.getProviderType() : ProviderType.LOCAL);
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    public UserDto updateUser(UserDto userDto, String userId) {
        UUID uId = UserHelper.parseUUID(userId);
        User existingUser = userRepository
                .findById(uId)
                .orElseThrow(() -> new ResourceNotFound("User not found with given id"));

        if(userDto.getName() != null) existingUser.setName(userDto.getName());
        if(userDto.getPassword() != null) existingUser.setPassword(userDto.getPassword());
        if(userDto.getImage() != null) existingUser.setImage(userDto.getImage());
        if(userDto.getProviderType() != null) existingUser.setProviderType(userDto.getProviderType());
        existingUser.setEnabled(userDto.isEnabled());
        existingUser.setUpdatedAt(Instant.now());

        User updatedUser = userRepository.save(existingUser);

        return modelMapper.map(updatedUser, UserDto.class);
    }

    @Override
    public void deleteUser(String userId) {
        UUID uId = UserHelper.parseUUID(userId);
        User user = userRepository
                .findById(uId)
                .orElseThrow(() -> new ResourceNotFound("User not found with given id: "));
        userRepository.delete(user);
    }

    @Override
    public UserDto getUserByUsername(String username) {
        User user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new ResourceNotFound("Username not found"));
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public UserDto getByUserId(String userId) {
        UUID uId = UserHelper.parseUUID(userId);
        User user = userRepository
                .findById(uId)
                .orElseThrow(() -> new ResourceNotFound("User not found with id: "));
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public Iterable<UserDto> getAllUsers() {
        return userRepository
                .findAll()
                .stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .toList();
    }
}
