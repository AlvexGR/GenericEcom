package com.nhannn.generic_ecom.services;

import com.nhannn.generic_ecom.models.User;
import com.nhannn.generic_ecom.repositories.interfaces.IUserRepository;
import com.nhannn.generic_ecom.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Author: nhannn
 */
@Service("userSer")
public class UserService implements IUserService {
    private final IUserRepository userRepository;

    @Autowired
    public UserService(@Qualifier("userRepo") IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getById(String id) {
        return userRepository.getById(User.class, id);
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.getByEmail(email);
    }

    @Override
    public void insert(User user) {
        user.setId(UUID.randomUUID().toString());
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userRepository.insert(user);
    }
}
