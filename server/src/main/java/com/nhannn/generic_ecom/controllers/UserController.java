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
    public User getUser() {
        User user = userService.getById(1);
        return user;
    }

    @PostMapping(path = "sign-up")
    public SignUpResponse signUp(@RequestBody SignUpRequest signUpRequest) {
        try {
            signUpRequest.getUser().setId(UUID.randomUUID().toString());
            userService.insert(signUpRequest.getUser());
            return new SignUpResponse(true);
        } catch(Exception ex) {
            ex.printStackTrace();
            return new SignUpResponse(false);
        }
    }

    @PostMapping(path = "login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        User user = this.userService.getById(1);
        return new LoginResponse(user);
    }
}
