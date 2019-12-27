package com.nhannn.generic_ecom.repositories.interfaces;

import com.nhannn.generic_ecom.models.BaseModel;

/**
 * Author: nhannn
 */
public interface IBaseRepository<TEntity extends BaseModel> {
    TEntity getById(Class<TEntity> objClass, String id);
    void insert(TEntity obj);
}
