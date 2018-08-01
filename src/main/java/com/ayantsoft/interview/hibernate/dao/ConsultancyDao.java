package com.ayantsoft.interview.hibernate.dao;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import com.ayantsoft.interview.hibernate.pojo.ContactAdddress;
import com.ayantsoft.interview.hibernate.pojo.JobConsultancy;
import com.ayantsoft.interview.hibernate.util.HibernateUtil;

@ManagedBean(eager=true)
@ApplicationScoped
public class ConsultancyDao implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -3125230428971764390L;

	public void saveConsultancy(JobConsultancy jobConsultancy,ContactAdddress contactAddress){
		Session session=HibernateUtil.getSessionFactory().openSession();
		try{
			session.beginTransaction();
			session.saveOrUpdate(jobConsultancy.getContactAdddress());
			jobConsultancy.setContactAdddress(jobConsultancy.getContactAdddress());
			session.saveOrUpdate(jobConsultancy);
			session.getTransaction().commit();
		}catch(Exception e){
			e.printStackTrace();
			session.getTransaction().rollback();
			throw new HibernateException("Data save exception");
		}finally{
			session.close();
		}
	}
	
	public List<JobConsultancy> findConsultancyDetails(){
		Session session=HibernateUtil.getSessionFactory().openSession();
		List<JobConsultancy> results = null;
		try{
			Criteria criteria = session.createCriteria(JobConsultancy.class,"jobConsultancy");
			criteria.createAlias("jobConsultancy.contactAdddress", "contactAdddress");
			results = criteria.list();
			return results;
		}catch(Exception e){
			e.printStackTrace(); 
		 }finally{
			session.close();
		  }
		return results;
	}

	public boolean checkUniqueEmail(String email, Integer candidateId){
		email=email.trim();
		Session session=HibernateUtil.getSessionFactory().openSession();
		Long resultCount = 0L;
		try{
			String hql="SELECT count(*) FROM JobConsultancy c RIGHT JOIN c.contactAdddress con WHERE (";
			if(candidateId != null){
				hql = hql + "c.id != :id";
			}else{
				hql = hql + "c.id is not null";
			}

			hql = hql + " or c.id = null) and (con.email = :email or con.alternateEmail= :alternateEmail) ";
			Query query = session.createQuery(hql);
			
			if(candidateId != null){
				query.setParameter("candidateId", candidateId);
			}

			query.setString("email", email);
			query.setString("alternateEmail", email);
			resultCount = (Long)query.uniqueResult();
		}catch(HibernateException ex){
			ex.printStackTrace();
			throw new HibernateException("Data access exception.");
		 }finally{
			session.close();
		  }
		if(resultCount==0){
			return true;
		}
		return false;
	}
	
	public boolean checkUniquePhone(String phone, Integer candidateId){
		phone=phone.trim();
		Session session=HibernateUtil.getSessionFactory().openSession();
		Long resultCount = 0L;

		try{
			String hql="SELECT count(*) FROM JobConsultancy c RIGHT JOIN c.contactAdddress  con WHERE (";
			
			if(candidateId != null){
				hql = hql + "c.id != :id";
			}else{
				hql = hql + "c.id is not null";
			}

			hql = hql + " or c.id = null) and (con.workMobile = :workMobile or con.homeMobile= :homeMobile) ";

			Query query = session.createQuery(hql);
			
			if(candidateId != null){
				query.setParameter("id", candidateId);
			}
			
			query.setString("workMobile", phone);
			query.setString("homeMobile", phone);
			resultCount = (Long)query.uniqueResult();
		}catch(HibernateException ex){
			ex.printStackTrace();
			throw new HibernateException("Data access exception.");
		 }finally{
			session.close();
		  }
		
		if(resultCount==0){
			return true;
		}
		
		return false;
	}

}
