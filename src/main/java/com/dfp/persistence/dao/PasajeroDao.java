package com.dfp.persistence.dao;

import java.util.List;

import org.hibernate.Session;

import com.dfp.persistencia.entities.Pasajero;
import com.dfp.utiles.hibernate.HibernateUtil;

public class PasajeroDao {
	
	
	
	
	public int persist(Pasajero entity) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try{
			session.save(entity);
		}catch(Exception e){
			return -1;
		}
		
		return entity.getId();
	}

	
	public void update(Pasajero entity) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.update(entity);
	}

	public Pasajero findById(String id) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Pasajero claim = (Pasajero) session.get(Pasajero.class, id);
		return claim;
	}

	
	public void delete(Pasajero entity) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.delete(entity);
	}


	public List<Pasajero> findAll() {		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		
        List<Pasajero> result = session.createCriteria(Pasajero.class).list();
//		List<Reclamacion> reclamaciones = (List<Reclamacion>) getCurrentSession().createQuery("from Reclamacion").list();
//		session.getTransaction().commit();
		return result;
	}


	public void deleteAll() {
		List<Pasajero> entityList = findAll();
		for (Pasajero entity : entityList) {
			delete(entity);
		}
	}

}
