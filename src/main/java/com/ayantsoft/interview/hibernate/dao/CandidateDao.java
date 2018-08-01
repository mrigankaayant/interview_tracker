package com.ayantsoft.interview.hibernate.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.primefaces.model.SortOrder;

import com.ayantsoft.interview.hibernate.pojo.Candidate;
import com.ayantsoft.interview.hibernate.pojo.ContactAdddress;
import com.ayantsoft.interview.hibernate.pojo.Doument;
import com.ayantsoft.interview.hibernate.pojo.JobConsultancy;
import com.ayantsoft.interview.hibernate.util.HibernateUtil;

@ManagedBean(eager = true)
@ApplicationScoped
public class CandidateDao implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -7689453644628712392L;

	public Integer save(Candidate candidate){
		Session session = HibernateUtil.getSessionFactory().openSession();
		Integer id = null;
		try{
			session.beginTransaction();
			session.saveOrUpdate(candidate.getContactAdddress());
			
			if(candidate.getDoument() != null){
				session.saveOrUpdate(candidate.getDoument());
			}
			
			//session.saveOrUpdate(jobConsultancy);
			candidate.setContactAdddress(candidate.getContactAdddress());
			candidate.setJobConsultancy(candidate.getJobConsultancy());
			
			if(candidate.getInterviewSchduleDate() == null){
				candidate.setRecrutmentStatus("New Entry");
			}else{
				candidate.setRecrutmentStatus("Interview Scheduled");
			}
			
			candidate.setInterviewDate(new Date());
			session.saveOrUpdate(candidate);
			session.getTransaction().commit();
			
			if(candidate != null){
				id = candidate.getId();
			}
			
		}catch(Exception e){
			e.printStackTrace();
			session.getTransaction().rollback();
			throw new HibernateException("Data save Exception");
		}finally{
			session.close();
		 }
		return id;
	  }

	public boolean duplicatechk(String mobile, Integer candidateId){
		mobile = mobile.trim();
		Session session = HibernateUtil.getSessionFactory().openSession();
		Long resultCount = 0L;

		try{
			Criteria criteria = session.createCriteria(ContactAdddress.class,"contactAdddress");

			if(candidateId!=null){
				criteria.add(Restrictions.ne("id", candidateId));
			}
			
			Criterion workMobileCheck = Restrictions.eq("contactAdddress.workMobile", mobile);
			Disjunction myQueryDisjunc = Restrictions.disjunction();
			myQueryDisjunc.add(workMobileCheck);
			criteria.add(myQueryDisjunc);
			criteria.setProjection(Projections.rowCount());
			resultCount = (Long) criteria.uniqueResult();
			
			if(resultCount == 0){
				return true;
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
			throw new HibernateException("Data access exception.");
		}finally{
			session.close();
		}
		return false;
	  }
	
	public boolean checkUniqueEmail(String email, Integer candidateId){
		email = email.trim();
		Session session = HibernateUtil.getSessionFactory().openSession();
		Long resultCount = 0L;

		try{
			String hql = "SELECT count(*) FROM Candidate c RIGHT JOIN c.contactAdddress con WHERE (";
			
			if(candidateId != null){
				hql = hql + "c.id != :id";
			}else{
				hql = hql + "c.id is not null";
			}

			hql = hql + " or c.id = null) and (con.email = :email or con.alternateEmail = :alternateEmail) ";
			Query query = session.createQuery(hql);
			
			if(candidateId != null){
				query.setParameter("candidateId", candidateId);
			}

			query.setString("email", email);
			query.setString("alternateEmail", email);
            resultCount = (Long)query.uniqueResult();
            
            if(resultCount == 0){
    			return true;
    		}
            
		    }catch(HibernateException ex){
			ex.printStackTrace();
			throw new HibernateException("Data access exception.");
		     }finally{
			session.close();
		      }
		return false;
	   }

	public boolean checkUniquePhone(String phone, Integer candidateId){
		phone = phone.trim();
		Session session = HibernateUtil.getSessionFactory().openSession();
		Long resultCount = 0L;

		try{
			String hql = "SELECT count(*) FROM Candidate c RIGHT JOIN c.contactAdddress  con WHERE (";
			
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
            
            if(resultCount == 0){
    			return true;
    		}
            
		 }catch(HibernateException ex){
			ex.printStackTrace();
			throw new HibernateException("Data access exception.");
		 }finally{
			session.close();
		}
		return false;
	  }

	
	public void saveFile(Doument doc, Candidate candidate){
		Session session = HibernateUtil.getSessionFactory().openSession();
		try{
			session.beginTransaction();
			session.saveOrUpdate(doc);
			candidate.setDoument(doc);
			session.saveOrUpdate(candidate);
			session.getTransaction().commit();
		}catch(Exception e){
			e.printStackTrace();
			session.getTransaction().rollback();
			throw new HibernateException("Data save Exception");
		 }finally{
			session.close();
		   }
	 }

	public Boolean saveAssesment(Candidate candidate){
		Session session = HibernateUtil.getSessionFactory().openSession();
		Boolean status = false;
		try{  
			session.beginTransaction();
			candidate.setRecrutmentStatus("Interview Taken");
			session.saveOrUpdate(candidate);
			session.getTransaction().commit();
			status = true;
		}catch (Exception e) {
			status = false;
			e.printStackTrace();
			session.getTransaction().rollback();
			throw new HibernateException("Data save Exception");
		  }finally{
			session.close();
		  }
		return status;
	}
	
	public Object[] expenseFilter(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters){
		Session session=HibernateUtil.getSessionFactory().openSession();
		Object[] resultWithCount = new Object[2];
		try{
			Criteria criteria = session.createCriteria(Candidate.class,"candidate");
			criteria.createAlias("candidate.contactAdddress", "contactAdddress");
			criteria.createAlias("candidate.jobConsultancy", "jobConsultancy");
			
			if (filters != null) {
				filters.forEach((k,v)->{
					criteria.add(Restrictions.ilike(k, (String)v, MatchMode.ANYWHERE));
				});
			}
			
            criteria.setProjection(Projections.rowCount());
			Long resultCount = (Long)criteria.uniqueResult();
			resultWithCount[0] = resultCount;
			criteria.setProjection(null);

			if(sortField != null){
				if(SortOrder.ASCENDING == sortOrder){
					criteria.addOrder(Order.asc(sortField));
				}else if(SortOrder.DESCENDING == sortOrder){
					criteria.addOrder(Order.desc(sortField));
				}
			}

			criteria.setFirstResult(first);
			criteria.setMaxResults(pageSize);
			criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			resultWithCount[1]= criteria.list();
         }catch(Exception e){
			e.printStackTrace();
			throw new HibernateException("Data access exception.");
		  }finally{
			session.close();
		    }
           return resultWithCount;
	   }


	public List<JobConsultancy> findAllJobconsultancy(){
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<JobConsultancy> results = null;
		try{
		Criteria criteria = session.createCriteria(JobConsultancy.class);
		results = criteria.list();
		return results;
        }catch(Exception e){
        	e.printStackTrace(); 
		}finally{
			session.close();
		}
         return results;
   }

	public Candidate findById(Integer id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		try{
			Candidate candidate = (Candidate) session.get(Candidate.class, id);
			return candidate;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	    }
}
