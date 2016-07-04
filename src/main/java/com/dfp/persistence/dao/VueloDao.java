package com.dfp.persistence.dao;

import java.util.List;

import org.hibernate.Session;

import com.dfp.persistencia.entities.Vuelo;
import com.dfp.utiles.hibernate.HibernateUtil;

public class VueloDao {
	
	public int persist(Vuelo entity) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try{
			session.save(entity);
		}catch(Exception e){
			return -1;
		} 
		
		return entity.getId();
	}

	
	public void update(Vuelo entity) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.update(entity);
	}

	public Vuelo findById(String id) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Vuelo claim = (Vuelo) session.get(Vuelo.class, id);
		return claim;
	}

	
	public void delete(Vuelo entity) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.delete(entity);
	}


	public List<Vuelo> findAll() {		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		
        List<Vuelo> result = session.createCriteria(Vuelo.class).list();
//		List<Reclamacion> reclamaciones = (List<Reclamacion>) getCurrentSession().createQuery("from Reclamacion").list();
//		session.getTransaction().commit();
		return result;
	}


	public void deleteAll() {
		List<Vuelo> entityList = findAll();
		for (Vuelo entity : entityList) {
			delete(entity);
		}
	}


}
