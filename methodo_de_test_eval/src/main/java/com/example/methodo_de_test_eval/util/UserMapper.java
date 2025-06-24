package com.example.methodo_de_test_eval.util;

import com.example.methodo_de_test_eval.dto.CreateUserDto;
import com.example.methodo_de_test_eval.dto.UpdateUserDto;
import com.example.methodo_de_test_eval.dto.UserDto;
import com.example.methodo_de_test_eval.entity.User;

import java.util.List;
import java.util.stream.Collectors;


public class UserMapper {


    public static UserDto toDto(User user) {
        if (user == null) {
            return null;
        }
        
        return new UserDto(
            user.getId(),
            user.getName(),
            user.getEmail()
        );
    }


    public static List<UserDto> toDtoList(List<User> users) {
        if (users == null) {
            return null;
        }
        
        return users.stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toList());
    }

    public static User fromCreateDto(CreateUserDto createUserDto) {
        if (createUserDto == null) {
            return null;
        }
        
        User user = new User();
        user.setName(createUserDto.getName());
        user.setEmail(createUserDto.getEmail());
        user.setPassword(createUserDto.getPassword());
        
        return user;
    }

    
    public static User fromUpdateDto(UpdateUserDto updateUserDto, Long userId) {
        if (updateUserDto == null) {
            return null;
        }
        
        User user = new User();
        user.setId(userId);
        user.setName(updateUserDto.getName());
        user.setEmail(updateUserDto.getEmail());
        
        if (updateUserDto.getPassword() != null && !updateUserDto.getPassword().trim().isEmpty()) {
            user.setPassword(updateUserDto.getPassword());
        }
        
        return user;
    }


    public static void updateUserFromDto(User existingUser, UpdateUserDto updateUserDto) {
        if (existingUser == null || updateUserDto == null) {
            return;
        }
        
        existingUser.setName(updateUserDto.getName());
        existingUser.setEmail(updateUserDto.getEmail());
        
        if (updateUserDto.getPassword() != null && !updateUserDto.getPassword().trim().isEmpty()) {
            existingUser.setPassword(updateUserDto.getPassword());
        }
    }
} 