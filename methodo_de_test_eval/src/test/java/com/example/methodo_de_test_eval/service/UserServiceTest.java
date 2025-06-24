package com.example.methodo_de_test_eval.service;

import com.example.methodo_de_test_eval.dto.CreateUserDto;
import com.example.methodo_de_test_eval.dto.UpdateUserDto;
import com.example.methodo_de_test_eval.dto.UserDto;
import com.example.methodo_de_test_eval.entity.User;
import com.example.methodo_de_test_eval.exception.DataIntegrityViolationException;
import com.example.methodo_de_test_eval.exception.ObjectNotFoundException;
import com.example.methodo_de_test_eval.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@DisplayName("Tests unitaires pour UserService")
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private User validUser;
    private User anotherUser;
    private UserDto validUserDto;
    private UserDto anotherUserDto;
    private CreateUserDto createUserDto;
    private UpdateUserDto updateUserDto;

    @BeforeEach
    void setUp() {
        validUser = new User();
        validUser.setId(1L);
        validUser.setName("John Doe");
        validUser.setEmail("john.doe@example.com");
        validUser.setPassword("password123");

        anotherUser = new User();
        anotherUser.setId(2L);
        anotherUser.setName("Jane Smith");
        anotherUser.setEmail("jane.smith@example.com");
        anotherUser.setPassword("password456");

        validUserDto = new UserDto(1L, "John Doe", "john.doe@example.com");
        anotherUserDto = new UserDto(2L, "Jane Smith", "jane.smith@example.com");

        createUserDto = new CreateUserDto("John Doe", "john.doe@example.com", "password123");
        updateUserDto = new UpdateUserDto("John Updated", "john.updated@example.com", "newpassword123");
    }

    @Test
    @DisplayName("Test 1: getAllUsers() - Doit retourner la liste de tous les utilisateurs")
    void getAllUsers_ShouldReturnAllUsers() {
        List<User> expectedUsers = Arrays.asList(validUser, anotherUser);
        when(userRepository.findAll()).thenReturn(expectedUsers);

        List<UserDto> actualUsers = userService.getAllUsers();

        assertNotNull(actualUsers);
        assertEquals(2, actualUsers.size());
        assertEquals(validUserDto.getId(), actualUsers.get(0).getId());
        assertEquals(validUserDto.getName(), actualUsers.get(0).getName());
        assertEquals(validUserDto.getEmail(), actualUsers.get(0).getEmail());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Test 2: getUserById() - Doit retourner un utilisateur existant")
    void getUserById_WithValidId_ShouldReturnUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(validUser));

        Optional<UserDto> result = userService.getUserById(1L);

        assertTrue(result.isPresent());
        assertEquals(validUserDto.getId(), result.get().getId());
        assertEquals(validUserDto.getName(), result.get().getName());
        assertEquals(validUserDto.getEmail(), result.get().getEmail());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Test 3: getUserById() - Doit retourner empty pour un ID invalide")
    void getUserById_WithInvalidId_ShouldReturnEmpty() {
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<UserDto> result = userService.getUserById(999L);

        assertFalse(result.isPresent());
        verify(userRepository, times(1)).findById(999L);
    }

    @Test
    @DisplayName("Test 4: getUserById() - Doit lever une exception pour un ID null")
    void getUserById_WithNullId_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> {
            userService.getUserById(null);
        });
        verify(userRepository, never()).findById(any());
    }

    @Test
    @DisplayName("Test 5: createUser() - Doit créer un utilisateur valide")
    void createUser_WithValidUser_ShouldCreateUser() {
        when(userRepository.existsByEmail(createUserDto.getEmail())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(validUser);

        UserDto createdUser = userService.createUser(createUserDto);

        assertNotNull(createdUser);
        assertEquals(validUserDto.getName(), createdUser.getName());
        assertEquals(validUserDto.getEmail(), createdUser.getEmail());
        verify(userRepository, times(1)).existsByEmail(createUserDto.getEmail());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("Test 6: createUser() - Doit lever une exception si l'email existe déjà")
    void createUser_WithExistingEmail_ShouldThrowDataIntegrityViolationException() {
        when(userRepository.existsByEmail(createUserDto.getEmail())).thenReturn(true);
        DataIntegrityViolationException exception = assertThrows(
            DataIntegrityViolationException.class,
            () -> userService.createUser(createUserDto)
        );
        
        assertTrue(exception.getMessage().contains("existe déjà"));
        verify(userRepository, times(1)).existsByEmail(createUserDto.getEmail());
        verify(userRepository, never()).save(any());
    }

    @Test
    @DisplayName("Test 7: updateUser() - Doit mettre à jour un utilisateur existant")
    void updateUser_WithValidUser_ShouldUpdateUser() {
        User updatedUser = new User();
        updatedUser.setId(1L);
        updatedUser.setName("John Updated");
        updatedUser.setEmail("john.updated@example.com");
        updatedUser.setPassword("newpassword123");

        when(userRepository.findById(1L)).thenReturn(Optional.of(validUser));
        when(userRepository.existsByEmail(updateUserDto.getEmail())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        UserDto result = userService.updateUser(1L, updateUserDto);

        assertNotNull(result);
        assertEquals(updateUserDto.getName(), result.getName());
        assertEquals(updateUserDto.getEmail(), result.getEmail());
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).existsByEmail(updateUserDto.getEmail());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("Test 8: updateUser() - Doit lever une exception si l'utilisateur n'existe pas")
    void updateUser_WithNonExistentUser_ShouldThrowObjectNotFoundException() {
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        ObjectNotFoundException exception = assertThrows(
            ObjectNotFoundException.class,
            () -> userService.updateUser(999L, updateUserDto)
        );
        
        assertTrue(exception.getMessage().contains("non trouvé"));
        verify(userRepository, times(1)).findById(999L);
        verify(userRepository, never()).save(any());
    }

    @Test
    @DisplayName("Test 9: deleteUser() - Doit supprimer un utilisateur existant")
    void deleteUser_WithValidId_ShouldDeleteUser() {
        when(userRepository.existsById(1L)).thenReturn(true);
        doNothing().when(userRepository).deleteById(1L);

        userService.deleteUser(1L);

        verify(userRepository, times(1)).existsById(1L);
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Test 10: deleteUser() - Doit lever une exception si l'utilisateur n'existe pas")
    void deleteUser_WithNonExistentId_ShouldThrowObjectNotFoundException() {
        when(userRepository.existsById(999L)).thenReturn(false);

        ObjectNotFoundException exception = assertThrows(
            ObjectNotFoundException.class,
            () -> userService.deleteUser(999L)
        );
        
        assertTrue(exception.getMessage().contains("non trouvé"));
        verify(userRepository, times(1)).existsById(999L);
        verify(userRepository, never()).deleteById(anyLong());
    }

    @Test
    @DisplayName("Test 11: getUserByEmail() - Doit retourner un utilisateur par email")
    void getUserByEmail_WithValidEmail_ShouldReturnUser() {
        when(userRepository.findByEmail(validUser.getEmail())).thenReturn(Optional.of(validUser));

        Optional<UserDto> result = userService.getUserByEmail(validUser.getEmail());

        assertTrue(result.isPresent());
        assertEquals(validUserDto.getId(), result.get().getId());
        assertEquals(validUserDto.getName(), result.get().getName());
        assertEquals(validUserDto.getEmail(), result.get().getEmail());
        verify(userRepository, times(1)).findByEmail(validUser.getEmail());
    }

    @Test
    @DisplayName("Test 12: emailExists() - Doit retourner true si l'email existe")
    void emailExists_WithExistingEmail_ShouldReturnTrue() {
        when(userRepository.existsByEmail(validUser.getEmail())).thenReturn(true);
        boolean exists = userService.emailExists(validUser.getEmail());

        assertTrue(exists);
        verify(userRepository, times(1)).existsByEmail(validUser.getEmail());
    }

    @Test
    @DisplayName("Test 13: emailExists() - Doit retourner false si l'email n'existe pas")
    void emailExists_WithNonExistingEmail_ShouldReturnFalse() {
        when(userRepository.existsByEmail("nonexistent@example.com")).thenReturn(false);

        boolean exists = userService.emailExists("nonexistent@example.com");

        assertFalse(exists);
        verify(userRepository, times(1)).existsByEmail("nonexistent@example.com");
    }

    @Test
    @DisplayName("Test 14: userExists() - Doit retourner true si l'utilisateur existe")
    void userExists_WithExistingId_ShouldReturnTrue() {
        when(userRepository.existsById(1L)).thenReturn(true);

        boolean exists = userService.userExists(1L);

        assertTrue(exists);
        verify(userRepository, times(1)).existsById(1L);
    }

    @Test
    @DisplayName("Test 15: userExists() - Doit retourner false si l'utilisateur n'existe pas")
    void userExists_WithNonExistingId_ShouldReturnFalse() {
        when(userRepository.existsById(999L)).thenReturn(false);

        boolean exists = userService.userExists(999L);

        assertFalse(exists);
        verify(userRepository, times(1)).existsById(999L);
    }

    @Test
    @DisplayName("Test BONUS: updateUser() - Doit lever une exception si le nouvel email existe déjà")
    void updateUser_WithExistingEmail_ShouldThrowDataIntegrityViolationException() {
        User existingUser = new User();
        existingUser.setId(1L);
        existingUser.setName("John Doe");
        existingUser.setEmail("john.doe@example.com");
        existingUser.setPassword("password123");

        UpdateUserDto updateUserDto = new UpdateUserDto("John Updated", "jane.smith@example.com", "newpassword123");

        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(userRepository.existsByEmail("jane.smith@example.com")).thenReturn(true);

        DataIntegrityViolationException exception = assertThrows(
            DataIntegrityViolationException.class,
            () -> userService.updateUser(1L, updateUserDto)
        );
        
        assertTrue(exception.getMessage().contains("existe déjà"));
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).existsByEmail("jane.smith@example.com");
        verify(userRepository, never()).save(any());
    }

    @Test
    @DisplayName("Test 16: Validation des données - Doit lever une exception pour un utilisateur null")
    void createUser_WithNullUser_ShouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            userService.createUser(null);
        });
        verify(userRepository, never()).existsByEmail(anyString());
        verify(userRepository, never()).save(any());
    }

    @Test
    @DisplayName("Test 17: Validation des données - Doit lever une exception pour un nom vide")
    void createUser_WithEmptyName_ShouldThrowIllegalArgumentException() {       
        CreateUserDto invalidUser = new CreateUserDto("", "test@example.com", "password123");

        assertThrows(IllegalArgumentException.class, () -> {
            userService.createUser(invalidUser);
        });
        verify(userRepository, never()).existsByEmail(anyString());
        verify(userRepository, never()).save(any());
    }

    @Test
    @DisplayName("Test 18: Validation des données - Doit lever une exception pour un email sans .com")
    void createUser_WithInvalidEmail_ShouldThrowIllegalArgumentException() {
        CreateUserDto invalidUser = new CreateUserDto("John Doe", "john.doe@example.org", "password123");

        assertThrows(IllegalArgumentException.class, () -> {
            userService.createUser(invalidUser);
        });
        verify(userRepository, never()).existsByEmail(anyString());
        verify(userRepository, never()).save(any());
    }
} 