package com.chatapp.quickchat.services;

import com.chatapp.quickchat.dto.MessageDTO;
import com.chatapp.quickchat.dto.UserDTO;
import com.chatapp.quickchat.entities.User;
import com.chatapp.quickchat.repositories.UsersRepository;
import com.chatapp.quickchat.responses.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;

@Service
public class WebSocketService {

    @Autowired
    UsersRepository usersRepository;

    public MessageDTO setWebSocketReturnMessage(MessageResponse messageResponse) {
        User sender = usersRepository.findByLogin(messageResponse.getSenderName());
        User receiver = usersRepository.findByLogin(messageResponse.getReceiverName());
        return new MessageDTO(
                messageResponse.getMessage(),
                new Timestamp(new Date().getTime()),
                null,
                new UserDTO(sender.getLogin(), sender.getActive()),
                new UserDTO(receiver.getLogin(), receiver.getActive())
        );
    }
}
