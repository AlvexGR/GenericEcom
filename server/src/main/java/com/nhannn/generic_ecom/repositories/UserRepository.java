package com.nhannn.generic_ecom.repositories;

import com.nhannn.generic_ecom.models.User;
import com.nhannn.generic_ecom.repositories.interfaces.IUserRepository;
import org.springframework.stereotype.Repository;

/*
* Author: nhannn
* */
@Repository("userRepo")
public class UserRepository extends BaseRepository<User> implements IUserRepository {
}
