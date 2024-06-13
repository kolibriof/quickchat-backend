package com.chatapp.quickchat.repositories;

import com.chatapp.quickchat.entities.Messages;
import com.chatapp.quickchat.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends CrudRepository<Messages, Integer> {

    @Query("SELECT u FROM Messages u WHERE u.sender = :sender_id AND u.receiver = :receiver_id OR u.receiver = :sender_id AND u.sender = :receiver_id")
    List<Messages> findBySenderOrReceiver(@Param("sender_id") User sender, @Param("receiver_id") User receiver);
}
