package com.chatapp.quickchat.controllers;


import com.chatapp.quickchat.dto.MessageDTO;
import com.chatapp.quickchat.responses.MessageResponse;
import com.chatapp.quickchat.services.WebSocketService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    private final WebSocketService webSocketService;

    public ChatController(WebSocketService webSocketService) {
        this.webSocketService = webSocketService;
    }

    @MessageMapping("/message")
    @SendTo("/topic/quickchat-group")
    public MessageDTO broadcastGroupMessage(@Payload MessageResponse messageResponse) {
        return this.webSocketService.setWebSocketReturnMessage(messageResponse);
    }

}
