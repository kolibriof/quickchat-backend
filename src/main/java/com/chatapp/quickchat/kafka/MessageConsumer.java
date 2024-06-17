package com.chatapp.quickchat.kafka;

import com.chatapp.quickchat.services.KafkaMessagingService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {

    private final KafkaMessagingService kafkaMessagingService;

    public MessageConsumer(KafkaMessagingService kafkaMessagingService) {
        this.kafkaMessagingService = kafkaMessagingService;
    }

    @KafkaListener(topics = "quickchat-topic", groupId = "quickchat-id")
    public void listen(String message) {
        System.out.println("Received message: " + message);
        this.kafkaMessagingService.publishMessage(message);
    }
}
