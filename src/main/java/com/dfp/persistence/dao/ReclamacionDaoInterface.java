package com.dfp.persistence.dao;

import java.io.Serializable;
import java.util.List;

public interface ReclamacionDaoInterface<T, Id extends Serializable> {

	public int persist(T entity);

	public void update(T entity);

	public T findById(Id id);

	public void delete(T entity);

	public List<T> findAll();

	public void deleteAll();

}
