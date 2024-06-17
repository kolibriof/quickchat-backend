package com.chatapp.quickchat.dto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTO {
    private String login;
    private Boolean active;
}
