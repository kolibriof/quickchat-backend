package com.chatapp.quickchat.services;

import com.chatapp.quickchat.dto.UserDTO;
import com.chatapp.quickchat.entities.User;
import com.chatapp.quickchat.repositories.UsersRepository;
import com.chatapp.quickchat.responses.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;

    public UserResponse authenticateUser(String login, String password) {
        Integer id = this.usersRepository.findByLoginAndPassword(login, password);
        if(id != null) {
            return new UserResponse(200, login);
        }
        return new UserResponse(404, "Incorrect credentials.");
    }

    public Iterable<UserDTO> findAllUsers() {
        List<UserDTO> userDTOS = new ArrayList<>();
        List<User> retrievedUsers = this.usersRepository.findAll();
        for (User userItem : retrievedUsers) {
            userDTOS.add(new UserDTO(userItem.getLogin(), userItem.getActive()));
        }
        return userDTOS;
    }

    public UserResponse createNewUser(User user) {
        String retrievedLogin = this.usersRepository.userExists(user.getLogin());
        if(retrievedLogin != null && !retrievedLogin.isEmpty()) {
            if(retrievedLogin.equals(user.getLogin())) {
                return new UserResponse(404, "User already exists.");
            }
        }
        this.usersRepository.save(user);
        return new UserResponse(200, user.getLogin());
    }
}
