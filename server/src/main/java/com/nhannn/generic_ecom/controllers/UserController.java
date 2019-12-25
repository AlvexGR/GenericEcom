package com.nhannn.generic_ecom.controllers;

import com.nhannn.generic_ecom.helpers.enums.Role;
import com.nhannn.generic_ecom.models.User;
import com.nhannn.generic_ecom.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
