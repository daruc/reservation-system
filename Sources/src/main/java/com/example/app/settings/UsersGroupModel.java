package com.example.app.settings;


public class UsersGroupModel {
	
	private int id;
	private String name;
	private String description;

	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
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
