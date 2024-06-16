package com.chatapp.quickchat.dto;

public class UserDTO {
    private String login;
    private Boolean active;

    public UserDTO(String login, Boolean active) {
        this.login = login;
        this.active = active;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
