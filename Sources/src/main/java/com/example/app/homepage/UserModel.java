package com.example.app.homepage;

import com.example.app.core.Dao;

public class UserModel {
	private Dao dao;
	
	private String login;
	private String password;
	private String name;
	private String surname;
	
	public UserModel(Dao dao) {
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getFullname() {
		if (name == null && surname == null) {
			return login;
		}
		return name + ' ' + surname;
	}

	public boolean createUser() {
		return dao.createUser(this);
	}
}
