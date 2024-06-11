package com.chatapp.quickchat.repositories;

import com.chatapp.quickchat.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UsersRepository extends CrudRepository<User, Integer> {

    @Query("SELECT id FROM User WHERE login = :login AND password = :password")
    Integer findByLoginAndPassword(@Param("login") String login, @Param("password") String password);
}
