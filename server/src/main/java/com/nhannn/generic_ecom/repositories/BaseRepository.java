package com.nhannn.generic_ecom.repositories;

import com.nhannn.generic_ecom.models.BaseModel;
import com.nhannn.generic_ecom.repositories.interfaces.IBaseRepository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

/**
 * Author: nhannn
 */
public class BaseRepository<TEntity extends BaseModel> implements IBaseRepository<TEntity> {
    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    public TEntity getById(Class<TEntity> objClass , String id) {
        return entityManager.find(objClass, id);
    }

    @Transactional
    @Override
    public void insert(TEntity obj) {
        entityManager.persist(obj);
    }
}
