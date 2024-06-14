package com.chatapp.quickchat.controllers;


import com.chatapp.quickchat.entities.Messages;
import com.chatapp.quickchat.responses.MessageResponse;
import com.chatapp.quickchat.responses.WebSocketService;
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
    public Messages broadcastGroupMessage(@Payload MessageResponse messageResponse) {
        return this.webSocketService.setWebSocketReturn(messageResponse);
    }


}
