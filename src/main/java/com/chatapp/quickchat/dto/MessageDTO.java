package com.chatapp.quickchat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class MessageDTO {
    private String message;
    private Timestamp timestamp;
    private Integer id;
    private UserDTO sender_id;
    private UserDTO receiver_id;
}
