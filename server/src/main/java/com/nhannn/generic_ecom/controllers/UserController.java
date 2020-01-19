package com.nhannn.generic_ecom.controllers;

import com.nhannn.generic_ecom.helpers.enums.ErrorCode;
import com.nhannn.generic_ecom.models.AuthenticatedUser;
import com.nhannn.generic_ecom.models.User;
import com.nhannn.generic_ecom.models.apis.login.GoogleLoginRequest;
import com.nhannn.generic_ecom.models.apis.login.LoginRequest;
import com.nhannn.generic_ecom.models.apis.login.LoginResponse;
import com.nhannn.generic_ecom.models.apis.sign_up.SignUpRequest;
import com.nhannn.generic_ecom.models.apis.sign_up.SignUpResponse;
import com.nhannn.generic_ecom.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
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
     * @return response for sign up result
     */
    @PostMapping(path = "sign-up")
    public SignUpResponse signUp(@RequestBody SignUpRequest signUpRequest) {
        try {
            // Null user
            if (signUpRequest.getUser() == null) {
                return new SignUpResponse(ErrorCode.BAD_REQUEST);
            }

            // Null email
            if (StringUtils.isEmpty(signUpRequest.getUser().getEmail())) {
                return new SignUpResponse(ErrorCode.EMAIL_EMPTY);
            }

            // Email exists
            if (userService.containsEmail(signUpRequest.getUser().getEmail())) {
                return new SignUpResponse(ErrorCode.EMAIL_EXISTED);
            }

            // Password null
            if (StringUtils.isEmpty(signUpRequest.getUser().getEmail())) {
                return new SignUpResponse(ErrorCode.PASSWORD_EMPTY);
            }

            boolean result = userService.signUp(signUpRequest.getUser());
            if (!result) {
                // Can't insert user
                return new SignUpResponse(ErrorCode.INTERNAL_ERROR);
            }
            return new SignUpResponse(ErrorCode.SUCCESS);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new SignUpResponse(ErrorCode.INTERNAL_ERROR);
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
            // Check null for login request
            if (loginRequest == null) {
                return new LoginResponse(ErrorCode.BAD_REQUEST);
            }

            // null email
            if (StringUtils.isEmpty(loginRequest.getEmail())) {
                return new LoginResponse(ErrorCode.EMAIL_EMPTY);
            }

            // null password
            if (StringUtils.isEmpty(loginRequest.getPassword())) {
                return new LoginResponse(ErrorCode.PASSWORD_EMPTY);
            }

            AuthenticatedUser authenticatedUser =
                    userService.login(
                            loginRequest.getEmail(),
                            loginRequest.getPassword(),
                            loginRequest.getRememberMe()
                    );
            if (authenticatedUser == null) {
                return new LoginResponse(ErrorCode.LOGIN_FAILED);
            }
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setJwtToken(authenticatedUser.getAccessToken());
            loginResponse.setUser(authenticatedUser.getUser());
            loginResponse.setErrorCode(ErrorCode.SUCCESS);
            return loginResponse;
        } catch (Exception ex) {
            ex.printStackTrace();
            return new LoginResponse(ErrorCode.INTERNAL_ERROR);
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
            // Check null for request
            if (googleLoginRequest == null) {
                return new LoginResponse(ErrorCode.BAD_REQUEST);
            }

            // null email
            if (StringUtils.isEmpty(googleLoginRequest.getEmail())) {
                return new LoginResponse(ErrorCode.EMAIL_EMPTY);
            }

            // null access token
            if (StringUtils.isEmpty(googleLoginRequest.getAccessToken())) {
                return new LoginResponse(ErrorCode.BAD_REQUEST);
            }

            AuthenticatedUser authenticatedUser = userService.loginWithGoogle(googleLoginRequest);

            // null user and null access token
            if (authenticatedUser == null || StringUtils.isEmpty(authenticatedUser.getAccessToken())) {
                return new LoginResponse(ErrorCode.INTERNAL_ERROR);
            }

            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setJwtToken(authenticatedUser.getAccessToken());
            loginResponse.setUser(authenticatedUser.getUser());
            loginResponse.setErrorCode(ErrorCode.SUCCESS);
            return loginResponse;
        } catch (Exception ex) {
            ex.printStackTrace();
            return new LoginResponse(ErrorCode.INTERNAL_ERROR);
        }
    }
}
