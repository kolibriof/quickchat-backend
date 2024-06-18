package com.chatapp.quickchat.services;

import com.chatapp.quickchat.dto.UserDTO;
import com.chatapp.quickchat.entities.User;
import com.chatapp.quickchat.repositories.UsersRepository;
import com.chatapp.quickchat.responses.UserResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsersServiceTest {

    @Mock
    private UsersRepository usersRepository;

    @InjectMocks
    private UsersService usersService;

    @Test
    void shouldReturn200_IfUserExists() {
        when(usersRepository.findByLoginAndPassword("login", "pass")).thenReturn(1);
        UserResponse expected = new UserResponse(200, "login", null);

        UserResponse result = usersService.authenticateUser("login", "pass");

        assertEquals(expected.getMessage(), result.getMessage());
    }
    @Test
    void shouldReturn200_IfUserDoesNotExists() {
        when(usersRepository.findByLoginAndPassword("login", "pass")).thenReturn(null);
        UserResponse expected = new UserResponse(400, "Incorrect credentials.", null);

        UserResponse result = usersService.authenticateUser("login", "pass");

        assertEquals(expected.getMessage(), result.getMessage());
    }

    @Test
    void shouldReturnAllUsers() {
        List<User> userList = new ArrayList<>();
        List<UserDTO> userDTOList = new ArrayList<>();
        for(int i = 0; i<=2; i++) {
            userList.add(new User("login" + i, "password", true));
            userDTOList.add(new UserDTO("login" + i, true));
        }
        when(usersRepository.findAll()).thenReturn(userList);

        Iterable<UserDTO> result = usersService.findAllUsers();

        assertEquals(userDTOList, result);

    }

    @Test
    void shouldReturnCode400_IfUserExists() {
        User user = new User("dima", "dima", true);
        when(usersRepository.userExists("dima")).thenReturn(user.getLogin());

        ResponseEntity<UserResponse> result = usersService.createNewUser(user);

        if(result.getBody() != null && result.getBody().getMessage() != null) {
            assertEquals(result.getBody().getMessage(), "This user already exists.");
            assertEquals(result.getBody().getCode(), 400);
        } else {
            fail();
        }
    }
    @Test
    void shouldReturnCode200_IfUserDoesNotExists() {
        User user = new User("dima", "dima", true);
        when(usersRepository.userExists("dima")).thenReturn(null);

        ResponseEntity<UserResponse> result = usersService.createNewUser(user);

        if(result.getBody() != null && result.getBody().getMessage() != null) {
            assertEquals(result.getBody().getMessage(), "User has been added.");
            assertEquals(result.getBody().getCode(), 200);
        } else {
            fail();
        }
    }

    @Test
    void shouldReturnUnexpectedError_IfUserDoesNotEqualToPayload() {
        User user = new User("dima", "dima", true);
        when(usersRepository.userExists("dima")).thenReturn("admin");

        ResponseEntity<UserResponse> result = usersService.createNewUser(user);

        if(result.getBody() != null && result.getBody().getMessage() != null) {
            assertEquals(result.getBody().getMessage(), "Unexpected error.");
            assertEquals(result.getBody().getCode(), 400);
        } else {
            fail();
        }
    }
}