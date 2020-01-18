package com.nhannn.generic_ecom.models;

public class AuthenticatedUser extends User {
    private String accessToken;

    public AuthenticatedUser() {
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
