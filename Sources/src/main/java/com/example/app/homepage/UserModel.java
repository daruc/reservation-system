package com.example.app.homepage;

import java.util.Optional;

import com.example.app.core.appcontroller.AccessLevel;

public class UserModel {
	
	private int id;
	private Optional<Integer> groupId = Optional.empty();
	private String login;
	private String password;
	private String name;
	private String surname;
	private AccessLevel accessLevel;
	
	
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
		if (login != null) {
			this.login = login.trim();
		}
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
		if (name != null) {
			this.name = name.trim();
		}
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		if (surname != null) {
			this.surname = surname.trim();
		}
	}
	public int getAccessLevel() {
		return accessLevel.toInt();
	}
	public void setAccessLevel(int accessLevel) {
		this.accessLevel = AccessLevel.fromInt(accessLevel);
	}
	public String getFullname() {
		if (name == null && surname == null) {
			return login;
		}
		return name + ' ' + surname;
	}
}
