package com.example.app.settings;

import com.example.app.core.DomainModel;

public class UsersGroupModel extends DomainModel {
	
	private String name;
	private String description;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		if (name != null) {
			this.name = name.trim();
		}
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		if (description != null) {
			this.description = description.trim();
		}
	}
	
}
