package com.example.methodo_de_test_eval.util;

import com.example.methodo_de_test_eval.dto.CreateUserDto;
import com.example.methodo_de_test_eval.dto.UpdateUserDto;
import com.example.methodo_de_test_eval.dto.UserDto;
import com.example.methodo_de_test_eval.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests unitaires pour UserMapper")
class UserMapperTest {

    @Test
    @DisplayName("Test toDto() - Doit convertir User en UserDto")
    void toDto_ShouldConvertUserToUserDto() {
        User user = new User();
        user.setId(1L);
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("password123");

        UserDto result = UserMapper.toDto(user);

        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
        assertEquals(user.getName(), result.getName());
        assertEquals(user.getEmail(), result.getEmail());
        // Le mot de passe ne doit pas être présent dans le DTO (pas de getter)
    }

    @Test
    @DisplayName("Test toDto() - Doit retourner null pour un User null")
    void toDto_WithNullUser_ShouldReturnNull() {
        UserDto result = UserMapper.toDto(null);
        assertNull(result);
    }

    @Test
    @DisplayName("Test toDtoList() - Doit convertir une liste de User en liste de UserDto")
    void toDtoList_ShouldConvertUserListToUserDtoList() {
        User user1 = new User();
        user1.setId(1L);
        user1.setName("John Doe");
        user1.setEmail("john.doe@example.com");
        user1.setPassword("password123");

        User user2 = new User();
        user2.setId(2L);
        user2.setName("Jane Smith");
        user2.setEmail("jane.smith@example.com");
        user2.setPassword("password456");

        List<User> users = Arrays.asList(user1, user2);

        List<UserDto> result = UserMapper.toDtoList(users);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(user1.getId(), result.get(0).getId());
        assertEquals(user1.getName(), result.get(0).getName());
        assertEquals(user1.getEmail(), result.get(0).getEmail());
        assertEquals(user2.getId(), result.get(1).getId());
        assertEquals(user2.getName(), result.get(1).getName());
        assertEquals(user2.getEmail(), result.get(1).getEmail());
    }

    @Test
    @DisplayName("Test toDtoList() - Doit retourner null pour une liste null")
    void toDtoList_WithNullList_ShouldReturnNull() {
        List<UserDto> result = UserMapper.toDtoList(null);
        assertNull(result);
    }

    @Test
    @DisplayName("Test fromCreateDto() - Doit convertir CreateUserDto en User")
    void fromCreateDto_ShouldConvertCreateUserDtoToUser() {
        CreateUserDto createUserDto = new CreateUserDto("John Doe", "john.doe@example.com", "password123");

        User result = UserMapper.fromCreateDto(createUserDto);

        assertNotNull(result);
        assertEquals(createUserDto.getName(), result.getName());
        assertEquals(createUserDto.getEmail(), result.getEmail());
        assertEquals(createUserDto.getPassword(), result.getPassword());
        assertNull(result.getId());
    }

    @Test
    @DisplayName("Test fromCreateDto() - Doit retourner null pour un CreateUserDto null")
    void fromCreateDto_WithNullDto_ShouldReturnNull() {
        User result = UserMapper.fromCreateDto(null);
        assertNull(result);
    }

    @Test
    @DisplayName("Test fromUpdateDto() - Doit convertir UpdateUserDto en User avec ID")
    void fromUpdateDto_ShouldConvertUpdateUserDtoToUserWithId() {
        UpdateUserDto updateUserDto = new UpdateUserDto("John Updated", "john.updated@example.com", "newpassword123");
        Long userId = 1L;

        User result = UserMapper.fromUpdateDto(updateUserDto, userId);

        assertNotNull(result);
        assertEquals(userId, result.getId());
        assertEquals(updateUserDto.getName(), result.getName());
        assertEquals(updateUserDto.getEmail(), result.getEmail());
        assertEquals(updateUserDto.getPassword(), result.getPassword());
    }

    @Test
    @DisplayName("Test fromUpdateDto() - Doit gérer un mot de passe null")
    void fromUpdateDto_WithNullPassword_ShouldHandleNullPassword() {
        UpdateUserDto updateUserDto = new UpdateUserDto("John Updated", "john.updated@example.com", null);
        Long userId = 1L;

        User result = UserMapper.fromUpdateDto(updateUserDto, userId);

        assertNotNull(result);
        assertEquals(userId, result.getId());
        assertEquals(updateUserDto.getName(), result.getName());
        assertEquals(updateUserDto.getEmail(), result.getEmail());
        assertNull(result.getPassword());
    }

    @Test
    @DisplayName("Test fromUpdateDto() - Doit retourner null pour un UpdateUserDto null")
    void fromUpdateDto_WithNullDto_ShouldReturnNull() {
        User result = UserMapper.fromUpdateDto(null, 1L);
        assertNull(result);
    }

    @Test
    @DisplayName("Test updateUserFromDto() - Doit mettre à jour un User existant")
    void updateUserFromDto_ShouldUpdateExistingUser() {
        User existingUser = new User();
        existingUser.setId(1L);
        existingUser.setName("John Doe");
        existingUser.setEmail("john.doe@example.com");
        existingUser.setPassword("oldpassword");

        UpdateUserDto updateUserDto = new UpdateUserDto("John Updated", "john.updated@example.com", "newpassword123");

        UserMapper.updateUserFromDto(existingUser, updateUserDto);

        assertEquals(1L, existingUser.getId());
        assertEquals(updateUserDto.getName(), existingUser.getName());
        assertEquals(updateUserDto.getEmail(), existingUser.getEmail());
        assertEquals(updateUserDto.getPassword(), existingUser.getPassword());
    }

    @Test
    @DisplayName("Test updateUserFromDto() - Doit gérer un mot de passe vide")
    void updateUserFromDto_WithEmptyPassword_ShouldNotUpdatePassword() {
        User existingUser = new User();
        existingUser.setId(1L);
        existingUser.setName("John Doe");
        existingUser.setEmail("john.doe@example.com");
        existingUser.setPassword("oldpassword");

        UpdateUserDto updateUserDto = new UpdateUserDto("John Updated", "john.updated@example.com", "");

        UserMapper.updateUserFromDto(existingUser, updateUserDto);

        assertEquals("oldpassword", existingUser.getPassword());
    }

    @Test
    @DisplayName("Test updateUserFromDto() - Doit gérer des paramètres null")
    void updateUserFromDto_WithNullParameters_ShouldHandleGracefully() {
        User existingUser = new User();
        existingUser.setId(1L);
        existingUser.setName("John Doe");
        existingUser.setEmail("john.doe@example.com");
        existingUser.setPassword("password");

        UserMapper.updateUserFromDto(null, new UpdateUserDto("Test", "test@example.com", "password"));

        UserMapper.updateUserFromDto(existingUser, null);
        assertEquals("John Doe", existingUser.getName());
        assertEquals("john.doe@example.com", existingUser.getEmail());
        assertEquals("password", existingUser.getPassword());
    }
} 