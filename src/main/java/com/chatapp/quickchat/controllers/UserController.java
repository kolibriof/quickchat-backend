package com.chatapp.quickchat.controllers;

import com.chatapp.quickchat.dto.UserDTO;
import com.chatapp.quickchat.entities.User;
import com.chatapp.quickchat.repositories.UsersRepository;
import com.chatapp.quickchat.responses.UserResponse;
import com.chatapp.quickchat.services.UsersService;
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
    public UserResponse addOneUser(@RequestBody User user) {
        return this.usersService.createNewUser(user);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public UserResponse loginUser(@RequestBody User user) {
        return this.usersService.authenticateUser(user.getLogin(), user.getPassword());
    }
}
