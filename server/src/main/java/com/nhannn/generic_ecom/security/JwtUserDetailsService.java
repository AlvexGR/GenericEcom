package com.nhannn.generic_ecom.security;

import com.nhannn.generic_ecom.models.User;
import com.nhannn.generic_ecom.repositories.interfaces.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * Author: nhannn
 */
@Component
public class JwtUserDetailsService implements UserDetailsService {
    private final IUserRepository userRepository;

    @Autowired
    public JwtUserDetailsService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Username is indeed email
        User user = userRepository.getByEmail(username);
        if (user == null) {
            return null;
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>());
    }
}
