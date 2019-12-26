package com.nhannn.generic_ecom.models.apis.login;

import com.nhannn.generic_ecom.models.User;
import com.nhannn.generic_ecom.models.apis.BaseResponse;

/**
 * Author: nhannn
 */
public class LoginResponse extends BaseResponse {
    private User user;

    public LoginResponse(User user) {
        this.user = user;
    }

    public LoginResponse() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
