package com.example.app.homepage;

import java.util.Optional;

import com.example.app.core.repository.Dao;

public class UserModel {
	
	private int id;
	private Optional<Integer> groupId = Optional.empty();
	private String login;
	private String password;
	private String name;
	private String surname;
	
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public void setGroupId(Integer groupId) {
		this.groupId = Optional.of(groupId);
	}
	
	public Optional<Integer> getGroupId() {
		return groupId;
	}
	
	public void clearGroupId() {
		groupId = Optional.empty();
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
}
