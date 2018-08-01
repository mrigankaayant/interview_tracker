package com.ayantsoft.expense.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import org.primefaces.model.SortOrder;

import com.ayantsoft.interview.hibernate.dao.CandidateDao;
import com.ayantsoft.interview.hibernate.pojo.Candidate;
import com.ayantsoft.interview.hibernate.pojo.Doument;
import com.ayantsoft.interview.hibernate.pojo.JobConsultancy;
@ManagedBean
@ApplicationScoped

public class CandidateService implements Serializable {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 7306147717775060843L;

	@ManagedProperty(value = "#{candidateDao}")
	private CandidateDao candidateDao;

	public Integer saveCandidate(Candidate candidate){
		return candidateDao.save(candidate);
	}

	public Boolean saveAssmnt(Candidate candidate){
		return candidateDao.saveAssesment(candidate);
	}

	public void saveFile(Doument doc, Candidate candidate){
		candidateDao.saveFile(doc,candidate);
	}

	public boolean checkUniqueEmail(String email, Integer candidateId){
		return candidateDao.checkUniqueEmail(email, candidateId);
	}

	public boolean checkUniquePhone(String phone, Integer candidateId){
		return candidateDao.checkUniquePhone(phone, candidateId);
	}

	public List<JobConsultancy> getConsultancyName(){
		return candidateDao.findAllJobconsultancy();
	}

	public boolean chkUnique(String mobile, Integer candidateId){
		return candidateDao.duplicatechk(mobile,candidateId);
	}

	public Object[] expenseFilter(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters){
		return candidateDao.expenseFilter(first, pageSize, sortField, sortOrder, filters);
	}
	
	public Candidate findById(Integer id) {
		return candidateDao.findById(id);
	}
	
	/*GETTER SETTER*/
	public CandidateDao getCandidateDao() {
		return candidateDao;
	}
	public void setCandidateDao(CandidateDao candidateDao) {
		this.candidateDao = candidateDao;
	}
}