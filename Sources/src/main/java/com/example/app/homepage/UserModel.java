package com.example.app.homepage;

import java.util.Optional;

import com.example.app.core.DomainModel;
import com.example.app.core.appcontroller.AccessLevel;
import com.example.app.core.valueobjects.Name;
import com.example.app.core.valueobjects.Surname;

public class UserModel extends DomainModel {
	
	private Optional<Integer> groupId = Optional.empty();
	private String login;
	private String password;
	private Name name;
	private Surname surname;
	private AccessLevel accessLevel;
	
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
		return name.getName();
	}
	public void setName(String name) {
		if (name != null) {
			this.name = new Name(name);
		}
	}
	public String getSurname() {
		return surname.getSurname();
	}
	public void setSurname(String surname) {
		if (surname != null) {
			this.surname = new Surname(surname);
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
		return name.getName() + ' ' + surname.getSurname();
	}
}
