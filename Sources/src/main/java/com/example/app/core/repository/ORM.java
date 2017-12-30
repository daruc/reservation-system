package com.example.app.core.repository;

import java.util.HashMap;
import java.util.Map;

import com.example.app.homepage.UserModel;

public enum ORM {
	INSTANCE;
	
	private Map<Class<?>, DataMap> map = new HashMap<>();
	
	ORM() {
		map.put(UserModel.class, build(UserModel.class));
	}
	
	public DataMap get(Class<?> domainModel) {
		return map.get(domainModel);
	}
	
	public static DataMap build(Class<UserModel> domainModel) {
		
		return new DataMap()
			.setModelClass(UserModel.class)
			.setTableName("users")
			.addFieldColumnMap("id", "id")
			.addFieldColumnMap("login", "login")
			.addFieldColumnMap("password", "password")
			.addFieldColumnMap("name", "name")
			.addFieldColumnMap("surname", "surname");
	}
}
