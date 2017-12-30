package com.example.app.core.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DataMap {
	
	protected Class<?> modelClass;
	protected String tableName;
	protected Map<String, String> fieldColumnMap = new HashMap<>();
	protected Map<String, Field> columnFieldMap = new HashMap<>();
	
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
	
	public Field column2Field(String columnName) {
		return columnFieldMap.get(columnName);
	}
	
	public String field2Column(String fieldName) {
		return fieldColumnMap.get(fieldName);
	}
	
	public DataMap addFieldColumnMap(Class fieldType, String fieldName, String columnName) {
		fieldColumnMap.put(fieldName, columnName);
		columnFieldMap.put(columnName, new Field(fieldName, fieldType));
		return this;
	}
	
	public Set<String> getColumns() {
		return columnFieldMap.keySet();
	}
	
	public Class<?> getFieldType(String fieldName) {
		Field field = columnFieldMap.values().stream()
				.filter(columnField -> columnField.label.equals(fieldName))
				.findAny()
				.orElseThrow(() -> new IllegalArgumentException("Cannot find field " + "fieldName + in the Data Map."));
		return field.type;
	}
	
	public static class Field {
		public final String label;
		public final Class<?> type;
		
		public Field(String label, Class<?> type) {
			this.label = label;
			this.type = type;
		}
	}
}
