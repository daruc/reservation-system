package com.example.app.reservation;

import com.example.app.core.DomainModel;
import com.example.app.core.valueobjects.Description;
import com.example.app.core.valueobjects.Name;

public class ReservationModel extends DomainModel {
	
	private Name name;
	private Description description;
	private int authorId;
	private int groupId;
	private int resourceId;
	
	private int quantity;
	
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
	public int getAuthorId() {
		return authorId;
	}
	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}
	public int getGroupId() {
		return groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getResourceId() {
		return resourceId;
	}
	public void setResourceId(int resourceId) {
		this.resourceId = resourceId;
	}
}
