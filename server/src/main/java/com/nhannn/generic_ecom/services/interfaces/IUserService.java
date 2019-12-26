package com.nhannn.generic_ecom.services.interfaces;

import com.nhannn.generic_ecom.models.User;

/**
 * Author: nhannn
 */
public interface IUserService {
    User getById(int id);
    void insert(User user);
}
