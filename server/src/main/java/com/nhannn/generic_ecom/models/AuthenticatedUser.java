package com.nhannn.generic_ecom.models;

public class AuthenticatedUser {
    private User user;
    private String accessToken;

    public AuthenticatedUser(User user, String accessToken) {
        this.user = user;
        this.accessToken = accessToken;
    }

    public AuthenticatedUser() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
