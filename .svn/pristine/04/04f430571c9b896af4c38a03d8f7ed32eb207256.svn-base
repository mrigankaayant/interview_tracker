package com.ayantsoft.interview.hibernate.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil{
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -4748258416178996003L;
	
	private static SessionFactory sessionFactory;

	static{
		System.out.println("in hibernate util");
		try{
			// loads configuration and mappings
			Configuration configuration = new Configuration().configure();
			ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
					.applySettings(configuration.getProperties()).build();

			// builds a session factory from the service registry
			sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("session factory created.");
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}	

}
