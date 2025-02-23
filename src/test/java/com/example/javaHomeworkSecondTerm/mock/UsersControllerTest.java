package com.example.javaHomeworkSecondTerm.mock;

import com.example.javaHomeworkSecondTerm.Application;
import com.example.javaHomeworkSecondTerm.controller.UsersController;
import com.example.javaHomeworkSecondTerm.model.User;
import com.example.javaHomeworkSecondTerm.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import com.example.javaHomeworkSecondTerm.security.JwtLoggingFilter;
import com.example.javaHomeworkSecondTerm.security.SecurityConfig;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UsersController.class)
@ContextConfiguration(classes = {SecurityConfig.class})
@ActiveProfiles("test")
public class UsersControllerTest {

    @MockitoBean
    private JwtLoggingFilter jwtLoggingFilter;

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Test
    public void testGetAllUsers() throws Exception {
        List<User> users = List.of(new User(1L, "test@email.com", "John", "Doe"));
        when(userService.getAllUsers()).thenReturn(users);

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].email").value("test@email.com"));
    }

    @Test
    public void testCreateUser() throws Exception {
        User user = new User(1L, "test@email.com", "John", "Doe");
        when(userService.createUser(any())).thenReturn(user);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\": \"test@email.com\", \"name\": \"John\", \"surname\": \"Doe\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("test@email.com"));
    }

    @Test
    public void testCreateUser_InvalidInput() throws Exception {
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }
}
