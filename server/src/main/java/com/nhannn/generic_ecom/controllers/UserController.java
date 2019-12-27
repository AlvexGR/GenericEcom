package com.nhannn.generic_ecom.controllers;

import com.nhannn.generic_ecom.models.User;
import com.nhannn.generic_ecom.models.apis.login.LoginRequest;
import com.nhannn.generic_ecom.models.apis.login.LoginResponse;
import com.nhannn.generic_ecom.models.apis.sign_up.SignUpRequest;
import com.nhannn.generic_ecom.models.apis.sign_up.SignUpResponse;
import com.nhannn.generic_ecom.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Author: nhannn
 */
@RequestMapping("api/users")
@RestController
public class UserController {
    private final IUserService userService;

    @Autowired
    public UserController(@Qualifier("userSer") IUserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public User getById() {
        User user = userService.getBy("c6f5e832-42b7-43b6-a042-a77ed6e52f5d");
        return user;
    }

    @PostMapping(path = "sign-up")
    public SignUpResponse signUp(@RequestBody SignUpRequest signUpRequest) {
        try {
            userService.insert(signUpRequest.getUser());
            return new SignUpResponse(true);
        } catch(Exception ex) {
            ex.printStackTrace();
            return new SignUpResponse(false);
        }
    }

    @PostMapping(path = "login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse = new LoginResponse();
        try {
            User user = this.userService.getBy(loginRequest.getEmail(), loginRequest.getPassword());
            loginResponse.setUser(user);
            loginResponse.setSuccess(true);
        } catch (Exception ex) {
            ex.printStackTrace();
            loginResponse.setUser(null);
            loginResponse.setSuccess(false);
        }
        return loginResponse;
    }
}
