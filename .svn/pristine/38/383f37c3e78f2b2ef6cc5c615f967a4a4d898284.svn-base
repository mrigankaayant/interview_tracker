package com.ayantsoft.expense.service;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;


import com.ayantsoft.interview.hibernate.dao.LoginDao;
import com.ayantsoft.interview.hibernate.pojo.User;

@ManagedBean
@ApplicationScoped
public class LoginService {

	@ManagedProperty(value="#{loginDao}")
	private LoginDao loginDao;
	public User login(String username,String password){
		return loginDao.findLogin(username,password);
	}

	/*GETTER SETTER*/

	public LoginDao getLoginDao() {
		return loginDao;
	}
	public void setLoginDao(LoginDao loginDao) {
		this.loginDao = loginDao;

	}

}