package com.chatapp.quickchat.responses;

import com.chatapp.quickchat.entities.Messages;
import com.chatapp.quickchat.entities.User;
import com.chatapp.quickchat.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;

@Service
public class WebSocketService {

    @Autowired
    UsersRepository usersRepository;

    public Messages setWebSocketReturn(MessageResponse messageResponse) {
        Messages messages = new Messages();
        User sender = usersRepository.findByLogin(messageResponse.getSenderName());
        User receiver = usersRepository.findByLogin(messageResponse.getReceiverName());
        messages.setReceiver(receiver);
        messages.setSender(sender);
        messages.setMessage(messageResponse.getMessage());
        messages.setTimestamp(new Timestamp(new Date().getTime()));
        return messages;
    }
}
