package com.nhannn.generic_ecom.repositories;

import com.nhannn.generic_ecom.models.User;
import com.nhannn.generic_ecom.repositories.interfaces.IUserRepository;
import org.springframework.stereotype.Repository;
import javax.persistence.TypedQuery;

/**
 * Author: nhannn
 */
@Repository("userRepo")
public class UserRepository extends BaseRepository<User> implements IUserRepository {
    @Override
    public User getBy(String email, String password) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT u ");
        sql.append(" FROM users u ");
        sql.append(" WHERE u.email = :email AND u.password = :password ");

        TypedQuery<User> query = entityManager.createQuery(sql.toString(), User.class);

        query.setParameter("email", email);
        query.setParameter("password", password);
        return query.getSingleResult();
    }
}
