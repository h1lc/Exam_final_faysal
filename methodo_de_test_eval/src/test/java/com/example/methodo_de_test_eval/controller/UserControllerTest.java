package com.example.methodo_de_test_eval.controller;

import com.example.methodo_de_test_eval.entity.User;
import com.example.methodo_de_test_eval.exception.DataIntegrityViolationException;
import com.example.methodo_de_test_eval.exception.ObjectNotFoundException;
import com.example.methodo_de_test_eval.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(MockitoExtension.class)
@DisplayName("Tests unitaires pour UserController")
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    private User validUser;
    private User anotherUser;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        objectMapper = new ObjectMapper();

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
    }

    @Test
    @DisplayName("Test GET /users - Doit retourner la liste de tous les utilisateurs")
    void getAllUsers_ShouldReturnAllUsers() throws Exception {
        List<User> users = Arrays.asList(validUser, anotherUser);
        when(userService.getAllUsers()).thenReturn(users);

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("John Doe"))
                .andExpect(jsonPath("$[0].email").value("john.doe@example.com"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Jane Smith"));

        verify(userService, times(1)).getAllUsers();
    }

    @Test
    @DisplayName("Test GET /users/{id} - Doit retourner un utilisateur existant")
    void getUserById_WithValidId_ShouldReturnUser() throws Exception {
        when(userService.getUserById(1L)).thenReturn(Optional.of(validUser));

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));

        verify(userService, times(1)).getUserById(1L);
    }

    @Test
    @DisplayName("Test GET /users/{id} - Doit retourner 404 pour un utilisateur inexistant")
    void getUserById_WithInvalidId_ShouldReturn404() throws Exception {
        when(userService.getUserById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/users/999"))
                .andExpect(status().isNotFound());

        verify(userService, times(1)).getUserById(999L);
    }

    @Test
    @DisplayName("Test POST /users - Doit créer un utilisateur valide")
    void createUser_WithValidUser_ShouldCreateUser() throws Exception {
        when(userService.createUser(any(User.class))).thenReturn(validUser);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validUser)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));

        verify(userService, times(1)).createUser(any(User.class));
    }

    @Test
    @DisplayName("Test POST /users - Doit retourner 409 pour un email déjà existant")
    void createUser_WithExistingEmail_ShouldReturn409() throws Exception {
        when(userService.createUser(any(User.class)))
                .thenThrow(new DataIntegrityViolationException("Un utilisateur avec cet email existe déjà"));

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validUser)))
                .andExpect(status().isConflict())
                .andExpect(content().string("Un utilisateur avec cet email existe déjà"));

        verify(userService, times(1)).createUser(any(User.class));
    }

    @Test
    @DisplayName("Test POST /users - Doit retourner 400 pour des données invalides")
    void createUser_WithInvalidData_ShouldReturn400() throws Exception {
        User invalidUser = new User();
        invalidUser.setName("");
        invalidUser.setEmail("invalid-email");
        invalidUser.setPassword("ab");

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidUser)))
                .andExpect(status().isBadRequest());

        verify(userService, never()).createUser(any(User.class));
    }

    @Test
    @DisplayName("Test PUT /users/{id} - Doit mettre à jour un utilisateur existant")
    void updateUser_WithValidUser_ShouldUpdateUser() throws Exception { 
        User updatedUser = new User();
        updatedUser.setName("John Updated");
        updatedUser.setEmail("john.updated@example.com");
        updatedUser.setPassword("newpassword123");

        when(userService.updateUser(anyLong(), any(User.class))).thenReturn(updatedUser);

        mockMvc.perform(put("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedUser)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("John Updated"))
                .andExpect(jsonPath("$.email").value("john.updated@example.com"));

        verify(userService, times(1)).updateUser(1L, updatedUser);
    }

    @Test
    @DisplayName("Test PUT /users/{id} - Doit retourner 404 pour un utilisateur inexistant")
    void updateUser_WithNonExistentUser_ShouldReturn404() throws Exception {
        when(userService.updateUser(anyLong(), any(User.class)))
                .thenThrow(new ObjectNotFoundException("Utilisateur non trouvé avec l'ID: 999"));

        mockMvc.perform(put("/users/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validUser)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Utilisateur non trouvé avec l'ID: 999"));

        verify(userService, times(1)).updateUser(999L, validUser);
    }

    @Test
    @DisplayName("Test DELETE /users/{id} - Doit supprimer un utilisateur existant")
    void deleteUser_WithValidId_ShouldDeleteUser() throws Exception {
        doNothing().when(userService).deleteUser(1L);

        mockMvc.perform(delete("/users/1"))
                .andExpect(status().isNoContent());

        verify(userService, times(1)).deleteUser(1L);
    }

    @Test
    @DisplayName("Test DELETE /users/{id} - Doit retourner 404 pour un utilisateur inexistant")
    void deleteUser_WithNonExistentId_ShouldReturn404() throws Exception {
        doThrow(new ObjectNotFoundException("Utilisateur non trouvé avec l'ID: 999"))
                .when(userService).deleteUser(999L);

        mockMvc.perform(delete("/users/999"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Utilisateur non trouvé avec l'ID: 999"));

        verify(userService, times(1)).deleteUser(999L);
    }

    @Test
    @DisplayName("Test GET /users/email/{email} - Doit retourner un utilisateur par email")
    void getUserByEmail_WithValidEmail_ShouldReturnUser() throws Exception {
        when(userService.getUserByEmail("john.doe@example.com")).thenReturn(Optional.of(validUser));

        mockMvc.perform(get("/users/email/john.doe@example.com"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));

        verify(userService, times(1)).getUserByEmail("john.doe@example.com");
    }

    @Test
    @DisplayName("Test GET /users/check-email/{email} - Doit retourner true si l'email existe")
    void checkEmailExists_WithExistingEmail_ShouldReturnTrue() throws Exception {
        when(userService.emailExists("john.doe@example.com")).thenReturn(true);

        mockMvc.perform(get("/users/check-email/john.doe@example.com"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));

        verify(userService, times(1)).emailExists("john.doe@example.com");
    }

    @Test
    @DisplayName("Test GET /users/check-email/{email} - Doit retourner false si l'email n'existe pas")
    void checkEmailExists_WithNonExistingEmail_ShouldReturnFalse() throws Exception {
        when(userService.emailExists("nonexistent@example.com")).thenReturn(false);

        mockMvc.perform(get("/users/check-email/nonexistent@example.com"))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));

        verify(userService, times(1)).emailExists("nonexistent@example.com");
    }
} 