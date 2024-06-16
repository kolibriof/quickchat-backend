package com.chatapp.quickchat.entities;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "messages", schema = "usersdataschema")
public class Messages {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usersdata_seq")
    @SequenceGenerator(name = "usersdata_seq", sequenceName = "usersdata_seq", allocationSize = 1)
    private Integer Id;

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false)
    private User receiver;

    private String message;

    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp timestamp;

    public Messages() {}

    public Messages(User sender_id, User receiver_id, String message, Timestamp timestamp) {
        this.sender = sender_id;
        this.receiver = receiver_id;
        this.message = message;
        this.timestamp = timestamp;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public User getSender_id() {
        return sender;
    }

    public void setSender(User sender_id) {
        this.sender = sender_id;
    }

    public User getReceiver_id() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
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
}
