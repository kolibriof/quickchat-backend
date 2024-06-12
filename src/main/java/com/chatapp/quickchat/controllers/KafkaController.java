package com.chatapp.quickchat.controllers;

import com.chatapp.quickchat.KafkaResponse;
import com.chatapp.quickchat.kafka.producer.MessageProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
public class KafkaController {

    @Autowired
    private MessageProducer messageProducer;

    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public KafkaResponse sendMessage(@RequestBody() KafkaResponse message) {
        messageProducer.sendMessage("quickchat-topic", message.toString());
        return message;
    }
}
