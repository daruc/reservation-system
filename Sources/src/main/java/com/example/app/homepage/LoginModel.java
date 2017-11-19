package com.example.app.homepage;

import com.example.app.core.Dao;

public class LoginModel {
	
	private String login;
	private String password;
	
	private Dao dao;
	
	public LoginModel(Dao dao) {
		this.dao = dao;
	}

	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean isCorrect() {
		return dao.checkUser(this);
	}

}
