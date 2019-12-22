package com.nhannn.generic_ecom.services;

import com.nhannn.generic_ecom.repositories.interfaces.IUserRepository;
import com.nhannn.generic_ecom.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("userSer")
public class UserService implements IUserService {
    private final IUserRepository userRepository;

    @Autowired
    public UserService(@Qualifier("userRepo") IUserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
