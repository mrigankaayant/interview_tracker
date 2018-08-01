package com.ayantsoft.interview.jsf.view;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.ayantsoft.expense.service.LoginService;
import com.ayantsoft.interview.hibernate.pojo.User;


/**
 * @author user
 *
 */
@SessionScoped
@ManagedBean
public class LoginBean implements Serializable {

	/**
	 *  serialVersionUID
	 */
	private static final long serialVersionUID = -262510023048348597L;

	private String username;
	private String password;
	private User user;

    @ManagedProperty(value="#{loginService}")
	private LoginService loginService;

	public String login(){
	    user = loginService.login(username, password);
		
	    if(user != null){
			return "home.xhtml?faces-redirect=true";
		}else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error:", "Login Failed"));
			return "login.xhtml?faces-redirect=true";
		}
	}

	
	/*GETTER SETTER*/
    public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public LoginService getLoginService() {
		return loginService;
	}
	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}

}
