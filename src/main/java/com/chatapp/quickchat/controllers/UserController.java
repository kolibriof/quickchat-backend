package com.chatapp.quickchat.controllers;

import com.chatapp.quickchat.entities.User;
import com.chatapp.quickchat.repositories.UsersRepository;
import com.chatapp.quickchat.services.UsersService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class UserController {

    private final UsersRepository usersRepository;

    private final UsersService usersService;

    public UserController(UsersRepository usersRepository, UsersService usersService) {
        this.usersRepository = usersRepository;
        this.usersService = usersService;
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public Iterable<User> getAllUsers() {
        return this.usersRepository.findAll();
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public User addOneUser(@RequestBody User user) {
        return this.usersRepository.save(user);
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET, params = "id")
    public Optional<User> getOneUser(@RequestParam("id") int id) {
        return this.usersRepository.findById(id);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Integer loginUser(@RequestBody User user) {
        return this.usersService.authenticateUser(user.getLogin(), user.getPassword());
    }
}
