package com.chatapp.quickchat.controllers;


import com.chatapp.quickchat.dto.MessageDTO;
import com.chatapp.quickchat.responses.MessageResponse;
import com.chatapp.quickchat.services.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @Autowired
    WebSocketService webSocketService;

    @MessageMapping("/message")
    @SendTo("/topic/quickchat-group")
    public MessageDTO broadcastGroupMessage(@Payload MessageResponse messageResponse) {
        return this.webSocketService.setWebSocketReturnMessage(messageResponse);
    }

}
