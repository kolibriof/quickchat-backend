package com.chatapp.quickchat.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponse {
    private int code;
    private String message;
    private String token;
}
