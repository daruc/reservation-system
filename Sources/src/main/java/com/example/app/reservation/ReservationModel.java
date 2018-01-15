package com.example.app.reservation;

import com.example.app.core.DomainModel;

public class ReservationModel extends DomainModel {
	
	private String name;
	private String description;
	private int authorId;
	private int groupId;
	private int resourceId;
	
	private int quantity;
	
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
