package com.dfp.persistence.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.dfp.persistencia.entities.Reclamacion;
import com.dfp.utiles.hibernate.HibernateUtil;

public class ReclamacionDao  {
	
	
	
//	/**
//	 * devuelve la lista de reclamaciones
//	 */
//	public List<Reclamacion> getEstadoByExample(Reclamacion claim) {	
//		Criteria criteria = HibernateUtil.getSessionFactory().getCurrentSession().createCriteria(Reclamacion.class);
//		List<Reclamacion> oListReclamacion = null;
//	//	String sQuery = "select r from reclamacion r";
//	//	String sQueryEstado = "select e from estado e";
//		
//		if(claim.getId()!=null)
//			criteria.add(Restrictions.eq("id", claim.getId()));
//		
//		if(claim.getEstado()!=null)
//			criteria.add(Restrictions.eq("idEstado", claim.getEstado().getId()));
//			
//		try {
//			oListReclamacion = (List<Reclamacion>) criteria.list();	
//		} catch (Exception e) {
//			System.out.println("Errro al recuperar la lista de estados");
//		} 
//		finally {
//			return oListReclamacion.get(0).getEstado().getId();
//		}
//	}
	
	
	public List<Reclamacion> getListReclamacionByExample(Reclamacion claim) {	
		Criteria criteria = HibernateUtil.getSessionFactory().getCurrentSession().createCriteria(Reclamacion.class);
		List<Reclamacion> oListReclamacion = null;
		
		if(claim.getId()!=null)
			criteria.add(Restrictions.eq("id", claim.getId()));
		
		if(claim.getEstado()!=null)
			criteria.add(Restrictions.eq("idEstado", claim.getEstado().getId()));
			
		try {
			
			oListReclamacion = (List<Reclamacion>) criteria.list();
		
		} catch (Exception e) {
			System.out.println("Errro al recuperar la lista de estados");
		} 
		finally {
			return oListReclamacion;
		}
	}
	
	public Reclamacion getReclamacionByExample(Reclamacion claim) {
		
		List<Reclamacion> oList = this.getListReclamacionByExample(claim);
		
		if (oList.isEmpty())
			return null;
		else
			return oList.get(0);
	}
	
	
	
	public int persist(Reclamacion entity) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try{
			session.save(entity);
		}catch(Exception e){
			return -1;
		}
		
		return entity.getId();
	}

	
	public Reclamacion update(Reclamacion entity) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.update(entity);
		return entity;
	}



	
	public void delete(Reclamacion entity) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.delete(entity);
	}


	public List<Reclamacion> findAll() {		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		List<Reclamacion> reclamaciones = null;
		Criteria criteria = session.createCriteria(Reclamacion.class);
		try{
			reclamaciones = criteria.list();
		}catch (Exception e) {
			e.printStackTrace();
		}
//		List<Reclamacion> reclamaciones = (List<Reclamacion>) getCurrentSession().createQuery("from Reclamacion").list();
//		session.getTransaction().commit();
		return reclamaciones;
	}


	public void deleteAll() {
		List<Reclamacion> entityList = findAll();
		for (Reclamacion entity : entityList) {
			delete(entity);
		}
	}




}
