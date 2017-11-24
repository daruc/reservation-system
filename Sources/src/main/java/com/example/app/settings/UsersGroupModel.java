package com.example.app.settings;

import com.example.app.core.Dao;

public class UsersGroupModel {
	
	private String name;
	private String description;
	
	private Dao dao;
	
	public UsersGroupModel(Dao dao) {
		this.dao = dao;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public boolean create() {
		return dao.createUsersGroup(this);
	}

	
}
