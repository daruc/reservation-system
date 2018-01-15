package com.example.app.core;

public enum Event {
	USER_CREATED("user_created"),
	USER_DELETED("user_deleted"),
	RESOURCE_CREATED("resource_created"),
	RESOURCE_DELETED("resource_deleted"),
	USERS_GROUP_CREATED("users_group_created"),
	USERS_GROUP_DELETED("users_group_deleted"),
	ADDED_USERS_TO_GROUP("added_users_to_group"),
	RESERVATION_CREATED("reservation_created"),
	RESERVATION_DELETED("reservation_deleted"),
	RESERVATION_MADE("reservation_made"),
	RESERVATION_CANCELED("reservation_canceled");
	
	private String name;
	Event(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}
