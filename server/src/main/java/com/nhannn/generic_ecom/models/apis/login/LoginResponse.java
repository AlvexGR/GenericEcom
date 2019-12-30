package com.nhannn.generic_ecom.models.apis.login;

import com.nhannn.generic_ecom.models.User;
import com.nhannn.generic_ecom.models.apis.BaseResponse;

/**
 * Author: nhannn
 */
public class LoginResponse extends BaseResponse {
    private User user;
    private String jwtToken;

    public LoginResponse(boolean success, User user, String jwtToken) {
        super(success);
        this.user = user;
        this.jwtToken = jwtToken;
    }

    public LoginResponse(User user, String jwtToken) {
        this.user = user;
        this.jwtToken = jwtToken;
    }

    public LoginResponse() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }
}
