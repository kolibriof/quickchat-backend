package com.chatapp.quickchat.kafka.consumer;

import com.chatapp.quickchat.services.KafkaMessagingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {

    @Autowired
    SimpMessagingTemplate template;

    @Autowired
    KafkaMessagingService kafkaMessagingService;

    @KafkaListener(topics = "quickchat-topic", groupId = "quickchat-id")
    public void listen(String message) {
        System.out.println("Received message: " + message);
        this.kafkaMessagingService.publishMessage(message);
    }
}
