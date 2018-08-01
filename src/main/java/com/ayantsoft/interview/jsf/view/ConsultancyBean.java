package com.ayantsoft.interview.jsf.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import com.ayantsoft.expense.service.ConsultancyService;
import com.ayantsoft.interview.hibernate.pojo.ContactAdddress;
import com.ayantsoft.interview.hibernate.pojo.JobConsultancy;

@ManagedBean
@ViewScoped
public class ConsultancyBean implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 4143085358548496271L;
	
	private String action;
	
	private JobConsultancy jobConsultancy;
	private ContactAdddress contactAdddress;	
	
	private List<JobConsultancy> jobConsultancyList = new ArrayList<JobConsultancy>();
	
	
	@ManagedProperty(value = "#{consultancyService}")
	private ConsultancyService consultancyService;

	@PostConstruct
	public void init(){
		jobConsultancyList = consultancyService.showJobconsultancy();
     }

	public void newConsultant(){
		this.action = "NEW";
		jobConsultancy = new JobConsultancy();
		jobConsultancy.setContactAdddress(new ContactAdddress());
	}
	
	public void onRowSelect(){
		this.action = "NEW";
	}

	public void viewConsultant(){
		this.action = "List";
		jobConsultancyList = consultancyService.showJobconsultancy();
	}
	
	public void saveConsultant(){
		try{
			consultancyService.saveConsultant(jobConsultancy,contactAdddress);
			jobConsultancy = new JobConsultancy();
			jobConsultancy.setContactAdddress(new ContactAdddress());
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage(FacesMessage.SEVERITY_INFO,"", "Consultancy saved"));
		}catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage(FacesMessage.SEVERITY_ERROR,"", "Consultancy not saved " + e.getMessage()));
		}
	}


	public void emailValidate(FacesContext context, UIComponent component, Object value){
		try{
			if(jobConsultancy.getId() == null){
				boolean hasEmail = consultancyService.checkUniqueEmail((String)value,jobConsultancy.getId());
				if(!hasEmail){
					FacesContext.getCurrentInstance().addMessage(component.getClientId(), 
							new FacesMessage(FacesMessage.SEVERITY_ERROR,"Email duplicate:", "Email already exists"));
				}
			}	
		}catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage(FacesMessage.SEVERITY_ERROR,"", "Error" + e.getMessage()));
		}
		
	}

	public void workPhoneValidate(FacesContext context, UIComponent component, Object value){
		try{
			if(jobConsultancy.getId() == null){
				boolean hasPhone = consultancyService.checkUniquePhone((String)value,jobConsultancy.getId());
				if(!hasPhone){
					FacesContext.getCurrentInstance().addMessage(component.getClientId(), 
							new FacesMessage(FacesMessage.SEVERITY_ERROR,"Phone duplicate:", "Phone already exists"));
				}
			}else{
				boolean hasPhone = consultancyService.checkUniquePhone((String)value,jobConsultancy.getId());
				if(!hasPhone){
					FacesContext.getCurrentInstance().addMessage(component.getClientId(), 
							new FacesMessage(FacesMessage.SEVERITY_ERROR,"Phone duplicate:", "Phone already exists"));
				}	
			}
		}catch(Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage(FacesMessage.SEVERITY_ERROR,"", "Error" + e.getMessage()));
		}
		
	}

	/*GETTER SETTER*/
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	
	public ContactAdddress getContactAdddress() {
		return contactAdddress;
	}
	public void setContactAdddress(ContactAdddress contactAdddress) {
		this.contactAdddress = contactAdddress;
	}
	
	public ConsultancyService getConsultancyService() {
		return consultancyService;
	}
	public void setConsultancyService(ConsultancyService consultancyService) {
		this.consultancyService = consultancyService;
	}
	
	public JobConsultancy getJobConsultancy() {
		return jobConsultancy;
	}
	public void setJobConsultancy(JobConsultancy jobConsultancy) {
		this.jobConsultancy = jobConsultancy;
	}
	
	public List<JobConsultancy> getJobConsultancyList() {
		return jobConsultancyList;
	}
	public void setJobConsultancyList(List<JobConsultancy> jobConsultancyList) {
		this.jobConsultancyList = jobConsultancyList;
	}
}