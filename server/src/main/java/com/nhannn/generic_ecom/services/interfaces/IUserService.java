package com.nhannn.generic_ecom.services.interfaces;

import com.nhannn.generic_ecom.models.User;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Author: nhannn
 */
public interface IUserService {
    User getById(String id);
    User getByEmail(String email);
    User createWithRandomPassword();

    void insert(User user);
}
