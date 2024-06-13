package com.chatapp.quickchat.repositories;

import com.chatapp.quickchat.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<User, Integer> {

    @Query("SELECT u.id FROM User u WHERE u.login = :login AND u.password = :password")
    Integer findByLoginAndPassword(@Param("login") String login, @Param("password") String password);

    @Query("SELECT u.login FROM User u WHERE u.login = :login")
    String userExists(@Param("login") String login);

    @Query("SELECT u FROM User u WHERE u.login = :login")
    User findByLogin(@Param("login") String login);
}
