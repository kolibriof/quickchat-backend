package com.chatapp.quickchat.services;

import com.chatapp.quickchat.dto.UserDTO;
import com.chatapp.quickchat.entities.User;
import com.chatapp.quickchat.repositories.UsersRepository;
import com.chatapp.quickchat.responses.UserResponse;
import com.chatapp.quickchat.security.UserAuthProvider;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UsersService implements UserDetailsService {

    private final UserAuthProvider userAuthProvider;
    private final UsersRepository usersRepository;

    public UsersService(UserAuthProvider userAuthProvider, UsersRepository usersRepository) {
        this.userAuthProvider = userAuthProvider;
        this.usersRepository = usersRepository;
    }

    public UserResponse authenticateUser(String login, String password) {
        Integer id = this.usersRepository.findByLoginAndPassword(login, password);
        if(id != null) {
            User user = this.usersRepository.findByLogin(login);
            return new UserResponse(200, login, this.userAuthProvider.createToken(user));
        }
        return new UserResponse(404, "Incorrect credentials.", null);
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
                return ResponseEntity.badRequest().body(new UserResponse(400, "This user already exists.", null));
            }
            return ResponseEntity.badRequest().body(new UserResponse(400, "Unexpected error.", null));
        }
        this.usersRepository.save(user);

        return ResponseEntity.ok(new UserResponse(200, "User has been added.", this.userAuthProvider.createToken(user)));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User result = usersRepository.findByLogin(username);
        if(result != null) {
            return (UserDetails) result;
        }
        throw new UsernameNotFoundException("User is not found.");
    }
}
