package com.example.app.core.repository;

import java.util.HashMap;
import java.util.Map;

import com.example.app.homepage.UserModel;
import com.example.app.settings.UsersGroupModel;

public enum ORM {
	INSTANCE;
	
	private Map<Class<?>, DataMap> map = new HashMap<>();
	
	ORM() {
		map.put(UserModel.class, usersMap());
		map.put(UsersGroupModel.class, groupsMap());
	}
	
	public DataMap get(Class<?> domainModel) {
		return map.get(domainModel);
	}
	
	private DataMap usersMap() {
		
		return new DataMap()
			.setModelClass(UserModel.class)
			.setTableName("users")
			.addFieldColumnMap(int.class, "id", "id")
			.addFieldColumnMap(String.class, "login", "login")
			.addFieldColumnMap(String.class, "password", "password")
			.addFieldColumnMap(String.class, "name", "name")
			.addFieldColumnMap(String.class, "surname", "surname");
	}
	
	public DataMap groupsMap() {
		return new DataMap()
			.setModelClass(UsersGroupModel.class)
			.setTableName("groups")
			.addFieldColumnMap(int.class, "id", "id")
			.addFieldColumnMap(String.class, "name", "name")
			.addFieldColumnMap(String.class, "description", "description");
	}
}
