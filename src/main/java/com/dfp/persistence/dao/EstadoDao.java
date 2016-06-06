package com.dfp.persistence.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.dfp.persistencia.entities.Estado;
import com.dfp.persistencia.entities.Reclamacion;
import com.dfp.utiles.hibernate.HibernateUtil;

public class EstadoDao {
	
	/**
	 * devuelve la lista de reclamaciones
	 */
	@SuppressWarnings("unchecked")
	public List<Estado> getEstadoDetail(Estado estado) {	
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		List<Estado> oListReclamacion = null;
		String sQuery = "select e from Estado e";
		
		if(estado.getSecEstado()!=null)
			sQuery = sQuery + " where e.codigoReclamacion = "+estado.getSecEstado();
		
		if(estado.getNombreEstado()!=null)
			sQuery = sQuery + " where e.nombreEstado = '"+estado.getNombreEstado()+"'";
			
		try {
			oListReclamacion = (List<Estado>) session.createQuery(sQuery).list();
		} catch (Exception e) {
			System.out.println("Errro al recuperar la lista de estados");
		} finally {
			return oListReclamacion;
		}
	}
	
	public List<Estado> getEstadoByExample(Estado estado) {	
		Criteria criteria = HibernateUtil.getSessionFactory().getCurrentSession().createCriteria(Estado.class);
		List<Estado> oListReclamacion = null;
		
		if(estado.getSecEstado()!=null)
			criteria.add(Restrictions.eq("secEstado", estado.getSecEstado()));
		
		if(estado.getNombreEstado()!=null)
			criteria.add(Restrictions.eq("nombreEstado", estado.getNombreEstado()));
			
		try {
			oListReclamacion = (List<Estado>) criteria.list();
		} catch (Exception e) {
			System.out.println("Error al recuperar la lista de estados");
		} finally {
			return oListReclamacion;
		}
	}
	
	
	
	
	
	
	public int persist(Estado entity) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try{
			session.save(entity);
		}catch(Exception e){
			return -1;
		}
		
		return entity.getId();
	}

	
	public void update(Estado entity) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.update(entity);
	}

	public Estado findById(String id) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Estado state = (Estado) session.get(Reclamacion.class, id);
		return state;
	}

	
	public void delete(Estado entity) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.delete(entity);
	}


	public List<Estado> findAll() {
		return HibernateUtil.getSessionFactory().getCurrentSession().createCriteria(Estado.class).list();
	}
	
	public Map<Integer,Estado> hashMapFindAll() {
//	    List<Estado> oListEstados = (List<Estado>) HibernateUtil.getSessionFactory().getCurrentSession().createCriteria(Estado.class).list();
	    Map<Integer,Estado> oMapEstado = new HashMap<Integer,Estado>();
	    
	    Query query = HibernateUtil.getSessionFactory().getCurrentSession().createQuery("from estado" );
	    List<Estado> oListEstados  = query.list();
            
	    Integer key = 0;
	    for(Estado estado:oListEstados){
		oMapEstado.put(estado.getSecEstado(), estado);
	    }
	    
	    return oMapEstado;
	}


	public void deleteAll() {
		List<Estado> entityList = findAll();
		for (Estado entity : entityList) {
			delete(entity);
		}
	}



}
