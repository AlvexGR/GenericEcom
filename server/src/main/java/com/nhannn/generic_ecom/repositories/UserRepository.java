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
    public User getByEmail(String email) {
        try {
            String sql =
                    " SELECT u " +
                    " FROM users u " +
                    " WHERE u.email = :email";

            TypedQuery<User> query = entityManager.createQuery(sql, User.class);
            query.setParameter("email", email);

            return query.getSingleResult();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
