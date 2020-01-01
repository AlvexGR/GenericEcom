package com.nhannn.generic_ecom.models.apis.login;

import com.nhannn.generic_ecom.models.apis.BaseRequest;

/**
 * Author: nhannn
 */
public class LoginRequest extends BaseRequest {
    private String email;
    private String password;
    private Boolean rememberMe;

    public LoginRequest(String email, String password, Boolean rememberMe) {
        this.email = email;
        this.password = password;
        this.rememberMe = rememberMe;
    }

    public LoginRequest() {}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(Boolean rememberMe) {
        this.rememberMe = rememberMe;
    }
}
