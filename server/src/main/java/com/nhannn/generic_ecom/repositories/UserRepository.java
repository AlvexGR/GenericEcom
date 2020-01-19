package com.nhannn.generic_ecom.repositories;

import com.nhannn.generic_ecom.models.User;
import com.nhannn.generic_ecom.repositories.interfaces.IUserRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 * Author: nhannn
 */
@Repository("userRepo")
public class UserRepository extends BaseRepository<User> implements IUserRepository {

    /**
     * Ger user by email
     * @param email to query
     * @return user
     */
    @Override
    public User getByEmail(String email) {
        String sql =
            " SELECT u " +
            " FROM users u " +
            " WHERE u.email = :email";

        TypedQuery<User> query = entityManager.createQuery(sql, User.class);
        query.setParameter("email", email);

        return query.getSingleResult();
    }

    /**
     * Count user by email
     * @param email to count
     * @return total user with that email
     */
    @Override
    public int countByEmail(String email) {
        String sql =
            "SELECT COUNT(u) " +
            "FROM users u " +
            "WHERE u.email = :email ";
        Query query = entityManager.createQuery(sql);
        query.setParameter("email", email);
        return (int) (long)query.getSingleResult();
    }
}
