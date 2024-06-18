package com.chatapp.quickchat.controllers;

import com.chatapp.quickchat.dto.UserDTO;
import com.chatapp.quickchat.entities.User;
import com.chatapp.quickchat.responses.UserResponse;
import com.chatapp.quickchat.security.UserAuthProvider;
import com.chatapp.quickchat.services.UsersService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@RestController
public class UserController {

    private final UsersService usersService;

    public UserController(UsersService usersService) {
        this.usersService = usersService;
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public Iterable<UserDTO> getAllUsers() {
        return this.usersService.findAllUsers();
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public ResponseEntity<UserResponse> addOneUser(@Valid @RequestBody User user, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(new UserResponse(400, "User validation error.", null));
        }
        return this.usersService.createNewUser(user);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public UserResponse loginUser(@RequestBody User user) {
        return this.usersService.authenticateUser(user.getLogin(), user.getPassword());
    }
}
