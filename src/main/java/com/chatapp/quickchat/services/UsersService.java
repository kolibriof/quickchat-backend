package com.chatapp.quickchat.services;

import com.chatapp.quickchat.dto.UserDTO;
import com.chatapp.quickchat.entities.User;
import com.chatapp.quickchat.repositories.UsersRepository;
import com.chatapp.quickchat.responses.UserResponse;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UsersService {


    private final UsersRepository usersRepository;

    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public UserResponse authenticateUser(String login, String password) {
        Integer id = this.usersRepository.findByLoginAndPassword(login, password);
        if(id != null) {
            return new UserResponse(200, login);
        }
        return new UserResponse(404, "Incorrect credentials.");
    }

    public Iterable<UserDTO> findAllUsers() {
        List<User> retrievedUsers = this.usersRepository.findAll();
        return retrievedUsers.stream().map((user)-> new UserDTO(user.getLogin(), user.getActive())).toList();
    }

    @Transactional
    public ResponseEntity<UserResponse> createNewUser(User user) {
        String retrievedLogin = this.usersRepository.userExists(user.getLogin());
        if(retrievedLogin != null && !retrievedLogin.isEmpty()) {
            if(retrievedLogin.equals(user.getLogin())) {
                return ResponseEntity.badRequest().body(new UserResponse(400, "This user already exists."));
            }
        }
        this.usersRepository.save(user);
        return ResponseEntity.ok(new UserResponse(200, "User has been added."));
    }
}
