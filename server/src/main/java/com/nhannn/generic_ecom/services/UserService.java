package com.nhannn.generic_ecom.services;

import com.nhannn.generic_ecom.helpers.encryptors.MD5Encryption;
import com.nhannn.generic_ecom.models.User;
import com.nhannn.generic_ecom.repositories.interfaces.IUserRepository;
import com.nhannn.generic_ecom.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

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
    public User getBy(String id) {
        return userRepository.getById(User.class, id);
    }

    @Override
    public User getBy(String email, String password) {
        return userRepository.getBy(email, MD5Encryption.encrypt(password));
    }

    @Override
    public void insert(User user) {
        user.setId(UUID.randomUUID().toString());
        user.setPassword(MD5Encryption.encrypt(user.getPassword()));
        userRepository.insert(user);
    }


}
