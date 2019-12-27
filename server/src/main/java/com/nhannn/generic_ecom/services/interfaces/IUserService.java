package com.nhannn.generic_ecom.services.interfaces;

import com.nhannn.generic_ecom.models.User;

/**
 * Author: nhannn
 */
public interface IUserService {
    User getBy(String id);
    User getBy(String email, String password);
    void insert(User user);
}
