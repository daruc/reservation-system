package com.example.app.core.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DataMap {
	
	protected Class<?> modelClass;
	protected String tableName;
	protected Map<String, String> getterColumnMap = new HashMap<>();
	protected Map<String, String> columnSetterMap = new HashMap<>();
	
	public DataMap setModelClass(Class<?> modelClass) {
		this.modelClass = modelClass;
		return this;
	}
	
	public Class<?> getModelClass() {
		return modelClass;
	}
	
	public DataMap setTableName(String tableName) {
		this.tableName = tableName;
		return this;
	}
	
	public String getTableName() {
		return tableName;
	}
	
	public String column2Setter(String columnName) {
		return null;
	}
	
	public String getter2Column(String getterName) {
		return null;
	}
	
	public DataMap addFieldColumnMap(String fieldName, String columnName) {
		getterColumnMap.put("get" + fieldName, columnName);
		columnSetterMap.put(columnName, "set"+fieldName);
		return this;
	}
	
	public Set<String> getColumns() {
		return columnSetterMap.keySet();
	}
}
