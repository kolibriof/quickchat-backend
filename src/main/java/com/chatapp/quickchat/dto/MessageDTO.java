package com.chatapp.quickchat.dto;

import java.sql.Timestamp;

public class MessageDTO {
    private String message;
    private Timestamp timestamp;
    private Integer id;
    private UserDTO sender_id;
    private UserDTO receiver_id;

    public MessageDTO(String message, Timestamp timestamp, Integer id, UserDTO sender_id, UserDTO receiver_id) {
        this.message = message;
        this.timestamp = timestamp;
        this.id = id;
        this.sender_id = sender_id;
        this.receiver_id = receiver_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserDTO getSender_id() {
        return sender_id;
    }

    public void setSender_id(UserDTO sender_id) {
        this.sender_id = sender_id;
    }

    public UserDTO getReceiver_id() {
        return receiver_id;
    }

    public void setReceiver_id(UserDTO receiver_id) {
        this.receiver_id = receiver_id;
    }
}
