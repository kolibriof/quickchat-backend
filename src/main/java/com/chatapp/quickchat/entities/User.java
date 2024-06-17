package com.chatapp.quickchat.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "usersdata", schema = "usersdataschema")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usersdata_seq")
    @SequenceGenerator(name = "usersdata_seq", sequenceName = "usersdata_seq", allocationSize = 1)
    private Integer Id;

    @NotBlank
    private String login;

    @NotBlank
    @Size(min = 4, message = "Password must be at least 4 characters long.")
    private String password;

    @NotNull
    private Boolean active;

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
