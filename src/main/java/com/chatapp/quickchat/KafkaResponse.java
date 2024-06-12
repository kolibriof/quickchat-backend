package com.chatapp.quickchat;

public class KafkaResponse {
    private String message;
    private int date;

    public KafkaResponse(int date, String message) {
        this.message = message;
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "{date:"+this.date+","+" message:"+this.message+"}";
    }
}
