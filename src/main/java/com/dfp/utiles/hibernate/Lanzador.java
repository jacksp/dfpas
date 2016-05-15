package com.dfp.utiles.hibernate;

import java.util.Date;

public class Lanzador {
	
	 public static void main(String[] args) {
	        EventManager mgr = new EventManager();

	        if (args[0].equals("store")) {
	            mgr.createAndStoreEvent("My Event", new Date());
	        }

	        HibernateUtil.getSessionFactory().close();
	    }

}
