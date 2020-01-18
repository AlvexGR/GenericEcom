package com.nhannn.generic_ecom.services.interfaces;

import com.nhannn.generic_ecom.models.User;
import com.nhannn.generic_ecom.models.AuthenticatedUser;
import com.nhannn.generic_ecom.models.apis.login.GoogleLoginRequest;

/**
 * Author: nhannn
 */
public interface IUserService {
    User getById(String id);
    boolean signUp(User user);
    AuthenticatedUser login(String email, String password, Boolean rememberMe);
    AuthenticatedUser loginWithGoogle(GoogleLoginRequest googleLoginRequest);
}
