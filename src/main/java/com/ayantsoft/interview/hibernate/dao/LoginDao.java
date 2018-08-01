package com.ayantsoft.interview.hibernate.dao;

import java.io.Serializable;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.hibernate.Query;
import org.hibernate.Session;

import com.ayantsoft.interview.hibernate.pojo.User;
import com.ayantsoft.interview.hibernate.util.HibernateUtil;

@ManagedBean(eager=true)
@ApplicationScoped
public class LoginDao implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -2877874901888525434L;

	public User findLogin(String username,String password){
		Session session=HibernateUtil.getSessionFactory().openSession();
		User user = null;
		try{
			String hql = "FROM User u WHERE u.username =:username and u.password =:password";
			Query query = session.createQuery(hql);
			query.setParameter("username", username);
			query.setParameter("password", password);
			user = (User) query.uniqueResult();
		}catch(Exception e){
			e.printStackTrace();
		 }finally{
			session.close();
		  }
		return user;
	}
}
