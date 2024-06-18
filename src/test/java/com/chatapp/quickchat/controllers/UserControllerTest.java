package com.chatapp.quickchat.controllers;

import com.chatapp.quickchat.dto.UserDTO;
import com.chatapp.quickchat.entities.User;
import com.chatapp.quickchat.responses.UserResponse;
import com.chatapp.quickchat.services.UsersService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    UsersService usersServiceMock;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void beforeEach() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }


    @Test
    void shouldReturnAllUsers() throws Exception {

        List<UserDTO> userDTOList = new ArrayList<>();
        for (int i = 0; i <= 3; i++) {
            userDTOList.add(new UserDTO("user" + i, true));
        }
        String expected = new ObjectMapper().writeValueAsString(userDTOList);

        when(usersServiceMock.findAllUsers()).thenReturn(userDTOList);
        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.get("/users"))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(expected, mvcResult.getResponse().getContentAsString());

    }

    @Test
    void shouldReturnOKResponse_IfCredentialsAreCorrect() throws Exception {
        User user = new User("dima", "dima", true);
        user.setId(1);
        UserResponse userResponse = new UserResponse(400, "The user already exists", null);
        String expected = new ObjectMapper().writeValueAsString(userResponse);

        when(usersServiceMock.createNewUser(any(User.class))).thenReturn(ResponseEntity.badRequest().body(userResponse));

        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.post("/users")
                        .content(new ObjectMapper().writeValueAsString(user))
                        .contentType("application/json"))
                .andExpect(content().string(expected))
                .andReturn();

        assertEquals(expected, mvcResult.getResponse().getContentAsString());
    }

    @Test
    void shouldFailValidation_IfLoginIsNull() throws Exception {
        User user = new User("", "dima", true);
        user.setId(1);
        UserResponse userResponse = new UserResponse(400, "User validation error.", null);
        String expected = new ObjectMapper().writeValueAsString(userResponse);
        String payload = new ObjectMapper().writeValueAsString(user);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .content(payload)
                        .contentType("application/json"))
                .andExpect(content().string(expected))
                .andReturn();

        assertEquals(expected, mvcResult.getResponse().getContentAsString());

    }



    @Test
    void shouldLoginUser_IfCredentialsAreCorrect() throws Exception {
        User user = new User("dima", "dima", true);
        user.setId(1);
        String payload = new ObjectMapper().writeValueAsString(user);
        UserResponse response = new UserResponse(200, user.getLogin(), null);
        String expected = new ObjectMapper().writeValueAsString(response);
        when(usersServiceMock.authenticateUser(user.getLogin(), user.getPassword())).thenReturn(response);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.
                post("/login").
                content(payload).
                contentType("application/json")).
                andReturn();

        assertEquals(expected, mvcResult.getResponse().getContentAsString());

    }
}