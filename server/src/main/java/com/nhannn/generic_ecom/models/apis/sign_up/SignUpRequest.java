package com.nhannn.generic_ecom.models.apis.sign_up;

import com.nhannn.generic_ecom.models.User;
import com.nhannn.generic_ecom.models.apis.BaseRequest;

/**
 * Author: nhannn
 */
public class SignUpRequest extends BaseRequest {
    User user;

    public SignUpRequest() {
    }

    public SignUpRequest(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
