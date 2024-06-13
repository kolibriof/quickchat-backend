package com.chatapp.quickchat.controllers;

import com.chatapp.quickchat.entities.Messages;
import com.chatapp.quickchat.kafka.producer.MessageProducer;
import com.chatapp.quickchat.requests.ChatHistoryRequest;
import com.chatapp.quickchat.responses.MessageResponse;
import com.chatapp.quickchat.services.KafkaMessagingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class KafkaController {

    @Autowired
    private MessageProducer messageProducer;

    @Autowired
    private KafkaMessagingService kafkaMessagingService;

    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public void sendMessage(@RequestBody() MessageResponse message) {
        this.kafkaMessagingService.sendMessageToKafka(message);
    }

    @RequestMapping(value = "/history", method = RequestMethod.POST)
    public List<Messages> getChatHistory(@RequestBody() ChatHistoryRequest request) {
        return this.kafkaMessagingService.getChatHistory(request);
    }
}
