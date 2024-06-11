package com.chatapp.quickchat.services;

import com.chatapp.quickchat.entities.User;
import com.chatapp.quickchat.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersService {
    @Autowired
    private UsersRepository usersRepository;

    public Integer authenticateUser(String login, String password) {
        return this.usersRepository.findByLoginAndPassword(login, password);
    }
}
