package com.ayantsoft.expense.service;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import com.ayantsoft.interview.hibernate.dao.ConsultancyDao;
import com.ayantsoft.interview.hibernate.pojo.ContactAdddress;
import com.ayantsoft.interview.hibernate.pojo.JobConsultancy;


@ManagedBean
@ApplicationScoped
public class ConsultancyService implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1006739796013010882L;
	
	@ManagedProperty(value = "#{consultancyDao}")
	private ConsultancyDao consultancyDao;
	
	public void saveConsultant(JobConsultancy jobConsultancy,ContactAdddress contactAddress){
		 consultancyDao.saveConsultancy(jobConsultancy,contactAddress);
	}
	
	public boolean checkUniqueEmail(String email, Integer candidateId){
		return consultancyDao.checkUniqueEmail(email, candidateId);
	}
	
	public boolean checkUniquePhone(String phone, Integer candidateId){
		return consultancyDao.checkUniquePhone(phone, candidateId);
	}
	   
	public List<JobConsultancy> showJobconsultancy(){
		return consultancyDao.findConsultancyDetails();
	}
	
	/*GETTER SETTER*/
	public ConsultancyDao getConsultancyDao() {
   		return consultancyDao;
   	}
   	public void setConsultancyDao(ConsultancyDao consultancyDao) {
   		this.consultancyDao = consultancyDao;
   	}
}