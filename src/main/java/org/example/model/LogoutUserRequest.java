package org.example.model;

public class LogoutUserRequest {
    String token;
    public LogoutUserRequest(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
