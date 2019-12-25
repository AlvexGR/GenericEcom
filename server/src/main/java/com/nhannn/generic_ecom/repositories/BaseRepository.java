package com.nhannn.generic_ecom.repositories;

import com.nhannn.generic_ecom.models.BaseModel;
import com.nhannn.generic_ecom.repositories.interfaces.IBaseRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/*
 * Author: nhannn
 * */
public class BaseRepository<TEntity extends BaseModel> implements IBaseRepository<TEntity> {
    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    public TEntity getById(Class<TEntity> objClass , int id) {
        try {
            return entityManager.find(objClass, id);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
