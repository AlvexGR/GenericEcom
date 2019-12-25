package com.nhannn.generic_ecom.repositories.interfaces;

import com.nhannn.generic_ecom.models.BaseModel;

import java.util.List;

/*
 * Author: nhannn
 * */
public interface IBaseRepository<TEntity extends BaseModel> {
    TEntity getById(Class<TEntity> objClass, int id);
}
