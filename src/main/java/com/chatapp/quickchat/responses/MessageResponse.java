package com.chatapp.quickchat.responses;

public class MessageResponse {

    private String message;
    private String senderName;
    private String receiverName;

    public MessageResponse(String message, String senderName, String receiverName) {
        this.message = message;
        this.senderName = senderName;
        this.receiverName = receiverName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }
}
