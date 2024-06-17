package com.chatapp.quickchat.controllers;

import com.chatapp.quickchat.dto.MessageDTO;
import com.chatapp.quickchat.requests.ChatHistoryRequest;
import com.chatapp.quickchat.responses.MessageResponse;
import com.chatapp.quickchat.services.KafkaMessagingService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class KafkaController {

    private final KafkaMessagingService kafkaMessagingService;

    public KafkaController(KafkaMessagingService kafkaMessagingService) {
        this.kafkaMessagingService = kafkaMessagingService;
    }

    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public void sendMessage(@Valid @RequestBody MessageResponse message) {
        this.kafkaMessagingService.sendMessageToKafka(message);
    }

    @RequestMapping(value = "/history", method = RequestMethod.POST)
    public List<MessageDTO> getChatHistory(@RequestBody ChatHistoryRequest request) {
        return this.kafkaMessagingService.getChatHistory(request);
    }
}
