package com.chatapp.quickchat.services;


import com.chatapp.quickchat.dto.MessageDTO;
import com.chatapp.quickchat.dto.UserDTO;
import com.chatapp.quickchat.entities.Messages;
import com.chatapp.quickchat.entities.User;
import com.chatapp.quickchat.kafka.producer.MessageProducer;
import com.chatapp.quickchat.repositories.MessageRepository;
import com.chatapp.quickchat.repositories.UsersRepository;
import com.chatapp.quickchat.requests.ChatHistoryRequest;
import com.chatapp.quickchat.responses.MessageResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
public class KafkaMessagingService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private MessageProducer messageProducer;

    @Transactional
    public void publishMessage(String message)  {
        JsonNode jsonMessage = this.messageDeserialization(message);
        String senderName;
        String receiverName;
        String receivedMessage;

        if(jsonMessage == null) {
          System.out.println("The jsonMessage returned is NULL");
          return;
        }

        senderName = jsonMessage.get("senderName").asText();
        receiverName = jsonMessage.get("receiverName").asText();
        receivedMessage = jsonMessage.get("message").asText();

        User sender = this.usersRepository.findByLogin(senderName);
        User receiver = this.usersRepository.findByLogin(receiverName);

        Messages messages = new Messages();

        messages.setMessage(receivedMessage);
        messages.setSender(sender);
        messages.setReceiver(receiver);
        messages.setTimestamp(new Timestamp(new Date().getTime()));

        this.messageRepository.save(messages);

    }

    public List<MessageDTO> getChatHistory(ChatHistoryRequest request) {
        User sender = this.usersRepository.findByLogin(request.getSenderName());
        User receiver = this.usersRepository.findByLogin(request.getReceiverName());
        return this.convertMessageResponse(sender, receiver);
    }

    public void sendMessageToKafka(MessageResponse messageResponse) {
        String response;
        ObjectMapper objectMapper = new ObjectMapper();
        if(messageResponse.getReceiverName() != null && messageResponse.getSenderName() !=null) {
            try {
                response = objectMapper.writeValueAsString(messageResponse);
            } catch (JsonProcessingException exception) {
                response = "Error.";
            }
            this.messageProducer.sendMessage("quickchat-topic", response);
        }
    }

    private List<MessageDTO> convertMessageResponse(User sender, User receiver) {
        List<MessageDTO> messageDTOList = new ArrayList<>();
        List<Messages> messagesList = this.messageRepository.findBySenderOrReceiver(sender, receiver);
        for(Messages messagesItem : messagesList ) {
            messageDTOList.add(
                    new MessageDTO(messagesItem.getMessage(),
                            messagesItem.getTimestamp(),
                            messagesItem.getId(),
                            new UserDTO(messagesItem.getSender_id().getLogin(), messagesItem.getSender_id().getActive()),
                            new UserDTO(messagesItem.getReceiver_id().getLogin(), messagesItem.getReceiver_id().getActive())
                    ));
        }
        return messageDTOList;
    }

    private JsonNode messageDeserialization(String message) {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode JsonMessage;
        try {
            JsonMessage = objectMapper.readTree(message);
        } catch (JsonProcessingException exception) {
            System.out.println("Failed to parse a message.");
            JsonMessage = objectMapper.nullNode();
        }
        return JsonMessage;
    }


}

