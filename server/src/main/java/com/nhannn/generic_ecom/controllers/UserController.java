package com.nhannn.generic_ecom.controllers;

import com.nhannn.generic_ecom.models.AuthenticatedUser;
import com.nhannn.generic_ecom.models.User;
import com.nhannn.generic_ecom.models.apis.login.GoogleLoginRequest;
import com.nhannn.generic_ecom.models.apis.login.LoginRequest;
import com.nhannn.generic_ecom.models.apis.login.LoginResponse;
import com.nhannn.generic_ecom.models.apis.sign_up.SignUpRequest;
import com.nhannn.generic_ecom.models.apis.sign_up.SignUpResponse;
import com.nhannn.generic_ecom.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Author: nhannn
 */
@RequestMapping("api/users")
@RestController
public class UserController {
    private final IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    /**
     * API testing
     *
     * @return user
     */
    @GetMapping
    public User getById() {
        User user = userService.getById("c9474f09-157e-4055-bdec-cbe7fdc9f394");
        return user;
    }

    /**
     * Sign up new account
     *
     * @param signUpRequest contains new user profile
     * @return successfully created user
     */
    @PostMapping(path = "sign-up")
    public SignUpResponse signUp(@RequestBody SignUpRequest signUpRequest) {
        try {
            boolean result = userService.signUp(signUpRequest.getUser());
            return new SignUpResponse(result);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new SignUpResponse();
        }
    }

    /**
     * Normal login with email and password
     *
     * @param loginRequest contains email and password
     * @return full user with access token
     */
    @PostMapping(path = "login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        try {
            AuthenticatedUser authenticatedUser =
                    userService.login(
                            loginRequest.getEmail(),
                            loginRequest.getPassword(),
                            loginRequest.getRememberMe()
                    );
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setJwtToken(authenticatedUser.getAccessToken());
            loginResponse.setUser(authenticatedUser);
            loginResponse.setSuccess(true);
            return loginResponse;
        } catch (Exception ex) {
            ex.printStackTrace();
            return new LoginResponse();
        }
    }

    /**
     * Login by google account
     *
     * @param googleLoginRequest google account info
     * @return LoginResponse
     */
    @PostMapping(path = "login/google")
    public LoginResponse login(@RequestBody GoogleLoginRequest googleLoginRequest) {
        try {
            AuthenticatedUser authenticatedUser = userService.loginWithGoogle(googleLoginRequest);
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setJwtToken(authenticatedUser.getAccessToken());
            loginResponse.setUser(authenticatedUser);
            loginResponse.setSuccess(true);
            return loginResponse;
        } catch (Exception ex) {
            ex.printStackTrace();
            return new LoginResponse();
        }
    }
}
