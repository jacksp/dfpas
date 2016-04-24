package com.dfp.persistence.dao;

import java.io.Serializable;
import java.util.List;

import com.dfp.persistence.HibernateWraper;
import com.dfp.persistencia.entities.Reclamacion;

public class ReclamacionDao extends HibernateWraper implements ReclamacionDaoInterface<Reclamacion, String> {
	
	/**
	 * devuelve la lista de reclamaciones
	 */
	@SuppressWarnings("unchecked")
	public List<Reclamacion> getClaimDetail(String sCodeClaim) {		
		List<Reclamacion> oListReclamacion = null;
		String sQuery = "select r from Claim r ";
		
		if(!sCodeClaim.isEmpty())
			sQuery = sQuery + " where r.codigoReclamacion = "+sCodeClaim;
			
		try {
			oListReclamacion = (List<Reclamacion>) getCurrentSession().createQuery(sQuery).list();
		} catch (Exception e) {
			System.out.println("Errro al recuperar la lista de reclamaciones");
		} finally {
			return oListReclamacion;
		}
	}
	
	
	
	public int persist(Reclamacion entity) {
		try{
			Serializable o = getCurrentSession().save(entity);
			
			
		}catch(Exception e){
			return 0;
		}
		return 1;
	}

	
	public void update(Reclamacion entity) {
		getCurrentSession().update(entity);
	}

	public Reclamacion findById(String id) {
		Reclamacion claim = (Reclamacion) getCurrentSession().get(Reclamacion.class, id);
		return claim;
	}

	
	public void delete(Reclamacion entity) {
		getCurrentSession().delete(entity);
	}


	public List<Reclamacion> findAll() {
		List<Reclamacion> reclamaciones = (List<Reclamacion>) getCurrentSession().createQuery("from Reclamacion").list();
		return reclamaciones;
	}


	public void deleteAll() {
		List<Reclamacion> entityList = findAll();
		for (Reclamacion entity : entityList) {
			delete(entity);
		}
	}
}
