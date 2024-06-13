package com.chatapp.quickchat.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "usersdata", schema = "usersdataschema")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usersdata_seq")
    @SequenceGenerator(name = "usersdata_seq", sequenceName = "usersdata_seq", allocationSize = 1)
    private Integer Id;

    @OneToMany(mappedBy = "sender")
    private List<Messages> sentMessages;

    @OneToMany(mappedBy = "receiver")
    private List<Messages> receivedMessages;

    private String login;
    private String password;
    private Boolean active;

    private User(){}

    public User(String login, String password, Boolean isActive) {
        this.login = login;
        this.password = password;
        this.active = isActive;
    }

    public int getId() {
        return Id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public Boolean getActive() {
        return active;
    }
}
