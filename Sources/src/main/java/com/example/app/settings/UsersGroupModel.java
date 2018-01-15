package com.example.app.settings;

import com.example.app.core.DomainModel;
import com.example.app.core.valueobjects.Description;
import com.example.app.core.valueobjects.Name;

public class UsersGroupModel extends DomainModel {
	
	private Name name;
	private Description description;
	
	public String getName() {
		return name.getName();
	}
	public void setName(String name) {
		if (name != null) {
			this.name = new Name(name);
		}
	}
	public String getDescription() {
		return description.getDescription();
	}
	public void setDescription(String description) {
		if (description != null) {
			this.description = new Description(description);
		}
	}
	
}
