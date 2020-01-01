package com.nhannn.generic_ecom.controllers;

import com.nhannn.generic_ecom.models.User;
import com.nhannn.generic_ecom.models.apis.login.LoginRequest;
import com.nhannn.generic_ecom.models.apis.login.LoginResponse;
import com.nhannn.generic_ecom.models.apis.sign_up.SignUpRequest;
import com.nhannn.generic_ecom.models.apis.sign_up.SignUpResponse;
import com.nhannn.generic_ecom.security.JwtToken;
import com.nhannn.generic_ecom.security.JwtUserDetailsService;
import com.nhannn.generic_ecom.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Author: nhannn
 */
@RequestMapping("api/users")
@RestController
public class UserController {
    private final IUserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtToken jwtToken;
    private final JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    public UserController(
            IUserService userService,
            AuthenticationManager authenticationManager,
            JwtToken jwtToken,
            JwtUserDetailsService jwtUserDetailsService) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtToken = jwtToken;
        this.jwtUserDetailsService = jwtUserDetailsService;
    }

    /**
     * API testing
     * @return user
     */
    @GetMapping
    public User getById() {
        User user = userService.getById("0a51df0c-9e36-4051-87fc-d9992b62f940");
        return user;
    }

    /**
     * Check if there is any user with this email
     * @param email to check
     * @return true if exists otherwise false
     */
    @GetMapping(path = "/verify/{email}")
    public boolean verifyEmailExistence(@PathVariable("email") String email) {
        return false;
    }

    /**
     * Sign up new account
     * @param signUpRequest contains new user profile
     * @return successfully created user
     */
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

    /**
     * Normal login with email and password
     * @param loginRequest contains email and password
     * @return full user with access token
     */
    @PostMapping(path = "login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        try {
            LoginResponse loginResponse = new LoginResponse();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(loginRequest.getEmail());
            String token = jwtToken.generateToken(userDetails, loginRequest.getRememberMe());
            loginResponse.setJwtToken(token);

            User user = userService.getByEmail(loginRequest.getEmail());
            loginResponse.setUser(user);
            loginResponse.setSuccess(true);
            return loginResponse;
        } catch (Exception ex) {
            ex.printStackTrace();
            return new LoginResponse();
        }
    }
}
