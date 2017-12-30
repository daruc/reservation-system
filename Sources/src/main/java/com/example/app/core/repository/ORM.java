package com.example.app.core.repository;

import java.util.HashMap;
import java.util.Map;

import com.example.app.core.repository.DataMap.Field;
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
			.addFieldColumnMap(int.class, "id", "id")
			.addFieldColumnMap(String.class, "login", "login")
			.addFieldColumnMap(String.class, "password", "password")
			.addFieldColumnMap(String.class, "name", "name")
			.addFieldColumnMap(String.class, "surname", "surname");
	}
}
