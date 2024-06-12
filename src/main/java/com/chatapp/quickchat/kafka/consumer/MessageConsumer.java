package com.chatapp.quickchat.kafka.consumer;

import com.chatapp.quickchat.KafkaResponse;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {

    @KafkaListener(topics = "quickchat-topic", groupId = "quickchat-id")
    public void listen(String message) {
        System.out.println("Received message: " + message);
    }
}
