package com.example.app.core.repository;

public class Criteria {
	  protected SqlOperator operator;
	  protected String field;
	  protected Object value;
	  
	  public Criteria(String field, SqlOperator operator, Object value) {
		  this.field = field;
		  this.operator = operator;
		  this.value = value;
	  }
	  
	  public static enum SqlOperator {
		  EQUAL("="),
		  IS("is");
		  
		  private String strOperator;
		  
		  SqlOperator(String strOperator) {
			  this.strOperator = strOperator;
		  }
		  
		  @Override
		  public String toString() {
			  return strOperator;
		  }
	  }
	  
	  public String toSql(DataMap dataMap) {
		  
		  Class<?> fieldType = dataMap.getFieldType(field);
		  
		  if (value == null) {
			  return "(" + dataMap.field2Column(field) + " " + operator + " null)";
		  } else if (fieldType.equals(String.class)) {
			  return "(" + dataMap.field2Column(field) + " " + operator + " "
					  + "'" + value.toString() + "'" + ")";
		  } else if (fieldType.equals(int.class)) {
			  return "(" + dataMap.field2Column(field) + " " + operator + " " + value.toString() + ")";
		  }
		  
		  return null;
	  }
}
